package ticket.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import ticket.enums.TicketPriority;
import ticket.enums.TicketStatus;
import ticket.enums.TicketType;

/**
 * TicketEntity is used to create the ticket detail table with transaction
 * data..
 * 
 * @author Mohammed Shameer
 */
@Entity
@Data
@Table(name = "ticket_detail")
@SecondaryTable(name = "ticket_detail_response", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ticketId"))
public class TicketEntity {

	@Id
	@GeneratedValue
	public Long ticketId;

	@NotNull
	private TicketType type;

	private String description;

	private String title;

	@NotNull
	private String createdUser;

	@NotNull
	private String customer;

	private String assignedToUser;

	@NotNull
	private TicketStatus status;

	private Date statusUpdatedDate;

	@NotNull
	private TicketPriority priority;

	@Column(table = "ticket_detail_response")
	private String response;

	@Column(table = "ticket_detail_response")
	private Date responseDateTime;
}
