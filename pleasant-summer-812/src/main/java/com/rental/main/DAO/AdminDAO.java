package com.rental.main.DAO;

import java.util.List;

import com.rental.main.entities.Car;
import com.rental.main.entities.Customer;
import com.rental.main.entities.Reservation;
import com.rental.main.exceptions.NoRecordException;
import com.rental.main.exceptions.RecordDeletedException;
import com.rental.main.exceptions.SomeThingWentWrongException;

public interface AdminDAO {

	List<Customer> getCustomerList() throws SomeThingWentWrongException, NoRecordException;
	
	List<Car> getCarList() throws SomeThingWentWrongException, NoRecordException; 
	
	List<Reservation> getReservationList();
	
	void addCar(Car car) throws SomeThingWentWrongException;
	
	void addBack(String carId) throws SomeThingWentWrongException, NoRecordException;
	
	void updateCar(String Id , Car car) throws NoRecordException, SomeThingWentWrongException, RecordDeletedException;
	
	void  deleteCar(String carId) throws SomeThingWentWrongException, NoRecordException;
	
	String generateReport(String carId);
		
	
	
	
	
	
	
}
