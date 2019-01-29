package com.zzhs.entity;

import java.sql.Date;

public class Mechanic {
	

	private int eid;
	private String email;
	private String password;
	private String name;
	private String address;
	private String phone;
	private int hour_Worked;
	private int hourly_pay;
	private String payroll;	
	private Date start_date;
	
	public Mechanic() {
		
	}

	public Mechanic(int eid, String email, String password, String name, String address, String phone, int hour_Worked,
			int hourly_pay, String payroll, Date start_date) {
		super();
		this.eid = eid;
		this.email = email;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.hour_Worked = hour_Worked;
		this.hourly_pay = hourly_pay;
		this.payroll = payroll;
		this.start_date = start_date;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getHour_Worked() {
		return hour_Worked;
	}

	public void setHour_Worked(int hour_Worked) {
		this.hour_Worked = hour_Worked;
	}

	public int getHourly_pay() {
		return hourly_pay;
	}

	public void setHourly_pay(int hourly_pay) {
		this.hourly_pay = hourly_pay;
	}

	public String getPayroll() {
		return payroll;
	}

	public void setPayroll(String payroll) {
		this.payroll = payroll;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	@Override
	public String toString() {
		return "Mechanic [eid=" + eid + ", email=" + email + ", password=" + password + ", name=" + name + ", address="
				+ address + ", phone=" + phone + ", hour_Worked=" + hour_Worked + ", hourly_pay=" + hourly_pay
				+ ", payroll=" + payroll + ", start_date=" + start_date + "]";
	}
		
}
