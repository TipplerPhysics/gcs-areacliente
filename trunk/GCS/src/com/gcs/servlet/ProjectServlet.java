package com.gcs.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
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

import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ProjectServlet extends HttpServlet{

	private static final long serialVersionUID = -9001060985723788469L;
	
	public void doGet (HttpServletRequest req, HttpServletResponse resp){
		JSONObject json = new JSONObject();
		
		try{
			String accion = req.getParameter("accion");

			HttpSession sesion = req.getSession();
			//int sesionpermiso = (int) sesion.getAttribute("permiso");

			if ("new".equals(accion)){
				createProject(req,resp);
			}else if ("update".equals(accion)){
				editProject(req,resp);
			}else if ("delete".equals(accion)){
				deleteProject(req,resp);
			}else if ("xls".equals(accion)){
				generateXLS(req,resp);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	public void generateXLS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=ProyectosGCS.xls");
			
			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream());
			
			ProyectoDao pDao = ProyectoDao.getInstance();
			List<Proyecto> projects = pDao.getAllProjects();
			
			WritableSheet s = w.createSheet("Proyectos", 0);
		
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		    cellFont.setColour(Colour.WHITE);
		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.BLUE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);			
			
		    s.setColumnView(0, 20);
		    s.setColumnView(1, 20);
		    s.setColumnView(2, 20);
		    s.setColumnView(3, 20);
		    s.setColumnView(4, 15);
		    s.setColumnView(5, 30);
		    s.setColumnView(6, 30);
		    s.setColumnView(7, 20);
		    s.setRowView(0, 900);
						
			s.addCell(new Label(0, 0, "FECHA ALTA",cellFormat));
			s.addCell(new Label(1, 0, "COD. PROYECTO",cellFormat));
			s.addCell(new Label(2, 0, "TIPO",cellFormat));
			s.addCell(new Label(3, 0, "CLIENTE",cellFormat));
			s.addCell(new Label(4, 0, "CLASIFICACION",cellFormat));
			s.addCell(new Label(5, 0, "GESTOR IT",cellFormat));
			s.addCell(new Label(6, 0, "GESTOR NEGOCIO",cellFormat));
			s.addCell(new Label(7, 0, "COSTE",cellFormat));
			
			
			int aux=1;
			
			
			
			for (Proyecto p:projects){
				
								
				s.addCell(new Label(0, aux, p.getFecha_alta_str()));
				s.addCell(new Label(1, aux, p.getCod_proyecto()));
				s.addCell(new Label(2, aux, p.getTipo()));
				s.addCell(new Label(3, aux, p.getClienteName()));
				s.addCell(new Label(4, aux, Integer.toString(p.getClasificacion())));
				s.addCell(new Label(5, aux, p.getGestor_it_name()));
				s.addCell(new Label(6, aux, p.getGestor_negocio_name()));
				s.addCell(new Label(7, aux, p.getCoste() + " €"));
				
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
	
	private void deleteProject(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		
		JSONObject json = new JSONObject();
		ProyectoDao pDao = ProyectoDao.getInstance();	
		
		String id = req.getParameter("id");
		
		Proyecto p = pDao.getProjectbyId(Long.parseLong(id));
		pDao.deleteProject(p);
		
		json.append("success", "true");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);		
	}
	
	private void editProject (HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		JSONObject json = new JSONObject();		
		ProyectoDao pDao = ProyectoDao.getInstance();
		
		try {
		
			String id = req.getParameter("id");
			
			Proyecto p = pDao.getProjectbyId(Long.parseLong(id));
			
			if (p!=null){
				String fecha_alta_str = req.getParameter("fecha_alta_cliente");
				
				String tipo = req.getParameter("tipo");
				String cliente = req.getParameter("cliente");
				
				String clasificacion = req.getParameter("clasificacion");
				String gestor_it_str = req.getParameter("gestor_it");
				String gestor_negocio_str = req.getParameter("gestor_negocio");
				String coste = req.getParameter("coste");
				
				List<Proyecto> projects = pDao.getAllProjects();
				Boolean exist_project = false;
				
				
				
					p.setFecha_alta_str(fecha_alta_str);					
					
						p.setFecha_alta(Utils.dateConverter(fecha_alta_str));
									
				
					p.setTipo(tipo);
					p.setClienteKey(Long.parseLong(cliente));
					p.setClasificacion(Integer.parseInt(clasificacion));
					p.setGestor_it(Long.parseLong(gestor_it_str));
					p.setGestor_negocio(Long.parseLong(gestor_negocio_str));
					p.setCoste(coste);
					
					
					pDao.createProject(p);
			
				
				json.append("success", "true");
				json.append("id", p.getKey().getId());
				
			}
			
		} catch (ParseException e) {
			json.append("failure", "true");
			json.append("error", "Se ha producido un error inesperado");
			e.printStackTrace();
		}	
			
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);		
		
	}
	
	private void createProject(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException{
		
		JSONObject json = new JSONObject();
		Proyecto p = new Proyecto();
	
		try {
			
			String fecha_alta_str = req.getParameter("fecha_alta_cliente");
			
			String tipo = req.getParameter("tipo");
			String cliente = req.getParameter("cliente");
			
			String clasificacion = req.getParameter("clasificacion");
			String gestor_it_str = req.getParameter("gestor_it");
			String gestor_negocio_str = req.getParameter("gestor_negocio");
			String coste = req.getParameter("coste");
			
			if (fecha_alta_str.equals("")||tipo.equals("")||cliente.equals("")||clasificacion.equals("")
					||gestor_it_str.equals("")||gestor_negocio_str.equals("")||coste.equals("")){
				json.append("failure", "true");
				json.append("error","Faltan campos obligatorios.");
				
			}else{
				
				ProyectoDao pDao = ProyectoDao.getInstance();
				
				List<Proyecto> projects = pDao.getAllProjects();
				Boolean exist_project = false;
				
				
				
					
					p.setFecha_alta_str(fecha_alta_str);					
					p.setFecha_alta(Utils.dateConverter(fecha_alta_str));					
					
					p.setTipo(tipo);
					p.setClienteKey(Long.parseLong(cliente));
					p.setClasificacion(Integer.parseInt(clasificacion));
					p.setGestor_it(Long.parseLong(gestor_it_str));
					p.setGestor_negocio(Long.parseLong(gestor_negocio_str));
					p.setCoste(coste);
					
					
					pDao.createProject(p);
							
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		
		json.append("success", "true");
		json.append("id", p.getKey().getId());
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}

}