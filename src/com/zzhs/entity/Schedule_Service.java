package com.zzhs.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Schedule_Service {
	private int aid;
	private int svid;
	private int rsvid;
	private int sid;
	private int cid;
	private String licence_id;
	private String service_type;
	private int eid;
	private Timestamp service_date;
	private Timestamp service_end_date;
	
	public Schedule_Service() {
		
	}
	public Schedule_Service(int aid, int svid, int rsvid, int sid, int cid, String licence_id, String service_type, int eid, Timestamp service_date, Timestamp service_end_date) {
		super();
		this.aid = aid;
		this.svid = svid;
		this.rsvid = rsvid;
		this.sid = sid;
		this.cid = cid;
		this.licence_id = licence_id;
		this.service_type = service_type;
		this.eid = eid;
		this.service_date = service_date;
		this.service_end_date = service_end_date;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public int getSvid() {
		return svid;
	}
	public void setSvid(int svid) {
		this.svid = svid;
	}
	public int getRsvid() {
		return rsvid;
	}
	public void setRsvid(int rsvid) {
		this.rsvid = rsvid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
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
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public Timestamp getService_date() {
		return service_date;
	}
	public void setService_date(Timestamp service_date) {
		this.service_date = service_date;
	}
	public Timestamp getService_end_date() {
		return service_end_date;
	}
	public void setService_end_date(Timestamp service_end_date) {
		this.service_end_date = service_end_date;
	}
	@Override
	public String toString() {
		return "Schedule_Service [aid = " + aid + ", svid=" + svid + ", sid=" + sid + ", cid=" + cid + ", licence_id=" + licence_id
				+ ", eid=" + eid + ", service_date=" + service_date + "]";
	}
	
}
