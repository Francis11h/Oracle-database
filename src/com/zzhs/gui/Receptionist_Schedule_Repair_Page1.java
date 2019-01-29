package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.zzhs.entity.Schedule_Service;
import com.zzhs.dao.Schedule_ServiceDao;
import com.zzhs.dao.InventoryDao;
import java.sql.Timestamp;

public class Receptionist_Schedule_Repair_Page1 extends JFrame implements ActionListener
{
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	JButton button5;
	JButton button6;
	JButton button7;
	JButton button8;
	int employeeID;
	Schedule_Service service;
	Schedule_ServiceDao schedule_service = new Schedule_ServiceDao();
	InventoryDao inventory = new InventoryDao();
	Timestamp first_date;
	Timestamp second_date;
	
	public Receptionist_Schedule_Repair_Page1(int employeeID, Schedule_Service service)
	{
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.employeeID = employeeID;
        this.service = service;
	}
	
	void init()
	{
		this.setTitle("Receptionist Schedule Repair Page 1");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.button1 = new JButton("Engine Knock");
		this.button2 = new JButton("Car drifts in particular direction");
		this.button3 = new JButton("Battery does not hold charge");
		this.button4 = new JButton("Black/unclean exhaust");
		this.button5 = new JButton("A/C-Heater not working");
		this.button6 = new JButton("Headlamps/Tail lamps not working");
		this.button7 = new JButton("Check engine light");
		this.button8 = new JButton("Go Back");
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.button3.addActionListener(this);
		this.button4.addActionListener(this);
		this.button5.addActionListener(this);
		this.button6.addActionListener(this);
		this.button7.addActionListener(this);
		this.button8.addActionListener(this);
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.add(button4);
		this.add(button5);
		this.add(button6);
		this.add(button7);
		this.add(button8);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		first_date = schedule_service.GetTwoEarlestDate(service.getEid(), service.getService_type());
		second_date = schedule_service.AddHalfHour(first_date);
		if(e.getSource() == this.button8)
		{
			new Receptionist_Schedule_Service_Page(this.employeeID);
		}
		else if(e.getSource() == this.button7)
		{
			/* create the dianostic report and caculate the repair date.
			 * if can not find a service date
			 * place an order and show messages
			 * then go back to Receptionist_Schedule_Service_Page
			 * else calculate service date and report and pass as params  
			 */
			this.service.setRsvid(25);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) 
			{
				new Receptionist_Schedule_Repair_Page2(this.employeeID, this.service, this.first_date, this.second_date);
			}
			else 
			{
				
			}
		}
		else if(e.getSource() == this.button6) 
		{
			this.service.setRsvid(24);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) 
			{
				new Receptionist_Schedule_Repair_Page2(this.employeeID, this.service, this.first_date, this.second_date);
			}
			else 
			{
				
			}
		}
		else if(e.getSource() == this.button5) 
		{
			this.service.setRsvid(23);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) 
			{
				new Receptionist_Schedule_Repair_Page2(this.employeeID, this.service, this.first_date, this.second_date);
			}
			else 
			{
				
			}
		}
		else if(e.getSource() == this.button4) 
		{
			this.service.setRsvid(22);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) 
			{
				new Receptionist_Schedule_Repair_Page2(this.employeeID, this.service, this.first_date, this.second_date);
			}
			else 
			{
				
			}
		}
		else if(e.getSource() == this.button3) 
		{
			this.service.setRsvid(21);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) 
			{
				new Receptionist_Schedule_Repair_Page2(this.employeeID, this.service, this.first_date, this.second_date);
			}
			else 
			{
				
			}
		}
		else if(e.getSource() == this.button2) 
		{
			this.service.setRsvid(20);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) 
			{
				new Receptionist_Schedule_Repair_Page2(this.employeeID, this.service, this.first_date, this.second_date);
			}
			else 
			{
				
			}
		}
		else 
		{
			this.service.setRsvid(19);
			if(inventory.partIsEnough(first_date, service.getSid(), service.getService_type(), service.getRsvid(), service.getLicence_id())) 
			{
				new Receptionist_Schedule_Repair_Page2(this.employeeID, this.service, this.first_date, this.second_date);
			}
			else 
			{
				
			}
		}
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		//new Receptionist_Schedule_Repair_Page1(12345);
	}
}
