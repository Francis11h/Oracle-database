package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Service_Choose_Page extends JFrame implements ActionListener 
{
	JTextField serviceID_input;
	JButton button;
	JTextArea show_service;
	int employeeID;
	
	public Service_Choose_Page(int employeeID)
	{
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.employeeID = employeeID;
	}
	
	void init()
	{
		this.setTitle("Service Choose Page");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.serviceID_input = new JTextField(10);
		this.button = new JButton("Submit");
		this.show_service = new JTextArea(6, 13);
		// add info to the show_service
		//this.show_service.append();
		this.button.addActionListener(this);
		this.add(new JLabel("Input the service ID you choose:"));
		this.add(serviceID_input);
		this.add(button);
		this.add(new JLabel("Service information here:"));
		this.add(show_service);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		// find two earliest dates and pass as params
		new Receptionist_Reschedule_Service_Page2(this.employeeID);
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		new Service_Choose_Page(1234);
	}
}
