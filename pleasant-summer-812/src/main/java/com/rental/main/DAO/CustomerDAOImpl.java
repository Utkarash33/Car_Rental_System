package com.rental.main.DAO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.rental.main.Util.DbUtils;
import com.rental.main.entities.Car;
import com.rental.main.entities.Customer;
import com.rental.main.entities.Reservation;
import com.rental.main.entities.Transaction;
import com.rental.main.exceptions.DuplicateRecordException;
import com.rental.main.exceptions.NoRecordException;
import com.rental.main.exceptions.SomeThingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public  class CustomerDAOImpl implements CustomerDAO{

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
				throw new DuplicateRecordException("Cannot use the particluar username.");
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
              if(count.isDeleted())
              {
            	  throw new NoRecordException("Cannot use the particluar username.");
              }
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

	@Override
	public void makeReservation(String username, String resId, LocalDateTime startDateTime, LocalDateTime endDateTime,Long hours) throws SomeThingWentWrongException {
		updateCarAvailability();
		EntityManager entityManager =  DbUtils.getManger();

	        try {
	            
	            Car car = entityManager.find(Car.class, resId);
              
	            Double amountToPay = car.getRent()*hours;
	            
	            Customer customer = entityManager.createQuery("SELECT c FROM Customer c WHERE c.username = :username", Customer.class)
	                .setParameter("username", username)
	                .getSingleResult();

	           
	            Reservation reservation = new Reservation(car, customer, startDateTime, endDateTime);
	           
	            entityManager.getTransaction().begin();
	           
	           
	            Scanner sc = new Scanner(System.in);
	            System.out.println("Total amount to pay:-> "+amountToPay);
	            System.out.println("confirm Payment[yes/no]");
	            String con = sc.nextLine().toLowerCase();
	            Transaction t = new Transaction(reservation, amountToPay);
	            if(con.equals("yes"))
	            {
	            	 entityManager.persist(reservation);
	            	 entityManager.persist(t);
	            	 car.setAvailability(false);
	            entityManager.getTransaction().commit();
	            }else
	            {
	            	throw new SomeThingWentWrongException("Unable to make the reservation");
	            }
	            System.out.println("Reservation successfully created!");
	        } catch (NoResultException e) {
	        	entityManager.getTransaction().rollback();
	            System.out.println("Car with registration number " + resId + " not found!");
	        } finally {
	            
	            entityManager.close();

	        }
	}

	
	
	@Override
	public  void updateCarAvailability() {
	   
	    EntityManager em = DbUtils.getManger();

	    try {
	        em.getTransaction().begin();

	        
	        LocalDateTime currentDateTime = LocalDateTime.now();
	        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.rentalPeriodEnd <= :currentDateTime");
	        query.setParameter("currentDateTime", currentDateTime);
	        List<Reservation> expiredReservations = query.getResultList();

	        // Set car availability to true for each expired reservation
	        for (Reservation reservation : expiredReservations) {
	            Car car = reservation.getCar();
	            car.setAvailability(true);
	            em.merge(car);
	        }

	        em.getTransaction().commit();
	    } catch (Exception e) {
	        System.out.println("An error occurred while updating car availability: " + e.getMessage());
	    } finally {
	        em.close();
	    }
	}

	@Override
	public List<Reservation> viewAllReservations(String username) throws SomeThingWentWrongException {
	    EntityManager em = null;

	    try {
	        em = DbUtils.getManger();

	        Query query = em.createQuery("SELECT r FROM Reservation r LEFT JOIN FETCH r.transactions WHERE r.customer.username = :username");
	        query.setParameter("username", username);

	        List<Reservation> reservations = query.getResultList();

	        if (reservations.isEmpty()) {
	            throw new NoRecordException("There are no reservation details present for the user " + username);
	        }

	        return reservations;
	    } catch (Exception e) {
	        throw new SomeThingWentWrongException("Unable to find any reservation details...");
	    } finally {
	        if (em != null) {
	            em.close();
	        }
	    }
	}

	    @Override
	    public void cancelReservation(String username, Long id, String pass) throws SomeThingWentWrongException {
	        EntityManager entityManager = DbUtils.getManger();

	        try {
	            entityManager.getTransaction().begin();

	            
	            Reservation reservation = entityManager.find(Reservation.class, id);

	            if (reservation != null && reservation.getRentalPeriodStart().isAfter(LocalDateTime.now())) {
	                Customer customer = reservation.getCustomer();

	               
	                if (customer != null && customer.getUsername().equals(username) && customer.getPassword().equals(pass)) {
	                    Transaction transaction = getTransactionByReservationId(id);
	                    Double amount = transaction.getAmount();
	                    entityManager.remove(reservation);
	                    if (transaction != null) {
	                        entityManager.remove(transaction);
	                    }

	                    entityManager.getTransaction().commit();
	                    System.out.println("Reservation with ID " + id + " has been canceled.");
	                    System.out.println("Amount Rs."+amount+" will we refunded to you account in 24 hours.");
	                } else {
	                    throw new SomeThingWentWrongException("Invalid username or password.");
	                }
	            } else {
	                throw new SomeThingWentWrongException("Unable to cancel the reservation. Please check the reservation ID or reservation time.");
	            }
	        } catch (SomeThingWentWrongException e) {
	            entityManager.getTransaction().rollback();
	            System.out.println(e.getMessage());
	        } finally {
	            entityManager.close();
	        }
	    }

	    public Transaction getTransactionByReservationId(Long reservationId) {
	        EntityManager entityManager = DbUtils.getManger();

	        try {
	            entityManager.getTransaction().begin();
	            Query query = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.reservation.id = :reservationId", Transaction.class);
	            query.setParameter("reservationId", reservationId);
	            Transaction transaction = (Transaction) query.getSingleResult();
	            entityManager.getTransaction().commit();
	            return transaction;
	        } catch (Exception e) {
	            entityManager.getTransaction().rollback();
	            return null; 
	        } finally {
	            entityManager.close();
	        }
	    }

	

}
