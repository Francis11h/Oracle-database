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

import javax.persistence.criteria.From;

import com.zzhs.entity.Vehicle;
import com.zzhs.entity.Customer;
import com.zzhs.entity.EmployeeMR;
import com.zzhs.entity.Notification;

public class NotificationDao {
	
	private Connection conn = Dbconnection.getConnection();
	
	public NotificationDao(){

	}
	
	public boolean addNotification(int eid) {
		try {
			// try from distributor orders
			PreparedStatement pstmt = conn.prepareStatement(
					"select doid, CURRENT_DATE - expected_delivery_date as delays "
					+ "from order_from_distributor "
					+ "where status = 'pending' and bsid = (select sid from employeemr_work_at where eid = ?) and expected_delivery_date < CURRENT_DATE");
			pstmt.setInt(1, eid);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				PreparedStatement pstmt1 = conn.prepareStatement(
						"Insert into notification (nid, notify_time, doid, delayed_by) values ( notification_sequence.nextval, CURRENT_DATE, ?, ?)");
				pstmt1.setInt(1, res.getInt("doid"));
				pstmt1.setInt(2, res.getInt("delays"));
				int row = pstmt1.executeUpdate();
				pstmt1.close();
			}
			pstmt.close();
			
			// try from service center orders
			PreparedStatement pstmt3 = conn.prepareStatement(
					"select soid, CURRENT_DATE - expected_delivery_date as delays "
					+ "from order_from_service_center "
					+ "where status = 'pending' and bsid = (select sid from employeemr_work_at where eid = ?) and expected_delivery_date < CURRENT_DATE");
			pstmt3.setInt(1, eid);
			ResultSet res3 = pstmt3.executeQuery();
			while(res3.next()) {
				PreparedStatement pstmt4 = conn.prepareStatement(
						"Insert into notification (nid, notify_time, doid, delayed_by) values ( notification_sequence.nextval, CURRENT_DATE, ?, ?)");
				pstmt4.setInt(1, res3.getInt("doid"));
				pstmt4.setInt(2, res3.getInt("delays"));
				int row = pstmt4.executeUpdate();
				pstmt4.close();
			}
			pstmt3.close();
			
		} catch (SQLException e1) {
		    e1.printStackTrace();
		    return false;
		}
		return true;
	}
	
	public String getNotification(int eid) {
		StringBuilder notifications = new StringBuilder();
		try {
			//from distributor orders
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT n.nid, n.notify_time, n.doid, D.NAME, ob.expected_delivery_date, n.delayed_by "
					+ "FROM NOTIFICATION N, ORDER_FROM_DISTRIBUTOR OB, DISTRIBUTOR D " 
					+ "WHERE N.DOID = OB.DOID AND OB.SDID = D.DID AND ob.bsid = (SELECT SID FROM employeemr_work_at WHERE EID = ?)");
			pstmt.setInt(1, eid);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				String string = String.format(
						"NotificationID %s, notified on %s, OrderID %s, from supplier %s, expected delivery on %s, but delayed %s days.\n",
						res.getInt("nid"),
						res.getTimestamp("notify_time"),
						res.getInt("doid"),
						res.getString("name"),
						res.getDate("expected_delivery_date"),
						res.getInt("delayed_by"));
				notifications.append(string);
			}
			//from service center orders
			pstmt = conn.prepareStatement(
					"SELECT n.nid, n.notify_time, n.soid, S.NAME, os.expected_delivery_date, n.delayed_by "
					+ "FROM NOTIFICATION N, ORDER_FROM_SERVICE_CENTER OS, SERVICE_CENTER S " 
					+ "WHERE N.SOID = OS.SOID AND OS.SSID = S.SID AND os.bsid = (SELECT SID FROM employeemr_work_at WHERE EID = ?)");
			pstmt.setInt(1, eid);
			res = pstmt.executeQuery();
			while(res.next()) {
				String string = String.format(
						"NotificationID %s, notified on %s, OrderID %s, from supplier %s, expected delivery on %s, but delayed %s days.\n",
						res.getInt("nid"),
						res.getTimestamp("notify_time"),
						res.getInt("soid"),
						res.getString("name"),
						res.getDate("expected_delivery_date"),
						res.getInt("delayed_by"));
				notifications.append(string);
			}
		    pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return notifications.toString();
	}
	
	public String getNotification(int eid, int oid) {
		StringBuilder notifications = new StringBuilder();
		try {
			// try from distributor orders of that order
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT ob.doid, ob.datetime, pt.name as pname, D.NAME as dname, s.name as bname, ob.quantity as qty, pt.price, ob.status " 
					+ "FROM NOTIFICATION N, ORDER_FROM_DISTRIBUTOR OB, DISTRIBUTOR D, service_center s, part pt " 
					+ "WHERE N.DOID = OB.DOID AND OB.SDID = D.DID AND ob.doid = ? and ob.pid = pt.pid and ob.bsid = s.sid");
			pstmt.setInt(1, oid);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				String string = String.format(
						"OrderID %s %s, %s ordered %s %s from supplier %s on %s, total cost %s and %s per unit.\n",
						res.getInt("doid"),
						res.getString("status"),
						res.getString("bname"),
						res.getInt("qty"),
						res.getString("pname"),
						res.getString("dname"),
						res.getDate("datetime"),
						res.getInt("qty") * res.getInt("price"),
						res.getInt("price"));
				notifications.append(string);
			}
			//from service center orders
			pstmt = conn.prepareStatement(
					"SELECT os.soid, os.order_date, pt.name as pname, s1.NAME as sname, s2.name as bname, os.quantity as qty, pt.price, os.status " 
					+ "FROM NOTIFICATION N, ORDER_FROM_SERVICE_CENTER OS, service_center s1, service_center s2, part pt " 
					+ "WHERE N.SOID = OS.SOID AND OS.SSID = S1.SID AND os.soid = ? and os.pid = pt.pid and os.bsid = s2.sid");
			pstmt.setInt(1, oid);
			res = pstmt.executeQuery();
			while(res.next()) {
				String string = String.format(
						"OrderID %s %s, %s ordered %s %s from supplier %s on %s, total cost %s and %s per unit.\n",
						res.getInt("soid"),
						res.getString("status"),
						res.getString("bname"),
						res.getInt("qty"),
						res.getString("pname"),
						res.getString("sname"),
						res.getDate("order_date"),
						res.getInt("qty") * res.getInt("price"),
						res.getInt("price"));
				notifications.append(string);
			}
		    pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return notifications.toString();
	}
	
}
