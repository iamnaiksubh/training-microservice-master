package com.dnb.accountservice.controller;

import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.exception.IdNotFoundException;
import com.dnb.accountservice.exception.InvalidContactNumberException;
import com.dnb.accountservice.exception.InvalidDateException;
import com.dnb.accountservice.exception.InvalidFloatException;
import com.dnb.accountservice.exception.InvalidIdException;
import com.dnb.accountservice.payload.request.AccountRequest;
import com.dnb.accountservice.service.AccountService;
import com.dnb.accountservice.utils.RequestToEntityMapper;

import jakarta.validation.Valid;

@RefreshScope
@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	AccountService accountService;
	
	@Autowired
	RequestToEntityMapper mapper;
	
	@Value("${customProperty.test}")
	private String test;
	
	@GetMapping("/test")
	public ResponseEntity<String> getTest(){
		return ResponseEntity.ok(test);
	}
	
	
	@DeleteMapping("/{accountId}")
	public ResponseEntity<?> deleteAccountById(@PathVariable("accountId") String accountId) throws IdNotFoundException{
		if (accountService.exitsById(accountId)) {
			try {
				 accountService.deleteAccount(accountId);
				 return ResponseEntity.ok("account deleted");
			} catch (IdNotFoundException e) {
				throw new IdNotFoundException("account does not exist");
			}
		}else {
			throw new IdNotFoundException("account does not exist");
		}
		
		
	}
	
	//Path Variable
	@GetMapping("/{accountId}")
	public ResponseEntity<?> getAccountById(@PathVariable("accountId") String accountId) throws IdNotFoundException{
		try {
			Optional<Account> account = accountService.getAccountById(accountId);
			
			if (account.isPresent()) {
				return ResponseEntity.ok(account.get());
			}else {
				
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("message", "id not found");
//				map.put("HttpStatus", HttpStatus.NOT_FOUND + "");
//				ResponseEntity<?> entity = new ResponseEntity(map, HttpStatus.NOT_FOUND);
//				return entity;
//				return ResponseEntity.notFound().build()
				throw new IdNotFoundException("account id not found");
			}
			
		} catch (InvalidNameException | InvalidDateException | InvalidContactNumberException | InvalidFloatException
				| InvalidIdException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	
//insert / create account : post : @PostMapping
	@PostMapping("/create") // @RequestMapping + post method ==> spring 4.3.x
	public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest accountRequest) {
//		try {
//			Account newAccount = accountService.createAccount(account);
//			return new ResponseEntity(newAccount, HttpStatus.CREATED);
//		} catch (InvalidPanException | InvalidUUIDException | InvalidContactNumberException | InvalidIdException
//				| IdNotFoundException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
		Account account = mapper.getAccountEntityObject(accountRequest);
		Account createdAccount;
		try {
			createdAccount = accountService.createAccount(account);
			return ResponseEntity.ok(createdAccount);
		} catch (InvalidContactNumberException | InvalidIdException
				| IdNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}
	
	
	// fetch the details based on contact number
	
	@GetMapping("/cn/{contactNumber:^[0-9]{10}$}")
	public ResponseEntity<?> getAccountByContactNumber(@PathVariable("contactNumber") String contactNumber) throws InvalidContactNumberException{
		List<Account> result =  accountService.getAccountByContactNumber(contactNumber);
		
		if(!result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		
		throw new InvalidContactNumberException("contact number does not found");
	}
	
	
	@GetMapping("/allAccount")
	public ResponseEntity<?> getAllAccount() throws InvalidContactNumberException{
		List<Account> result;
		try {
			result = (List<Account>) accountService.getAllAccount();
			
			return ResponseEntity.ok(result);
		} catch (InvalidNameException | InvalidDateException | InvalidContactNumberException | InvalidFloatException
				| InvalidIdException e) {
			throw new InvalidContactNumberException("contact number does not found");
		}
		
	}

}
