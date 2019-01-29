package com.zzhs.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconnection {
	
	static final String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";


	//static final String jdbcURL = "jdbc:oracle:thin:@10.153.57.73:1521:orcl";
	static final String user = "zzha";
	static final String passwd = "111111";
	static Connection conn = null;
		
	private Dbconnection() {}
	
	public static Connection getConnection() {
        if (conn != null) {
        	return conn;
        } else {
    		try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn =  DriverManager.getConnection(jdbcURL, user, passwd);
            } catch(Throwable oops) {
                oops.printStackTrace();
            }
    		return conn;
        }
	}
}