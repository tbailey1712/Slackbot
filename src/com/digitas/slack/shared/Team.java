package com.digitas.slack.shared;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Team implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id Long id;
	String teamName;
	String token;
	Date launchDate;
	String webhook;
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getLaunchDate() {
		return launchDate;
	}
	
	public String getLaunchDateFormatted() {
		SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
		String date = mdyFormat.format(launchDate);
		return date;
	}
	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}
	public String getWebhook() {
		return webhook;
	}
	public void setWebhook(String webhook) {
		this.webhook = webhook;
	}
	
}
