package com.gcs.servlet;

import java.util.logging.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
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

import com.gcs.beans.Pais;
import com.gcs.dao.PaisDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class PaisesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -826683004548238295L;

	private static final Logger log = Logger.getLogger(UserServlet.class.getName());

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
				if ("new".equals(accion)) {
					createPais(req, resp,usermail);
				} else if (accion.equals("delete")) {
					deletePais(req, resp,usermail);
				} else if (accion.equals("update")) {
					updatePais(req, resp,usermail);
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
	
/*
 * Metodo del Servlet que da de alta un país en tabla Pais
*/	 
	private void createPais(HttpServletRequest req, HttpServletResponse resp, String usermail) throws JSONException, IOException{
		JSONObject json = new JSONObject();
		
		PaisDao pDao = PaisDao.getInstance();

		try{
		
			String nombre = req.getParameter("nombre");
			Boolean existePais = false;
			
			List<Pais> paises = pDao.getAllPaises();
			
			for (Pais pa:paises){
				if (pa.getName().toUpperCase().equals(nombre.toUpperCase())){
					existePais = true;
				}
			}
			
			if (existePais) {
				json.append("failure", "true");
				json.append("error", "Ya existe un pais con este nombre");
				existePais = false;
			}
			else{
				Pais p = new Pais();
				p.setNme(nombre.toUpperCase());
				pDao.createPais(p);
				json.append("success", "true");
				json.append("id", p.getKey().getId());			
			
			}
		}catch (Exception e){
			log.warning("Error en PaisesServlet");
			log.warning((e.toString()));
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			e.printStackTrace(ps);
			String content = baos.toString("ISO-8859-1"); // e.g. ISO-8859-1
			
		
			log.warning(content);
			
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
		}
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
	}

	private void updatePais(HttpServletRequest req, HttpServletResponse resp, String usermail) throws JSONException, IOException{
		JSONObject json = new JSONObject();
		
		String id = req.getParameter("id");
		String nombre = req.getParameter("name");
		PaisDao pDao = PaisDao.getInstance();

		try{
			Pais p = pDao.getPaisById(Long.parseLong(id));
			p.setNme(nombre.toUpperCase());

			pDao.createPais(p);
			
			json.append("success", "true");
			json.append("id", p.getKey().getId());
		
		}catch (Exception e){
			json.append("failure", "true");
		}
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
		
		
	}
	
	
	private void deletePais(HttpServletRequest req, HttpServletResponse resp, String usermail) throws JSONException, IOException {
		JSONObject json = new JSONObject();
		

		PaisDao pDao = PaisDao.getInstance();

		try{
			String identificador = req.getParameter("id");
			Pais p = pDao.getPaisById(Long.parseLong(identificador));
			pDao.deletePais(p);
			json.append("success", "true");
		}catch(Exception e){
			json.append("failure", "true");
		}
		
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
	}

	
	private void generateXLS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=PaisesGCS.xls");
			
			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream());

			
			PaisDao pDao = PaisDao.getInstance();
			List<Pais> paises = pDao.getAllPaises();
			
		
			WritableSheet s = w.createSheet("Paises", 0);
		
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		    cellFont.setColour(Colour.WHITE);
		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.BLUE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);			
			
		    s.setColumnView(0, 16);
		    s.setColumnView(1, 16);
		    s.setRowView(0, 900);
						
			s.addCell(new Label(0, 0, "ID PAIS",cellFormat));
			s.addCell(new Label(1, 0, "NOMBRE",cellFormat));
			
			
			int aux=1;
			
			
			
			for (Pais p:paises){
				
				s.addCell(new Label(1, aux, p.getName().trim()));
				s.addCell(new Label(0, aux, Long.toString(p.getKey().getId()) ));
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
