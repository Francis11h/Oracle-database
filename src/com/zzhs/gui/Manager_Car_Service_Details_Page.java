package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.MaitServiceDao;

import java.awt.event.*;

public class Manager_Car_Service_Details_Page extends JFrame implements ActionListener 
{
	int eid;
	JButton button;
	JTextArea show_service_detail;
	
	public Manager_Car_Service_Details_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Manager Car Service Details Page");
		this.setLayout(new FlowLayout());
		this.setSize(1000, 800);
		this.setLocation(400, 500);
		this.button = new JButton("Go Back");
		this.show_service_detail = new JTextArea(6, 13);
		MaitServiceDao maitServiceDao = new MaitServiceDao();
		this.show_service_detail.append(maitServiceDao.listCarService());
		this.button.addActionListener(this);
		this.add(button);
		this.add(new JLabel("Disply car service detail:"));
		this.add(show_service_detail);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		new Manager_Landing_Page(eid);
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		new Manager_Car_Service_Details_Page(950932130);
	}
}
