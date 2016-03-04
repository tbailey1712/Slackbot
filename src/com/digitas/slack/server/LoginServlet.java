package com.digitas.slack.server;

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
        String pwd = request.getParameter("password");
		
		ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
		String appID = env.getAppId();
        DataService dataService = DataService.createService();
		log.info("Login Request on " + appID + " with password " + pwd);
		
		Preferences prefs = dataService.getPrefs();
		
		if (prefs.getPassword().equals(pwd))
		{
			HttpSession session = request.getSession();
            session.setAttribute("authenticated", "true");
            session.setMaxInactiveInterval(30*60);
            Cookie cookie = new Cookie("DigitasProjectbot", "slack");
            response.addCookie(cookie);
            
            response.sendRedirect("/index.html");			
		}
		else
		{
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html?error=failed");
            rd.include(request, response);
        }
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		resp.sendRedirect("/index.html");
	}
	

}
