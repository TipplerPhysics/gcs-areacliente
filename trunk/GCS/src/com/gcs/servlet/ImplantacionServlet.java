package com.gcs.servlet;

import java.io.IOException;
import java.io.OutputStream;
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

public class ImplantacionServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(ImplantacionServlet.class.getName());

	private static final long serialVersionUID = 1L;
	
	private static final String VACIO = "";
	private static final String CALENDADA = "Calendada";
	private static final String OK = "OK";
	private static final String KO = "KO";
	private static final String SOLICITADO = "Solicitado";
	private static final String CONFIRMADO = "Confirmado";
	private static final String PRODUCCION = "Produccion";
	private static final String PENNY_TEST = "En Penny Test";
	private static final String PENDIENTE_IMPL = "PDTE Implantar";
		

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = new JSONObject();

		log.info("Dentro de servletImplantacion");
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
				if (SOLICITADO.equals(accion)) {
					generarSolicitud(req, resp, usermail);					
				} else if (CONFIRMADO.equals(accion)) {
					generarConfirmacion(req, resp, usermail);
				} else if (PRODUCCION.equals(accion)) {
					generarProduccion(req, resp, usermail);
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

	private void generarSolicitud(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();

		try {
			
			String tipoSubida = req.getParameter("tipo_subida");
			String fechaImplantacion = req.getParameter("fecha_implantacion");
			String serviciosParam = req.getParameter("servicios");
			String conectividadesParam = req.getParameter("conectividades");
			
			//Proceso parametros de codigos de conectividades y de servicios
			List<String> conectividadesList = new ArrayList<>();
			if(!VACIO.equals(conectividadesParam)) {
				String[] conectividadesArray = conectividadesParam.split(",");
				conectividadesList = Arrays.asList(conectividadesArray);
			}
			
			List<String> serviciosList = new ArrayList<>();
			if(!VACIO.equals(serviciosParam)) {
				String[] serviciosArray = serviciosParam.split(",");
				serviciosList = Arrays.asList(serviciosArray);
			}
			
			ServicioDao sDao = ServicioDao.getInstance();
			ConectividadDao cDao = ConectividadDao.getInstance();
			
			// Actualiza el estado de las Conectividades
			if(conectividadesList.size() > 0) {				
				for(String c : conectividadesList) {
					Conectividad cObj = cDao.getConectividadById(c);
					if(cObj != null) {
						cObj.setEstadoImplantacion(SOLICITADO);
						cObj.setEstadoSubida(OK);
						cObj.setdetalleSubida("");
						cObj.setFecha_implantacion(Utils.dateConverter(fechaImplantacion));
						cObj.setStr_fecha_implantacion(fechaImplantacion);
						if(CALENDADA.equals(tipoSubida)) {
							cObj.setsubidaCalendada(true);
						}
						else {
							cObj.setsubidaCalendada(false);
						}
						cDao.createConectividad(cObj, usermail);
					}
				}
			}
			
			// Actualiza el estado de las Servicios
			if(serviciosList.size() > 0) {							
				for(String s : serviciosList) {
					Servicio sObj = sDao.getServicioById(s);
					if(sObj != null) {
						sObj.setEstadoImplantacion(SOLICITADO);
						sObj.setEstadoSubida(OK);
						sObj.setdetalleSubida("");
						sObj.setFecha_implantacion_produccion(Utils.dateConverter(fechaImplantacion));
						sObj.setStr_fecha_implantacion_produccion(fechaImplantacion);
						if(CALENDADA.equals(tipoSubida)) {
							sObj.setsubidaCalendada(true);
						}
						else {
							sObj.setsubidaCalendada(false);
						}
						sDao.createServicio(sObj, usermail);
					}
				}
			}
			
			// send email solicitud
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	        ProyectoDao pDao = ProyectoDao.getInstance();
	        Proyecto p = null;
	        String msgBody = "";
	        msgBody +="<ul>";
	        
	        if(serviciosList.size() > 0) {		
		        msgBody +="<br/>Servicios solicitados:<br/>";
		        for(String s : serviciosList){
	        		Servicio sObj = sDao.getServicioById(s);
	        		p = pDao.getProjectbyId(sObj.getId_proyecto());
	        		msgBody +="<li>";
	        		msgBody +=p.getClienteName()+", "+sObj.getPais()+", "+sObj.getServicio();
	        		msgBody +="</li>";
	        		msgBody +="<br/>"+sObj.getdetalleSubida();	        		
	        	}
	        }
	        
	        if(conectividadesList.size() > 0) {	
	        	msgBody +="<br/>Conectividades solicitadas:<br/>";
	        	for(String c : conectividadesList){
	        		Conectividad cObj = cDao.getConectividadById(c);
	        		p = pDao.getProjectbyId(cObj.getKey_proyecto());
	        		msgBody +="<li>";
	        		msgBody +=p.getClienteName()+", "+p.getConectividad();
	        		msgBody +="</li>";
	        		msgBody +="<br/>"+cObj.getdetalleSubida();	        		
	        	}
	        }
	        
	        msgBody +="</ul>";
	        
	        try {
	            //Message msg = new MimeMessage(session);
	            MimeMessage msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("david.martin.beltran.contractor@bbva.com", "Example.com Admin"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress("david.martin.beltran.contractor@bbva.com", "Mr. User"));
	            msg.setSubject("Implantaciones solicitadas");
	            //msg.setText(msgBody);
	            msg.setContent(msgBody, "text/html; charset=utf-8");
	            Transport.send(msg);

	        } 
	        catch (AddressException e) {
	        	e.printStackTrace();
	        } 
								
			json.append("success", "true");
		}
		catch (Exception e) {
			json.append("failure", "true");
		}

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	private void generarConfirmacion(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();

		try {
			String serviciosParam = req.getParameter("servicios");
			String conectividadesParam = req.getParameter("conectividades");
			
			//Proceso parametros de codigos de conectividades y de servicios
			List<String> conectividadesList = new ArrayList<>();
			if(!VACIO.equals(conectividadesParam)) {
				String[] conectividadesArray = conectividadesParam.split(",");
				conectividadesList = Arrays.asList(conectividadesArray);
			}
			
			List<String> serviciosList = new ArrayList<>();
			if(!VACIO.equals(serviciosParam)) {
				String[] serviciosArray = serviciosParam.split(",");
				serviciosList = Arrays.asList(serviciosArray);
			}
			
			ConectividadDao cDao = ConectividadDao.getInstance();	
			ServicioDao sDao = ServicioDao.getInstance();			
			
			// Actualiza el estado de las Conectividades
			if(conectividadesList.size() > 0) {
				for(String c : conectividadesList) {
					Conectividad cObj = cDao.getConectividadById(c);
					if(cObj != null) {
						cObj.setEstado(PENNY_TEST);
						cObj.setEstadoImplantacion(CONFIRMADO);
						cDao.createConectividad(cObj, usermail);
					}
				}
			}
			
			// Actualiza el estado de las Servicios
			if(serviciosList.size() > 0) {
				for(String s : serviciosList) {
					Servicio sObj = sDao.getServicioById(s);
					if(sObj != null) {
						sObj.setEstado("En Penny Test");
						sObj.setEstadoImplantacion(CONFIRMADO);
						sDao.createServicio(sObj, usermail);
					}
				}
			}
			
			// send email confirmacion
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	        ProyectoDao pDao = ProyectoDao.getInstance();
	        Proyecto p = null;
	        String msgBody = "";
	        msgBody +="<ul>";
	        
	        if(serviciosList.size() > 0) {
	        	msgBody +="<br/>Servicios confirmados:<br/>";
		        for(String s : serviciosList){
	        		Servicio sObj = sDao.getServicioById(s);
	        		p = pDao.getProjectbyId(sObj.getId_proyecto());
	        		msgBody +="<li>";
	        		msgBody +=p.getClienteName()+", "+sObj.getPais()+", "+sObj.getServicio();
	        		msgBody +="</li>";
	        		msgBody +="<br/>"+sObj.getdetalleSubida();	        		
	        	}
	        }
	        if(conectividadesList.size() > 0) {
	        	msgBody +="Conectividades confirmadas:<br/>";
	        	for(String c : conectividadesList){
	        		Conectividad cObj = cDao.getConectividadById(c);
	        		p = pDao.getProjectbyId(cObj.getKey_proyecto());
	        		msgBody +="<li>";
	        		msgBody +=p.getClienteName()+", "+p.getConectividad();
	        		msgBody +="</li>";
	        		msgBody +="<br/>"+cObj.getdetalleSubida();	        		
	        	}
	        }
	        	
	        msgBody +="</ul>";
	        
	        try {
	            //Message msg = new MimeMessage(session);
	            MimeMessage msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("david.martin.beltran.contractor@bbva.com", "Example.com Admin"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress("david.martin.beltran.contractor@bbva.com", "Mr. User"));
	            msg.setSubject("Implantaciones confirmadas");
	            //msg.setText(msgBody);
	            msg.setContent(msgBody, "text/html; charset=utf-8");
	            Transport.send(msg);

	        } catch (AddressException e) {
	           e.printStackTrace();
	        } 
			
			json.append("success", "true");
		}
		catch (Exception e) {
			json.append("failure", "true");
		}

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	private void generarProduccion(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();

		try {
			String serviciosParam = req.getParameter("servicios");
			String conectividadesParam = req.getParameter("conectividades");
			
			//Proceso parametros de codigos de conectividades y de servicios
			List<String> conectividadesList = new ArrayList<>();
			if(!VACIO.equals(conectividadesParam)) {
				String[] conectividadesArray = conectividadesParam.split(",");
				conectividadesList = Arrays.asList(conectividadesArray);
			}
			
			List<String> serviciosList = new ArrayList<>();
			if(!VACIO.equals(serviciosParam)) {
				String[] serviciosArray = serviciosParam.split(",");
				serviciosList = Arrays.asList(serviciosArray);
			}
			
			// Actualiza el estado de las Conectividades
			if(conectividadesList.size() > 0) {
				ConectividadDao cDao = ConectividadDao.getInstance();			
				for(String c : conectividadesList) {
					Conectividad cObj = cDao.getConectividadById(c);
					if(cObj != null) {
						if(KO.equals(cObj.getEstadoSubida())) {
							cObj.setEstado(PENDIENTE_IMPL);
							cObj.setEstadoImplantacion(null);
							cObj.setEstadoSubida(null);
						}
						else {
							cObj.setEstadoImplantacion(PRODUCCION);
						}
						
						cDao.createConectividad(cObj, usermail);
					}
				}
			}
			
			// Actualiza el estado de las Servicios
			if(serviciosList.size() > 0) {
				ServicioDao sDao = ServicioDao.getInstance();
				for(String s : serviciosList) {
					Servicio sObj = sDao.getServicioById(s);
					if(sObj != null) {
						if(KO.equals(sObj.getEstadoSubida())) {
							sObj.setEstado(PENDIENTE_IMPL);
							sObj.setEstadoImplantacion(null);
							sObj.setEstadoSubida(null);
						}
						else {
							sObj.setEstadoImplantacion(PRODUCCION);
						}
						
						sDao.createServicio(sObj, usermail);
					}
				}
			}
						
			// TODO generar informe linkar servicios y conectividades asociadas
			InformeDao inDao = InformeDao.getInstance();
			Informe infor = new Informe();
			Boolean tipoSubida;
			String fechaImplantacion;
			String texto_informe ;
			
			if(!VACIO.equals(conectividadesParam)) {
				ConectividadDao cDao = ConectividadDao.getInstance();
				String a  = conectividadesList.get(0);
				Conectividad conect = cDao.getConectividadById(a);
				tipoSubida = conect.subidaCalendada();
				fechaImplantacion = conect.getStr_fecha_implantacion();
				texto_informe = conect.getdetalleSubida();
				
			}else{
				ServicioDao cDao = ServicioDao.getInstance();
				String a  = serviciosList.get(0);
				Servicio serv = cDao.getServicioById(a);
				tipoSubida = serv.subidaCalendada();
				fechaImplantacion = serv.getStr_fecha_implantacion_produccion();
				texto_informe = serv.getdetalleSubida();
				
			}
			
			
			infor.setAnyoImplantacion(fechaImplantacion.substring(6,10));
			infor.setMesImplantacion(fechaImplantacion.substring(3, 5));
			infor.setDiaImplantacion(fechaImplantacion.substring(0, 2));
			if (tipoSubida){
				infor.setTipoSubida("Calendada");
			}else {
				infor.setTipoSubida("No calendada");
			}			
			infor.setTextoInforme(texto_informe);
			
			
			
			
			inDao.createInforme(infor, usermail);
			
			
			
			
			
			json.append("success", "true");
		} catch (Exception e) {
			json.append("failure", "true");
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
					"attachment; filename=RegistroImplantacionesGCS.xls");

			WritableWorkbook w = Workbook
					.createWorkbook(resp.getOutputStream());
			
			ConectividadDao cDao = ConectividadDao.getInstance();
			ServicioDao sDao = ServicioDao.getInstance();
			
			List<Conectividad> conectividades = cDao.getConectividadesEnCurso();
			List<Servicio> servicios = sDao.getServiciosEnCurso();
			
			if(conectividades == null && servicios == null) {
				conectividades = cDao.getConectividadesByEstado(PENDIENTE_IMPL);
				servicios = sDao.getServiciosByEstado(PENDIENTE_IMPL);
			}
			
			WritableSheet s = w.createSheet("Registro de implantaciones", 0);

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
			s.setRowView(0, 900);

			s.addCell(new Label(0, 0, "FECHA_SUBIDA", cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(2, 0, "ESTADO", cellFormat));
			s.addCell(new Label(3, 0, "GESTORIT", cellFormat));
			s.addCell(new Label(4, 0, "SERVICIO", cellFormat));
			s.addCell(new Label(5, 0, "CONECTIVIDAD", cellFormat));

			int aux = 1;
			
			ProyectoDao pDao = ProyectoDao.getInstance();
	        Proyecto p = null;
	        if (conectividades !=null && !conectividades.isEmpty()) {
				for (Conectividad c : conectividades) {
					p = pDao.getProjectbyId(c.getKey_proyecto());
					
					s.addCell(new Label(0, aux, c.getStr_fecha_implantacion()));
					s.addCell(new Label(1, aux, p.getClienteName()));
					s.addCell(new Label(2, aux, c.getEstado()));
					s.addCell(new Label(3, aux, p.getGestor_it_name()));
					s.addCell(new Label(5, aux, p.getConectividad()));
			
					aux++;
				}
	        }
			int aux1 = aux;
			if (servicios !=null && !servicios.isEmpty()) {
				for (Servicio serv : servicios) {
					p = pDao.getProjectbyId(serv.getId_proyecto());
					
					s.addCell(new Label(0, aux1, serv.getStr_fecha_implantacion_produccion()));
					s.addCell(new Label(1, aux1, p.getClienteName()));
					s.addCell(new Label(2, aux1, serv.getEstado()));
					s.addCell(new Label(3, aux1, p.getGestor_it_name()));
					s.addCell(new Label(4, aux1, serv.getServicio()));
			
					aux1++;					
				}
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
	
}
