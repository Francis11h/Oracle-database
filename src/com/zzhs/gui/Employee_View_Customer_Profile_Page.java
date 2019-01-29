package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.zzhs.dao.CustomerDao;
import com.zzhs.entity.Customer;

public class Employee_View_Customer_Profile_Page extends JFrame implements ActionListener{

	int employeeID;
	int customerID;
	boolean isManager;
	boolean employeeMR;
	
	String email;
	JButton goBackButton;
	JButton checkButton;
	
	
	
	;
	JTextField emailInputFild;
	JTextField emailFild;
	JTextField namefild;
	JTextField addressFild;
	JTextField phoneNumberFild;
	JTextField customerIDFild;
	JList<String> listOfCarsFild;
	
	JLabel lblCustomerID;
	JLabel lblInputEmail;
	JLabel lblEmail;
	JLabel lblName;
	JLabel lblAddress;
	JLabel lblPhoneNumber;
	JLabel lblListOfCars;
	

	public Employee_View_Customer_Profile_Page(int employeeID, boolean isManager)
	{
		this.employeeID = employeeID;
		this.isManager = isManager;
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	void init()
	{
		//TODO
		//search customer's upcoming services (customerID) 
		//return licence plate, erviceID,Service date, service type(maintenance/repair), Service Details(A/B/C or problem))
		//this.licensePlate = licence plate....
		this.setTitle("Employee View Customer Profile");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 400);
		
		
		
		this.emailInputFild = new JTextField();
		this.lblInputEmail = new JLabel("Input Customer email address to check");
		
		this.customerIDFild = new JTextField();
		this.emailFild = new JTextField();
		this.namefild = new JTextField();
		this.addressFild = new JTextField();
		this.phoneNumberFild = new JTextField();
		this.addressFild = new JTextField();
		this.listOfCarsFild = new JList<>();
		
		
		this.lblCustomerID = new JLabel("Cusomter ID");
		this.lblEmail = new JLabel("Email Adress");
		this.lblName = new JLabel("Name");
		this.lblAddress = new JLabel("Adress");
		this.lblPhoneNumber = new JLabel("Phone Number");
		this.lblListOfCars = new JLabel("List of all cars(and theire details)");
		
		this.goBackButton = new JButton("Go Back");
		this.checkButton = new JButton("Check");
		
		
		
		this.add(lblInputEmail);
		this.add(emailInputFild);
		this.add(checkButton);
		this.add(lblCustomerID);
		this.add(customerIDFild);
		this.add(lblName);
		this.add(namefild);
		this.add(lblAddress);
		this.add(addressFild);
		this.add(lblEmail);
		this.add(emailFild);
		
		
		this.add(lblPhoneNumber);
		this.add(phoneNumberFild);
		this.add(lblListOfCars);
		this.add(listOfCarsFild);

		
		this.add(goBackButton);
		
		
		
		
		
	
		this.checkButton.addActionListener(this);
		this.goBackButton.addActionListener(this);
		

	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		
		if(e.getSource() == this.checkButton)
		{
			
			String email = this.emailInputFild.getText();
			
			CustomerDao customerDao = new CustomerDao();
			this.customerID = customerDao.getCustomerID(email);
			if (customerID == 0) {
				new Popdialog("customer with this email does not exist.");
			} else {
			
				List<String> allCars = customerDao.getCars(customerID);
			
				this.emailFild.setText(email);
				Customer customer = customerDao.getCustomer(customerID);
				if (customer!=null) {
					this.namefild.setText(customer.getName());
					this.addressFild.setText(customer.getAddress());
					this.phoneNumberFild.setText(customer.getPhone());
				}
				
				this.customerIDFild.setText(String.valueOf(customerID));
				this.listOfCarsFild.setListData(new Vector<String>(allCars));
			}
			
		}
		else if(e.getSource() == this.goBackButton)
		{
			if(isManager) {
				new Manager_Landing_Page(employeeID);
			}else {
				new Receptionist_Landing_Page(employeeID);
			}
			this.dispose();
		}
		
	}
	
	
	public static void main(String[] args) {
		new Employee_View_Customer_Profile_Page(950932130, true);
		
		
	}
}
