package com.zzhs.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.zzhs.entity.Own;

public class OwnDao {
	
	private Connection conn = Dbconnection.getConnection();
	
	public OwnDao(){

	}
	
	public boolean addOwn(Own own) {
		try {
			String sql = "INSERT INTO zzha.own VALUES ( '" 
					+ own.getCid() + "', '"
					+ own.getLicence_id() + "' )"; 
		      Statement statement = conn.createStatement();
		      statement.executeUpdate(sql);
		      statement.close();
		      return true;
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		      return false;
		    }
	}	
}
