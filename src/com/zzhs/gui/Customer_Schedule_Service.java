package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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

import com.zzhs.entity.Schedule_Service;
import com.zzhs.dao.MechanicDao;
import com.zzhs.dao.Schedule_ServiceDao;

public class Customer_Schedule_Service extends JFrame implements ActionListener{

	int customerID;
	int eid = 0;
	Schedule_Service service = new Schedule_Service();
	MechanicDao mechanic_dao = new MechanicDao();
	Schedule_ServiceDao service_schedule = new Schedule_ServiceDao();
	
	JButton scheduleMaintenanceButton;
	JButton schedulRepairButton;
	JButton goBackButton;
	
	JTextField licencePlateFild;
	JTextField currentMileageFild;
	JTextField mechanicNameFild;

	JLabel lblLicencePlate;
	JLabel lblCurrentMileage;
	JLabel lblMechanicName;
	
	

	public Customer_Schedule_Service(int customerID)
	{
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.customerID = customerID;
	}
	void init()
	{
		
		this.setTitle("Customer Schedule Service");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 400);
		
		this.licencePlateFild = new JTextField();
		this.mechanicNameFild = new JTextField();
		this.currentMileageFild = new JTextField();
		

		this.lblLicencePlate = new JLabel("Licence Plate");
		this.lblMechanicName = new JLabel("Mechanic Name");
		this.lblCurrentMileage = new JLabel("Current mileage");
		
		this.scheduleMaintenanceButton = new JButton("Schedule Maintenace");
		this.schedulRepairButton = new JButton("Schedule Repair");
		this.goBackButton = new JButton("Go Back");
		
		
		
		this.add(lblLicencePlate);
		this.add(licencePlateFild);
		this.add(lblCurrentMileage);
		this.add(currentMileageFild);
		this.add(lblMechanicName);
		this.add(mechanicNameFild);
		
		
		this.add(schedulRepairButton);
		this.add(scheduleMaintenanceButton);
		this.add(goBackButton);
		
		
		
	
		this.scheduleMaintenanceButton.addActionListener(this);
		this.schedulRepairButton.addActionListener(this);
		this.goBackButton.addActionListener(this);
		
		
		
		

		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{	
		String licencePlate = this.licencePlateFild.getText();
		String currentMileage = this.currentMileageFild.getText();
		String Mechanic = this.mechanicNameFild.getText();
		service.setCid(customerID);
		service.setLicence_id(licencePlate);
		eid =  mechanic_dao.getEidFromName(Mechanic);
		if(e.getSource() == this.scheduleMaintenanceButton)
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
			new Customer_Schedule_Maintenance_Page1(this.customerID, service);
		}
		else if(e.getSource() == this.schedulRepairButton)
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
			new Customer_Schedule_Repair_Page1(this.customerID, service);
		}
		else if(e.getSource() == this.goBackButton)
		{
			new Customer_Service(this.customerID);
		}
		this.dispose();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Customer_Schedule_Service(1001);	
	}

}
