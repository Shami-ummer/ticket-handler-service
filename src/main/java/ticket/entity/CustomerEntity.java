package ticket.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;

/**
 * CustomerEntity is used to create the customer Master Table.
 * 
 * @author Mohammed Shameer
 */
@Entity
@Data
@Table(name = "customer_master")
public class CustomerEntity {

	@Id
	private String customer;

	@NotNull
	private String emailId;

	public CustomerEntity(String customer, String mailId) {
		this.customer = customer;
		this.emailId = mailId;
	}

	public CustomerEntity() {
	}
}
