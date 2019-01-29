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

import com.zzhs.entity.Schedule_Service;
import com.zzhs.dao.MechanicDao;
import com.zzhs.dao.Schedule_ServiceDao;
import com.zzhs.dao.CustomerDao;

public class Receptionist_Schedule_Service_Page extends JFrame implements ActionListener {

	int employeeID;
	int eid;
	String email;
	String licencePlate;
	double currentMileage;
	String mechanicName;
	Schedule_Service service = new Schedule_Service();
	Schedule_ServiceDao service_schedule = new Schedule_ServiceDao();
	MechanicDao mechanic_dao = new MechanicDao();
	CustomerDao customer_dao = new CustomerDao();
	
	JButton goBackButton;
	JButton repairButton;
	JButton maintenanceButton;
	

	JTextField customerEmailFild;
	JTextField licencePlateFild;
	JTextField currentMileageFild;
	JTextField mechanicNameFild;
	
	
	JLabel lblCustomerEmail;
	JLabel lblLicencePlate;
	JLabel lblCurrentMileage;
	JLabel lblMechanicName;
	

	public Receptionist_Schedule_Service_Page(int employeeID)
	{
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.employeeID= employeeID;
	}
	void init()
	{
		 
		this.setTitle("Receptionist Schedule Service");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 400);
	
		this.customerEmailFild = new JTextField();
		this.licencePlateFild = new JTextField();
		this.currentMileageFild = new JTextField();
		this.mechanicNameFild = new JTextField();
		
		this.lblCustomerEmail = new JLabel("Customer email address");
		this.lblLicencePlate = new JLabel("Licence Plate");
		this.lblCurrentMileage = new JLabel("Current Mileage");
		this.lblMechanicName = new JLabel("Mechanic");
		
		
		this.goBackButton = new JButton("Go Back");
		this.repairButton = new JButton("Scehdule Repair");
		this.maintenanceButton = new JButton("Schedule Maintenance");
		
		this.add(lblCustomerEmail);
		
		this.add(customerEmailFild);
		
		this.add(lblLicencePlate);
		this.add(licencePlateFild);
		this.add(lblCurrentMileage);
		this.add(currentMileageFild);
		this.add(lblMechanicName);
		this.add(mechanicNameFild);
		
		this.add(maintenanceButton);
		this.add(repairButton);
		this.add(goBackButton);
		
		
		this.maintenanceButton.addActionListener(this);
		this.repairButton.addActionListener(this);
		this.goBackButton.addActionListener(this);		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		this.email = customerEmailFild.getText();
		this.licencePlate = licencePlateFild.getText();
		this.currentMileage= Double.parseDouble(currentMileageFild.getText());
		this.mechanicName = mechanicNameFild.getText();
		service.setCid(customer_dao.getCustomerID(email));
		service.setLicence_id(licencePlate);
		eid =  mechanic_dao.getEidFromName(mechanicName);
		if(e.getSource() == this.maintenanceButton) 
		{
			service.setService_type("MENT");
			service.setSvid(service_schedule.getSvidFromLicence(service.getLicence_id()));
			if(eid != -1) {
				service.setEid(eid);
			}
			else{
				eid = service_schedule.GetEid("MENT");
				service.setEid(eid);
			}
			service.setSid(service_schedule.getServiceCenterIdFromMechanic(eid));
			new Receptionist_Schedule_Maintenance_Page1(this.employeeID, this.service);
		}
		else if(e.getSource() == this.repairButton) 
		{
			service.setService_type("REPR");
			if(eid != -1){
				service.setEid(eid);
			}
			else{
				eid = service_schedule.GetEid("REPR");
				service.setEid(eid);
			}
			service.setSid(service_schedule.getServiceCenterIdFromMechanic(eid));
			new Receptionist_Schedule_Repair_Page1(this.employeeID, this.service);
		}
		else if(e.getSource() == this.goBackButton)
		{
			new Receptionist_Landing_Page(this.employeeID);
		}
		
		this.dispose();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		new Receptionist_Schedule_Service_Page(634622236);
		//new Receptionist_Schedule_Service_Page(Integer.parseInt ("323"));
		
		
	}


}
