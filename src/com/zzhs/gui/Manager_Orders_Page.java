package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Manager_Orders_Page extends JFrame implements ActionListener 
{
	int eid;
	JButton button1;
	JButton button2;
	JButton button3;
	
	public Manager_Orders_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Manager Orders Page");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.button1 = new JButton("Order History");
		this.button2 = new JButton("New Order");
		this.button3 = new JButton("Go Back");
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.button3.addActionListener(this);
		this.add(button1);
		this.add(button2);
		this.add(button3);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.button1)
		{
			new Manager_Order_History_Page(eid);
		}
		else if(e.getSource() == this.button2)
		{
			new Manager_New_Order_Page(eid);
		}
		else
		{
			new Manager_Landing_Page(eid);
		}
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		new Manager_Orders_Page(950932130);
	}
}
