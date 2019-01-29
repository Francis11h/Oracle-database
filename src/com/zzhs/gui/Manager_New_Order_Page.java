package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.OrderDao;

import java.awt.event.*;

public class Manager_New_Order_Page extends JFrame implements ActionListener  
{
	int eid;
	JButton button1;
	JButton button2;
	JTextField input_part_ID;
	JTextField input_quantity;
	JTextArea show_confirmation_message;
	
	public Manager_New_Order_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Manager New Order Page");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.button1 = new JButton("Place Order");
		this.button2 = new JButton("Go Back");
		this.input_part_ID = new JTextField(10);
		this.input_quantity = new JTextField(10);
		this.show_confirmation_message = new JTextArea(6, 13);
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.add(button1);
		this.add(button2);
		this.add(new JLabel("input part ID:"));
		this.add(input_part_ID);
		this.add(new JLabel("input quantity:"));
		this.add(input_quantity);
		this.add(new JLabel("show confirmation message:"));
		this.add(show_confirmation_message);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.button1)
		{
			OrderDao orderDao = new OrderDao();
			this.show_confirmation_message.setText(orderDao.addOrder(eid, input_part_ID.getText(), input_quantity.getText()));
		}
		else
		{
			new Manager_Orders_Page(eid);
			this.dispose();
		}
	}
	
	public static void main(String[] args)
	{
		new Manager_New_Order_Page(950932130);
	}
}
