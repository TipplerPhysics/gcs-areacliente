package com.gcs.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.gcs.beans.Cliente;
import com.gcs.beans.ConectividadProyecto;
import com.gcs.beans.Pais;
import com.gcs.beans.ProductoProyecto;
import com.gcs.beans.Proyecto;
import com.gcs.beans.ServicioFile;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ConectividadProyectoDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ProductoProyectoDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioFileDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
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
			String usermail = (String)sesion.getAttribute("usermail");

			if ("new".equals(accion)){
				createProject(req,resp,usermail);
			}else if ("update".equals(accion)){
				editProject(req,resp,usermail);	
			}else if ("delete".equals(accion)){
				deleteProject(req,resp,usermail);
			}else if ("xls".equals(accion)){
				generateXLS(req,resp,usermail);
			}else if ("getProjectsByClient".equals(accion)){
				getProjectsByClient(req,resp);
			}else if ("getConectividades".equals(accion)){
				getConectividades(req,resp);
			}else if ("getConectividadesByProduct".equals(accion)){
				getConectividadesByProduct(req,resp);
			}else if ("clone".equals(accion)){
				cloneProject(req,resp,usermail);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		
	}
	
	private void getProjectsByClient(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException{
		String id = req.getParameter("id");
		
		JSONArray jarray = new JSONArray();
		
		ProyectoDao pDao = ProyectoDao.getInstance();
		List<Proyecto> projects = pDao.getProjectsByClient(Long.parseLong(id));
		
		for (Proyecto p:projects){
			JSONObject json = new JSONObject();
			json.append("id",p.getKey().getId());
			json.append("name",p.getCod_proyecto());
			jarray.put(json);			
		}			
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(jarray);
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	public void generateXLS(HttpServletRequest req, HttpServletResponse resp, String usermail) throws ServletException, IOException{
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=ProyectosGCS.xls");
			
			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream());
			
			ProyectoDao pDao = ProyectoDao.getInstance();
			List<Proyecto> projects = pDao.getAllProjects();
			
			WritableSheet s = w.createSheet("Proyectos", 0);
		
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		    cellFont.setColour(Colour.WHITE);
		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.BLUE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);			
			
		    s.setColumnView(0, 20);
		    s.setColumnView(1, 20);
		    s.setColumnView(2, 20);
		    s.setColumnView(3, 20);
		    s.setColumnView(4, 15);
		    s.setColumnView(5, 30);
		    s.setColumnView(6, 30);
		    s.setColumnView(7, 20);
		    s.setColumnView(8, 30);
		    
		    s.setColumnView(9, 20);
		    s.setColumnView(10, 20);
		    s.setColumnView(11, 30);
		    s.setColumnView(12, 30);
		    s.setColumnView(13, 30);
		    s.setColumnView(14, 30);
		    
		    s.setColumnView(15, 30);
		    s.setColumnView(16, 30);
		    s.setColumnView(17, 30);
		    
		    
		    s.setRowView(0, 900);
						
			s.addCell(new Label(0, 0, "FECHA ALTA",cellFormat));
			s.addCell(new Label(1, 0, "COD. PROYECTO",cellFormat));
			s.addCell(new Label(2, 0, "TIPO",cellFormat));
			s.addCell(new Label(3, 0, "CLIENTE",cellFormat));
			s.addCell(new Label(4, 0, "CLASIFICACION",cellFormat));
			s.addCell(new Label(5, 0, "GESTOR IT",cellFormat));
			s.addCell(new Label(6, 0, "GESTOR NEGOCIO",cellFormat));
			s.addCell(new Label(7, 0, "COSTE",cellFormat));
			s.addCell(new Label(8, 0, "PETICION",cellFormat));
			
			s.addCell(new Label(9, 0, "PRODUCTO",cellFormat));
			s.addCell(new Label(10, 0, "CONECTIVIDAD",cellFormat));
			s.addCell(new Label(11, 0, "FECHA INICIO VALORACION",cellFormat));
			s.addCell(new Label(12, 0, "FECHA FIN VALORACION",cellFormat));
			s.addCell(new Label(13, 0, "FECHA INICIO VIABILIDAD",cellFormat));
			s.addCell(new Label(14, 0, "FECHA FIN VIABILIDAD",cellFormat));
			
			s.addCell(new Label(15, 0, "FECHA ENVIO C100",cellFormat));
			s.addCell(new Label(16, 0, "FECHA OK NEGOCIO",cellFormat));
			s.addCell(new Label(17, 0, "OBSERVACIONES",cellFormat));
			
			
			int aux=1;
			
			
			
			for (Proyecto p:projects){
				
								
				s.addCell(new Label(0, aux, p.getFecha_alta_str()));
				s.addCell(new Label(1, aux, p.getCod_proyecto()));
				s.addCell(new Label(2, aux, p.getTipo().trim()));
				s.addCell(new Label(3, aux, p.getClienteName()));
				s.addCell(new Label(4, aux, Integer.toString(p.getClasificacion())));
				s.addCell(new Label(5, aux, p.getGestor_it_name()));
				s.addCell(new Label(6, aux, p.getGestor_negocio_name()));

								
				s.addCell(new Label(7, aux, p.getCoste() ));
				
				s.addCell(new Label(8, aux, p.getUrl_doc_google_drive()));
				
				s.addCell(new Label(9, aux, p.getProducto()));
				s.addCell(new Label(10, aux, p.getConectividad()));
				s.addCell(new Label(11, aux, p.getStr_fecha_inicio_valoracion()));
				s.addCell(new Label(12, aux, p.getStr_fecha_fin_valoracion()));
				s.addCell(new Label(13, aux, p.getStr_fecha_inicio_viabilidad()));
				s.addCell(new Label(14, aux, p.getStr_fecha_fin_viabilidad()));
				
				s.addCell(new Label(15, aux, p.getStr_envioC100()));
				s.addCell(new Label(16, aux, p.getStr_OKNegocio()));
				s.addCell(new Label(17, aux, p.getObservaciones()));
				aux++;
			}		
			
			w.write();
			w.close();
		} catch (Exception e) {
			throw new ServletException("Exception in Excel", e);
		} finally {
			if (out != null)
				out.close();
		}

	}
	
	public static List<ConectividadProyecto> getConectividadByProductoJSON(String producto) throws JSONException{
		
		ConectividadProyectoDao conectividadDao = ConectividadProyectoDao.getInstance();
		List<ConectividadProyecto> conectividades = conectividadDao.getConectividadesByProducto(producto);	
		return conectividades;
	}
	
	private void getConectividadesByProduct(HttpServletRequest req, HttpServletResponse resp){
		String producto = req.getParameter("producto");
																
		try{			
						
			List<ConectividadProyecto> list = getConectividadByProductoJSON(producto);
			JSONObject json = new JSONObject();
			JSONArray jarray = new JSONArray();
			
			for(ConectividadProyecto o:list){
				jarray.put(o.getName());
			}
			
			json.append("success","true");
			json.append("conectividades", jarray);
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);
		
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	private void getConectividades(HttpServletRequest req, HttpServletResponse resp){
		String producto = req.getParameter("producto");
		
		
		
				
		try{
			
			
			ConectividadProyectoDao conectividadDao = ConectividadProyectoDao.getInstance();
			List<ConectividadProyecto> conectividades = conectividadDao.getConectividadesByProducto(producto);
			JSONObject json = new JSONObject();
			List<String> jarray = new ArrayList<String>();
			
			for(ConectividadProyecto conect : conectividades){
				jarray.add(conect.getName());
			}
			

			
			json.append("success","true");
			json.append("jarray", jarray);
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);
		
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	private void deleteProject(HttpServletRequest req, HttpServletResponse resp, String usermail) throws JSONException, IOException{
		
		JSONObject json = new JSONObject();
		ProyectoDao pDao = ProyectoDao.getInstance();	
		
		String id = req.getParameter("id");
		
		Proyecto p = pDao.getProjectbyId(Long.parseLong(id));
		pDao.deleteProject(p, usermail);
		
		json.append("success", "true");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);		
	}
	
	private void editProject (HttpServletRequest req, HttpServletResponse resp, String usermail) throws JSONException, IOException{
		JSONObject json = new JSONObject();		
		ProyectoDao pDao = ProyectoDao.getInstance();
		
		try {
		
			String id = req.getParameter("id");
			
			Proyecto p = pDao.getProjectbyId(Long.parseLong(id));
			
			if (p!=null){
				//String fecha_alta_str = req.getParameter("fecha_alta_cliente");
				
				//String tipo = req.getParameter("tipo");
				
				//String cliente = req.getParameter("cliente");
				String clasificacion = req.getParameter("clasificacion");
				String gestor_it_str = req.getParameter("gestor_it");
				String gestor_negocio_str = req.getParameter("gestor_negocio");
				//String coste = req.getParameter("coste");
				String url_doc_google_drive = req.getParameter("url_doc_google_drive");
				String producto = req.getParameter("producto");
				String conectividad = req.getParameter("conectividad");
				String fecha_inicio_valoracion_str = req.getParameter("fecha_inicio_valoracion");
				String fecha_fin_valoracion_str = req.getParameter("fecha_fin_valoracion");	
				String fecha_inicio_viabilidad_str = req.getParameter("fecha_inicio_viabilidad");
				String fecha_fin_viabilidad_str = req.getParameter("fecha_fin_viabilidad");
				String fecha_plan_trabajo_str = req.getParameter("fecha_plan_trabajo");
				String fecha_disponible_conectividad_str = req.getParameter("fecha_disponible_conectividad");
				String observaciones = req.getParameter("observaciones");
				String envioC100 = req.getParameter("envio_c100");
				String ok_negocio = req.getParameter("ok_negocio");
				String ok_negocio_check = req.getParameter("ok_negocio_check");
				
				//String cliente_nombre = req.getParameter("cliente_id");
				

				//p.setFecha_alta_str(fecha_alta_str);
				//p.setFecha_alta(Utils.dateConverter(fecha_alta_str));

				
				//p.setTipo(tipo);
				//p.setClienteKey(Long.parseLong(cliente_nombre));
				if (!"".equals(clasificacion))
				p.setClasificacion(Integer.parseInt(clasificacion));
				if (!"".equals(gestor_it_str))
				p.setGestor_it(Long.parseLong(gestor_it_str));
				if (!"".equals(gestor_negocio_str))
				p.setGestor_negocio(Long.parseLong(gestor_negocio_str));
				//p.setCoste(coste);
				if (!"".equals(url_doc_google_drive))
				p.setUrl_doc_google_drive(url_doc_google_drive);
				if (!"default".equals(producto) && !"".equals(producto)){
					p.setProducto(producto);
				}
				if (!"default".equals(conectividad) && !"".equals(conectividad)){
					p.setConectividad(conectividad);	
				}
				/*if (!"".equals(fecha_inicio_valoracion_str))
				p.setStr_fecha_inicio_valoracion(fecha_inicio_valoracion_str);
				if (!"".equals(fecha_fin_valoracion_str))
				p.setStr_fecha_fin_valoracion(fecha_fin_valoracion_str);
				if (!"".equals(fecha_inicio_viabilidad_str))
				p.setStr_fecha_inicio_viabilidad(fecha_inicio_viabilidad_str);
				if (!"".equals(fecha_fin_viabilidad_str))
				p.setStr_fecha_fin_viabilidad(fecha_fin_viabilidad_str);
				if (!"".equals(fecha_plan_trabajo_str))
				p.setStr_fecha_plan_trabajo(fecha_plan_trabajo_str);
				if (!"".equals(fecha_disponible_conectividad_str))
				p.setStr_fecha_disponible_conectividad(fecha_disponible_conectividad_str);*/
				
				p.setStr_fecha_inicio_valoracion(fecha_inicio_valoracion_str);
				p.setStr_fecha_fin_valoracion(fecha_fin_valoracion_str);
				p.setStr_fecha_inicio_viabilidad(fecha_inicio_viabilidad_str);
				p.setStr_fecha_fin_viabilidad(fecha_fin_viabilidad_str);
				p.setStr_fecha_plan_trabajo(fecha_plan_trabajo_str);
				p.setStr_fecha_disponible_conectividad(fecha_disponible_conectividad_str);
				p.setObservaciones(observaciones);
				p.setStr_envioC100(envioC100);
				p.setStr_OKNegocio(ok_negocio);
					
				/*if (!"".equals(envioC100))
				p.setStr_envioC100(envioC100);
				if (!"".equals(ok_negocio))
				p.setStr_OKNegocio(ok_negocio);*/
				if (!"".equals(ok_negocio_check))
				p.setStr_OKNegocioCheck(ok_negocio_check);
					
				pDao.createProject(p,usermail);
			
				json.append("success", "true");
				json.append("id", p.getKey().getId());
				
			}
			
		} catch (Exception e) {
			json.append("failure", "true");
			json.append("error", "Se ha producido un error inesperado");
			json.append("errordetail", e.getStackTrace());
			e.printStackTrace();
		}	
			
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);		
		
	}
