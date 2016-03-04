package com.digitas.slack.shared;

public class SlackMessage 
{
	/*
	token=peoR9a82uIrnq0MQZWKEYHWi
			&team_id=T0F0DBBL1
			&team_domain=digitas-tech-chisf
			&channel_id=C0F09MXPX
			&channel_name=general
			&user_id=U0F0DU5F1
			&user_name=tbailey
			&command=%2Fmilestones&text=list
			&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT0F0DBBL1%2F22695195651%2FKRojawNkIUBQocutIRQ5fOK7	
		*/
	
	private String token;
	private String teamID;
	private String teamDomain;
	private String channelID;
	private String channelName;
	private String userID;
	private String userName;
	private String command;
	private String text;
	private String responseURL;
	
	public SlackMessage()
	{
	
	}
	
	public SlackMessage(String response)
	{
		String[] fields = response.split("&");
		token = getValue(fields[0]);
		teamID = getValue(fields[1]);
		teamDomain = getValue(fields[2]);
		channelID = getValue(fields[3]);
		channelName = getValue(fields[4]);
		userID = getValue(fields[5]);
		userName = getValue(fields[6]);
		command = getValue(fields[7]);
		text = getValue(fields[8]);
		responseURL = getValue(fields[9]);
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Contents of the Slack message:\n");
		sb.append("token=").append(token).append("\n");
		sb.append("teamID=").append(teamID).append("\n");
		sb.append("teamDomain=").append(teamDomain).append("\n");
		sb.append("channelID=").append(channelID).append("\n");
		sb.append("channelName=").append(channelName).append("\n");
		sb.append("userID=").append(userID).append("\n");
		sb.append("userName=").append(userName).append("\n");
		sb.append("command=").append(command).append("\n");
		sb.append("text=").append(text).append("\n");
		sb.append("responseURL=").append(responseURL).append("\n");
		
		return sb.toString();
	}
	
	private String getValue(String nvp)
	{
		return nvp.substring( nvp.indexOf("=") + 1 );
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTeamID() {
		return teamID;
	}
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	
	public String getTeamDomain() {
		return teamDomain;
	}

	public void setTeamDomain(String teamDomain) {
		this.teamDomain = teamDomain;
	}
	
	public String getChannelID() {
		return channelID;
	}
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getResponseURL() {
		return responseURL;
	}
	public void setResponseURL(String responseURL) {
		this.responseURL = responseURL;
	}
	
}
