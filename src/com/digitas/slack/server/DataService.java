package com.digitas.slack.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.digitas.slack.shared.Phase;
import com.digitas.slack.shared.Phases;
import com.digitas.slack.shared.Preferences;
import com.digitas.slack.shared.Team;
import com.digitas.slack.shared.Teams;
import com.google.apphosting.api.ApiProxy;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class DataService 
{
	private static DataService service = null;
	private static Logger log = Logger.getLogger("slackbot");
	
	private static String appID;
	
	public DataService()
	{
		ObjectifyService.register(Preferences.class);
		ObjectifyService.register(Phases.class);
		ObjectifyService.register(Phase.class);
		ObjectifyService.register(Teams.class);
		ObjectifyService.register(Team.class);				
	}
	
	public static DataService createService()
	{
		if (service==null)
		{
			service = new DataService();
			ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
			appID = env.getAppId();
		}
		return service;
	}
	
	public Preferences getPrefs()
	{
		  Preferences prefs = ofy().load().type(Preferences.class).filter("appID", appID).first().now();
		  
		  if (prefs == null)
		  {
			  prefs = new Preferences();
			  prefs.setAppID(appID);
			  prefs.setPassword("joshua");
			  prefs.setPasswordRequired(false);
			  ofy().save().entities(prefs).now();
		  }
		  return prefs;
	}	
	public void setPassword(String appID, String id, String passwd)
	{
		  Preferences prefs = ofy().load().type(Preferences.class).filter("appID", id).first().now();
		  prefs.setPassword(passwd);
		 // prefs.setAppID(id);
		  ofy().save().entities(prefs).now();		
	}	
	
	public void setPasswordRequired(String appID, boolean req)
	{
		  Preferences prefs = ofy().load().type(Preferences.class).filter("appID", appID).first().now();
		  prefs.setPasswordRequired(req);
		  ofy().save().entities(prefs).now();		
	}

	public void setConnectToSlack(String appID, boolean req)
	{
		  Preferences prefs = ofy().load().type(Preferences.class).filter("appID", appID).first().now();
		  prefs.setConnectToSlack(req);
		  ofy().save().entities(prefs).now();	
		  
		  if (req) {
//			  connectToSlack();
		  } else {
	//		  disconnectFromSlack();
		  }
	}
	
	public void deletePhase(String id, String name)
	{
		Phases phases = ofy().load().type(Phases.class).filter("appID", id).first().now();
		
		int size = phases.getPhaseList().size();
		for (int i=0; i < size; i++)
		{
			Phase ph = phases.getPhaseList().get(i);
			if (ph.getName().equals(name))
			{
				phases.getPhaseList().remove(i);
				i = size;
			}
		}
		ofy().save().entities(phases).now();
	}


	public void storeToken(String appID, String name, String token)
	{
		
		Teams teams = getTeams();
		List<Team> teamList = teams.getTeamList();
		for (int i=0; i < teamList.size(); i++)
		{
			Team tl = teamList.get(i);
			if (tl.getTeamName().equals(name))
			{
				log.info("Found team " + name + " to update token to " + token);
				tl.setToken(token);
				continue;
			}
		}
		
		ofy().save().entities(teams).now();	
		
	}

	public void storeWebhook(String appID, String name, String url)
	{
		
		Teams teams = getTeams();
		List<Team> teamList = teams.getTeamList();
		for (int i=0; i < teamList.size(); i++)
		{
			Team tl = teamList.get(i);
			if (tl.getTeamName().equals(name))
			{
				tl.setWebhook(url);
				continue;
			}
		}
		
		ofy().save().entities(teams).now();	
		
	}

	public void storeLaunchDate(String appID, String name, String launchDate)
	{
		//Team t = new Team();
		//t.setToken(token);
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		
		try {
			date = formatter.parse(launchDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());
		}
		
		Teams teams = getTeams();
		List<Team> teamList = teams.getTeamList();
		
		for (int i=0; i < teamList.size(); i++)
		{
			Team tl = teamList.get(i);
			if (tl.getTeamName().equals(name))
			{
				log.info("Found team " + name + " to update launch date to " + launchDate);
				tl.setLaunchDate(date);
				continue;
			}
		}
		
		ofy().save().entities(teams).now();	
		
	}

	public void storeNewTeam(String appID, String name)
	{
		Teams teams = ofy().load().type(Teams.class).filter("appID", appID).first().now();
		if (teams == null)
		{
			log.info("No Teams currently exist on app ID " + appID);
			teams = new Teams();
		}
		Team t = new Team();
		t.setTeamName(name);
		t.setToken("SLACK_TOKEN");
		teams.addTeam(t);
		teams.setAppID(appID);
		ofy().save().entities(teams).now();		
	}
	
	public void storePhase(String teamName, String name, String start, String end)
	{
		/*
		 * Date myDate = 
		 * SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
		 * String mdy = mdyFormat.format(myDate);
		 * 
		 * SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
           Date utilDate = formatter.parse(date);
		 */
		Phases phases = ofy().load().type(Phases.class).filter("teamName", teamName).first().now();
		Date startDate = new Date();
		Date endDate = new Date();
		
		log.fine("Team " + teamName + " has " + phases.getPhaseList().size() + " phases currently");
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		try 
		{
			startDate = formatter.parse(start);
			endDate = formatter.parse(end);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		Phase phase = new Phase(name,startDate,endDate);
		//phases.setTeamName(teamName);
		log.fine("Adding phase " + name + " to this team ");
		phases.addPhase(phase);
		
		ofy().save().entities(phases).now();
	}

	public void deleteAllPhases()
	{
		List<Key<Phases>> keys = ofy().load().type(Phases.class).keys().list();
		ofy().delete().keys(keys).now();
		
	}
	
	public Teams getTeams()
	{
		Teams teams = ofy().load().type(Teams.class).filter("appID", appID).first().now();
		if (teams == null)
		{
			log.info("No Teams currently exist on app ID " + appID);
			teams = new Teams();
		}
		return teams;
	}
	
	public Team getTeam(String teamName)
	{
		Teams teams = getTeams();

		int size = teams.getTeamList().size();
		for (int i=0; i < size; i++)
		{
			Team t = teams.getTeamList().get(i);
			if (t.getTeamName().equals(teamName))
			{
				return t;
			}
		}
		log.warning("Unable to find a team named " + teamName);
		return new Team();
	}
	public Phases getPhases(String teamName)
	{
		Phases phases = ofy().load().type(Phases.class).filter("teamName", teamName).first().now();
		if (phases == null)
		{
			log.info("No Phases Exist in the datastore for team " + teamName + ", creating a default entry");
			phases = new Phases();
			phases.setTeamName(teamName);
			Date d = new Date();
			Phase phase = new Phase("Example",d,d);
			phases.addPhase(phase);
			ofy().save().entities(phases).now();
			
		}
		return phases;
	}
	
}
