package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;

import com.zzhs.dao.InventoryDao;

import java.awt.event.*;

public class Manager_Inventory_Page extends JFrame implements ActionListener 
{
	int eid;
	JButton button;
	JTextArea show_part_info;
	JScrollPane sPane;
	public Manager_Inventory_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Manager Inventory");
		this.setLayout(new FlowLayout());
		this.setSize(600, 1000);
		this.setLocation(400, 500);
		this.button = new JButton("Go Back");
		this.show_part_info = new JTextArea();
		this.button.addActionListener(this);
		InventoryDao inventoryDao = new InventoryDao();
		this.show_part_info.append(inventoryDao.listInventory(eid));
		this.show_part_info.setLineWrap(true);
		this.show_part_info.setSize(550, 950);
		this.sPane = new JScrollPane(show_part_info);
		this.sPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.sPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(button);
		this.add(new JLabel("part information:"));
		this.add(sPane);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		new Manager_Landing_Page(eid);
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		new Manager_Inventory_Page(950932130);
	}
}
