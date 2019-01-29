package com.zzhs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.zzhs.dao.CustomerDao;
import com.zzhs.dao.OwnDao;
import com.zzhs.dao.VehicleDao;
import com.zzhs.entity.Own;
import com.zzhs.entity.Vehicle;

public class Receptionist_Register_Car_Page extends JFrame implements ActionListener{

	int employeeID;
	int customerID;
	String licencePlate;
	String email;
	String purchaseDate;
	String make;
	String year;
	String currentMileage;
	String lastServiceType;
	String lastServiceDate;
	
	
	JButton registerButton;
	JButton cancelButton;
	
	
	JTextField licencePlateFild;
	JTextField emailfild;
	JTextField purchaseDateFild;
	JTextField makeFild;
	JTextField modelFild;
	JTextField yearFild;
	JTextField currentMileadgeFild;
	JTextField lastServiceTypeFild;
	JTextField lastServiceDateFild;
	
	
	JLabel lblLicencePlate;
	JLabel lblemail;
	JLabel lblPurchaseDate;
	JLabel lblMake;
	JLabel lblModel;
	JLabel lblYear;
	JLabel lblCurrentMileage;
	JLabel lbllastServiceType;
	JLabel lblLastServiceDate;
	
	

	public Receptionist_Register_Car_Page(int employeeID)
	{
		this.init();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.employeeID = employeeID;
	}
	void init()
	{
		this.setTitle("Receptionist Register Car");
		this.setLayout(new GridLayout(0,1));
		this.setLocation(400, 500);
		this.setSize(400, 400);
		
		
		
		this.licencePlateFild = new JTextField();
		this.emailfild = new JTextField();
		this.purchaseDateFild = new JTextField();
		this.makeFild = new JTextField();
		this.purchaseDateFild = new JTextField();
		this.modelFild = new JTextField();
		this.yearFild = new JTextField();
		this.currentMileadgeFild = new JTextField();
		this.lastServiceTypeFild = new JTextField();
		this.lastServiceDateFild = new JTextField();
		
		
		
		
		
		this.lblLicencePlate = new JLabel("Licence Plate");
		this.lblemail = new JLabel("Customer Email address");
		this.lblPurchaseDate = new JLabel("Purchase Date(dd/MM/yyyy)");
		this.lblMake = new JLabel("Make");
		this.lblModel = new JLabel("Model");
		this.lblYear = new JLabel("Year");
		this.lblCurrentMileage = new JLabel("Current Mileage");
		this.lbllastServiceType = new JLabel("Last Service Type(A, B or C)");
		this.lblLastServiceDate = new JLabel("Last Service Date(dd/MM/yyyy)");
		
		
	

		this.cancelButton = new JButton("Cancel");
		this.registerButton = new JButton("Register");
		
		
		
		
		this.add(lblemail);
		this.add(emailfild);
		
		this.add(lblLicencePlate);
		this.add(licencePlateFild);
		
		this.add(lblPurchaseDate);
		this.add(purchaseDateFild);
		this.add(lblMake);
		this.add(makeFild);
		this.add(lblModel);
		this.add(modelFild);
		this.add(lblYear);
		this.add(yearFild);
		this.add(lblCurrentMileage);
		this.add(currentMileadgeFild);
		this.add(lbllastServiceType);
		this.add(lastServiceTypeFild);
		this.add(lblLastServiceDate);
		this.add(lastServiceDateFild);
	
		this.add(registerButton);
		this.add(cancelButton);
		
		
		
	
		this.registerButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		

	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == this.registerButton)
		{
			
			this.email = emailfild.getText();
			if (!this.email.isEmpty()) {
				CustomerDao customerDao = new CustomerDao();
				this.customerID = customerDao.getCustomerID(email); 
				if (customerID != 0) {
					if (this.addCar() && this.addOwn()) {
						new Receptionist_Landing_Page(employeeID);
						this.dispose();
					} else {
						new Popdialog("register failed");
					}
				} else {
					new Popdialog("customer with this email does not exists, "
							+ "please change the email or sign up the customer with that email first.");
				}
			} else {
				new Popdialog("customer email cannot be empty!");
			}
		}
		else if(e.getSource() == this.cancelButton)
		{
			new Receptionist_Landing_Page(employeeID);
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
					Integer.parseInt(currentMileadgeFild.getText()),
					lastServiceTypeFild.getText(),
					new Date(new SimpleDateFormat("dd/MM/yyyy").parse(lastServiceDateFild.getText()).getTime()),
					new Date(new SimpleDateFormat("dd/MM/yyyy").parse(purchaseDateFild.getText()).getTime()));
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
		new Receptionist_Register_Car_Page(634622236);		
	}
}
