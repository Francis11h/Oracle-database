package com.zzhs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.criteria.CriteriaBuilder.Case;

import com.zzhs.entity.User;

public class UserDao {
	
	private Connection conn;
	
	public UserDao() { 
		conn = Dbconnection.getConnection();
	}
	
	public String authenticateUser(String email, String password) {
				
		try {
		      String sql = "select password from zzha.customer where email = '" 
		    		  		+ email + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
//		      System.out.println(sql);
		      if (res.next()) {
//			      System.out.println(password + password.length());
		    	  if (password.equals(res.getString("password"))) {
		    		  return "customer";
		    		  }
		    	  }
		      sql = "select password, usertype from zzha.employeemr where email = '" 
	    		  		+ email + "'" ;
		      res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  if (password.equals(res.getString("password"))) {
		    		  return res.getString("usertype");
		    		  }
		    	  }
		      res.close();
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return null;
	}

	public boolean checkUserExists(String email) {
		try {
			// check if existed in customer table
			String sql = "select 1 from zzha.customer where email = '" 
		    		  		+ email + "'" ;
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  System.out.println("exists in customer table");
		    	  return true;
		    	  }
			
		      // check if existed in employeemr table		      
		      sql = "select 1 from zzha.employeemr where email = '" 
	    		  		+ email + "'" ;
		      res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  System.out.println("exists in employeemr table");
		    	  return true;
		    	  }
			
		      // check if existed in mechanic table
		      sql = "select 1 from zzha.mechanic where email = '" 
	    		  		+ email + "'" ;
		      res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  System.out.println("exists in mechanic table");
		    	  return true;
		    	  }
		      res.close();
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return false;
	}
	
	public int getId(String email, String userType) {
		String sql;
		switch (userType) {
			case "receptionist": sql = "select eid as id from zzha.employeemr where email = '" + email + "'" ;
			break;
			case "manager": sql = "select eid as id from zzha.employeemr where email = '" + email + "'" ;
			break;
			case "mechanic": sql = "select eid as id from zzha.mechanic where email = '" + email + "'" ;
			break;
			default: sql = "select cid as id from zzha.customer where email = '" + email + "'" ;
			break;
		}
		System.out.println(sql);	
		try {
		      Statement statement = conn.createStatement();
		      ResultSet res = statement.executeQuery(sql);
		      if (res.next()) {
		    	  return res.getInt("id");
		    	  }
		      res.close();
		      statement.close();
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		return 0;
	}
	
	public static void main(String[] args) {
		UserDao userDao = new UserDao();
		System.out.println(userDao.authenticateUser("wimartin@acme.com", "1"));
//		System.out.println(userDao.checkUserExists("jarvis@gmail.com"));
//		System.out.println(userDao.getId("jarvis@gmail.com", "customer"));
	}
}
