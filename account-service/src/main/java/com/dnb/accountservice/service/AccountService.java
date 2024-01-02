package com.dnb.accountservice.service;


import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.exception.IdNotFoundException;
import com.dnb.accountservice.exception.InvalidContactNumberException;
import com.dnb.accountservice.exception.InvalidDateException;
import com.dnb.accountservice.exception.InvalidFloatException;
import com.dnb.accountservice.exception.InvalidIdException;


public interface AccountService {
    public Account createAccount(Account account) throws InvalidContactNumberException, InvalidIdException, IdNotFoundException ;
    public Optional<Account> getAccountById(String accountId) throws InvalidNameException, InvalidDateException, InvalidContactNumberException, InvalidFloatException, InvalidIdException;
    public boolean deleteAccount(String accountId) throws IdNotFoundException;
    public boolean updateAccount(String accountId);
    public Iterable<Account> getAllAccount() throws InvalidNameException, InvalidDateException, InvalidContactNumberException, InvalidFloatException, InvalidIdException;
    public boolean exitsById(String accountId);
    public List<Account> getAccountByContactNumber(String contactNumber);
}
