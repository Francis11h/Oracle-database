package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.zzhs.dao.CustomerDao;
import com.zzhs.dao.UserDao;
import com.zzhs.entity.Customer;
import com.zzhs.entity.User;

public class Customer_Update_Profile extends JFrame implements ActionListener
{
	int customerID;
	JTextField email;
	JLabel lblEmailAddress;
	JButton updateButton;
	JButton gobackButton;
	JTextField password;
	JTextField name;
	JTextField address;
	JTextField phoneNumber;
	JLabel lblPassword;
	JLabel lblName;
	JLabel lblAddress;
	JLabel lblPhoneNumber;
	
	

	public Customer_Update_Profile(int customerID)
	{
		this.customerID = customerID;
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("CustomerID: " + customerID + "    Customer Update Profile");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
		this.email  = new JTextField();
		this.password = new JTextField();
		this.name  = new JTextField();
		this.address  = new JTextField();
		this.phoneNumber  = new JTextField();
		this.lblEmailAddress = new JLabel(" New Email Address");
		this.lblPassword = new JLabel("New Password");
		this.lblName = new JLabel("New Name");
		this.lblAddress = new JLabel("New Address");
		this.lblPhoneNumber = new JLabel("New Phone Number");
		this.updateButton = new JButton("Updatde");
		this.gobackButton = new JButton("Go back");
		
	
		
		this.add(lblEmailAddress);
		this.add(email);
		this.add(lblPassword);
		this.add(password);
		this.add(lblName);
		this.add(name);
		this.add(lblAddress);
		this.add(address);
		this.add(lblPhoneNumber);
		this.add(phoneNumber);
		
		this.add(updateButton);
		this.add(gobackButton);
		
		
		
		
		this.updateButton.addActionListener(this);
		this.gobackButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.updateButton)
		{	
			String emailString = this.email.getText();
			String nameString = this.name.getText();
			String passwordString = this.password.getText();
			String addressString = this.address.getText();
			String phoneString = this.phoneNumber.getText();
			
			UserDao userDao = new UserDao();
			CustomerDao customerDao = new CustomerDao();
			if(emailString.isEmpty() || nameString.isEmpty() || passwordString.isEmpty() || addressString.isEmpty()) {
				new Popdialog("email, name, password and address can not be empty!");
				
			} else {
				Customer newCustomer = new Customer(customerID, emailString, passwordString, nameString, addressString, phoneString);
				customerDao.updateCustomer(newCustomer);
				new Customer_Profile(customerID);
				this.dispose();
			}
			
			
		}
		else if(e.getSource() == this.gobackButton)
		{
			new Customer_Profile(customerID);
			this.dispose();
		}
		
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		new Customer_Update_Profile(47);
	}
}
