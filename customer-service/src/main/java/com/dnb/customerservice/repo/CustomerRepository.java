package com.dnb.customerservice.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dnb.customerservice.dto.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	Optional<Customer> findByCustomerContactNumber(String contactNumber);
}
