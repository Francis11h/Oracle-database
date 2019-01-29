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
import org.jboss.jandex.Main;

import com.zzhs.entity.Vehicle;

import oracle.jdbc.dcn.QueryChangeDescription.QueryChangeEventType;
import oracle.jdbc.proxy.annotation.GetCreator;
import oracle.net.aso.s;

import com.zzhs.entity.Customer;
import com.zzhs.entity.Schedule_Service;

public class MaitServiceDao {
	
	private Connection conn; 
	
	public MaitServiceDao(){
		conn = Dbconnection.getConnection();
	}
	
	public String listCarService() {
		StringBuilder services = new StringBuilder();
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select * from car_model");
			ResultSet res = pstmt.executeQuery();
			int i = 1; 
			while(res.next()) {
				int cmid = res.getInt("cmid");
				services.append(String.format(
						"%s, %s %s:\n", i, res.getString("make"), res.getString("model")));
				PreparedStatement pstmt1 = conn.prepareStatement(
						"select svid, service_type, mile from maintenance_service where car_model = ? order by service_type asc");
				pstmt1.setInt(1, cmid);
				ResultSet res1 = pstmt1.executeQuery();
				while(res1.next()) {
					int svid = res1.getInt("svid");
					services.append(String.format(
							"    Service type %s: mile %s, list of basic service: ", res1.getString("service_type"), res1.getInt("mile")));
					PreparedStatement pstmt2 = conn.prepareStatement(
							"select bs.name from maintenance_include mi, basic_service bs where mi.svid = ? and mi.bsid = bs.bsid");
					pstmt2.setInt(1, svid);
					ResultSet res2 = pstmt2.executeQuery();
					while(res2.next()) {
						services.append(String.format("%s, ", res2.getString("name")));
					}
					services.append("\n");
					pstmt2.close();
				}
				pstmt1.close();
				i++;
			}
		    pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return services.toString();
	}	

	public static void main(String[] args) {
		MaitServiceDao maitServiceDao = new MaitServiceDao();
		System.out.println(maitServiceDao.listCarService());
	}
}
