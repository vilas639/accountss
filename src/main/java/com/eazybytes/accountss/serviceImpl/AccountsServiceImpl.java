package com.eazybytes.accountss.serviceImpl;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eazybytes.accountss.constants.AccountsConstants;
import com.eazybytes.accountss.dto.AccountsDto;
import com.eazybytes.accountss.dto.CustomerDto;
import com.eazybytes.accountss.entity.Accounts;
import com.eazybytes.accountss.entity.Customer;
import com.eazybytes.accountss.exception.CustomerAlreadyExistsException;
import com.eazybytes.accountss.exception.ResourceNotFoundException;
import com.eazybytes.accountss.mapper.AccountsMapper;
import com.eazybytes.accountss.mapper.CustomerMapper;
import com.eazybytes.accountss.repository.AccountsRepository;
import com.eazybytes.accountss.repository.CustomerRepository;
import com.eazybytes.accountss.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl  implements IAccountsService {

	@Autowired
    private AccountsRepository accountsRepository;
	
	@Autowired
    private CustomerRepository customerRepository;

    
    @Override
    public void createAccount(CustomerDto customerDto) {
    	
    
    
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
  
    }
    
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		// TODO Auto-generated method stub
		
		Optional<Customer> customer = customerRepository.findByMobileNumber(mobileNumber);
		
		
		if (customer.isPresent()) {
		    Customer customerDetails = customer.get();
		    // Proceed with your logic for the customer
		} else {
		    // Handle the case where no customer is found
			System.out.println("custoemr"+customer.get());
			new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber);
		}

		
		
		Optional<Accounts> accounts = accountsRepository.findByCustomerId(customer.get().getCustomerId());
		
		 
		if (accounts.isPresent()) {
		    Accounts accountDetails = accounts.get();
		    // Proceed with your logic for the customer
		} else {
		    // Handle the case where no customer is found
			new ResourceNotFoundException("Account", "customerId", customer.get().getCustomerId().toString());
		}
		
	
	        		
	        		
	        
	        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer.get(), new CustomerDto());
	        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts.get(), new AccountsDto()));
	       
	        return customerDto;
	}
    
}

   
    