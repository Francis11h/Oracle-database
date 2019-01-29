package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;
import com.zzhs.dao.PayrollDao;
import java.awt.event.*;

public class Manager_Payroll_Page extends JFrame implements ActionListener 
{
	int employeeID;
	JButton button1;
	JButton button2;
	JTextField input_emp_id;
	JTextArea show_paycheck;
	
	public Manager_Payroll_Page(int employeeID)
	{
		this.employeeID = employeeID;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("EID: " + employeeID + "   Manager Payroll Page");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 500);
		this.button1 = new JButton("Go Back");
		this.button2 = new JButton("Submit");
		this.input_emp_id = new JTextField(10);
		this.show_paycheck = new JTextArea(6, 13);
		this.show_paycheck.setLineWrap(true);
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.add(button1);
		this.add(new JLabel("input employee ID:"));
		this.add(input_emp_id);
		this.add(button2);
		this.add(new JLabel("Display paycheck:"));
		this.add(show_paycheck);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.button1)
		{
			new Manager_Landing_Page(employeeID);
			this.dispose();
		}
		else
		{
			try {
				int eid = Integer.parseInt(input_emp_id.getText());
				PayrollDao payrollDao = new PayrollDao();
				this.show_paycheck.setText(payrollDao.generatePayroll(eid, employeeID));
				
			} catch (NumberFormatException e2) {
				new Popdialog("Please enter number only.");
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Manager_Payroll_Page(950932130);
		//test recep: 634622236, mech, 557279280
	}
}
