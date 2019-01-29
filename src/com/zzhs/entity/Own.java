package com.zzhs.entity;

public class Own {
	
	private int cid;
	private String licence_id;
	
	public Own() {
		
	}
	
	public Own(int cid, String licence_id) {
		super();
		this.cid = cid;
		this.licence_id = licence_id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getLicence_id() {
		return licence_id;
	}

	public void setLicence_id(String licence_id) {
		this.licence_id = licence_id;
	}

	@Override
	public String toString() {
		return "Own [cid=" + cid + ", licence_id=" + licence_id + "]";
	}


}
