package com.rental.main;

import java.util.List;
import java.util.Scanner;


import com.rental.main.entities.Car;
import com.rental.main.entities.Customer;
import com.rental.main.exceptions.NoRecordException;
import com.rental.main.exceptions.RecordDeletedException;
import com.rental.main.exceptions.SomeThingWentWrongException;
import com.rental.main.services.AdminServices;
import com.rental.main.services.AdminServicesImpl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class AdminUI {

	public static void addNewCarRecord(Scanner sc) {
	
		System.out.println("Enter the detials:");
		System.out.println();
		System.out.println("Enter the Resistration Number for Car");
		String number = sc.nextLine();
		System.out.println("Enter the Brand of the Car");
		String brand = sc.nextLine();
	    System.out.println("Enter the Model of the Car");
	    String model = sc.nextLine();
	    System.out.println("Enter the Model year of the Car");
	    Integer year = Integer.parseInt(sc.nextLine());
	    System.out.println("Enter the Mileage of the Car");
	    Double mileage = Double.parseDouble(sc.nextLine());
	    System.out.println("Enter the Rent per Hour:");
	    Double rent = Double.parseDouble(sc.nextLine());
	    System.out.println("Resistered city name");
	    String city = sc.nextLine();
	    
	    Car car = new Car(number, brand, model, year, mileage,rent,city, true, false);
	    
	    AdminServices adminServices = new AdminServicesImpl();
	    
	    try {
			adminServices.addCar(car);
		} catch (SomeThingWentWrongException e) {
			System.out.println(e.getMessage());
		}
	    return;
	}

	public static void updateCarDetails(Scanner sc) {
		
		System.out.println("Enter the Resistration Number of the Car to Update");
		String number = sc.nextLine();
		System.out.println("Enter the updated details for the car");
		System.out.println("Enter the Brand of the Car");
		String brand = sc.nextLine();
	    System.out.println("Enter the Model of the Car");
	    String model = sc.nextLine();
	    System.out.println("Enter the Model year of the Car");
	    Integer year = Integer.parseInt(sc.nextLine());
	    System.out.println("Enter the Mileage of the Car");
	   
	    Double mileage = Double.parseDouble(sc.nextLine());
	    System.out.println("Enter the Rent per Hour:");
	    Double rent = Double.parseDouble(sc.nextLine());
	    System.out.println("Enter the Resisteration city ");
	    String city= sc.nextLine();
        System.out.println("Is Avaliable [y/n] ");
	    
	    String av = sc.nextLine().toLowerCase();
	    boolean avFlag = false;
	    if(av.equals("y"))
	    {
	    	avFlag = true;
	    	
	    }
	    Car car = new Car(number, brand, model, year, mileage,rent,city, avFlag, false);
	   
	    AdminServices adminServices = new AdminServicesImpl();
	    
	    try {
			adminServices.updateCar(number,car);
			
		} catch (NoRecordException | SomeThingWentWrongException | RecordDeletedException e) {
			System.out.println(e.getMessage());
		}
		
		
	}

	public static void deleteCarFromSystem(Scanner sc) {
		
		
		System.out.println("Enter the Resistration Number of the Car to Delete");
		String number = sc.nextLine();
        AdminServices adminServices = new AdminServicesImpl();
	    
	    try {
			adminServices.deleteCar(number);
		} catch (SomeThingWentWrongException | NoRecordException e) {
			System.out.println(e.getMessage());
		}
		
	}

	public static void addBackTheRecord(Scanner sc) {
		
		System.out.println("Enter the Resistration Number of the Car to Add Back");
		String number = sc.nextLine();
        AdminServices adminServices = new AdminServicesImpl();
	    
	    try {
			adminServices.addBack(number);
		} catch (SomeThingWentWrongException | NoRecordException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	public static void generateReportForCar(Scanner sc) {
		System.out.println("Enter the Resistration Number of the Car to Generate Report");
		String number = sc.nextLine();
        AdminServices adminServices = new AdminServicesImpl();
	    
	    adminServices.generateReport(number);
	}

	public static void viewAllCarDetails() {
		
		AdminServices adminServices = new AdminServicesImpl();
		try {
			List<Car> list =adminServices.getCarList();
			list = list.stream().filter(c->c.isDeleted()==false).toList();
			if(list==null)
			{
				System.out.println("There is no car detail available.");
				return;
			}
			list.forEach(c->
			{
				System.out.println("Resistration number:->"+ c.getId());
				System.out.println("Brand:-> "+c.getBrand());
				System.out.println("Model:-> "+c.getModel());
				System.out.println("Mileage:-> "+c.getMileage());
				System.out.println("Rent per hour:-> "+ c.getRent());
				System.out.println("Resistration city:-> "+c.getCity());
				System.out.print("Availability status:-> ");
				System.out.println(c.isAvailability()?"Available":"Not Available");
				System.out.println("===============================================");
			});
		} catch (SomeThingWentWrongException | NoRecordException e) {
			System.out.println(e.getMessage());
		}
	}
	public static void viewAllRemovedCarList()
	{
		AdminServices adminServices = new AdminServicesImpl();
		try {
			List<Car> list =adminServices.getCarList();
			
			list = list.stream().filter(c->c.isDeleted()==true).toList();
			if(list==null)
			{
				System.out.println("There is no car detail available.");
				return;
			}
			list.forEach(c->
			{
				System.out.println("Resistration number :"+ c.getId());
				System.out.println("Brand:-> "+c.getBrand());
				System.out.println("Model:-> "+c.getModel());
				System.out.println("Mileage:-> "+c.getMileage());
				System.out.println("Rent per hour:-> "+ c.getRent());
				System.out.println("Resistration city:-> "+c.getCity());
				System.out.print("Availability status:-> ");
				System.out.println(c.isAvailability()?"Available":"Not Available");
				System.out.println("===============================================");
			});
		} catch (SomeThingWentWrongException | NoRecordException e) {
			System.out.println(e.getMessage());
		}
	}
	public static void viewAllCustomers() {
		AdminServices adminServices = new AdminServicesImpl();
		try {
			
			
			List<Customer> list =adminServices.getCustomerList();

			list = list.stream().filter(c->c.isDeleted()==false).toList();
			if(list==null)
			{
				System.out.println("There is no car detail available.");
				return;
			}
			list.forEach(c->
			{
				System.out.println("Name:-> "+c.getName());
				System.out.println("Email:-> "+c.getUsername());
				System.out.println("Address:-> "+c.getAddress());
				System.out.println("Contact number:-> "+ c.getContactDetails());
				System.out.println("===============================================");
			});			
		} catch (SomeThingWentWrongException | NoRecordException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void RemoveCustomer(Scanner sc) {
		System.out.println("Enter the username of the customer");
		String username = sc.nextLine();
        AdminServices adminServices = new AdminServicesImpl();
	    
	    try {
			adminServices.deleteCustomer(username);
		} catch (SomeThingWentWrongException | NoRecordException e) {
			System.out.println(e.getMessage());
		}
	}


}
