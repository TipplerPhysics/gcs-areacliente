package com.gcs.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.config.Config;
import com.gcs.config.StaticConfig;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ServicioServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1879747622689943880L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		String accion = req.getParameter("accion");
		
		try {
		
			if ("new".equals(accion)){				
				createService(req,resp);				
			}else if ("getServicesByCountry".equals(accion)){
				getServicesByCountry(req,resp);
			}else if ("delete".equals(accion)){
				deleteServicio(req,resp);
			}
		
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		doPost(req,resp);
	}
	
	private void deleteServicio(HttpServletRequest req, HttpServletResponse resp){
		String id = req.getParameter("id");
		
		ServicioDao sDao = ServicioDao.getInstance();
		Servicio s = sDao.getServicioById(Long.parseLong(id));
		sDao.deleteServicio(s);
		
		try{
		
			JSONObject json = new JSONObject();
			json.append("success", "true");
			
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);
		
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getServicesByCountry(HttpServletRequest req, HttpServletResponse resp){
		String pais = req.getParameter("pais");
		JSONArray jarray = new JSONArray();
		ArrayList<Config> servicios = new ArrayList<>();
		
		try {
		
			switch(pais){
				case "Argentina":
					servicios = StaticConfig.servicios_argentina; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				
				case "Bélgica":
					servicios = StaticConfig.servicios_belgica; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				case "Chile":
					servicios = StaticConfig.servicios_chile; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				case "Colombia":
					servicios = StaticConfig.servicios_colombia; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				case "España":
					servicios = StaticConfig.servicios_espania; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				case "UK":
					servicios = StaticConfig.servicios_uk; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				case "Mexico":
					servicios = StaticConfig.servicios_mexico; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				case "EEUU":
					servicios = StaticConfig.servicios_eeuu; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				case "Francia":
					servicios = StaticConfig.servicios_francia; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				case "Perú":
					servicios = StaticConfig.servicios_peru; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				case "Portugal":
					servicios = StaticConfig.servicios_portugal; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
				case "Venezuela":
					servicios = StaticConfig.servicios_venezuela; 
					for (int a =0; a<=servicios.size()-1; a++){
						jarray.put(servicios.get(a).getValue());
					}				
					break;
			}
		
			JSONObject json = new JSONObject();
			
			
			json.append("success", "true");
			json.append("servicios", jarray);
			
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);
		
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void createService(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException{
		
		JSONObject json = new JSONObject();
		
		try{
			String project_id = req.getParameter("cod_proyecto");
			
			ProyectoDao pDao = ProyectoDao.getInstance();
			Proyecto p = pDao.getProjectbyId(Long.parseLong(project_id));
			
			String servicio = req.getParameter("servicio");
			String estado = req.getParameter("estado");
			String cod_servicio = req.getParameter("cod_servicio");
			
			String observaciones = req.getParameter("observaciones");
			String formato_intermedio = req.getParameter("formato_intermedio");		
			String formato_local = req.getParameter("formato_local");
			
			String referencia_local1 = req.getParameter("ref_local1");
			String referencia_local2 = req.getParameter("ref_local2");
			
			String str_fecha_ini_integradas = req.getParameter("fecha_inicio_integradas");
			String str_fecha_fin_integradas = req.getParameter("fecha_fin_integradas");
			
			String str_fecha_ini_aceptacion = req.getParameter("fecha_inicio_aceptacion");
			String str_fecha_fin_aceptacion = req.getParameter("fecha_fin_aceptacion");

			String str_fecha_ini_validacion = req.getParameter("fecha_inicio_validacion");
			String str_fecha_fin_validacion = req.getParameter("fecha_fin_validacion");
			
			String str_fecha_implantacion_prod = req.getParameter("fecha_implantacion_produccion");
			
			String str_fecha_ini_primera_op = req.getParameter("fecha_inicio_primera_op");
			String str_fecha_fin_primera_op = req.getParameter("fecha_fin_primera_op");

			String str_fecha_inicio_op_cliente = req.getParameter("fecha_inicio_op_cliente");
			String str_fecha_ans = req.getParameter("ans");
			
			String str_fecha_inicio_pruebas = req.getParameter("fecha_inicio_pruebas");
			String str_fecha_fin_pruebas = req.getParameter("fecha_fin_pruebas");
			
			String str_fecha_mig_channeling = req.getParameter("fecha_mig_channeling");
			String str_fecha_mig_infraestructura = req.getParameter("fecha_mig_infraestructura");
			
			String pais = req.getParameter("pais");

			Servicio s = new Servicio();
			s.setId_proyecto(p.getKey().getId());
			s.setCod_proyecto(p.getCod_proyecto());
			s.setPais(pais);
			
			s.setServicio(servicio);
			s.setEstado(estado);
			s.setCod_servicio(cod_servicio);
			
			s.setObservaciones(observaciones);
			s.setFormato_intermedio(formato_intermedio);
			s.setFormato_local(formato_local);
			s.setReferencia_local1(referencia_local1);
			s.setReferencia_local2(referencia_local2);
			
			s.setStr_fecha_ini_integradas(str_fecha_ini_integradas);
			s.setStr_fecha_fin_integradas(str_fecha_fin_integradas);
			
			s.setStr_fecha_ini_aceptacion(str_fecha_ini_aceptacion);
			s.setStr_fecha_fin_aceptacion(str_fecha_fin_aceptacion);
			
			s.setStr_fecha_ini_validacion(str_fecha_ini_validacion);
			s.setStr_fecha_fin_validacion(str_fecha_fin_validacion);
			
			s.setStr_fecha_implantacion_produccion(str_fecha_implantacion_prod);
			
			s.setStr_fecha_ini_primera_operacion(str_fecha_ini_primera_op);
			s.setStr_fecha_fin_primera_operacion(str_fecha_fin_primera_op);
			
			s.setStr_fecha_ini_op_cliente(str_fecha_inicio_op_cliente);
			s.setStr_fecha_ANS(str_fecha_ans);
			
			s.setStr_fecha_ini_pruebas(str_fecha_inicio_pruebas);
			s.setStr_fecha_fin_pruebas(str_fecha_fin_pruebas);
			
			s.setStr_migracion_channeling(str_fecha_mig_channeling);
			s.setStr_migracion_infra(str_fecha_mig_infraestructura);
			
			ServicioDao sDao = ServicioDao.getInstance();
			sDao.createServicio(s);
			json.append("success", "true");
			
		}catch (Exception e){
			e.printStackTrace();
			json.append("failure", "true");
			json.append("error", "Se ha producido un error inesperado");
		}
		
		
		
		
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
}
