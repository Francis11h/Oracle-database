package com.zzhs.entity;

import java.sql.Date;

public class Order_From_Service_Center {
	private int ssid;
	private int oid;
	private int quantity;
	private int pid;
	private Date order_date; 
	private String status;
	private int bsid;
	
	public Order_From_Service_Center() {
		
	}
	
	public Order_From_Service_Center(int ssid, int oid, int quantity, int pid, Date order_date, String status, int bsid) {
		super();
		this.ssid = ssid;
		this.oid = oid;
		this.quantity = quantity;
		this.pid = pid;
		this.order_date = order_date;
		this.status = status;
		this.bsid = bsid;
	}
	
	public int getSsid() {
		return ssid;
	}
	
	public void setSsid(int ssid) {
		this.ssid = ssid;
	}
	
	public int getOid() {
		return oid;
	}
	
	public void setOid(int oid) {
		this.oid = oid;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public Date getOrder_date() {
		return order_date;
	}
	
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getBsid() {
		return bsid;
	}
	
	public void setBsid(int bsid) {
		this.bsid = bsid;
	}
	
	@Override
	public String toString() {
		return "Order_From_Service_Center [ssid = " + ssid +", oid = " + oid + ", quantity = " + quantity + ","
				+ " pid = " + pid + ", order_date = " + order_date + ", status = " + status + ", bsid = " + bsid + "]";
	}
}
