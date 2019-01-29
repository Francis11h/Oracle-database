package com.zzhs.entity;

public class Basic_Service {
	private String name;
	private int time;
	private double chargeRate;
	
	public Basic_Service() {
		
	}

	public Basic_Service(String name, int time, double chargeRate) {
		super();
		this.name = name;
		this.time = time;
		this.chargeRate = chargeRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(double chargeRate) {
		this.chargeRate = chargeRate;
	}

	@Override
	public String toString() {
		return "Basic_Service [name=" + name + ", time=" + time + ", chargeRate=" + chargeRate + "]";
	}
	
}
