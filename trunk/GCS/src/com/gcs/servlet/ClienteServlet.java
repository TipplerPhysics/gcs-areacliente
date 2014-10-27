package com.gcs.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.WorkbookSettings;
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
import com.gcs.beans.Proyecto;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ClienteServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1677664943148930903L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		JSONObject json = new JSONObject();

		String accion = req.getParameter("accion");

		try {

			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");

			
			
				if (accion.equals("new")) {
					if (sesionpermiso < 5) {
						createClient(req,resp);
					}else{
						returnNoPermission(req, resp);
					}
				}else if (accion.equals("delete")){
					if (sesionpermiso < 5) {
						deleteClient(req,resp);
					}else{
						returnNoPermission(req, resp);
					}
					
				}else if(accion.equals("edit")){
					editClient(req,resp);
				}else if (accion.equals("xls")){
					generateXLS(req,resp);
				}
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public void returnNoPermission(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		JSONObject json = new JSONObject();
		json.append("failure", "true");
		json.append("error","No tienes los permisos para realizar esta operación");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	private void generateXLS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=ClientesGCS.xls");
			
			WorkbookSettings ws = new WorkbookSettings();
			
			ws.setEncoding("Cp1252");
			
			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream(),ws);
			
			
			ClienteDao cDao = ClienteDao.getInstance();
			List<Cliente> clientes = cDao.getAllClientes();
			
			WritableSheet s = w.createSheet("Clientes", 0);
			
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		    cellFont.setColour(Colour.WHITE);
		    
		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.BLUE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);	
		  
			
		    s.setColumnView(0, 35);
		    s.setColumnView(1, 16);
		    s.setColumnView(2, 16);
		    s.setColumnView(3, 20);
		    s.setColumnView(4, 40);
		    s.setColumnView(5, 20);
		    s.setColumnView(6, 15);
		    s.setColumnView(7, 40);
		  
		   
		    s.setRowView(0, 900);
						
			s.addCell(new Label(0, 0, "NOMBRE",cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE ID",cellFormat));
			s.addCell(new Label(2, 0, "CRITICIDAD",cellFormat));
			s.addCell(new Label(3, 0, "FECHA ALTA CLIENTE",cellFormat));
			s.addCell(new Label(4, 0, "LOGO URL",cellFormat));
			s.addCell(new Label(5, 0, "REF. GLOBAL",cellFormat));
			s.addCell(new Label(6, 0, "TIPO",cellFormat));
			s.addCell(new Label(7, 0, "PAISES",cellFormat));
			
			
			int aux=1;
			
			for (Cliente c:clientes){
				s.addCell(new Label(0, aux, c.getNombre()));
				s.addCell(new Label(1, aux, c.getClientId()));
				s.addCell(new Label(2, aux, c.getCriticidad()));
				s.addCell(new Label(3, aux, c.getStr_fecha_alta_cliente()));
				s.addCell(new Label(4, aux, c.getLogo_url()));
				s.addCell(new Label(5, aux, c.getRef_global()));
				s.addCell(new Label(6, aux, c.getTipo()));
				if (!c.getPaises().isEmpty()){
					String str_paises = c.getPaises().toString();
					s.addCell(new Label(7, aux, c.getPaises().toString().substring(1, str_paises.length()-1)));
				}
				
				
				
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

	
	
	private void deleteClient(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		String id = req.getParameter("id");
		JSONObject json = new JSONObject();
		
		try{
			ClienteDao cDao = new ClienteDao();
			Cliente c = cDao.getClienteById(Long.parseLong(id));
			cDao.logicalDelete(c);
			
			json.append("success", "true");
		} catch (Exception e) {
			json.append("failure", "true");	
		}
	
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
		
	}
	
	private void editClient(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException{
		JSONObject json = new JSONObject();
		
		Cliente c = new Cliente();
		ClienteDao cDao = ClienteDao.getInstance();
		
		try {
			
			String errorStr = "";
			String nombre = req.getParameter("client_name");
			String fecha_entrada_peticion_ed = req.getParameter("fecha_alta_cliente");
			
			
			String ref_global = req.getParameter("ref_global");

			String id = req.getParameter("id");
			String logo_url = req.getParameter("logo_url");
			
			String criticidad = req.getParameter("criticidad");
			String tipo = req.getParameter("tipo");
			String[] paises_str = req.getParameterValues("paises");
			
			Set<String> paises = new HashSet<String>();
			
			for (String p:paises_str){
				paises.add(p);
			}
				
			
			c = cDao.getClienteById(Long.parseLong(id));
			
			String errorMsg = "";
			
			if (cDao.getClienteByRefGlobal(ref_global)!=null) {
				if (!cDao.getClienteByRefGlobal(ref_global).equals(c)){
					errorMsg = "Ya existe un usuario con esta referencia global";
				}
			}
			
			
		
			if (errorMsg.equals("")){
				c.setNombre(nombre);			
				
				c.setRef_global(ref_global);
				c.setLogo_url(logo_url);
				c.setCriticidad(criticidad);
				c.setTipo(tipo);
				c.setPaises(paises);
				
				c.setStr_fecha_alta_cliente(fecha_entrada_peticion_ed);
				c.setFecha_alta_cliente(Utils.dateConverter(fecha_entrada_peticion_ed));
				
				cDao.createCliente(c);
				json.append("success", "true");		
				json.append("id", c.getKey().getId());
			}else{
				json.append("success", "false");
				json.append("error", errorMsg);
			}
		
			
			
		} catch (JSONException e) {
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
			e.printStackTrace();
		} catch (ParseException e) {
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
			e.printStackTrace();
		}catch (Exception e) {
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
			e.printStackTrace();
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	private void createClient(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException{
		
		JSONObject json = new JSONObject();
		
		Cliente c = new Cliente();
		ClienteDao cDao = ClienteDao.getInstance();
		
		try {
			
			String errorStr = "";
			
			String nombre = req.getParameter("client_name");
			String criticidad = req.getParameter("criticidad");		
			String str_fecha_alta_cliente = req.getParameter("fecha_alta_cliente");
			String logo_url = req.getParameter("logo_url");			
			String ref_global = req.getParameter("ref_global").toUpperCase();
			String tipo = req.getParameter("tipo");
			String[] paises = req.getParameterValues("paises");
			
			Set<String> paises_set = new HashSet<String>();
			for (String p:paises)
				paises_set.add(p);
			
			
			
			c = cDao.getClienteByName(nombre);
			List<Cliente> clientes = cDao.getAllClientes();
			Boolean exist_client = false;
			
			for (Cliente cl:clientes){
				if (cl.getNombre().toLowerCase().equals(nombre.toLowerCase())){
					exist_client = true;
				}
			}
			
			
			if (exist_client){
				errorStr += "Ya existe un cliente con este nombre";			
			}
			
			c = cDao.getClienteByRefGlobal(ref_global);
			if (c!=null){
				if (!errorStr.equals(""))
					errorStr += " </br> ";
				errorStr += "Ya existe un cliente con esta referencia global";			
			}
			
			
			if (errorStr.equals("")){
				c = new Cliente();
				c.setNombre(nombre);
				c.setCriticidad(criticidad);
				c.setStr_fecha_alta_cliente(str_fecha_alta_cliente);
				c.setFecha_alta_cliente(Utils.dateConverter(str_fecha_alta_cliente));
				c.setLogo_url(logo_url);
				c.setRef_global(ref_global);
				c.setTipo(tipo);
				c.setPaises(paises_set);
				
				
				cDao.createCliente(c);
				json.append("success", "true");		
				json.append("id", c.getKey().getId());
			}else{
				json.append("success", "false");
				json.append("error", errorStr);	
			}		
		
		} catch (JSONException | ParseException e) {
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
			e.printStackTrace();
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
		
		
	}
	
}
