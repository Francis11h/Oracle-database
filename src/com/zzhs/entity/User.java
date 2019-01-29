/**
 * This entity is used for user sign in. 
 */
package com.zzhs.entity;

/**
 * @author zhangqi
 *
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="id")
	private int id;
	
	@Column(name="usertype")
	private String userType;
	
	public User() {
		
	}

	/* userType can be "manager", "receptionist" or "customer",
	 * this constructor will be used in sign up process, which will
	 * assigned userType as 'customer', when manager create receptionist,
	 * it will be assigned as 'receptionist'*/
	
	public User(String email, String password, String userType, int id) {
		super();
		this.email = email;
		this.password = password;
		this.userType = userType;
		this.id = id;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + ", eid=" + id + ", userType=" + userType + "]";
	}
	
	
}
