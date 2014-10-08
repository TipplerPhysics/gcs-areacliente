package com.gcs.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConectividadServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7450337631873072616L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		
		String project_id = req.getParameter("id");
		
		
		
	}
	
	public void doGet (HttpServletRequest req, HttpServletResponse resp){
		
		doPost(req,resp);
	}

}
