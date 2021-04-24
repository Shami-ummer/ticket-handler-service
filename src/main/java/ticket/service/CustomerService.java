package ticket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ticket.entity.CustomerEntity;

/**
 * CustomerService is the service class for customer master table
 * 
 * @author Mohammed Shameer
 */
@Service
public interface CustomerService {

	/**
	 * create method is used to create customers.
	 * 
	 * @param ticket
	 * @return
	 */
	public List<CustomerEntity> create(List<CustomerEntity> ticket);

	/**
	 * deleteAll is used to delete all customers in the table.
	 * 
	 */
	public void deleteAll();

	/**
	 * getCount is used to get the count of customers present.
	 * 
	 * @return
	 */
	public long getCount();
}
