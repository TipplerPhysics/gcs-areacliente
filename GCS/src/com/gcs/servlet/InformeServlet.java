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
import java.util.Set;
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
				if (accion.equals("getInforme"))obtenerInforme(req, resp,false);
				if (accion.equals("getInformeDown"))obtenerInforme(req, resp,true);
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
		
		
		if(ImplantacionServlet.fech[0]!=null){
			json.append("Anio",ImplantacionServlet.fech[0]);
			json.append("Mes", ImplantacionServlet.fech[1]);
			json.append("Dia", ImplantacionServlet.fech[2]);
			json.append("Calendada", ImplantacionServlet.fech[3]);
		}else{
			json.append("success", "false");
			json.append("error", "todavia no se ha generado un informe");
		}
		
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
	
	private void obtenerInforme(HttpServletRequest req, HttpServletResponse resp,boolean Descarg)throws JSONException, IOException {		
		JSONObject json = new JSONObject();
		String anio = req.getParameter("year");
		String mes = req.getParameter("month");
		String dia = req.getParameter("day");
		String calenda = req.getParameter("calendada");
		
		resp.setContentType("application/pdf");
		if(Descarg){
			resp.addHeader("Content-Disposition", "attachment; filename=informe"+anio+"/"+mes+"/"+dia+".pdf");
		}else{
			resp.addHeader("Content-Disposition", "inline; filename=informe"+anio+"/"+mes+"/"+dia+".pdf");
		}
		resp.setHeader("Cache-Control", "no-cache");  
		resp.setDateHeader("Expires", 0);  
		resp.setHeader("Pragma", "No-cache");   


		
		InformeDao iDao = InformeDao.getInstance();
		List<Informe> Informes = iDao.getAllInformesByDate(calenda,anio, mes, dia);
		Informe inf =Informes.get(0);
		
		
		List<String> name = new ArrayList<String>();
		List<String> estado = new ArrayList<String>();
		List<String> detalle = new ArrayList<String>();
		List<String> comentSub = new ArrayList<String>();
		List<String> nameCli = new ArrayList<String>();
		List<String> pais = new ArrayList<String>();
		List<String> producto = new ArrayList<String>();
		
		
		List<String>conectividades = inf.getConectividadesId();
		List<String>servicios = inf.getServiciosId();
		if(!servicios.isEmpty()){
			for (String a : servicios ){
				ServicioDao cDao = ServicioDao.getInstance();
				Servicio serv = cDao.getServicioById(a);
				
				nameCli.add(serv.getCliente_name());
				estado.add(serv.getEstadoSubida());
				detalle.add(serv.getObservaciones());
				comentSub.add(serv.getdetalleSubida());
				
				long ident = serv.getId_proyecto();
				ProyectoDao projDao = ProyectoDao.getInstance();
				Proyecto proj = projDao.getProjectbyId(ident);
				
				producto.add(proj.getProducto());
				name.add(proj.getServicio());
				
				long identCli = serv.getCliente_key();
				ClienteDao cliDao = ClienteDao.getInstance();
				Cliente cliente = cliDao.getClienteById(identCli);
				
				Set<String> Paises = cliente.getPaises();
				
				if(Paises.size()>1){
					pais.add("Varios Paises");
				}else{
					pais.add(Paises.toString());
				}
			}	
		}
		
		if(!conectividades.isEmpty()){
			for (String a : conectividades ){
				ConectividadDao cDao = ConectividadDao.getInstance();
				Conectividad conect = cDao.getConectividadById(a);
				
				comentSub.add(conect.getdetalleSubida());
				estado.add(conect.getEstadoSubida());			
				detalle.add("");
				
				long ident = conect.getKey_proyecto();
				ProyectoDao projDao = ProyectoDao.getInstance();
				Proyecto proj = projDao.getProjectbyId(ident);
	
				producto.add(proj.getProducto());
				name.add(proj.getConectividad());
				
				long identCli = proj.getClienteKey();
				ClienteDao cliDao = ClienteDao.getInstance();
				Cliente cliente = cliDao.getClienteById(identCli);
				
				nameCli.add(cliente.getNombre());
				Set<String> Paises = cliente.getPaises();
				
				if(Paises.size()>1){
					pais.add("Varios Paises");
				}else{
					pais.add(Paises.toString());
				}
	
			}
		}
		
		try {

		   
		   Document document = new Document();
		   PdfWriter.getInstance(document, resp.getOutputStream());
		   document.open();
		   
		   Image bbva = Image.getInstance("img/logo_bbva.png");
		   bbva.scaleToFit(78, 45);
		   bbva.setAlignment(Chunk.ALIGN_LEFT);
		   document.add(bbva);
		   Paragraph header =new Paragraph("Solicitudes desplegadas en producción",FontFactory.getFont("arial",22,Font.BOLD,BaseColor.BLACK));
		   header.setAlignment(Chunk.ALIGN_CENTER);
		   document.add(header); 
		   
		   document.add(new Paragraph("Sudida confimada y ejecutada solicidata por el equipo de Global Customer Services:"));
		   document.add(new Paragraph(" "));
		   
		   for(int i=0;i<estado.size();i++){
			   document.add(new Paragraph(" "));
			   document.add(new Paragraph("*  "+nameCli.get(i)+","+pais.get(i)+","+producto.get(i)));
			   document.add(new Paragraph(" "));   
			   document.add(new Paragraph(detalle.get(i)));
			   document.add(new Paragraph(" "));
			   document.add(new Paragraph("Subida "+estado.get(i)));
		   }
		   

		   
		   document.close();
		  } catch (DocumentException e) {
		   e.printStackTrace();
		  }


	}

}