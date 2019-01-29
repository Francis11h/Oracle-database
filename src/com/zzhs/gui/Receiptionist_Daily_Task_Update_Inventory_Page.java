package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.Schedule_ServiceDao;
import com.zzhs.entity.Schedule_Service;

import net.bytebuddy.asm.Advice.This;

import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Receiptionist_Daily_Task_Update_Inventory_Page extends JFrame implements ActionListener 
{
	int eid;
	JButton goback;
	JButton update;
	JLabel lblChooseDate;
	JTextField chooseDateFild;
	JTextArea output;
	
	public Receiptionist_Daily_Task_Update_Inventory_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Receptionist Daily Task Update Inventory Page");
		this.setLayout(new GridLayout(0,1));
		this.setSize(400, 400);
		this.setLocation(400, 500);
		
		this.update = new JButton("Update Invertory");
		this.goback = new JButton("Go Back");
		this.output = new JTextArea(6, 13);
		this.lblChooseDate = new JLabel("Enter a Date to be Update(dd/MM/yyyy):");
		this.chooseDateFild = new JTextField();
		this.add(lblChooseDate);
		this.add(chooseDateFild);
		this.add(update);
		this.add(goback);
		this.add(new JLabel("task message:"));
		this.add(output);
		this.goback.addActionListener(this);
		this.update.addActionListener(this);

	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.goback) {
			new Receptionist_Landing_Page(eid);
//			System.out.println("goback");
			this.dispose();
		} 
		if (e.getSource() == this.update){
			Schedule_ServiceDao schedule_ServiceDao = new Schedule_ServiceDao();
			Date date;
			try {
				date = new Date(new SimpleDateFormat("dd/MM/yyyy").parse(chooseDateFild.getText()).getTime());
//				System.out.println("I am here1");
				this.output.setText("Updated date " + date.toString() + ": \n" + schedule_ServiceDao.daily_update_inventory(date, eid));
			} catch (Exception e2) {
//				System.out.println("I am here");
				date = new Date(System.currentTimeMillis());
				this.output.setText("Entered date is not valid, Updated todays date " + date.toString() + ": \n" + schedule_ServiceDao.daily_update_inventory(date, eid));
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Receiptionist_Daily_Task_Update_Inventory_Page(634622236);
	}
}
