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

public class Customer_View_Invoice_Details extends JFrame implements ActionListener {

	int customerID;
	String licencePlate;
	int serviceID;
	String serviceType;//maintenace or repair
	String mechanicName;
	String serviceStartDateTime;
	String serviceEndDateTime;
	String partsUsedWithEachCost;
	String totalLaborHours;
	String laborsWagesPerHour;
	String totalServiceCost;
	
	
	JButton goBackButton;
	
	JTextField serviceIDfild;
	JTextField serviceStartDateTimeFild;
	JTextField serviceEndDateTimeFild;
	JTextField licencePlateFild;
	JTextField serviceTypeFild;
	JTextField mechanicNameFild;
	JTextField partsUsedWithEachCostFild;
	JTextField totalLaborHoursFild;
	JTextField laborsWagesPerHourFild;
	JTextField totalServiceCostFild;

	JLabel lblServiceID;
	JLabel lblLicencePlate;
	JLabel lblServiceStartDateTime;
	JLabel lblServiceEndDateTime;
	JLabel lblMechanicName;
	JLabel lblServiceType;
	JLabel lblPartsUsedWithEachCost;
	JLabel lblTotalLaborHours;
	JLabel lblLaborsWagesPerHour;
	JLabel lblTotalServiceCost;
	
	

	public Customer_View_Invoice_Details(int customerID)
	{
		//TODO GUI: use card layout??
		
		String serviceIDInput = JOptionPane.showInputDialog(this,"Input service ID you want to view the invoice");
		serviceID = Integer.parseInt(serviceIDInput);
		System.out.print(serviceID);
		
		this.init();
		
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.customerID = customerID;
	}
	void init()
	{
	
			System.out.print(serviceID);
			//TODO
			//search customer's invoices (serviceID) 
			//return (licence plate, serviceID,Service start date/time, Service end date/time, service type(maintenance/repair), Mechanic Name, Total service cost)
			//this.licensePlate = licence plate....
			this.setTitle("Customer view invoice detailes");
			this.setLayout(new GridLayout(0,1));
			this.setLocation(400, 500);
			this.setSize(400, 400);
			
			
			this.licencePlateFild = new JTextField(licencePlate);
			this.serviceIDfild = new JTextField(serviceID);
			this.serviceEndDateTimeFild = new JTextField(serviceEndDateTime);
			this.serviceStartDateTimeFild = new JTextField(serviceStartDateTime);
			this.serviceTypeFild = new JTextField(serviceType);
			this.mechanicNameFild = new JTextField(mechanicName);
			this.partsUsedWithEachCostFild = new JTextField(partsUsedWithEachCost);
			this.totalLaborHoursFild = new JTextField(totalLaborHours);
			this.laborsWagesPerHourFild = new JTextField(laborsWagesPerHour);
			this.totalServiceCostFild = new JTextField(totalServiceCost);
			
			
			this.lblLicencePlate = new JLabel("Licence Plate");
			this.lblServiceID = new JLabel("Service ID");
			this.lblServiceStartDateTime = new JLabel("Service Start Date");
			this.lblServiceEndDateTime = new JLabel("Service end Date");
			this.lblServiceType = new JLabel("Service Type");
			this.lblMechanicName = new JLabel("Mechanic Name");
			this.lblPartsUsedWithEachCost = new JLabel("Parts Used in service with cost of each part");
			this.lblTotalLaborHours = new JLabel("Total labor hours");
			this.lblLaborsWagesPerHour = new JLabel("Labor wages per hour");
			this.lblTotalServiceCost = new JLabel("Total service cost");
			
		
	
			this.goBackButton = new JButton("Go Back");
		
			
			
			
			this.add(goBackButton);
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
			this.add(lblPartsUsedWithEachCost);
			this.add(partsUsedWithEachCostFild);
			this.add(lblTotalLaborHours);
			this.add(totalLaborHoursFild);
			this.add(lblLaborsWagesPerHour);
			this.add(laborsWagesPerHourFild);
			this.add(lblTotalServiceCost);
			this.add(totalServiceCostFild);
			
	
			
			this.goBackButton.addActionListener(this);
		
		

	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		
		if(e.getSource() == this.goBackButton)
		{
			new Customer_Invoice(customerID);
		}
		
		this.dispose();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//new Customer_View_Invoice_Details(Integer.parseInt(args[0]));
		new Customer_View_Invoice_Details(4);
		
		
		
	}


}
