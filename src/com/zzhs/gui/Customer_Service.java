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

public class Customer_Service extends JFrame implements ActionListener{

	int customerID;
	JButton viewServiceHistory;
	JButton scheduleService;
	JButton rescheduleService;
	JButton goBack;
	
	
	public Customer_Service(int customerID)
	{
		this.init();
		this.setVisible(true);
		this.customerID = customerID;
	}
	void init()
	{
		this.setTitle("Customer Service");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
		this.viewServiceHistory = new JButton("View Service History");
		this.scheduleService = new JButton("Schedule Service");
		this.rescheduleService = new JButton("Reschedule Service");
		this.goBack = new JButton("Go Back");
		

		this.add(viewServiceHistory);
		this.add(scheduleService);
		this.add(rescheduleService);
		this.add(goBack);
		
		
		this.viewServiceHistory.addActionListener(this);
		this.scheduleService.addActionListener(this);
		this.rescheduleService.addActionListener(this);
		this.goBack.addActionListener(this);
	
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.viewServiceHistory)
		{
			new Customer_View_Service_History(customerID);
		}
		else if(e.getSource() == this.scheduleService)
		{
			new Customer_Schedule_Service(customerID);
		}
		else if(e.getSource() == this.rescheduleService)
		{
			new Customer_Reschedule_Service_Page1(customerID);
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
//		new  Customer_Service(args[0]);
		
	}

	

}
