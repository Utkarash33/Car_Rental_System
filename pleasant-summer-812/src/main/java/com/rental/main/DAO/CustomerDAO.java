package com.rental.main.DAO;

import java.time.LocalDateTime;
import java.util.List;

import com.rental.main.entities.Customer;
import com.rental.main.entities.Reservation;
import com.rental.main.entities.Transaction;
import com.rental.main.exceptions.NoRecordException;
import com.rental.main.exceptions.SomeThingWentWrongException;

public interface CustomerDAO {

	void addCustomer(Customer customer);

	void customerLogin(String username, String password) throws NoRecordException;
	void makeReservation(String username, String resId, LocalDateTime startDateTime, LocalDateTime endDateTime,
			Long hours) throws SomeThingWentWrongException;
	
	
	 void updateCarAvailability();

	List<Reservation> viewAllReservations(String username) throws SomeThingWentWrongException;

	void cancelReservation(String username, Long id, String pass) throws SomeThingWentWrongException;

	Transaction getTransactionByReservationId(Long i); 


}
