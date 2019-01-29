package com.zzhs.dao;

import java.security.Provider.Service;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//import java.util.Date;

import com.zzhs.entity.Vehicle;
import com.zzhs.entity.Customer;
import com.zzhs.entity.Schedule_Service;

public class Schedule_ServiceDao {
	
	private Connection conn; 
	
	public Schedule_ServiceDao(){
		conn = Dbconnection.getConnection();
	}
	
	public void CloseConn(){
		try {
		conn.close();
		}catch (SQLException e1) {
		 e1.printStackTrace();
		}
	}
	
	public int GetEid(String service_type){
		try {
			int AnsEid = 0;
			String sql = "select SCHEDULE_SERVICE.EID \r\n" + 
					"from MECHANIC full outer join SCHEDULE_SERVICE on MECHANIC.EID = SCHEDULE_SERVICE.EID and SCHEDULE_SERVICE.SERVICE_TYPE = '" + service_type + "'\r\n" + 
					"where SCHEDULE_SERVICE.SERVICE_END_DATE = NULL";
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(sql);
			if(res.next()){
				AnsEid = res.getInt("schedule_service.eid");
				statement.close();
			}
			else {
				String sql1 = "create or replace view EMP_FREE_DATE as\r\n" + 
						"	select EID, MAX(SERVICE_END_DATE) as FREE_DATE\r\n" + 
						"	from SCHEDULE_SERVICE\r\n" + 
						"	where SERVICE_TYPE = '" + service_type + "'\r\n" + 
						"	group by EID";
				Statement statement1 = conn.createStatement();
				ResultSet res1 = statement1.executeQuery(sql1);
				String sql2 = "select EID from EMP_FREE_DATE where FREE_DATE in (select MIN(FREE_DATE) from EMP_FREE_DATE)";
				Statement statement2 = conn.createStatement();
				ResultSet res2 = statement2.executeQuery(sql2);
				if(res2.next()) {
					AnsEid = res2.getInt("eid");
				}
				statement1.close();
				statement2.close();
			}
			return AnsEid;
		    }catch (SQLException e1) {
		     e1.printStackTrace();
		     return -1;
		    }
	}
	
