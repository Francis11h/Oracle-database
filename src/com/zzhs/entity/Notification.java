package com.zzhs.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Notification {
	private int did;
	private Timestamp notify_time;
	private Date due_date;
	private String content;
	private int sid;
	
	public Notification() {
		
	}

	public Notification(int did, Timestamp notify_time, Date due_date, String content, int sid) {
		super();
		this.did = did;
		this.notify_time = notify_time;
		this.due_date = due_date;
		this.content = content;
		this.sid = sid;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public Timestamp getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(Timestamp notify_time) {
		this.notify_time = notify_time;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	@Override
	public String toString() {
		return "Notification [did=" + did + ", notify_time=" + notify_time + ", due_date=" + due_date + ", content="
				+ content + ", sid=" + sid + "]";
	}
	
}
