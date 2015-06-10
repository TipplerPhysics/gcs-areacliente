package com.gcs.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.gcs.beans.Cliente;
import com.gcs.beans.Conectividad;
import com.gcs.beans.ConectividadProyecto;
import com.gcs.beans.Coste;
import com.gcs.beans.Demanda;
import com.gcs.beans.Departamentos;
import com.gcs.beans.Equipo;
import com.gcs.beans.EstadoPeticion;
import com.gcs.beans.Estados;
import com.gcs.beans.Pais;
import com.gcs.beans.ProductoProyecto;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.beans.ServicioFile;
import com.gcs.beans.TipoPeticion;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.ConectividadProyectoDao;
import com.gcs.dao.CosteDao;
import com.gcs.dao.DemandaDao;
import com.gcs.dao.DepartamentosDao;
import com.gcs.dao.EquipoDao;
import com.gcs.dao.EstadoPeticionDao;
import com.gcs.dao.EstadosDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ProductoProyectoDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.ServicioFileDao;
import com.gcs.dao.TipoPeticionDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class MayusUpdate extends HttpServlet{
	private static final long serialVersionUID = 8059778774757795508L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession sesion = req.getSession();
		JSONObject json = new JSONObject();
		String result = "";

		String accion = req.getParameter("accion");

		HttpSession session = req.getSession();
		String usermail = (String) session.getAttribute("usermail");
		try {
			if ("cliente".equals(accion)) {
				result = updateCliente(req, resp,usermail);
				json.append("success", "true");
				json.append("result", result);
			} else if ("conectividad".equals(accion)) {
				result = updateConectividad(req, resp,usermail);
				json.append("success", "true");
				json.append("result", result);
				//
			} else if ("conectividadProyecto".equals(accion)) {
				result = updateConectividadProyecto(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("coste".equals(accion)) {
				result = updateCoste(req, resp, usermail);
				json.append("success", "true");
				json.append("result", result);
			} else if ("demanda".equals(accion)) {
				result = updateDemandas(req, resp, usermail);
				json.append("success", "true");
				json.append("result", result);
				//
			} else if ("departamentos".equals(accion)) {
				result = updateDepartamentos(req, resp);
				json.append("success", "true");
				json.append("result", result);
				//
			} else if ("equipo".equals(accion)) {
				result = updateEquipos(req, resp);
				json.append("success", "true");
				json.append("result", result);
				//
			} else if ("estadoPeticion".equals(accion)) {
				result = updateEstadoPeticion(req, resp);
				json.append("success", "true");
				json.append("result", result);
				//
			} else if ("estados".equals(accion)) {
				result = updateEstados(req, resp);
				json.append("success", "true");
				json.append("result", result);
				//
			}else if ("pais".equals(accion)) {
				result = updatePaiss(req, resp);
				json.append("success", "true");
				json.append("result", result);
			}else if ("productoProyecto".equals(accion)) {
				result = updateProductoProyectos(req, resp);
				json.append("success", "true");
				json.append("result", result);
			}else if ("proyecto".equals(accion)) {
				result = updateProyectos(req, resp);
				json.append("success", "true");
				json.append("result", result);
			}else if ("servicio".equals(accion)) {
				result = updateServicios(req, resp);
				json.append("success", "true");
				json.append("result", result);
			}else if ("servicioFile".equals(accion)) {
				result = updateServicioFile(req, resp);
				json.append("success", "true");
				json.append("result", result);
			}else if ("user".equals(accion)) {
				result = updateUser(req, resp,usermail);
				json.append("success", "true");
				json.append("result", result);
			}

			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/plain");
			resp.getWriter().println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}


	public String updateCliente(HttpServletRequest req,
			HttpServletResponse resp, String usermail) throws InterruptedException, IOException {

		String result = "";
		try {
			ClienteDao clientesDao = ClienteDao.getInstance();
			List<Cliente> clientes = clientesDao.getAllClientes();
			int from = Integer.parseInt(req.getParameter("from"));
			int to = Integer.parseInt(req.getParameter("to"));
			Cliente cliente = null;
			for (int i = from; i<to;i++) {
				cliente = clientes.get(i);
				cliente.setCriticidad(cliente.getCriticidad().toUpperCase());
				cliente.setNombre(cliente.getNombre().toUpperCase());
				cliente.setPaises(setToUppercase(cliente.getPaises()));
				clientesDao.createCliente(cliente, usermail);
				result += cliente.getNombre() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public List<String> arrayListToUppercase(List<String> variables){
		List<String> nuevaVariables = new ArrayList<String>();
		for(String variable:variables){nuevaVariables.add(variable.toUpperCase());}
		return nuevaVariables;
	}
	
	public Set<String> setToUppercase(Set<String> variables){
		Set<String> nuevaVariables = new HashSet<String>();
		for(String variable:variables){nuevaVariables.add(variable.toUpperCase());}
		return nuevaVariables;
	}

	public String updateConectividad(HttpServletRequest req,
			HttpServletResponse resp, String usermail) throws InterruptedException, IOException {

		String result = "";
		try {
			ConectividadDao conectividadDao = ConectividadDao.getInstance();
			List<Conectividad> conectividades = conectividadDao.getAllConectividades();
			int from = Integer.parseInt(req.getParameter("from"));
			int to = Integer.parseInt(req.getParameter("to"));
			Conectividad conectividad = null;
			
			for (int i = from; i<to;i++) {
				conectividad = conectividades.get(i);
				if(conectividad.getdetalleSubida()!=null)conectividad.setdetalleSubida(conectividad.getdetalleSubida().toUpperCase());
				if(conectividad.getEstado()!=null)conectividad.setEstado(conectividad.getEstado().toUpperCase());
				if(conectividad.getSeguridad()!=null)conectividad.setSeguridad(conectividad.getSeguridad().toUpperCase());
				conectividadDao.createConectividad(conectividad, usermail);
				result += conectividad.getdetalleSubida() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateConectividadProyecto(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			ConectividadProyectoDao conectividadProyectoDao = ConectividadProyectoDao.getInstance();
			List<ConectividadProyecto> conectividadesPry = conectividadProyectoDao.getAllConectividadProyectoes();

			for (ConectividadProyecto conectividad:conectividadesPry) {
				conectividad.setName(conectividad.getName().toUpperCase());
				conectividadProyectoDao.createConectividadProyecto(conectividad);
				result += conectividad.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateCoste(HttpServletRequest req,
			HttpServletResponse resp,String usermail) throws InterruptedException, IOException {
		String result = "";
		try {
			CosteDao CostesDao = CosteDao.getInstance();
			List<Coste> Costes = CostesDao.getAllCostes();
			int from = Integer.parseInt(req.getParameter("from"));
			int to = Integer.parseInt(req.getParameter("to"));
			Coste coste = null;
			for (int i = from; i<to;i++) {
				coste = Costes.get(i);
				if(coste.getCliente_name()!=null)coste.setCliente_name(coste.getCliente_name().toUpperCase());
				if(coste.getComentarios()!=null)coste.setComentarios(coste.getComentarios().toUpperCase());
				if(coste.getEquipos()!=null)coste.setEquipos(coste.getEquipos().toUpperCase());
				if(coste.getGestor_it_name()!=null)coste.setGestor_it_name(coste.getGestor_it_name().toUpperCase());
				CostesDao.createCosteRaw(coste);
				result += coste.getDetalle() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateDemandas(HttpServletRequest req,
			HttpServletResponse resp,String usermail) throws InterruptedException, IOException {

		String result = "";
		try {
			DemandaDao DemandasDao = DemandaDao.getInstance();
			List<Demanda> Demandas = DemandasDao.getAllDemandas();
			int from = Integer.parseInt(req.getParameter("from"));
			int to = Integer.parseInt(req.getParameter("to"));
			Demanda demanda = null;
			for (int i = from; i<to;i++) {
				demanda = Demandas.get(i);
				if(demanda.getCatalogacion()!=null)demanda.setCatalogacion(demanda.getCatalogacion().toUpperCase());
				if(demanda.getComentarios()!=null)demanda.setComentarios(demanda.getComentarios().toUpperCase());
				if(demanda.getDetalle()!=null)demanda.setDetalle(demanda.getDetalle().toUpperCase());
				if(demanda.getEstado()!=null)demanda.setEstado(demanda.getEstado().toUpperCase());
				if(demanda.getMotivo_catalogacion()!=null)demanda.setMotivo_catalogacion(demanda.getMotivo_catalogacion().toUpperCase());
				DemandasDao.createDemanda(demanda,usermail);
				result += demanda.getCod_peticion() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateDepartamentos(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			DepartamentosDao DepartamentossDao = DepartamentosDao.getInstance();
			List<Departamentos> Departamentoss = DepartamentossDao.getAllDepartamentos();

			for (Departamentos Departamentos : Departamentoss) {
				Departamentos.setNme(Departamentos.getName().toUpperCase());
				DepartamentossDao.createDepartamentos(Departamentos);
				result += Departamentos.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateEquipos(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			EquipoDao EquiposDao = EquipoDao.getInstance();
			List<Equipo> Equipos = EquiposDao.getAllEquipos();

			for (Equipo Equipo : Equipos) {
				Equipo.setNombre(Equipo.getNombre().toUpperCase());
				EquiposDao.createEquipo(Equipo);
				result += Equipo.getNombre() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateEstadoPeticion(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			EstadoPeticionDao EstadoPeticionsDao = EstadoPeticionDao.getInstance();
			List<EstadoPeticion> EstadoPeticions = EstadoPeticionsDao.getAllEstadoPeticion();

			for (EstadoPeticion EstadoPeticion : EstadoPeticions) {
				EstadoPeticion.setNme(EstadoPeticion.getName().toUpperCase());
				EstadoPeticionsDao.createEstadoPeticion(EstadoPeticion);
				result += EstadoPeticion.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateEstados(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			EstadosDao estadosDao = EstadosDao.getInstance();
			List<Estados> estados = estadosDao.getAllEstados();

			for (Estados estado : estados) {
				estado.setNme(estado.getName().toUpperCase());
				estadosDao.createEstados(estado);
				result += estado.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updatePaiss(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			PaisDao paissDao = PaisDao.getInstance();
			List<Pais> paiss = paissDao.getAllPaises();

			for (Pais pais : paiss) {
				pais.setNme(pais.getName().toUpperCase());
				paissDao.createPais(pais);
				result += pais.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	
	public String updateProductoProyectos(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			ProductoProyectoDao productoProyectoDao = ProductoProyectoDao.getInstance();
			List<ProductoProyecto> ProductoProyectos = productoProyectoDao.getAllProductoProyectoes();

			for (ProductoProyecto ProductoProyecto : ProductoProyectos) {
				ProductoProyecto.setNme(ProductoProyecto.getName().toUpperCase());
				productoProyectoDao.createProductoProyecto(ProductoProyecto);
				result += ProductoProyecto.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public String updateProyectos(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			ProyectoDao proyectoDao = ProyectoDao.getInstance();
			List<Proyecto> proyectos = proyectoDao.getAllProjects();
			int from = Integer.parseInt(req.getParameter("from"));
			int to = Integer.parseInt(req.getParameter("to"));
			Proyecto proyecto = null;
			for (int i = from; i<to;i++) {
				proyecto = proyectos.get(i);
				if(proyecto.getClienteName()!=null)proyecto.setClienteName(proyecto.getClienteName().toUpperCase());
				if(proyecto.getConectividad()!=null)proyecto.setConectividad(proyecto.getConectividad().toUpperCase());
				if(proyecto.getGestor_it_name()!=null)proyecto.setGestor_it_name(proyecto.getGestor_it_name().toUpperCase());
				if(proyecto.getGestor_negocio_name()!=null)proyecto.setGestor_negocio_name(proyecto.getGestor_negocio_name().toUpperCase());
				if(proyecto.getProducto()!=null)proyecto.setProducto(proyecto.getProducto().toUpperCase());
				if(proyecto.getServicio()!=null)proyecto.setServicio(proyecto.getServicio().toUpperCase());
				if(proyecto.getSubtipo()!=null)proyecto.setSubtipo(proyecto.getSubtipo().toUpperCase());
				if(proyecto.getTipo()!=null)proyecto.setTipo(proyecto.getTipo().toUpperCase());
				proyectoDao.createProjectRaw(proyecto);
				result += proyecto.getCod_proyecto() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	public String updateServicios(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			ServicioDao servicioDao = ServicioDao.getInstance();
			List<Servicio> servicios = servicioDao.getAllServicios();
			int from = Integer.parseInt(req.getParameter("from"));
			int to = Integer.parseInt(req.getParameter("to"));
			Servicio servicio = null;
			for (int i = from; i<to;i++) {
				servicio=servicios.get(i);
				if(servicio.getCliente_name()!=null)servicio.setCliente_name(servicio.getCliente_name().toUpperCase());
				if(servicio.getCod_servicio()!=null)servicio.setCod_servicio(servicio.getCod_servicio().toUpperCase());
				if(servicio.getDetalle()!=null)servicio.setDetalle(servicio.getDetalle().toUpperCase());
				if(servicio.getdetalleSubida()!=null)servicio.setdetalleSubida(servicio.getdetalleSubida().toUpperCase());
				if(servicio.getEstado()!=null)servicio.setEstado(servicio.getEstado().toUpperCase());
				if(servicio.getEstadoImplantacion()!=null)servicio.setEstadoImplantacion(servicio.getEstadoImplantacion().toUpperCase());
				if(servicio.getEstadoSubida()!=null)servicio.setEstadoSubida(servicio.getEstadoSubida().toUpperCase());
				if(servicio.getExtension()!=null)servicio.setExtension(servicio.getExtension().toUpperCase());
				if(servicio.getFormato_intermedio()!=null)servicio.setFormato_intermedio(servicio.getFormato_intermedio().toUpperCase());
				if(servicio.getFormato_local()!=null)servicio.setFormato_local(servicio.getFormato_local().toUpperCase());
				if(servicio.getGestor_it_name()!=null)servicio.setGestor_it_name(servicio.getGestor_it_name().toUpperCase());
				if(servicio.getGestor_negocio_name()!=null)servicio.setGestor_negocio_name(servicio.getGestor_negocio_name().toUpperCase());
				if(servicio.getObservaciones()!=null)servicio.setObservaciones(servicio.getObservaciones().toUpperCase());
				if(servicio.getPais()!=null)servicio.setPais(servicio.getPais().toUpperCase());
				if(servicio.getReferencia_local1()!=null)servicio.setReferencia_local1(servicio.getReferencia_local1().toUpperCase());
				if(servicio.getReferencia_local2()!=null)servicio.setReferencia_local2(servicio.getReferencia_local2().toUpperCase());
				if(servicio.getServicio()!=null)servicio.setServicio(servicio.getServicio().toUpperCase());
				servicioDao.createServicioRaw(servicio);
				result +=servicio.getCod_servicio() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public String updateServicioFile(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			ServicioFileDao servicioFileDao = ServicioFileDao.getInstance();
			List<ServicioFile> servicioFiles = servicioFileDao.getAllServicios();

			for (ServicioFile servicioFile : servicioFiles) {
				servicioFile.setName(servicioFile.getName().toUpperCase());
				servicioFileDao.createServicioFile(servicioFile);
				result += servicioFile.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	public String updateUser(HttpServletRequest req,
			HttpServletResponse resp,String usermail) throws InterruptedException, IOException {

		String result = "";
		try {
			UserDao userDao = UserDao.getInstance();
			List<User> users = userDao.getAllUsers();

			for (User user: users) {
				if(user.getApellido1()!=null)user.setApellido1(user.getApellido1().toUpperCase());
				if(user.getApellido2()!=null)user.setApellido2(user.getApellido2().toUpperCase());
				if(user.getAreas()!=null)user.setAreas(user.getAreas().toUpperCase());
				if(user.getDepartamento()!=null)user.setDepartamento(user.getDepartamento().toUpperCase());
				if(user.getNombre()!=null)user.setNombre(user.getNombre().toUpperCase());
				if(user.getPermisoStr()!=null)user.setPermisoStr(user.getPermisoStr().toUpperCase());
				userDao.createUser(user, usermail);
				result += user.getNombre()+user.getApellido1()+user.getApellido2() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}	
		
	
	public boolean isThisDateValid(String dateToValidate, String dateFormat) {

		if (dateToValidate == null || "".equals(dateToValidate)) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);

		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			// System.out.println(date);

		} catch (ParseException e) {
			return false;
		}

		return true;
	}

}
