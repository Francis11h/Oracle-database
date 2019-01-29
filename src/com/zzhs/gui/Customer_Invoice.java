package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.zzhs.dao.EmployeeMRDao;

public class Customer_Invoice extends JFrame implements ActionListener{

	int customerID;
	String licencePlate;
	String serviceID;
	String serviceType;//maintenance or repair
	String mechanicName;
	String serviceStartDateTime;
	String serviceEndDateTime;
	String totalServiceCost;
	
	
	
	
	JButton viewInvoiceDetailsButton;
	JButton goBackButton;
	
	JTextField serviceIDfild;
	JTextField serviceStartDateTimeFild;
	JTextField serviceEndDateTimeFild;
	JTextField licencePlateFild;
	JTextField serviceTypeFild;
	JTextField mechanicNameFild;
	JTextField totalServiceCostFild;
	
	JLabel lblServiceID;
	JLabel lblLicencePlate;
	JLabel lblServiceStartDateTime;
	JLabel lblServiceEndDateTime;
	JLabel lblMechanicName;
	JLabel lblServiceType;
	JLabel lblTotalServiceCost;
	
	

	public Customer_Invoice(int customerID)
	{
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.customerID = customerID;
	}
	void init()
	{
		
		
		
		
		
		this.setTitle("Customer Invoice");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 400);
		
		
		
		this.licencePlateFild = new JTextField(serviceID);
		this.serviceIDfild = new JTextField(licencePlate);
		this.serviceEndDateTimeFild = new JTextField(serviceEndDateTime);
		this.serviceStartDateTimeFild = new JTextField(serviceStartDateTime);
		this.serviceTypeFild = new JTextField(serviceType);
		this.mechanicNameFild = new JTextField(mechanicName);
		this.totalServiceCostFild = new JTextField(totalServiceCost);
		
		
		this.lblLicencePlate = new JLabel("Licence Plate");
		this.lblServiceID = new JLabel("Service ID");
		this.lblServiceStartDateTime = new JLabel("Service Start Date");
		this.lblServiceEndDateTime = new JLabel("Service end Date");
		this.lblServiceType = new JLabel("Service Type");
		this.lblMechanicName = new JLabel("Mechanic Name");
		this.lblTotalServiceCost = new JLabel("Total service cost");
		
	
		
		
		this.goBackButton = new JButton("Go Back");
		this.viewInvoiceDetailsButton = new JButton("View Invoice Details");
		
		
		
		this.add(goBackButton);
		this.add(viewInvoiceDetailsButton);
		this.add(lblServiceID);
		this.add(serviceIDfild);
		this.add(lblServiceStartDateTime);
		this.add(serviceStartDateTimeFild);
		this.add(lblServiceEndDateTime);
		this.add(serviceEndDateTimeFild);	
		this.add(lblLicencePlate);
		this.add(licencePlateFild);
		this.add(lblServiceType);
		this.add(serviceTypeFild);
		this.add(lblMechanicName);
		this.add(mechanicNameFild);
		this.add(lblTotalServiceCost);
		this.add(totalServiceCostFild);
		
		
		
	
		this.viewInvoiceDetailsButton.addActionListener(this);
		this.goBackButton.addActionListener(this);
		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		
		if(e.getSource() == this.goBackButton)
		{
			new Customer_Landing(customerID);
		}
		else if(e.getSource() == this.viewInvoiceDetailsButton)
		{
			
			new Customer_View_Invoice_Details(customerID);
		}
		this.dispose();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//new Customer_Invoice(Integer.parseInt(args[0]));
		new Customer_Invoice(4);
		
	}

}
