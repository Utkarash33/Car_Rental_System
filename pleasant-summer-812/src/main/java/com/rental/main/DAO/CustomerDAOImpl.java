package com.rental.main.DAO;

import java.util.List;

import com.rental.main.Util.DbUtils;
import com.rental.main.entities.Customer;
import com.rental.main.exceptions.DuplicateRecordException;
import com.rental.main.exceptions.NoRecordException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class CustomerDAOImpl implements CustomerDAO{

	@Override
	public void addCustomer(Customer customer) {
		
		
		EntityManager em = null;
		
		try
		{
			em = DbUtils.getManger();
			
			em.getTransaction().begin();
			
               Query query = em.createQuery("SELECT count(c) FROM Customer c WHERE c.username =: username");
               query.setParameter("username", customer.getUsername());
               
              Long count =(Long) query.getSingleResult();

			if( count!=null && count>0)
			{
				throw new DuplicateRecordException("The user is already present");
			}
			em.persist(customer);
			em.getTransaction().commit();
			System.out.println("Customer Added Successfully.");
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			em.close();
		}
		
		
	}

	@Override
	public void customerLogin(String username, String password) throws NoRecordException {
		

		EntityManager em = null;
		
		try
		{
			em = DbUtils.getManger();
			
			
			
               Query query = em.createQuery("SELECT c FROM Customer c WHERE c.username =: username");
               query.setParameter("username", username);
               
              Customer count =(Customer) query.getSingleResult();
			if(!count.getPassword().equals(password))
			{
				throw new NoRecordException("Invalid Password please try again.");
			}
			
			System.out.println("Welcome "+count.getName()+" to the system.");
			
			
		}catch(Exception e)
		{
         throw new NoRecordException("Invalid username or password please try again");
		}
		finally
		{
			em.close();
		}
		
	}

}
