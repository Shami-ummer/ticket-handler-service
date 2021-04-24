package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticket.entity.CustomerEntity;

/**
 * CustomerRepository serves as the repository class for customer master table.
 * 
 * @author Mohammed Shameer
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
}
