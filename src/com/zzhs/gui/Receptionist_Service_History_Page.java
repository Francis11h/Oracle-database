package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.zzhs.dao.CustomerDao;
import com.zzhs.dao.Schedule_ServiceDao;

public class Receptionist_Service_History_Page extends JFrame implements ActionListener{
	
	int employeeID;
	int customerID;
	/*String serviceID;
	String licencePlate;
	String serviceType;
	String mechanicName;
	String serviceStartDateTime;
	String serviceEndDateTime;
	String serviceStatus;*/
	
	
	JButton goBackButton;
	

	//JTextField customerEmailFild;
	//JLabel lblCustomerEmail;
	/*JTextField serviceIDfild;
	JTextField licencePlateFild;
	JTextField serviceTypeFild;
	JTextField mechanicNameFild;
	JTextField serviceStartDateTimeFild;
	JTextField serviceEndDateTimeFild;
	JTextField serviceStatusFild;
	
	JLabel lblCustomerEmail;
	JLabel lblServiceID;
	JLabel lblLicencePlate;
	JLabel lblServiceType;
	JLabel lblMechanicName;
	JLabel lblServiceStartDateTime;
	JLabel lblServiceEndDateTime;
	JLabel lblServiceStatus;*/
	JTextArea show_service_history;

	public Receptionist_Service_History_Page(int employeeID)
	{
		this.init();
		//this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//this.customerID = customerID;
	}
	void init()
	{
		
		String newName = JOptionPane.showInputDialog(this,"Input Customer's email address");
		if(newName != null){
			//TODO searchCustomer'sServiceData (emailAddress) in DB
			//Return serviceID, license plate,service type,Mechanic name, service start date/time, service end Date/time
			//		(expected or actual), Service status(pending, ongoing, complete)	
		
			//this.serviceID = ;
			//this.licensePlate = ; etc...
			CustomerDao customerdao = new CustomerDao();
			this.customerID = customerdao.getCustomerID(newName);
			List<List<String>> service_history;
			Schedule_ServiceDao schedule_serviceDao = new Schedule_ServiceDao();
			service_history = schedule_serviceDao.viewServiceHistory(this.customerID);
			this.show_service_history = new JTextArea(6, 13);
			String[] title = {"service id:", "license plate:", "service type:", "Service Start Date:", "Mechanic Name:", "Service End Date:", "Service Status:"}; 
			int i = 0;
			while(i < service_history.size()){
				int j = 0;
				while(j < service_history.get(i).size()){
					this.show_service_history.append(title[j] + service_history.get(i).get(j) + " ");
					j = j + 1;
				}
				this.show_service_history.append("\n");
				i = i + 1;
			}
			
			
			this.setVisible(true);
			
	
			
			
		}
		 
		this.setTitle("Receptionist View Service History");
		this.setLayout(new FlowLayout());
		this.setLocation(400, 500);
		this.setSize(400, 400);
		
		//this.customerEmailFild = new JTextField();
		/*this.licencePlateFild = new JTextField(licencePlate);
		this.serviceIDfild = new JTextField(serviceID);
		this.serviceTypeFild = new JTextField(serviceType);
		this.mechanicNameFild = new JTextField(mechanicName);
		this.serviceStartDateTimeFild = new JTextField(serviceStartDateTime);
		this.serviceEndDateTimeFild = new JTextField(serviceEndDateTime);
		this.serviceStatusFild = new JTextField(serviceStatus);*/
		
		//this.lblCustomerEmail = new JLabel("Input email here");
		/*this.lblServiceID = new JLabel("Service ID");
		this.lblLicencePlate = new JLabel("Licence Plate");
		this.lblServiceType = new JLabel("Service Type");
		this.lblMechanicName = new JLabel("Mechanic");
		this.lblServiceStartDateTime = new JLabel("Service Start Date/Time");
		this.lblServiceEndDateTime = new JLabel("Service End Date/Time");
		this.lblServiceStatus = new JLabel("Service Status");*/
		
		
		this.goBackButton = new JButton("Go Back");
		
		//this.add(lblCustomerEmail);
		this.add(goBackButton);
		this.add(show_service_history);
		//this.add(customerEmailFild);
		/*this.add(lblLicencePlate);
		this.add(licencePlateFild);
		this.add(lblServiceID);
		this.add(serviceIDfild);
		this.add(lblServiceType);
		this.add(serviceTypeFild);
		this.add(lblMechanicName);
		this.add(mechanicNameFild);
		this.add(lblServiceStartDateTime);
		this.add(serviceStartDateTimeFild);
		this.add(lblServiceEndDateTime);
		this.add(serviceEndDateTimeFild);
		this.add(lblServiceStatus);
		this.add(serviceStatusFild);*/
		
		
	
		
		this.goBackButton.addActionListener(this);
		
		
		
		
		

		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == this.goBackButton)
		{
			new Receptionist_Landing_Page(employeeID);
		}
		this.dispose();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		//new Receptionist_Service_History_Page(Integer.parseInt(args[0]));
		new Receptionist_Service_History_Page(Integer.parseInt ("323"));
		
		
	}


}
