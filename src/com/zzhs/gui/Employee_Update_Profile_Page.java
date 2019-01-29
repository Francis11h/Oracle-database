package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.zzhs.dao.EmployeeMRDao;

public class Employee_Update_Profile_Page extends JFrame implements ActionListener{

	int employeeID;
	boolean isManager;
	JButton nameButton;
	JButton addressButton;
	JButton emailButton;
	JButton phoneNumberButton;
	
	JButton passwordButton;
	JButton goBackButton;
	

	public Employee_Update_Profile_Page(int employeeID, boolean isManager)
	{
		this.employeeID = employeeID;
		this.isManager = isManager;
		this.init();
		this.setVisible(true);
		
		
	}
	void init()
	{
		this.setTitle("Employee Update profile");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 300);
	
		this.nameButton = new JButton("Name");
		this.addressButton = new JButton("Address");
		this.emailButton = new JButton("Email Address");
		this.passwordButton = new JButton("Password");
		
		this.phoneNumberButton = new JButton("Phone Number");
		
		this.goBackButton = new JButton("Go Back");
	
		this.add(nameButton);
		this.add(addressButton);
		this.add(emailButton);
		this.add(phoneNumberButton);
		this.add(passwordButton);
		
		this.add(goBackButton);
		
		this.nameButton.addActionListener(this);
		this.addressButton.addActionListener(this);
		this.passwordButton.addActionListener(this);
		
		this.emailButton.addActionListener(this);
		this.phoneNumberButton.addActionListener(this);

		this.goBackButton.addActionListener(this);
		
	
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == this.nameButton)
		{
			String newName = JOptionPane.showInputDialog(this,"Input you new name");
			if(!newName.isEmpty()){
				EmployeeMRDao employeeMRDao = new EmployeeMRDao();
				if (employeeMRDao.updateEmployeeMR(employeeID, "name", newName)) {
					
				} else {
					new Popdialog("update failed");
				};
			}	
		}
		else if(e.getSource() == this.addressButton)
		{
			String newAddress = JOptionPane.showInputDialog(this,"Input you new address");
			if(!newAddress.isEmpty()){
				EmployeeMRDao employeeMRDao = new EmployeeMRDao();
				if (employeeMRDao.updateEmployeeMR(employeeID, "address", newAddress)) {
					
				} else {
					new Popdialog("update failed");
				};
			}
		}
		else if(e.getSource() == this.emailButton)
		{
			String newEmail = JOptionPane.showInputDialog(this,"Input you new Email");
			if(!newEmail.isEmpty()){
				EmployeeMRDao employeeMRDao = new EmployeeMRDao();
				if (employeeMRDao.updateEmployeeMR(employeeID, "email", newEmail)) {
					
				} else {
					new Popdialog("update failed");
				};
			}
		}
		else if(e.getSource() == this.passwordButton)
		{
			String newpassword = JOptionPane.showInputDialog(this,"Input you new passeword");
			if(!newpassword.isEmpty()){
				EmployeeMRDao employeeMRDao = new EmployeeMRDao();
				if (employeeMRDao.updateEmployeeMR(employeeID, "password", newpassword)) {
					
				} else {
					new Popdialog("update failed");
				};
			}
		}
		else if (e.getSource() == this.phoneNumberButton)
		{
			String newPhoneNumber = JOptionPane.showInputDialog(this,"Input you new phone number");
			if(!newPhoneNumber.isEmpty()){
				EmployeeMRDao employeeMRDao = new EmployeeMRDao();
				if (employeeMRDao.updateEmployeeMR(employeeID, "phone", newPhoneNumber)) {
					
				} else {
					new Popdialog("update failed");
				};
			}
		}

		else if(e.getSource() == this.goBackButton)
		{
			new Employee_Profile_Page(employeeID, isManager);
			this.dispose();
		}
		
		
		
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Employee_Update_Profile_Page(950932130, true);
		
	}
}
