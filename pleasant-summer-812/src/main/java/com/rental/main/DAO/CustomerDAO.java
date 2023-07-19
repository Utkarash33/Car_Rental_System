package com.rental.main.DAO;

import com.rental.main.entities.Customer;
import com.rental.main.exceptions.NoRecordException;

public interface CustomerDAO {

	void addCustomer(Customer customer);

	void customerLogin(String username, String password) throws NoRecordException;

}
