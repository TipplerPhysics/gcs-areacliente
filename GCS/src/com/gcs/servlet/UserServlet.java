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
			}else if (accion.equals("update")){
				json = updateUser(req);
			}
			
	        
			resp.setCharacterEncoding("UTF-8");
	        resp.setContentType("application/json");       
			resp.getWriter().println(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	private JSONObject updateUser(HttpServletRequest req) throws JSONException{
		JSONObject json = new JSONObject();
		
		String nombre = req.getParameter("nombre");
		String ap1 = req.getParameter("ap1");
		String ap2 = req.getParameter("ap2");
		String email = req.getParameter("email");
		String areas = req.getParameter("areas");
		String dto = req.getParameter("dto");
		dto = dto.replace('#', '&');
		
		String id = req.getParameter("id");
		if (!areas.equals("")){
			areas = areas.substring(0, areas.length()-1);
		}
		Integer permiso = Integer.parseInt(req.getParameter("permiso"));
		String permisoStr = Utils.getPermisoStr(permiso);
		
		UserDao uDao = UserDao.getInstance();
		User u = uDao.getUserbyId(Long.parseLong(id));
		
		u.setNombre(nombre);
		u.setApellido1(ap1);
		u.setApellido2(ap2);
		u.setEmail(email);
		u.setAreas(areas);
		u.setDepartamento(dto);
		u.setPermisoStr(permisoStr);
		
		uDao.createUser(u);
		
		json.append("success", "true");
		json.append("id", u.getKey().getId());
		json.append("permiso", u.getPermisoStr());
		
		return json;
	}
	
	private JSONObject createUser(HttpServletRequest req) throws JSONException{
		JSONObject json = new JSONObject();
		
		String nombre = req.getParameter("nombre");
		String ap1 = req.getParameter("ap1");
		String ap2 = req.getParameter("ap2");
		String email = req.getParameter("email");
		String areas = req.getParameter("areas");
		String dto = req.getParameter("dto");
		if (areas.length()!=0)
		areas = areas.substring(0, areas.length()-1);
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
			json.append("error", "No tienes los permisos para realizar esta operaci�n");
		}else{
			
            User u = new User(nombre,ap1,ap2,email,permiso,permisoStr,areas,dto);	
			
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
