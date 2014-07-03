package com.gcs.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.User;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -826683004548238295L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		
		JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
		
		 try {
		
			if (accion.equals("new")){
				json = createUser(req);
			}else if (accion.equals("delete")){
				json = deleteUser(req);
			}
			
	        
			resp.setCharacterEncoding("UTF-8");
	        resp.setContentType("application/json");       
			resp.getWriter().println(json);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	private JSONObject createUser(HttpServletRequest req) throws JSONException{
		JSONObject json = new JSONObject();
		
		String nombre = req.getParameter("nombre");
		String ap1 = req.getParameter("ap1");
		String ap2 = req.getParameter("ap2");
		String email = req.getParameter("email");
		Integer permiso = Integer.parseInt(req.getParameter("permiso"));
		String permisoStr = Utils.getPermisoStr(permiso);
		
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		UserDao uDao = UserDao.getInstance();
		User us = uDao.getUserByMail(email);
		
		if (us!=null){
			json.append("failure", "true");
			json.append("error", "Ya existe un usuario con este email");
		}else if (sesionpermiso>2){
			json.append("failure", "true");
			json.append("error", "No tienes los permisos para realizar esta operación");
		}else{
			
            User u = new User(nombre,ap1,ap2,email,permiso,permisoStr);	
			
			uDao.createUser(u);
			
			json.append("success", "true");
			json.append("id", u.getKey().getId());
			json.append("permiso", u.getPermisoStr());
		}
		
		
		return json;
	}
	
	private JSONObject deleteUser(HttpServletRequest req) throws JSONException {
		JSONObject json = new JSONObject();
		
		UserDao udao = UserDao.getInstance();
		try{
			User u = udao.getUserbyId(Long.parseLong(req.getParameter("id")));
			
			udao.deleteUser(u);
			json.append("success", "true");
		}catch(Exception e){
			json.append("failure", "true");
		}
		
		
		return json;
	}
	
}
