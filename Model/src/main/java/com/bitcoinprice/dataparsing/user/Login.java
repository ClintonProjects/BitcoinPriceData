package com.bitcoinprice.dataparsing.user;

import javax.persistence.Id;

import org.bson.types.ObjectId;

import com.bitcoinprice.dataparsing.enums.Usertype;

public class Login {

	@Id
	private ObjectId _id;
	public String email;
	public String password;
	public Usertype userType;

	public Login(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Easier access to have this code here:
	public void setUserToUser() {
		userType = userType.User;
	}

	// Not actually going to be used and I am aware I could method above with method
	// below
	public void setUserToAdmin() {
		userType = userType.Admin;
	}

	public boolean isUserAdmin() {
		if (userType == userType.Admin)
			return true;
		return false;
	}

}
