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
import jxl.write.WritableWorkbook;

import com.gcs.beans.Conectividad;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.dao.ConectividadDao;
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
			
			// TODO send email solicitud
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	        ProyectoDao pDao = ProyectoDao.getInstance();
	        Proyecto p = null;
	        String msgBody = "";
	        msgBody +="<ul>";
	        
	        	for(String c : conectividadesList){
	        		Conectividad cObj = cDao.getConectividadById(c);
	        		p = pDao.getProjectbyId(cObj.getKey_proyecto());
	        		msgBody +="Conectividades solicitadas:<br/>";
	        		msgBody +="<li>";
	        		msgBody +=p.getClienteName()+", "+p.getConectividad();
	        		msgBody +="</li>";
	        		msgBody +="<br/>"+cObj.getdetalleSubida();	        		
	        	}
	        	
	        	for(String s : serviciosList){
	        		Servicio sObj = sDao.getServicioById(s);
	        		p = pDao.getProjectbyId(sObj.getId_proyecto());
	        		msgBody +="<br/><br/>Servicios solicitados:<br/>";
	        		msgBody +="<li>";
	        		msgBody +=p.getClienteName()+", "+sObj.getPais()+", "+sObj.getServicio();
	        		msgBody +="</li>";
	        		msgBody +="<br/>"+sObj.getdetalleSubida();	        		
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

	        } catch (AddressException e) {
	        	e.printStackTrace();
	        } 
					
			
			json.append("success", "true");
		}catch (Exception e) {
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
			
			// TODO send email confirmacion
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	        ProyectoDao pDao = ProyectoDao.getInstance();
	        Proyecto p = null;
	        String msgBody = "";
	        msgBody +="<ul>";
	        
	        	for(String c : conectividadesList){
	        		Conectividad cObj = cDao.getConectividadById(c);
	        		p = pDao.getProjectbyId(cObj.getKey_proyecto());
	        		msgBody +="Conectividades confirmadas:<br/>";
	        		msgBody +="<li>";
	        		msgBody +=p.getClienteName()+", "+p.getConectividad();
	        		msgBody +="</li>";
	        		msgBody +="<br/>"+cObj.getdetalleSubida();	        		
	        	}
	        	
	        	for(String s : serviciosList){
	        		Servicio sObj = sDao.getServicioById(s);
	        		p = pDao.getProjectbyId(sObj.getId_proyecto());
	        		msgBody +="<br/><br/>Servicios confirmados:<br/>";
	        		msgBody +="<li>";
	        		msgBody +=p.getClienteName()+", "+sObj.getPais()+", "+sObj.getServicio();
	        		msgBody +="</li>";
	        		msgBody +="<br/>"+sObj.getdetalleSubida();	        		
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
		}catch (Exception e) {
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
						
			// TODO generar informe
			
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

			/*ImplantacionDao dDao = ImplantacionDao.getInstance();
			List<Implantacion> demandas = dDao.getAllImplantaciones();

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

			s.addCell(new Label(0, 0, "FECHA_SUBIDA", cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(2, 0, "ESTADO", cellFormat));
			s.addCell(new Label(3, 0, "GESTORIT", cellFormat));
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

			for (Implantacion d : demandas) {
				
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
				if (d.getDevuelta().equals(true))
					s.addCell(new Label(11, aux, "Si"));
				else
					s.addCell(new Label(11, aux, "No"));

				if (d.getGestor_it()!=null){
					u = uDao.getUserbyId(d.getGestor_it());
					if (u!=null){
						s.addCell(new Label(12, aux, u.getNombre() + " "
								+ u.getApellido1() + " " + u.getApellido2()));
					}
				}
				
				

				s.addCell(new Label(13, aux, d.getCatalogacion()));
				s.addCell(new Label(14,aux,d.getStr_fecha_comunicacion()));
				s.addCell(new Label(15,aux,d.getHora_comunicacion_asignacion()));

				aux++;
			}*/

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
	
	/*private void deleteImplantacion(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();

		ImplantacionDao dDao = ImplantacionDao.getInstance();

		try {
			Implantacion d = dDao.getImplantacionById(Long.parseLong(req
					.getParameter("id")));

			dDao.deleteImplantacion(d,usermail);
			json.append("success", "true");
		} catch (Exception e) {
			json.append("failure", "true");
		}

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}*/

	/*private void updateImplantacion(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();

		try {
			
			log.info("Dentro de updateImplantacion");

			String idStr = req.getParameter("id");
			Long id = Long.parseLong(idStr);

			ImplantacionDao dDao = ImplantacionDao.getInstance();
			Implantacion d = dDao.getImplantacionById(id);

			
			String gestor_negocio = req.getParameter("gestor_negocio");
			
			
			
			String cliente = req.getParameter("cliente");
			String estado = req.getParameter("estado");
			String gestor_it = req.getParameter("gestor_it");
			
			
			d.setClientekey(Long.parseLong(cliente));
			//d.setEstado(estado);
			d.setGestor_it(Long.parseLong(gestor_it));
			
			
			
			if (!"".equals(gestor_negocio) && gestor_negocio!=null)
				
				d.setGestor_negocio(Long.parseLong(gestor_negocio));
			
			
			
			
			
			
			
						

			dDao.createImplantacion(d,usermail);

			json.append("success", "true");
			json.append("id", d.getKey().getId());
			json.append("servicio", d.getServicio());

		} catch (Exception e) {
			log.warning("Error en updateImplantacion");
			log.warning((e.toString()));
		//	log.warning((e.fillInStackTrace().toString()));
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			e.printStackTrace(ps);
			String content = baos.toString("ISO-8859-1"); // e.g. ISO-8859-1
			
		
			log.warning(content);
			
			json.append("failure", "true");
			json.append("error", "Se ha producido un error inesperado");

		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);

	}*/
	
	/*private void updateEstadoSubida(HttpServletRequest req, HttpServletResponse resp)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();
		
		try{
			
			log.info("Dentro de updateEstadoSubida");
			

			String idStr = req.getParameter("id");
			Long id = Long.parseLong(idStr);

			ImplantacionDao dDao = ImplantacionDao.getInstance();
			Implantacion d = dDao.getImplantacionById(id);		
			
			String estadoSubida = req.getParameter("estadoSubida");
			String detalleSubida = req.getParameter("detalleSubida");
			
			d.setEstadoSubida(estadoSubida);
			d.setDetalleSubida(detalleSubida);
			
			dDao.createImplantacion(d,estadoSubida);

			json.append("success", "true");
			json.append("id", d.getKey().getId());
			json.append("servicio", d.getServicio());
			
		}catch (Exception e) {
			
			
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}*/
	
	private void createImplantacion(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws JSONException, IOException {
		JSONObject json = new JSONObject();
		
		

		/*Implantacion d = new Implantacion();

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
			
			
			

			ImplantacionDao dDao = ImplantacionDao.getInstance();

			
			
			
			
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

			dDao.createImplantacionAndIncreaseCount(d,usermail);

			json.append("success", "true");
			json.append("id", d.getKey().getId());
			json.append("servicio", d.getServicio());

		} catch (ParseException e) {
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
			e.printStackTrace();
		} catch (Exception e) {
			json.append("success", "true");
			json.append("id", d.getKey().getId());
			json.append("servicio", d.getServicio());
		}

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);*/
	}

	/*public void generateXLS(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=RegistroImplantacionesGCS.xls");

			WritableWorkbook w = Workbook
					.createWorkbook(resp.getOutputStream());

			ImplantacionDao dDao = ImplantacionDao.getInstance();
			List<Implantacion> demandas = dDao.getAllImplantaciones();

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

			s.addCell(new Label(0, 0, "FECHA_SUBIDA", cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(2, 0, "ESTADO", cellFormat));
			s.addCell(new Label(3, 0, "GESTORIT", cellFormat));
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

			int aux = 1;*/

			/*for (Implantacion d : demandas) {
				
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
				if (d.getDevuelta().equals(true))
					s.addCell(new Label(11, aux, "Si"));
				else
					s.addCell(new Label(11, aux, "No"));

				if (d.getGestor_it()!=null){
					u = uDao.getUserbyId(d.getGestor_it());
					if (u!=null){
						s.addCell(new Label(12, aux, u.getNombre() + " "
								+ u.getApellido1() + " " + u.getApellido2()));
					}
				}
				
				

				s.addCell(new Label(13, aux, d.getCatalogacion()));
				s.addCell(new Label(14,aux,d.getStr_fecha_comunicacion()));
				s.addCell(new Label(15,aux,d.getHora_comunicacion_asignacion()));

				aux++;
			}*/

	/*		w.write();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Exception in Excel", e);
		} finally {
			if (out != null)
				out.close();
		}

	}*/

	private boolean isLong(String input) {
		try {
			Long.parseLong(input);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
