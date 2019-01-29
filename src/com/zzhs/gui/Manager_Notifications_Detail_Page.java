package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.NotificationDao;

import java.awt.event.*;

public class Manager_Notifications_Detail_Page extends JFrame implements ActionListener 
{
	int oid;
	int eid;
	JButton button;
	JTextArea show_order;
	
	public Manager_Notifications_Detail_Page(int oid, int eid)
	{
		this.eid = eid;
		this.oid = oid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Manager Notifications Detail Page");
		this.setLayout(new FlowLayout());
		this.setLocation(400, 500);
		this.setSize(400, 500);
		this.button = new JButton("Go Back");
		this.show_order = new JTextArea(6, 13);
		this.button.addActionListener(this);
		NotificationDao notificationDao = new NotificationDao();
		this.show_order.append(notificationDao.getNotification(eid, oid));
		this.show_order.setLineWrap(true);
		this.show_order.setSize(350, 400);
		this.add(button);
		this.add(new JLabel("Display order detail:"));
		this.add(show_order);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		new Manager_Notifications_Page(eid);
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		new Manager_Notifications_Detail_Page(1, 950932130);
	}
}
