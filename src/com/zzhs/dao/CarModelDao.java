package com.zzhs.dao;

import java.security.Provider.Service;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.Order;

import org.hibernate.boot.model.naming.DatabaseIdentifier;
import org.hibernate.hql.spi.id.cte.AbstractCteValuesListBulkIdHandler;

import com.zzhs.entity.Vehicle;

import oracle.jdbc.dcn.QueryChangeDescription.QueryChangeEventType;
import oracle.jdbc.proxy.annotation.GetCreator;
import oracle.net.aso.s;

import com.zzhs.entity.Customer;
import com.zzhs.entity.Schedule_Service;

public class CarModelDao {
	
	private Connection conn; 
	
	public CarModelDao(){
		conn = Dbconnection.getConnection();
	}
	
	public boolean addCarModel(String make, String model, int year, int amile, int bmile, int cmile, int[] Alist, int[] Blist, int[] Clist ) {
		if (!checkmodelExists(make, model)) {
			if (addCarModel(make, model, year)) {
				int cmid = getCmid(make, model);
				if(addMaitService(cmid, "A", amile) && addMaitService(cmid, "B", bmile) && addMaitService(cmid, "C", cmile)) {
					int svidA = getSvid(cmid, "A");
					int svidB = getSvid(cmid, "B");
					int svidC = getSvid(cmid, "C");
					if (addMaitServiceInclude(svidA, Alist) && addMaitServiceInclude(svidB, Blist) && addMaitServiceInclude(svidC, Clist)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public String listBasicService() {
		StringBuilder bs = new StringBuilder();
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select bsid, name from basic_service order by bsid asc");
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				bs.append("BSID:" + res.getInt("bsid") + ", ServiceName: " + res.getString("name") + ";   ");
			}
		    pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return bs.toString();
	}	
	
	public boolean checkmodelExists(String make, String model) {
		try {
			// check if existed in car_model table
			PreparedStatement pstmt = conn.prepareStatement("select 1 from car_model where make = ? and model = ?");
			pstmt.setString(1, make);
			pstmt.setString(2, model);
			ResultSet res = pstmt.executeQuery();
		    if (res.next()) {
		    	pstmt.close();
		    	return true;
		    }
		}catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return false;
	}
	
	private boolean addCarModel(String make, String model, int year) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("Insert into car_model (cmid, make, model, year) values (car_model_sequence.nextval, ?, ?, ?)");
			pstmt.setString(1, make.toLowerCase());
			pstmt.setString(2, model.toLowerCase());
			pstmt.setInt(3, year);
			int row = pstmt.executeUpdate();
		    if (row == 1) {
		    	pstmt.close();
		    	return true;
		    }
		}catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return false;
	}
	
	private int getCmid(String make, String model) {
		int cmid = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement("select cmid from car_model where make = ? and model = ?");
			pstmt.setString(1, make.toLowerCase());
			pstmt.setString(2, model.toLowerCase());
			ResultSet res = pstmt.executeQuery();
		    if (res.next()) {
		    	cmid = res.getInt("cmid");
		    }
	    	pstmt.close();
		}catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return cmid;
	}
	
	private int getSvid(int cmid, String type) {
		int svid = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement("select svid from maintenance_service where car_model = ? and service_type = ?");
			pstmt.setInt(1, cmid);
			pstmt.setString(2, type.toUpperCase());
			ResultSet res = pstmt.executeQuery();
		    if (res.next()) {
		    	svid = res.getInt("svid");
		    }
		    pstmt.close();
		}catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return svid;
	}
	
	public boolean addMaitService(int cmid, String type, int mile) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("Insert into maintenance_Service (svid, service_type, car_model, mile) values (service_sequence.nextval, ?, ?, ?)");
			pstmt.setString(1, type.toUpperCase());
			pstmt.setInt(2, cmid);
			pstmt.setInt(3, mile);
			int row = pstmt.executeUpdate();
		    if (row == 1) {
		    	pstmt.close();
		    	return true;
		    }
		}catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return false;
	}
	
	public boolean addMaitServiceInclude(int svid, int[] blist) {
		boolean status = true;
		for (int bsid: blist) {
//			System.out.println("bsid is: " + bsid + ", svid is: " + svid);
			try {
				PreparedStatement pstmt = conn.prepareStatement("Insert into maintenance_include (svid, bsid) values (?, ?)");
				pstmt.setInt(1, svid);
				pstmt.setInt(2, bsid);
				int row = pstmt.executeUpdate();
			    if (row != 1) {
			    	status = false;
			    }
			    pstmt.close();
			}catch (SQLException e1) {
			    e1.printStackTrace();
			}
		}
		return status;
	}
	
	public static void main(String[] args) {
		CarModelDao carModelDao = new CarModelDao();
		System.out.println(carModelDao.listBasicService());
	}
}