	public Timestamp GetTwoEarlestDate(int eid, String service_type){
		try {
			Timestamp AnsDate = null;
			Timestamp SystemDate = null;
			String sql = "select MAX(SERVICE_END_DATE) as FREEDATE \r\n" + 
					"from SCHEDULE_SERVICE \r\n" + 
					"where EID = '" + eid + "' and SERVICE_TYPE = '" + service_type +"'";
			String sql1 = "SELECT SYSTIMESTAMP FROM DUAL";
			Statement statement = conn.createStatement();
			Statement statement1 = conn.createStatement();
			ResultSet res = statement.executeQuery(sql);
			ResultSet res1 = statement1.executeQuery(sql1);
			if(res1.next()) {
				SystemDate = res1.getTimestamp("systimestamp");
				statement1.close();
			}
			if(res.next()){
				AnsDate = res.getTimestamp("freedate");
				if(AnsDate == null) {
					AnsDate = SystemDate;
				}
				if(AnsDate.compareTo(SystemDate) < 0){
					AnsDate = SystemDate;
				}
				statement.close();
			}
			else
			{
				AnsDate = SystemDate;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(AnsDate);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			while(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			if(service_type == "MENT") {
				calendar.set(Calendar.HOUR_OF_DAY, 8);
			}
			else {
				calendar.set(Calendar.HOUR_OF_DAY, 13);
			}
			AnsDate.setTime(calendar.getTimeInMillis());
			return AnsDate;
		}catch (SQLException e1) {
		     e1.printStackTrace();
		     return Timestamp.valueOf("0000-00-00");
		}
		
	}
	
	public Timestamp AddHalfHour(Timestamp first_date) {
		Timestamp second_date = new Timestamp(123);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(first_date);
		calendar.add(Calendar.MINUTE, 30);
		second_date.setTime(calendar.getTimeInMillis());
		return second_date;
	}
	
	public Timestamp CalculateServiceEndDateFromService(Schedule_Service service) {
		Timestamp Ansdate = null;
		try {
		String sql = " SELECT NUMTODSINTERVAL(SUM(TIME)*3600, 'SECOND') + TO_TIMESTAMP('" + service.getService_date() + "', 'YYYY-MM-DD:HH24:MI:SS.FF') AS END_DATE \r\n" + 
				" FROM BASIC_SERVICE, MAINTENANCE_INCLUDE\r\n" + 
				" WHERE BASIC_SERVICE.BSID = MAINTENANCE_INCLUDE.BSID AND MAINTENANCE_INCLUDE.SVID = '" + service.getSvid() + "'\r\n" + 
				" GROUP BY MAINTENANCE_INCLUDE.SVID";
		String sql1 = " SELECT NUMTODSINTERVAL(SUM(TIME)*3600, 'SECOND') + TO_TIMESTAMP('" + service.getService_date() + "', 'YYYY-MM-DD:HH24:MI:SS.FF') AS END_DATE \r\n" + 
				" FROM BASIC_SERVICE, REPAIR_INCLUDE\r\n" + 
				" WHERE BASIC_SERVICE.BSID = REPAIR_INCLUDE.BSID AND REPAIR_INCLUDE.RSVID = '" + service.getRsvid() + "'\r\n" + 
				" GROUP BY REPAIR_INCLUDE.RSVID ";
		Statement statement = conn.createStatement();
		if(service.getService_type() == "MENT") {
			ResultSet res = statement.executeQuery(sql);
			if(res.next()) {
				Ansdate = res.getTimestamp("end_date");
			}
		}
		else {
			ResultSet res = statement.executeQuery(sql1);
			if(res.next()) {
				Ansdate = res.getTimestamp("end_date");
			}
		}
		statement.close();
		}catch (SQLException e1) {
			e1.printStackTrace();
	    }
		return Ansdate;
	}
	
	public int getSvidFromLicence(String licence_id) {
		int ans = 0;
		try {
		String sql = "select CMID, MILEAGE from VEHICLE where LICENCE_ID = '" + licence_id + "'";
		Statement statement = conn.createStatement();
		ResultSet res = statement.executeQuery(sql);
		int cmid = 0;
		int mileage = 0;
		if(res.next()) {
			cmid = res.getInt("cmid");
			mileage = res.getInt("mileage");
		}
		String sql1 = "select SVID from MAINTENANCE_SERVICE where CAR_MODEL = '" + cmid +"' and MILE = \r\n" +  
		"(select MAX(MILE) from MAINTENANCE_SERVICE where CAR_MODEL = '" + cmid + "' and MILE <= '" + mileage + "' )";
		Statement statement1 = conn.createStatement();
		ResultSet res1 = statement1.executeQuery(sql1);
		if(res1.next()) {
			ans = res1.getInt("svid");
		}
		statement.close();
		statement1.close();
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		return ans;
	}
	
	public int getRsvidFromProblem(String problem) {
		int ans = 0;
		try {
		String sql = "select RSVID from REPAIR_SERVICE where SPECIFIC_PROBLEM = '" + problem + "'";
		Statement statement = conn.createStatement();
		ResultSet res = statement.executeQuery(sql);
		if(res.next()) {
			ans = res.getInt("rsvid");
		}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		return ans;
	}
	
	public int getServiceCenterIdFromMechanic(int eid) {
		try {
		int Ans = -1;
		String sql = "select SID from MECHANIC_WORK_AT where EID = '" + eid + "'";
		Statement statement = conn.createStatement();
		ResultSet res = statement.executeQuery(sql);
		if(res.next())
		{
			Ans = res.getInt("sid");
		}
		return Ans;
		}catch (SQLException e1) {
		e1.printStackTrace();
		return -1;
		}
	}
	
	public int getServiceCenterIdFromManager(int eid) {
		try {
		int Ans = -1;
		String sql = "select SID from EMPLOYEEMR_WORK_AT where EID = '" + eid + "'";
		Statement statement = conn.createStatement();
		ResultSet res = statement.executeQuery(sql);
		if(res.next())
		{
			Ans = res.getInt("sid");
		}
		return Ans;
		}catch (SQLException e1) {
		e1.printStackTrace();
		return -1;
		}
	}
	
	public boolean ScheduleService(Schedule_Service service) {
		try {
			String sql = "INSERT INTO zzha.Schedule_Service VALUES ( schedule_sequence.nextval, '" 
							+ service.getService_type() + "', '"
							+ service.getSvid() + "'," 
							+ "NULL,"							
		    		  		+" '" + service.getSid() + "', '" 
				    		+ service.getCid() + "', '" 
		    		  		+ service.getLicence_id() + "', '"
		    		  		+ service.getEid() + "',"
		    		  		+ "TO_TIMESTAMP('" + service.getService_date() + "', 'YYYY-MM-DD:HH24:MI:SS.FF')," 
		    		  		+ "TO_TIMESTAMP('" + service.getService_end_date() + "', 'YYYY-MM-DD:HH24:MI:SS.FF'))";
			String sql1 = "INSERT INTO zzha.Schedule_Service VALUES ( schedule_sequence.nextval, '" 
					+ service.getService_type() + "'," 
					+ "NULL,"
					+" '" + service.getRsvid() + "', '"							
    		  		+ service.getSid() + "', '" 
		    		+ service.getCid() + "', '" 
    		  		+ service.getLicence_id() + "', '"
    		  		+ service.getEid() + "',"
    		  		+ "TO_TIMESTAMP('" + service.getService_date() + "', 'YYYY-MM-DD:HH24:MI:SS.FF')," 
    		  		+ "TO_TIMESTAMP('" + service.getService_end_date() + "', 'YYYY-MM-DD:HH24:MI:SS.FF'))";
		      Statement statement = conn.createStatement();
		      if(service.getService_type() == "MENT") {
		    	  statement.executeUpdate(sql);
		      }
		      else {
		    	  statement.executeUpdate(sql1);
		      }
		      statement.close();
		      return true;
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		      return false;
		    }
	}
	
	public List<List<String>> getCustomerServiceDetail(int cid) {
		
		try {
			List<List<String>> ans = new ArrayList<>();
			String sql = "SELECT * FROM SCHEDULE_SERVICE \n" + 
					"WHERE CID = '" + cid + "' AND SERVICE_DATE > SYSTIMESTAMP";
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(sql);
			while(res.next()) {
				
				List<String> cur = new ArrayList<>();
				cur.add(res.getString("licence_id"));
				cur.add(res.getString("aid"));
				cur.add(res.getString("service_date"));
				cur.add(res.getString("service_type"));
				if (cur.get(3).equals("MENT")) {
					String svid = res.getString("SVID");
					String sql1 = "SELECT SERVICE_TYPE AS DETAILS FROM MAINTENANCE_SERVICE WHERE SVID = '" + svid + "'";
					Statement statement1 = conn.createStatement();
					ResultSet res1 = statement1.executeQuery(sql1);
					if (res1.next()) {
						cur.add(res1.getString("DETAILS"));
					}
				} else {
					String rsvid = res.getString("RSVID");
					String sql2 = "SELECT SPECIFIC_PROBLEM  AS DETAILS FROM REPAIR_SERVICE WHERE RSVID = '" + rsvid + "'";
					Statement statement2 = conn.createStatement();
					ResultSet res2 = statement2.executeQuery(sql2);
					if (res2.next()) {
						cur.add(res2.getString("DETAILS"));
					}
				}
				ans.add(cur);
				
			}
			
			return ans;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	
	
	
	public boolean ReScheduleService(Schedule_Service service) {
		try {
			/*PreparedStatement pstmt = conn.prepareStatement(
					"update SCHEDULE_SERVICE set SERVICE_DATE = ?, SERVICE_END_DATE = ? where AID = ? ");
			pstmt.setTimestamp(0, service.getService_date());
			pstmt.setTimestamp(1, service.getService_end_date());
			pstmt.setInt(2, service.getAid());
			pstmt.executeUpdate();
		    pstmt.close();*/
			String sql = "update SCHEDULE_SERVICE set SERVICE_DATE = TO_TIMESTAMP('" + service.getService_date() + "', 'YYYY-MM-DD:HH24:MI:SS.FF'), SERVICE_END_DATE = TO_TIMESTAMP('" + service.getService_end_date() + "', 'YYYY-MM-DD:HH24:MI:SS.FF') where AID = '" + service.getAid() + "'";
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			return true;
		} catch (SQLException e1) {
		    e1.printStackTrace();
		    return false;
		}

	}
	
	
//	public List<Schedule_Service> getService(int aid) {
//		List<Schedule_Service> schedule_Services = new ArrayList<>();
//		try {
//		      String sql = "select * from zzha.Schedule_Service where aid = '" 
//		    		  		+ aid + "'" ;
//		      Statement statement = conn.createStatement();
//		      ResultSet res = statement.executeQuery(sql);
//		      while (res.next()) {
//		    	  Schedule_Service service = new Schedule_Service(res.getInt("aid"), res.getInt("svid"),
//		    			  res.getInt("sid"),res.getInt("cid"),res.getString("licence_id"), res.getString("service_type")
//		    			  res.getInt("eid"), res.getDate("service_date"), res.getTimestamp("service_end_date"));
//		    	  schedule_Services.add(service);
////		    	  System.out.println(service.toString());
//		    	  }
//		      res.close();
//		      statement.close();
//		    } catch (SQLException e1) {
//		      e1.printStackTrace();
//		    }
//		return schedule_Services;
//	}
	
	
	public List<List<String>> viewServiceHistory(int cid) {
		List<List<String>> ans = new ArrayList<>();
		try {
			String sql = "SELECT AID, LICENCE_ID, SERVICE_TYPE, SERVICE_DATE \n" + 
					"	 FROM SCHEDULE_SERVICE\n" + 
					"	 WHERE SCHEDULE_SERVICE.CID = '" + cid + "'\n" + 
					"	 ORDER BY AID";
			String sql1 = "SELECT AID,NAME \n" + 
					"	 FROM MECHANIC, SCHEDULE_SERVICE \n" + 
					"	 WHERE MECHANIC.EID = SCHEDULE_SERVICE.EID AND SCHEDULE_SERVICE.CID = '" + cid + "'\n" + 
					"	 ORDER BY AID";
			String sql2 = "CREATE OR REPLACE VIEW SPENT AS \n" + 
					"    SELECT MAINTENANCE_INCLUDE.SVID, SCHEDULE_SERVICE.CID, SUM(TIME)*3600 AS DURING\n" + 
					"    FROM BASIC_SERVICE, MAINTENANCE_INCLUDE, SCHEDULE_SERVICE\n" + 
					"    WHERE BASIC_SERVICE.BSID = MAINTENANCE_INCLUDE.BSID AND MAINTENANCE_INCLUDE.SVID = SCHEDULE_SERVICE.SVID AND SCHEDULE_SERVICE.CID = '" + cid + "'\n" + 
					"    GROUP BY MAINTENANCE_INCLUDE.SVID, SCHEDULE_SERVICE.CID \n";			
			String sql3 = "SELECT AID, SERVICE_DATE + NUMTODSINTERVAL(DURING, 'SECOND') AS SERVICE_END_DATE\n" + 
					"    FROM SCHEDULE_SERVICE, SPENT\n" + 
					"    WHERE SCHEDULE_SERVICE.SVID = SPENT.SVID AND SCHEDULE_SERVICE.CID = SPENT.CID\n" + 
					"    ORDER BY AID";
			String sql4 = "CREATE OR REPLACE VIEW SPENT2 AS \n" + 
					"    SELECT REPAIR_INCLUDE.RSVID, SCHEDULE_SERVICE.CID, SUM(TIME)*3600 AS DURING \n" + 
					"    FROM BASIC_SERVICE, REPAIR_INCLUDE, SCHEDULE_SERVICE\n" + 
					"    WHERE BASIC_SERVICE.BSID = REPAIR_INCLUDE.BSID AND REPAIR_INCLUDE.RSVID = SCHEDULE_SERVICE.RSVID AND SCHEDULE_SERVICE.CID = '" + cid + "' \n" + 
					"    GROUP BY REPAIR_INCLUDE.RSVID, SCHEDULE_SERVICE.CID";
			String sql5 = "SELECT AID, SERVICE_DATE + NUMTODSINTERVAL(DURING, 'SECOND') AS SERVICE_END_DATE \n" + 
					"    FROM SCHEDULE_SERVICE, SPENT2\n" + 
					"    WHERE SCHEDULE_SERVICE.RSVID = SPENT2.RSVID AND SCHEDULE_SERVICE.CID = SPENT2.CID\n" + 
					"    ORDER BY AID";
			
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(sql);
			Statement statement1 = conn.createStatement();
			ResultSet res1 = statement1.executeQuery(sql1);
			Statement statement2 = conn.createStatement();
			ResultSet res2 = statement2.executeQuery(sql2);
			Statement statement3 = conn.createStatement();
			ResultSet res3 = statement3.executeQuery(sql3);
			Statement statement4 = conn.createStatement();
			ResultSet res4 = statement4.executeQuery(sql4);
			Statement statement5 = conn.createStatement();
			ResultSet res5 = statement5.executeQuery(sql5);
			
			while (res.next() && res1.next()) {
				List<String> record = new ArrayList<>();
				record.add(res.getString("aid"));
				record.add(res.getString("licence_id"));
				record.add(res.getString("service_type"));
				record.add(res.getString("service_date"));
				record.add(res1.getString("name"));
				ans.add(record);
			}
			
			while (res3.next()) {			
				for (int i = 0; i < ans.size(); i++) {
					if (ans.get(i).get(0).equals(res3.getString("aid"))) {
						ans.get(i).add(res3.getString("service_end_date"));
					}
				}
			}
			
			while (res5.next()) {
				for (int i = 0; i < ans.size(); i++) {
					if (ans.get(i).get(0).equals(res5.getString("aid"))) {
						ans.get(i).add(res5.getString("service_end_date"));
					}
				}
			}
			
			for (int i = 0; i < ans.size(); i++) {
				Timestamp start = Timestamp.valueOf(ans.get(i).get(3));
				Timestamp end = Timestamp.valueOf(ans.get(i).get(5));
			
				Calendar c = Calendar.getInstance();
				java.util.Date date = c.getTime();
				//System.out.println(date);
				Timestamp now = new Timestamp(date.getTime());
				
				if (start.compareTo(now) > 0) {
					ans.get(i).add("Pending");
				}
				else if (end.compareTo(now) < 0) {
					ans.get(i).add("Complete");
				} else {
					ans.get(i).add("Ongoing");
				}
			}
		} catch (SQLException e1){
			e1.printStackTrace();
		}	
		return ans;
	}
	
	public List<List<String>> viewServiceHistoryManager(int sid) {
		List<List<String>> ans = new ArrayList<>();
		
		try {
			String sql = "SELECT AID, LICENCE_ID, SERVICE_TYPE, SERVICE_DATE \n" + 
					"	 FROM SCHEDULE_SERVICE\n" + 
					"	 WHERE SCHEDULE_SERVICE.SID = '" + sid + "'\n" + 
					"	 ORDER BY AID";
			
			String sql1 = "SELECT AID,NAME \n" + 
					"	 FROM MECHANIC, SCHEDULE_SERVICE \n" + 
					"	 WHERE MECHANIC.EID = SCHEDULE_SERVICE.EID AND SCHEDULE_SERVICE.SID = '" + sid + "'\n" + 
					"	 ORDER BY AID";
			
			String sql2 = "CREATE OR REPLACE VIEW SPENT AS \n" + 
					"    SELECT MAINTENANCE_INCLUDE.SVID, SCHEDULE_SERVICE.CID, SUM(TIME)*3600 AS DURING\n" + 
					"    FROM BASIC_SERVICE, MAINTENANCE_INCLUDE, SCHEDULE_SERVICE\n" + 
					"    WHERE BASIC_SERVICE.BSID = MAINTENANCE_INCLUDE.BSID AND MAINTENANCE_INCLUDE.SVID = SCHEDULE_SERVICE.SVID AND SCHEDULE_SERVICE.SID = '" + sid + "'\n" + 
					"    GROUP BY MAINTENANCE_INCLUDE.SVID, SCHEDULE_SERVICE.CID \n";	
			
			String sql3 = "SELECT AID, SERVICE_DATE + NUMTODSINTERVAL(DURING, 'SECOND') AS SERVICE_END_DATE\n" + 
					"    FROM SCHEDULE_SERVICE, SPENT\n" + 
					"    WHERE SCHEDULE_SERVICE.SVID = SPENT.SVID AND SCHEDULE_SERVICE.CID = SPENT.CID\n" + 
					"    ORDER BY AID";
			
			String sql4 = "CREATE OR REPLACE VIEW SPENT2 AS \n" + 
					"    SELECT REPAIR_INCLUDE.RSVID, SCHEDULE_SERVICE.CID, SUM(TIME)*3600 AS DURING \n" + 
					"    FROM BASIC_SERVICE, REPAIR_INCLUDE, SCHEDULE_SERVICE\n" + 
					"    WHERE BASIC_SERVICE.BSID = REPAIR_INCLUDE.BSID AND REPAIR_INCLUDE.RSVID = SCHEDULE_SERVICE.RSVID AND SCHEDULE_SERVICE.SID = '" + sid + "' \n" + 
					"    GROUP BY REPAIR_INCLUDE.RSVID, SCHEDULE_SERVICE.CID";
			
			String sql5 = "SELECT AID, SERVICE_DATE + NUMTODSINTERVAL(DURING, 'SECOND') AS SERVICE_END_DATE \n" + 
					"    FROM SCHEDULE_SERVICE, SPENT2\n" + 
					"    WHERE SCHEDULE_SERVICE.RSVID = SPENT2.RSVID AND SCHEDULE_SERVICE.CID = SPENT2.CID\n" + 
					"    ORDER BY AID";
			
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(sql);
			Statement statement1 = conn.createStatement();
			ResultSet res1 = statement1.executeQuery(sql1);
			Statement statement2 = conn.createStatement();
			ResultSet res2 = statement2.executeQuery(sql2);
			Statement statement3 = conn.createStatement();
			ResultSet res3 = statement3.executeQuery(sql3);
			Statement statement4 = conn.createStatement();
			ResultSet res4 = statement4.executeQuery(sql4);
			Statement statement5 = conn.createStatement();
			ResultSet res5 = statement5.executeQuery(sql5);
			
			while (res.next() && res1.next()) {
				List<String> record = new ArrayList<>();
				record.add(res.getString("aid"));
				record.add(res.getString("licence_id"));
				record.add(res.getString("service_type"));
				record.add(res.getString("service_date"));
				record.add(res1.getString("name"));
				ans.add(record);
			}
			
			while (res3.next()) {			
				for (int i = 0; i < ans.size(); i++) {
					if (ans.get(i).get(0).equals(res3.getString("aid"))) {
						ans.get(i).add(res3.getString("service_end_date"));
					}
				}
			}
			
			while (res5.next()) {
				for (int i = 0; i < ans.size(); i++) {
					if (ans.get(i).get(0).equals(res5.getString("aid"))) {
						ans.get(i).add(res5.getString("service_end_date"));
					}
				}
			}
			
			for (int i = 0; i < ans.size(); i++) {
				Timestamp start = Timestamp.valueOf(ans.get(i).get(3));
				Timestamp end = Timestamp.valueOf(ans.get(i).get(5));
			
				Calendar c = Calendar.getInstance();
				java.util.Date date = c.getTime();
				//System.out.println(date);
				Timestamp now = new Timestamp(date.getTime());
				
				if (start.compareTo(now) > 0) {
					ans.get(i).add("Pending");
				}
				else if (end.compareTo(now) < 0) {
					ans.get(i).add("Complete");
				} else {
					ans.get(i).add("Ongoing");
				}
			}
			
		} catch (SQLException e1){
			e1.printStackTrace();
		}	
		return ans;
	}
	 

	public String daily_update_inventory(Date date, int eid) {
		StringBuilder out = new StringBuilder();
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select * from Schedule_Service where SERVICE_DATE = ? and sid = (select sid from employeemr_work_at where eid = ?)");
			pstmt.setDate(1, date);
			pstmt.setInt(2, eid);
			ResultSet res = pstmt.executeQuery();
		    //FOR each of the given date, we will update the inventory
			while (res.next()) {
				out.append(update_inventory(res));
//		    	System.out.println(res.getInt("rsvid"));
		    }
//	    	System.out.println("yes, we are here2");

			pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return out.toString();
	}
	
	private String update_inventory(ResultSet res) throws SQLException {
		int aid = res.getInt("aid");
		StringBuilder out = new StringBuilder();
		int sid = res.getInt("sid");
		String licence_id = res.getString("licence_id");
		int rsvid = res.getInt("rsvid");
		int svid = res.getInt("svid");
		if (rsvid == 0) {
			//this is a maintenance service
			PreparedStatement pstmt = conn.prepareStatement(
					"select pid, quantity from basic_service_need "
					+ "where bsid in "
					+ "(select bsid from maintenance_include where svid = ?) "
					+ "and cmid = (select car_model from maintenance_service where svid = ?)");
			pstmt.setInt(1, svid);
			pstmt.setInt(2, svid);
			ResultSet res2 = pstmt.executeQuery();
			while(res2.next()) {
				int pid = res2.getInt("pid");
				int qty = res2.getInt("quantity");
				if(update_part_qty(sid, pid, qty)) {
					out.append("updated apportment number " + aid + ", part number " + pid + " success!\n");
				} else {
					out.append("updated apportment number " + aid + ", part number " + pid + " fail!\n");
				}
			}
		} else {
			//this is repair service
			PreparedStatement pstmt = conn.prepareStatement(
					"select pid, quantity from basic_service_need "
					+ "where bsid in "
					+ "(select bsid from maintenance_include where svid = ?) "
					+ "and cmid = (select cmid from vehicle where licence_id = ?)");
			pstmt.setInt(1, svid);
			pstmt.setString(2, licence_id);
			ResultSet res2 = pstmt.executeQuery();
			while(res2.next()) {
				int pid = res2.getInt("pid");
				int qty = res2.getInt("quantity");
				if(update_part_qty(sid, pid, qty)) {
					out.append("updated apportment number " + aid + ", part number " + pid + " success!\n");
				} else {
					out.append("updated apportment number " + aid + ", part number " + pid + " fail!\n");
				}
			}
		}
		return out.toString();
	}
	
	private boolean update_part_qty(int sid, int pid, int qty) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"update zzha.inventory set quantity = (select quantity from inventory where pid = ? and sid = ?) - ? where pid = ? and sid = ?");
			pstmt.setInt(1, pid);
			pstmt.setInt(2, sid);
			pstmt.setInt(3, qty);
			pstmt.setInt(4, pid);
			pstmt.setInt(5, sid);
			int a = pstmt.executeUpdate();
			pstmt.close();
		    return a == 1;
		    } catch (SQLException e1) {
		      e1.printStackTrace();
		      System.out.println("update qty fail!!!!");
		    }
		return false;
	}	
	
	public String GetReportFromRsvid(int rsvid) {
		String Report = "";
		try {
			String sql = "select REPORT from REPAIR_SERVICE where RSVID = '" + rsvid + "'";
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(sql);
			if(res.next()) {
				Report = res.getString("report");
			}
			statement.close();
		}catch (SQLException e1) {
		      e1.printStackTrace();
		}
		return Report;
	}
	
	public Schedule_Service GetServiceFromAid(int Aid) {
		Schedule_Service service = new Schedule_Service();
		service.setAid(Aid);
		try {
			String sql = "select SERVICE_TYPE, SVID, RSVID, SID, CID, LICENCE_ID, EID, SERVICE_DATE, SERVICE_END_DATE from SCHEDULE_SERVICE where AID = '" + Aid + "'";
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(sql);
			if(res.next()) {
				service.setService_type(res.getString("service_type"));
				if(service.getService_type() == "MENT") {
					service.setSvid(res.getInt("svid"));
					res.getInt("rsvid");
				}
				else {
					res.getInt("svid");
					service.setRsvid(res.getInt("rsvid"));
				}
				service.setSid(res.getInt("sid"));
				service.setCid(res.getInt("cid"));
				service.setLicence_id(res.getString("licence_id"));
				service.setEid(res.getInt("eid"));
				service.setService_date(res.getTimestamp("service_date"));
				service.setService_end_date(res.getTimestamp("service_end_date"));
			}
			statement.close();
		}catch (SQLException e1) {
		      e1.printStackTrace();
		}
		return service;
	}
	
	public static void main(String[] args) {
		Schedule_ServiceDao schedule_ServiceDao = new Schedule_ServiceDao();

		
		Calendar c = Calendar.getInstance();
		java.util.Date date = c.getTime();
		System.out.println(date);
		
		
		//System.out.println(schedule_ServiceDao.viewServiceHistory(1001).toString());
		System.out.println(schedule_ServiceDao.getCustomerServiceDetail(1001).toString());
		schedule_ServiceDao.CloseConn();
		
	}
}
