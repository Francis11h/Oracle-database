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

public class EmployeeMRDao {
	
	private Connection conn;
	
	public EmployeeMRDao(){
		conn = Dbconnection.getConnection();
	}
	
	public boolean addRecip(EmployeeMR employeeMR) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO zzha.employeemr VALUES ( employee_sequence.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, employeeMR.getEmail());
			pstmt.setString(2, employeeMR.getPassword());
			pstmt.setString(3, employeeMR.getUserType());
			pstmt.setString(4, employeeMR.getName());
			pstmt.setString(5, employeeMR.getAddress());
			pstmt.setString(6, employeeMR.getPhone());
			pstmt.setInt(7, employeeMR.getMonthlyPay());
			pstmt.setString(8, employeeMR.getPayroll());
			pstmt.setDate(9, employeeMR.getStart_date());
			pstmt.executeUpdate();
		    pstmt.close();
		    return true;
		} catch (SQLException e1) {
		    e1.printStackTrace();
		    return false;
		}
	}
	
	public boolean updateEmployeeMR(int eid, String name, String value) {
		try {
		      String sql = "UPDATE zzha.employeemr SET " + name + " = '" + value + "' WHERE EID = '" + eid + "'";
		      Statement statement = conn.createStatement();
		      int a = statement.executeUpdate(sql);
		      statement.close();
		      return a == 1;
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		      System.out.println("this is it!!!!");
		    }
		return false;
	
	}
	
	public int getEmployeeID(String email) {
		try {
		      String sql = "select eid from zzha.employeemr where email = '" 
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
	
	public EmployeeMR getEmployeeMR(int eid) {
		try {
		      String sql = "select * from zzha.employeemr where eid = '" 
		    		  		+ eid + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  return new EmployeeMR(
		    			  res.getInt("eid"),
		    			  res.getString("email"),
		    			  res.getString("password"),
		    			  res.getString("usertype"),
		    			  res.getString("name"),
		    			  res.getString("address"),
		    			  res.getString("phone"),
		    			  res.getInt("monthly_pay"),
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
		      String sql = "select service_center.name from zzha.employeemr_work_at, zzha.service_center"
		      		+ " where service_center.sid = employeemr_work_at.sid and employeemr_work_at.eid = '" 
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
	
	public boolean addEmployeeWorkAt(int managerEid, int eid) {
		try {
		      String sql = "select sid from zzha.employeemr_work_at where eid = '" + managerEid + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  int sid = res.getInt("sid");
		    	  try {
		    		  	PreparedStatement pstmt = conn.prepareStatement(
		  					"insert into zzha.employeemr_work_at values (?, ?)");
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
//		EmployeeMR employeeMR = null;
//		try {
//			employeeMR = new EmployeeMR(
//					1, "q@q2", "1", "manager", "qin", "5d29 newyork", "12345", 12, "Jan,18000", 
//					new Date(new SimpleDateFormat("dd/MM/yyyy").parse("1/1/2017").getTime()));
//			System.out.println(employeeMR.toString());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
		EmployeeMRDao employeeMRDao = new EmployeeMRDao();
//		if (employeeMRDao.addRecip(employeeMR)) {
//			System.out.println("added manager id: " + employeeMRDao.getEmployeeID("q@q2"));
//		} else {
//			System.out.println("add failed");
//		}
		System.out.println(employeeMRDao.getEmployeeMR(634622236));
//		System.out.println(employeeMRDao.updateEmployeeMR(61, "name", "xue"));
	}
}
