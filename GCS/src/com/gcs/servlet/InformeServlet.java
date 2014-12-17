package com.gcs.servlet;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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
import javax.servlet.ServletOutputStream;
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
import com.gcs.beans.Conectividad;
import com.gcs.beans.Informe;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.InformeDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.FontFactory;




public class InformeServlet extends HttpServlet {
	

	private static final Logger log = Logger.getLogger(InformeServlet.class.getName());
	private String[] fech = new String[5];
	
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
						"No tienes los permisos para realizar esta operaci�n");

				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("application/json");
				resp.getWriter().println(json);
			} else {
				
				//cargarDatos(req, resp);if (accion.equals("getMoths"))
				if (accion.equals("getMonths"))obtenerMeses(req, resp);
				if (accion.equals("getDays"))obtenerDias(req, resp);
				if (accion.equals("getInforme"))obtenerInforme(req, resp);
				if (accion.equals("getYears"))obtenerAnios(req, resp);
				if (accion.equals("getDefault"))obtenerDefault(req, resp);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	private void obtenerDefault(HttpServletRequest req, HttpServletResponse resp)throws JSONException, IOException {		
		JSONObject json = new JSONObject();
		try{
		
		
		
		
		json.append("Anio",fech[0]);
		json.append("Mes", fech[1]);
		json.append("Dia", fech[2]);
		json.append("Calendada", fech[3]);
		} catch (JSONException e) {
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
			e.printStackTrace();
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	private void obtenerAnios(HttpServletRequest req, HttpServletResponse resp)throws JSONException, IOException {		
		JSONObject json = new JSONObject();
		try{
		
		
		String calendada = req.getParameter("calendada");
		InformeDao iDao = InformeDao.getInstance();
		List<String> Anios = iDao.getYearsForInforme(calendada);
		
		
		json.append("Anios", Anios);
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
		String calendada = req.getParameter("calendada");
		InformeDao iDao = InformeDao.getInstance();
		List<String> Dias = iDao.getDaysForInforme(calendada, anio,mes);
		
		
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

			
		
		String anio = req.getParameter("year");
		String mes = req.getParameter("month");
		String dia = req.getParameter("day");
		String calenda = req.getParameter("calendada");
		fech[0]=anio;
		fech[1]=mes;
		fech[2]=dia;
		fech[3]=calenda;
		
		InformeDao iDao = InformeDao.getInstance();
		List<Informe> Informes = iDao.getAllInformesByDate(calenda,anio, mes, dia);
		Informe inf =Informes.get(0);
		
		
		String name;
		String pais;
		String producto;
		String detalle;
		String estado;
		String comentario;
		
		
		List<String>conectividades = inf.getConectividadesId();
		List<String>servicios = inf.getServiciosId();
		
		for (String a : servicios ){
			ServicioDao cDao = ServicioDao.getInstance();
			Servicio serv = cDao.getServicioById(a);
			name = serv.getCliente_name();
			estado = serv.getEstadoSubida();
			comentario = serv.getdetalleSubida();
			
			long ident = serv.getCliente_key();
			
			ClienteDao cliDao = ClienteDao.getInstance();
			Cliente cliente = cliDao.getClienteById(ident);

		}	
		
		for (String a : conectividades ){
			ConectividadDao cDao = ConectividadDao.getInstance();
			Conectividad serv = cDao.getConectividadById(a);



		}
			
			

		if(!Informes.isEmpty()){
		
		try {

		   
		   Document document = new Document();
		   PdfWriter.getInstance(document, resp.getOutputStream());
		   document.open();
		   
		   Image bbva = Image.getInstance("img/logo_bbva.png");
		   bbva.scaleToFit(78, 45);
		   bbva.setAlignment(Chunk.ALIGN_LEFT);
		   document.add(bbva);
		   Paragraph header =new Paragraph("Solicitudes desplegadas en producci�n",FontFactory.getFont("arial",22,Font.BOLD,BaseColor.BLACK));
		   header.setAlignment(Chunk.ALIGN_CENTER);
		   document.add(header); 
		   
		   document.add(new Paragraph("Sudida confimada y ejecutada solicidata por el equipo de Global Customer Services:"));
		   
		   document.close();
		   

		  } catch (DocumentException e) {
		   e.printStackTrace();
		  }
		}

	}

}