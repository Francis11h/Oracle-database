package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.sql.Timestamp;
import com.zzhs.entity.Schedule_Service;
import com.zzhs.dao.Schedule_ServiceDao;
import com.zzhs.entity.Mechanic;
import com.zzhs.dao.MechanicDao;

public class Receptionist_Schedule_Maintenance_Page2 extends JFrame implements ActionListener
{
	JButton button1;
	JButton button2;
	JTextField mechanic;
	int employeeID;
	Timestamp date1;
	Timestamp date2;
	Schedule_Service service;
	MechanicDao mechanic_dao = new MechanicDao();
	
	public Receptionist_Schedule_Maintenance_Page2(int employeeID, Schedule_Service service, Timestamp first_date, Timestamp second_date)
	{
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.employeeID = employeeID;
        this.date1 = first_date;
        this.date2 = second_date;
        this.service = service;
		this.init();
		this.setVisible(true);
	}
	
	void init()
	{
		this.setTitle("Receptionist Schedule Maintenance Page 2");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.button1 = new JButton("Schedule on Date");
		this.button2 = new JButton("Go Back");
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.mechanic = new JTextField(this.mechanic_dao.getMechanic(this.service.getEid()).getName());
		this.add(button1);
		this.add(button2);
		this.add(new JLabel("Mechanic Name:"));
		this.add(mechanic);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.button1)
		{
			// pass the dates and type as params 
			new Date_Choose_Page(this.employeeID, this.service, this.date1, this.date2);
		}
		else
		{
			new Receptionist_Schedule_Maintenance_Page1(this.employeeID, this.service);
		}
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		//new Receptionist_Schedule_Maintenance_Page2(1234);
	}
}
