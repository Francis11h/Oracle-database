package com.zzhs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.property.access.spi.GetterMethodImpl;
import org.omg.CORBA.TIMEOUT;

public class PayrollDao {
	
	private Connection conn;
	
	public PayrollDao(){
		
		conn = Dbconnection.getConnection();
	}

	public String generatePayroll(int eid, int meid) {
		String out = null;
		try {
			//try from employeemr
			PreparedStatement pstmt = conn.prepareStatement("select em.name, em.monthly_pay, em.start_date "
					+ "from employeemr em, employeemr_work_at ema "
					+ "where em.eid = ? and em.eid = ema.eid and ema.sid = (select sid from employeemr_work_at where eid = ?)");
			pstmt.setInt(1, eid);
			pstmt.setInt(2, meid);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				Date start_date = res.getDate("start_date");
				int monthly_pay = res.getInt("monthly_pay");
				String paycheck_front = String.format("Paycheck to %s, EID: %s, "
						+ "Compensation: Monthly, %s$/month, ", res.getString("name"), eid, monthly_pay);
				pstmt.close();
				out = paycheck_front + calculateMREarns(monthly_pay, start_date);
			} else {
				//try from mechanic
				PreparedStatement pstmt2 = conn.prepareStatement("select em.name, em.hourly_pay, em.start_date "
						+ "from mechanic em, mechanic_work_at ema "
						+ "where em.eid = ? and em.eid = ema.eid and ema.sid = (select sid from employeemr_work_at where eid = ?)");
				pstmt2.setInt(1, eid);
				pstmt2.setInt(2, meid);
				ResultSet res2 = pstmt2.executeQuery();
				if (res2.next()) {
					Date start_date = res2.getDate("start_date");
					int hourly_pay = res2.getInt("hourly_pay");
					String paycheck_front = String.format("Paycheck to %s, EID: %s, "
						+ "Compensation: hourly, %s$/hour, ", res2.getString("name"), eid, hourly_pay);
					pstmt2.close();
					out = paycheck_front + calculateMeEarns(eid, hourly_pay, start_date);
				} else {
					out = "No such employee int your service center.";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}
	// private static String calculateEarns(int monthly_pay, Calendar sDate) {
	private static String calculateMREarns(int monthly_pay, Date start_date) {
		List<Calendar> payDays = generatePayrollDate();
		Calendar sDate = Calendar.getInstance();
		sDate.setTime(start_date);
		float year_pay;
		float cur_pay;
		float paiddays;
		Calendar sPayday;
		Calendar ePayday;
		if(payDays.size() < 2) {
			// this means current day is not reach the first pay check day yet.
			return "no paycheck has issued at this time.";
		} else {
			// find the start day index in the current years payroll
			int i = 0;
			for (Calendar calendar : payDays) {
				if(sDate.before(calendar)) {
					break;
				}
				i++;
			}
			if (i == 0) {
				//start_date is before this year
				cur_pay = monthly_pay / 2;
				year_pay = (payDays.size() - 1) * cur_pay;
				sPayday = payDays.get(payDays.size()-2);
				ePayday = payDays.get(payDays.size()-1);
				paiddays = (int) ((ePayday.getTimeInMillis() - sPayday.getTimeInMillis()) / (1000 * 60 * 60 * 24));
				return String.format("Pay period: %s to %s total %s days, paid %s $ on %s, year total: %s $.", 
						new Date(sPayday.getTime().getTime()),
						new Date(ePayday.getTime().getTime()),
						paiddays,
						cur_pay,
						new Date(ePayday.getTime().getTime()),
						year_pay
						);
			} else {
				//start_date is somewhere this year
				if(i == payDays.size()){
					//start_Date is after the last pay check date
					return "no paycheck has issued at this time."; 
				} else {
					// start_Date is before last pay check period
					int start_paycheck_Days = (int) ((payDays.get(i).getTimeInMillis() - sDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
					int maxdays_month = sDate.getActualMaximum(Calendar.DAY_OF_MONTH);
					int start_pay = Math.round(monthly_pay * ((float)start_paycheck_Days / maxdays_month));
					year_pay = start_pay + (payDays.size() - i - 1) * (monthly_pay / 2);
					
					ePayday = payDays.get(payDays.size()-1);
					if (i == payDays.size() - 1){
						//start_date is right before the last pay day
						sPayday = sDate;
						cur_pay = start_pay;
						paiddays = start_paycheck_Days;
					} else {
						sPayday = payDays.get(payDays.size() - 2);
						cur_pay = monthly_pay / 2;
						paiddays = (int) ((payDays.get(payDays.size() - 1).getTimeInMillis() - payDays.get(payDays.size() - 2).getTimeInMillis()) / (1000 * 60 * 60 * 24));
					}
					return String.format("Pay period: %s to %s total %s days, paid %s $ on %s, year total: %s $.", 
						new Date(sPayday.getTime().getTime()),
						new Date(ePayday.getTime().getTime()),
						paiddays,
						cur_pay,
						new Date(ePayday.getTime().getTime()),
						year_pay);
				}
			}
		}
	}
	
	private String calculateMeEarns(int eid, int hourly_pay, Date start_date) {
		List<Calendar> payDays = generatePayrollDate();
		Calendar sDate = Calendar.getInstance();
		sDate.setTime(start_date);
		float year_pay;
		float cur_pay;
		float paidhours;
		Calendar sPayday;
		Calendar ePayday;
		if(payDays.size() < 2) {
			// this means current day is not reach the first pay check day yet.
			return "no paycheck has issued at this time.";
		} else {
			sPayday = payDays.get(payDays.size()-2);
			// sPayday.set(Calendar.HOUR, 1);
			ePayday = payDays.get(payDays.size()-1);
			// ePayday.set(Calendar.HOUR_OF_DAY, 11);
			// // ePayday.set(Calendar.PM, 1);
			paidhours = getHourWorked(eid, sPayday, ePayday);
			cur_pay = paidhours * hourly_pay;
			Calendar firstDayofThisYear = getThisYearFirstDay();
			year_pay = getHourWorked(eid, firstDayofThisYear, ePayday) * hourly_pay;
			return String.format("Pay period: %s to %s total %s hours, paid %s $ on %s, year total: %s $.", 
					new Date(sPayday.getTime().getTime()),
					new Date(ePayday.getTime().getTime()),
					paidhours,
					cur_pay,
					new Date(ePayday.getTime().getTime()),
					year_pay
					);
		}
	}

	private float getHourWorked(int eid, Calendar sdate, Calendar edate){
		Timestamp sdate_sql = new Timestamp(sdate.getTime().getTime());
		System.out.print("\nsdate_sql: " + sdate_sql.toString());
		Timestamp edate_sql = new Timestamp(edate.getTime().getTime());
		System.out.print("\nedate_sql: " + edate_sql.toString());
		long hours_by_mil = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement("select eid, service_end_date te, service_date as ts "
			+ "from schedule_service where eid = ? and service_end_date < ? and service_end_date > ? ");
			pstmt.setInt(1, eid);
			pstmt.setTimestamp(2, edate_sql);
			pstmt.setTimestamp(3, sdate_sql);
			ResultSet res = pstmt.executeQuery();
			
			while (res.next()) {
				Timestamp te = res.getTimestamp("te");
				Timestamp ts = res.getTimestamp("ts");
				hours_by_mil += te.getTime() - ts.getTime();
				System.out.print("\nhours_by_mil: " + hours_by_mil + ", tf = " + te);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (float)hours_by_mil / (1000 * 60 *60 );
	}
	
	private static List<Calendar> generatePayrollDate() {
		List<Calendar> payDays = new ArrayList<>();
		Calendar today = Calendar.getInstance();
//		today.set(2017, 0, 15);
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH);
		int day = today.get(Calendar.DATE);
		for (int i = 0; i < month; i++) {
			Calendar date = Calendar.getInstance();
			date.set(year, i, 1);
			payDays.add(date);
			Calendar date2 = Calendar.getInstance();
			date2.set(year, i, 15);
			payDays.add(date2);
		}
		Calendar date = Calendar.getInstance();
		date.set(year, month, 1);
		payDays.add(date);
		if ( day >= 15) {
			Calendar date2 = Calendar.getInstance();
			date2.set(year, month, 15);
			payDays.add(date2);
		}
		return payDays;
	}

	private  Calendar getThisYearFirstDay() {
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		today.set(year, 0, 1, 0, 0, 0);
		return today;
	}
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}		
	public static void main(String[] args) {
//		List<Calendar> paydays = generatePayrollDate();
//		Calendar today = Calendar.getInstance();
//
//		for (Calendar calendar : paydays) {
//			System.out.println((calendar.getTime().getTime() - today.getTime().getTime()) / (1000 * 60 * 60 * 24));
//		}
		// PayrollDao payrollDao = new PayrollDao();
		// System.out.println(payrollDao.generatePayroll(634622236, 950932130));
		// payrollDao.close();
		// Calendar Sday = Calendar.getInstance();
		// Sday.set(2018, 0, 2);
		// System.out.println(calculateEarns(8000, Sday));
	}
	
}
