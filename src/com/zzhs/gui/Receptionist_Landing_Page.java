package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Receptionist_Landing_Page extends JFrame implements ActionListener{

	int employeeID;
	
	JButton profileButton;
	JButton viewCustomerProfileButton;
	JButton registerCarButton;
	JButton serviceHistoryButton;
	
	JButton scheduleServiceButton;
	JButton rescheduleServiceButton;
	JButton invoicesButton;
	JButton dailyTaskUpdateInventoryButton;
	JButton dailyTaskRecordDeliveriesButton;
	JButton logoutButton;
	
	
	public Receptionist_Landing_Page(int employeeID)
	{
		this.employeeID = employeeID;
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
	void init()
	{
		this.setTitle("EID: " + employeeID + " Receptionist landing page");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 500);
	
		this.profileButton = new JButton("Profile");
		this.viewCustomerProfileButton = new JButton("View Customer Profile");
		this.registerCarButton = new JButton("Register Car");
		this.scheduleServiceButton = new JButton("Schedule Service");
		this.rescheduleServiceButton = new JButton("Reshesule service");
		this.invoicesButton = new JButton("Invoices");
		this.dailyTaskUpdateInventoryButton = new JButton(" Daily Task-Update Inventory");
		this.dailyTaskRecordDeliveriesButton = new JButton("Dailt Task-Record Deliveries");
		this.serviceHistoryButton = new JButton("Service History");
		this.logoutButton = new JButton("Log out");
		
		
		this.add(profileButton);
		this.add(viewCustomerProfileButton);
		this.add(registerCarButton);
		this.add(serviceHistoryButton);
		this.add(scheduleServiceButton);
		this.add(rescheduleServiceButton);
		this.add(invoicesButton);
		this.add(dailyTaskUpdateInventoryButton);
		this.add(dailyTaskRecordDeliveriesButton);
		this.add(logoutButton);
		
		this.profileButton.addActionListener(this);
		this.viewCustomerProfileButton.addActionListener(this);
		this.scheduleServiceButton.addActionListener(this);
		this.rescheduleServiceButton.addActionListener(this);
		this.invoicesButton.addActionListener(this);
		this.dailyTaskUpdateInventoryButton.addActionListener(this);
		this.registerCarButton.addActionListener(this);
		this.serviceHistoryButton.addActionListener(this);
		this.dailyTaskRecordDeliveriesButton.addActionListener(this);
		this.logoutButton.addActionListener(this);
	
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
	
		if(e.getSource() == this.profileButton)
		{
			new Employee_Profile_Page(employeeID, false);
			//TODO getEmployeeProfile(employeeID)
			//return (ID, name,address,, email,phone,service center, role, start date, compensation$,compensation frequency(monthly/hourly))
//			new Employee_Profile_Page(employeeID,name,address,email,phoneNumber,center,role,startDate,compensation,compensationFreq);
		}
		else if(e.getSource() == this.viewCustomerProfileButton)
		{
			new Employee_View_Customer_Profile_Page(employeeID, false);
		}
		else if(e.getSource() == this.registerCarButton)
		{
			new Receptionist_Register_Car_Page(employeeID);
		}
		else if(e.getSource() == this.scheduleServiceButton)
		{
			new Receptionist_Schedule_Service_Page(employeeID);
		}
		else if (e.getSource() == this.serviceHistoryButton)
		{
			new Receptionist_Service_History_Page(employeeID);
		}
		else if(e.getSource() == this.rescheduleServiceButton)
		{
			new Receptionist_Reschedule_Service_Page1(employeeID);
		}
		else if(e.getSource() == this.invoicesButton)
		{
			new Receptionist_Invoices_Page(employeeID);
		}
		else if(e.getSource() == this.dailyTaskUpdateInventoryButton)
		{
			new Receiptionist_Daily_Task_Update_Inventory_Page(employeeID);
		}
		
		else if(e.getSource() == this.dailyTaskRecordDeliveriesButton)
		{
			new Receptionist_Daily_Task_Record_Deliveries_Page(employeeID);
		}
		else if(e.getSource() == this.logoutButton)
		{
			new Home();
		}
		else
		{			
		}
		this.dispose();
		
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Receptionist_Landing_Page(634622236);
		
	}

}
