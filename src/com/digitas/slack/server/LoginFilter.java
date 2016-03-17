package com.digitas.slack.server;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digitas.slack.shared.Preferences;
import com.google.apphosting.api.ApiProxy;

public class LoginFilter implements Filter 
{
	private ServletContext context;
	private static Logger log = Logger.getLogger("slackbot");
	private static String appID;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		   HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse res = (HttpServletResponse) response;
	         
	        String uri = req.getRequestURI();
	        log.fine("Requested Resource::"+uri);
	         
	        HttpSession session = req.getSession(false);
	        Preferences prefs = DataService.createService().getPrefs();


	         
	        if (prefs.isPasswordRequired() == false)
	        {
	        	log.fine("No Password required for page " + uri);
	        	chain.doFilter(request, response);
	        	return;
	        }
      
	        
	        if ( isAuthenticated(session) ) 
	        {	        	
	        	log.fine("User is authenticated for page " + uri);
	            chain.doFilter(request, response);
	        }
	        else
	        {
	            log.warning("Unauthorized access request for " + uri );
	            res.sendRedirect("/login.html");
	            return;
	            //RequestDispatcher dispatcher = context.getRequestDispatcher("/login.html");
	            //dispatcher.forward(request, response); 	            
	            // pass the request along the filter chain
	        }	
	}

	private boolean isAuthenticated(HttpSession session)
	{
		boolean auth = false;
		
		if ( (session != null) && (session.getAttribute("authenticated") != null) && (session.getAttribute("authenticated").equals("true") ) ) 
		{
			auth = true;
		}
		return auth;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException 
	{
		this.context = arg0.getServletContext();
		
		ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
		appID = env.getAppId();
        log.fine("LoginFilter initialized");		
	}

}
