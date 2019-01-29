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

import java.sql.Timestamp;
import com.zzhs.entity.Schedule_Service;
import com.zzhs.entity.Mechanic;
import com.zzhs.dao.Schedule_ServiceDao;
import com.zzhs.dao.MechanicDao;


public class Customer_Schedule_Maintenance_Page2 extends JFrame implements ActionListener{

	int customerID;
	String date1Text;
	String date2Text;
	String selectedDate;
	JButton ScheduleOnDateButton;
	JButton goBackButton;
	JRadioButton date1Radio;
	JRadioButton date2Radio;
	JTextField mechanicFild;
	Schedule_Service service;
	Timestamp date1;
	Timestamp date2;
	Schedule_ServiceDao schedule_service;
	MechanicDao mechanic_dao;
	
	public Customer_Schedule_Maintenance_Page2(int customerID, Schedule_Service service, Timestamp first_date, Timestamp second_date)
	{
		this.customerID = customerID;
		this.date1 = first_date;
		this.date2 = second_date;
		this.date1Text = date1.toString();
		this.date2Text = date2.toString();
		this.service = service;
		this.schedule_service = new Schedule_ServiceDao();
		this.mechanic_dao = new MechanicDao();
		this.init();
		this.setVisible(true);
	}
	void init()
	{
		this.setTitle("Customer shcedule Maintenance(1)");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
		
		this.ScheduleOnDateButton = new JButton("Schedule on Date");
		this.goBackButton = new JButton("Go Back");
		this.date1Radio = new JRadioButton(date1Text);
		this.date2Radio = new JRadioButton(date2Text);
		this.mechanicFild = new JTextField(this.mechanic_dao.getMechanic(this.service.getEid()).getName());
		
		this.add(date1Radio);
		this.add(date2Radio);
		this.add(ScheduleOnDateButton);
		this.add(goBackButton);
		this.add(mechanicFild);
		
		
		this.ScheduleOnDateButton.addActionListener(this);
		this.goBackButton.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.ScheduleOnDateButton)
		{
			if(this.date1Radio.isSelected())
			{
				this.selectedDate = this.date1Radio.getText();
				service.setService_date(date1);
			}
			if(this.date2Radio.isSelected())
			{
				this.selectedDate = this.date2Radio.getText();
				service.setService_date(date2);
			}
			//gui show message
		
			//TODO record shchedule on the selectedDate, customerID
			service.setService_end_date(schedule_service.CalculateServiceEndDateFromService(service));
			this.schedule_service.ScheduleService(service);
			new Customer_Schedule_Service(this.customerID);
		}
		
		else if(e.getSource() == this.goBackButton)
		{
			new Customer_Schedule_Maintenance_Page1(this.customerID, this.service);
		}
		this.dispose();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		new Customer_Schedule_Maintenance_Page2(args[0],args[1],args[2],args[3],args[4],args[5]);
		
	}

}
