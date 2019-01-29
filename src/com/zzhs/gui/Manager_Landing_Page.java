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

public class Manager_Landing_Page extends JFrame implements ActionListener{

	int employeeID;
	
	JButton profileButton;
	JButton viewCustomerProfileButton;
	JButton addNewEmployeesButton;
	JButton payrollButton;
	
	JButton inventoryButton;
	
	JButton ordersButton;
	JButton notificationsButton;
	JButton newCarModelButton;
	JButton carServiceDetailsButton;
	
	JButton serviceHistoryButton;
	JButton invoicesButton;
	
	JButton logoutButton;
	
	
	public Manager_Landing_Page(int employeeID)
	{
		this.employeeID = employeeID;
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
	void init()
	{
		this.setTitle("EID: " + employeeID + "   Manager landing page");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 500);
	
		this.profileButton = new JButton("Profile");
		this.viewCustomerProfileButton = new JButton("View Customer Profile");
		this.addNewEmployeesButton = new JButton("Add new Employees");
		this.inventoryButton = new JButton("Inventory");
		this.ordersButton = new JButton("Orders");
		this.invoicesButton = new JButton("Invoices");
		this.notificationsButton = new JButton("Notifications");
		this.newCarModelButton = new JButton("New Car Model");
		this.payrollButton = new JButton("Payroll");
		this.carServiceDetailsButton = new JButton("Car Service Details");
		this.serviceHistoryButton = new JButton("Servie Hisotry");
		this.logoutButton = new JButton("Log out");
		
		
		this.add(profileButton);
		this.add(viewCustomerProfileButton);
		this.add(addNewEmployeesButton);
		this.add(payrollButton);
		this.add(inventoryButton);
		this.add(ordersButton);
		this.add(invoicesButton);
		this.add(notificationsButton);
		this.add(newCarModelButton);
		this.add(carServiceDetailsButton);
		this.add(serviceHistoryButton);
		this.add(logoutButton);
		
		this.profileButton.addActionListener(this);
		this.viewCustomerProfileButton.addActionListener(this);
		this.inventoryButton.addActionListener(this);
		this.ordersButton.addActionListener(this);
		this.invoicesButton.addActionListener(this);
		this.notificationsButton.addActionListener(this);
		this.addNewEmployeesButton.addActionListener(this);
		this.payrollButton.addActionListener(this);
		this.newCarModelButton.addActionListener(this);
		this.carServiceDetailsButton.addActionListener(this);
		this.serviceHistoryButton.addActionListener(this);
		this.logoutButton.addActionListener(this);
	
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
	
		if(e.getSource() == this.profileButton)
		{
			new Employee_Profile_Page(employeeID, true);
		}
		else if(e.getSource() == this.viewCustomerProfileButton)
		{
			
			new Employee_View_Customer_Profile_Page(employeeID, true);
		}
		else if(e.getSource() == this.addNewEmployeesButton)
		{
			new Manager_Add_New_Employees_Page(employeeID);
		}
		else if(e.getSource() == this.inventoryButton)
		{
			new Manager_Inventory_Page(employeeID);
		}
		else if (e.getSource() == this.payrollButton)
		{
			new Manager_Payroll_Page(employeeID);
		}
		else if(e.getSource() == this.ordersButton)
		{
			new Manager_Orders_Page(employeeID);
		}
		else if(e.getSource() == this.invoicesButton)
		{
			new Manager_Invoices_Page(employeeID);
		}
		else if(e.getSource() == this.notificationsButton)
		{
			new Manager_Notifications_Page(employeeID);
		}
		
		else if(e.getSource() == this.newCarModelButton)
		{
			new Manager_New_Car_Model_Page(employeeID);
		}
		
		else if(e.getSource() == this.carServiceDetailsButton)
		{
			new Manager_Car_Service_Details_Page(employeeID);
		}
		else if(e.getSource() == this.serviceHistoryButton)
		{
			new Manager_Service_History_Page(employeeID);
		}
		else if(e.getSource() == this.logoutButton)
		{
			new Home();
		}
		
		this.dispose();
		
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Manager_Landing_Page(950932130);
		
	}

}
