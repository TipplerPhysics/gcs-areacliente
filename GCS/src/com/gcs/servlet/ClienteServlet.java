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

import com.gcs.beans.Cliente;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;
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

			if (sesionpermiso > 2) {
				json.append("failure", "true");
				json.append("error",
						"No tienes los permisos para realizar esta operaci�n");

				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("application/json");
				resp.getWriter().println(json);
			} else {
				if (accion.equals("new")) {
					createClient(req, resp);
				}else if (accion.equals("delete")){
					deleteClient(req,resp);
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
	
	private void generateXLS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=ClientesGCS.xls");
			
			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream());
			
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
		    s.setColumnView(5, 30);
		    s.setColumnView(6, 20);
		    s.setColumnView(7, 15);
		    s.setColumnView(8, 15);
		    s.setRowView(0, 900);
						
			s.addCell(new Label(0, 0, "NOMBRE",cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE ID",cellFormat));
			s.addCell(new Label(2, 0, "CRITICIDAD",cellFormat));
			s.addCell(new Label(3, 0, "FECHA ALTA CLIENTE",cellFormat));
			s.addCell(new Label(4, 0, "LOGO URL",cellFormat));
			s.addCell(new Label(5, 0, "REF. LOCAL",cellFormat));
			s.addCell(new Label(6, 0, "REF. GLOBAL",cellFormat));
			s.addCell(new Label(7, 0, "TIPO",cellFormat));
			s.addCell(new Label(8, 0, "WORKFLOW",cellFormat));
			
			
			int aux=1;
			
			for (Cliente c:clientes){
				s.addCell(new Label(0, aux, c.getNombre()));
				s.addCell(new Label(1, aux, c.getClientId()));
				s.addCell(new Label(2, aux, c.getCriticidad()));
				s.addCell(new Label(3, aux, c.getStr_fecha_alta_cliente()));
				s.addCell(new Label(4, aux, c.getLogo_url()));
				s.addCell(new Label(5, aux, c.getRef_local()));
				s.addCell(new Label(6, aux, c.getRef_global()));
				s.addCell(new Label(7, aux, c.getTipo()));
				if (c.getWorkflow()==true){
					s.addCell(new Label(8, aux, "SI"));
				}else{
					s.addCell(new Label(8, aux, "NO"));
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
			cDao.deleteClient(c);
			
			json.append("success", "true");
		} catch (Exception e) {
			json.append("failure", "true");	
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
			String paises = req.getParameter("paises");
			String ref_global = req.getParameter("ref_global").toUpperCase();
			String ref_local = req.getParameter("ref_local").toUpperCase();
			String tipo = req.getParameter("tipo");
			String workflow = req.getParameter("workflow");
			Boolean workflow_bool = false;
			
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
			c = cDao.getClienteByRefLocal(ref_local);
			if (c!=null){
				if (!errorStr.equals(""))
					errorStr += " </br> ";
				errorStr += "Ya existe un cliente con esta referencia local";			
			}
			
			if (errorStr.equals("")){
				c = new Cliente();
				c.setNombre(nombre);
				c.setCriticidad(criticidad);
				c.setStr_fecha_alta_cliente(str_fecha_alta_cliente);
				c.setFecha_alta_cliente(Utils.dateConverter(str_fecha_alta_cliente));
				c.setLogo_url(logo_url);
				c.setPaises(paises);
				c.setRef_global(ref_global);
				c.setRef_local(ref_local);
				c.setTipo(tipo);
				if ("SI".equals(workflow))
					workflow_bool=true;
				c.setWorkflow(workflow_bool);
				
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
