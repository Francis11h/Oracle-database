package com.zzhs.entity;

public class Distributor {
	private int did;
	private String name;
	private String address;
	private String phone;
	
	public Distributor() {
		
	}
	
	public Distributor(int did, String name, String address, String phone) {
		super();
		this.did = did;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	public int getDid() {
		return did;
	}
	
	public void setDid(int did) {
		this.did = did;
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
	
	public void setaddress(String address) {
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
		return "Distributor [did = " + did + ", name = " + name + ", address = " + address + ", phone = " + phone + "]";
	}
}
