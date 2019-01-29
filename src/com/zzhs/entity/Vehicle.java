package com.zzhs.entity;

import java.sql.Date;

public class Vehicle {
	private String licence_id;
	private String make;
	private String model;
	private int year;
	private int mileage;
	private String serivce_type;
	private Date service_date;
	private Date purchase_date;
	
	public Vehicle() {
		
	}

	public Vehicle(String licence_id, String make, String model, int year, int mileage, String serivce_type,
			Date service_date, Date purchase_date) {
		super();
		this.licence_id = licence_id;
		this.make = make;
		this.model = model;
		this.year = year;
		this.mileage = mileage;
		this.serivce_type = serivce_type;
		this.service_date = service_date;
		this.purchase_date = purchase_date;
	}

	public String getLicence_id() {
		return licence_id;
	}

	public void setLicence_id(String licence_id) {
		this.licence_id = licence_id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public String getSerivce_type() {
		return serivce_type;
	}

	public void setSerivce_type(String serivce_type) {
		this.serivce_type = serivce_type;
	}

	public Date getService_date() {
		return service_date;
	}

	public void setService_date(Date service_date) {
		this.service_date = service_date;
	}

	public Date getPurchase_date() {
		return purchase_date;
	}

	public void setPurchase_date(Date purchase_date) {
		this.purchase_date = purchase_date;
	}

	@Override
	public String toString() {
		return "Vehicle [licence_id=" + licence_id + ", make=" + make + ", model=" + model + ", year=" + year
				+ ", mileage=" + mileage + ", serivce_type=" + serivce_type + ", service_date=" + service_date
				+ ", purchase_date=" + purchase_date + "]";
	}

}
