package ticket.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ticket.entity.CustomerEntity;
import ticket.entity.UserEntity;
import ticket.service.CustomerService;
import ticket.service.UserService;

/**
 * DemoDataController is used to insert the demo sample data in customer master
 * and user master tables.
 * 
 * @author Mohammed Shameer
 *
 */
@RestController
@RequestMapping("/data")
public class DemoDataController {

	/** The Customer service */
	private CustomerService customerService;

	/** The User Service */
	private UserService userService;

	public DemoDataController(CustomerService customerService, UserService userService) {
		this.customerService = customerService;
		this.userService = userService;
	}

	/**
	 * addCustomers is used to add customer list in customer master.
	 * 
	 * @param customerList
	 * @return
	 */
	@PostMapping(value = "/addNewCustomers")
	public ResponseEntity<Object> addCustomers(@RequestBody List<CustomerEntity> customerList) {
		this.customerService.deleteAll();
		this.customerService.create(customerList);
		return new ResponseEntity<>(Collections.singletonMap("status", true), HttpStatus.CREATED);
	}

	/**
	 * addUsers is used to add users in user master table
	 * 
	 * @param userList
	 * @return
	 */
	@PostMapping(value = "/addNewUsers")
	public ResponseEntity<Object> addUsers(@RequestBody List<UserEntity> userList) {
		this.userService.deleteAll();
		this.userService.create(userList);
		return new ResponseEntity<>(Collections.singletonMap("status", true), HttpStatus.CREATED);
	}
}
