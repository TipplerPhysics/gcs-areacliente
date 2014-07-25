package com.gcs.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.Demanda;
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
		
		try{
		
		String motivo_catalogacion = req.getParameter("motivo_catalogacion");
		
		
		DemandaDao dDao = DemandaDao.getInstance();
				
		Demanda d = new Demanda();
		d.setMotivo_catalogacion(motivo_catalogacion);
			
           
			
		json.append("success", "true");
		json.append("id", d.getKey().getId());
			
		
		
		}catch(Exception e){
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
		
		}
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
	}
}
