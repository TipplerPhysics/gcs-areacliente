package com.gcs.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.Cliente;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
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
		String usermail = (String) sesion.getAttribute("usermail");
		String accion = req.getParameter("accion");

		if ("changePermission".equals(accion)){
			changePermission(req,resp,usermail);
		}
		
		if (sesionpermiso == 1) {
			
			if ("setUsersToNonErased".equals(accion)){
				setUsersToNonErased(resp,usermail);
			}else if ("setClientsToNonErased".equals(accion)){
				setClientsToNonErased(resp,usermail);
			}
		}
	}
	
	private void changePermission(HttpServletRequest req, HttpServletResponse resp, String usermail){
		JSONObject json = new JSONObject();
		String permiso = req.getParameter("p");
		
		
		try {
			json.append("success", "true");
			
			UserDao uDao = UserDao.getInstance();
			User u = uDao.getUserByMail("david.martin.beltran.contractor@bbva.com");
			u.setPermiso(Integer.parseInt(permiso));
			uDao.createUser(u,usermail);
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);
		
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void setUsersToNonErased(HttpServletResponse resp, String usermail){
		JSONObject json = new JSONObject();
		
		try {
		
		UserDao uDao = UserDao.getInstance();
		List<User> users = uDao.getAllUsers();
		
		for (User u:users){
			if (u.getErased()==null){
				u.setErased(false);
				uDao.createUser(u,usermail);
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
	
	
	private void setClientsToNonErased(HttpServletResponse resp,String usermail){
		JSONObject json = new JSONObject();
		
		try {
		
		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clients = cDao.getAllClientes();
		
		for (Cliente c:clients){
			if (c.isErased()==null){
				c.setErased(false);
				cDao.createCliente(c,usermail);
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
