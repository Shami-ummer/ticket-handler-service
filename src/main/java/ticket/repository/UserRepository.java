package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticket.entity.UserEntity;

/**
 * UserRepository serves as the repository class for user master table
 * 
 * @author Mohammed Shameer
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
