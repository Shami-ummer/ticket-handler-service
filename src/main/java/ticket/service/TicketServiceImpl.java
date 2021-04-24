package ticket.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import ticket.entity.CustomerEntity;
import ticket.entity.FilterTicketEntity;
import ticket.entity.TicketCountEntity;
import ticket.entity.TicketEntity;
import ticket.entity.TicketResponseEntity;
import ticket.entity.UserEntity;
import ticket.enums.TicketStatus;
import ticket.exception.ResourceNotFoundException;
import ticket.repository.CustomerRepository;
import ticket.repository.TicketRepository;
import ticket.repository.TicketResponseRepository;
import ticket.repository.UserRepository;

@Component
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private TicketResponseRepository ticketResponseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public TicketEntity getById(Long ticketId) {
		TicketEntity ticket = this.ticketRepository.findById(ticketId).orElse(null);
		verifyTicketExists(ticket, ticketId);
		return ticket;
	}

	@Override
	public TicketEntity create(TicketEntity ticket) {
		if (StringUtils.isEmpty(ticket.getAssignedToUser())) {
			ticket.setAssignedToUser(getAssignUser());
		}
		return this.ticketRepository.save(ticket);
	}

	@Override
	public TicketEntity update(TicketEntity ticket) {
		return this.ticketRepository.saveAndFlush(ticket);
	}

	@Override
	public List<TicketEntity> getAll() {
		return this.ticketRepository.findAll();
	}

	@Override
	public void changeStatus(Long ticketId, TicketStatus status) {
		TicketEntity ticket = this.getById(ticketId);
		verifyTicketExists(ticket, ticketId);
		ticket.setStatus(status);
		ticket.setStatusUpdatedDate(new Date());
		this.ticketRepository.save(ticket);
	}

	/**
	 * verifyTicketExists is used to verify whether the ticket exists or not.
	 * 
	 * @param ticket
	 * @param ticketId
	 * @throws ResourceNotFoundException
	 */
	private void verifyTicketExists(TicketEntity ticket, Long ticketId) throws ResourceNotFoundException {
		if (ticket == null) {
			throw new ResourceNotFoundException("Ticket with id " + ticketId + " not found");
		}
	}

	@Override
	public List<TicketEntity> getTicketsByStatus(List<TicketStatus> statusList) {
		return CollectionUtils.isNotEmpty(statusList) ? this.ticketRepository.getTicketsByStatus(statusList)
				: this.ticketRepository.findAll();
	}

	@Override
	public List<TicketEntity> getTicketsByAssignedUser(List<String> userList) {
		return CollectionUtils.isNotEmpty(userList) ? this.ticketRepository.getTicketsByAssignedUser(userList)
				: this.ticketRepository.findAll();
	}

	@Override
	public List<TicketEntity> getTicketsByCustomer(List<String> customerList) {
		return CollectionUtils.isNotEmpty(customerList) ? this.ticketRepository.getTicketsByCustomer(customerList)
				: this.ticketRepository.findAll();
	}

	@Override
	public List<TicketEntity> getFilteredTickets(FilterTicketEntity filterItems) {
		return this.ticketRepository.getFilteredTickets(filterItems.getAssignedToUser(), filterItems.getCustomer(),
				filterItems.getStatus());
	}

	@Override
	public void deleteById(Long ticketId) {
		TicketEntity ticket = this.getById(ticketId);
		verifyTicketExists(ticket, ticketId);
		this.ticketRepository.deleteById(ticketId);
	}

	@Override
	public void addResponse(TicketResponseEntity ticketResponse) {
		TicketEntity ticket = this.getById(ticketResponse.getTicketId());
		verifyTicketExists(ticket, ticketResponse.getTicketId());
		this.ticketResponseRepository.save(ticketResponse);
		try {
			sendMailToCustomer(ticket);
		} catch (IOException e) {
			throw new ResourceNotFoundException("Response Updated. But Mail Sending Failed");
		}
	}

	/**
	 * sendMailToCustomer is used to send mail through sendgrid api.
	 * 
	 * @param ticket
	 * @throws IOException
	 */
	private void sendMailToCustomer(TicketEntity ticket) throws IOException {
		Email from = new Email("yogesh@sinecycle.com");
		String subject = "Ticket:" + ticket.getTicketId() + " Notification";
		Email to = new Email(getCustomerMailId(ticket.getCustomer()));
		Content content = new Content("text/plain", "Response Added<br><br>" + ticket.getResponse());
		Mail mail = new Mail(from, subject, to, content);
		final SendGrid sg = new SendGrid("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");
		final Request request = new Request();
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		request.setBody(mail.build());
		sg.api(request);
	}

	/**
	 * getCustomerMailId is used to get the customer mail id.
	 * 
	 * @param customer
	 * @return
	 */
	private String getCustomerMailId(String customer) {
		CustomerEntity customerEntity = this.customerRepository.findById(customer).orElse(null);
		if (customerEntity == null) {
			throw new ResourceNotFoundException("Invalid Customer " + customer + " Configuration in Customer Master");
		}
		return customerEntity.getEmailId();
	}

	@Override
	public void assignToUser(Long ticketId) {
		TicketEntity ticket = this.getById(ticketId);
		verifyTicketExists(ticket, ticketId);
		updateUser(ticketId, getAssignUser());
	}

	/**
	 * getAssignUser methods auto checks which user we need to assign
	 * 
	 * @return
	 */
	private String getAssignUser() {
		List<TicketCountEntity> ticketCountData = this.ticketRepository
				.getUserTicketCount(TicketStatus.getOpenTickets());
		List<UserEntity> allUserList = this.userRepository.findAll();
		if (CollectionUtils.isEmpty(allUserList)) {
			throw new ResourceNotFoundException("No User Found");
		}
		Map<String, Long> userCountMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(ticketCountData)) {
			userCountMap.putAll(ticketCountData.stream().collect(
					Collectors.toMap(keyMapper -> keyMapper.getUserName(), valueMapper -> valueMapper.getUserCount())));
			List<UserEntity> unAssignedUserList = allUserList.stream()
					.filter(predicate -> !userCountMap.containsKey(predicate.getUserName()))
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(unAssignedUserList)) {
				// unAssignedUserList - if any user is left without any tickets
				return unAssignedUserList.get(0).getUserName();
			} else if (CollectionUtils.isNotEmpty(allUserList)) {
				List<TicketCountEntity> emptyFilteredList = ticketCountData.stream()
						.filter(predicate -> Objects.nonNull(predicate.getUserName())
								&& StringUtils.isNotEmpty(predicate.getUserName()))
						.collect(Collectors.toList());
				Collections.sort(emptyFilteredList, Comparator.comparingLong(TicketCountEntity::getUserCount));
				// emptyFilteredList - for finding the minimum ticket holder.
				return emptyFilteredList.get(0).getUserName();
			} else {
				throw new ResourceNotFoundException("No User Found");
			}
		} else {
			// allUserList - if all are free, random pick
			return allUserList.get(0).getUserName();
		}
	}

	/**
	 * updateUser is user to update the ticket details with the latest user.
	 * 
	 * @param ticketId
	 * @param userName
	 */
	private void updateUser(Long ticketId, String userName) {
		TicketEntity ticket = this.getById(ticketId);
		verifyTicketExists(ticket, ticketId);
		ticket.setAssignedToUser(userName);
		this.ticketRepository.save(ticket);
	}

	@Override
	public void changeResolvedToClosed() {
		List<TicketEntity> ticketList = this.ticketRepository.getTicketByStatusAndDate(TicketStatus.RESOLVED,
				getResolvedDate());
		ticketList.stream().forEach(action -> {
			action.setStatus(TicketStatus.CLOSED);
			action.setStatusUpdatedDate(new Date());
		});
		this.ticketRepository.saveAll(ticketList);
	}

	/**
	 * getResolvedDate is used to calculate 30daya before current date to change
	 * status automatically.
	 * 
	 * @return
	 */
	private Date getResolvedDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -30);
		return calendar.getTime();
	}

	@Override
	public void assignAgent(Long ticketId, String assignedToUser) {
		TicketEntity ticket = this.getById(ticketId);
		verifyTicketExists(ticket, ticketId);
		if (StringUtils.isEmpty(assignedToUser)) {
			throw new ResourceNotFoundException("Assigned User cannot be null or empty");
		}
		ticket.setAssignedToUser(assignedToUser);
		this.ticketRepository.save(ticket);
	}
}
