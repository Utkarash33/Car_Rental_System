package com.rental.main.services;

import java.util.List;

import com.rental.main.entities.Car;
import com.rental.main.entities.Customer;
import com.rental.main.entities.Reservation;
import com.rental.main.exceptions.NoRecordException;
import com.rental.main.exceptions.SomeThingWentWrongException;

public interface AdminServices {

	
	
List<Customer> getCustomerList();
	
	List<Car> getCarList(); 
	
	List<Reservation> getReservationList();
	
	void addCar(Car car) throws SomeThingWentWrongException;
	
	void addBack(String carId) throws SomeThingWentWrongException, NoRecordException;
	
	void updateCar(String Id , Car car) throws NoRecordException, SomeThingWentWrongException;
	
	void  deleteCar(String carId) throws SomeThingWentWrongException, NoRecordException;
	
	String generateReport(String carId);

	
		
}
