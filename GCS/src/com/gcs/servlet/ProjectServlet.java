package com.gcs.servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.Proyecto;
import com.gcs.dao.ProyectoDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ProjectServlet extends HttpServlet{

	private static final long serialVersionUID = -9001060985723788469L;
	
	public void doGet (HttpServletRequest req, HttpServletResponse resp){
		JSONObject json = new JSONObject();
		
		try{
			String accion = req.getParameter("accion");

			HttpSession sesion = req.getSession();
			//int sesionpermiso = (int) sesion.getAttribute("permiso");

			if ("new".equals(accion)){
				createProject(req,resp);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	private void createProject(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException{
		
		JSONObject json = new JSONObject();
		
		try {
		
		String fecha_alta_str = req.getParameter("fecha_alta_cliente");
		String nombre = req.getParameter("project_name");
		String tipo = req.getParameter("tipo");
		String cliente = req.getParameter("cliente");
		
		String clasificacion = req.getParameter("clasificacion");
		String gestor_it_str = req.getParameter("gestor_it");
		String gestor_negocio_str = req.getParameter("gestor_negocio");
		String coste = req.getParameter("coste");
		
		if (fecha_alta_str.equals("")||nombre.equals("")||tipo.equals("")||cliente.equals("")||clasificacion.equals("")
				||gestor_it_str.equals("")||gestor_negocio_str.equals("")||coste.equals("")){
			json.append("failure", "true");
			json.append("error","Faltan campos obligatorios.");
			
		}else{
			Proyecto p = new Proyecto();
			
			p.setFecha_alta_str(fecha_alta_str);
			p.setFecha_alta(Utils.dateConverter(fecha_alta_str));	
			p.setNombre(nombre);
			p.setTipo(tipo);
			p.setClienteKey(Long.parseLong(cliente));
			p.setClasificacion(Integer.parseInt(clasificacion));
			p.setGestor_it(Long.parseLong(gestor_it_str));
			p.setGestor_negocio(Long.parseLong(gestor_negocio_str));
			p.setCoste(Long.parseLong(coste));
			
			ProyectoDao pDao = ProyectoDao.getInstance();
			pDao.createProject(p);
			
		}
		
		} catch (ParseException e) {
			json.append("failure", "true");
			json.append("error", "Se ha producido un error inesperado");
			e.printStackTrace();
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}

}
