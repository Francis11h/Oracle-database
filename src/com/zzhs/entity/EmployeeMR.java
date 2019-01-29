package com.zzhs.entity;

import java.sql.Date;

public class EmployeeMR {
	

	private int eid;
	private String email;
	private String password;
	private String userType;
	private String name;
	private String address;
	private String phone;
	private int monthlyPay;
	private String payroll;	
	private Date start_date;
	
	public EmployeeMR() {
		
	}

	public EmployeeMR(int eid, String email, String password, String userType, String name, String address,
			String phone, int monthlyPay, String payroll, Date start_date) {
		super();
		this.eid = eid;
		this.email = email;
		this.password = password;
		this.userType = userType;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.monthlyPay = monthlyPay;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public int getMonthlyPay() {
		return monthlyPay;
	}

	public void setMonthlyPay(int monthlyPay) {
		this.monthlyPay = monthlyPay;
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
		return "EmployeeMR [eid=" + eid + ", email=" + email + ", password=" + password + ", userType=" + userType
				+ ", name=" + name + ", address=" + address + ", phone=" + phone + ", monthlyPay=" + monthlyPay
				+ ", payroll=" + payroll + ", start_date=" + start_date + "]";
	}

	
	
}
