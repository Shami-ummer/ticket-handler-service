package ticket.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;

/**
 * UserEntity is used to create the user Master Table.
 * 
 * @author Mohammed Shameer
 */
@Entity
@Data
@Table(name = "user_master")
public class UserEntity {

	@Id
	private String userName;

	@NotNull
	private String emailId;

	public UserEntity(String userName, String emailId) {
		this.userName = userName;
		this.emailId = emailId;
	}

	public UserEntity() {
	}
}
