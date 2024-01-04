package com.dnb.customerservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnb.customerservice.dto.Customer;
import com.dnb.customerservice.exception.IdNotFoundException;
import com.dnb.customerservice.exception.InvalidContactNumberException;
import com.dnb.customerservice.exception.InvalidIdException;
import com.dnb.customerservice.service.CustomerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
		return new ResponseEntity(customerService.createCustomer(customer), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") int customerId) throws IdNotFoundException {
		try {
			if (customerService.deleteCustomer(customerId)) {
				return ResponseEntity.ok("customer deleted");
			}
		} catch (IdNotFoundException e) {
			throw new IdNotFoundException("customer id not found");
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<?> getCustomerById(@PathVariable("customerId") int customerId) throws IdNotFoundException, InvalidIdException{
		Optional<Customer> customerOptional;
		try {
			customerOptional = customerService.getCustomerById(customerId);
			if (customerOptional.isPresent()) {
				return ResponseEntity.ok(customerOptional.get());
			}
			
			throw new IdNotFoundException("customer id not found");
			
		} catch (InvalidIdException e) {
			throw new InvalidIdException("invalid customer id");
		}
		
	}
	
	
	@GetMapping("/cn/{contactNumber:^[0-9]{10}$}")
	public ResponseEntity<?> getCustomerByContactNumber(@PathVariable("contactNumber") String contactNumber) throws InvalidContactNumberException{
		Optional<Customer> result =  customerService.getCustomerByContactNumber(contactNumber);
		
		if(!result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		
		throw new InvalidContactNumberException("contact number does not found");
	}
	
	
	@GetMapping("/allCustomer")
	public ResponseEntity<?> getAllCustomer(){
		Iterable<Customer> result = null;
		result = customerService.getAllCustomer();
		
		return ResponseEntity.ok(result);
		
	}
	
}
