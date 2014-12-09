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

public class InformeServlet extends HttpServlet {
	

	private static final Logger log = Logger.getLogger(InformeServlet.class.getName());

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
				
				cargarDatos(req, resp);
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void cargarDatos(HttpServletRequest req, HttpServletResponse resp)
		throws JSONException, IOException {
		
		JSONObject json = new JSONObject();
		InformeDao iDao = InformeDao.getInstance();
		List<Informe> informes = iDao.getAllInformes();
		List<String> Meses= new ArrayList<String>();
		List<String> Dias= new ArrayList<String>();
		List<String> Tipo= new ArrayList<String>();
		
		try{
			
		
			if(informes.isEmpty()){
				json.append("error","No hay ningún informe generado");
			
			}else{
			
			for (Informe i : informes) {
				if(Meses.indexOf(i.getMesImplantacion())==-1){
					Meses.add(i.getMesImplantacion());
				}
				if(Dias.indexOf(i.getDiaImplantacion())==-1){
					Dias.add(i.getDiaImplantacion());
				}
				if(Tipo.indexOf(i.getTipoSubida())==-1){
					Tipo.add(i.getTipoSubida());
				}
		
			}
			
			json.append("Meses", Meses);
			json.append("Dias", Dias);
			json.append("Tipo", Tipo);
			json.append("success", "true");
			
			}
		}catch (Exception e) {
			json.append("failure", "true");
		}
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
		
		
	}

}