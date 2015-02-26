package com.gcs.servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;

import com.gcs.beans.Cliente;
import com.gcs.beans.ConectividadProyecto;
import com.gcs.beans.ContadorCliente;
import com.gcs.beans.ContadorDemanda;
import com.gcs.beans.Equipo;
import com.gcs.beans.FechaCalendada;
import com.gcs.beans.Pais;
import com.gcs.beans.ProductoProyecto;
import com.gcs.beans.Proyecto;
import com.gcs.beans.ServicioFile;
import com.gcs.beans.User;
import com.gcs.beans.Servicio;
import com.gcs.beans.Conectividad;
import com.gcs.beans.Coste;
import com.gcs.beans.Demanda;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ConectividadProyectoDao;
import com.gcs.dao.ContadorClienteDao;
import com.gcs.dao.ContadorDemandaDao;
import com.gcs.dao.EquipoDao;
import com.gcs.dao.FechaCalendadaDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ProductoProyectoDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioFileDao;
import com.gcs.dao.UserDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.CosteDao;
import com.gcs.dao.DemandaDao;
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
				}else if ("def_counter_services".equals(accion)){
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
				}else if ("users".equals(accion)){
					result = loadUsuarios(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}else if ("servicios".equals(accion)){
					result = loadServicios(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}else if ("conectividad".equals(accion)){
					result = loadConectividad(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}else if ("coste".equals(accion)){
					result = loadCoste(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}else if ("demanda".equals(accion)){
					result = loadDemanda(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}else if ("paises".equals(accion)){
					result = loadPaises(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}else if ("producto".equals(accion)){
					result = loadProductoProyecto(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}else if ("conectividadProyecto".equals(accion)){
					result = loadConectividadProyecto(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}
				else if ("serviciosFile".equals(accion)){
					result = loadServiciosFile(req,resp, usermail);
					json.append("success", "true");
					json.append("result", result);
				}/*else if ("borrarclientes".equals(accion)){
					json.append("success", "true");
					json.append("result", result);
				}*/
			}
			else {
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
	
	/*private void borrarClientes(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		boolean save = false;
		String saveParam = req.getParameter("save"); 
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
			try {	
				String link ="";
				InputStream stream = this.getServletContext().getResourceAsStream(link);
				BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

						
				ClienteDao clientesDao = ClienteDao.getInstance();
				Cliente cliente = null;
				clientesDao.deleteClientAll(usermail);
				
			}catch (Exception e) {
				String result = "";
				e.printStackTrace();
				result = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e);
		}
		
		
		
	}*/
	
	private String loadClientes(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{

		boolean save = false;
		String saveParam = req.getParameter("save"); 
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
		String link = "/datadocs/clientes_2015_01_28.csv";
		
		String result = "";
		try {
			
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			ClienteDao clientesDao = ClienteDao.getInstance();
			
			String deleteParam = req.getParameter("delete"); 
			
			boolean delete = false;
			 
			if(deleteParam != null && deleteParam.equals("true")) {
				clientesDao.deleteClientAll(usermail);
			}
			Cliente cliente = null;
			int counter = 0;
			boolean error = false;

			while ((inputLine = in.readLine()) != null) {
				error = false;
				
				String line = inputLine;
				String[] clienteSplit = line.split(";", -1);

				boolean procesar = true;
				if (clienteSplit.length < 7) {
					procesar = false;
				}
				String nombre= clienteSplit[0];
				if (esNuloVacio(nombre)) {
					procesar = false;
					break;
				}

				if (procesar) {
					String clientId= clienteSplit[1];
					String criticidad = clienteSplit[2];
					String str_fecha_alta_cliente = clienteSplit[3];
					String logo_url = clienteSplit[4];
					String ref_global = clienteSplit[5];
					String tipo = clienteSplit[6];
					String[] paises = clienteSplit[7].split(",");
					Set<String> paises_set = new HashSet<String>();
							for (String paisLista : paises){
								paises_set.add(paisLista.trim());
						}							
					
					
					cliente = clientesDao.getClienteByName(nombre);
					List<Cliente> clientes = clientesDao.getAllClientes();
					Boolean exist_client = false;
					
					for (Cliente cl:clientes){
						if (cl.getNombre().toLowerCase().equals(nombre.toLowerCase())){
							exist_client = true;
						}
					}
										
					if (exist_client){
						result += "Error\r\nYa existe un cliente con este nombre \r\n";		
						error = true;
					}
					
					cliente = new Cliente();		
					if(ref_global!=""){
						cliente.setRef_global(ref_global); 
						
					}
				
					cliente.setClientId(clientId);
					
					if(!esNuloVacio(nombre)) {
						cliente.setNombre(nombre);
					}
					else {
						result += "Error\r\nError nombre \r\n";
						error = true;
					}
					
					// TODO: check 
					if(!esNuloVacio(criticidad)) {
						cliente.setCriticidad(criticidad);
					}
					else {
						result += "Error\r\nError Criticidad \r\n";
						error = true;
					}
					
					
					if (isThisDateValid(str_fecha_alta_cliente, "dd/MM/yyyy")) {
							cliente.setStr_fecha_alta_cliente(str_fecha_alta_cliente);
							cliente.setFecha_alta_cliente(Utils.dateConverter(str_fecha_alta_cliente));
						}
						else {
							result += "Error\r\nError fecha alta \r\n";
							error = true;
						}
					cliente.setFecha_alta_cliente(Utils.dateConverter(str_fecha_alta_cliente));
					cliente.setLogo_url(logo_url);
					if(!esNuloVacio(tipo)) {
						cliente.setTipo(tipo);
					}
					else {
						result += "Error\r\nError Tipo \r\n";
						error = true;
					}
					if(paises_set.size() > 0) {
						cliente.setPaises(paises_set);
					}
					else {
						result += "Error\r\nError Paises\r\n";
						error = true;
					}
										
					if(!error) {
						result += cliente.toString() + "\r\n\r\n";
					}
					else {
						result += "\r\n";
					}
					
					if(save) {
						clientesDao.createClienteRaw(cliente);
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
		
		String contParam = req.getParameter("counter"); 
		if(contParam != null && contParam.equals("true")) {
			ClienteDao clienteDao = ClienteDao.getInstance();
			clienteDao.initCounters();
		}
		

		//"/datadocs/proyectos_2014_11_13.csv"
		String link = req.getParameter("link");
		
		String result = "";
		try {
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			ProyectoDao proyectoDao = ProyectoDao.getInstance();
			
			String deleteParam = req.getParameter("delete"); 
			
			boolean delete = false;
			 
			if(deleteParam != null && deleteParam.equals("true")) {
				proyectoDao.deleteAllProject(usermail);
			}

			
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
				String codigo = proyectoSplit[1];
				if (esNuloVacio(codigo)) {
					procesar = false;
					break;
				}

				if (procesar) {
					String fechaAlta = proyectoSplit[0];
					String clienteKey = proyectoSplit[4];
					String clienteName = proyectoSplit[5];
					String tipo = proyectoSplit[2];
					String subtipo = proyectoSplit[3];
					String producto = proyectoSplit[13];
					String conectividad = proyectoSplit[14];
					String gestorItKey = proyectoSplit[7];
					String gestorItName = proyectoSplit[8];
					String gestorNegocioKey = proyectoSplit[9];
					String gestorNegocioName = proyectoSplit[10];
					String clasificacion = proyectoSplit[6];
					String coste = proyectoSplit[11];
					String inicioEspecificaciones = proyectoSplit[15];
					String finEspecificaciones = proyectoSplit[16];
					String c100 = proyectoSplit[19];
					String okNegocio = proyectoSplit[20];
					String solicitudViabilidad = proyectoSplit[17];
					String finViabilidad = proyectoSplit[18];
					String fechaPlanTrabajo = proyectoSplit[21];
					String fechaDisponibleConect = proyectoSplit[22];

					proyecto = new Proyecto();
					
					result += counter + " :\r\n";
					
					proyecto.setStr_envioC100(c100);
					proyecto.setStr_OKNegocio(okNegocio);
					proyecto.setClienteName(clienteName);
					proyecto.setStr_fecha_fin_valoracion(finEspecificaciones);
					proyecto.setStr_fecha_inicio_valoracion(inicioEspecificaciones);
					proyecto.setGestor_negocio_name(gestorNegocioName);
					proyecto.setGestor_it_name(gestorItName);
					proyecto.setStr_fecha_fin_viabilidad(finViabilidad);
					proyecto.setStr_fecha_inicio_viabilidad(solicitudViabilidad);
					proyecto.setStr_fecha_disponible_conectividad(fechaDisponibleConect);
					proyecto.setStr_fecha_plan_trabajo(fechaPlanTrabajo);
					proyecto.setCod_proyecto(codigo);
					
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
						ClienteDao cliDao = ClienteDao.getInstance();

						List<Cliente> clientesForId =cliDao.getClientesByNameLow(clienteName);
						if(clientesForId.size()==1){
							long clienteKeyLong = clientesForId.get(0).getKey().getId();
							proyecto.setClienteKey(clienteKeyLong);
							proyecto.setClienteName(clientesForId.get(0).getNombre());
						}else{
							result += "Error cliente \r\n";
							error = true;	
						}
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
						UserDao usrDao = UserDao.getInstance();

						String []gestorItComplete = gestorItName.split(" ", -1);
						for(int i=0;i<gestorItComplete.length;i++){
							String aux = gestorItComplete[i];
							gestorItComplete[i] = aux.replace("-", " ");
						}
 
						List<User> usuariosforid = usrDao.getUsersByCompleteName(gestorItComplete);
						if(usuariosforid.size()==1){
							long usuarioid = usuariosforid.get(0).getKey().getId();
							proyecto.setGestor_it(usuarioid);
							proyecto.setGestor_it_name(usuariosforid.get(0).getNombre()+" "+usuariosforid.get(0).getApellido1()+" "+usuariosforid.get(0).getApellido2());
						}else{
							result += "Error gestor it \r\n";
							error = true;
						}
					}
					if(!esNuloVacio(gestorNegocioKey)) {
						proyecto.setGestor_negocio(Long.parseLong(gestorNegocioKey));
					}
					else {	
						UserDao usrDao = UserDao.getInstance();

						String []gestorNegComplete = gestorNegocioName.split(" ", -1);
						
						for(int i=0;i<gestorNegComplete.length;i++){
							String aux = gestorNegComplete[i];
							gestorNegComplete[i] = aux.replace("-", " ");
						}

						List<User> usuariosforid = usrDao.getUsersByCompleteName(gestorNegComplete);
						if(usuariosforid.size()==1){
							long usuarioid = usuariosforid.get(0).getKey().getId();
							proyecto.setGestor_negocio(usuarioid);
							proyecto.setGestor_negocio_name(usuariosforid.get(0).getNombre()+" "+usuariosforid.get(0).getApellido1()+" "+usuariosforid.get(0).getApellido2());
						}else{
							result += "Error gestor negocio \r\n";
							error = true;
						}
					}
					proyecto.setCoste(coste);
					//proyecto.setUrl_doc_google_drive(url_doc_google_drive);
					
					if(!error) {
						result += proyecto.toString() + "\r\n\r\n";
					}
					else {
						result += "\r\n";
					}
					
					if(save&&!error) {
						proyectoDao.createProjectImport(proyecto, usermail);
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
	
	private String loadUsuarios(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		boolean save = false;
		String saveParam = req.getParameter("save");
		String deleteParam = req.getParameter("delete"); 
		
		boolean delete = false;
		 
		if(deleteParam != null && deleteParam.equals("true")) {
			delete = true;
		}
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
		String link = "/datadocs/users_2015_02_05.csv";
		
		String result = "";
		try {
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			UserDao userDao = UserDao.getInstance();
			if(delete){
				userDao.deleteAll();
			}
			User user = null;
			
			int counter = 0;
			boolean error = false;

			while ((inputLine = in.readLine()) != null) {
				error = false;
				
				String line = inputLine;
				String[] userSplit = line.split(";", -1);

				boolean procesar = true;
				if (userSplit.length < 8) {
					procesar = false;
				}
				String nombre = userSplit[0];
				if (esNuloVacio(nombre)) {
					procesar = false;
					break;
				}

				if (procesar) {
					
					String apellido1 = userSplit[1];
					String apellido2 = userSplit[2];
					int perm1= 0;
					String permiso = Integer.toString(perm1); 
					permiso = userSplit[3];
					String permisoStr = userSplit[4];
					String departamento = userSplit[5];
					String email = userSplit[6];
					email.replace(" ", "");
					Boolean activo = true;
					
					String activo1 = userSplit[7];
					
					
					user = new User();		
					 
					if(!esNuloVacio(nombre)) {
						user.setNombre(nombre);
					}
					else {
						result += "Error Falta Nombre \r\n";
						error = true;
					}	
						if(!esNuloVacio(apellido1)) {
							user.setApellido1(apellido1);
						}
						else {
							result += "Error Falta Apellido1 \r\n";
							error = true;
						}	
												
						if(!esNuloVacio(email)) {
							user.setEmail(email);
						}
						else {
							result += "Error Falta email \r\n";
							error = true;
						}
						
						
						if(!esNuloVacio(departamento)) {
							user.setDepartamento(departamento);
						}
						else {
							result += "Error Falta Departamento \r\n";
							error = true;
						}	
						
						if(!esNuloVacio(permisoStr)) {
							user.setPermisoStr(permisoStr);
						}
						else {
							result += "Error Falta Permiso de Perfil \r\n";
							error = true;
						}
						if(activo1.equals("false")||activo1.equals("FALSE"))activo=false;
						user.setPermiso(Integer.parseInt(permiso));
						user.setApellido2(apellido2);
						user.setActivo(activo);
						user.setErased(false);
										
					if(!error) {
						result += user.toString() + "\r\n\r\n";
					}
					else {
						result += "\r\n";
					}
					
					if(save) {
						userDao.createUser(user, usermail);
					}
				}
				counter++;
			}
		}catch (Exception e) {
			e.printStackTrace();
			result = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e);
		}
		User user = new User();
		user.setActivo(true);
		user.setErased(false);
		user.setPermiso(1);
		user.setPermisoStr("1");
		user.setEmail("david.martin.beltran.contractor@bbva.com");
		user.setNombre("david");
		UserDao userDao = UserDao.getInstance();
		userDao.createUser(user, usermail);
		
		return result;
					
				
				
	}
	private String loadServiciosFile(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		String result = "";
		String link = "/datadocs/serviceFiles____.csv";
		
		try{
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));
			PaisDao paisDao = PaisDao.getInstance();
			String inputLine = new String();
			ServicioFileDao servFileDao = ServicioFileDao.getInstance();
			
			while ((inputLine = in.readLine()) != null) {
				ServicioFile servFile = new ServicioFile();
				String line = inputLine;
				String[] servicioSplit = line.split(";", -1);
				String servName = servicioSplit[0];
				String paisName = servicioSplit[1];
				ArrayList <String> extensiones = new ArrayList<String>();
				for (int i = 2 ; i<servicioSplit.length;i++){
					extensiones.add(servicioSplit[i]);
				}
				
				List<Pais> paisesList = paisDao.getPaisesByName(paisName);
				Pais pais = paisesList.get(0);
				
				servFile.setPaisId(pais.getKey().getId());
				servFile.setName(servName);
				servFile.setExtensiones(extensiones);
				servFileDao.createServicioFile(servFile);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			result = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e);
		}
		return result;
	}
	
	private String loadPaises(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		String result = "";
		String link = "/datadocs/pais____.csv";
		
		try{
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));
			PaisDao paisDao = PaisDao.getInstance();
			paisDao.deleteAll();
			String inputLine = new String();
			
			while ((inputLine = in.readLine()) != null) {
				String line = inputLine;

				if (!line.equals("")&&!line.equals(null)){
					Pais pais = new Pais();
					pais.setNme(inputLine);
					paisDao.createPais(pais);
				}
				
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			result = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e);
		}
		return result;
	}
	
	private String loadProductoProyecto(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		String result = "";
		String link = "/datadocs/producto.csv";
		
		try{
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));
			ProductoProyectoDao ProductoProyectoDao = com.gcs.dao.ProductoProyectoDao.getInstance();
			ProductoProyectoDao.deleteAll();
			String inputLine = new String();
			
			while ((inputLine = in.readLine()) != null) {
				String line = inputLine;

				if (!line.equals("")&&!line.equals(null)){
					ProductoProyecto productoProyecto = new ProductoProyecto();
					productoProyecto.setNme(inputLine);
					ProductoProyectoDao.createProductoProyecto(productoProyecto);
				}
				
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			result = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e);
		}
		return result;
	}
	
	private String loadConectividadProyecto(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		String result = "";
		String link = "/datadocs/conectividad_proyecto.csv";
		
		try{
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));
			ConectividadProyectoDao ConectividadProyectoDao = com.gcs.dao.ConectividadProyectoDao.getInstance();
			ConectividadProyectoDao.deleteAll();
			String inputLine = new String();
			
			while ((inputLine = in.readLine()) != null) {
				String line = inputLine;

				if (!line.equals("")&&!line.equals(null)){
					String[] split = line.split(";", -1);
					ConectividadProyecto conectividadProyecto = new ConectividadProyecto();
					conectividadProyecto.setName(split[0]);
					ProductoProyectoDao produDao = ProductoProyectoDao.getInstance();
					List<ProductoProyecto> productos = produDao.getProductoProyectoesByName(split[1]);
					if(productos.size()==1){
						conectividadProyecto.setProductoId(productos.get(0).getKey().getId());
						ConectividadProyectoDao.createConectividadProyecto(conectividadProyecto);
					}
					
					
				}
				
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			result = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e);
		}
		return result;
	}
	
	private String loadServicios(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		boolean save = false;
		String saveParam = req.getParameter("save"); 
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
		String link = "/datadocs/servicio____.csv";
		String linkParam = req.getParameter("link"); 
		if(linkParam != null ) {
			link = linkParam;
		}
		String result = "";
		try {
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			
			ServicioDao servicioDao = ServicioDao.getInstance();
			Servicio servicio = null;
			
			int counter = 1;
			boolean error = false;

			while ((inputLine = in.readLine()) != null) {
				error = false;
				
				String line = inputLine;
				String[] servicioSplit = line.split(";", -1);
				
				
				boolean procesar = true;
				if (servicioSplit.length < 22) {
					procesar = false;
				}
				
				

				if (procesar) {
					
					String cod_proyect = servicioSplit[0];
					String pais = servicioSplit[1];
					String servicio1 = servicioSplit[2];
					String estado = servicioSplit[3];
					String cod_servicio = servicioSplit[4];
					String observaciones = servicioSplit[5];
					String formato_intermedio = servicioSplit[6];	
					String formato_local = servicioSplit[7];
					String referencia_local1 = servicioSplit[8];
					String referencia_local2 = servicioSplit[9];
					String str_fecha_ini_integradas = servicioSplit[20];
					String str_fecha_fin_integradas = servicioSplit[21];
					String str_fecha_ini_aceptacion = servicioSplit[10];
					String str_fecha_fin_aceptacion = servicioSplit[11];
					String str_fecha_ini_validacion = servicioSplit[12];
					String str_fecha_fin_validacion = servicioSplit[13];
					String str_fecha_implantacion_prod = servicioSplit[14];
					String str_fecha_ini_primera_op = servicioSplit[15];
					String str_fecha_fin_primera_op = servicioSplit[16];
					String str_fecha_ans = servicioSplit[17];
					String str_fecha_inicio_pruebas = servicioSplit[18];
					String str_fecha_fin_pruebas = servicioSplit[19];

								
						servicio = new Servicio();		
						
						if(!esNuloVacio(estado)) {
							servicio.setEstado(estado);
						}
						else {
							result += "Error Falta Estado \r\n";
							error = true;
						}	
						
						if(!esNuloVacio(servicio1)) {
							servicio.setServicio(servicio1);
						}
						else {
							result += "Error Falta Servicio \r\n";
							error = true;
						}	
												
						if(!esNuloVacio(cod_servicio)) {
							servicio.setCod_servicio(cod_servicio);
						}
						else {
							result += "Error Falta Codigo de Servicio \r\n";
							error = true;
						}
						
						if(!esNuloVacio(pais)) {
							servicio.setPais(pais);
						}
						else {
							result += "Error Falta Pais \r\n";
							error = true;
						}
						
						if(!esNuloVacio(cod_proyect)) {
							ProyectoDao proyDao = ProyectoDao.getInstance();

							List<Proyecto> proyectosForId =proyDao.getProjectsByCode(cod_proyect);
							if(proyectosForId.size()==1){
								ClienteDao clientDao = ClienteDao.getInstance();
								try{
									Cliente c = clientDao.getClienteById(proyectosForId.get(0).getClienteKey());
								}catch(Exception e){
									result += "Error codigo proyecto no exist cliente\r\n";
								}
			
								servicio.setId_proyecto(proyectosForId.get(0).getKey().getId());
								servicio.setCod_proyecto(proyectosForId.get(0).getCod_proyecto());
							}else {
								result += "Error codigo proyecto:"+counter+" \r\n";
								error = true;	
							}
						}
						
						servicio.setObservaciones(observaciones);
						servicio.setFormato_intermedio(formato_intermedio);
						servicio.setFormato_local(formato_local);
						servicio.setReferencia_local1(referencia_local1);
						servicio.setReferencia_local2(referencia_local2);
						servicio.setStr_fecha_ini_integradas(str_fecha_ini_integradas);
						servicio.setStr_fecha_fin_integradas(str_fecha_fin_integradas);
						servicio.setStr_fecha_ini_aceptacion(str_fecha_ini_aceptacion);
						servicio.setStr_fecha_fin_aceptacion(str_fecha_fin_aceptacion);
						servicio.setStr_fecha_ini_validacion(str_fecha_ini_validacion);
						servicio.setStr_fecha_fin_validacion(str_fecha_fin_validacion);
						servicio.setStr_fecha_implantacion_produccion(str_fecha_implantacion_prod);
						servicio.setStr_fecha_ini_primera_operacion(str_fecha_ini_primera_op);
						servicio.setStr_fecha_fin_primera_operacion(str_fecha_fin_primera_op);
						servicio.setStr_fecha_ANS(str_fecha_ans);
						servicio.setStr_fecha_ini_pruebas(str_fecha_inicio_pruebas);
						servicio.setStr_fecha_fin_pruebas(str_fecha_fin_pruebas);
						//servicio.setStr_migracion_channeling(str_fecha_mig_channeling);
						//servicio.setStr_migracion_infra(str_fecha_mig_infraestructura);
						
							
					
					if(save&&!error) {
						servicioDao.createServicio(servicio, usermail);
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
		
	private String loadConectividad(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		boolean save = false;
		String saveParam = req.getParameter("save"); 
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
		String link = "/datadocs/conectividad____.csv";
		
		String linkParam = req.getParameter("link");
		if(linkParam != null) {
			link = linkParam;
		}
		String result = "";
		try {
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			
			ConectividadDao conectividadDao = ConectividadDao.getInstance();
			Conectividad conectividad = null;
			
			int counter = 1;
			boolean error = false;

			while ((inputLine = in.readLine()) != null) {
				error = false;
				result += counter +":   \r\n";
				String line = inputLine;
				String[] conectividadSplit = line.split(";", -1);

				boolean procesar = true;
				if (conectividadSplit.length < 11) {
					procesar = false;
				}


				if (procesar) {
					String cod_proyect = conectividadSplit[0];
					String fecha_ini_infra = conectividadSplit[1];
					String fecha_fin_infra = conectividadSplit[2];
					String fecha_implantacion = conectividadSplit[3];
					String ini_seguridad = conectividadSplit[5];
					String fin_seguridad = conectividadSplit[6];
					String seguridad = conectividadSplit[4];
					String firewall = null;
					String fin_certificado = null;
					String fin_conectividad = null;
					String estado = conectividadSplit[7];
					
												
						conectividad = new Conectividad();		
						
						if(!esNuloVacio(estado)) {
							conectividad.setEstado(estado);
						}
						else {
							result += "Error Falta Estado \r\n";
							error = true;
						}	

						if(!esNuloVacio(cod_proyect)) {
							ProyectoDao proyDao = ProyectoDao.getInstance();

							List<Proyecto> proyectosForId =proyDao.getProjectsByCode(cod_proyect);
							if(proyectosForId.size()==1){
								ClienteDao clientDao = ClienteDao.getInstance();
								try{
									Cliente c = clientDao.getClienteById(proyectosForId.get(0).getClienteKey());
								}catch(Exception e){
									result += "Error codigo proyecto no exist cliente\r\n";
								}
								
								conectividad.setKey_proyecto(proyectosForId.get(0).getKey().getId());
								conectividad.setNombre_proyecto(proyectosForId.get(0).getCod_proyecto());
							}else {
								result += "Error codigo proyecto \r\n";
								error = true;	
							}
						}
						else {
								result += "Error codigo proyecto \r\n";
								error = true;	
						}
						
						conectividad.setStr_reglas_firewall(firewall);
						conectividad.setStr_fecha_fin_certificado(fin_certificado);
						conectividad.setStr_fecha_fin_conectividad(fin_conectividad);
						conectividad.setStr_fecha_fin_infraestructura(fecha_fin_infra);
						conectividad.setStr_fecha_implantacion(fecha_implantacion);
						conectividad.setStr_fecha_ini_infraestructura(fecha_ini_infra);
						conectividad.setStr_fecha_fin_seguridad(fin_seguridad);
						conectividad.setStr_fecha_ini_seguridad(ini_seguridad);
						conectividad.setSeguridad(seguridad);
						
							
					
					if(save&&!error) {
						conectividadDao.createConectividadRaw(conectividad);
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
	
	private String loadCoste(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		
		boolean save = false;
		String saveParam = req.getParameter("save"); 
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
		
		boolean delete = false;
		String deleteParam = req.getParameter("delete"); 
		if(deleteParam != null && deleteParam.equals("true")) {
			delete = true;
		}
		
		String link = "/datadocs/coste____.csv";
		
		String linkParam = req.getParameter("link"); 
		if(linkParam != null) {
			link = linkParam;
		}
		
		
		
		String result = "";
		try {
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			
			CosteDao costeDao = CosteDao.getInstance();
			Coste coste = null;
			
			if(delete==true){
				costeDao.deleteAllCostes(usermail);
			}
			
			int counter = 1;
			boolean error = false;

			while ((inputLine = in.readLine()) != null) {
				error = false;
				
				String line = inputLine;
				String[] costeSplit = line.split(";", -1);

				boolean procesar = true;
				if (costeSplit.length < 21) {
					procesar = false;
				}


				if (procesar) {
					String cliente =costeSplit[0];
					String cod_proyecto = costeSplit[1];
					String num_control = costeSplit[2];
					String equipo = costeSplit[3];
					String fecha_alta_costes = costeSplit[4];
					String gestor_it_name = costeSplit[5];
					String num_valoracion = costeSplit[6];
					String comentarios = costeSplit[7];
					String fecha_solicitud_valoracion = costeSplit[8];
					String fecha_recepcion_valoracion = costeSplit[9];
					
					String analisis_horas = costeSplit[10];	
					String analisis_coste = costeSplit[11];
					String diseño_horas = costeSplit[12];	
					String diseño_coste = costeSplit[13];
					String construccion_horas = costeSplit[14];
					String construccion_coste = costeSplit[15];
					String pruebas_horas = costeSplit[16];
					String pruebas_costes = costeSplit[17];					
					String gestion_horas = costeSplit[18];
					String gestion_coste = costeSplit[19];
					String total_horas = costeSplit[20];
					String total_coste = costeSplit[21];
						
					coste = new Coste();	
					
					
					coste.setHoras_pruebas(pruebas_horas);
					coste.setCoste_pruebas(pruebas_costes);
					coste.setCoste_total(total_coste);
					coste.setCoste_analisis(analisis_coste);
					coste.setHoras_analisis(analisis_horas);
					coste.setComentarios(comentarios);
					coste.setCoste_construccion(construccion_coste);
					coste.setHoras_construccion(construccion_horas);
					coste.setHoras_diseño(diseño_horas);
					coste.setCoste_diseño(diseño_coste);
					coste.setHoras_gestion(gestion_horas);
					coste.setCoste_gestion(gestion_coste);
					coste.setNum_control(num_control);
					coste.setStr_fecha_recepcion_valoracion("");

					ClienteDao cliDao = ClienteDao.getInstance();
					List<Cliente> clientesForId =cliDao.getClientesByNameLow(cliente);
					if(clientesForId.size()==1){
						long clienteKeyLong = clientesForId.get(0).getKey().getId();
						coste.setClienteKey(clienteKeyLong);
						coste.setCliente_name(clientesForId.get(0).getNombre());
					}else{
						result += "Error cliente \r\n";
						error = true;	
					}

					
					if (isThisDateValid(fecha_alta_costes, "dd/MM/yyyy")) {
						coste.setStr_fecha_alta(fecha_alta_costes);
						coste.setFecha_alta(Utils.dateConverter(fecha_alta_costes));
					}
					else {
						result += "Error fecha alta  costes \r\n";
						error = true;
					}
					
					if (isThisDateValid(fecha_solicitud_valoracion, "dd/MM/yyyy")) {
						coste.setStr_fecha_solicitud_valoracion(fecha_solicitud_valoracion);
						coste.setFecha_solicitud_valoracion(Utils.dateConverter(fecha_solicitud_valoracion));
					}
					else {
						result += "Error fecha de solicitud de valoración \r\n";
						error = true;
					}
					
					if (isThisDateValid(fecha_recepcion_valoracion, "dd/MM/yyyy")) {
						coste.setStr_fecha_solicitud_valoracion(fecha_solicitud_valoracion);
						coste.setFecha_solicitud_valoracion(Utils.dateConverter(fecha_solicitud_valoracion));
					}
					else {
//						result += "Fecha recepcion invalida"
//								+ " \r\n";
						
					}
						
					
					UserDao usrDao = UserDao.getInstance();
						String []gestorItComplete = gestor_it_name.split(" ", -1);
					for(int i=0;i<gestorItComplete.length;i++){
						String aux = gestorItComplete[i];
					gestorItComplete[i] = aux.replace("-", " ");
					}

					List<User> usuariosforid = usrDao.getUsersByCompleteName(gestorItComplete);
					if(usuariosforid.size()==1){
						long usuarioid = usuariosforid.get(0).getKey().getId();
						coste.setGestor_it_key(usuarioid);
						coste.setGestor_it_name(usuariosforid.get(0).getNombre()+" "+usuariosforid.get(0).getApellido1()+" "+usuariosforid.get(0).getApellido2());
					}else{
						result += "Error gestor it \r\n";
						error = true;
					}
					
					if(!esNuloVacio(cod_proyecto)) {
						ProyectoDao proyDao = ProyectoDao.getInstance();

						List<Proyecto> proyectosForId =proyDao.getProjectsByCode(cod_proyecto);
						if(proyectosForId.size()==1){
							ClienteDao clientDao = ClienteDao.getInstance();
							Cliente c = null; 
							try{
								c = clientDao.getClienteById(proyectosForId.get(0).getClienteKey());
							}catch(Exception e){
								result += "Error codigo proyecto no exist cliente\r\n";
							}
							if(c.getKey().getId()!=coste.getClienteKey()){
								result += "Error codigo proyecto no corresponde al cliente especificado\r\n";
							}
							coste.setProjectKey(proyectosForId.get(0).getKey().getId());
							coste.setProject_name(proyectosForId.get(0).getCod_proyecto());
						}else {
							result += "Error codigo proyecto:"+ counter +" \r\n";
							error = true;	
						}
					}
					
					if(!esNuloVacio(equipo)) {
						coste.setEquipos(equipo);}
					else {
						result += "Error falta el equipo \r\n";
						error = true;
						}	
					if(!esNuloVacio(total_horas)) {
						coste.setHoras_total(total_horas);}
						else {
						result += "Error falta el total de horas \r\n";
						error = true;
						}	
					if(!esNuloVacio(num_valoracion)) {
						coste.setNum_valoracion(num_valoracion);}
						else {
						result += "Error falta el número de valoración \r\n";
						error = true;
						}
											
				
					if(save&&!error) {
						costeDao.createCoste(coste, usermail);
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
	
	private String loadDemanda(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		boolean save = false;
		String saveParam = req.getParameter("save"); 
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
		boolean delete = false;
		String deleteParam = req.getParameter("delete"); 
		if(deleteParam != null && deleteParam.equals("true")) {
			delete = true;
		}
		String link = "/datadocs/demanda____.csv";
		String linkParam = req.getParameter("link"); 
		if(linkParam != null ) {
			link = linkParam;
		}
		String result = "";
		try {
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			
			DemandaDao demandaDao = DemandaDao.getInstance();
			if (delete)demandaDao.deleteAllDemandas();
			
			Demanda demanda = null;
			
			int counter = 0;
			boolean error = false;

			while ((inputLine = in.readLine()) != null) {
				error = false;
				
				String line = inputLine;
				String[] demandaSplit = line.split(";", -1);

				boolean procesar = true;



				if (procesar) {
					
					String demandaId = demandaSplit[0];
					String clientKey = demandaSplit[1];
					String clientName = demandaSplit[2];
					String tipo = demandaSplit[3];
					String estado = demandaSplit[4];
					String fecha_entrada_str1 = demandaSplit[5];
					String hora_entrada_str1 = demandaSplit[6];
					String fecha_entrada_str = fecha_entrada_str1 +" "+ hora_entrada_str1;
					String motivo_catalogacion = demandaSplit[7];
					String comentarios = demandaSplit[8];
					String gestor_negocio_key = demandaSplit[9];
					String gestor_negocio_name = demandaSplit[10];
					
					String fecha_solicitud_str1 =  demandaSplit[11];
					String hora_solicitud_str1 = demandaSplit[12];
					String fecha_solicitud_str = fecha_solicitud_str1 +" "+ hora_solicitud_str1;
					
					boolean devuelta = false;
					String devuelta_str = demandaSplit[13];
					if(devuelta_str.equals("SI"))devuelta = true;
					
					String gestor_it_key = demandaSplit[14];
					String gestor_it_name = demandaSplit[15];
					String catalogacion_peticion = demandaSplit[16];
					
					String fecha_comunicacion_str1 =  demandaSplit[17];
					String hora_comunicacion_str1 = demandaSplit[18];
					String fecha_comunicacion_str = fecha_comunicacion_str1 +" "+ hora_comunicacion_str1;
					
					demanda = new Demanda();		
					result += counter + " :\r\n";
									
					
					demanda.setMotivo_catalogacion(motivo_catalogacion);
					demanda.setComentarios(comentarios);
					demanda.setCatalogacion(catalogacion_peticion);
					demanda.setCod_peticion(demandaId);
					
					
					
					if(!esNuloVacio(clientKey)) {
						demanda.setClientekey(Long.parseLong(clientKey));
					}
					else {
						ClienteDao cliDao = ClienteDao.getInstance();

						List<Cliente> clientesForId =cliDao.getClientesByNameLow(clientName);
						if(clientesForId.size()==1){
							long clienteKeyLong = clientesForId.get(0).getKey().getId();
							demanda.setClientekey(clienteKeyLong);
							demanda.setClienteName(clientesForId.get(0).getNombre());
						}else{
							result += "Error cliente \r\n";
							error = true;	
						}
					}
					
					if(!esNuloVacio(gestor_it_key)&&!gestor_it_key.equals("SI")) {
						demanda.setGestor_it(Long.parseLong(gestor_it_key));
					}
					else {
						UserDao usrDao = UserDao.getInstance();

						String []gestorItComplete = gestor_it_name.split(" ", -1);
						for(int i=0;i<gestorItComplete.length;i++){
							String aux = gestorItComplete[i];
							gestorItComplete[i] = aux.replace("-", " ");
						}
 
						List<User> usuariosforid = usrDao.getUsersByCompleteName(gestorItComplete);
						if(usuariosforid.size()==1){
							long usuarioid = usuariosforid.get(0).getKey().getId();
							demanda.setGestor_it(usuarioid);

						}else{
							result += "Error gestor it \r\n";
							error = true;
						}
					}
					
					
					if(!esNuloVacio(gestor_negocio_key)) {
						demanda.setGestor_negocio(Long.parseLong(gestor_negocio_key));
					}
					else {	
						UserDao usrDao = UserDao.getInstance();

						String []gestorNegComplete = gestor_negocio_name.split(" ", -1);
						
						for(int i=0;i<gestorNegComplete.length;i++){
							String aux = gestorNegComplete[i];
							gestorNegComplete[i] = aux.replace("-", " ");
						}

						List<User> usuariosforid = usrDao.getUsersByCompleteName(gestorNegComplete);
						if(usuariosforid.size()==1){
							long usuarioid = usuariosforid.get(0).getKey().getId();
							demanda.setGestor_negocio(usuarioid);
						}else{
							result += "Error gestor negocio \r\n";
							error = true;
						}
					}
					
					
					if(!esNuloVacio(fecha_entrada_str)) {
						Date fecha_entrada = null;
						try{
						fecha_entrada = Utils.dateConverterComplete(fecha_entrada_str);
						}catch(Exception e){
							result += "Error al convertir la fecha \r\n";
							error = true;
						}
						if(!error){
							demanda.setFecha_entrada_peticion(fecha_entrada);
							demanda.setHora_entrada_peticion(Utils.dateConverterToStrHour(fecha_entrada));
							demanda.setStr_fecha_entrada_peticion(Utils.dateConverterToStr(fecha_entrada));
						}
					}else {
						result += "Error Falta fecha \r\n";
						error = true;
					}
					
					if(!esNuloVacio(fecha_solicitud_str1)) {
						Date fecha_solicitud = null;
						try{
						fecha_solicitud = Utils.dateConverterComplete(fecha_solicitud_str);
						}catch(Exception e){
							result += "Error al convertir la fecha \r\n";
							error = true;
						}
						if(!error){
							demanda.setFecha_solicitud_asignacion(fecha_solicitud);
							demanda.setHora_solicitud_asignacion(Utils.dateConverterToStrHour(fecha_solicitud));
							demanda.setStr_fecha_solicitud_asignacion(Utils.dateConverterToStr(fecha_solicitud));
						}
					}else {

					}
					
					if(!esNuloVacio(fecha_comunicacion_str1)) {
						Date fecha_comunicacion = null;
						try{
						fecha_comunicacion = Utils.dateConverterComplete(fecha_comunicacion_str);
						}catch(Exception e){
							result += "Error al convertir la fecha \r\n";
							error = true;
						}
						if(!error){
							demanda.setFecha_comunicacion(fecha_comunicacion);
							demanda.setHora_comunicacion(Utils.dateConverterToStrHour(fecha_comunicacion));
							demanda.setStr_fecha_comunicacion(Utils.dateConverterToStr(fecha_comunicacion));
						}
					}else {

					}
					

					
					
					
					/*
					if (isThisDateValid(fecha_comunicacion_asignacion ,"dd/MM/yyyy")) {
						demanda.setStr_fecha_comunicacion_asignacion(fecha_comunicacion_asignacion);
						}
					else {
						result += "Error fecha solicitud asignación \r\n";
						error = true;
					}
					
					if (isThisDateValid(fecha_solicitud_asignacion ,"dd/MM/yyyy")) {
						demanda.setStr_fecha_solicitud_asignacion(fecha_solicitud_asignacion);
						}
					else {
						result += "Error fecha solicitud asignación \r\n";
						error = true;
					}
					if (isThisDateValid(fecha_entrada_peticion, "dd/MM/yyyy")) {
						demanda.setStr_fecha_entrada_peticion(fecha_entrada_peticion);
						}
					else {
						result += "Error fecha entrada peticion \r\n";
						error = true;
					}
					*/
	
					if(!esNuloVacio(estado)) {
						demanda.setEstado(estado);}
					else {
						result += "Error Falta Estado \r\n";
						error = true;
						}		
					
					if(!esNuloVacio(tipo)) {
						demanda.setTipo(tipo);}
					else {
						result += "Error Falta Tipo Peticion \r\n";
						error = true;
						}		
					if(!esNuloVacio(devuelta_str)) {
						demanda.setDevuelta(devuelta);}
					else {
						result += "Error Falta si esta o no devuelta \r\n";
						error = true;
						}		
					
					if(!error) {
						result += demanda.toString() + "\r\n\r\n";
					}
					else {
						result += "\r\n";
					}
					
					
					
					if(save&&!error) {
						demandaDao.createDemandaRaw(demanda);
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
