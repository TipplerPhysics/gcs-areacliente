package com.gcs.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.Cliente;
import com.gcs.beans.ContadorDemanda;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ContadorDemandaDao;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class DefaultConf extends HttpServlet {

	private static final long serialVersionUID = 5138569693243826017L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {

		JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
		

		try {
			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");

			if (sesionpermiso == 1) {
				
				if ("def_user".equals(accion)){
					/* Crea cliente de prueba */
					ClienteDao cDao = ClienteDao.getInstance();				
					Cliente cliente = new Cliente("Cliente de Prueba", "deprueba@gmail.com");			
					cDao.createCliente(cliente);	
					json.append("success", "true");
				}else if ("def_counter".equals(accion)){
					ContadorDemanda cd = new ContadorDemanda(1);
					ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();
					cdDao.createContador(cd);	
					json.append("success", "true");
				}
				
			
				
				
				
			} else {
				json.append("failure", "true");
				json.append("error",
						"No tienes los permisos para realizar esta operaci�n");
			}
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);

		} catch (Exception e) {
			try {
				json.append("failure", "true");
				json.append("error", "Error inesperado");

				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("application/json");
				resp.getWriter().println(json);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		doGet(req, resp);
	}

}
