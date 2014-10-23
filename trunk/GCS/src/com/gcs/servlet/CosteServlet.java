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
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.NumberCell;
import jxl.write.Number;
import jxl.format.CellFormat;
import jxl.write.biff.NumberRecord;

import com.gcs.beans.Cliente;
import com.gcs.beans.Coste;
import com.gcs.beans.Demanda;
import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.CosteDao;
import com.gcs.dao.DemandaDao;
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
					deleteCoste(req, resp);
				} else if (accion.equals("update")) {
					updateCoste(req, resp);
				}else if (accion.equals("xls")) {
					generateXLS(req, resp);
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
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=CostesGCS.xls");

			WritableWorkbook w = Workbook
					.createWorkbook(resp.getOutputStream());

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
			s.setColumnView(4, 30);
			s.setColumnView(5, 25);
			s.setColumnView(6, 25);
			s.setColumnView(7, 50);
			s.setColumnView(8, 40);
			s.setColumnView(9, 10);
			s.setColumnView(10, 10);
			s.setColumnView(11, 10);
			s.setColumnView(12, 10);
			s.setColumnView(13, 10);
			s.setColumnView(14, 10);
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
			
			s.mergeCells(9, 0, 10, 0);
			s.mergeCells(11, 0, 12, 0);
			s.mergeCells(13, 0, 14, 0);
			s.mergeCells(15, 0, 16, 0);
			s.mergeCells(17, 0, 18, 0);
			s.mergeCells(19, 0, 20, 0);
			

			s.addCell(new Label(0, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(1, 0, "NOMBRE PROYECTO", cellFormat));
			s.addCell(new Label(2, 0, "NUM. CONTROL", cellFormat));
			s.addCell(new Label(3, 0, "EQUIPO", cellFormat));
			s.addCell(new Label(4, 0, "FECHA ALTA COSTES", cellFormat));
			s.addCell(new Label(5, 0, "GESTOR IT", cellFormat));
			s.addCell(new Label(6, 0, "NUM. VALORACION", cellFormat));
			s.addCell(new Label(7, 0, "COMENTARIOS", cellFormat));
			s.addCell(new Label(8, 0, "FECHA SOLICITUD VALORACION", cellFormat));
			s.addCell(new Label(9, 0, "ANALISIS", cellFormat));
			s.addCell(new Label(9, 1, "HORAS", cellFormat));
			s.addCell(new Label(10, 1, "COSTE", cellFormat));
			s.addCell(new Label(11, 0, "DISEÑO", cellFormat));
			s.addCell(new Label(11, 1, "HORAS", cellFormat));
			s.addCell(new Label(12, 1, "COSTE", cellFormat));
			s.addCell(new Label(13, 0, "CONSTRUCCIÓN", cellFormat));
			s.addCell(new Label(13, 1, "HORAS", cellFormat));
			s.addCell(new Label(14, 1, "COSTE", cellFormat));
			s.addCell(new Label(15, 0, "PRUEBAS", cellFormat));
			s.addCell(new Label(15, 1, "HORAS", cellFormat));
			s.addCell(new Label(16, 1, "COSTE", cellFormat));
			s.addCell(new Label(17, 0, "GESTIÓN",cellFormat));
			s.addCell(new Label(17, 1, "HORAS", cellFormat));
			s.addCell(new Label(18, 1, "COSTE", cellFormat));
			s.addCell(new Label(19, 0, "TOTAL", cellFormat));
			s.addCell(new Label(19, 1, "HORAS", cellFormat));
			s.addCell(new Label(20, 1, "COSTE", cellFormat));
			

			UserDao uDao = UserDao.getInstance();
			User u = new User();

			int aux = 2;

			for (Coste c : costes) {
				
				s.addCell(new Label(0, aux, c.getCliente_name()));
				s.addCell(new Label(1, aux, c.getProject_name()));
				s.addCell(new Label(2, aux, c.getNum_control()));
				s.addCell(new Label(3, aux, c.getEquipos()));
				s.addCell(new Label(4, aux, c.getStr_fecha_alta()));
				s.addCell(new Label(5, aux, c.getGestor_it_name()));
				s.addCell(new Label(6, aux, c.getNum_valoracion()));
				s.addCell(new Label(7, aux, c.getComentarios()));
				s.addCell(new Label(8, aux, c.getStr_fecha_solicitud_valoracion()));
				if (!"".equals(c.getHoras_analisis()))
					s.addCell(new Number(9, aux, Double.parseDouble(c.getHoras_analisis()),cellFormatRight));
				if (!"".equals(c.getCoste_analisis()))
					s.addCell(new Number(10, aux, Double.parseDouble(c.getCoste_analisis()),cellFormatRight));
				if (!"".equals(c.getHoras_diseño()))
					s.addCell(new Number(11, aux, Double.parseDouble(c.getHoras_diseño()),cellFormatRight));
				if (!"".equals(c.getCoste_diseño()))
					s.addCell(new Number(12, aux, Double.parseDouble(c.getCoste_diseño()),cellFormatRight));
				if (!"".equals(c.getHoras_construccion()))
					s.addCell(new Number(13, aux, Double.parseDouble(c.getHoras_construccion()),cellFormatRight));
				if (!"".equals(c.getCoste_construccion()))
					s.addCell(new Number(14, aux, Double.parseDouble(c.getCoste_construccion()),cellFormatRight));
				if (!"".equals(c.getHoras_pruebas()))
					s.addCell(new Number(15, aux, Double.parseDouble(c.getHoras_pruebas()),cellFormatRight));
				if (!"".equals(c.getCoste_pruebas()))
					s.addCell(new Number(16, aux, Double.parseDouble(c.getCoste_pruebas()),cellFormatRight));
				if (!"".equals(c.getHoras_gestion()))
					s.addCell(new Number(17, aux, Double.parseDouble(c.getHoras_gestion()),cellFormatRight));
				if (!"".equals(c.getCoste_gestion()))
					s.addCell(new Number(18, aux, Double.parseDouble(c.getCoste_gestion()),cellFormatRight));
				if (!"".equals(c.getHoras_total()))
					s.addCell(new Number(19, aux, Double.parseDouble(c.getHoras_total()),cellFormatRight));
				if (!"".equals(c.getCoste_total()))
					s.addCell(new Number(20, aux, Double.parseDouble(c.getCoste_total()),cellFormatRight));
				
				aux++;
			}

			w.write();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Exception in Excel", e);
		} finally {
			if (out != null)
				out.close();
		}

	}

	private void updateCoste(HttpServletRequest req, HttpServletResponse resp)
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
			String gestion_coste = req.getParameter("gestion_coste");
			String gestion_horas = req.getParameter("gestion_horas");
			String gestor_it = req.getParameter("gestor_it");

			String pruebas_horas = req.getParameter("pruebas_horas");
			String pruebas_costes = req.getParameter("pruebas_coste");
			String total_coste = req.getParameter("total_coste");
			String total_horas = req.getParameter("total_horas");
			String num_valoracion = req.getParameter("num_valoracion");

			CosteDao cDao = CosteDao.getInstance();

			Coste c = cDao.getCostebyId(Long.parseLong(id));

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

	private void createCoste(HttpServletRequest req, HttpServletResponse resp)
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

			CosteDao cDao = CosteDao.getInstance();
			ClienteDao clDao = ClienteDao.getInstance();
			ProyectoDao pDao = ProyectoDao.getInstance();
			Coste c = new Coste();
			Proyecto p = pDao.getProjectbyId(Long.parseLong(project));

			Cliente cliente_obj = clDao.getClienteById(Long.parseLong(cliente));
			
			c.setNum_control(num_control);
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

	private void deleteCoste(HttpServletRequest req, HttpServletResponse resp)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();
		String id = req.getParameter("id");
		CosteDao cDao = CosteDao.getInstance();

		Coste c = cDao.getCostebyId(Long.parseLong(id));

		cDao.deleteCoste(c);

		json.append("success", "true");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}

}
