package ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * {@link TicketHandlerApp} is the entry point of the Custom Desginer service
 * web application
 *
 */
@SpringBootApplication
public class TicketHandlerApp {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/ticket-handler-service");
		SpringApplication.run(TicketHandlerApp.class, args);
	}

}
