package com.dnb.customerservice.payload.request;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

@Component
public class CustomerRequest {
	private int customerId;
	private String customerName;
	private String customerContactNumber;
	private String customerAddress;
	private String customerPan;
 	private String customerUUID;
}
