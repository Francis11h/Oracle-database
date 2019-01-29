package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
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

public class Sign_Up extends JFrame implements ActionListener
{
	JTextField email;
	JLabel lblEmailAddress;
	JButton signupButton;
	JButton gobackButton;
	JTextField password;
	JTextField name;
	JTextField address;
	JTextField phoneNumber;
	JLabel lblPassword;
	JLabel lblName;
	JLabel lblAddress;
	JLabel lblPhoneNumber;
	
	

	public Sign_Up()
	{
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Sign Up");
		this.setLayout(null);
		
		this.setSize(400, 500);
		this.setLocation(400, 500);
		this.email  = new JTextField();
		this.password = new JTextField();
		this.name  = new JTextField();
		this.address  = new JTextField();
		this.phoneNumber  = new JTextField();
		this.lblEmailAddress = new JLabel("Email Address");
		this.lblPassword = new JLabel("password");
		this.lblName = new JLabel("Name");
		this.lblAddress = new JLabel("Adress");
		this.lblPhoneNumber = new JLabel("Phone Number");
		this.signupButton = new JButton("Sign up");
		this.gobackButton = new JButton("Go back");
		
		
		
		
		this.email.setBounds(141, 57, 130, 26);
		this.password.setBounds(141, 100, 130, 26);
		this.name.setBounds(141, 150, 130, 26);
		this.address.setBounds(141, 200, 130, 26);
		this.phoneNumber.setBounds(141, 250, 130, 26);
		
		this.lblEmailAddress.setBounds(155, 41, 98, 16);
		this.lblPassword.setBounds(155, 85, 98, 16);
		this.lblName.setBounds(155, 134, 98, 16);
		this.lblAddress.setBounds(155,180,98,16);
		this.lblPhoneNumber.setBounds(155, 234, 98, 16);
		this.signupButton.setBounds(154, 300, 117, 29);
		this.gobackButton.setBounds(154, 340, 117, 29);
		
		this.add(email);
		this.add(lblEmailAddress);
		this.add(signupButton);
		this.add(gobackButton);
		this.add(password);
		this.add(name);
		this.add(address);
		this.add(phoneNumber);
		this.add(lblPassword);
		this.add(lblName);
		this.add(lblPhoneNumber);
		this.add(lblAddress);
		
		this.add(gobackButton);
		this.add(gobackButton);
		
		
		
		
		this.signupButton.addActionListener(this);
		this.gobackButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.signupButton)
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
				if(!userDao.checkUserExists(emailString)) {
					Customer newCustomer = new Customer(1, emailString, passwordString, nameString, addressString, phoneString);
					customerDao.addCustomer(newCustomer);
					new Login();
					this.dispose();
				} else {
					new Popdialog("The email has been used in this system, please choose another one.");
				}
			}
			
			
		}
		else if(e.getSource() == this.gobackButton)
		{
			new Home();
		}
		this.dispose();
		
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		new Sign_Up();
	}
}
