package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.zzhs.dao.VehicleDao;
import com.zzhs.dao.EmployeeMRDao;
import com.zzhs.dao.OwnDao;
import com.zzhs.entity.Vehicle;
import com.zzhs.entity.EmployeeMR;
import com.zzhs.entity.Own;

public class Employee_View_Profile_Page extends JFrame implements ActionListener{

	int employeeID;
	boolean isManager;
	String name;
	String address;
	String email;
	String phoneNumber;
	String serviceCenter;
	String role;
	String startDate;
	String compensation;
	String compensationFreq;

	JButton goBackButton;
	
	JTextField employeeIDFild;
	JTextField nameFild;
	JTextField addressFild;
	JTextField emailFild;
	JTextField phoneNumberFild;
	JTextField serviceCenterFild;
	JTextField roleFild;
	JTextField startDateFild;
	JTextField compensationFild;
	JTextField compensationFreqFild;
	
	JLabel lblEmployeeID;
	JLabel lblName;
	JLabel lblAdress;
	JLabel lblEmail;
	JLabel lblPhoneNumber;
	JLabel lblServiceCenter;
	JLabel lblRole;
	JLabel lblStartDate;
	JLabel lblCompensation;
	JLabel lblCompensationFreq;
	

	public Employee_View_Profile_Page(int employeeID, boolean isManager)
	{
		this.employeeID = employeeID;
		this.isManager = isManager;
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}
	void init()
	{
		//TODO: search employee Profile data(employeeID)
		//return Name,address,email address,phone number, Service Center,Role,Start Date, Compensation($), Compensation frequency(Monthly/hourly))
		//this.name = name; etc...
		EmployeeMRDao employeeMRDao = new EmployeeMRDao();
		EmployeeMR employee = employeeMRDao.getEmployeeMR(employeeID);
		if (employee != null) {
			this.name = employee.getName();
			this.email = employee.getEmail();
			this.address = employee.getAddress();
			this.phoneNumber = employee.getPhone();
			this.role = employee.getUserType();
			this.startDate = employee.getStart_date().toString();
			this.compensation = Integer.toString(employee.getMonthlyPay());
		}
		this.serviceCenter = employeeMRDao.getEmployeeWorkAt(employeeID);
		
		this.setTitle("EID: " + employeeID + "  Employee View Profile");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 400);
		
		
		this.employeeIDFild = new JTextField(Integer.toString(employeeID));
		this.nameFild = new JTextField(name);
		this.addressFild = new JTextField(address);
		this.emailFild = new JTextField(email);
		this.phoneNumberFild = new JTextField(phoneNumber);
		this.serviceCenterFild = new JTextField(serviceCenter);
		this.roleFild = new JTextField(role);
		this.startDateFild = new JTextField(startDate);
		this.compensationFild = new JTextField(compensation);
		this.compensationFreqFild = new JTextField("Monthly");
		
		
		this.lblEmployeeID = new JLabel("Employee ID");
		this.lblName = new JLabel("Name");
		this.lblAdress = new JLabel("Address");
		this.lblEmail = new JLabel("Email Address");
		this.lblPhoneNumber = new JLabel("Phone Number");
		this.lblServiceCenter = new JLabel("Service Center");
		this.lblRole = new JLabel("Role");
		this.lblStartDate = new JLabel("Start Date(yyyy-MM-dd)");
		this.lblCompensation = new JLabel("Compensation");
		this.lblCompensationFreq = new JLabel("Compensation Frequency");
		
	
		this.goBackButton = new JButton("Go Back");
		
		
		this.add(goBackButton);
		
		this.add(lblEmployeeID);
		this.add(employeeIDFild);
		this.add(lblName);
		this.add(nameFild);
		this.add(lblAdress);
		this.add(addressFild);
		this.add(lblEmail);
		this.add(emailFild);
		this.add(lblPhoneNumber);
		this.add(phoneNumberFild);
		this.add(lblServiceCenter);
		this.add(serviceCenterFild);
		this.add(lblRole);
		this.add(roleFild);
		this.add(lblStartDate);
		this.add(startDateFild);
		this.add(lblCompensation);
		this.add(compensationFild);
		this.add(lblCompensationFreq);
		this.add(compensationFreqFild);
		
		
	
	
		this.goBackButton.addActionListener(this);
		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
	
		if(e.getSource() == this.goBackButton)
		{
			new Employee_Profile_Page(employeeID, isManager);
		}
		this.dispose();
	}
	
	
	public static void main(String[] args) {
		new Employee_View_Profile_Page(950932130, true);
		
	}

}
