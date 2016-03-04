package com.digitas.slack.shared;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Teams {
    @Id Long id;
    @Index String appID; 
	@Embedded List<Team> teamList = new ArrayList<Team>();
    
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	
	public List<Team> getTeamList() {
		return teamList;
	}
	public void setTeamList(List<Team> li) {
		this.teamList = li;
	}
	
	public void addTeam(Team p)
	{
		teamList.add(p);
	}
}
