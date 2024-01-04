package com.dnb.customerservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnb.customerservice.dto.Customer;
import com.dnb.customerservice.exception.IdNotFoundException;
import com.dnb.customerservice.exception.InvalidContactNumberException;
import com.dnb.customerservice.exception.InvalidIdException;
import com.dnb.customerservice.repo.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Optional<Customer> getCustomerById(int customerId) throws InvalidIdException {
		return customerRepository.findById(customerId);
	}

	@Override
	public boolean deleteCustomer(int customerId) throws IdNotFoundException {
		
		if (customerRepository.existsById(customerId)) {
			customerRepository.deleteById(customerId);
			
			if (!customerRepository.existsById(customerId)) {
				return true;
			}
		}
		
		throw new IdNotFoundException("customer id not found");
	}

	@Override
	public Iterable<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}

	@Override
	public Optional<Customer> getCustomerByContactNumber(String customerNumber) {
		// TODO Auto-generated method stub
		return customerRepository.findByCustomerContactNumber(customerNumber);
	}
	

	
}
