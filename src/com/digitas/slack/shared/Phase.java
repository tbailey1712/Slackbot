package com.digitas.slack.shared;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Phase implements Serializable  
{
	@Id Long id;
	@Index private String name = "";
	private Date start;
	private Date end;
	
	public Phase()
	{
		
	}
	public Phase(String name, Date start, Date end)
	{
		this.name = name;
		this.start = start;
		this.end = end;
	}

	public String getID()
	{
		return id.toString();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
}
