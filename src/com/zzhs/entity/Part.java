package com.zzhs.entity;

public class Part {
	
	private int pid;
	private String name;
	private double price;
	
	public Part() {
		
	}
	
	public Part(int pid, String name, double price) {
		super();
		this.pid = pid;
		this.name = name;
		this.price = price;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Part [pid=" + pid + ", name=" + name + ", price=" + price + "]";
	}
	
}
