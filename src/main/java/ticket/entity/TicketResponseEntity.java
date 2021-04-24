package ticket.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.Data;

/**
 * TicketResponseEntity is used to create the response that is maintained in the
 * seperate table.
 * 
 * @author Mohammed Shameer
 */
@Entity
@Data
@Table(name = "ticket_detail_response")
public class TicketResponseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long ticketId;

	@NotNull
	private String response;

	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date responseDateTime;

}
