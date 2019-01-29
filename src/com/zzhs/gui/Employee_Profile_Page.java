package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.zzhs.dao.Dbconnection;

public class Employee_Profile_Page extends JFrame implements ActionListener{

	int employeeID;
	boolean isManager;
	JButton viewProfileButton;
	JButton updateProfileButton;
	JButton goBackButton;
	
	public Employee_Profile_Page(int employeeID, boolean isManager)
	{
		this.employeeID = employeeID;
		this.isManager = isManager;
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Employee Prfofile");
		this.setLayout(new GridLayout(0,1));
		this.setSize(400, 300);
		this.setLocation(400,500);
		this.viewProfileButton =   new JButton("View Profile");
		
		this.updateProfileButton= new JButton("Update Profile");
		this.goBackButton =  new JButton("go Back");
		
		
		
		getContentPane().add(viewProfileButton);
		getContentPane().add(updateProfileButton);
		getContentPane().add(goBackButton);
		
		this.viewProfileButton.addActionListener(this);
		this.updateProfileButton.addActionListener(this);
		this.goBackButton.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("buttonClicked");
		if(e.getSource() == this.viewProfileButton)
		{
			
			new Employee_View_Profile_Page(employeeID, isManager);
			
		}
		else if(e.getSource() == this.updateProfileButton)
		{
			new Employee_Update_Profile_Page(employeeID, isManager);
		}
		else if(e.getSource() == this.goBackButton)
		{
			if(isManager)
			{
				new Manager_Landing_Page(employeeID);
			}
			else {
				new Receptionist_Landing_Page(employeeID);
			}
			
		}
		this.dispose();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		new Employee_Profile_Page(950932130, true);
	}
}
