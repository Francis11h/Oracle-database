package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Customer_Landing extends JFrame implements ActionListener{
	
	int customerID;
	JButton profile;
	JButton registerCar;
	JButton service;
	JButton invoices;
	JButton logout;
	
	public Customer_Landing(int customerID)
	{
		this.init();
		this.setVisible(true);
		this.customerID = customerID;
	}
	void init()
	{
		this.setTitle("Customer Landing");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
		this.profile = new JButton("profile");
		this.registerCar = new JButton("Register Car");
		this.service = new JButton("Service");
		this.invoices = new JButton("Invoices");
		this.logout = new JButton("Log out");
		
		
		
		
		this.add(profile);
		this.add(registerCar);
		this.add(service);
		this.add(invoices);
		this.add(logout);
		
		this.profile.addActionListener(this);
		this.registerCar.addActionListener(this);
		this.service.addActionListener(this);
		this.invoices.addActionListener(this);
		this.logout.addActionListener(this);
		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.profile)
		{
			new Customer_Profile(customerID);
		}
		else if(e.getSource() == this.registerCar)
		{
			new Customer_Register_Car(customerID);
		}
		else if(e.getSource() == this.service)
		{
			new Customer_Service(customerID);
		}
		else if(e.getSource() == this.invoices)
		{
			new Customer_Invoice(customerID);
		}
		else if(e.getSource() == this.logout)
		{
			new Home();
		}
		this.dispose();
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Customer_Landing(47);
		
	}

	
	

}
