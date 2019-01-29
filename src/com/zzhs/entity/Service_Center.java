package com.zzhs.entity;

public class Service_Center {
	private int sid;
	private String name;
	private String address;
	private String phone;
	
	public Service_Center() {
		
	}

	public Service_Center(int sid, String name, String address, String phone) {
		super();
		this.sid = sid;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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

	@Override
	public String toString() {
		return "Service_Center [sid=" + sid + ", name=" + name + ", address=" + address + ", phone=" + phone + "]";
	}
	
}
