package com.dnb.customerservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
	public void getCustomerByIdTest() throws InvalidContactNumberException, InvalidIdException, IdNotFoundException {
		Customer customer = new Customer();
		customer.setCustomerId(11);
		
		when(customerService.getCustomerById(11)).thenReturn(Optional.of(customer));
		
		assertEquals(HttpStatus.OK, customerController.getCustomerById(11).getStatusCode());
	}
	
	@Test
	public void getCustomerByContactNumberTest() throws InvalidContactNumberException {
		Customer customer = new Customer();
		customer.setCustomerContactNumber("9876543210");
		
		when(customerService.getCustomerByContactNumber("9876543210")).thenReturn(Optional.of(customer));
		
		assertEquals(HttpStatus.OK, customerController.getCustomerByContactNumber("9876543210").getStatusCode());
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
