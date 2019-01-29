package com.zzhs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.zzhs.entity.Vehicle;
import com.zzhs.entity.Customer;
import com.zzhs.entity.EmployeeMR;
import com.zzhs.entity.Mechanic;

public class MechanicDao {
	
	private Connection conn = Dbconnection.getConnection();
	
	public MechanicDao(){

	}
	
	public boolean addMechanic(Mechanic mechanic) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO zzha.mechanic VALUES ( employee_sequence.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, mechanic.getEmail());
			pstmt.setString(2, mechanic.getPassword());
			pstmt.setString(3, mechanic.getName());
			pstmt.setString(4, mechanic.getAddress());
			pstmt.setString(5, mechanic.getPhone());
			pstmt.setInt(6, mechanic.getHour_Worked());
			pstmt.setInt(7, mechanic.getHourly_pay());
			pstmt.setString(8, mechanic.getPayroll());
			pstmt.setDate(9, mechanic.getStart_date());
			pstmt.executeUpdate();
		    pstmt.close();
		    return true;
		} catch (SQLException e1) {
		    e1.printStackTrace();
		    return false;
		}
	}
	
	public int getEidFromName(String emp_name) {
		try {
		     String sql = "select eid from zzha.mechanic where name = '" 
	    		  		+ emp_name + "'";
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if(res.next()) {
		    	  return res.getInt("eid");
		      }
		     statement.close();
		} catch (SQLException e1) {
		  e1.printStackTrace();
		}
		return -1;
	}
	
	public int getEmployeeID(String email) {
		try {
		      String sql = "select eid from zzha.mechanic where email = '" 
		    		  		+ email + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  return res.getInt("eid");
		    	  }
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return 0;
	}
	
	public Mechanic getMechanic(int eid) {
		try {
		      String sql = "select * from zzha.mechanic where eid = '" 
		    		  		+ eid + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  return new Mechanic(
		    			  res.getInt("eid"),
		    			  res.getString("email"),
		    			  res.getString("password"),
		    			  res.getString("name"),
		    			  res.getString("address"),
		    			  res.getString("phone"),
		    			  res.getInt("hour_worked"),
		    			  res.getInt("hourly_pay"),
		    			  res.getString("payroll"),
		    			  res.getDate("start_date"));
		    	  }
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return null;
	}
	
	public String getEmployeeWorkAt(int eid) {
		try {
		      String sql = "select service_center.name from zzha.mechanic_work_at, zzha.service_center"
		      		+ " where service_center.sid = mechanic_work_at.sid and mechanic_work_at.eid = '" 
		    		+ eid + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  return res.getNString("name");
		    	  }
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return null;
	}
	
	public boolean addMechanicWorkAt(int managerEid, int eid) {
		try {
		      String sql = "select sid from zzha.employeemr_work_at where eid = '" + managerEid + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  int sid = res.getInt("sid");
		    	  try {
		    		  	PreparedStatement pstmt = conn.prepareStatement(
		  					"insert into zzha.mechanic_work_at values (?, ?)");
		  				pstmt.setInt(1, eid);
		  				pstmt.setInt(2, sid);
		  				pstmt.executeUpdate();
		  				pstmt.close();
		  				return true;
		  			} catch (SQLException e1) {
		  				e1.printStackTrace();
		  			}
		      }
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return false;
	}
	
	public static void main(String[] args) {
		Mechanic mechanic = null;
		try {
			mechanic = new Mechanic(
					1, "q@q3", "1", "qin", "5d29 newyork", "12345", 0, 12, "Jan,18000", 
					new Date(new SimpleDateFormat("dd/MM/yyyy").parse("1/1/2017").getTime()));
			System.out.println(mechanic.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		MechanicDao mechanicDao = new MechanicDao();
		if (mechanicDao.addMechanic(mechanic)) {
			System.out.println("added mechanic id: " + mechanicDao.getEmployeeID("q@q3"));
		} else {
			System.out.println("add failed");
		}
		System.out.println(mechanicDao.getMechanic(mechanicDao.getEmployeeID("q@q3")).toString());
	}
}
