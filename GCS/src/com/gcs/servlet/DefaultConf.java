package com.gcs.servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


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
				}
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

			ClienteDao clienteDao = ClienteDao.getInstance();
			ProyectoDao proyectoDao = ProyectoDao.getInstance();
			UserDao userDao = UserDao.getInstance();

			List<User> gestores_it = userDao.getUsersByPermisoStr(3);
			List<User> gestores_negocio = userDao.getUsersByPermisoStr(4);

			Proyecto proyecto = null;
			User gestorItObj = null;
			User gestorNegocioObj = null;
			Cliente cliente = null;

			Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
			
			int counter = 0;

			while ((inputLine = in.readLine()) != null) {
				String line = inputLine;
				String[] proyectoSplit = line.split(";", -1);

				boolean procesar = true;
				if (proyectoSplit.length < 16) {
					procesar = false;
				}
				String codigo = proyectoSplit[0];
				if (codigo == null || codigo.equals("")) {
					procesar = false;
				}

				if (procesar) {
					String fechaAlta = proyectoSplit[1];
					String clienteName = proyectoSplit[2];
					String tipo = proyectoSplit[3];
					String producto = proyectoSplit[4];
					String conectividad = proyectoSplit[5];
					String gestorIt = proyectoSplit[6];
					String gestorNegocio = proyectoSplit[7];
					String clasificacion = proyectoSplit[8];
					String coste = proyectoSplit[9];
					String inicioEspecificaciones = proyectoSplit[10];
					String finEspecificaciones = proyectoSplit[11];
					String c100 = proyectoSplit[12];
					String okNegocio = proyectoSplit[13];
					String solicitudViabilidad = proyectoSplit[14];
					String finViabilidad = proyectoSplit[15];

					proyecto = new Proyecto();

					if (isThisDateValid(fechaAlta, "dd/MM/yyyy")) {
						proyecto.setFecha_alta_str(fechaAlta);
						proyecto.setFecha_alta(Utils.dateConverter(fechaAlta));
					}

					cliente = clienteDao.getClienteByName(clienteName);
					if (cliente != null) {
						proyecto.setClienteKey(cliente.getKey().getId());
						proyecto.setClienteName(cliente.getNombre());
					}

					proyecto.setTipo(tipo);
					proyecto.setProducto(producto);
					proyecto.setConectividad(conectividad);

					gestorItObj = findUserByName(gestores_it, gestorIt);
					if (gestorItObj != null) {
						proyecto.setGestor_it(gestorItObj.getKey().getId());
						proyecto.setGestor_it_name(gestorItObj.getFullName());
					}

					gestorNegocioObj = findUserByName(gestores_negocio,
							gestorNegocio);
					if (gestorNegocioObj != null) {
						proyecto.setGestor_negocio(gestorNegocioObj.getKey()
								.getId());
						proyecto.setGestor_negocio_name(gestorNegocioObj
								.getFullName());
					}

					proyecto.setClasificacion(Integer.parseInt(clasificacion));
					proyecto.setCoste(coste); // añadir decimales

					if (isThisDateValid(inicioEspecificaciones, "dd/MM/yyyy")) {
						proyecto.setStr_fecha_inicio_valoracion(inicioEspecificaciones);
						proyecto.setFecha_inicio_valoracion(Utils
								.dateConverter(inicioEspecificaciones));
					}

					if (isThisDateValid(finEspecificaciones, "dd/MM/yyyy")) {
						proyecto.setStr_fecha_fin_valoracion(finEspecificaciones);
						proyecto.setFecha_fin_valoracion(Utils
								.dateConverter(finEspecificaciones));
					}

					if (isThisDateValid(c100, "dd/MM/yyyy")) {
						proyecto.setStr_envioC100(c100);
						proyecto.setEnvioC100(Utils.dateConverter(c100));
					}
					if (isThisDateValid(okNegocio, "dd/MM/yyyy")) {
						proyecto.setStr_OKNegocio(okNegocio);
						proyecto.setOkNegocio(Utils.dateConverter(okNegocio));
					}

					if (isThisDateValid(solicitudViabilidad, "dd/MM/yyyy")) {
						proyecto.setStr_fecha_inicio_viabilidad(solicitudViabilidad);
						proyecto.setFecha_inicio_viabilidad(Utils
								.dateConverter(solicitudViabilidad));
					}

					if (isThisDateValid(finViabilidad, "dd/MM/yyyy")) {
						proyecto.setStr_fecha_fin_viabilidad(finViabilidad);
						proyecto.setFecha_fin_viabilidad(Utils
								.dateConverter(finViabilidad));
					}
					
					
					result += counter + " :\r\n" + proyecto.toString() + "\r\n\r\n";
					
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
