package com.rental.main.services;

import java.time.LocalDateTime;
import java.util.List;

import com.rental.main.entities.Car;
import com.rental.main.entities.Customer;
import com.rental.main.entities.Reservation;
import com.rental.main.exceptions.NoRecordException;
import com.rental.main.exceptions.SomeThingWentWrongException;

public interface CustomerServices {

	void addCustomer(Customer customer);

	void customerLogin(String username, String password) throws NoRecordException;

	List<Car> searchByRentalRange(int min, int max) throws SomeThingWentWrongException, NoRecordException;

	List<Car> searchByMileageRange(int min, int max) throws SomeThingWentWrongException, NoRecordException;

	List<Car> searchByCity(String city) throws SomeThingWentWrongException, NoRecordException;

	List<Car> viewAllCars() throws SomeThingWentWrongException, NoRecordException;

	Car getCarById(String resId) throws SomeThingWentWrongException, NoRecordException;

	void makeReservation(String username, String resId, LocalDateTime rentalPeriodStart, LocalDateTime rentalPeriodEnd,
			long hours) throws SomeThingWentWrongException;

	List<Reservation> viewAllReservations(String username) throws SomeThingWentWrongException;

	void cancelReservation(String username, Long id, String pass) throws SomeThingWentWrongException;

}
