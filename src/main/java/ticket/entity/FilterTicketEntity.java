package ticket.entity;

import java.util.List;

import lombok.Data;
import ticket.enums.TicketStatus;

/**
 * FilterTicketEntity is used to filter the tickets based on the below three
 * values
 * 
 * @author Mohammed Shameer
 */
@Data
public class FilterTicketEntity {

	private List<String> assignedToUser;

	private List<String> customer;

	private List<TicketStatus> status;
}
