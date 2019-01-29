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
import com.zzhs.dao.OrderDao;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer_Schedule_Maintenance_Page1 extends JFrame implements ActionListener{

	int customerID;
	Schedule_Service service;
	Timestamp first_date;
	Timestamp second_date;
	JButton findServiceDateButton;
	JButton goBackButton;
	Schedule_ServiceDao schedule_service = new Schedule_ServiceDao();
	InventoryDao inventory = new InventoryDao();
	OrderDao orderDao = new OrderDao();
	
	public Customer_Schedule_Maintenance_Page1(int customerID, Schedule_Service service)
	{
		this.init();
		this.setVisible(true);
		this.customerID = customerID;
		this.service = service;
	}
	void init()
	{
		this.setTitle("Customer shcedule Maintenance(1)");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
		
		this.findServiceDateButton = new JButton("Find Service Date");
		this.goBackButton = new JButton("Go Back");
		
	
		this.add(findServiceDateButton);
		this.add(goBackButton);
		
		this.findServiceDateButton.addActionListener(this);
		this.goBackButton.addActionListener(this);
		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.findServiceDateButton)
		{
			//gui show message
			//TODO find 2 earliest service data(String customerID,String licencePlate, String currentMileage, String mechanic)
			//return available days(list or whatever)
			//this.date1 = 
			//this.data2 =
			//TODO place an order() if there are a lack of parts
			//return closest date that parts will be arrived
			this.first_date = schedule_service.GetTwoEarlestDate(service.getEid(), service.getService_type());
			if(inventory.partIsEnough(this.first_date, service.getSid(), service.getService_type(), service.getSvid(), service.getLicence_id())) {
				this.second_date = schedule_service.AddHalfHour(this.first_date);
				new Customer_Schedule_Maintenance_Page2(this.customerID, this.service, this.first_date, this.second_date);
			}
			else {
				// make an order
				// may be a list of parts with separated quantities, you may need do somthing like this:
//				Date latestAvaiableDate = new Date(0);
//				for (int pid : pids) {
//					Date expectedDeliveryDate = orderDao.addOrderAuto(sid, pid, qty);
//					if (latestAvaiableDate.compareTo(expectedDeliveryDate) < 0) {
//						latestAvaiableDate = expectedDeliveryDate;
//					}
//				}
			}
		}
		
		else if(e.getSource() == this.goBackButton)
		{
			new Customer_Schedule_Service(this.customerID);
		}
		this.dispose();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		new Customer_Schedule_Maintenance_Page1(args[0],args[1],args[2],args[3]);
		
	}

	
}
