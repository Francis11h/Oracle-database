package com.zzhs.entity;

public class Maintenance_Include {
	private int svid;
	private String name;
	
	public Maintenance_Include() {
		
	}
	
	public Maintenance_Include(int svid, String name) {
		super();
		this.svid = svid;
		this.name = name;
	}
	
	public int getSvid() {
		return svid;
	}
	
	public void setSvid(int svid) {
		this.svid = svid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Maintenance_Include [svid = " + svid + ", name=" + name + "]";
	}
}
