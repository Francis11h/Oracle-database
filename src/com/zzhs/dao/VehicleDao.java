package com.zzhs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.zzhs.entity.Vehicle;

import net.bytebuddy.dynamic.TypeResolutionStrategy.Passive;

public class VehicleDao {
	
	private Connection conn = Dbconnection.getConnection();
	
	public VehicleDao(){

	}
	
	public boolean addCar(Vehicle vehicle) {
		int cmid = getCmid(vehicle);
		if(cmid != 0) {
			try {
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO zzha.vehicle VALUES (?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, vehicle.getLicence_id());
				pstmt.setInt(2, vehicle.getYear());
				pstmt.setInt(3, vehicle.getMileage());
				pstmt.setString(4, vehicle.getSerivce_type());
				pstmt.setDate(5, vehicle.getService_date());
				pstmt.setDate(6, vehicle.getPurchase_date());
				pstmt.setInt(7, cmid);
				pstmt.executeUpdate();
			    pstmt.close();
			    return true;
			} catch (SQLException e1) {
			    e1.printStackTrace();
			}
		}
		return false;
	}
	
	public Vehicle getCar(String licence_id) {
		
		try {
		      String sql = "select * from zzha.car where licence_id = '" 
		    		  		+ licence_id + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  Vehicle vehicle = new Vehicle(res.getString("licence_id"),
		    			  "make",
		    			  "model",
		    			  res.getInt("year"),
		    			  res.getInt("mileage"),
		    			  res.getString("service_type"),
		    			  res.getDate("service_date"),
		    			  res.getDate("purchase_date"));
		    	  return getMakeModel(res.getInt("cmid"), vehicle);
		    	  }
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return null;
	}
	
	private int getCmid(Vehicle vehicle) {
		int cmid = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"Select cmid from zzha.car_model where make = ? and model = ?");
			pstmt.setString(1, vehicle.getMake().toLowerCase());
			pstmt.setString(2, vehicle.getModel().toLowerCase());
			ResultSet resultSet = pstmt.executeQuery();
		    if(resultSet.next()) {
		    	cmid = resultSet.getInt("cmid");
		    }
			pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return cmid;
	}
	
	private Vehicle getMakeModel(int cmid, Vehicle vehicle) {
		Vehicle updatedVehicle = vehicle;
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"Select make, model from zzha.car_model where cmid = ?");
			pstmt.setInt(1, cmid);
			ResultSet res = pstmt.executeQuery();
		    if(res.next()) {
		    	updatedVehicle.setMake(res.getString("make"));
		    	updatedVehicle.setModel(res.getString("model"));
		    	return updatedVehicle;
		    }
			pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return null;
	}
}
