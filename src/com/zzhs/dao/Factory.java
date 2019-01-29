package com.zzhs.dao;

import com.zzhs.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Factory {

	// create session factory
	private static SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(User.class)
								.addAnnotatedClass(Customer.class)
								.addAnnotatedClass(EmployeeMR.class)
								.buildSessionFactory();
		
	private Factory() {}
	
	public static SessionFactory getFactory() {
		return factory;
	}
}
