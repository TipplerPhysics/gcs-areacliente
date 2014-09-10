package com.gcs.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
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

import com.gcs.beans.Demanda;
import com.gcs.beans.User;
import com.gcs.dao.DemandaDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class DemandaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = new JSONObject();

		String accion = req.getParameter("accion");

		try {

			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");

			if (sesionpermiso > 2) {
				json.append("failure", "true");
				json.append("error",
						"No tienes los permisos para realizar esta operaci�n");

				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("application/json");
				resp.getWriter().println(json);
			} else {
				if (accion.equals("new")) {
					createDemanda(req, resp);
				} else if (accion.equals("delete")) {
					deleteDemanda(req, resp);
				} else if (accion.equals("update")) {
					updateDemanda(req, resp);
				} else if (accion.equals("xls")) {
					generateXLS(req, resp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		doGet(req, resp);
	}

	private void deleteDemanda(HttpServletRequest req, HttpServletResponse resp)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();

		DemandaDao dDao = DemandaDao.getInstance();

		try {
			Demanda d = dDao.getDemandaById(Long.parseLong(req
					.getParameter("id")));

			dDao.deleteDemanda(d);
			json.append("success", "true");
		} catch (Exception e) {
			json.append("failure", "true");
		}

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}

	private void updateDemanda(HttpServletRequest req, HttpServletResponse resp)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();

		try {

			String idStr = req.getParameter("id");
			Long id = Long.parseLong(idStr);

			DemandaDao dDao = DemandaDao.getInstance();
			Demanda d = dDao.getDemandaById(id);

			String catalogacion_peticion = req.getParameter("catalogacion_peticion");
			String comentarios = req.getParameter("comentarios");
			String devuelta = req.getParameter("devuelta");
			String fecha_comunicacion_asignacion = req.getParameter("fecha_comunicacion_asignacion");
			String fecha_entrada_peticion = req.getParameter("fecha_entrada_peticion");
			String fecha_solicitud_asignacion = req.getParameter("fecha_solicitud_asignacion");
			String gestor_negocio = req.getParameter("gestor_negocio");
			String hora_comunicacion_asignacion = req.getParameter("hora_comunicacion_asignacion");
			String min_comunicacion_asignacion = req.getParameter("min_comunicacion_asignacion");
			String hora_peticion = req.getParameter("hora_peticion");
			String min_peticion = req.getParameter("min_peticion");
			String hora_solicitud_asignacion = req.getParameter("hora_solicitud_asignacion");
			String min_solicitud_asignacion = req.getParameter("min_solicitud_asignacion");
			String motivo_catalogacion = req.getParameter("motivo_catalogacion");
			
			
			String cliente = req.getParameter("cliente");
			String tipo = req.getParameter("tipo");
			String estado = req.getParameter("estado");
			String gestor_it = req.getParameter("gestor_it");
			
			d.setMotivo_catalogacion(motivo_catalogacion);
			d.setCatalogacion(catalogacion_peticion);
			d.setComentarios(comentarios);
			if ("SI".equals(devuelta))
				d.setDevuelta(true);
			else
				d.setDevuelta(false);
			d.setClientekey(Long.parseLong(cliente));
			d.setTipo(tipo);
			d.setEstado(estado);
			d.setGestor_it(Long.parseLong(gestor_it));
			d.setGestor_negocio(Long.parseLong(gestor_negocio));
			
			
			
			if (!"".equals(fecha_entrada_peticion)){
				d.setFecha_entrada_peticion(Utils.dateConverter(fecha_entrada_peticion));
				d.setStr_fecha_entrada_peticion(fecha_entrada_peticion);
			}
			
			d.setHora_entrada_peticion(hora_peticion + ":" + min_peticion);
			d.setHora_comunicacion_asignacion(hora_comunicacion_asignacion + ":" + min_comunicacion_asignacion);
			d.setHora_solicitud_asignacion(hora_solicitud_asignacion + ":" + min_solicitud_asignacion);
			
			if (!"".equals(fecha_solicitud_asignacion)){
				d.setFecha_solicitud_asignacion(Utils.dateConverter(fecha_solicitud_asignacion));
				d.setStr_fecha_solicitud_asignacion(fecha_solicitud_asignacion);
			}
			
			
			
			if (!"".equals(fecha_comunicacion_asignacion)){
				d.setFecha_comunicacion(Utils.dateConverter(fecha_comunicacion_asignacion));
				d.setStr_fecha_comunicacion(fecha_comunicacion_asignacion);
			}			

			dDao.createDemanda(d);

			json.append("success", "true");
			json.append("id", d.getKey().getId());
			json.append("cod_peticion", d.getCod_peticion());

		} catch (Exception e) {
			json.append("failure", "true");
			json.append("error", "Se ha producido un error inesperado");

		}

	}

	private void createDemanda(HttpServletRequest req, HttpServletResponse resp)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();
		
		Date fecha_comunicacion = new Date();
		
		String hora_hora = String.valueOf(fecha_comunicacion.getHours());
		if (hora_hora.length()==1)
			hora_hora = "0" + hora_hora;
		
		String min_min = String.valueOf(fecha_comunicacion.getMinutes());
		if (min_min.length()==1)
			min_min = "0" + min_min;
		
		String hora_comunicacion = hora_hora +":"+ min_min;

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
			
			String hora_comunicacion_asignacion = req.getParameter("hora_comunicacion_asignacion");
			
			String min_comunicacion_asignacion = req.getParameter("min_comunicacion_asignacion");
			
			String fecha_comunicacion_asignacion = req.getParameter("fecha_comunicacion_asignacion");
			
			
			

			DemandaDao dDao = DemandaDao.getInstance();

			
			String dia_com = String.valueOf(fecha_comunicacion.getDate());
			if (dia_com.length()==1)
				dia_com = "0"+dia_com;
			String mes_com = String.valueOf(fecha_comunicacion.getMonth()+1);
			if (mes_com.length()==1)
				mes_com = "0"+mes_com;
			String anio = String.valueOf(fecha_comunicacion.getYear()+1900);
			
			
			d.setCatalogacion(catalogacion_peticion);
			d.setComentarios(comentarios);
			d.setDevuelta(devBool);
			d.setEstado(estado);
			d.setFecha_entrada_peticion(Utils
					.dateConverter(fecha_entrada_peticion));
			if (!"".equals(fecha_solicitud_asignacion)) {
				d.setFecha_solicitud_asignacion(Utils
						.dateConverter(fecha_solicitud_asignacion));
			}
			d.setStr_fecha_entrada_peticion(fecha_entrada_peticion);
			d.setStr_fecha_solicitud_asignacion(fecha_solicitud_asignacion);
			if (!"default".equals(gestor_it))
				d.setGestor_it(Long.parseLong(gestor_it));
			if (isLong(gestor_negocio)) {
				d.setGestor_negocio(Long.parseLong(gestor_negocio));
			}
			d.setHora_comunicacion_asignacion(hora_comunicacion_asignacion+":"+ min_comunicacion_asignacion);
			
			if (!"".equals(fecha_comunicacion_asignacion)){
				d.setFecha_comunicacion_asignacion(Utils
						.dateConverter(fecha_comunicacion_asignacion));
			}
			d.setStr_fecha_comunicacion_asignacion(fecha_comunicacion_asignacion);
			d.setHora_entrada_peticion(hora_peticion + ":" + min_peticion);
			if (!hora_solicitud_asignacion.equals("default") && !min_solicitud_asignacion.equals("default"))
				d.setHora_solicitud_asignacion(hora_solicitud_asignacion + ":"	+ min_solicitud_asignacion);
			d.setMotivo_catalogacion(motivo_catalogacion);
			d.setTipo(tipo);
			d.setMotivo_catalogacion(motivo_catalogacion);
			d.setComentarios(comentarios);
			if (cliente.equals("default")) {
				d.setClientekey(Long.parseLong("1"));
			} else {
				d.setClientekey(Long.parseLong(cliente));
			}

			dDao.createDemandaAndIncreaseCount(d);

			json.append("success", "true");
			json.append("id", d.getKey().getId());
			json.append("cod_peticion", d.getCod_peticion());

		} catch (ParseException e) {
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
			e.printStackTrace();
		} catch (Exception e) {
			json.append("success", "true");
			json.append("id", d.getKey().getId());
			json.append("cod_peticion", d.getCod_peticion());
		}

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}

	public void generateXLS(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=DemandasGestionGCS.xls");

			WritableWorkbook w = Workbook
					.createWorkbook(resp.getOutputStream());

			DemandaDao dDao = DemandaDao.getInstance();
			List<Demanda> demandas = dDao.getAllDemandas();

			WritableSheet s = w.createSheet("Demandas de gestion", 0);

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.WHITE);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.BLUE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

			s.setColumnView(0, 16);
			s.setColumnView(1, 30);
			s.setColumnView(2, 10);
			s.setColumnView(3, 50);
			s.setColumnView(4, 30);
			s.setColumnView(5, 30);
			s.setColumnView(6, 70);
			s.setColumnView(7, 70);
			s.setColumnView(8, 30);
			s.setColumnView(9, 25);
			s.setColumnView(10, 20);
			s.setColumnView(11, 15);
			s.setColumnView(12, 30);
			s.setColumnView(13, 30);
			s.setColumnView(14, 30);
			s.setColumnView(15, 30);
			s.setRowView(0, 900);

			s.addCell(new Label(0, 0, "COD_PETICION", cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(2, 0, "TIPO", cellFormat));
			s.addCell(new Label(3, 0, "ESTADO", cellFormat));
			s.addCell(new Label(4, 0, "FECHA ENTRADA", cellFormat));
			s.addCell(new Label(5, 0, "HORA ENTRADA", cellFormat));
			s.addCell(new Label(6, 0, "MOTIVO DE CATALOGACION", cellFormat));
			s.addCell(new Label(7, 0, "COMENTARIOS", cellFormat));
			s.addCell(new Label(8, 0, "GESTOR DE NEGOCIO", cellFormat));
			s.addCell(new Label(9, 0, "FECHA DE SOLICITUD", cellFormat));
			s.addCell(new Label(10, 0, "HORA DE SOLICITUD", cellFormat));
			s.addCell(new Label(11, 0, "DEVUELTA", cellFormat));
			s.addCell(new Label(12, 0, "GESTOR IT", cellFormat));
			s.addCell(new Label(13, 0, "CATALOGACION DE LA PETICION",cellFormat));
			s.addCell(new Label(14, 0, "FECHA COMUNICACION", cellFormat));
			s.addCell(new Label(15, 0, "HORA COMUNICACION",cellFormat));

			UserDao uDao = UserDao.getInstance();
			User u = new User();

			int aux = 1;

			for (Demanda d : demandas) {
				
				s.addCell(new Label(0, aux, d.getCod_peticion()));
				s.addCell(new Label(1, aux, d.getClienteName()));
				s.addCell(new Label(2, aux, d.getTipo()));
				s.addCell(new Label(3, aux, d.getEstado()));
				s.addCell(new Label(4, aux, d.getStr_fecha_entrada_peticion()));
				s.addCell(new Label(5, aux, d.getHora_entrada_peticion()));
				s.addCell(new Label(6, aux, d.getMotivo_catalogacion()));
				s.addCell(new Label(7, aux, d.getComentarios()));

				if (d.getGestor_negocio()!=null){
					u = uDao.getUserbyId(d.getGestor_negocio());
					if (u!=null){
						s.addCell(new Label(8, aux, u.getNombre() + " "
								+ u.getApellido1() + " " + u.getApellido2()));
					}
					
				}
				

				s.addCell(new Label(9, aux, d
						.getStr_fecha_solicitud_asignacion()));
				s.addCell(new Label(10, aux, d.getHora_solicitud_asignacion()));
				if (d.getDevuelta().toString().equals(true))
					s.addCell(new Label(11, aux, "Si"));
				else
					s.addCell(new Label(11, aux, "No"));

				u = uDao.getUserbyId(d.getGestor_it());
				if (u!=null){
					s.addCell(new Label(12, aux, u.getNombre() + " "
							+ u.getApellido1() + " " + u.getApellido2()));
				}
				

				s.addCell(new Label(13, aux, d.getCatalogacion()));
				s.addCell(new Label(14,aux,d.getStr_fecha_comunicacion()));
				s.addCell(new Label(15,aux,d.getHora_comunicacion()));

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

	private boolean isLong(String input) {
		try {
			Long.parseLong(input);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
