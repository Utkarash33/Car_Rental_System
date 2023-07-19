package com.rental.main.Util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DbUtils {

	 private static EntityManagerFactory emf;
     
     EntityManager em = emf.createEntityManager();
     
       static
       {
    	   emf = Persistence.createEntityManagerFactory("rentConnect");
       }
	  
       public static EntityManager getManger()
       {
    	   return emf.createEntityManager();
       }
	
}
