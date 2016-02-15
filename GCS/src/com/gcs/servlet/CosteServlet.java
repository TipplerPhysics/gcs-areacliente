package com.gcs.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
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
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.gcs.beans.Cliente;
import com.gcs.beans.Coste;
import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ContadorCodCosteDao;
import com.gcs.dao.CosteDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.UserDao;
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
		
		HttpSession session = req.getSession();
		String usermail = (String)session.getAttribute("usermail");

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
					createCoste(req, resp,usermail);
				} else if (accion.equals("delete")) {
					deleteCoste(req, resp,usermail);
				} else if (accion.equals("update")) {
					updateCoste(req, resp,usermail);
				}else if (accion.equals("xls")) {
					generateXLS(req, resp);
				}else if (accion.equals("clone")) {
					cloneCoste(req, resp,usermail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}
	
	private void generateXLS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=CostesGCS.xls");

			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream());

			CosteDao cDao = CosteDao.getInstance();
			List<Coste> costes = cDao.getAllCostes();
			
			WritableSheet s = w.createSheet("Gestion de costes", 0);

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.WHITE);
			
			WritableFont cellFont2 = new WritableFont(WritableFont.TIMES, 12);
			cellFont2.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.BLUE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			
			WritableCellFormat cellFormatRight = new WritableCellFormat(cellFont2);
		
			
			cellFormatRight.setAlignment(jxl.format.Alignment.RIGHT);
			cellFormatRight.setVerticalAlignment(VerticalAlignment.CENTRE);
			
			// WritableCellFormat numberFormat=new WritableCellFormat(new  jxl.write.NumberFormat("#.##"));
			// numberFormat.setShrinkToFit(true);


			s.setColumnView(0, 20);
			s.setColumnView(1, 30);
			s.setColumnView(2, 30);
			s.setColumnView(3, 30);
			s.setColumnView(4, 25);
			s.setColumnView(5, 30);
			s.setColumnView(6, 25);
			s.setColumnView(7, 25);
			s.setColumnView(8, 50);
			s.setColumnView(9, 40);
			s.setColumnView(10, 40);
			s.setColumnView(11, 10);
			s.setColumnView(12, 10);
			s.setColumnView(13, 10);
			s.setColumnView(14, 10);
			s.setColumnView(15, 10);
			s.setColumnView(16, 10);
			s.setColumnView(17, 10);
			s.setColumnView(18, 10);
			s.setColumnView(19, 10);
			s.setColumnView(20, 10);
			s.setColumnView(21, 10);
			s.setColumnView(22, 10);
			s.setColumnView(23, 10);
			
			//s.setColumnView(15, 30);
			//s.setRowView(0, 900);
			
			s.mergeCells(0, 0, 0, 1);
			s.mergeCells(1, 0, 1, 1);
			s.mergeCells(2, 0, 2, 1);
			s.mergeCells(3, 0, 3, 1);
			s.mergeCells(4, 0, 4, 1);
			s.mergeCells(5, 0, 5, 1);
			s.mergeCells(6, 0, 6, 1);
			s.mergeCells(7, 0, 7, 1);
			s.mergeCells(8, 0, 8, 1);
			s.mergeCells(9, 0, 9, 1);
			s.mergeCells(10, 0, 10, 1);
			s.mergeCells(11, 0, 12, 0);
			s.mergeCells(13, 0, 14, 0);
			s.mergeCells(15, 0, 16, 0);
			s.mergeCells(17, 0, 18, 0);
			s.mergeCells(19, 0, 20, 0);
			s.mergeCells(21, 0, 22, 0);
			s.mergeCells(23, 0, 23, 1);
			

			s.addCell(new Label(0, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(1, 0, "NOMBRE PROYECTO", cellFormat));
			s.addCell(new Label(2, 0, "NUM. CONTROL", cellFormat));
			s.addCell(new Label(3, 0, "EQUIPO", cellFormat));
			s.addCell(new Label(4, 0, "CONTROL PRESUPUESTARIO", cellFormat));
			s.addCell(new Label(5, 0, "FECHA ALTA COSTES", cellFormat));
			s.addCell(new Label(6, 0, "GESTOR IT", cellFormat));
			s.addCell(new Label(7, 0, "NUM. VALORACION", cellFormat));
			s.addCell(new Label(8, 0, "COMENTARIOS", cellFormat));
			s.addCell(new Label(9, 0, "FECHA SOLICITUD VALORACION", cellFormat));
			s.addCell(new Label(10, 0, "FECHA RECEPCION VALORACION", cellFormat));
			s.addCell(new Label(11, 0, "ANALISIS", cellFormat));
			s.addCell(new Label(11, 1, "HORAS", cellFormat));
			s.addCell(new Label(12, 1, "COSTE", cellFormat));
			s.addCell(new Label(13, 0, "DISEÑO", cellFormat));
			s.addCell(new Label(13, 1, "HORAS", cellFormat));
			s.addCell(new Label(14, 1, "COSTE", cellFormat));
			s.addCell(new Label(15, 0, "CONSTRUCCIÓN", cellFormat));
			s.addCell(new Label(15, 1, "HORAS", cellFormat));
			s.addCell(new Label(16, 1, "COSTE", cellFormat));
			s.addCell(new Label(17, 0, "PRUEBAS", cellFormat));
			s.addCell(new Label(17, 1, "HORAS", cellFormat));
			s.addCell(new Label(18, 1, "COSTE", cellFormat));
			s.addCell(new Label(19, 0, "GESTIÓN",cellFormat));
			s.addCell(new Label(19, 1, "HORAS", cellFormat));
			s.addCell(new Label(20, 1, "COSTE", cellFormat));
			s.addCell(new Label(21, 0, "TOTAL", cellFormat));
			s.addCell(new Label(21, 1, "HORAS", cellFormat));
			s.addCell(new Label(22, 1, "COSTE", cellFormat));
			
			

			////////////////////////////////////////UserDao uDao = UserDao.getInstance();
			////////////////////////////////////////User u = new User();

			int aux = 2;

			
			////////////////////////////////////////////
			
			for (Coste c : costes) {
				
				s.addCell(new Label(0, aux, c.getCliente_name()));
				s.addCell(new Label(1, aux, c.getProject_name()));
				if(!c.getNum_control().equals("")&&c.getNum_control()!=null)
				s.addCell(new Label(2, aux, c.getNum_control()));
				s.addCell(new Label(3, aux, c.getEquipos()));
				s.addCell(new Label(4, aux, c.getControl_presupuestario()));
				s.addCell(new Label(5, aux, c.getStr_fecha_alta()));
				s.addCell(new Label(6, aux, c.getGestor_it_name()));
				s.addCell(new Label(7, aux, c.getNum_valoracion()));
				
				if(!c.getComentarios().equals("")&&c.getComentarios()!=null)
				s.addCell(new Label(8, aux, c.getComentarios()));
				
				if(c.getStr_fecha_solicitud_valoracion()!=null)
					s.addCell(new Label(9, aux, c.getStr_fecha_solicitud_valoracion().replace(",", ".")));
				
				if(c.getStr_fecha_recepcion_valoracion()!=null)
					s.addCell(new Label(10, aux, c.getStr_fecha_recepcion_valoracion().replace(",", ".")));					
		
				if (!"".equals(c.getHoras_analisis())&&c.getHoras_analisis()!=null)
					s.addCell(new Number(11, aux, Double.parseDouble(c.getHoras_analisis().replace(",", ".")),cellFormatRight));
								
				if (!"".equals(c.getCoste_analisis())&&c.getCoste_analisis()!=null)
					s.addCell(new Number(12, aux, Double.parseDouble(c.getCoste_analisis().replace(",", ".").replace(" ", "")),cellFormatRight));
				
				if (!"".equals(c.getHoras_diseño())&&c.getHoras_diseño()!=null)
					s.addCell(new Number(13, aux, Double.parseDouble(c.getHoras_diseño().replace(",", ".").replace(" ", "")),cellFormatRight));
				
				if (!"".equals(c.getCoste_diseño())&&c.getCoste_diseño()!=null)
					s.addCell(new Number(14, aux, Double.parseDouble(c.getCoste_diseño().replace(",", ".").replace(" ", "")),cellFormatRight));
				
				if (!"".equals(c.getHoras_construccion())&&c.getHoras_construccion()!=null)
					s.addCell(new Number(15, aux, Double.parseDouble(c.getHoras_construccion().replace(",", ".").replace(" ", "")),cellFormatRight));
				
				if (!"".equals(c.getCoste_construccion())&&c.getCoste_construccion()!=null)
					s.addCell(new Number(16, aux, Double.parseDouble(c.getCoste_construccion().replace(",", ".").replace(" ", "")),cellFormatRight));
				
				if (!"".equals(c.getHoras_pruebas())&&c.getHoras_pruebas()!=null)
					s.addCell(new Number(17, aux, Double.parseDouble(c.getHoras_pruebas().replace(",", ".").replace(" ", "")),cellFormatRight));
				
				if (!"".equals(c.getCoste_pruebas())&&c.getCoste_pruebas()!=null)
					s.addCell(new Number(18, aux, Double.parseDouble(c.getCoste_pruebas().replace(",", ".").replace(" ", "")),cellFormatRight));
				
				if (!"".equals(c.getHoras_gestion())&&c.getHoras_gestion()!=null)
					s.addCell(new Number(19, aux, Double.parseDouble(c.getHoras_gestion().replace(",", ".").replace(" ", "")),cellFormatRight));
				
				if (!"".equals(c.getCoste_gestion())&&c.getCoste_gestion()!=null)
					s.addCell(new Number(20, aux, Double.parseDouble(c.getCoste_gestion().replace(",", ".").replace(" ", "")),cellFormatRight));
				
				if (!"".equals(c.getHoras_total())&&c.getHoras_total()!=null)
					
					s.addCell(new Number(21, aux, Double.parseDouble(c.getHoras_total().replace(",", ".").replace(" ", "")),cellFormatRight));
				
				
				
				if (!"".equals(c.getCoste_total())&&c.getCoste_total()!=null){
					String costetotal = c.getCoste_total();
					if(costetotal.contains(".")&&costetotal.contains(","))costetotal.replace(".", "");
					s.addCell(new Number(22, aux, Double.parseDouble(costetotal.replace(",", ".").replace(" ", "")),cellFormatRight));
				}
				
				
				aux++;
			}
						
			////////////////////////////////////////////
			/*
			    if(!c.getStr_fecha_solicitud_valoracion().equals("")&&c.getStr_fecha_solicitud_valoracion()!=null)
					s.addCell(new Label(8, aux, c.getStr_fecha_solicitud_valoracion().replace(",", ".")));
					
				if(!c.getStr_fecha_recepcion_valoracion().equals("")&&c.getStr_fecha_recepcion_valoracion()!=null)
					s.addCell(new Label(9, aux,c.getStr_fecha_recepcion_valoracion()));							
			*/
			////////////////////////////////////////////////

			w.write();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Exception in Excel", e);
		} finally {
		}

	}

	private void updateCoste(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws JSONException, IOException {

		JSONObject json = new JSONObject();

		try {

			String id = req.getParameter("id");
			String analisis_coste = req.getParameter("analisis_coste");
			String analisis_horas = req.getParameter("analisis_horas");
			String comentarios = req.getParameter("comentarios");
			String construccion_coste = req.getParameter("construccion_coste");
			String construccion_horas = req.getParameter("construccion_horas");
			String diseño_coste = req.getParameter("diseño_coste");
			String diseño_horas = req.getParameter("diseño_horas");
			String equipo = req.getParameter("equipo");
			String fecha_alta_costes = req.getParameter("fecha_alta_costes");
			String fecha_solicitud_valoracion = req
					.getParameter("fecha_solicitud_valoracion");
			String fecha_recepcion_valoracion = req
					.getParameter("fecha_recepcion_valoracion");
			String gestion_coste = req.getParameter("gestion_coste");
			String gestion_horas = req.getParameter("gestion_horas");
			String gestor_it = req.getParameter("gestor_it");

			String pruebas_horas = req.getParameter("pruebas_horas");
			String pruebas_costes = req.getParameter("pruebas_coste");
			String total_coste = req.getParameter("total_coste");
			String total_horas = req.getParameter("total_horas");
			String num_valoracion = req.getParameter("num_valoracion");
			String num_control = req.getParameter("numero_control");
			String control_presupuestario = req.getParameter("control_presupuestario");

			CosteDao cDao = CosteDao.getInstance();

			Coste c = cDao.getCostebyId(Long.parseLong(id));

			c.setNum_control(num_control);
			c.setCoste_analisis(analisis_coste);
			c.setHoras_analisis(analisis_horas);
			c.setComentarios(comentarios);
			c.setControl_presupuestario(control_presupuestario);
			c.setCoste_construccion(construccion_coste);
			c.setHoras_construccion(construccion_horas);
			c.setHoras_diseño(diseño_horas);
			c.setCoste_diseño(diseño_coste);
			if (!"default".equals(equipo))
				c.setEquipos(equipo);
			c.setStr_fecha_alta(fecha_alta_costes);
			c.setFecha_alta(Utils.dateConverter(fecha_alta_costes));
			c.setStr_fecha_solicitud_valoracion(fecha_solicitud_valoracion);
			c.setStr_fecha_recepcion_valoracion(fecha_recepcion_valoracion);
			c.setFecha_solicitud_valoracion(Utils
					.dateConverter(fecha_solicitud_valoracion));
			c.setHoras_gestion(gestion_horas);
			c.setCoste_gestion(gestion_coste);
			if (!"default".equals(gestor_it)) {
				c.setGestor_it_key(Long.parseLong(gestor_it));
				UserDao uDao = UserDao.getInstance();
				User gestor_it_obj = uDao
						.getUserbyId(Long.parseLong(gestor_it));
				c.setGestor_it_name(gestor_it_obj.getNombre() + " "
						+ gestor_it_obj.getApellido1() + " "
						+ gestor_it_obj.getApellido2());
			}

			c.setHoras_pruebas(pruebas_horas);
			c.setCoste_pruebas(pruebas_costes);

			c.setCoste_total(total_coste);
			c.setHoras_total(total_horas);
			

			if (!"default".equals(num_valoracion))
				c.setNum_valoracion(num_valoracion);

			cDao.createCoste(c,usermail);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		json.append("success", "true");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);

	}

	private void createCoste(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws JSONException, IOException {

		JSONObject json = new JSONObject();

		try {
			String cliente = req.getParameter("cliente");
			String analisis_coste = req.getParameter("analisis_coste");
			String analisis_horas = req.getParameter("analisis_horas");
			String comentarios = req.getParameter("comentarios");
			String construccion_coste = req.getParameter("construccion_coste");
			String construccion_horas = req.getParameter("construccion_horas");
			String diseño_coste = req.getParameter("diseño_coste");
			String diseño_horas = req.getParameter("diseño_horas");
			String equipo = req.getParameter("equipo");
			String fecha_alta_costes = req.getParameter("fecha_alta_costes");
			String fecha_solicitud_valoracion = req
					.getParameter("fecha_solicitud_valoracion");
			String fecha_recepcion_valoracion = req
					.getParameter("fecha_recepcion_valoracion");
			String plazo_estimado = req
					.getParameter("plazo_estimado");
			String gestion_coste = req.getParameter("gestion_coste");
			String gestion_horas = req.getParameter("gestion_horas");
			String gestor_it = req.getParameter("gestor_it");
			String project = req.getParameter("project");
			String pruebas_horas = req.getParameter("pruebas_horas");
			String pruebas_costes = req.getParameter("pruebas_coste");
			String total_coste = req.getParameter("total_coste");
			String total_horas = req.getParameter("total_horas");
			String num_valoracion = req.getParameter("num_valoracion");
			String asunto = req.getParameter("asunto");
			String tipo_coste = req.getParameter("tipo_coste");
			
			ContadorCodCosteDao contDao = ContadorCodCosteDao.getInstance();
			contDao.increaseCont();
			//String num_control = String.valueOf(contDao.getContadorValue());
			String control_presupuestario = req.getParameter("control_presupuestario");

			CosteDao cDao = CosteDao.getInstance();
			ClienteDao clDao = ClienteDao.getInstance();
			ProyectoDao pDao = ProyectoDao.getInstance();
			Coste c = new Coste();
			Proyecto p = pDao.getProjectbyId(Long.parseLong(project));

			Cliente cliente_obj = clDao.getClienteById(Long.parseLong(cliente));
			
			//c.setNum_control(num_control);
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
			c.setStr_fecha_recepcion_valoracion(fecha_recepcion_valoracion);
			c.setFecha_solicitud_valoracion(Utils
					.dateConverter(fecha_solicitud_valoracion));
			c.setFecha_recepcion_valoracion(Utils
					.dateConverter(fecha_recepcion_valoracion));
			c.setHoras_gestion(gestion_horas);
			c.setCoste_gestion(gestion_coste);
			if (!"default".equals(gestor_it)) {
				c.setGestor_it_key(Long.parseLong(gestor_it));
				UserDao uDao = UserDao.getInstance();
				User gestor_it_obj = uDao
						.getUserbyId(Long.parseLong(gestor_it));
				c.setGestor_it_name(gestor_it_obj.getNombre() + " "
						+ gestor_it_obj.getApellido1() + " "
						+ gestor_it_obj.getApellido2());
			}

			c.setProjectKey(Long.parseLong(project));
			c.setProject_name(p.getCod_proyecto());

			c.setHoras_pruebas(pruebas_horas);
			c.setCoste_pruebas(pruebas_costes);

			c.setCoste_total(total_coste);
			c.setHoras_total(total_horas);
			c.setControl_presupuestario(control_presupuestario);
			c.setStr_plazo_estimado(plazo_estimado);
			c.setPlazo_estimado(Utils
					.dateConverter(plazo_estimado));
			c.setTipo_coste(tipo_coste);
			c.setAsunto(asunto);

			if (!"default".equals(num_valoracion))
				c.setNum_valoracion(num_valoracion);

			cDao.createCoste(c,usermail);
			json.append("success", "true");
			
		} catch (Exception e) {
			e.printStackTrace();
			json.append("success", "false");
			json.append("error", "Ha ocurrido un error.");
			json.append("errorDetail", e.getStackTrace());
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	private void cloneCoste(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws JSONException, IOException {

		JSONObject json = new JSONObject();

		try {
			String cliente = req.getParameter("cliente");
			String analisis_coste = req.getParameter("analisis_coste");
			String analisis_horas = req.getParameter("analisis_horas");
			String comentarios = req.getParameter("comentarios");
			String construccion_coste = req.getParameter("construccion_coste");
			String construccion_horas = req.getParameter("construccion_horas");
			String diseño_coste = req.getParameter("diseño_coste");
			String diseño_horas = req.getParameter("diseño_horas");
			String equipo = req.getParameter("equipo");
			String fecha_alta_costes = req.getParameter("fecha_alta_costes");
			String fecha_solicitud_valoracion = req
					.getParameter("fecha_solicitud_valoracion");
			String fecha_recepcion_valoracion = req
					.getParameter("fecha_recepcion_valoracion");
			String gestion_coste = req.getParameter("gestion_coste");
			String gestion_horas = req.getParameter("gestion_horas");
			String gestor_it = req.getParameter("gestor_it");
			String project = req.getParameter("project");
			String pruebas_horas = req.getParameter("pruebas_horas");
			String pruebas_costes = req.getParameter("pruebas_coste");
			String total_coste = req.getParameter("total_coste");
			String total_horas = req.getParameter("total_horas");
			String num_valoracion = req.getParameter("num_valoracion");
			String num_control = req.getParameter("numero_control");
			String control_presupuestario = req.getParameter("control_presupuestario");
			String asunto = req.getParameter("asunto");
			String tipo_coste = req.getParameter("tipo_coste");
			String plazo_estimado = req.getParameter("plazo_estimado");

			CosteDao cDao = CosteDao.getInstance();
			ClienteDao clDao = ClienteDao.getInstance();
			ProyectoDao pDao = ProyectoDao.getInstance();
			Coste c = new Coste();
			
			Proyecto p = pDao.getProjectsByCode(project).get(0);

			
			Cliente cliente_obj = clDao.getClienteByName(cliente);
			
			c.setNum_control(num_control);
			c.setControl_presupuestario(control_presupuestario);
			c.setCliente_name(cliente_obj.getNombre());
			c.setClienteKey(cliente_obj.getKey().getId());
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
			c.setStr_fecha_recepcion_valoracion(fecha_recepcion_valoracion);
			c.setFecha_solicitud_valoracion(Utils
					.dateConverter(fecha_solicitud_valoracion));
			c.setHoras_gestion(gestion_horas);
			c.setCoste_gestion(gestion_coste);
			if (!"default".equals(gestor_it)) {
				c.setGestor_it_key(Long.parseLong(gestor_it));
				UserDao uDao = UserDao.getInstance();
				User gestor_it_obj = uDao
						.getUserbyId(Long.parseLong(gestor_it));
				c.setGestor_it_name(gestor_it_obj.getNombre() + " "
						+ gestor_it_obj.getApellido1() + " "
						+ gestor_it_obj.getApellido2());
			}

			c.setProjectKey(p.getKey().getId());
			c.setProject_name(p.getCod_proyecto());

			c.setHoras_pruebas(pruebas_horas);
			c.setCoste_pruebas(pruebas_costes);

			c.setCoste_total(total_coste);
			c.setHoras_total(total_horas);
			c.setAsunto(asunto);
			c.setTipo_coste(tipo_coste);
			c.setPlazo_estimado(Utils
					.dateConverter(plazo_estimado));

			if (!"default".equals(num_valoracion))
				c.setNum_valoracion(num_valoracion);

			cDao.createCoste(c,usermail);
			json.append("success", "true");
			
		} catch (Exception e) {
			e.printStackTrace();
			json.append("success", "false");
			json.append("error", "Ha ocurrido un error.");
			json.append("errorDetail", e.getStackTrace());
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}

	private void deleteCoste(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();
		String id = req.getParameter("id");
		CosteDao cDao = CosteDao.getInstance();

		Coste c = cDao.getCostebyId(Long.parseLong(id));

		cDao.deleteCoste(c,usermail);

		json.append("success", "true");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}	

}
