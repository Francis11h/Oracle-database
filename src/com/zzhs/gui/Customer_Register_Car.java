package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.KeySelector.Purpose;

import com.zzhs.dao.VehicleDao;
import com.zzhs.dao.OwnDao;
import com.zzhs.entity.Vehicle;
import com.zzhs.entity.Own;

public class Customer_Register_Car extends JFrame implements ActionListener {

	int customerID;
	
	JButton registerButton;
	JButton cancelButton;
	JTextField licencePlateFild;
	JTextField mileageFild;
	JTextField makeFild;
	JTextField modelFild;
	JTextField yearFild;
	JTextField puchaseDateFild;
	JTextField lastServiceTypeFild;
	JTextField lastServiceDateFild;

	JLabel lblLicencePlate;
	JLabel lblMileage;
	JLabel lblMake;
	JLabel lblModel;
	JLabel lblYear;
	JLabel lblPurchaseDate;
	JLabel lbllastServiceType;
	JLabel lblLastServiceDate;

	public Customer_Register_Car(int customerID)
	{
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.customerID = customerID;
	}
	void init()
	{
		//TODO
		//search customer's data(customerID)
		//return Name,address,email address,phone number, list of all cars
		this.setTitle("Customer Register Car");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 400);
		
		this.licencePlateFild = new JTextField();
		this.mileageFild = new JTextField();
		this.makeFild = new JTextField();
		this.modelFild = new JTextField();
		this.yearFild = new JTextField();
		this.puchaseDateFild = new JTextField();
		this.lastServiceTypeFild = new JTextField();
		this.lastServiceDateFild = new JTextField();

		this.lblLicencePlate = new JLabel("Licenceplate");
		this.lblMileage = new JLabel("Mileage");
		this.lblMake = new JLabel("Make");
		this.lblModel = new JLabel("Model");
		this.lblYear = new JLabel("Year");
		this.lblPurchaseDate = new JLabel("Purchase Date(dd/MM/yyyy)");
		this.lbllastServiceType = new JLabel("Last Service Type");
		this.lblLastServiceDate = new JLabel("Last Service Date(dd/MM/yyyy)");
		
		this.registerButton = new JButton("Register");
		this.cancelButton = new JButton("Cancel");
		
		this.add(registerButton);
		this.add(cancelButton);
		
		this.add(lblLicencePlate);
		this.add(licencePlateFild);
		this.add(lblMileage);
		this.add(mileageFild);
		this.add(lblMake);
		this.add(makeFild);
		this.add(lblModel);
		this.add(modelFild);
		this.add(lblYear);
		this.add(yearFild);
		this.add(lblPurchaseDate);
		this.add(puchaseDateFild);
		this.add(lbllastServiceType);
		this.add(lastServiceTypeFild);
		this.add(lblLastServiceDate);
		this.add(lastServiceDateFild);
		
		
	
		this.registerButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == this.registerButton)
		{
			if (this.addCar() && this.addOwn()) {
				new Customer_Landing(customerID);
				this.dispose();
			} else {
				new Popdialog("register failed");
			}
			
		}
		else if(e.getSource() == this.cancelButton)
		{
			new Customer_Landing(customerID);
			this.dispose();
		}
	}
	
	private boolean addCar() {
		VehicleDao vehicleDao = new VehicleDao();
		Vehicle newCar = null;
		try {
			newCar = new Vehicle(licencePlateFild.getText(), 
					makeFild.getText(), 
					modelFild.getText(), 
					Integer.parseInt(yearFild.getText()), 
					Integer.parseInt(mileageFild.getText()),
					lastServiceTypeFild.getText(),
					new Date(new SimpleDateFormat("dd/MM/yyyy").parse(lastServiceDateFild.getText()).getTime()),
					new Date(new SimpleDateFormat("dd/MM/yyyy").parse(puchaseDateFild.getText()).getTime()));
		} catch (NumberFormatException | ParseException e) {
			e.printStackTrace();
//			System.out.println(newCar.toString());
			return false;
		} 
		return vehicleDao.addCar(newCar);
	}
	
	private boolean addOwn() {
		OwnDao ownDao = new OwnDao();
		Own newOwn = new Own(customerID, licencePlateFild.getText());
		return ownDao.addOwn(newOwn);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Customer_Register_Car(47);
		
	}

}
