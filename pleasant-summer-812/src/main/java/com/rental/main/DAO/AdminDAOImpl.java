package com.rental.main.DAO;

import java.util.List;

import com.rental.main.Util.DbUtils;
import com.rental.main.entities.Car;
import com.rental.main.entities.Customer;
import com.rental.main.entities.Reservation;
import com.rental.main.exceptions.NoRecordException;
import com.rental.main.exceptions.SomeThingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class AdminDAOImpl implements AdminDAO{

	@Override
	public List<Customer> getCustomerList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Car> getCarList() {
		// TODO Auto-generated method stub
		return null;
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
			
			if(car1!=null && !car.isDeleted())
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
	public void updateCar(String Id, Car car) throws NoRecordException, SomeThingWentWrongException {
		
         EntityManager em = null;
		
		try {
			em = DbUtils.getManger();
			
			Query query = em.createQuery("SELECT COUNT(c) FROM Car c WHERE id =:id");
			query.setParameter("id",Id);
			
			if((Long)query.getSingleResult()==0)
			{
				throw new NoRecordException("The Resistration number " + car.getId() + " is not Present");
				
			}
		     Car car1 = em.find(Car.class,Id);
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
