package com.gcs.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
						deleteDemanda(req,resp);
					}else if (accion.equals("update")){
						//updateDemanda(req,resp);
					}else if (accion.equals("xls")){
						generateXLS(req,resp);
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
	
	private void deleteDemanda(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException {
		JSONObject json = new JSONObject();
		
		DemandaDao dDao = DemandaDao.getInstance();
		
		try{
			Demanda d = dDao.getDemandaById(Long.parseLong(req.getParameter("id")));
			
			dDao.deleteDemanda(d);
			json.append("success", "true");
		}catch(Exception e){
			json.append("failure", "true");
		}
		
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
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
		d.setFecha_entrada_peticion(dateConverter(fecha_entrada_peticion));		
		d.setFecha_solicitud_asignacion(dateConverter(fecha_solicitud_asignacion));
		d.setStr_fecha_entrada_peticion(fecha_entrada_peticion);
		d.setStr_fecha_solicitud_asignacion(fecha_solicitud_asignacion);
		d.setGestor_it(Long.parseLong(gestor_it));
		d.setGestor_negocio(Long.parseLong(gestor_negocio));
		d.setHora_entrada_peticion(hora_peticion+":"+min_peticion);
		d.setHora_solicitud_asignacion(hora_solicitud_asignacion+":"+min_solicitud_asignacion);
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
	
	public Date dateConverter(String cadena) throws ParseException{
		DateFormat formatter = null;
		formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date convertedDate = (Date) formatter.parse(cadena);
        
        return convertedDate;
	}
	
	public void generateXLS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=DemandasGestionGCS.xls");
			
			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream());
			
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
		    s.setRowView(0, 900);
						
			s.addCell(new Label(0, 0, "COD_PETICION",cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE",cellFormat));
			s.addCell(new Label(2, 0, "TIPO",cellFormat));
			s.addCell(new Label(3, 0, "ESTADO",cellFormat));
			s.addCell(new Label(4, 0, "FECHA ENTRADA",cellFormat));
			s.addCell(new Label(5, 0, "HORA ENTRADA",cellFormat));
			s.addCell(new Label(6, 0, "MOTIVO DE CATALOGACION",cellFormat));
			s.addCell(new Label(7, 0, "COMENTARIOS",cellFormat));
			
			s.addCell(new Label(8, 0, "GESTOR DE NEGOCIO",cellFormat));
			
			s.addCell(new Label(9, 0, "FECHA DE SOLICITUD",cellFormat));
			s.addCell(new Label(10, 0, "HORA DE SOLICITUD",cellFormat));
			s.addCell(new Label(11, 0, "DEVUELTA",cellFormat));
			s.addCell(new Label(12, 0, "GESTOR IT",cellFormat));
			s.addCell(new Label(13, 0, "CATALOGACION DE LA PETICION",cellFormat));
			
			UserDao uDao = UserDao.getInstance();
			User u = new User();
			
			
			int aux=1;
			
			for (Demanda d:demandas){
				s.addCell(new Label(0, aux, d.getCod_peticion()));
				s.addCell(new Label(1, aux, d.getClientekey().toString()));
				s.addCell(new Label(2, aux, d.getTipo()));
				s.addCell(new Label(3, aux, d.getEstado()));
				s.addCell(new Label(4, aux, d.getStr_fecha_entrada_peticion()));
				s.addCell(new Label(5, aux, d.getHora_entrada_peticion()));
				s.addCell(new Label(6, aux, d.getMotivo_catalogacion()));
				s.addCell(new Label(7, aux, d.getComentarios()));
				
				u = uDao.getUserbyId(d.getGestor_negocio());
				s.addCell(new Label(8, aux, u.getNombre() + " " + u.getApellido1() + " " + u.getApellido2()));
				
				s.addCell(new Label(9, aux, d.getStr_fecha_solicitud_asignacion()));
				s.addCell(new Label(10, aux, d.getHora_solicitud_asignacion()));
				s.addCell(new Label(11, aux, d.getDevuelta().toString()));
				
				u = uDao.getUserbyId(d.getGestor_it());
				s.addCell(new Label(12, aux, u.getNombre() + " " + u.getApellido1() + " " + u.getApellido2()));
				
				s.addCell(new Label(13, aux, d.getCatalogacion()));
				 
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
}
