package com.zzhs.dao;

import java.security.Provider.Service;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.HashMap;
import java.util.Set;

import javax.persistence.criteria.Order;

import org.hibernate.boot.model.naming.DatabaseIdentifier;
import org.hibernate.hql.spi.id.cte.AbstractCteValuesListBulkIdHandler;

import com.zzhs.entity.Vehicle;

import oracle.jdbc.dcn.QueryChangeDescription.QueryChangeEventType;
import oracle.jdbc.proxy.annotation.GetCreator;
import oracle.net.aso.s;

import com.zzhs.entity.Customer;
import com.zzhs.entity.Schedule_Service;

public class InventoryDao {
	
	private Connection conn; 
	
	public InventoryDao(){
		conn = Dbconnection.getConnection();
	}
	
	public String listInventory(int eid) {
		StringBuilder parts = new StringBuilder();
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select pt.name, pt.make, iv.quantity, iv.minimal_order, iv.threshold "
					+ "from inventory iv, part pt "
					+ "where iv.pid = pt.pid and iv.sid = (select sid from employeemr_work_at where eid = ?)");
			pstmt.setInt(1, eid);
			ResultSet res = pstmt.executeQuery();
			int i = 1;
			while(res.next()) {
				String string = String.format(
						"%s, %s %s, current quantity: %s, minimal order: %s, threshold: %s\n",
						i,
						res.getString("make"),
						res.getString("name"),
						res.getInt("quantity"),
						res.getInt("minimal_order"),
						res.getInt("threshold"));
				parts.append(string);
				i++;
			}
		    pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return parts.toString();
	}	
	
	
	
	
	public List<Integer> getPreviousService(Timestamp A , int sid) {
		List<Integer> aid = new ArrayList<>();
		try {
			String sql = "SELECT AID FROM SCHEDULE_SERVICE WHERE SID = '" + sid + "' AND SERVICE_END_DATE \r\n" + 
					"BETWEEN SYSTIMESTAMP AND TO_TIMESTAMP('" + A + "', 'YYYY-MM-DD:HH24:MI:SS.FF')";
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(sql);
			while (res.next()) {
				aid.add(res.getInt("aid"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return aid;
	}
	
	
	public HashMap<Integer,Integer> partCosted(List<Integer> aid) {
		HashMap<Integer,Integer> cost = new HashMap<>();
		try {
			for(int j = 0;j < aid.size(); j++) {
				String sql = "SELECT PID, QUANTITY\n" + 
						"    FROM SCHEDULE_SERVICE, MAINTENANCE_INCLUDE, VEHICLE, BASIC_SERVICE_NEED\n" + 
						"    WHERE VEHICLE.LICENCE_ID = SCHEDULE_SERVICE.LICENCE_ID AND SCHEDULE_SERVICE.SVID = MAINTENANCE_INCLUDE.SVID AND SCHEDULE_SERVICE.AID = '" + aid.get(j) + "'\n" + 
						"        AND VEHICLE.CMID = BASIC_SERVICE_NEED.CMID AND MAINTENANCE_INCLUDE.BSID = BASIC_SERVICE_NEED.BSID";
				String sql1 = "SELECT PID, QUANTITY\n" + 
						"	FROM SCHEDULE_SERVICE, REPAIR_INCLUDE, VEHICLE, BASIC_SERVICE_NEED\n" + 
						"	WHERE VEHICLE.LICENCE_ID = SCHEDULE_SERVICE.LICENCE_ID AND SCHEDULE_SERVICE.RSVID = REPAIR_INCLUDE.RSVID AND SCHEDULE_SERVICE.AID = '" + aid.get(j) + "'\n" + 
						"    AND VEHICLE.CMID = BASIC_SERVICE_NEED.CMID AND REPAIR_INCLUDE.BSID = BASIC_SERVICE_NEED.BSID";
				Statement statement = conn.createStatement();
				ResultSet res = statement.executeQuery(sql);
				Statement statement1 = conn.createStatement();
				ResultSet res1 = statement1.executeQuery(sql1);
				int pid = 0;
				int quantity = 0;
				while (res.next()) {
					pid = res.getInt("pid");
					quantity = res.getInt("quantity");
					if(cost.containsKey(pid)) {
						cost.put(pid, cost.get(pid) + quantity);
					}
					else {
						cost.put(pid, quantity);
					}
				}
				while (res1.next()) {
					pid = res.getInt("pid");
					quantity = res.getInt("quantity");
					if(cost.containsKey(pid)) {
						cost.put(pid, cost.get(pid) + quantity);
					}
					else {
						cost.put(pid, quantity);
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cost;
	}
	
//	public List<Integer> getSid(int aid) {
//		List<Integer> sid = new ArrayList<>();
//		try {
//			String sql = "SELECT SID FROM SCHEDULE_SERVICE WHERE SCHEDULE_SERVICE.AID = '" + aid + "';";
//			Statement statement = conn.createStatement();
//			ResultSet res = statement.executeQuery(sql);
//			while (res.next()) {
//				sid.add(res.getInt("sid"));
//			}
//			
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		return sid;
//	}

	public HashMap<Integer,Integer> partRemain(int sid) {
		HashMap<Integer,Integer> remain = new HashMap<>();
		try {
			String sql = "SELECT PID, QUANTITY FROM INVENTORY WHERE SID = '" + sid + "'";
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(sql);
			int pid = 0;
			int quantity = 0;
			while (res.next()) {
				pid = res.getInt("pid");
				quantity = res.getInt("quantity");
				if(remain.containsKey(pid)) {
					remain.put(pid, remain.get(pid) + quantity);
				}
				else {
					remain.put(pid, quantity);
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return remain;
	}
	
	public boolean partIsEnough(Timestamp A, int sid, String service_type, int target_id, String licence_id) {
		boolean Ans = false;
		int pid = 0;
		int quantity = 0;
		HashMap<Integer,Integer> use = new HashMap<>();
		List<Integer> aid = getPreviousService(A, sid);
		HashMap<Integer,Integer> remain = partRemain(sid);
		HashMap<Integer,Integer> cost = partCosted(aid);
		String sql = "SELECT PID, QUANTITY\n" + 
				"    FROM MAINTENANCE_INCLUDE, VEHICLE, BASIC_SERVICE_NEED\n" + 
				"    WHERE VEHICLE.LICENCE_ID = '" + licence_id + "' AND MAINTENANCE_INCLUDE.SVID = '" + target_id + "' \n" + 
				"        AND VEHICLE.CMID = BASIC_SERVICE_NEED.CMID AND MAINTENANCE_INCLUDE.BSID = BASIC_SERVICE_NEED.BSID";
		String sql1 = "SELECT PID, QUANTITY\n" + 
				"    FROM REPAIR_INCLUDE, VEHICLE, BASIC_SERVICE_NEED\n" + 
				"    WHERE VEHICLE.LICENCE_ID = '" + licence_id + "' AND REPAIR_INCLUDE.RSVID = '" + target_id + "' \n" + 
				"        AND VEHICLE.CMID = BASIC_SERVICE_NEED.CMID AND REPAIR_INCLUDE.BSID = BASIC_SERVICE_NEED.BSID";
		try {
			Statement statement = conn.createStatement();
			if(service_type == "MENT")
			{
				ResultSet res = statement.executeQuery(sql);
				while(res.next()) {
					pid = res.getInt("pid");
					quantity = res.getInt("quantity");
					if(use.containsKey(pid)) {
						use.put(pid, use.get(pid) + quantity);
					}
					else {
						use.put(pid, quantity);
					}
				}
			}
			else {
				ResultSet res = statement.executeQuery(sql1);
				while(res.next()) {
					pid = res.getInt("pid");
					quantity = res.getInt("quantity");
					if(use.containsKey(pid)) {
						use.put(pid, use.get(pid) + quantity);
					}
					else {
						use.put(pid, quantity);
					}
				}
			}
			Set<Integer> keyset = use.keySet();
			for(int key : keyset) {
				if(remain.containsKey(key) == false) {
					Ans = false;
					break;
				}
				else if(cost.containsKey(key) == false) {
					if(remain.get(key) - use.get(key) >= 0) {
						Ans = true;
					}
					else
					{
						Ans = false;
						break;
					}
				}
				else {
					if(remain.get(key) - cost.get(key) - use.get(key) >= 0) {
						Ans = true;
					}
					else {
						Ans = false;
						break;
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Ans;
	}
	
	
	
	
	

	public static void main(String[] args) {
		InventoryDao inventory = new InventoryDao();
		System.out.println(inventory.partRemain(1));
		Timestamp date = new Timestamp(System.currentTimeMillis());
		System.out.println(date.toString());
		List<Integer> aid = inventory.getPreviousService(date, 1);
		System.out.println(inventory.partCosted(aid));
		System.out.println(inventory.partIsEnough(date, 1, "MENT", 1, "XYZ-5643"));
	}
	
	
	
}
