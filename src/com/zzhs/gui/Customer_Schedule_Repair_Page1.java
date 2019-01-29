package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.zzhs.entity.Schedule_Service;
import com.zzhs.dao.Schedule_ServiceDao;
import com.zzhs.dao.InventoryDao;
import java.sql.Timestamp;

public class Customer_Schedule_Repair_Page1 extends JFrame implements ActionListener {

	int customerID;
	Schedule_ServiceDao schedule_service = new Schedule_ServiceDao();
	InventoryDao inventory = new InventoryDao();
	Timestamp first_date;
	Timestamp second_date;
	Schedule_Service service;
	JButton engineKnockButton;
	JButton carDriftsInaParicularDirectionButton;
	JButton batteryDoesNotHoldChargeButton;
	JButton blackUncleanExhaustButton;
	JButton ACHeaterHotWorkingButton;
	JButton headlampsTrailLampsNotWorkingButton;
	JButton checkEngineLightButton;
	JButton goBack;
	
	
	public Customer_Schedule_Repair_Page1(int customerID, Schedule_Service service)
	{
		this.init();
		this.setVisible(true);
		this.customerID = customerID;
		this.service = service;
	}
	void init()
	{
		this.setTitle("Customer Service");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
		this.engineKnockButton = new JButton("Engine knock");
		this.blackUncleanExhaustButton = new JButton("Black/unclean exhaust");
		this.ACHeaterHotWorkingButton = new JButton("A/C-Heater not working");
		this.headlampsTrailLampsNotWorkingButton = new JButton("Headlamps/Tail lamps not working");
		this.checkEngineLightButton = new JButton("Chack engine light");
		this.carDriftsInaParicularDirectionButton = new JButton("Car drifts in a particular direction");
		
		this.batteryDoesNotHoldChargeButton = new JButton("Battery does not hold charge");
		this.goBack = new JButton("Go Back");
		

		this.add(engineKnockButton);
		this.add(carDriftsInaParicularDirectionButton);
		this.add(batteryDoesNotHoldChargeButton);
		this.add(blackUncleanExhaustButton);
		this.add(ACHeaterHotWorkingButton);
		this.add(headlampsTrailLampsNotWorkingButton);
		this.add(checkEngineLightButton);
		this.add(goBack);
		
		
		this.engineKnockButton.addActionListener(this);
		this.blackUncleanExhaustButton.addActionListener(this);
		this.ACHeaterHotWorkingButton.addActionListener(this);
		this.headlampsTrailLampsNotWorkingButton.addActionListener(this);
		this.checkEngineLightButton.addActionListener(this);
		this.carDriftsInaParicularDirectionButton.addActionListener(this);
		this.batteryDoesNotHoldChargeButton.addActionListener(this);
		this.goBack.addActionListener(this);
	
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		//TODO f
		
		first_date = schedule_service.GetTwoEarlestDate(service.getEid(), service.getService_type());
		second_date = schedule_service.AddHalfHour(first_date);
		if(e.getSource() == this.engineKnockButton)
		{
			this.service.setRsvid(19);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) {
				new Customer_Schedule_Repair_Page2(this.customerID, this.service, this.first_date, this.second_date);
			}
			else {
				
			}
		}
		else if(e.getSource() == this.carDriftsInaParicularDirectionButton)
		{
			this.service.setRsvid(20);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) {
				new Customer_Schedule_Repair_Page2(this.customerID, this.service, this.first_date, this.second_date);
			}
			else {
				
			}
		}
		else if(e.getSource() == this.blackUncleanExhaustButton)
		{
			this.service.setRsvid(22);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) {
				new Customer_Schedule_Repair_Page2(this.customerID, this.service, this.first_date, this.second_date);
			}
			else {
				
			}
		}
		else if(e.getSource() == this.ACHeaterHotWorkingButton)
		{
			this.service.setRsvid(23);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) {
				new Customer_Schedule_Repair_Page2(this.customerID, this.service, this.first_date, this.second_date);
			}
			else {
				
			}
		}
		else if(e.getSource() == this.headlampsTrailLampsNotWorkingButton)
		{
			this.service.setRsvid(24);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) {
				new Customer_Schedule_Repair_Page2(this.customerID, this.service, this.first_date, this.second_date);
			}
			else {
				
			}
		}
		else if(e.getSource() == this.checkEngineLightButton)
		{
			this.service.setRsvid(25);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) {
				new Customer_Schedule_Repair_Page2(this.customerID, this.service, this.first_date, this.second_date);
			}
			else {
				
			}
		}
		else if(e.getSource() == this.carDriftsInaParicularDirectionButton)
		{
			this.service.setRsvid(20);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) {
				new Customer_Schedule_Repair_Page2(this.customerID, this.service, this.first_date, this.second_date);
			}
			else {
				
			}
		}
		else if(e.getSource() == this.batteryDoesNotHoldChargeButton)
		{
			this.service.setRsvid(21);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) {
				new Customer_Schedule_Repair_Page2(this.customerID, this.service, this.first_date, this.second_date);
			}
			else {
				
			}
		}
		else if(e.getSource() == this.goBack)
		{
			new Customer_Landing(customerID);
		}
		this.dispose();
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new  Customer_Service(Integer.parseInt(args[0]));
		
	}

}
