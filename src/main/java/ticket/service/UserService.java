package ticket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ticket.entity.UserEntity;

/**
 * UserService is the service class for user master table
 * 
 * @author Mohammed Shameer
 */
@Service
public interface UserService {

	/**
	 * create method is used to create a user.
	 * 
	 * @param ticket
	 * @return
	 */
	public List<UserEntity> create(List<UserEntity> user);

	/**
	 * deleteAll is used to delete all users in the table.
	 * 
	 */
	public void deleteAll();

	/**
	 * getCount is used to get the count of users present.
	 * 
	 * @return
	 */
	public long getCount();
}
