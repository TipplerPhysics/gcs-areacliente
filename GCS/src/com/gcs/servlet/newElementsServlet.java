package com.gcs.servlet;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.Equipo;
import com.gcs.dao.EquipoDao;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class newElementsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = new JSONObject();

		String accion = req.getParameter("accion");
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		String usermail = (String)sesion.getAttribute("usermail");
		try {
			if (sesionpermiso != 1) {
				json.append("failure", "true");
				json.append("error",
						"No tienes los permisos para realizar esta operación");
	
				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("application/json");
				resp.getWriter().println(json);
			}else{
				if(accion.equals("newEquipo"))createEquipo(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		doGet(req, resp);
	}
	
	private void createEquipo(HttpServletRequest req, HttpServletResponse resp){
		EquipoDao equipoDao = EquipoDao.getInstance();
		Equipo equipo = new Equipo();
		String nombre = req.getParameter("nombre");
		equipo.setNombre(nombre);
		equipo.setContador(1);
		equipo.setUltima_escritura(new Date());
		equipoDao.createEquipo(equipo);
	}
}
