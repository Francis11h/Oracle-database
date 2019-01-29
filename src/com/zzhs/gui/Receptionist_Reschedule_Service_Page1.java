package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.Schedule_ServiceDao;
import com.zzhs.entity.Schedule_Service;

import java.awt.event.*;
import java.sql.Timestamp;
import java.util.List;
import com.zzhs.dao.*;

public class Receptionist_Reschedule_Service_Page1 extends JFrame implements ActionListener 
{
	int customerID;
	int eid;
	/*String licencePlate;
	String serviceID;
	String serviceDate;
	String serviceType;//maintenace or repair
	String serviceDetails;//service A,B,C or problem*/
	
	
	
	JButton pickServiceButton;
	JButton goBackButton;
	JButton submitButton;
	JTextArea show_service;
	Schedule_ServiceDao schedule_service = new Schedule_ServiceDao();
	CustomerDao customer_dao = new CustomerDao();
	Schedule_Service service;
	Timestamp first_date;
	Timestamp second_date;
	List<List<String>> service_detail;
	JTextField customer_email;
	/*JTextField licencePlateFild;
	JTextField serviceIDfild;
	JTextField serviceDateFild;
	JTextField serviceTypeFild;
	JTextArea serviceDetailsFild;
	JLabel lblLicencePlate;
	JLabel lblServiceID;
	JLabel lblServiceDate;
	JLabel lblServiceType;
	JLabel lblServiceDetails;*/
	

	public Receptionist_Reschedule_Service_Page1(int eid)
	{
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.eid = eid;
		this.init();
		this.setVisible(true);
	}
	void init()
	{
		//TODO
		//search customer's upcoming services (customerID) 
		//return licence plate, erviceID,Service date, service type(maintenance/repair), Service Details(A/B/C or problem))
		//this.licensePlate = licence plate....
		
		this.setTitle("Receptionist Reshcedule Service page1");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 400);
		
		
		
		/*this.licencePlateFild = new JTextField(serviceID);
		this.serviceIDfild = new JTextField(licencePlate);
		this.serviceDateFild = new JTextField(serviceDate);
		this.serviceTypeFild = new JTextField(serviceType);
		this.serviceDateFild = new JTextField(serviceDate);
		this.serviceDetailsFild = new JTextArea(serviceDetails);
		this.serviceDetailsFild.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		this.serviceDetailsFild.setLineWrap(true);
		
		
		
		
		this.lblLicencePlate = new JLabel("Licence Plate");
		this.lblServiceID = new JLabel("Service ID");
		this.lblServiceDate = new JLabel("Service Date");
		this.lblServiceType = new JLabel("Service Type");
		this.lblServiceDetails = new JLabel("Service Details");*/
		
	
		
		
		this.goBackButton = new JButton("Go Back");
		this.pickServiceButton = new JButton("Pick a Service");
		this.submitButton = new JButton("submit");
		this.show_service = new JTextArea();
		this.customer_email = new JTextField();
		
		
		
		this.add(goBackButton);
		this.add(pickServiceButton);
		this.add(new JLabel("input email:"));
		this.add(customer_email);
		this.add(new JLabel("service detail:"));
		this.add(show_service);
		this.add(submitButton);
		/*this.add(lblLicencePlate);
		this.add(licencePlateFild);
		this.add(lblServiceID);
		this.add(serviceIDfild);
		this.add(lblServiceDate);
		this.add(serviceDateFild);
		this.add(lblServiceType);
		this.add(serviceTypeFild);
		this.add(lblServiceDetails);
		this.add(serviceDetailsFild);*/
		
		
		
	
		this.pickServiceButton.addActionListener(this);
		this.goBackButton.addActionListener(this);
		this.submitButton.addActionListener(this);

	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		
		if(e.getSource() == this.goBackButton)
		{
			new Receptionist_Landing_Page(eid);
			this.dispose();
		}
		else if(e.getSource() == this.pickServiceButton)
		{
			String serviceIDInput = JOptionPane.showInputDialog(this,"Input service ID you want to rechedule");
			if(!serviceIDInput.isEmpty()){
				int serviceID = Integer.parseInt(serviceIDInput);
				//TODO find two Earliest available maintenance/repair dates( for that service ID) that are at least one day after the current service date.
				//(ServiceID, customerID,serviceDate)
				//Return (date1,date2, mehcanicname)
				//new Customer_Reshcedule_Service_Page2(customerID,serviceID,mechanicName,date1,date2)
				service = this.schedule_service.GetServiceFromAid(serviceID);
				first_date = this.schedule_service.GetTwoEarlestDate(service.getEid(), service.getService_type());
				second_date = this.schedule_service.AddHalfHour(first_date);
				new Receptionist_Reschedule_Service_Page2(eid, service, first_date, second_date);
			}
			this.dispose();
		}
		else {
			this.service_detail = this.schedule_service.getCustomerServiceDetail(this.customer_dao.getCustomerID(this.customer_email.getText()));
			String[] title = {"License Plate:", "Service ID:", "Service Date:", "Service Type:", "Service Detail:"};
			String content = "";
			int i = 0;
			while(i < service_detail.size()){
				int j = 0;
				while(j < service_detail.get(i).size()){
					content = content + title[j] + service_detail.get(i).get(j) + " ";
					j = j + 1;
				}
				content = content + "\n";
				i = i + 1;
			}
			this.show_service.setText(content);
		}
	}
	
	public static void main(String[] args)
	{
		new Receptionist_Reschedule_Service_Page1(634622236);
	}
}
