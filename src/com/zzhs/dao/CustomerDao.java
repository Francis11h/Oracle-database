package com.zzhs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zzhs.entity.Vehicle;
import com.zzhs.entity.Customer;

public class CustomerDao {
	
	private Connection conn = Dbconnection.getConnection();
	
	public CustomerDao(){

	}
	
	public boolean addCustomer(Customer customer) {
		try {
			String sql = "INSERT INTO zzha.customer VALUES ( customer_sequence.nextval, '" 
		    		  		+ customer.getEmail() + "', '" 
				    		+ customer.getPassword() + "', '" 
		    		  		+ customer.getName() + "', '"
		    		  		+ customer.getAddress() + "', '"
		    		  		+ customer.getPhone() + "') ";
		      Statement statement = conn.createStatement();
		      statement.executeUpdate(sql);
		      statement.close();
		      return true;
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		      return false;
		    }
	}
	
	public boolean updateCustomer(Customer customer) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"update customer set email = ?, password = ?, name = ?, address = ?, phone = ? where cid = ? ");
			pstmt.setString(1, customer.getEmail());
			pstmt.setString(2, customer.getPassword());
			pstmt.setString(3, customer.getName());
			pstmt.setString(4, customer.getAddress());
			pstmt.setString(5, customer.getPhone());
			pstmt.setInt(6, customer.getCid());
			pstmt.executeUpdate();
		    pstmt.close();
		    return true;
		} catch (SQLException e1) {
		    e1.printStackTrace();
		    return false;
		}

	}
	
	public int getCustomerID(String email) {
		try {
		      String sql = "select cid from zzha.customer where email = '" 
		    		  		+ email + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  return res.getInt("cid");
		    	  }
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return 0;
	}
	
	public Customer getCustomer(int cid) {
		try {
		      String sql = "select * from zzha.customer where cid = '" 
		    		  		+ cid + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  return new Customer(
		    			  res.getInt("cid"),
		    			  res.getString("email"),
		    			  res.getString("password"),
		    			  res.getString("name"),
		    			  res.getString("address"),
		    			  res.getString("phone"));
		    	  }
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return null;
	}
	
	public List<String> getCars(int cid) {
		List<String> cars = new ArrayList<>();
		try {
		      String sql = "select * from vehicle " + 
		      		"inner join own on own.licence_id = vehicle.licence_id " + 
		      		"inner join car_model on vehicle.cmid = car_model.cmid " + 
		      		"where own.cid = '" + cid + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      while (res.next()) {
		    	  Vehicle newCar = new Vehicle(
		    			  res.getString("licence_id"),
		    			  res.getString("make"),
		    			  res.getString("model"),
		    			  res.getInt("year"),
		    			  res.getInt("mileage"),
		    			  res.getString("service_type"),
		    			  res.getDate("service_date"),
		    			  res.getDate("purchase_date"));
		    	  cars.add(newCar.toString());
		    	  }
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return cars;
	}
	
	public static void main(String[] args) {
		Customer customer = new Customer(1, "z@z", "1", "zha", "5d29 raleigh", "91945");
		CustomerDao customerDao = new CustomerDao();
//		if (customerDao.addCustomer(customer)) {
//			System.out.println("added customer id: " + customerDao.getCustomerID("z@z"));
//		}
		System.out.println(customerDao.getCustomer(1003).toString());
	}
}
