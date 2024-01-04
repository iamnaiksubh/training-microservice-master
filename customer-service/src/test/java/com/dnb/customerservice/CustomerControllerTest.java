package com.dnb.customerservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dnb.customerservice.controller.CustomerController;
import com.dnb.customerservice.dto.Customer;
import com.dnb.customerservice.exception.IdNotFoundException;
import com.dnb.customerservice.exception.InvalidContactNumberException;
import com.dnb.customerservice.exception.InvalidIdException;
import com.dnb.customerservice.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
	
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	@Test
	public void createCustomerTest() {
		Customer customer = new Customer();
		
		when(customerService.createCustomer(customer)).thenReturn(customer);
		
		assertEquals(customer, customerController.createCustomer(customer).getBody());
	}
	
	@Test
	public void deleteCustomerByIdTest() throws IdNotFoundException {
		when(customerService.deleteCustomer(11)).thenReturn(true);
		
		assertEquals(HttpStatus.OK, customerController.deleteCustomerById(11).getStatusCode());
	}
	
	@Test
	public void deleteCustomerByIdTest_IdNotFoundException() throws IdNotFoundException {
		when(customerService.deleteCustomer(13)).thenThrow(IdNotFoundException.class);
		
		try {
			customerController.deleteCustomerById(13);
		} catch (IdNotFoundException e) {
			assertEquals("customer id not found",e.getMessage());
		}
	}
	
	@Test
	public void deleteCustomerByIdTest_NotAbleToDelete() throws IdNotFoundException {
		when(customerService.deleteCustomer(14)).thenReturn(false);
		
		assertEquals(HttpStatus.NOT_FOUND, customerController.deleteCustomerById(14).getStatusCode());
	}
	
	@Test
	public void getCustomerByIdTest() throws IdNotFoundException, InvalidContactNumberException, InvalidIdException {
		Customer customer = new Customer();
		
		when(customerService.getCustomerById(11)).thenReturn(Optional.of(customer));
		
		assertEquals(HttpStatus.OK, customerController.getCustomerById(11).getStatusCode());
	}
	
	@Test
	public void getCustomerByIdTest_IdNotFoundException() throws InvalidIdException {
		
		when(customerService.getCustomerById(0)).thenReturn(Optional.empty());
		
		try {
			customerController.getCustomerById(0);
		} catch (IdNotFoundException e) {
			assertEquals("customer id not found", e.getMessage());
		}
	}
	
	@Test
	public void getCustomerByIdTest_InvalidIdException() throws  IdNotFoundException, InvalidIdException {
		
		when(customerService.getCustomerById(-2)).thenThrow(new InvalidIdException("invalid customer id"));
		
		try {
			customerController.getCustomerById(-2);
		} catch (InvalidIdException e) {
			assertEquals("invalid customer id", e.getMessage());
		}
	}
	
	@Test
	public void getCustomerByContactNumberTest() throws InvalidContactNumberException {
		Customer customer = new Customer();
		
		when(customerService.getCustomerByContactNumber("9876543210")).thenReturn(Optional.of(customer));
		
		assertEquals(HttpStatus.OK, customerController.getCustomerByContactNumber("9876543210").getStatusCode());
	}
	
	@Test
	public void getCustomerByContactNumberTest_InvalidContactNumberException() {
		String contactNumber = "0000000000";
		
		when(customerService.getCustomerByContactNumber(contactNumber)).thenReturn(Optional.empty());
		
		try {
			customerController.getCustomerByContactNumber(contactNumber);
		} catch (InvalidContactNumberException e) {
			assertEquals("contact number does not found", e.getMessage());
		}
			
	}
	
	@Test
	public void getAllCustomerTest() throws InvalidContactNumberException, InvalidIdException {
		Customer customer1 = new Customer();
		Customer customer2 = new Customer();
		
		List<Customer> list = new ArrayList<>();
		list.add(customer1);
		list.add(customer2);
		
		
		when(customerService.getAllCustomer()).thenReturn(list);
		assertEquals(HttpStatus.OK, customerController.getAllCustomer().getStatusCode());
		assertEquals(list, customerController.getAllCustomer().getBody());
		
	}
	
}
