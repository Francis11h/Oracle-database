package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.zzhs.dao.CustomerDao;
import com.zzhs.entity.Customer;

public class Customer_View_Profile extends JFrame implements ActionListener{

	
	JButton goBack;
	int customerID;
	String name;
	String address;
	String email;
	String phoneNumber;
	List<String> allCars = new ArrayList<>();
	
	

	JLabel lblName;
	JLabel lblAddress;
	JLabel lblEmail;
	JLabel lblPhoneNumber;
	JList allCarsList;
	JTextArea allCarsField;
	
	
	public Customer_View_Profile(int customerID)
	{
		this.customerID = customerID;
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}
	void init()
	{
		System.out.println(customerID);
		CustomerDao customerDao = new CustomerDao();
		Customer customer = customerDao.getCustomer(customerID);
		
		this.allCars = customerDao.getCars(customerID);
		
		if (customer != null) {
			this.name = customer.getName();
			this.address = customer.getAddress();
			this.email = customer.getEmail();
			this.phoneNumber = customer.getPhone();
		}
//		allCars.add("test");
		this.setTitle("Customer View Profile");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
		
		this.goBack = new JButton("Go Back");
		this.lblName = new JLabel("Customer Name: " + name);
		this.lblAddress = new JLabel("Customer address: " + address);
		this.lblEmail = new JLabel("Customer email: " + email);
		this.lblPhoneNumber = new JLabel("Customer phone: " + phoneNumber);
		
		//TODO GUI design
		//CHange array list to defualt List model

		//DefaultListModel<String> allCarsDefaultListModel = (DefaultListModel<String>)allCars.getModel();
		
		//this.allCarsList = new JList(allCarsDefaultListModel);
		//this.allCarsList.setListData(allCars);
		this.allCarsList = new JList(allCars.toArray());
		this.allCarsField = new JTextArea(allCars.toString());
		this.allCarsField.setLineWrap(true);
		this.add(lblName);
		this.add(lblEmail);
		this.add(lblAddress);
		this.add(lblPhoneNumber);
		this.add(allCarsList);
//		this.add(allCarsField);
		this.add(goBack);
	
		this.goBack.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == this.goBack)
		{
			new Customer_Profile(customerID);
		}
		this.dispose();
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Customer_View_Profile(47);
		//new Customer_View_Profile(Integer.parseInt(args[0]));
		
	}

}
