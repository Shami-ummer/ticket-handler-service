package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticket.entity.TicketResponseEntity;

/**
 * TicketResponseRepository serves as the repository class for ticket response
 * table
 * 
 * @author Mohammed Shameer
 */
@Repository
public interface TicketResponseRepository extends JpaRepository<TicketResponseEntity, Long> {
}
