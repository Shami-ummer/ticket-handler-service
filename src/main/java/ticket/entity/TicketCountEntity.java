package ticket.entity;

import lombok.Data;

/**
 * TicketCountEntity is used to implement auto assigning to user based on his
 * load.
 * 
 * @author Mohammed Shameer
 */
@Data
public class TicketCountEntity {

	private String userName;

	private long userCount;

	public TicketCountEntity(String userName, Long userCount) {
		this.userName = userName;
		this.userCount = userCount;
	}
}
