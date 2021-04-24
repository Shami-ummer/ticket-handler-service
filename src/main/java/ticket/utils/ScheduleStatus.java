package ticket.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ticket.service.TicketService;

/**
 * ScheduleStatus is used to schedule the resolved to close method daily once
 * using cron expression.
 * 
 * @author Mohammed Shameer
 */
@Component
@EnableScheduling
public class ScheduleStatus {

	@Autowired
	private TicketService ticketService;

	/**
	 * changeResolvedToClosed is used to change status to closed daily.
	 * 
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void changeResolvedToClosed() {
		ticketService.changeResolvedToClosed();

	}
}