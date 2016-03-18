package com.digitas.slack.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.digitas.slack.shared.Phase;
import com.digitas.slack.shared.Phases;
import com.digitas.slack.shared.SlackMessage;
import com.digitas.slack.shared.Team;
import com.digitas.slack.shared.Teams;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.apphosting.api.ApiProxy;

public class SlackService 
{
	private static SlackService service = null;

	private static Logger log = Logger.getLogger("slackbot");
	private static String appID;
	
	public static SlackService createService()
	{
		if (service == null)
		{
			service = new SlackService();
			ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
			appID = env.getAppId();
		}
		
		
		return service;
	}
	

	// {"text": "This is a line of text in a channel.\nAnd this is another line of text."}
	public void sendMessage(String webhook, String json)
	{       
        try 
        {
            URL url = new URL(webhook);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(json);
            writer.close();
    
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                log.fine("Message posted OK to Slack");
            } else {
                log.warning("Slack responded with " + connection.getResponseCode() + " " + connection.getResponseMessage() );
            }
        } catch (MalformedURLException e) {
            log.severe(e.toString());
        } catch (IOException e) {
        	log.severe(e.toString());
        }		
	}

	public String processMessage(SlackMessage message)
	{
		String command = message.getText();
		String response;
		log.fine("Incoming from Team: " + message.getTeamDomain() + " User:" + message.getUserName() );

		
		if (command.equals("dates"))
		{
			response = getMilestonesResponse(false, message);
		}
		else if (command.equals("help"))
		{
			response = getSlackResponseJSON(false, "announce: Broadcast the project schedule\n dates: Project schedule\n launch: Just the launch date\n add: not ready yet\n", "More fun things to come...");
		
		}
		else if (command.equals("announce"))
		{
			response = getMilestonesResponse(true, message);			
		}
		else if (command.equals("launch"))
		{
			response = getLaunchDateResponse(message);
		}
		else if (command.equals("add"))
		{
			response = getSlackResponseJSON(false, "Sorry, can't do that yet...", null);			
		}		
		// else check question/answer table
		else
		{
			response = getSlackResponseJSON(false, "Try again with a command.  Use 'help' to get an idea....", null);

		}

		return response;
	}
	
	private String getSlackResponseJSON(boolean broadcast, String message, String additional)
	{
		JSONObject jo = new JSONObject();
    	try {
    		//jo.put("response_type", "in_channel");
			jo.put("text", message);
			
			if (broadcast)
			{
				jo.put("response_type", "in_channel");									
			}
			
			if (additional != null) 
			{
				JSONObject jao = new JSONObject();

				//jao.put("username", "Project Status Bot");
				jao.put("text", additional);				
				
				JSONArray ja = new JSONArray();
				ja.put(jao);				
				jo.put("attachments", ja);				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    	
    	return jo.toString();
	}
	
	public void sendDailyUpdate()
	{
		DataService dataService = DataService.createService();
		
    	Teams teams = dataService.getTeams();
    	for (int i=0; i < teams.getTeamList().size(); i++)
    	{
    		Team t = teams.getTeamList().get(i);
    		log.fine("Daily update for team " + t.getTeamName() + " to URL " + t.getWebhook() );
    		if (t.getWebhook() != null) {
    			SlackMessage msg = new SlackMessage();
    			msg.setTeamDomain(t.getTeamName());
    			sendMessage(t.getWebhook(), getMilestonesResponse(false, msg));
    		}
    	}

	}
	
	private String getLaunchDateResponse(SlackMessage message)
	{
		StringBuffer sb = new StringBuffer();
		String teamName = message.getTeamDomain();

		DataService dataService = DataService.createService();
		Team team = dataService.getTeam(teamName);
		String launch = team.getLaunchDateFormatted();
		
		sb.append("Site launches on ").append(launch);
		String resp = getSlackResponseJSON(false, sb.toString(), null);
		log.fine("Response: " + resp);
		return resp;
	
	}

	private String getMilestonesResponse(boolean broadcast, SlackMessage message)
	{
		StringBuffer sb = new StringBuffer();
		String teamName = message.getTeamDomain();
		
		DataService dataService = DataService.createService();
		
		List<Phase> phases = dataService.getPhases(teamName).getPhaseList();
		for (int i=0; i < phases.size(); i++ ) {
			Phase p = phases.get(i);
			Date now = new Date();
			Date start = p.getStart();
			if (start.after(now) ){
				long gap = start.getTime() - now.getTime();
				long days = gap / 86400000;				
				sb.append( p.getName() ).append(" starts in ").append( days ).append(" days \n");
				
			}
			
		}
		
		Team team = dataService.getTeam(teamName);
		String launch = team.getLaunchDateFormatted();
		
		sb.append("Site launches on ").append(launch);
		String resp = getSlackResponseJSON(broadcast, sb.toString(), null);
		log.fine("Response: " + resp);
		return resp;
	}
}