//////////////////////////////////////////////////////////////////////////
	private void createProject(HttpServletRequest req, HttpServletResponse resp, String usermail) throws IOException, JSONException{
		
		JSONObject json = new JSONObject();
		Proyecto p = new Proyecto();
	
		try {
			
			String fecha_alta_str = req.getParameter("fecha_alta_cliente");
			
			String envioC100 = req.getParameter("envio_c100");
			String ok_negocio = req.getParameter("ok_negocio");
			String ok_negocio_check = req.getParameter("ok_negocio_check");
			
			String fecha_inicio_valoracion_str = req.getParameter("fecha_inicio_valoracion");
			String fecha_fin_valoracion_str = req.getParameter("fecha_fin_valoracion");
			
			String fecha_inicio_viabilidad_str = req.getParameter("fecha_inicio_viabilidad");
			String fecha_fin_viabilidad_str = req.getParameter("fecha_fin_viabilidad");
			
			String fecha_plan_trabajo_str = req.getParameter("fecha_plan_trabajo");
			String fecha_disponible_conectividad_str = req.getParameter("fecha_disponible_conectividad");
			
			String producto = req.getParameter("producto");
			String conectividad = req.getParameter("conectividad");
			String subtipo = req.getParameter("subtipo");
			String observaciones = req.getParameter("observaciones");
			
			
			
			
			String tipo = req.getParameter("tipo");
			String cliente = req.getParameter("cliente");

			
			String clasificacion = req.getParameter("clasificacion");
			String gestor_it_str = req.getParameter("gestor_it");
			String gestor_negocio_str = req.getParameter("gestor_negocio");
			String coste = req.getParameter("coste");
			String url_doc_google_drive = req.getParameter("url_doc_google_drive");
			
			if (fecha_alta_str.equals("")||tipo.equals("")||cliente.equals("")||clasificacion.equals("")
					||gestor_it_str.equals("")||gestor_negocio_str.equals("")){
				json.append("failure", "true");
				json.append("error","Faltan campos obligatorios.");
				
			}else{
				
				ProyectoDao pDao = ProyectoDao.getInstance();		
				
				p.setStr_envioC100(envioC100);
				p.setStr_OKNegocio(ok_negocio);
				p.setStr_OKNegocioCheck(ok_negocio_check);
				
				p.setSubtipo(subtipo);
				
				p.setStr_fecha_fin_valoracion(fecha_fin_valoracion_str);
				p.setStr_fecha_inicio_valoracion(fecha_inicio_valoracion_str);
				
				p.setStr_fecha_fin_viabilidad(fecha_fin_viabilidad_str);
				p.setStr_fecha_inicio_viabilidad(fecha_inicio_viabilidad_str);
				
				p.setStr_fecha_disponible_conectividad(fecha_disponible_conectividad_str);
				p.setStr_fecha_plan_trabajo(fecha_plan_trabajo_str);
				
				p.setProducto(producto);
				
				if (!"default".equals(conectividad))
					p.setConectividad(conectividad);
				
				
				p.setFecha_alta_str(fecha_alta_str);					
				p.setFecha_alta(Utils.dateConverter(fecha_alta_str));					
				
				p.setTipo(tipo);
				p.setClienteKey(Long.parseLong(cliente));
				p.setClasificacion(Integer.parseInt(clasificacion));
				p.setGestor_it(Long.parseLong(gestor_it_str));
				p.setGestor_negocio(Long.parseLong(gestor_negocio_str));
				p.setCoste(coste);
				p.setObservaciones(observaciones);
				p.setUrl_doc_google_drive(url_doc_google_drive);
				
				
				pDao.createProject(p,usermail);
				
				json.append("success", "true");
				json.append("id", p.getKey().getId());
							
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
//////////////////////////////////////////////////////////////////////////
	/*
	private void createProject(HttpServletRequest req, HttpServletResponse resp, String usermail) throws IOException, JSONException{
		
		JSONObject json = new JSONObject();
		Proyecto p = new Proyecto();
	
		try {
			
			String fecha_alta_str = req.getParameter("fecha_alta_cliente");
			
			String envioC100 = req.getParameter("envio_c100");
			String ok_negocio = req.getParameter("ok_negocio");
			String ok_negocio_check = req.getParameter("ok_negocio_check");
			
			String fecha_inicio_valoracion_str = req.getParameter("fecha_inicio_valoracion");
			String fecha_fin_valoracion_str = req.getParameter("fecha_fin_valoracion");
			
			String fecha_inicio_viabilidad_str = req.getParameter("fecha_inicio_viabilidad");
			String fecha_fin_viabilidad_str = req.getParameter("fecha_fin_viabilidad");
			
			String fecha_plan_trabajo_str = req.getParameter("fecha_plan_trabajo");
			String fecha_disponible_conectividad_str = req.getParameter("fecha_disponible_conectividad");
			
			String producto = req.getParameter("producto");
			String conectividad = req.getParameter("conectividad");
			String subtipo = req.getParameter("subtipo");
			
			
			
			
			String tipo = req.getParameter("tipo");
			String cliente = req.getParameter("cliente");

			
			String clasificacion = req.getParameter("clasificacion");
			String gestor_it_str = req.getParameter("gestor_it");
			String gestor_negocio_str = req.getParameter("gestor_negocio");
			String coste = req.getParameter("coste");
			String url_doc_google_drive = req.getParameter("url_doc_google_drive");
			
			if (fecha_alta_str.equals("")||tipo.equals("")||cliente.equals("")||clasificacion.equals("")
					||gestor_it_str.equals("")||gestor_negocio_str.equals("")){
				json.append("failure", "true");
				json.append("error","Faltan campos obligatorios.");
				
			}else{
				
				ProyectoDao pDao = ProyectoDao.getInstance();		
				
				p.setStr_envioC100(envioC100);
				p.setStr_OKNegocio(ok_negocio);
				p.setStr_OKNegocioCheck(ok_negocio_check);
				
				p.setSubtipo(subtipo);
				
				p.setStr_fecha_fin_valoracion(fecha_fin_valoracion_str);
				p.setStr_fecha_inicio_valoracion(fecha_inicio_valoracion_str);
				
				p.setStr_fecha_fin_viabilidad(fecha_fin_viabilidad_str);
				p.setStr_fecha_inicio_viabilidad(fecha_inicio_viabilidad_str);
				
				p.setStr_fecha_disponible_conectividad(fecha_disponible_conectividad_str);
				p.setStr_fecha_plan_trabajo(fecha_plan_trabajo_str);
				
				p.setProducto(producto);
				
				if (!"default".equals(conectividad))
					p.setConectividad(conectividad);
				
				
				p.setFecha_alta_str(fecha_alta_str);					
				p.setFecha_alta(Utils.dateConverter(fecha_alta_str));					
				
				p.setTipo(tipo);
				p.setClienteKey(Long.parseLong(cliente));
				p.setClasificacion(Integer.parseInt(clasificacion));
				p.setGestor_it(Long.parseLong(gestor_it_str));
				p.setGestor_negocio(Long.parseLong(gestor_negocio_str));
				p.setCoste(coste);
				p.setUrl_doc_google_drive(url_doc_google_drive);
				
				
				pDao.createProject(p,usermail);
				
				json.append("success", "true");
				json.append("id", p.getKey().getId());
							
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	*/
//////////////////////////////////////////////////////////////////////////
	
	private void cloneProject(HttpServletRequest req, HttpServletResponse resp, String usermail) throws IOException, JSONException{
		
		JSONObject json = new JSONObject();
		Proyecto p = new Proyecto();
	
		try {
			
			String fecha_alta_str = req.getParameter("fecha_alta_cliente");
			
			String envioC100 = req.getParameter("envio_c100");
			String ok_negocio = req.getParameter("ok_negocio");
			String ok_negocio_check = req.getParameter("ok_negocio_check");
			
			String fecha_inicio_valoracion_str = req.getParameter("fecha_inicio_valoracion");
			String fecha_fin_valoracion_str = req.getParameter("fecha_fin_valoracion");
			
			String fecha_inicio_viabilidad_str = req.getParameter("fecha_inicio_viabilidad");
			String fecha_fin_viabilidad_str = req.getParameter("fecha_fin_viabilidad");
			
			String fecha_plan_trabajo_str = req.getParameter("fecha_plan_trabajo");
			String fecha_disponible_conectividad_str = req.getParameter("fecha_disponible_conectividad");
			
			String producto = req.getParameter("producto");
			String conectividad = req.getParameter("conectividad");
			String subtipo = req.getParameter("subtipo");
			
			
			
			
			String tipo = req.getParameter("tipo");
			String cliente = req.getParameter("cliente");

			
			String clasificacion = req.getParameter("clasificacion");
			String gestor_it_str = req.getParameter("gestor_it");
			String gestor_negocio_str = req.getParameter("gestor_negocio");
			String coste = req.getParameter("coste");
			String url_doc_google_drive = req.getParameter("url_doc_google_drive");
			
			if (fecha_alta_str.equals("")||tipo.equals("")||cliente.equals("")||clasificacion.equals("")
					||gestor_it_str.equals("")||gestor_negocio_str.equals("")){
				json.append("failure", "true");
				json.append("error","Faltan campos obligatorios.");
				
			}else{
				
				ProyectoDao pDao = ProyectoDao.getInstance();		
				
				p.setStr_envioC100(envioC100);
				p.setStr_OKNegocio(ok_negocio);
				p.setStr_OKNegocioCheck(ok_negocio_check);
				
				p.setSubtipo(subtipo);
				
				p.setStr_fecha_fin_valoracion(fecha_fin_valoracion_str);
				p.setStr_fecha_inicio_valoracion(fecha_inicio_valoracion_str);
				
				p.setStr_fecha_fin_viabilidad(fecha_fin_viabilidad_str);
				p.setStr_fecha_inicio_viabilidad(fecha_inicio_viabilidad_str);
				
				p.setStr_fecha_disponible_conectividad(fecha_disponible_conectividad_str);
				p.setStr_fecha_plan_trabajo(fecha_plan_trabajo_str);
				
				p.setProducto(producto);
				
				if (!"default".equals(conectividad))
					p.setConectividad(conectividad);
				
				
				p.setFecha_alta_str(fecha_alta_str);					
				p.setFecha_alta(Utils.dateConverter(fecha_alta_str));					
				
				p.setTipo(tipo);
				ClienteDao clienteDao = ClienteDao.getInstance();
				Cliente clienteEnty = clienteDao.getClienteByName(cliente);
				p.setClienteKey(clienteEnty.getKey().getId());
				p.setClasificacion(Integer.parseInt(clasificacion));
				p.setGestor_it(Long.parseLong(gestor_it_str));
				p.setGestor_negocio(Long.parseLong(gestor_negocio_str));
				p.setCoste(coste);
				p.setUrl_doc_google_drive(url_doc_google_drive);
				
				
				pDao.createProject(p,usermail);
				
				json.append("success", "true");
				json.append("id", p.getKey().getId());
							
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
}
