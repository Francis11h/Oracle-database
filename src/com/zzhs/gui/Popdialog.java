package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Popdialog extends JFrame implements ActionListener {
	
	JButton okButton;
	String message;
	JLabel lblMsg;

	public Popdialog(String msg)
	{
		this.message = msg;
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}
	void init()
	{
		this.setTitle("information");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 400);
	
		this.lblMsg = new JLabel(this.message);
	
		this.okButton = new JButton("OK");
		
		this.add(lblMsg);
		this.add(okButton);
		
		this.okButton.addActionListener(this);		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == this.okButton)
		{
			this.dispose();
		}
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Popdialog("this is cool");
		
	}

}
