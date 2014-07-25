package com.gcs.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.Demanda;
import com.gcs.dao.ContadorDemandaDao;
import com.gcs.dao.DemandaDao;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class DemandaServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	public void doGet(HttpServletRequest req, HttpServletResponse resp){
JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
		
		 try {
			 
			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");
			 
			 if (sesionpermiso>2){
					json.append("failure", "true");
					json.append("error", "No tienes los permisos para realizar esta operaci�n");
					
					resp.setCharacterEncoding("UTF-8");
			        resp.setContentType("application/json");       
					resp.getWriter().println(json);
			 }else{
				 if (accion.equals("new")){
						createDemanda(req,resp);
					}else if (accion.equals("delete")){
						//deleteDemanda(req,resp);
					}else if (accion.equals("update")){
						//updateDemanda(req,resp);
					}else if (accion.equals("xls")){
						//generateXLS(req,resp);
					}
			 }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	private void createDemanda(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		JSONObject json = new JSONObject();
		
		Demanda d = new Demanda();

		try {
		
		String motivo_catalogacion = req.getParameter("motivo_catalogacion");
		String comentarios = req.getParameter("comentarios");
		String fecha_entrada_peticion = req.getParameter("fecha_entrada_peticion");
		
		String hora_peticion = req.getParameter("hora_peticion");
		String min_peticion = req.getParameter("min_peticion");
		String gestor_negocio = req.getParameter("gestor_negocio");
		String cliente = req.getParameter("cliente");
		String tipo = req.getParameter("tipo");
		String devuelta = req.getParameter("devuelta");
		Boolean devBool = false;
		if (devuelta.equals("SI"))
			devBool = true;
		String fecha_solicitud_asignacion = req.getParameter("fecha_solicitud_asignacion");
		
		


		
		String hora_solicitud_asignacion = req.getParameter("hora_solicitud_asignacion");
		String min_solicitud_asignacion = req.getParameter("min_solicitud_asignacion");
		String estado = req.getParameter("estado");
		String gestor_it = req.getParameter("gestor_it");
		String catalogacion_peticion = req.getParameter("catalogacion_peticion");
		
			
		
		
		DemandaDao dDao = DemandaDao.getInstance();
		
		d.setCatalogacion(catalogacion_peticion);
		d.setComentarios(comentarios);
		d.setDevuelta(devBool);
		d.setEstado(estado);
		d.setFecha_entrada_peticion(dateConverser(fecha_entrada_peticion));		
		d.setFecha_solicitud_asignacion(dateConverser(fecha_solicitud_asignacion));
		d.setStr_fecha_entrada_peticion(fecha_entrada_peticion);
		d.setStr_fecha_solicitud_asignacion(fecha_solicitud_asignacion);
		d.setGestor_it(Long.parseLong(gestor_it));
		d.setGestor_negocio(Long.parseLong(gestor_negocio));
		d.setHora_entrada_peticion(hora_peticion+min_peticion);
		d.setHora_solicitud_asignacion(hora_solicitud_asignacion+min_solicitud_asignacion);
		d.setMotivo_catalogacion(motivo_catalogacion);
		d.setTipo(tipo);
		d.setMotivo_catalogacion(motivo_catalogacion);
		d.setComentarios(comentarios);
		if (cliente.equals("default"))
			d.setClientekey(Long.parseLong("1"));
		else
			d.setClientekey(Long.parseLong(cliente));
		
		
		d.setCod_peticion("PET_"+dDao.countDemandas());
		
			
		dDao.createDemanda(d);	
			
		json.append("success", "true");
		json.append("id", d.getKey().getId());
			
		
		} catch (ParseException e) {
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
			e.printStackTrace();
		}catch(Exception e){
			json.append("success", "true");
			json.append("id", d.getKey().getId());		
		}
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
	}
	
	public Date dateConverser(String cadena) throws ParseException{
		DateFormat formatter = null;
		formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date convertedDate = (Date) formatter.parse(cadena);
        
        return convertedDate;
	}
}
