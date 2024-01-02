package com.dnb.customerservice.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

@Data
@NoArgsConstructor
@Entity
public class Customer {
	@Id 
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int customerId;
	private String customerName;
	private String customerContactNumber;
	private String customerAddress;
	private String customerPan;
 	private String customerUUID;
	
// 	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
// 	@JsonIgnoreProperties(value = "customer")
// 	@JsonIgnore
// 	List<Account> accountList = new ArrayList<>();

}
