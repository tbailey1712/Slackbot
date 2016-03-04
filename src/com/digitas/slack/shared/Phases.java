package com.digitas.slack.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Phases {
    @Id Long id;
    @Index String teamName; 

	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Embedded List<Phase> phaseList = new ArrayList<Phase>();
    
	public List<Phase> getPhaseList() {
		return phaseList;
	}
	public void setPhaseList(List<Phase> phaseList) {
		this.phaseList = phaseList;
	}
	
	public void addPhase(Phase p)
	{
		phaseList.add(p);
	}
}
