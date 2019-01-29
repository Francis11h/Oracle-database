package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.sql.Timestamp;
import com.zzhs.entity.Schedule_Service;
import com.zzhs.dao.Schedule_ServiceDao;

public class Date_Choose_Page extends JFrame implements ActionListener 
{
	JComboBox<Object> comBox;
	JButton button;
	int employeeID;
	Timestamp date1;
	Timestamp date2;
	Schedule_Service service;
	Schedule_ServiceDao schedule_service = new Schedule_ServiceDao();
	
	public Date_Choose_Page(int employeeID, Schedule_Service service, Timestamp first_date, Timestamp second_date)
	{
        this.employeeID = employeeID;
        this.date1 = first_date;
        this.date2 = second_date;
        this.service = service;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Date Choose Page");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.comBox = new JComboBox<>();
		this.button = new JButton("Submit");
		this.comBox.addItem(this.date1.toString());
		this.comBox.addItem(this.date2.toString());
		this.button.addActionListener(this);
		this.add(comBox);
		this.add(button);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		/* Create new record on the choosen date.*/
		if(this.comBox.getSelectedItem() == this.date1.toString()) {
			this.service.setService_date(this.date1);
		}
		else {
			this.service.setService_date(this.date2);
		}
		this.service.setService_end_date(this.schedule_service.CalculateServiceEndDateFromService(service));
		this.schedule_service.ScheduleService(this.service);
		new Receptionist_Schedule_Service_Page(this.employeeID);
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		//new Date_Choose_Page(1234);
	}
}
