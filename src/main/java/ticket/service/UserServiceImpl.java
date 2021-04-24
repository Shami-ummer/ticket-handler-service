package ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ticket.entity.UserEntity;
import ticket.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserEntity> create(List<UserEntity> ticket) {
		return this.userRepository.saveAll(ticket);
	}

	@Override
	public void deleteAll() {
		this.userRepository.deleteAll();
	}

	@Override
	public long getCount() {
		return this.userRepository.count();
	}
}
