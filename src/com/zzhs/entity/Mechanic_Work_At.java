package com.zzhs.entity;

public class Mechanic_Work_At {
	private int eid;
	private int sid;
	
	public Mechanic_Work_At() {
		
	}
	public Mechanic_Work_At(int eid, int sid) {
		super();
		this.eid = eid;
		this.sid = sid;
	}
	
	public int getEid() {
		return eid;
	}
	
	public void setEid(int eid) {
		this.eid = eid;
	}
	
	public int getSid() {
		return sid;
	}
	
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	@Override
	public String toString() {
		return "Mechanic_Work_At [eid = " + eid + ", sid = " + sid + "]";
	}
}
