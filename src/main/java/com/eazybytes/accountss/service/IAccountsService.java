package com.eazybytes.accountss.service;

import com.eazybytes.accountss.dto.CustomerDto;

public interface IAccountsService {

	 void createAccount(CustomerDto customerDto);

	    
	    CustomerDto fetchAccount(String mobileNumber);
//
//	   
//	    boolean updateAccount(CustomerDto customerDto);
//
//	   
//	    boolean deleteAccount(String mobileNumber);

}
