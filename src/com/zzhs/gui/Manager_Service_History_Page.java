package com.zzhs.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

import com.zzhs.dao.Schedule_ServiceDao;

public class Manager_Service_History_Page extends JFrame implements ActionListener 
{
	int eid;
	JButton button;
	JTextArea show_service_history;
	
	public Manager_Service_History_Page(int eid)
	{
		this.eid = eid;
		this.init();
		this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		this.setTitle("Manager Service History Page");
		this.setLayout(new FlowLayout());
		this.setSize(300, 300);
		this.setLocation(400, 500);
		this.button = new JButton("Go Back");
		this.show_service_history = new JTextArea(6, 13);
		int sid = 0;
		Schedule_ServiceDao schedule_service = new Schedule_ServiceDao();
		List<List<String>> service_history;
		sid = schedule_service.getServiceCenterIdFromManager(eid);
		service_history = schedule_service.viewServiceHistoryManager(sid);
		String[] title = {"service id:", "license plate:", "service type:", "Service Start Date:", "Mechanic Name:", "Service End Date:", "Service Status:"}; 
		int i = 0;
		while(i < service_history.size()){
			int j = 0;
			while(j < service_history.get(i).size()){
				this.show_service_history.append(title[j] + service_history.get(i).get(j) + " ");
				j = j + 1;
			}
			this.show_service_history.append("\n");
			i = i + 1;
		}
		this.button.addActionListener(this);
		this.add(button);
		this.add(new JLabel("Disply service history:"));
		this.add(show_service_history);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		new Manager_Landing_Page(eid);
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		new Manager_Service_History_Page(950932130);
	}
}

