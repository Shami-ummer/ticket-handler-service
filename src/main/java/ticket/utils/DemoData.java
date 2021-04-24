package ticket.utils;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ticket.entity.CustomerEntity;
import ticket.entity.UserEntity;
import ticket.service.CustomerService;
import ticket.service.UserService;

/**
 * DemoData is used to insert the default data in app ready.
 * 
 * @author Mohammed Shameer
 */
@Component
public class DemoData {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		long count = customerService.getCount();
		if (count == 0) {
			customerService.create(Arrays.asList(new CustomerEntity("Customer1", "shame995@gmail.com"),
					new CustomerEntity("Customer2", "shame994@gmail.com"),
					new CustomerEntity("Customer3", "shame993@gmail.com")));
		}
		long userCount = userService.getCount();
		if (userCount == 0) {
			userService.create(Arrays.asList(new UserEntity("User1", "shame775@gmail.com"),
					new UserEntity("User2", "shame776@gmail.com"), new UserEntity("User3", "shame777@gmail.com")));
		}
	}
}