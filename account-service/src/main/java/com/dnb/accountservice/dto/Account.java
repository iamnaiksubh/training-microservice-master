package com.dnb.accountservice.dto;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.dnb.accountservice.utils.CustomAccountIdGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
@Data
@NoArgsConstructor
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
	@GenericGenerator(name = "account_seq", strategy = "com.dnb.accountservice.utils.CustomAccountIdGenerator",
	parameters = {@Parameter(name = CustomAccountIdGenerator.INCREMENT_PARAM, value = "50"),
			@Parameter(name = CustomAccountIdGenerator.VALUE_PREFIX_PARAMETER, value = "A_"),
			@Parameter(name = CustomAccountIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
    private String accountId;
	@NotBlank(message = "account holder name should not be blank")
    @Column(nullable = false) private String accountHolderName;
    @Column private String accountType;

    @Min(value = 0, message = "balance can,t be negative")
    @Column private float balance;
    
    @jakarta.validation.constraints.Pattern(regexp = "^[0-9]{10}$")
    @Column private String contactNumber;
	@Column private String address;
	@Column private LocalDate accountCreatedDate = LocalDate.now();
	@NotNull 
	@Column private LocalDate dob;
    @Transient boolean accountStatus;
    private int customerId;
   
}
