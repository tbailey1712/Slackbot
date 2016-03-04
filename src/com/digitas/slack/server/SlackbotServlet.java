package com.digitas.slack.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitas.slack.shared.Phase;
import com.digitas.slack.shared.Phases;
import com.digitas.slack.shared.Preferences;
import com.digitas.slack.shared.SlackMessage;
import com.digitas.slack.shared.Team;
import com.digitas.slack.shared.Teams;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.apphosting.api.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.QueryKeys;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class SlackbotServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String appID = "";
	private static Logger log;
	
	  @Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
		
		log = Logger.getLogger("slackbot");
		log.info("Initializing the SlackbotServlet");
		
		// TODO Auto-generated method stub
		super.init(config);
		//ObjectifyFactory

		
		ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
		appID = env.getAppId();
		log.info("Running on GAE " + appID);
		
	}

	  
	  private boolean isValidToken(String team, String token)
	  {
		  boolean valid = true;
		  
		  log.info("Validating message for team " + team + " with token " + token);
		  return valid;
	  }
	  
	  
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String path = req.getServletPath();
		String uri = req.getRequestURI();
		String queryString = req.getQueryString();

	    ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
	    String id = env.getAppId();
		DataService dataService = DataService.createService();

	    
	    PrintWriter out = resp.getWriter();
	    resp.setStatus(200);
	    
	    String data = getPostData(req);
	    log.info("POST ACTION - Bot for AppID " + id );
	    log.info("Posted Data:" + data );
	    	    
	    String service = uri.substring( path.length() + 1 );	    
	    log.info("Service Requested:" + service);
	    
	    if (service.equals("slash"))
	    {
	    	SlackMessage slackMessage = new SlackMessage(data);
	    	SlackService slack = SlackService.createService();
	    	
	    	log.info(slackMessage.toString());
	    	
	    	if ( isValidToken( slackMessage.getTeamDomain(), slackMessage.getToken() ) )
	    	{
		    	String response = slack.processMessage(slackMessage);	    		
		    	resp.setContentType("application/json");
		    	out.println(response);
	    	}
	    	else
	    	{
	    		resp.setStatus(401);
	    		out.println("Not a valid token");
	    	}
	    	

	    }
	    else if (service.equals("team"))
	    {
	    	log.info("Create a new team on " + appID + " named " + data);
	    	dataService.storeNewTeam(appID, data);
	    }
	    else if (service.equals("password"))
	    {
	    	dataService.setPassword(appID, id, data);
	    }
	    else if (service.equals("passwordrequired"))
	    {
	    	boolean reqpass = Boolean.parseBoolean(data);
	    	log.fine("Passwords Required is now " + reqpass);
	    	dataService.setPasswordRequired(appID, reqpass);
	    }
	    else if (service.equals("slacklogin"))
	    {
	    	boolean doslack = Boolean.parseBoolean(data);
	    	log.fine("Connect to Slack is now " + doslack);
	    	dataService.setConnectToSlack(appID, doslack);
	    }

	    else if (service.startsWith("phase"))
	    {
	    	int idx = service.indexOf("/");
	    	
	    	if (idx > 0)
	    	{
		    	String team = service.substring(idx+1);
		    	String[] fields = data.split(",");
		    	dataService.storePhase(team, fields[0], fields[1], fields[2]);
	    	}
	    }
	    else if (service.startsWith("token"))
	    {
	    	int idx = service.indexOf("/");
	    	
	    	if (idx > 0)
	    	{
		    	String team = service.substring(idx+1);
		    	dataService.storeToken(appID, team, data);
	    	}
	    }
	    else if (service.startsWith("launchdate"))
	    {
	    	int idx = service.indexOf("/");
	    	
	    	if (idx > 0)
	    	{
		    	String team = service.substring(idx+1);
		    	dataService.storeLaunchDate(appID, team, data);
	    	}
	    }
	    else if (service.startsWith("webhook"))
	    {
	    	int idx = service.indexOf("/");
	    	
	    	if (idx > 0)
	    	{
		    	String team = service.substring(idx+1);
		    	dataService.storeWebhook(appID, team, data);
	    	}
	    }

	    else if (service.equals("deletephase"))
	    {
	    	dataService.deletePhase(id, data);
	    }
	    out.flush();

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		String path = req.getServletPath();
		String uri = req.getRequestURI();
		String queryString = req.getQueryString();

	    ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
	    String id = env.getAppId();
		DataService dataService = DataService.createService();
				
	    PrintWriter out = resp.getWriter();
	    
	    log.fine("BotService GET Request for AppID " + id );
	    	    
	    String service = uri.substring( path.length() + 1 );	    
	    log.fine("Service Requested: " + service);

	    if (service.startsWith("preferences"))
	    {
	    	JSONObject jo = new JSONObject();
	    	Preferences prefs = dataService.getPrefs();
	    	try {
					jo.put("password", prefs.getPassword());
					jo.put("passwordRequired", prefs.isPasswordRequired());
					jo.put("slackLogin", prefs.isConnectToSlack());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					log.severe("Couldn't put JSONObject " + e.toString());
					e.printStackTrace();
				}
	    	out.println( jo.toString() );
	    	
	    }
	    else if (service.startsWith("dailyupdate"))
	    {
	    	Preferences prefs = dataService.getPrefs();
	    	if (prefs.isConnectToSlack())
	    	{
	    		log.info("Sending Daily Updates to all teams");
	    		SlackService slackService = SlackService.createService();
	    		slackService.sendDailyUpdate();
	    	}
	    	
	    }
	    else if (service.startsWith("teamprefs"))
	    {
	    	JSONObject jo = new JSONObject();
	    	SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
	    	int idx = service.indexOf("/");
	    	
	    	if (idx > 0)
	    	{
		    	String team = service.substring(idx+1);
		    	log.info("Retrieving preferences for team " + team);
		    	Team t = dataService.getTeam(team);
		    	try {
						jo.put("teamname", t.getTeamName());
						jo.put("token", t.getToken() );
						jo.put("webhook", t.getWebhook());
						if (t.getLaunchDate() != null) {
							jo.put("launchDate", mdyFormat.format( t.getLaunchDate() ));							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						log.severe("Couldn't put JSONObject " + e.toString());
						e.printStackTrace();
					}
		    	log.fine("Team Prefs: " + jo.toString());
		    	out.println( jo.toString() );		    	
	    	}
	    }
	    else if (service.equals("teams-html"))
	    {
	    	/*
	    	 * 			    		<li><a href="#">Something else here</a></li>
			    		<li role="separator" class="divider"></li>
			    		<li><a href="#">Separated link</a></li>
	    	 */
	    	StringBuffer sb = new StringBuffer();
	    	
	    	Teams teams = dataService.getTeams();
	    	for (int i=0; i < teams.getTeamList().size(); i++)
	    	{
	    		Team t = teams.getTeamList().get(i);
	    		sb.append("<li><a href=\"#\">").append(t.getTeamName()).append("</a></li>");
	    	}

	    	sb.append("<li role=\"separator\" class=\"divider\"></li>");
    		sb.append("<li><a href=\"#\">Create new team...</a></li>");
	    	
	    	out.println(sb.toString());
	    }
	    else if (service.startsWith("phases"))
	    {
	    	JSONObject json = new JSONObject();
	    	JSONArray phases = new JSONArray();
	    	int idx = service.indexOf("/");
	    	
	    	if (idx > 0)
	    	{
		    	String team = service.substring(idx+1);

		    	Phases phasesData = dataService.getPhases(team);
		    	for (int i=0; i < phasesData.getPhaseList().size(); i++)
		    	{
		    		Phase phase = phasesData.getPhaseList().get(i);
			    	try 
			    	{
			    		SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
				    	JSONArray ja2 = new JSONArray();	    	
				    	ja2.put(phase.getName());
				    	ja2.put(mdyFormat.format( phase.getStart() ));
				    	ja2.put(mdyFormat.format( phase.getEnd() ));
				    	ja2.put(" <button class=\"btn-danger btn\" id=\"" + phase.getName() + "\" name=\"submit\" onClick=\"delete(this);\" type=\"submit\">X</button>  ");
				    		    	
				    	
				    	phases.put(ja2);
				    	

							json.put("data", phases.toString() );
					} catch (JSONException e) {
							
						e.printStackTrace();
					}	    		
		    	}
		    	out.println( phases.toString() );
	    		
	    	} else {
	    	   log.warning("Incorrect call to /phases, no team ID sent");
	    	}
	    }
	    out.flush();
	}
	  

	


	  
	  public  String getPostData(HttpServletRequest req) {
		  StringBuilder sb = new StringBuilder();
		  try {
		    	  // Read from request
		        BufferedReader reader = req.getReader();
		        String line;
		        while ((line = reader.readLine()) != null) {
		            sb.append(line);
		        }
		    } catch(IOException e) {
		        e.printStackTrace();    
		    } 

		    return sb.toString().trim();
		}
}
