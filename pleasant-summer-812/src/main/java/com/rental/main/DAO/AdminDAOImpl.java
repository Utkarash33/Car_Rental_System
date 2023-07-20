package com.rental.main.DAO;

import java.util.List;

import com.rental.main.Util.DbUtils;
import com.rental.main.entities.Car;
import com.rental.main.entities.Customer;
import com.rental.main.entities.Reservation;
import com.rental.main.exceptions.NoRecordException;
import com.rental.main.exceptions.RecordDeletedException;
import com.rental.main.exceptions.SomeThingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class AdminDAOImpl implements AdminDAO{

	@Override
	public List<Customer> getCustomerList() throws SomeThingWentWrongException ,NoRecordException{
		List<Customer> customerList = null;
		
		
		EntityManager em = null;
		
		try {
			
			em = DbUtils.getManger();
			Query query =em.createQuery("SELECT c FROM Customer c");
			customerList = query.getResultList();
			if(customerList.isEmpty())
			{
				throw new NoRecordException("There is no customer detail present in the system.");
			}
		} catch (Exception e) {
			
			throw new SomeThingWentWrongException("Unable to find any customer detail...");
		}
		finally
		{
			em.close();
		}
		return customerList;
	}

	@Override
	public List<Car> getCarList() throws SomeThingWentWrongException ,NoRecordException{
     List<Car> customerList = null;
		
		
		EntityManager em = null;
		
		try {
			
			em = DbUtils.getManger();
			
			Query query =em.createQuery("SELECT c FROM Car c");
			customerList = query.getResultList();
			
			if(customerList.isEmpty())
			{
				throw new NoRecordException("There is no cars detail present in the system.");
			}
			
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			  throw new SomeThingWentWrongException("Unable to add back the car try again...");
		}
		return customerList;
	}

	@Override
	public List<Reservation> getReservationList() {
		return null;
	}

	@Override
	public void addCar(Car car) throws SomeThingWentWrongException {
		EntityManager em = null;
		
		try {
			em = DbUtils.getManger();
			
			Car car1 = em.find(Car.class, car.getId());
			
			if(car1!=null)
			{
				throw new SomeThingWentWrongException("The Resistration number " + car.getId() + " is already occupied");
				
			}
			em.getTransaction().begin();
			em.persist(car);
			em.getTransaction().commit();
			System.out.println("Car Details has been added.");
		} catch (PersistenceException e) {
			throw new SomeThingWentWrongException("Unable to add car");
		}finally
		{
			if(em!=null)
			em.close();
		}
	}
	
	@Override
	public void addBack(String carId) throws SomeThingWentWrongException, NoRecordException {
		
        EntityManager em = null;
		
		try {
			em= DbUtils.getManger();
			
			Car car = em.find(Car.class, carId);
			if(car==null)
			{
				throw new NoRecordException("The Resistration number " + car.getId() + " is not Present");
			}
			
			em.getTransaction().begin();
			car.setDeleted(false);
			em.getTransaction().commit();
			System.out.println("The Car details has been deleted successfully...");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new SomeThingWentWrongException("Unable to Delete car");
		}
		finally
		{
			em.close();
		}
	}

	@Override
	public void updateCar(String Id, Car car) throws NoRecordException, SomeThingWentWrongException, RecordDeletedException {
		
         EntityManager em = null;
		
		try {
			em = DbUtils.getManger();
			
			Car car1 = em.find(Car.class, Id);
			
			if(car1==null)
			{
				throw new NoRecordException("The Resistration number " + car.getId() + " is not Present");	
			}
			if(car1.isDeleted())
			{
				System.out.println("Added back the record and then update");
				throw new RecordDeletedException("As the Resistration number "+ Id +" is already deleted");
			}
			em.getTransaction().begin();
			em.remove(car1);
			em.persist(car);
			em.getTransaction().commit();
			System.out.println("Car Details has been Updated.");
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new SomeThingWentWrongException("Unable to Update car");
		}finally
		{
			if(em!=null)
			em.close();
		}
		
		
	}

	@Override
	public void deleteCar(String carId) throws SomeThingWentWrongException, NoRecordException {
		
		EntityManager em = null;
		
		try {
			em= DbUtils.getManger();
			
			Car car = em.find(Car.class, carId);
			if(car==null)
			{
				throw new NoRecordException("The Resistration number " + car.getId() + " is not Present");
			}
			
			em.getTransaction().begin();
		    car.setDeleted(true);
			em.getTransaction().commit();
			System.out.println("The Car details has been deleted successfully...");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new SomeThingWentWrongException("Unable to Delete car");
		}
		finally
		{
			em.close();
		}
		
		
	}

	@Override
	public String generateReport(String carId) {
		// TODO Auto-generated method stub
		return null;
	}
     
	
	
	
	
}
