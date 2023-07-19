package com.rental.main.services;

import java.util.List;

import com.rental.main.DAO.AdminDAO;
import com.rental.main.DAO.AdminDAOImpl;
import com.rental.main.entities.Car;
import com.rental.main.entities.Customer;
import com.rental.main.entities.Reservation;
import com.rental.main.exceptions.NoRecordException;
import com.rental.main.exceptions.RecordDeletedException;
import com.rental.main.exceptions.SomeThingWentWrongException;

public class AdminServicesImpl implements AdminServices{

	@Override
	public List<Customer> getCustomerList() throws SomeThingWentWrongException, NoRecordException {
		  AdminDAO dao = new AdminDAOImpl();
		  return  dao.getCustomerList();
		 
	}

	@Override
	public List<Car> getCarList() throws SomeThingWentWrongException, NoRecordException {
		 AdminDAO dao = new AdminDAOImpl();
		  return  dao.getCarList();
			 
	}

	@Override
	public List<Reservation> getReservationList() {
		  AdminDAO dao = new AdminDAOImpl();
		  return  dao.getReservationList();
		 
		
	}

	@Override
	public void addCar(Car car) throws SomeThingWentWrongException {

		  AdminDAO dao = new AdminDAOImpl();
		  dao.addCar(car);
		 
	}

	@Override
	public void updateCar(String Id, Car car) throws NoRecordException, SomeThingWentWrongException, RecordDeletedException {
		 AdminDAO dao = new AdminDAOImpl();
		  dao.updateCar(Id, car);
	}

	@Override
	public void deleteCar(String Id) throws SomeThingWentWrongException, NoRecordException {
		 AdminDAO dao = new AdminDAOImpl();
		  dao.deleteCar(Id);
	}

	@Override
	public String generateReport(String carId) {
		 AdminDAO dao = new AdminDAOImpl();
		 return dao.generateReport(carId);
	}

	@Override
	public void addBack(String carId) throws SomeThingWentWrongException, NoRecordException {
		 AdminDAO dao = new AdminDAOImpl();
		  dao.addBack(carId);
		
		
	}

}
