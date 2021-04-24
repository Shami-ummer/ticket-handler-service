package ticket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ticket.entity.FilterTicketEntity;
import ticket.entity.TicketEntity;
import ticket.entity.TicketResponseEntity;
import ticket.enums.TicketStatus;

/**
 * TicketService is the service class for ticket detail table
 * 
 * @author Mohammed Shameer
 */
@Service
public interface TicketService {

	/**
	 * getById is used to get the single ticket details
	 * 
	 * @param ticketId
	 * @return
	 */
	public TicketEntity getById(Long ticketId);

	/**
	 * create method is used to create a ticket.
	 * 
	 * @param ticket
	 * @return
	 */
	public TicketEntity create(TicketEntity ticket);

	/**
	 * update ticket is used to update the ticket details and save the changes.
	 * 
	 * @param ticket
	 * @return
	 */
	public TicketEntity update(TicketEntity ticket);

	/**
	 * getAll is used to get all the tickets created so far.
	 * 
	 * @return
	 */
	public List<TicketEntity> getAll();

	/**
	 * changeStatus is used to update the ticket status.
	 * 
	 * @param ticket
	 */
	public void changeStatus(Long ticketId, TicketStatus status);

	/**
	 * getTicketsByStatus is used to get the tickets filtered by the status.
	 * 
	 * @param statusList
	 * @return
	 */
	public List<TicketEntity> getTicketsByStatus(List<TicketStatus> statusList);

	/**
	 * getTicketsByAssignedUser is used to get the tickets filtered by the assigned
	 * to user field.
	 * 
	 * @param userList
	 * @return
	 */
	public List<TicketEntity> getTicketsByAssignedUser(List<String> userList);

	/**
	 * getTicketsByCustomer is used to get the tickets filtered by the customer.
	 * 
	 * @param statusList
	 * @return
	 */
	public List<TicketEntity> getTicketsByCustomer(List<String> customerList);

	/**
	 * getFilteredTickets is used to get the tickets filtered by the status,
	 * assignedToUser and customer.
	 * 
	 * @param filterItems
	 * @return
	 */
	public List<TicketEntity> getFilteredTickets(FilterTicketEntity filterItems);

	/**
	 * deleteTicket is used to delete the ticket by its id
	 * 
	 * @param ticket
	 */
	public void deleteById(Long ticketId);

	/**
	 * addResponse is used to add response to the current ticket
	 * 
	 * @param ticketResponse
	 */
	public void addResponse(TicketResponseEntity ticketResponse);

	/**
	 * assignToUser is used to auto assign the ticket to user based on his load.
	 * 
	 * @param ticket
	 */
	public void assignToUser(Long ticketId);

	/**
	 * changeResolvedToClosed is used to change the resolved 30 days before bugs to
	 * close.
	 */
	public void changeResolvedToClosed();

	/**
	 * assignAgent is used to assign the user to the ticket
	 * 
	 * @param ticketId
	 * @param assignedToUser
	 */
	public void assignAgent(Long ticketId, String assignedToUser);
}
