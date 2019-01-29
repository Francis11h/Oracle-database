package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.MechanicDao;
import com.zzhs.dao.Schedule_ServiceDao;
import com.zzhs.entity.Schedule_Service;

import java.awt.event.*;
import java.sql.Timestamp;

public class Receptionist_Reschedule_Service_Page2 extends JFrame implements ActionListener 
{
	int eid;
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
	
	
	public Receptionist_Reschedule_Service_Page2(int eid, Schedule_Service service, Timestamp date1, Timestamp date2)
	{
		this.service = service;
		this.date1 = date1;
		this.date2 = date2;
		this.mechanicName = this.mechanic_dao.getMechanic(this.service.getEid()).getName();
		this.init();
		this.setVisible(true);	
	}
	void init()
	{
		this.setTitle("Receptionist Reschedule Service Page2");
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
			new Receptionist_Landing_Page(eid);
		}
		
		else if(e.getSource() == this.goBackButton)
		{
			new Receptionist_Reschedule_Service_Page1(this.eid);
		}
		this.dispose();
		
	}
	
	public static void main(String[] args)
	{
		//new Receptionist_Reschedule_Service_Page2(634622236);
	}
}
