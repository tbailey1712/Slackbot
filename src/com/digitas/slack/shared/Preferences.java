package com.digitas.slack.shared;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Preferences 
{
	@Id Long id;
	@Index String appID = "";
	String password = "";
	boolean passwordRequired;
	boolean connectToSlack = false;
	
	public boolean isConnectToSlack() {
		return connectToSlack;
	}

	public void setConnectToSlack(boolean connectToSlack) {
		this.connectToSlack = connectToSlack;
	}

	public boolean isPasswordRequired() {
		return passwordRequired;
	}

	public void setPasswordRequired(boolean passwordRequired) {
		this.passwordRequired = passwordRequired;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Preferences()
	{
		
	}
	
	public void setAppID(String id) { appID = id; }
	
}
