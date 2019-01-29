package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.CarModelDao;

import java.awt.event.*;

public class Manager_New_Car_Model_Page extends JFrame implements ActionListener 
{
	int eid;
	JButton button1;
	JButton button2;
	JTextField input_make;
	JTextField input_model;
	JTextField input_year;
	JTextField input_serviceA_miles;
	JTextField input_serviceA_BasicServiceList;;
	JTextField input_serviceB_miles;
	JTextField input_serviceB_BasicServiceList;;
	JTextField input_serviceC_miles;
	JTextField input_serviceC_BasicServiceList;
	CarModelDao carModelDao = new CarModelDao();
	JTextArea basicService;
	
	public Manager_New_Car_Model_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
	}
	
	void init()
	{
		this.setTitle("Manager New Car Model Page");
		this.setLayout(new FlowLayout());
		this.setSize(800, 600);
		this.setLocation(400, 500);
		this.button1 = new JButton("Add Car");
		this.button2 = new JButton("Go Back");
		this.input_make = new JTextField(10);
		this.input_model = new JTextField(10);
		this.input_year = new JTextField(10);
		this.input_serviceA_miles = new JTextField(10);
		this.input_serviceA_BasicServiceList = new JTextField(10);
		this.input_serviceB_miles = new JTextField(10);
		this.input_serviceB_BasicServiceList = new JTextField(10);
		this.input_serviceC_miles = new JTextField(10);
		this.input_serviceC_BasicServiceList = new JTextField(10);
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		
		this.add(new JLabel("Basic service list:"));
		this.basicService = new JTextArea();
		this.basicService.setText(carModelDao.listBasicService());
		this.basicService.setLineWrap(true);
		this.basicService.setSize(750, 400);
		this.add(basicService);
		this.add(button1);
		this.add(button2);
		this.add(new JLabel("input make:"));
		this.add(input_make);
		this.add(new JLabel("input model:"));
		this.add(input_model);
		this.add(new JLabel("input year:"));
		this.add(input_year);
		this.add(new JLabel("input service A miles:"));
		this.add(input_serviceA_miles);
		this.add(new JLabel("input service A BasicServiceID(number with comma separate):"));
		this.add(input_serviceA_BasicServiceList);
		this.add(new JLabel("input service B miles:"));
		this.add(input_serviceB_miles);
		this.add(new JLabel("input service B BasicServiceID(number with comma separate):"));
		this.add(input_serviceB_BasicServiceList);
		this.add(new JLabel("input service C miles:"));
		this.add(input_serviceC_miles);
		this.add(new JLabel("input service C BasicServiceID(number with comma separate):"));
		this.add(input_serviceC_BasicServiceList);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.button1)
		{
			// add the new car to the database
			try {
				int year = Integer.parseInt(input_year.getText());
				int amile = Integer.parseInt(input_serviceA_miles.getText());
				int bmile = Integer.parseInt(input_serviceB_miles.getText());
				int cmile = Integer.parseInt(input_serviceC_miles.getText());
				int[] alist = toInt(input_serviceA_BasicServiceList.getText());
				int[] blist = toInt(input_serviceB_BasicServiceList.getText());
				int[] clist = toInt(input_serviceC_BasicServiceList.getText());
				
				if (carModelDao.addCarModel(input_make.getText(), input_model.getText(), year, 
						amile, bmile, cmile, alist, blist, clist)) {
					new Popdialog("add car model successful!");
				} else {
					new Popdialog("add car model fail, please retry.");
				}
			} catch (NumberFormatException e2) {
				new Popdialog("please check the input format.");
			}
		}
		else
		{
			new Manager_Landing_Page(eid);
			this.dispose();
		}
	}
	
	private int[] toInt(String string) throws NumberFormatException {
		String[] strings = string.split(",");
		int[] out = new int[strings.length];
		int i = 0;
		for (String s: strings) {
			out[i] = Integer.parseInt(s);
			i++;
		}
		return out;
	} 
	
	public static void main(String[] args)
	{
		new Manager_New_Car_Model_Page(950932130);
	}
}
