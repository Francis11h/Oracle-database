package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Receptionist_Invoices_Page extends JFrame implements ActionListener 
{
	int eid;
	JButton button1;
	JButton button2;
	JTextField input_email;
	JTextArea show_service_detail;
	
	public Receptionist_Invoices_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Receptionist Invoices Page");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.button1 = new JButton("Go Back");
		this.button2 = new JButton("Submit");
		this.input_email = new JTextField(10);
		this.show_service_detail = new JTextArea(6, 13);
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.add(button1);
		this.add(button2);
		this.add(new JLabel("Input customer email:"));
		this.add(input_email);
		this.add(new JLabel("Service details:"));
		this.add(show_service_detail);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.button1)
		{
			new Receptionist_Landing_Page(eid);
			this.dispose();
		}
		else
		{
			// use the email to find the detailed info and display it
		}
	}
	
	public static void main(String[] args)
	{
		new Receptionist_Invoices_Page(634622236);
	}
}
