package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.NotificationDao;
import com.zzhs.dao.OrderDao;

import java.awt.event.*;

public class Receptionist_Daily_Task_Record_Deliveries_Page extends JFrame implements ActionListener
{
	int eid;
	JButton button1;
	JButton button2;
	JTextArea orderhis;
	JTextArea output;
	JTextField input_order_ID;
	OrderDao orderDao = new OrderDao();
	
	public Receptionist_Daily_Task_Record_Deliveries_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Receptionist_Daily_Task_Record_Deliveries_Page");
		this.setLayout(new FlowLayout());
		this.setSize(800, 400);
		this.setLocation(400, 500);
		this.button1 = new JButton("Go Back");
		this.button2 = new JButton("Submit");
		this.input_order_ID = new JTextField(10);
		this.output = new JTextArea(6, 13);
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.output.setLineWrap(true);
		this.orderhis = new JTextArea(6,13);
		this.orderhis.setText(orderDao.getOrder(eid));
		this.orderhis.setLineWrap(true);
		this.orderhis.setSize(750, 300);
		this.add(new JLabel("Order history:"));
		this.add(orderhis);
		this.add(new JLabel("Input order ID"));
		this.add(input_order_ID);
		this.add(button2);
		this.add(new JLabel("output"));
		this.add(output);
		this.add(button1);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.button1)
		{
			//check pending order if delay
			NotificationDao notificationDao = new NotificationDao();
			notificationDao.addNotification(eid);
			new Receptionist_Landing_Page(eid);
			this.dispose();
		}
		else
		{
			/*Run a task to update the status of any pending orders whose items have 
			 * arrived to complete and update their counts, and then show a message 
			 * displaying whether the task ran successfully or not.
			 */
			String out;
			try {
				int oid = Integer.parseInt(this.input_order_ID.getText());
				
				out = orderDao.updateOrder(oid, eid);
				if (out == null) {
					out = "No such order id or connection to server fail!";
				}
			} catch (Exception e2) {
				out = "input order id shall be only number.";
			}
			this.output.setText(out);
		}
	}
	
	public static void main(String[] args)
	{
		new Receptionist_Daily_Task_Record_Deliveries_Page(911639633);
	}
}
