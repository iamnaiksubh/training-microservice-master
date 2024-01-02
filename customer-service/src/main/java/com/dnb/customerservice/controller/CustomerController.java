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
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
		return new ResponseEntity(customerService.createCustomer(customer), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") int customerId) {
		try {
			if (customerService.deleteCustomer(customerId)) {
				return ResponseEntity.ok("customer deleted");
			}
		} catch (IdNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<?> getCustomerById(@PathVariable("customerId") int customerId) throws IdNotFoundException{
		try {
			System.out.println("this service is running");
			Optional<Customer> customerOptional =  customerService.getCustomerById(customerId);
			
			if (customerOptional.isPresent()) {
				return ResponseEntity.ok(customerOptional.get());
			}
			
			throw new IdNotFoundException("customer id not found");
		} catch (InvalidContactNumberException | InvalidIdException e) {
			throw new IdNotFoundException("customer id not found");
		}
	}
	

	// fetch the details based on contact number
	
	@GetMapping("/cn/{contactNumber:^[0-9]{10}$}")
	public ResponseEntity<?> getCustomerByContactNumber(@PathVariable("contactNumber") String contactNumber) throws InvalidContactNumberException{
		Optional<Customer> result =  customerService.getCustomerByContactNumber(contactNumber);
		
		if(!result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		
		throw new InvalidContactNumberException("contact number does not found");
	}
	
	
	@GetMapping("/allCustomer")
	public ResponseEntity<?> getAllCustomer() throws InvalidContactNumberException{
		Iterable<Customer> result = null;
		try {
//			try {
				result = customerService.getAllCustomer();
//			}
//			catch (InvalidPanException | InvalidUUIDException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			return ResponseEntity.ok(result);
		} catch (InvalidContactNumberException | InvalidIdException e) {
			throw new InvalidContactNumberException("contact number does not found");
		}
		
	}
	
}
