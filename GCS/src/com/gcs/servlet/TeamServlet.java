package com.gcs.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcs.beans.Equipo;
import com.gcs.dao.EquipoDao;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class TeamServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5364894908970251758L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)  throws IOException{
		
		String equipo = req.getParameter("equipo");
		EquipoDao eDao = EquipoDao.getInstance();
		
		Equipo e = eDao.getEquipoByName(equipo);
		Integer anio_ultimo_registro = e.getUltima_escritura().getYear();
		
		Date fecha = new Date();
		Integer anio = fecha.getYear();
		
		Integer contador;
		
		if (anio==anio_ultimo_registro){
			contador = e.getContador();
		}else{
			contador = 0;
		}
		
		JSONObject json = new JSONObject();
		
		try {
			json.append("success", "true");
			json.append("contador", String.format("%03d", contador));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		doPost(req,resp);
	}

}
