package ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ticket.entity.CustomerEntity;
import ticket.repository.CustomerRepository;

@Component
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<CustomerEntity> create(List<CustomerEntity> ticket) {
		return this.customerRepository.saveAll(ticket);
	}

	@Override
	public void deleteAll() {
		this.customerRepository.deleteAll();
	}

	@Override
	public long getCount() {
		return this.customerRepository.count();
	}
}
