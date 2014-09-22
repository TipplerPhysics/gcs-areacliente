package com.gcs.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.User;
import com.gcs.dao.UserDao;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ConfigurationServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 8700828182423197896L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");

		if (sesionpermiso == 1) {
			String accion = req.getParameter("accion");
			
			if ("setUsersToNonErased".equals(accion)){
				setUsersToNonErased(resp);
			}
		}
	}
	
	private void setUsersToNonErased(HttpServletResponse resp){
		JSONObject json = new JSONObject();
		
		try {
		
		UserDao uDao = UserDao.getInstance();
		List<User> users = uDao.getAllUsers();
		
		for (User u:users){
			if (u.getErased()==null){
				u.setErased(false);
				uDao.createUser(u);
			}
		}
		
	
		json.append("success", "true");		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
		
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
