package com.gcs.servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.Conectividad;
import com.gcs.beans.Proyecto;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.ProyectoDao;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ConectividadServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7450337631873072616L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		
		JSONObject json = new JSONObject();
		String project_id = req.getParameter("project_id");
		
		HttpSession sesion = req.getSession();
		
		String usermail = (String)sesion.getAttribute("usermail");
		
		try {
		
			ConectividadDao cDao = ConectividadDao.getInstance();
			Conectividad c = cDao.getConectividadByProject(Long.parseLong(project_id));
			ProyectoDao pDao = ProyectoDao.getInstance();
			Proyecto p = pDao.getProjectbyId(Long.parseLong(project_id));
			
			if (c==null){
				c = new Conectividad();
			}
			
			String fecha_fin_infra = req.getParameter("fecha_fin_infra");
			String fecha_implantacion = req.getParameter("fecha_implantacion");
			String fecha_ini_infra = req.getParameter("fecha_ini_infra");
			String fin_seguridad = req.getParameter("fin_seguridad");
			String ini_seguridad = req.getParameter("ini_seguridad");			
			String seguridad = req.getParameter("seguridad");
			
			String firewall = req.getParameter("firewall");
			String fin_certificado = req.getParameter("fin_certificado");
			String fin_conectividad = req.getParameter("fin_conectividad");
			
			c.setStr_reglas_firewall(firewall);
			c.setStr_fecha_fin_certificado(fin_certificado);
			c.setStr_fecha_fin_conectividad(fin_conectividad);
			
			c.setStr_fecha_fin_infraestructura(fecha_fin_infra);
			c.setStr_fecha_implantacion(fecha_implantacion);
			c.setStr_fecha_ini_infraestructura(fecha_ini_infra);
			c.setStr_fecha_fin_seguridad(fin_seguridad);
			c.setStr_fecha_ini_seguridad(ini_seguridad);
			c.setKey_proyecto(Long.parseLong(project_id));
			c.setNombre_proyecto(p.getCod_proyecto());
			
			
			c.setSeguridad(seguridad);
			
			try {
				cDao.createConectividad(c,usermail);
			} catch (ParseException e) {
				json.append("failure", "true");
				
				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("application/json");
				resp.getWriter().println(json);
			
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
	
	public void doGet (HttpServletRequest req, HttpServletResponse resp){
		
		doPost(req,resp);
	}

}
