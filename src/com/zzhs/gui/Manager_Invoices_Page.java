package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Manager_Invoices_Page extends JFrame implements ActionListener 
{
	int eid;
	JButton button;
	JTextArea show_invoices;
	
	public Manager_Invoices_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Manager Invoices Page");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.button = new JButton("Go Back");
		this.show_invoices = new JTextArea(6, 13);
		// search service detail for all cars and show them
		//this.show_invoices.append("fuck you");
		this.button.addActionListener(this);
		this.add(button);
		this.add(new JLabel("Disply invoices:"));
		this.add(show_invoices);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		new Manager_Landing_Page(eid);
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		new Manager_Invoices_Page(950932130);
	}
}


