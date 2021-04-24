package ticket.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ticket.entity.TicketCountEntity;
import ticket.entity.TicketEntity;
import ticket.enums.TicketStatus;

/**
 * TicketRepository serves as the repository class for ticket detail table
 * 
 * @author Mohammed Shameer
 */
@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

	@Query("SELECT ticket FROM TicketEntity ticket WHERE ticket.status IN ?1")
	List<TicketEntity> getTicketsByStatus(List<TicketStatus> statusList);

	@Query("SELECT ticket FROM TicketEntity ticket WHERE ticket.assignedToUser IN ?1")
	List<TicketEntity> getTicketsByAssignedUser(List<String> userList);

	@Query("SELECT ticket FROM TicketEntity ticket WHERE ticket.customer IN ?1")
	List<TicketEntity> getTicketsByCustomer(List<String> customerList);

	@Query("SELECT ticket FROM TicketEntity ticket WHERE ticket.assignedToUser IN ?1 AND ticket.customer IN ?2 AND ticket.status IN ?3")
	List<TicketEntity> getFilteredTickets(List<String> assignedToUser, List<String> customer,
			List<TicketStatus> status);

	@Query("SELECT new ticket.entity.TicketCountEntity(ticket.assignedToUser, COUNT(ticket.assignedToUser) AS userCount) FROM TicketEntity ticket WHERE ticket.status IN ?1 GROUP BY ticket.assignedToUser ORDER BY userCount DESC")
	List<TicketCountEntity> getUserTicketCount(List<TicketStatus> openTickets);

	@Query("SELECT ticket FROM TicketEntity ticket WHERE ticket.status IN ?1 AND ticket.statusUpdatedDate < ?2")
	List<TicketEntity> getTicketByStatusAndDate(TicketStatus resolved, Date resolvedDate);

}
