package com.zzhs.entity;

public class Basic_Service_Need {
	private String name;
	private  int pid;
	private int qty;
	
	public Basic_Service_Need() {
	
	}
	
	public Basic_Service_Need(String name, int pid, int qty) {
		super();
		this.name = name;
		this.pid = pid;
		this.qty = qty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "Basic_Service_Need [name=" + name + ", pid=" + pid + ", qty=" + qty + "]";
	}
	
	
	
}
