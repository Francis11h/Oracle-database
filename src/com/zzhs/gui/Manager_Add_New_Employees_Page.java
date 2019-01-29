package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.CustomerDao;
import com.zzhs.dao.EmployeeMRDao;
import com.zzhs.dao.MechanicDao;
import com.zzhs.dao.UserDao;
import com.zzhs.entity.Customer;
import com.zzhs.entity.EmployeeMR;
import com.zzhs.entity.Mechanic;

import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Manager_Add_New_Employees_Page extends JFrame implements ActionListener 
{
	int employeeID;
	JButton button1;
	JButton button2;
	JTextField input_name;
	JTextField input_address;
	JTextField input_email;
	JTextField input_phone;
	JComboBox<Object> input_role;
	JTextField input_start_date;
	JTextField input_compensation;
	JTextArea output_message;
	
	public Manager_Add_New_Employees_Page(int employeeID)
	{
		this.employeeID = employeeID;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Manager Add New Employees Page");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 500);
        this.button1 = new JButton("Add");
        this.button2 = new JButton("Go Back");
        this.input_name = new JTextField(10);
        this.input_address = new JTextField(10);
        this.input_email = new JTextField(10);
        this.input_phone = new JTextField(10);
        this.input_role = new JComboBox<>();
        this.input_start_date = new JTextField(10);
        this.input_compensation = new JTextField(10);
        this.output_message = new JTextArea(6, 13);
        
        this.button1.addActionListener(this);
        this.button2.addActionListener(this);
        
        this.input_role.addItem("receptionist");
        this.input_role.addItem("mechanic");
        
        this.add(button1);
        this.add(button2);
        
        this.add(new JLabel("input employee name:"));
        this.add(input_name);
        this.add(new JLabel("input employee address:"));
        this.add(input_address);
        this.add(new JLabel("input employee email:"));
        this.add(input_email);
        this.add(new JLabel("input employee phone number:"));
        this.add(input_phone);
        this.add(new JLabel("input employee role:"));
        this.add(input_role);
        this.add(new JLabel("input employee start date(dd/MM/yyyy):"));
        this.add(input_start_date);
        this.add(new JLabel("input employee compensation:"));
        this.add(input_compensation);
        this.add(new JLabel("new employee ID:"));
        this.add(output_message);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.button1)
		{
			/* create new employee record for this service center and display the new ID number. Set
			 * the default password 1 
			 */
			String nameString = this.input_name.getText();
			String emailString = this.input_email.getText();
			String addressString = this.input_address.getText();
			String phoneString = this.input_phone.getText();
			String roleString = this.input_role.getSelectedItem().toString();
			System.out.println(roleString);
			int compensation = 0;
			try{
				compensation = Integer.parseInt(this.input_compensation.getText());
			} catch (NumberFormatException e1) {
				new Popdialog("compensation shall be int number.");
			}
			Date startDate = null;
			try {
				startDate = new Date(new SimpleDateFormat("dd/MM/yyyy").parse(input_start_date.getText()).getTime());
			} catch (Exception e2) {
				new Popdialog("start date is not valid, please use format(dd/MM/yyyy).");
			}
			
			if ("receptionist".equals(roleString)) {
				EmployeeMRDao employeeMRDao = new EmployeeMRDao();
				UserDao userDao = new UserDao();
				if(nameString.isEmpty() || emailString.isEmpty() || addressString.isEmpty() 
						|| phoneString.isEmpty() || roleString.isEmpty() || compensation == 0 || startDate == null) {
					new Popdialog("email, name, password and address \n can not be empty, compensation can not be 0!");
					
				} else {
					if(!userDao.checkUserExists(emailString)) {
						EmployeeMR employeemr = new EmployeeMR(1, emailString, "1", roleString, nameString, addressString,
								phoneString, compensation, "", startDate);
						if (employeeMRDao.addRecip(employeemr) && employeeMRDao.addEmployeeWorkAt(employeeID, employeeMRDao.getEmployeeID(emailString))) {
							output_message.setText(employeeMRDao.getEmployeeMR(employeeMRDao.getEmployeeID(emailString)).toString());
						}
					} else {
						new Popdialog("The email has been used in this system, please choose another one.");
					}
				}
			} else {

				MechanicDao mechanicDao = new MechanicDao();
				UserDao userDao = new UserDao();
				if(nameString.isEmpty() || emailString.isEmpty() || addressString.isEmpty() 
						|| phoneString.isEmpty() || roleString.isEmpty() || compensation == 0 || startDate == null) {
					new Popdialog("email, name, password and address \n can not be empty, compensation can not be 0!");
					
				} else {
					if(!userDao.checkUserExists(emailString)) {
						Mechanic mechanic = new Mechanic(1, emailString, "1", nameString, addressString,
								phoneString, 0, compensation, "", startDate);
						if (mechanicDao.addMechanic(mechanic) && mechanicDao.addMechanicWorkAt(employeeID, mechanicDao.getEmployeeID(emailString))) {
							output_message.setText(mechanicDao.getMechanic(mechanicDao.getEmployeeID(emailString)).toString());
						}
					} else {
						new Popdialog("The email has been used in this system, please choose another one.");
					}
				}
			}
		}
		else
		{
			new Manager_Landing_Page(employeeID);
			this.dispose();
		}
	}
	
	public static void main(String[] args)
	{
		new Manager_Add_New_Employees_Page(950932130);
	}
}
