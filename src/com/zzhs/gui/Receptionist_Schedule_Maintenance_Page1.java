package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.zzhs.entity.Schedule_Service;
import com.zzhs.dao.Schedule_ServiceDao;
import com.zzhs.dao.InventoryDao;
import java.sql.Timestamp;

public class Receptionist_Schedule_Maintenance_Page1 extends JFrame implements ActionListener 
{
	JButton button1;
	JButton button2;
	int employeeID;
	Schedule_Service service;
	Schedule_ServiceDao schedule_service = new Schedule_ServiceDao();
	InventoryDao inventory = new InventoryDao();
	Timestamp first_date;
	Timestamp second_date;
	
	
	public Receptionist_Schedule_Maintenance_Page1(int employeeID, Schedule_Service service)
	{
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.employeeID = employeeID;
        this.service = service;
	}
	
	void init()
	{
		this.setTitle("Receptionist Schedule Maintenance Page 1");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.button1 = new JButton("Find Service Date");
		this.button2 = new JButton("Go Back");
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.add(button1);
		this.add(button2);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.button1)
		{
			/* if can not find a service date
			 * place an order and show messages
			 * then go back to Receptionist_Schedule_Service_Page
			 * else calculate service date and pass as param
			 */
			this.first_date = schedule_service.GetTwoEarlestDate(service.getEid(), service.getService_type());
			if(inventory.partIsEnough(this.first_date, service.getSid(), service.getService_type(), service.getSvid(), service.getLicence_id())) {
				this.second_date = schedule_service.AddHalfHour(this.first_date);
				new Receptionist_Schedule_Maintenance_Page2(this.employeeID, this.service, this.first_date, this.second_date);
			}
			else {
				// make an order
			}
			/* other operation */
		}
		else
		{
			new Receptionist_Schedule_Service_Page(this.employeeID);
		}
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		//new Receptionist_Schedule_Maintenance_Page1(1234);
	}
}
