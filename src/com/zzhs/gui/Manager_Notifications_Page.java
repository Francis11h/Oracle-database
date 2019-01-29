package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.NotificationDao;

import java.awt.event.*;

public class Manager_Notifications_Page extends JFrame implements ActionListener 
{
	int eid;
	JButton button1;
	JButton button2;
	JTextField input_order_ID;
	JTextArea show_notification;
	
	public Manager_Notifications_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Manager Notifications Page");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.setSize(600, 500);
		this.button1 = new JButton("Submit");
		this.button2 = new JButton("Go Back");
		this.input_order_ID = new JTextField(10);
		this.show_notification = new JTextArea(6, 13);
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		NotificationDao notificationDao = new NotificationDao();
		this.show_notification.append(notificationDao.getNotification(eid));
		this.show_notification.setLineWrap(true);
		this.show_notification.setSize(550, 400);
		this.add(button1);
		this.add(button2);
		this.add(new JLabel("input order ID:"));
		this.add(input_order_ID);
		this.add(new JLabel("show notification information:"));
		this.add(show_notification);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.button1)
		{
			//search the order table and find the lines that has input order ID
			try {
				int order_id = Integer.parseInt(input_order_ID.getText());
				new Manager_Notifications_Detail_Page(order_id, eid);
				this.dispose();
			} catch (NumberFormatException e2) {
				new Popdialog("order id shall be number only.");
			}
		}
		else
		{
			new Manager_Landing_Page(eid);
			this.dispose();
		}
		
	}
	
	public static void main(String[] args)
	{
		new Manager_Notifications_Page(950932130);
	}
}
