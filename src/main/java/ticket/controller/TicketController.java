package ticket.controller;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ticket.entity.FilterTicketEntity;
import ticket.entity.TicketEntity;
import ticket.entity.TicketResponseEntity;
import ticket.enums.TicketStatus;
import ticket.service.TicketService;

/**
 * TicketController is used to call the various apis related to ticket handling
 * functionality.
 * 
 * @author Mohammed Shameer
 *
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

	/** The Ticket Service */
	private TicketService ticketService;

	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	/**
	 * createTicket api is used to create a ticket.
	 * 
	 * @param ticket
	 * @return
	 */
	@PostMapping(value = "/createTicket")
	public ResponseEntity<Object> createTicket(@RequestBody TicketEntity ticket) {
		ticket = this.ticketService.create(ticket);
		HttpHeaders httpResponseHeaders = new HttpHeaders();
		URI newTicketUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("ticket/id-{id}")
				.buildAndExpand(ticket.getTicketId()).toUri();
		httpResponseHeaders.setLocation(newTicketUri);
		return new ResponseEntity<>(httpResponseHeaders, HttpStatus.CREATED);
	}

	/**
	 * getAllTickets is used to get all the tickets created so far.
	 * 
	 * @return
	 */
	@GetMapping(value = "/getAllTickets")
	public ResponseEntity<List<TicketEntity>> getAllTickets() {
		List<TicketEntity> tickets = this.ticketService.getAll();
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	/**
	 * getTicketsByStatus is used to get the tickets filtered by the status.
	 * 
	 * @param statusList
	 * @return
	 */
	@GetMapping(value = "/getTicketsByStatus")
	public ResponseEntity<List<TicketEntity>> getTicketsByStatus(@RequestBody List<TicketStatus> statusList) {
		List<TicketEntity> ticketsByCategory = this.ticketService.getTicketsByStatus(statusList);
		return new ResponseEntity<>(ticketsByCategory, HttpStatus.OK);
	}

	/**
	 * getTicketsByAssignedUser is used to get the tickets filtered by the assigned
	 * to user field.
	 * 
	 * @param userList
	 * @return
	 */
	@GetMapping(value = "/getTicketsByAssignedUser")
	public ResponseEntity<List<TicketEntity>> getTicketsByAssignedUser(@RequestBody List<String> userList) {
		List<TicketEntity> ticketsByCategory = this.ticketService.getTicketsByAssignedUser(userList);
		return new ResponseEntity<>(ticketsByCategory, HttpStatus.OK);
	}

	/**
	 * getTicketsByCustomer is used to get the tickets filtered by the customer.
	 * 
	 * @param statusList
	 * @return
	 */
	@GetMapping(value = "/getTicketsByCustomer")
	public ResponseEntity<List<TicketEntity>> getTicketsByCustomer(@RequestBody List<String> customerList) {
		List<TicketEntity> ticketsByCategory = this.ticketService.getTicketsByCustomer(customerList);
		return new ResponseEntity<>(ticketsByCategory, HttpStatus.OK);
	}

	/**
	 * getFilteredTickets is used to get the tickets filtered by the status,
	 * assignedToUser and customer.
	 * 
	 * @param filterItems
	 * @return
	 */
	@GetMapping(value = "/getFilteredTickets")
	public ResponseEntity<List<TicketEntity>> getFilteredTickets(@RequestBody FilterTicketEntity filterItems) {
		List<TicketEntity> ticketsByCategory = this.ticketService.getFilteredTickets(filterItems);
		return new ResponseEntity<>(ticketsByCategory, HttpStatus.OK);
	}

	/**
	 * getTicketByIdRest is used to get the single ticket details
	 * 
	 * @param ticketId
	 * @return
	 */
	@GetMapping(value = "/id-{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketEntity> getTicketByIdRest(@PathVariable("id") Long ticketId) {
		TicketEntity ticket = this.ticketService.getById(ticketId);
		return new ResponseEntity<>(ticket, HttpStatus.OK);
	}

	/**
	 * editTicket ticket is used to edit the ticket details and save the changes.
	 * 
	 * @param ticket
	 * @return
	 */
	@PutMapping(value = "/editTicket")
	public ResponseEntity<Object> editTicket(@RequestBody TicketEntity ticket) {
		ticket = this.ticketService.update(ticket);
		HttpHeaders httpResponseHeaders = new HttpHeaders();
		URI newTicketUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("ticket/id-{id}")
				.buildAndExpand(ticket.getTicketId()).toUri();
		httpResponseHeaders.setLocation(newTicketUri);
		return new ResponseEntity<>(httpResponseHeaders, HttpStatus.OK);
	}

	/**
	 * updateTicketStatus is used to update the ticket status.
	 * 
	 * @param ticket
	 * @return
	 */
	@PutMapping(value = "/updateStatus")
	public ResponseEntity<Object> updateTicketStatus(@RequestBody TicketEntity ticket) {
		this.ticketService.changeStatus(ticket.getTicketId(), ticket.getStatus());
		return new ResponseEntity<>(Collections.singletonMap("status", true), HttpStatus.OK);
	}

	/**
	 * assignAgent is used to update the assigned agent
	 * 
	 * @param ticket
	 * @return
	 */
	@PutMapping(value = "/assignAgent")
	public ResponseEntity<Object> assignAgent(@RequestBody TicketEntity ticket) {
		this.ticketService.assignAgent(ticket.getTicketId(), ticket.getAssignedToUser());
		return new ResponseEntity<>(Collections.singletonMap("status", true), HttpStatus.OK);
	}

	/**
	 * autoSelectAssignee is used to auto assign the ticket to user based on his
	 * load.
	 * 
	 * @param ticket
	 * @return
	 */
	@PutMapping(value = "/autoSelectAssignee")
	public ResponseEntity<Object> autoSelectAssignee(@RequestBody TicketEntity ticket) {
		this.ticketService.assignToUser(ticket.getTicketId());
		return new ResponseEntity<>(Collections.singletonMap("status", true), HttpStatus.OK);
	}

	/**
	 * addResponse is used to add response to the current ticket
	 * 
	 * @param ticketResponse
	 * @return
	 */
	@PostMapping(value = "/addResponse")
	public ResponseEntity<Object> addResponse(@RequestBody TicketResponseEntity ticketResponse) {
		this.ticketService.addResponse(ticketResponse);
		return new ResponseEntity<>(Collections.singletonMap("status", true), HttpStatus.CREATED);
	}

	/**
	 * deleteTicket is used to delete the ticket by its id
	 * 
	 * @param ticket
	 * @return
	 */
	@DeleteMapping(value = "/deleteTicket")
	public ResponseEntity<Object> deleteticket(@RequestBody TicketEntity ticket) {
		this.ticketService.deleteById(ticket.getTicketId());
		return new ResponseEntity<>(Collections.singletonMap("status", true), HttpStatus.NO_CONTENT);
	}

	/**
	 * changeResolvedToClosed is used to change the resolved 30 days before bugs to
	 * close.
	 * 
	 * @return
	 */
	@PutMapping(value = "/changeResolvedToClosed")
	public ResponseEntity<Object> changeResolvedToClosed() {
		this.ticketService.changeResolvedToClosed();
		return new ResponseEntity<>(Collections.singletonMap("status", true), HttpStatus.OK);
	}

}
