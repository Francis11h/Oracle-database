package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.OrderDao;

import java.awt.event.*;

public class Manager_Order_History_Page extends JFrame implements ActionListener 
{
	int eid;
	JButton button;
	JTextArea show_order_history;
	
	public Manager_Order_History_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Manager Order History Page");
		this.setLayout(new FlowLayout());
		this.setSize(700, 300);
		this.setLocation(400, 500);
		this.button = new JButton("Go Back");
		this.show_order_history = new JTextArea(6, 13);
		this.button.addActionListener(this);
		//select all the order info and show
		OrderDao orderDao = new OrderDao();
		this.show_order_history.append(orderDao.getOrder(eid));
		this.show_order_history.setLineWrap(true);
		this.show_order_history.setSize(600, 200);
		this.add(button);
		this.add(new JLabel("Order History:"));
		this.add(show_order_history);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		new Manager_Orders_Page(eid);
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		new Manager_Order_History_Page(950932130);
	}
}
