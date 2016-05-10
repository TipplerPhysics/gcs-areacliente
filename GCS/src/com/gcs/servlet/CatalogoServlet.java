package com.gcs.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

import com.gcs.beans.Pais;
import com.gcs.beans.ServicioFile;
import com.gcs.beans.User;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ServicioFileDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class CatalogoServlet extends HttpServlet {

	private static final long serialVersionUID = -826683004548238295L;

	private static final Logger log = Logger.getLogger(UserServlet.class.getName());

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		
		JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
		
		HttpSession sesion = req.getSession();
		String usermail = (String)sesion.getAttribute("usermail");
				
		 try {
			 
		
			int sesionpermiso = (int) sesion.getAttribute("permiso");
			 
			 if (sesionpermiso>2){	
					json.append("failure", "true");
					json.append("error", "No tienes los permisos para realizar esta operación");
					
					resp.setCharacterEncoding("UTF-8");
			        resp.setContentType("application/json");       
					resp.getWriter().println(json); 
			 }else{
				 if (accion.equals("new")){
						createService(req,resp,usermail);
					}else if (accion.equals("delete")){
						deleteUser(req,resp,usermail);
					}else if (accion.equals("update")){
						updateUser(req,resp,usermail);
					}else if (accion.equals("xls")){
						generateXLS(req,resp);
					}
			 }    
		
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	private void updateUser(HttpServletRequest req, HttpServletResponse resp, String usermail) throws JSONException, IOException{
		JSONObject json = new JSONObject();
		
		String id = req.getParameter("id");
		String nombre = req.getParameter("name");
		String paisName = req.getParameter("paisName");
		String  extensiones = req.getParameter("extensiones");
		ArrayList<String> ext = new ArrayList<String>();
		
		
		
		ServicioFileDao sfDao = ServicioFileDao.getInstance();
		ServicioFile sf = sfDao.getServicioFileById(Long.parseLong(id));
		PaisDao pdao = new PaisDao();
		Pais p = new Pais();
		p =pdao.getPaisByName(paisName);
		
		long paisId = p.getKey().getId();
		//propiedades
		
		sf.setName(nombre);
		sf.setPaisId(paisId);
		//sf.setExtensiones(ext);
		//sf.setExtensiones(extensiones);
		
		
		sfDao.createServicioFile(sf);
		
		json.append("success", "true");
		json.append("id", sf.getKey().getId());
		
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
		
		
	}
	
	private void createService(HttpServletRequest req, HttpServletResponse resp, String usermail) throws JSONException, IOException{
		JSONObject json = new JSONObject();
		
		try{
		
		String nombre = req.getParameter("nombre");
	    long pais = Long.valueOf(req.getParameter("pais"));
		String extensiones = req.getParameter("extensiones");
		ArrayList<String> ext = new ArrayList<String>();
		ServicioFileDao sfDao = new ServicioFileDao();
		
		
		
		
		ServicioFile sf = new ServicioFile();
		ext.add(extensiones.toUpperCase());	
			
		
           // u = new User(nombre,ap1,ap2,email,permiso,permisoStr,areas,dto);	
			sf.setPaisId(pais);
			sf.setName(nombre.toUpperCase());
			sf.setExtensiones(ext);
			sfDao.createServicioFile(sf);
			
			
			
			json.append("success", "true");
			json.append("id", sf.getKey().getId());
			
		
		
		}catch(Exception e){
			log.warning("Error en NewUserServlet");
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
	
	private void deleteUser(HttpServletRequest req, HttpServletResponse resp, String usermail) throws JSONException, IOException {
		JSONObject json = new JSONObject();
		
		UserDao udao = UserDao.getInstance();
		try{
			User u = udao.getUserbyId(Long.parseLong(req.getParameter("id")));
			
			udao.logicalDelete(u,usermail);
			json.append("success", "true");
		}catch(Exception e){
			json.append("failure", "true");
		}
		
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
	}	
	
	public void generateXLS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=CatalogoServiciosGCS.xls");
			
			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream());
			
			ServicioFileDao sfDao = ServicioFileDao.getInstance();
			List<ServicioFile> servicios = sfDao.getAllServicios();
			
			WritableSheet s = w.createSheet("Servicios", 0);
		
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		    cellFont.setColour(Colour.WHITE);
		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.BLUE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);			
			
		    s.setColumnView(0, 20);
		    s.setColumnView(1, 20);
		    s.setColumnView(2, 50);
		    s.setColumnView(3, 30);
		    s.setRowView(0, 900);
						
			s.addCell(new Label(0, 0, "ID PAIS",cellFormat));
			s.addCell(new Label(1, 0, "NOMBRE PAIS",cellFormat));
			s.addCell(new Label(2, 0, "NOMBRE SERVICIO",cellFormat));
			s.addCell(new Label(3, 0, "EXTENSIONES",cellFormat));
			
			
			int aux=1;
			
			
			
			for (ServicioFile sf:servicios){
				
				
				/*s.addCell(new Label(0, aux, sf.getStrPaisId().trim()));
				s.addCell(new Label(1, aux, sf.getNombrePais().trim()));
				s.addCell(new Label(2, aux, sf.getName().trim()));
				s.addCell(new Label(3, aux, sf.getStrExtensiones().trim()));*/
				String strPaisId = String.valueOf(sf.getPaisId());
				
				PaisDao pdao = new PaisDao();
				String paisNombre = pdao.getSPaisById(sf.getPaisId());
				
				
				String strExtensiones = "";
				for (int i=0;i<sf.getExtensiones().size();i++){
					   strExtensiones =strExtensiones.concat(sf.getExtensiones().get(i)+" ");
					}
				
				
				s.addCell(new Label(0, aux, strPaisId));
				s.addCell(new Label(1, aux, paisNombre));
				s.addCell(new Label(2, aux, sf.getName().trim()));
				s.addCell(new Label(3, aux, strExtensiones));
				
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

