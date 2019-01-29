package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.zzhs.entity.Schedule_Service;
import com.zzhs.entity.Mechanic;
import com.zzhs.dao.Schedule_ServiceDao;
import java.sql.Timestamp;
import com.zzhs.dao.MechanicDao;

public class Customer_Reschedule_Service_Page2 extends JFrame implements ActionListener{

	int customerID;
	Schedule_Service service;
	Timestamp date1;
	Timestamp date2;
	String selectedDate;
	String mechanicName;
	
	JButton rescheduleDateButton;
	JButton goBackButton;
	JRadioButton date1Radio;
	JRadioButton date2Radio;
	JTextField mechanicFild;
	MechanicDao mechanic_dao = new MechanicDao();
	Schedule_ServiceDao schedule_service = new Schedule_ServiceDao();
	
	
	public Customer_Reschedule_Service_Page2(int customerID, Schedule_Service service, Timestamp date1, Timestamp date2)
	{
		this.customerID = customerID;
		this.service = service;
		this.date1 = date1;
		this.date2 = date2;
		this.mechanicName = this.mechanic_dao.getMechanic(this.service.getEid()).getName();
		this.init();
		this.setVisible(true);	
	}
	void init()
	{
		this.setTitle("Customer Reschedule Service Page2");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
		
		this.rescheduleDateButton = new JButton("Reschedule on Date");
		this.goBackButton = new JButton("Go Back");
		this.mechanicFild = new JTextField(mechanicName);
		this.date1Radio = new JRadioButton(this.date1.toString());
		this.date2Radio = new JRadioButton(this.date2.toString());
		
		
		
		this.add(mechanicFild);
		this.add(date1Radio);
		this.add(date2Radio);
		this.add(rescheduleDateButton);
		this.add(goBackButton);
		
		
		this.rescheduleDateButton.addActionListener(this);
		this.goBackButton.addActionListener(this);
		
		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		
		if(e.getSource() == this.rescheduleDateButton)
		{
			if(this.date1Radio.isSelected())
			{
				this.selectedDate = this.date1Radio.getText();
				this.service.setService_date(date1);
			}
			if(this.date2Radio.isSelected())
			{
				this.selectedDate = this.date2Radio.getText();
				this.service.setService_date(date2);
			}
			//gui show message
		
			//TODO Reschedule his exisiting service to the chosen data(this.seelctedDate) and 
			//make necessary adjustment to the parts commitment in the inventory
			//(this.selectedDate,customerID,licencePlate,currentMileage,mechanicName)
			this.service.setService_end_date(this.schedule_service.CalculateServiceEndDateFromService(service));
			this.schedule_service.ReScheduleService(service);
			new Customer_Service(this.customerID);
		}
		
		else if(e.getSource() == this.goBackButton)
		{
			new Customer_Reschedule_Service_Page1(this.customerID);
		}
		this.dispose();
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//new Customer_Reschedule_Service_Page2(Integer.parseInt(args[0]),args[1],args[2],args[3],args[4]);
		
	}
}
