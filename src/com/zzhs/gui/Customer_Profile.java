package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Customer_Profile extends JFrame implements ActionListener {

	int customerID;
	JButton viewProfile;
	JButton updateProfile;
	JButton goBack;
	JButton invoices;
	JButton logout;
	
	public Customer_Profile(int customerID)
	{
		this.init();
		this.setVisible(true);
		this.customerID = customerID;
	}
	void init()
	{
		this.setTitle("Customer Profile");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
		this.viewProfile = new JButton("View Profile");
		this.updateProfile = new JButton("Update Profile");
		this.goBack = new JButton("Go Back");
		
	
		this.add(viewProfile);
		this.add(updateProfile);
		this.add(goBack);
		
		
		this.viewProfile.addActionListener(this);
		this.updateProfile.addActionListener(this);
		this.goBack.addActionListener(this);

		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.viewProfile)
		{
			new Customer_View_Profile(customerID);
		}
		else if(e.getSource() == this.updateProfile)
		{
			new Customer_Update_Profile(customerID);
		}
		
		else if(e.getSource() == this.goBack)
		{
			new Customer_Landing(customerID);
		}
		this.dispose();
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Customer_Profile(47);
		 
	}

}
