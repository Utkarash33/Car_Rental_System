package com.rental.main.services;

import com.rental.main.entities.Customer;
import com.rental.main.exceptions.NoRecordException;

public interface CustomerServices {

	void addCustomer(Customer customer);

	void customerLogin(String username, String password) throws NoRecordException;
}
