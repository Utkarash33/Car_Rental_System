package com.rental.main;

import java.util.Scanner;

import com.rental.main.entities.Car;
import com.rental.main.exceptions.NoRecordException;
import com.rental.main.exceptions.SomeThingWentWrongException;
import com.rental.main.services.AdminServices;
import com.rental.main.services.AdminServicesImpl;

import jakarta.persistence.Column;

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
	    
	    Car car = new Car(number, brand, model, year, mileage,rent, true, false);
	    
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
        System.out.println("Is Avaliable [y/n] ");
	    
	    String av = sc.nextLine().toLowerCase();
	    boolean avFlag = false;
	    if(av.equals("y"))
	    {
	    	avFlag = true;
	    	
	    }
	    Car car = new Car(number, brand, model, year, mileage,rent, avFlag, false);
	   
	    AdminServices adminServices = new AdminServicesImpl();
	    
	    try {
			adminServices.updateCar(number,car);
			
		} catch (NoRecordException | SomeThingWentWrongException e) {
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

	public static void generateReportForCar(Scanner sc) {
		System.out.println("Enter the Resistration Number of the Car to Generate Report");
		String number = sc.nextLine();
        AdminServices adminServices = new AdminServicesImpl();
	    
	    adminServices.generateReport(number);
	}

	public static void viewAllCarDetails() {
		
		AdminServices adminServices = new AdminServicesImpl();
		adminServices.getCarList();
	}

	public static void viewAllCustomers() {
		AdminServices adminServices = new AdminServicesImpl();
		adminServices.getCustomerList();
	}

}
