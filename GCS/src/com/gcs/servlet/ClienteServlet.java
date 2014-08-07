package com.gcs.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.Cliente;
import com.gcs.dao.ClienteDao;
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
				}
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
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
