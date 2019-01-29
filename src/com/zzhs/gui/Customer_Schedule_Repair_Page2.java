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
import com.zzhs.dao.InventoryDao;
import com.zzhs.dao.MechanicDao;
import java.sql.Timestamp;

public class Customer_Schedule_Repair_Page2 extends JFrame implements ActionListener{


	int customerID;
	Schedule_Service service;
	Mechanic mechanic;
	Schedule_ServiceDao schedule_service = new Schedule_ServiceDao();
	MechanicDao mechanic_dao = new MechanicDao();
	Timestamp date1;
	Timestamp date2;
	String mechanicName;
	String date1Text;
	String date2Text;
	String selectedDate;
	JButton repairOnDateButton;
	JButton goBackButton;
	JRadioButton date1Radio;
	JRadioButton date2Radio;
	JTextField mechanicFild;
	JTextField diagnosticReportField;
	
	public Customer_Schedule_Repair_Page2(int customerID, Schedule_Service service, Timestamp date1, Timestamp date2)
	{
		this.customerID = customerID;
		this.service = service;
		this.date1Text = date1.toString();
		this.date2Text = date2.toString();
		this.date1 = date1;
		this.date2 = date2;
		this.mechanicName = mechanic_dao.getMechanic(service.getEid()).getName();
		this.init();
		this.setVisible(true);
	}
	void init()
	{
		this.setTitle("Customer shcedule Maintenance(1)");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
		
		this.repairOnDateButton = new JButton("Schedule on Date");
		this.goBackButton = new JButton("Go Back");
		this.date1Radio = new JRadioButton(this.date1Text);
		this.date2Radio = new JRadioButton(this.date2Text);
		this.mechanicFild = new JTextField(this.mechanicName);
		this.diagnosticReportField = new JTextField(this.schedule_service.GetReportFromRsvid(this.service.getRsvid()));
		
		this.add(diagnosticReportField);
		this.add(mechanicFild);
		this.add(date1Radio);
		this.add(date2Radio);
		this.add(repairOnDateButton);
		this.add(goBackButton);
		
		
		this.repairOnDateButton.addActionListener(this);
		this.goBackButton.addActionListener(this);
		
		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		
		if(e.getSource() == this.repairOnDateButton)
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
		
			//TODO Create a new service record for repair service on the chosen data
			//(this.selectedDate,customerID,licencePlate,currentMileage,mechanicName,diagnosticReport)
			service.setService_end_date(schedule_service.CalculateServiceEndDateFromService(service));
			schedule_service.ScheduleService(service);
			new Customer_Schedule_Service(this.customerID);
		}
		
		else if(e.getSource() == this.goBackButton)
		{
			new Customer_Schedule_Repair_Page1(this.customerID, this.service);
		}
		this.dispose();
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		new Customer_Schedule_Repair_Page2(args[0],args[1],args[2],args[3],args[4],args[5]);
		//new Customer_Schedule_Repair_Page2(Integer.parseInt(args[0]),args[1],args[2],args[3],args[4],args[5]);
		//new Customer_Schedule_Repair_Page2(Integer.parseInt(args[0]),args[1],args[2],args[3],args[4],args[5],args[6]);
		
	}


}
