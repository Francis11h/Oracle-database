package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.zzhs.dao.UserDao;
import com.zzhs.entity.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JLabel;

import javax.swing.JTextField;
import javax.swing.WindowConstants;

//test2
public class Login extends JFrame implements ActionListener
{
	JButton signinButton;
	JButton gobackButton;
	JTextField userID ;
	JPasswordField password;
	JLabel useridLabel;
	JLabel pwLabel;
	
	public Login()
	{
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	void init()
	{
		this.setTitle("Login");
		this.setLayout(null);
		this.setLocation(400, 500);
		this.setSize(400, 300);
		this.signinButton = new JButton("sign in");
		this.gobackButton = new JButton("Go back");
		this.userID = new JTextField();
		this.password = new JPasswordField();
		this.useridLabel = new JLabel("User ID");
		this.pwLabel = new JLabel("Password");
		
		signinButton.setBounds(164, 183, 117, 29);
		gobackButton.setBounds(164, 224, 117, 29);
		userID.setBounds(151, 30, 130, 26);
		password.setBounds(151, 92, 130, 26);
		useridLabel.setBounds(154, 16, 61, 16);
		pwLabel.setBounds(154, 79, 61, 16);
		
		
		this.add(userID);
		this.add(signinButton);
		this.add(gobackButton);
		this.add(password);
		this.add(useridLabel);
		this.add(pwLabel);
		
		this.signinButton.addActionListener(this);
		this.gobackButton.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.signinButton)
		{
			//validate credentials and recognize if user is a Manager, Receptionist, or Customer
			UserDao userDao = new UserDao();
			String userIDString = userID.getText();
			String passwordString = new String(password.getPassword());
			
			if (userIDString == null) {
				new Popdialog("userID can not be empty!");
			} else {
				String userType = userDao.authenticateUser(userIDString, passwordString);
				System.out.println("usertype is: " + userType);
				if (userType == null) {
					new Popdialog("user does not exists or password is not correct.");
				} else {
					int id = userDao.getId(userIDString, userType);
					if ("customer".equals(userType)) {
						new Customer_Landing(id);
						this.dispose();
					} else if("manager".equals(userType)) {
						new Manager_Landing_Page(id);
						this.dispose();
					} else if("receptionist".equals(userType)) {
						new Receptionist_Landing_Page(id);
						this.dispose();
					}
				}
			}
		}
		if(e.getSource() == this.gobackButton)
		{
			new Home();
			this.dispose();
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Login();
		
	}

	

}
