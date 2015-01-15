package com.gcs.servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.Cliente;
import com.gcs.beans.ContadorCliente;
import com.gcs.beans.ContadorDemanda;
import com.gcs.beans.Equipo;
import com.gcs.beans.FechaCalendada;
import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ContadorClienteDao;
import com.gcs.dao.ContadorDemandaDao;
import com.gcs.dao.EquipoDao;
import com.gcs.dao.FechaCalendadaDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class DefaultConf extends HttpServlet {

	private static final long serialVersionUID = 5138569693243826017L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {

		JSONObject json = new JSONObject();
		String result = "";
		
		String accion = req.getParameter("accion");
		
		HttpSession session = req.getSession();
		String usermail = (String)session.getAttribute("usermail");
		

		try {
			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");

			if (sesionpermiso == 1) {
				
				if ("def_user".equals(accion)){
					/* Crea cliente de prueba */
					ClienteDao cDao = ClienteDao.getInstance();				
					Cliente cliente = new Cliente();			
					cliente.setNombre("Cliente de Prueba");
					cDao.createCliente(cliente, usermail);	
					json.append("success", "true");
				}else if ("def_counter_demand".equals(accion)){
					ContadorDemanda cd = new ContadorDemanda(1);
					ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();
					cdDao.createContador(cd);	
					json.append("success", "true");
				}else if ("def_counter_client".equals(accion)){
					ContadorCliente cc = new ContadorCliente(1);
					ContadorClienteDao ccDao = ContadorClienteDao.getInstance();
					ccDao.createContador(cc);	
					json.append("success", "true");
				}else if ("def_teams".equals(accion)){
					createDefTeams();
					json.append("success", "true");
				}else if ("inic_fechas".equals(accion)){
					inicFechas(req,resp);
					json.append("success", "true");
				}else if ("proyectos".equals(accion)){
					result = loadProyectos(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}else if ("clientes".equals(accion)){
					result = loadClientes(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}
				// TODO añadir usuarios
			} else {
				json.append("failure", "true");
				json.append("error",
						"No tienes los permisos para realizar esta operación");
			}
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/plain");
			resp.getWriter().println(result);

		} catch (Exception e) {
			try {
				json.append("failure", "true");
				json.append("error", "Error inesperado");

				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("text/plain");
				resp.getWriter().println(json);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		doGet(req, resp);
	}
	
	// TODO completar para usuarios
	/*private String loadUsuarios(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
				
	}*/
	
	private String loadClientes(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{

		boolean save = false;
		String saveParam = req.getParameter("save"); 
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
		String link = "/datadocs/clientes____.csv";
		
		String result = "";
		try {
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			ClienteDao clientesDao = ClienteDao.getInstance();
			Cliente cliente = null;
			
			int counter = 0;
			boolean error = false;

			while ((inputLine = in.readLine()) != null) {
				error = false;
				
				String line = inputLine;
				String[] clienteSplit = line.split(";", -1);

				boolean procesar = true;
				if (clienteSplit.length < 19) {
					procesar = false;
				}
				String codigo = clienteSplit[0];
				if (esNuloVacio(codigo)) {
					procesar = false;
					break;
				}

				if (procesar) {
					//String fechaAlta = proyectoSplit[1];
					// TODO: sacar columnas del array a variables. revisar las propiedades del objeto en Cliente.java
					String nombre = clienteSplit[1];
					String ref_global = clienteSplit[1];
					String criticidad = clienteSplit[1];
					String str_fecha_alta_cliente = null;
					String logo_url = null;
					String tipo = null;
					Set<String> paises_set = null;//new HashSet<String>();
					
					
					cliente = clientesDao.getClienteByName(nombre);
					List<Cliente> clientes = clientesDao.getAllClientes();
					Boolean exist_client = false;
					
					for (Cliente cl:clientes){
						if (cl.getNombre().toLowerCase().equals(nombre.toLowerCase())){
							exist_client = true;
						}
					}
										
					if (exist_client){
						result += "Ya existe un cliente con este nombre \r\n";		
						error = true;
					}
					
					cliente = new Cliente();		
					if(ref_global!=""){
						Cliente aux = new Cliente();	
						aux = clientesDao.getClienteByRefGlobal(ref_global);
						if (aux!=null){
							result += "Ya existe un cliente con esta referencia global \r\n";
							error = true;
						}else{
							cliente.setRef_global(ref_global); // ref golbal colocada aqui para poder hacer el campo opcional
						}
					}
					
					if(!esNuloVacio(nombre)) {
						cliente.setNombre(nombre);
					}
					else {
						result += "Error nombre \r\n";
						error = true;
					}
					// TODO: check criticidad 
					cliente.setCriticidad(criticidad);
					// TODO: check fecha alta 
					cliente.setStr_fecha_alta_cliente(str_fecha_alta_cliente);
					cliente.setFecha_alta_cliente(Utils.dateConverter(str_fecha_alta_cliente));
					cliente.setLogo_url(logo_url);
					// TODO: check tipo 
					cliente.setTipo(tipo);
					// TODO: check paises 
					cliente.setPaises(paises_set);
					
					if(!error) {
						result += cliente.toString() + "\r\n\r\n";
					}
					else {
						result += "\r\n";
					}
					
					if(save) {
						clientesDao.createCliente(cliente, usermail);
					}
				}
				counter++;
			}
		}catch (Exception e) {
			e.printStackTrace();
			result = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e);
		}
		return result;
	}
	
	private String loadProyectos(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{

		boolean save = false;
		String saveParam = req.getParameter("save"); 
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
		String link = "/datadocs/proyectos_2014_11_13.csv";
		
		String result = "";
		try {
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			ProyectoDao proyectoDao = ProyectoDao.getInstance();
			Proyecto proyecto = null;
			
			int counter = 0;
			boolean error = false;

			while ((inputLine = in.readLine()) != null) {
				error = false;
				
				String line = inputLine;
				String[] proyectoSplit = line.split(";", -1);

				boolean procesar = true;
				if (proyectoSplit.length < 19) {
					procesar = false;
				}
				String codigo = proyectoSplit[0];
				if (esNuloVacio(codigo)) {
					procesar = false;
					break;
				}

				if (procesar) {
					String fechaAlta = proyectoSplit[1];
					String clienteKey = proyectoSplit[2];
					String clienteName = proyectoSplit[3];
					String tipo = proyectoSplit[4];
					String producto = proyectoSplit[5];
					String conectividad = proyectoSplit[6];
					String gestorItKey = proyectoSplit[7];
					String gestorItName = proyectoSplit[8];
					String gestorNegocioKey = proyectoSplit[9];
					String gestorNegocioName = proyectoSplit[10];
					String clasificacion = proyectoSplit[11];
					String coste = proyectoSplit[12];
					String inicioEspecificaciones = proyectoSplit[13];
					String finEspecificaciones = proyectoSplit[14];
					String c100 = proyectoSplit[15];
					String okNegocio = proyectoSplit[16];
					String solicitudViabilidad = proyectoSplit[17];
					String finViabilidad = proyectoSplit[18];

					proyecto = new Proyecto();
					
					result += counter + " :\r\n";
					
					proyecto.setStr_envioC100(c100);
					proyecto.setStr_OKNegocio(okNegocio);
					
					proyecto.setStr_fecha_fin_valoracion(finEspecificaciones);
					proyecto.setStr_fecha_inicio_valoracion(inicioEspecificaciones);
					
					proyecto.setStr_fecha_fin_viabilidad(finViabilidad);
					proyecto.setStr_fecha_inicio_viabilidad(solicitudViabilidad);
					
					if(!esNuloVacio(producto)) {
						proyecto.setProducto(producto);
					}
					else {
						result += "Error producto \r\n";
						error = true;
					}
					
					proyecto.setConectividad(conectividad);
					
					if (isThisDateValid(fechaAlta, "dd/MM/yyyy")) {
						proyecto.setFecha_alta_str(fechaAlta);
						proyecto.setFecha_alta(Utils.dateConverter(fechaAlta));
					}
					else {
						result += "Error fecha alta \r\n";
						error = true;
					}				
					
					if(!esNuloVacio(tipo)) {
						proyecto.setTipo(tipo);
					}
					else {
						result += "Error tipo \r\n";
						error = true;
					}
					if(!esNuloVacio(clienteKey)) {
						proyecto.setClienteKey(Long.parseLong(clienteKey));
					}
					else {
						result += "Error cliente \r\n";
						error = true;
					}
					if(!esNuloVacio(clasificacion)) {
						proyecto.setClasificacion(Integer.parseInt(clasificacion));
					}
					else {
						result += "Error clasificacion \r\n";
						error = true;
					}
					if(!esNuloVacio(gestorItKey)) {
						proyecto.setGestor_it(Long.parseLong(gestorItKey));
					}
					else {
						result += "Error gestor it \r\n";
						error = true;
					}
					if(!esNuloVacio(gestorNegocioKey)) {
						proyecto.setGestor_negocio(Long.parseLong(gestorNegocioKey));
					}
					else {
						result += "Error gestor negocio \r\n";
						error = true;
					}
					proyecto.setCoste(coste);
					//proyecto.setUrl_doc_google_drive(url_doc_google_drive);
					
					if(!error) {
						result += proyecto.toString() + "\r\n\r\n";
					}
					else {
						result += "\r\n";
					}
					
					if(save) {
						proyectoDao.createProject(proyecto, usermail);
					}
				}
				counter++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e);
		}
		return result;
	}
	
	private void inicFechas(HttpServletRequest req, HttpServletResponse resp) throws InterruptedException{
		
		   String link = req.getParameter("link");
		      
		      try {
		    	  
		    	  
		          URL myURL = new URL(link);

		          InputStreamReader a = new InputStreamReader(myURL.openStream());
		          BufferedReader in = new BufferedReader(a);
		             
		          String inputLine = new String();
		          
		          FechaCalendadaDao fechDao = FechaCalendadaDao.getInstance();
		          FechaCalendada fecha = new FechaCalendada();
		          
		          
		          while ((inputLine = in.readLine()) != null){
		        	  fecha = new FechaCalendada();
		        	  String fechaStr = inputLine;
		        	  fecha.setStr_fecha(fechaStr);
		        	  fechDao.createFechaCalendada(fecha);
		          }
		      }catch(Exception e){
		          e.printStackTrace();
		      }
	}
	
	private void createDefTeams(){
			
		EquipoDao eDao = EquipoDao.getInstance();
		
		Equipo e = new Equipo();		
		e.setNombre("Innovery");
		e.setContador(1);
		e.setUltima_escritura(new Date());
		eDao.createEquipo(e);
		
		e = new Equipo();
		e.setNombre("Capgemini");
		e.setContador(1);
		e.setUltima_escritura(new Date());
		eDao.createEquipo(e);
		
		e = new Equipo();
		e.setNombre("Solutions");
		e.setContador(1);
		e.setUltima_escritura(new Date());
		eDao.createEquipo(e);
		
		e = new Equipo();
		e.setNombre("Soporte Swift");
		e.setContador(1);
		e.setUltima_escritura(new Date());
		eDao.createEquipo(e);
		
		e = new Equipo();
		e.setNombre("IS");
		e.setContador(1);
		e.setUltima_escritura(new Date());
		eDao.createEquipo(e);
		
		e = new Equipo();
		e.setNombre("Telemáticos");
		e.setContador(1);
		e.setUltima_escritura(new Date());
		eDao.createEquipo(e);
		
		e = new Equipo();
		e.setNombre("Gestor IT");
		e.setContador(1);
		e.setUltima_escritura(new Date());
		eDao.createEquipo(e);
	}

	public User findUserByName(List<User> users, String name) {
		for(User user : users) {
			String fullName = user.getFullName();
			if(fullName.equals(name)) {
				return user;
			}
		}
		return null;
	}
	
	public boolean esNuloVacio(String string) {
		if(string == null || "".equals(string)) {
			return true;
		}
		return false;
	}
	
	public boolean isThisDateValid(String dateToValidate, String dateFormat){
		 
		if(dateToValidate == null || "".equals(dateToValidate)){
			return false;
		}
 
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
 
		try {
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			//System.out.println(date);
 
		} catch (ParseException e) {
			return false;
		}
 
		return true;
	}
}
