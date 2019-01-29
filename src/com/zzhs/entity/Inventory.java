package com.zzhs.entity;

public class Inventory {
	private int pid;
	private int sid;
	private int thershold;
	private int quantity;
	private int minimal_order;
	
	public Inventory() {
		
	}
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public int getSid() {
		return sid;
	}
	
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public int getThershold() {
		return thershold;
	}
	
	public void getThershold(int thershold) {
		this.thershold = thershold;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getMinimal_order() {
		return minimal_order;
	}
	public void setMinimal_order(int minimal_order) {
		this.minimal_order = minimal_order;
	}
	
	@Override
	public String toString() {
		return "Inventory [pid = " + pid + ", sid = " + sid + ", thershold = " + thershold + ", quantity = " + quantity + ", minimal_order = " + minimal_order + "]";
	}
}
