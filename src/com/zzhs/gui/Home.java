package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.hibernate.SessionFactory;

import com.zzhs.dao.Dbconnection;
import com.zzhs.dao.Factory;

public class Home extends JFrame implements ActionListener
{
	JButton signupButton;
	JButton loginButton;
	JButton exitButton;
	Connection connection;
	public Home()
	{
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Home");
		this.setLayout(null);
		this.setSize(400, 300);
		this.setLocation(400,500);
		this.signupButton =   new JButton("Sign up");
		
		this.loginButton= new JButton("log in");
		this.exitButton =  new JButton("exit");
		
		this.signupButton.setBounds(132, 85, 117, 29);
		this.loginButton.setBounds(132, 130,117,29);
		this.exitButton.setBounds(132, 155, 117, 29);
		
		getContentPane().add(signupButton);
		getContentPane().add(loginButton);
		getContentPane().add(exitButton);
		
		this.signupButton.addActionListener(this);
		this.loginButton.addActionListener(this);
		this.exitButton.addActionListener(this);
		
		//setup db connection first
		connection = Dbconnection.getConnection();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.signupButton)
		{
			new Sign_Up();
		}
		else if(e.getSource() == this.loginButton)
		{
			new Login();
		}
		else if(e.getSource() == this.exitButton)
		{
			try {
				this.connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(ABORT);
		}
		this.dispose();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		new Home();
	}

	

}
