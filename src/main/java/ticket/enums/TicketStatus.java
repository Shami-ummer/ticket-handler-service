package ticket.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TicketStatus is used to create enum for the ticket status.
 * 
 * @author Mohammed Shameer
 */
public enum TicketStatus {
	OPEN, WAITING_ON_CUSTOMER, CUSTOMER_RESPONDED, RESOLVED, CLOSED;

	/**
	 * getOpenTickets is used to get the open tickets(NOT CLOSED).
	 * 
	 * @return
	 */
	public static List<TicketStatus> getOpenTickets() {
		return Arrays.asList(TicketStatus.values()).stream()
				.filter(predicate -> !predicate.toString().equals(TicketStatus.CLOSED.toString()))
				.collect(Collectors.toList());
	}
}
