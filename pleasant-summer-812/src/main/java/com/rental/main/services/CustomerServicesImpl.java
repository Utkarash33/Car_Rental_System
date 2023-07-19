package com.rental.main.services;

import com.rental.main.DAO.CustomerDAO;
import com.rental.main.DAO.CustomerDAOImpl;
import com.rental.main.entities.Customer;
import com.rental.main.exceptions.NoRecordException;

public class CustomerServicesImpl implements CustomerServices {

	@Override
	public void addCustomer(Customer customer) {
		
		
		CustomerDAO dao = new CustomerDAOImpl();
		dao.addCustomer(customer);
	}

	@Override
	public void customerLogin(String username, String password) throws NoRecordException {
	
		CustomerDAO dao = new CustomerDAOImpl();
		 dao.customerLogin(username,password);
		
		
	}

}
