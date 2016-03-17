package com.digitas.slack.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digitas.slack.shared.Preferences;
import com.google.apphosting.api.ApiProxy;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet 
{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger("slackbot");

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
        //String user = request.getParameter("email");
        String pwd = request.getParameter("txtPassword");
        //String data = getPostData(request);
		
		ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
		String appID = env.getAppId();
        DataService dataService = DataService.createService();
		log.info("Login Request on " + appID + " with password=" + pwd);
		
		Preferences prefs = dataService.getPrefs();
		
		if (prefs.getPassword().equals(pwd))
		{
			log.fine("Password Authenticated");
			HttpSession session = request.getSession();
            session.setAttribute("authenticated", "true");
            session.setMaxInactiveInterval(30*60);
            Cookie cookie = new Cookie("DigitasProjectbot", "slack");
            response.addCookie(cookie);
            
            response.sendRedirect("/secure/index.html");			
		}
		else
		{
			log.fine("Incorrect Password");
			response.sendRedirect("/login.html?error=failed");
            //RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html?error=failed");
            //rd.forward(request, response);
        }
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		resp.sendRedirect("/login.html");
		
		//resp.sendRedirect("/secure/index.html");
	}
	
	  private  String getPostData(HttpServletRequest req) {
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
