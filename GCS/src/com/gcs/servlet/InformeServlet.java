package com.gcs.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

import com.gcs.beans.Conectividad;
import com.gcs.beans.Informe;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.InformeDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;



public class InformeServlet extends HttpServlet {
	

	private static final Logger log = Logger.getLogger(InformeServlet.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = new JSONObject();


		log.info("Dentro de servletInforme");
		String accion = req.getParameter("accion");

		
		try {

			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");
			String usermail = (String)sesion.getAttribute("usermail");

			if (sesionpermiso > 2) {
				json.append("failure", "true");
				json.append("error",
						"No tienes los permisos para realizar esta operación");

				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("application/json");
				resp.getWriter().println(json);
			} else {
				
				//cargarDatos(req, resp);if (accion.equals("getMoths"))
				if (accion.equals("getMonths"))obtenerMeses(req, resp);
				if (accion.equals("getDays"))obtenerDias(req, resp);
				if (accion.equals("getInforme"))obtenerInforme(req, resp);
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void obtenerMeses(HttpServletRequest req, HttpServletResponse resp)throws JSONException, IOException {		
		JSONObject json = new JSONObject();
		try{
		
		
		String anio = req.getParameter("year");
		InformeDao iDao = InformeDao.getInstance();
		List<String> Meses = iDao.getMonthsForInforme("All", anio);
		
		
		json.append("Meses", Meses);
		json.append("success", "true");
		} catch (JSONException e) {
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
			e.printStackTrace();
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	private void obtenerDias(HttpServletRequest req, HttpServletResponse resp)throws JSONException, IOException {		
		JSONObject json = new JSONObject();
		try{
		
		
		String anio = req.getParameter("year");
		String mes = req.getParameter("month");
		InformeDao iDao = InformeDao.getInstance();
		List<String> Dias = iDao.getDaysForInforme("All", anio,mes);
		
		
		json.append("Dias", Dias);
		json.append("success", "true");
		} catch (JSONException e) {
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
			e.printStackTrace();
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	private void obtenerInforme(HttpServletRequest req, HttpServletResponse resp)throws JSONException, IOException {		
		JSONObject json = new JSONObject();

			
		resp.setContentType("application/pdf");
		
		String anio = req.getParameter("year");
		String mes = req.getParameter("month");
		String dia = req.getParameter("day");
		String calenda = req.getParameter("calendada");
		InformeDao iDao = InformeDao.getInstance();
		List<Informe> Informes = iDao.getAllInformesByDate(calenda,anio, mes, dia);
		
		//TODO generar pdf y guardarlo
		
		try {
		   Document document = new Document();
		   PdfWriter.getInstance(document, resp.getOutputStream());
		   document.open();
		   document.add(new Paragraph("This is a paragraph"));
		   document.close();
		  } catch (DocumentException e) {
		   e.printStackTrace();
		  }

	}

}