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
import javax.sound.midi.Patch;

import org.hibernate.boot.model.naming.DatabaseIdentifier;
import org.hibernate.hql.spi.id.cte.AbstractCteValuesListBulkIdHandler;

import com.zzhs.entity.Vehicle;

import javassist.expr.NewArray;
import oracle.jdbc.dcn.QueryChangeDescription.QueryChangeEventType;
import oracle.jdbc.proxy.annotation.GetCreator;
import oracle.net.aso.s;

import com.zzhs.entity.Customer;
import com.zzhs.entity.Schedule_Service;

public class OrderDao {
	
	private Connection conn; 
	
	public OrderDao(){
		conn = Dbconnection.getConnection();
	}
	
	public String getOrder(int eid) {
		List<String> dOrders = listDOrder(eid);
		List<String> sOrders = listSOrder(eid);
		StringBuilder sBuilder = new StringBuilder();
		for (String string: dOrders) {
			sBuilder.append(string);
			sBuilder.append("\n");
		}
		for (String string: sOrders) {
			sBuilder.append(string);
			sBuilder.append("\n");
		}
		return sBuilder.toString();
	}
	
	private List<String> listDOrder(int eid) {
		List<String> orders = new ArrayList<>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select ofd.doid, bs.name as bname, d.name as dname, pt.name as pname, "
					+ "ofd.quantity, ofd.datetime, ofd.status, ofd.expected_delivery_date, ofd.actual_delivery_date "
					+ "from order_from_distributor ofd, distributor d, service_center bs, part pt "
					+ "where ofd.bsid = bs.sid and ofd.sdid = d.did and ofd.pid = pt.pid "
					+ "and ofd.bsid = (select sid from employeemr_work_at where eid = ?)");
			pstmt.setInt(1, eid);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				String string = String.format(
						"OrderID %s %s, %s ordered %s %s from %s on %s, expected deliver on %s",
						res.getInt("doid"),
						res.getString("status"),
						res.getString("bname"),
						res.getInt("quantity"),
						res.getString("pname"),
						res.getString("dname"),
						res.getDate("datetime"),
						res.getDate("expected_delivery_date"));
				if("complete".equals(res.getString("status").toLowerCase())) {
					string = String.format("%s, actual delivered on %s.", string, res.getDate("actual_delivery_date"));
				}
				orders.add(string);
			}
		    pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return orders;
	}	

	private List<String> listSOrder(int eid) {
		List<String> orders = new ArrayList<>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select ofd.soid, bs.name as bname, ss.name as sname, pt.name as pname, "
					+ "ofd.quantity, ofd.order_date, ofd.status, ofd.expected_delivery_date, ofd.actual_delivery_date "
					+ "from order_from_service_center ofd, service_center ss, service_center bs, part pt "
					+ "where ofd.bsid = bs.sid and ofd.ssid = ss.sid and ofd.pid = pt.pid "
					+ "and ofd.bsid = (select sid from employeemr_work_at where eid = ?)");
			pstmt.setInt(1, eid);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				String string = String.format(
						"OrderID %s %s, %s ordered %s %s from %s on %s, expected deliver on %s",
						res.getInt("soid"),
						res.getString("status"),
						res.getString("bname"),
						res.getInt("quantity"),
						res.getString("pname"),
						res.getString("sname"),
						res.getDate("order_date"),
						res.getDate("expected_delivery_date"));
				if("complete".equals(res.getString("status").toLowerCase())) {
					string = String.format("%s, actual delivered on %s.", string, res.getDate("actual_delivery_date"));
				}
				orders.add(string);
			}
		    pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return orders;
	}
	
	public String addOrder(int eid, String partID, String quantity) {
		try {
			int pid = Integer.parseInt(partID);
			int qty = Integer.parseInt(quantity);
			PreparedStatement pstmt = conn.prepareStatement(
					"select minimal_order from inventory iv, employeemr_work_at em where em.eid = ? and em.sid = iv.sid and iv.pid = ?");
			pstmt.setInt(1, eid);
			pstmt.setInt(2, pid);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				int minimal_order = res.getInt("minimal_order");
				pstmt.close();
				if (minimal_order <= qty) {
					String orderlog = order_from_sc(eid,pid,qty);
					if (orderlog != null ) {
						return orderlog;
					} else {
						System.out.println("need distributor");
						return order_from_dis(eid, pid, qty);
					}
				} else {
					return "order quantity must larger than minimal order number.";
				}
			} else {
				pstmt.close();
				return "no such part number in the invertory, please define in invertory first.";
			}
		} catch (NumberFormatException | SQLException e) {
			return "Part id and quantity must be a number, sql query failed.";
		}
	}
	
	public Date addOrderAuto(int sid, int partID, int quantity) {
		//try to find if the pending order can fulfill the required quantity
		Date expectedDate = checkPendingOrder(sid, partID, quantity);
		if (expectedDate != null) {
			return expectedDate;
		} else {	
			try {
				int pid = partID;
				int qty = quantity;
				PreparedStatement pstmt = conn.prepareStatement(
						"select iv.minimal_order, mr.eid from inventory iv, employeemr_work_at mr where mr.sid = ? and mr.sid = iv.sid and iv.pid = ?");
				pstmt.setInt(1, sid);
				pstmt.setInt(2, pid);
				ResultSet res = pstmt.executeQuery();
				if(res.next()) {
					int minimal_order = res.getInt("minimal_order");
					int eid = res.getInt("eid");
					pstmt.close();
					if (minimal_order > qty) {
						qty = minimal_order;
					}
					String orderlog = order_from_sc(eid,pid,qty);
					if (orderlog != null ) {
						return getSOrderExpectDate(sid, pid);
					} else {
						System.out.println("need distributor");
						orderlog = order_from_dis(eid, pid, qty);
						return getDOrderExpectDate(sid, pid);
					}
				} 
				pstmt.close();
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	private Date checkPendingOrder(int sid, int pid, int qty) {
		int orderedQty = 0;
		Date expectedDate = new Date(0);
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select expected_delivery_date, quantity "
					+ "from order_from_service_center "
					+ "where bsid = ? and pid = ? and status = 'pending' ");
			pstmt.setInt(1, sid);
			pstmt.setInt(2, pid);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				Date date = res.getDate("expected_delivery_date");
				orderedQty += res.getInt("quantity");
				if (expectedDate.compareTo(date) < 0) {
					expectedDate = date;
				}
				if (orderedQty >= qty) {
					return expectedDate;
				}
			}
			pstmt.close();
			//try from distributor order table
			pstmt = conn.prepareStatement(
					"select expected_delivery_date, quantity "
					+ "from order_from_distributor "
					+ "where bsid = ? and pid = ? and status = 'pending' ");
			pstmt.setInt(1, sid);
			pstmt.setInt(2, pid);
			res = pstmt.executeQuery();
			while(res.next()) {
				Date date = res.getDate("expected_delivery_date");
				orderedQty += res.getInt("quantity");
				if (expectedDate.compareTo(date) < 0) {
					expectedDate = date;
				}
				if (orderedQty >= qty) {
					return expectedDate;
				}
			}
			pstmt.close();
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return null;
	}
	
	private Date getSOrderExpectDate(int sid, int pid) {
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select expected_delivery_date "
					+ "from order_from_service_center "
					+ "where bsid = ? and pid = ?");
			pstmt.setInt(1, sid);
			pstmt.setInt(2, pid);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				return res.getDate("expected_delivery_date");
			}
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return null;
	}
	
	public Date getDOrderExpectDate(int sid, int pid) {
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select expected_delivery_date "
					+ "from order_from_distributor "
					+ "where bsid = ? and pid = ?");
			pstmt.setInt(1, sid);
			pstmt.setInt(2, pid);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				return res.getDate("expected_delivery_date");
			}
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return null;
	}
	
	
	private String order_from_sc(int eid, int pid, int qty) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select iv.sid as sid_seller, em.sid, iv.quantity, iv.threshold from inventory iv, employeemr_work_at em where em.eid = ? and em.sid <> iv.sid and iv.pid = ?");
			pstmt.setInt(1, eid);
			pstmt.setInt(2, pid);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				int sid_buyer = res.getInt("sid");
				int sid_seller = res.getInt("sid_seller");
				int qty_seller = res.getInt("quantity");
				int thr_seller = res.getInt("threshold");
				if (qty_seller - qty >= thr_seller 
						&& update_seller_qty(sid_seller, pid, qty_seller - qty) 
						&& insert_sorder(sid_seller, pid, qty, sid_buyer)) {
					return getLastSOrder(eid, pid);
				}
			}
				
		} catch ( SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String order_from_dis(int eid, int pid, int qty) {
		int days = get_deliverDays(pid);
		int did = get_Did(pid);
		if ( days != 0 && did != 0) {
			try {
				PreparedStatement pstmt = conn.prepareStatement(
						"insert into order_from_distributor (sdid, doid, quantity, pid, datetime, status, bsid, expected_delivery_date) values (?, order_sequence.nextval, ?, ?, ?, ?, (select sid from employeemr_work_at where eid = ?), ?)");
				pstmt.setInt(1, did);
				pstmt.setInt(2, qty);
				pstmt.setInt(3, pid);
				pstmt.setDate(4, new Date(Calendar.getInstance().getTime().getTime()));
				pstmt.setString(5, "pending");
				pstmt.setInt(6, eid);
				pstmt.setDate(7, addWeekday(days));
				int row = pstmt.executeUpdate();
				pstmt.close();
				if (row == 1) {
					return getLastDOrder(eid, pid);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private int get_deliverDays(int pid) {
		int days = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select days from distributor_delivery where pid = ?");
			pstmt.setInt(1, pid);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				days = res.getInt("days");
			}
			pstmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return days;
	}
	
	private int get_Did(int pid) {
		int did = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select did from distributor_delivery where pid = ?");
			pstmt.setInt(1, pid);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				did = res.getInt("did");
			}
			pstmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return did;
	}
	
	private boolean update_seller_qty(int sid_seller, int pid, int qty) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"update inventory set quantity = ? where sid = ? and pid = ?");
			pstmt.setInt(1, qty);
			pstmt.setInt(2, sid_seller);
			pstmt.setInt(3, pid);
			int row = pstmt.executeUpdate();
			pstmt.close();
			return row == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean insert_sorder(int sid_seller, int pid, int qty, int sid_buyer) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into order_from_service_center (ssid, soid, quantity, pid, order_date, status, bsid, expected_delivery_date) values (?, order_sequence.nextval, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, sid_seller);
			pstmt.setInt(2, qty);
			pstmt.setInt(3, pid);
			pstmt.setDate(4, new Date(Calendar.getInstance().getTime().getTime()));
			pstmt.setString(5, "pending");
			pstmt.setInt(6, sid_buyer);
			pstmt.setDate(7, addWeekday(1));
			int row = pstmt.executeUpdate();
			pstmt.close();
			return row == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private String getLastSOrder(int eid, int pid) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select ofd.soid, bs.name as bname, ss.name as sname, pt.name as pname, "
					+ "ofd.quantity, ofd.order_date, ofd.status, ofd.expected_delivery_date, ofd.actual_delivery_date "
					+ "from order_from_service_center ofd, service_center ss, service_center bs, part pt "
					+ "where ofd.bsid = bs.sid and ofd.ssid = ss.sid and ofd.pid = pt.pid "
					+ "and ofd.bsid = (select sid from employeemr_work_at where eid = ?) and ofd.pid = ? order by ofd.soid desc");
			pstmt.setInt(1, eid);
			pstmt.setInt(2, pid);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				String string = String.format(
						"OrderID %s %s, %s ordered %s %s from %s on %s, expected deliver on %s",
						res.getInt("soid"),
						res.getString("status"),
						res.getString("bname"),
						res.getInt("quantity"),
						res.getString("pname"),
						res.getString("sname"),
						res.getDate("order_date"),
						res.getDate("expected_delivery_date"));
			    pstmt.close();
			    return string;
			}
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return null;
	}
	private String getLastDOrder(int eid, int pid) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select ofd.doid, bs.name as bname, d.name as dname, pt.name as pname, "
					+ "ofd.quantity, ofd.datetime, ofd.status, ofd.expected_delivery_date, ofd.actual_delivery_date "
					+ "from order_from_distributor ofd, distributor d, service_center bs, part pt "
					+ "where ofd.bsid = bs.sid and ofd.sdid = d.did and ofd.pid = pt.pid "
					+ "and ofd.bsid = (select sid from employeemr_work_at where eid = ?) and ofd.pid = ? order by ofd.doid desc");
			pstmt.setInt(1, eid);
			pstmt.setInt(2, pid);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				String string = String.format(
						"OrderID %s %s, %s ordered %s %s from %s on %s, expected deliver on %s",
						res.getInt("doid"),
						res.getString("status"),
						res.getString("bname"),
						res.getInt("quantity"),
						res.getString("pname"),
						res.getString("dname"),
						res.getDate("datetime"),
						res.getDate("expected_delivery_date"));
			    pstmt.close();
			    return string;
			}
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return null;
	}
	
	public static Date addWeekday(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		for(int i = 0;i <= days;)
	    {
	    	calendar.add(Calendar.DAY_OF_MONTH, 1);
//	    	System.out.println(calendar.getTime());
//	    	System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
//	    	System.out.println(i);

	    	//here even sat and sun are added
	        //but at the end it goes to the correct week day.
	        //because i is only increased if it is week day
	        if(1< calendar.get(Calendar.DAY_OF_WEEK) && calendar.get(Calendar.DAY_OF_WEEK)<7)
	        {
	            i++;
	        }
	    }
	    return new Date(calendar.getTime().getTime());
	}
	
	
	public String updateOrder(int oid, int eid) {
		String out = updateSOrder(oid, eid);
		if (out != null) {
			return out;
		} else {
			return updateDOrder(oid, eid);
		}
	}
	
	private String updateSOrder(int oid, int eid) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select pid, status, quantity from order_from_service_center where soid = ?");
			pstmt.setInt(1, oid);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				String status = res.getString("status");
				int pid = res.getInt("pid");
				int qty = res.getInt("quantity"); 
				if ("pending".equals(status.toLowerCase())) {
					pstmt = conn.prepareStatement("update order_from_service_center set status = 'complete', actual_delivery_date = ? where soid = ?");
					pstmt.setDate(1, new Date(Calendar.getInstance().getTime().getTime()));
					pstmt.setInt(2, oid);
					int row = pstmt.executeUpdate();
					pstmt = conn.prepareStatement("update inventory set quantity = quantity + ? where pid = ? and sid = (select sid from employeemr_work_at where eid = ?)");
					pstmt.setInt(1, qty);
					pstmt.setInt(2, pid);
					pstmt.setInt(3, eid);
					int row2 = pstmt.executeUpdate();
					pstmt.close();
					if (row == 1 && row2 == 1) {
						return String.format("Part id %s, %s of these parts has been updated into inventory.", pid, qty);
					}
				}
			}
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return null;
	}
	
	private String updateDOrder(int oid, int eid) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"select pid, status, quantity from order_from_distributor where doid = ?");
			pstmt.setInt(1, oid);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				String status = res.getString("status");
				int pid = res.getInt("pid");
				int qty = res.getInt("quantity"); 
				if ("pending".equals(status.toLowerCase())) {
					pstmt = conn.prepareStatement("update order_from_distributor set status = 'complete', actual_delivery_date = ? where doid = ?");
					pstmt.setDate(1, new Date(Calendar.getInstance().getTime().getTime()));
					pstmt.setInt(2, oid);
					int row = pstmt.executeUpdate();
					pstmt = conn.prepareStatement("update inventory set quantity = quantity + ? where pid = ? and sid = (select sid from employeemr_work_at where eid = ?)");
					pstmt.setInt(1, qty);
					pstmt.setInt(2, pid);
					pstmt.setInt(3, eid);
					int row2 = pstmt.executeUpdate();
					pstmt.close();
					if (row == 1 && row2 == 1) {
						return String.format("Part id %s, %s of these parts has been updated into inventory.", pid, qty);
					}
				}
			}
		} catch (SQLException e1) {
		    e1.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		OrderDao orderDao = new OrderDao();
//		System.out.println(orderDao.getOrder(634622236));
		System.out.println(orderDao.getLastSOrder(634622236, 24));
//		System.out.println(addWeekday(0));
	}
}
