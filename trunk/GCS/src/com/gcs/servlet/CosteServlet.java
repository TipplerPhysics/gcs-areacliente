package com.gcs.servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.Cliente;
import com.gcs.beans.Coste;
import com.gcs.beans.Proyecto;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.CosteDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class CosteServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5053382684256133113L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String accion = req.getParameter("accion");
		JSONObject json = new JSONObject();
		

		try {

			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");

			if (sesionpermiso > 4) {
				json.append("failure", "true");
				json.append("error",
						"No tienes los permisos para realizar esta operación");

				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("application/json");
				resp.getWriter().println(json);
			} else {
				if (accion.equals("new")) {
					createCoste(req, resp);
				} else if (accion.equals("delete")) {
					deleteCoste(req,resp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}
	
	private void createCoste(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		
		JSONObject json = new JSONObject();
		
		try {
			String cliente =  req.getParameter("cliente");
			String analisis_coste =  req.getParameter("analisis_coste");
			String analisis_horas =  req.getParameter("analisis_horas");
			String comentarios =  req.getParameter("comentarios");
			String construccion_coste =  req.getParameter("construccion_coste");
			String construccion_horas =  req.getParameter("construccion_horas");
			String diseño_coste =  req.getParameter("diseño_coste");
			String diseño_horas =  req.getParameter("diseño_horas");
			String equipo =  req.getParameter("equipo");
			String fecha_alta_costes =  req.getParameter("fecha_alta_costes");
			String fecha_solicitud_valoracion =  req.getParameter("fecha_solicitud_valoracion");
			String gestion_coste =  req.getParameter("gestion_coste");		
			String gestion_horas =  req.getParameter("gestion_horas");
			String gestor_it =  req.getParameter("gestor_it");
			String project =  req.getParameter("project");
			String pruebas_horas =  req.getParameter("pruebas_horas");
			String pruebas_costes =  req.getParameter("pruebas_coste");
			String total_coste =  req.getParameter("total_coste");
			String total_horas =  req.getParameter("total_horas");
			String num_valoracion = req.getParameter("num_valoracion");
			
			CosteDao cDao = CosteDao.getInstance();	
			ClienteDao clDao = ClienteDao.getInstance();
			ProyectoDao pDao = ProyectoDao.getInstance();
			Coste c = new Coste();
			Proyecto p = pDao.getProjectbyId(Long.parseLong(project));
			
			Cliente cliente_obj = clDao.getClienteById(Long.parseLong(cliente));
			c.setCliente_name(cliente_obj.getNombre());
			c.setClienteKey(Long.parseLong(cliente));
			c.setCoste_analisis(analisis_coste);
			c.setHoras_analisis(analisis_horas);
			c.setComentarios(comentarios);
			c.setCoste_construccion(construccion_coste);
			c.setHoras_construccion(construccion_horas);
			c.setHoras_diseño(diseño_horas);
			c.setCoste_diseño(diseño_coste);
			if (!"default".equals(equipo))
				c.setEquipos(equipo);
			c.setStr_fecha_alta(fecha_alta_costes);		
			c.setFecha_alta(Utils.dateConverter(fecha_alta_costes));
			c.setStr_fecha_solicitud_valoracion(fecha_solicitud_valoracion);
			c.setFecha_solicitud_valoracion(Utils.dateConverter(fecha_solicitud_valoracion));
			c.setHoras_gestion(gestion_horas);
			c.setCoste_gestion(gestion_coste);
			if (!"default".equals(gestor_it))
				c.setGestor_it_key(Long.parseLong(gestor_it));
			c.setProjectKey(Long.parseLong(project));
			c.setProject_name(p.getCod_proyecto());
			
			c.setHoras_pruebas(pruebas_horas);
			c.setCoste_pruebas(pruebas_costes);
			
			c.setCoste_total(total_coste);
			c.setHoras_total(total_horas);
			if (!"default".equals(num_valoracion))
				c.setNum_valoracion(num_valoracion);
			
			cDao.createCoste(c);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		json.append("success", "true");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);		
		
		
	}
	
	private void deleteCoste(HttpServletRequest req, HttpServletResponse resp){
		
	}
	
	

}
