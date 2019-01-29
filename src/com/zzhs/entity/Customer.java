/**
 * This entity is to store customer info. 
 */
package com.zzhs.entity;

/**
 * @author zhangqi
 *
 */

public class Customer {
	

	private int cid;	
	
	private String email;
	
	private String password;
	
	private String name;
	
	private String address;
	
	private String phone;
	
	public Customer() {
		
	}
	
	public Customer(int cid, String email, String password, String name, String address, String phone) {
		super();
		this.cid = cid;
		this.email = email;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Customer [cid=" + cid + ", email=" + email + ", password=" + password + ", name=" + name + ", address="
				+ address + ", phone=" + phone + "]";
	}


	
}
