package com.rental.main;

import java.util.Scanner;

import com.rental.main.Util.DbUtils;

import jakarta.persistence.EntityManager;

public class Main 
{
    public static void main( String[] args )
    {
     
    	System.out.println("Wlecome to the FASTKAR");
    	System.out.println();
    	System.out.println("==========================");
    	
    	System.out.println("Choice the option: ");
    	System.out.println("1. Login as Admin");
    	System.out.println("2. Resister as Customer");
    	System.out.println("2. Login as Customer");
    	System.out.println("0.Exit the system");
    	
    	Scanner sc = new Scanner(System.in);
    	
    	int choice = Integer.parseInt(sc.nextLine());
         
    	
    	do
    	{
    		switch (choice) {
			case 1: {
				adminMenu(sc);
				break;
			}
			default:
				System.out.println("Invalid choice, Please try again...");
				break;
			}
    		
    	}while(choice!=0);
    }

    
    static void displayAdminMenu()
    {
    	System.out.println("1. Add New Car record to the system...");
	    System.out.println("2. Update the details of the car");
	    System.out.println("3. Delete car for the system");
	    System.out.println("4. Generate report for a car");
	    System.out.println("5. View all Car Details");
	    System.out.println("6. View all customers");
	    System.out.println("0. Log out.");
    }
      
	private static void adminMenu(Scanner sc) {
		
		System.out.println("=============");
		System.out.println("Welcome Admin");
		System.out.println("Please Make the choice: ");
		
	    System.out.println();
	    int choice=0;
	    do
        {
	      displayAdminMenu();
		
          choice  = Integer.parseInt(sc.nextLine());
         
         
        	 switch(choice)
        	 {
        	 case 1:
        	 {
      		 AdminUI.addNewCarRecord(sc);
        		 break;
        	 }
        	 case 2 :
        	 {
        		 AdminUI.updateCarDetails(sc);
        		 break;
        	 }
        	 case 3:
        	 {
        		  AdminUI.deleteCarFromSystem(sc);
        		 break;
        	 }
        	 case 4:
        	 {
        		  AdminUI.generateReportForCar(sc);
        		 break;
        	 }
        	 case 5 :
        	 {
        		  AdminUI.viewAllCarDetails();
        		 break;
        		 
        	 }
        	 case 6:
        	 {
        		  AdminUI.viewAllCustomers();
        		 break;
        	 }
        	 case 0:
        	 {
        		 System.out.println("Logout returning to the main menu.");
        		 break;
        	 }
        	 
        	 }
        	 
         }while(choice!=0);
	}
}
