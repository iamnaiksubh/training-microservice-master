package com.dnb.accountservice.service;

import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.dto.Customer;
import com.dnb.accountservice.exception.IdNotFoundException;
import com.dnb.accountservice.exception.InvalidContactNumberException;
import com.dnb.accountservice.exception.InvalidDateException;
import com.dnb.accountservice.exception.InvalidFloatException;
import com.dnb.accountservice.exception.InvalidIdException;
import com.dnb.accountservice.repo.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	RestTemplate restTemplate;

//	@Autowired
//	APIClient apiClient;
	
	@Value("${api.customer}")
	private String URL;
	
//	@Autowired
//	CustomerRepository customerRepository;

	@Override
	public Account createAccount(Account account)
			throws InvalidContactNumberException, InvalidIdException, IdNotFoundException {

//		Optional<Customer> customer = customerRepository.findById(account.getCustomer().getCustomerId());
//		account.setCustomer(customer.get());
		System.out.println("customer id "+account.getCustomerId());
		try {
		 ResponseEntity<Customer> responseEntity =
		 restTemplate.getForEntity(URL+"/"+account.getCustomerId(), Customer.class);
			
//		apiClient.getCustomerById(account.getCustomerId());
		
		 
//		System.out.println(responseEntity.getBody());
		return accountRepository.save(account);
		}catch(Exception e) {
//			System.out.println(e.getMessage());
			throw new IdNotFoundException("Customer id not found");
		}
		// if (customer.isPresent()) {
		
//		}else 
//			customer.orElseThrow(() -> new IdNotFoundException("Customer Id is not valid"));

//		return null;

	}

	@Override
	public Optional<Account> getAccountById(String accountId) throws InvalidNameException, InvalidDateException,
			InvalidContactNumberException, InvalidFloatException, InvalidIdException {
		// TODO Auto-generated method stub
		return accountRepository.findById(accountId);
	}

	@Override
	public boolean deleteAccount(String accountId) throws IdNotFoundException {
		if (accountRepository.existsById(accountId)) {
			accountRepository.deleteById(accountId);

			if (!accountRepository.existsById(accountId)) {
				return true;
			}
		}
		throw new IdNotFoundException("account nahi mila re tera");
	}

	@Override
	public boolean updateAccount(String accountId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Account> getAllAccount() throws InvalidNameException, InvalidDateException,
			InvalidContactNumberException, InvalidFloatException, InvalidIdException {
		// TODO Auto-generated method stub

		return accountRepository.findAll();
	}

	@Override
	public boolean exitsById(String accountId) {
		return accountRepository.existsById(accountId);
	}

	@Override
	public List<Account> getAccountByContactNumber(String contactNumber) {

		return accountRepository.findByContactNumber(contactNumber);
	}

}
