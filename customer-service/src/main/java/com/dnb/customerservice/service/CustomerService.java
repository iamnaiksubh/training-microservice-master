package com.dnb.customerservice.service;

import java.util.Optional;

import com.dnb.customerservice.dto.Customer;
import com.dnb.customerservice.exception.IdNotFoundException;
import com.dnb.customerservice.exception.InvalidContactNumberException;
import com.dnb.customerservice.exception.InvalidIdException;

public interface CustomerService {
	 public Customer createCustomer(Customer customer);
	    public Optional<Customer> getCustomerById(int customerId) throws InvalidIdException;
	    public boolean deleteCustomer(int customerId) throws IdNotFoundException;
	    public Iterable<Customer> getAllCustomer();
	    public Optional<Customer> getCustomerByContactNumber(String customerNumber);
}
