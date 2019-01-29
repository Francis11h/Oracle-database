package com.zzhs.entity;

public class Maintenance_Service {
	private int svid;
	private String service_Type;
	private int is_First_Time;
	private double fee;
	
	public Maintenance_Service() {
		
	}

	public Maintenance_Service(int svid, String service_Type, int is_First_Time, double fee) {
		super();
		this.svid = svid;
		this.service_Type = service_Type;
		this.is_First_Time = is_First_Time;
		this.fee = fee;
	}

	public int getSvid() {
		return svid;
	}

	public void setSvid(int svid) {
		this.svid = svid;
	}

	public String getService_Type() {
		return service_Type;
	}

	public void setService_Type(String service_Type) {
		this.service_Type = service_Type;
	}

	public int getIs_First_Time() {
		return is_First_Time;
	}

	public void setIs_First_Time(int is_First_Time) {
		this.is_First_Time = is_First_Time;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	@Override
	public String toString() {
		return "Maintenance_Service [svid=" + svid + ", service_Type=" + service_Type + ", is_First_Time="
				+ is_First_Time + ", fee=" + fee + "]";
	}
	
	
}
