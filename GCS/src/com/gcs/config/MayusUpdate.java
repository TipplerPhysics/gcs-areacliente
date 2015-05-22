package com.gcs.config;

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
				result = updateCliente(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("conectividad".equals(accion)) {
				result = updateConectividad(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("conectividadProyecto".equals(accion)) {
				result = updateConectividadProyecto(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("coste".equals(accion)) {
				result = updateCoste(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("demanda".equals(accion)) {
				result = updateDemandas(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("departamentos".equals(accion)) {
				result = updateDepartamentos(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("equipo".equals(accion)) {
				result = updateEquipos(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("estadoPeticion".equals(accion)) {
				result = updateEstadoPeticion(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("estados".equals(accion)) {
				result = updateEstados(req, resp);
				json.append("success", "true");
				json.append("result", result);
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
			}else if ("servicioFile".equals(accion)) {
				result = updateServicioFile(req, resp);
				json.append("success", "true");
				json.append("result", result);
			}else if ("user".equals(accion)) {
				result = updateUser(req, resp);
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
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			ClienteDao clientesDao = ClienteDao.getInstance();
			List<Cliente> clientes = clientesDao.getAllClientes();
			String usermail ="actualizacion automatica";
			for (Cliente cliente : clientes) {
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
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			ConectividadDao conectividadDao = ConectividadDao.getInstance();
			List<Conectividad> conectividades = conectividadDao.getAllConectividades();
			String usermail ="actualizacion automatica";
			for (Conectividad conectividad: conectividades) {
				conectividad.setdetalleSubida(conectividad.getdetalleSubida().toUpperCase());
				conectividad.setEstado(conectividad.getEstado().toUpperCase());
				conectividad.setSeguridad(conectividad.getSeguridad().toUpperCase());
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
			HttpServletResponse resp) throws InterruptedException, IOException {
		String usermail = "acatualiz mayus";
		String result = "";
		try {
			CosteDao CostesDao = CosteDao.getInstance();
			List<Coste> Costes = CostesDao.getAllCostes();

			for (Coste coste : Costes) {
				coste.setCliente_name(coste.getCliente_name().toUpperCase());
				coste.setComentarios(coste.getComentarios().toUpperCase());
				coste.setEquipos(coste.getEquipos().toUpperCase());
				coste.setGestor_it_name(coste.getGestor_it_name().toUpperCase());
				CostesDao.createCoste(coste, usermail);
				result += coste.getDetalle() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateDemandas(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			DemandaDao DemandasDao = DemandaDao.getInstance();
			List<Demanda> Demandas = DemandasDao.getAllDemandas();

			for (Demanda demanda : Demandas) {
				demanda.setCatalogacion(demanda.getCatalogacion().toUpperCase());
				demanda.setComentarios(demanda.getComentarios().toUpperCase());
				demanda.setDetalle(demanda.getDetalle().toUpperCase());
				demanda.setEstado(demanda.getDetalle().toUpperCase());
				demanda.setMotivo_catalogacion(demanda.getMotivo_catalogacion().toUpperCase());
				DemandasDao.createDemanda(demanda,"a mayusculas demanda");
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

			for (Proyecto proyecto : proyectos) {
				proyecto.setClienteName(proyecto.getClienteName().toUpperCase());
				proyecto.setConectividad(proyecto.getConectividad().toUpperCase());
				proyecto.setGestor_it_name(proyecto.getGestor_it_name().toUpperCase());
				proyecto.setProducto(proyecto.getProducto().toUpperCase());
				proyecto.setServicio(proyecto.getServicio().toUpperCase());
				proyecto.setSubtipo(proyecto.getSubtipo().toUpperCase());
				proyecto.setTipo(proyecto.getTipo().toUpperCase());
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

			for (Servicio servicio: servicios) {
				servicio.setCliente_name(servicio.getCliente_name().toUpperCase());
				servicio.setCod_servicio(servicio.getCod_servicio().toUpperCase());
				servicio.setDetalle(servicio.getServicio().toUpperCase());
				servicio.setdetalleSubida(servicio.getdetalleSubida().toUpperCase());
				servicio.setEstado(servicio.getEstado().toUpperCase());
				servicio.setEstadoImplantacion(servicio.getEstadoImplantacion().toUpperCase());
				servicio.setEstadoSubida(servicio.getEstadoSubida().toUpperCase());
				servicio.setExtension(servicio.getExtension().toUpperCase());
				servicio.setFormato_intermedio(servicio.getFormato_intermedio().toUpperCase());
				servicio.setFormato_local(servicio.getFormato_local().toUpperCase());
				servicio.setGestor_it_name(servicio.getGestor_it_name().toUpperCase());
				servicio.setGestor_negocio_name(servicio.getGestor_negocio_name().toUpperCase());
				servicio.setObservaciones(servicio.getObservaciones().toUpperCase());
				servicio.setPais(servicio.getPais().toUpperCase());
				servicio.setReferencia_local1(servicio.getReferencia_local1().toUpperCase());
				servicio.setReferencia_local2(servicio.getReferencia_local2().toUpperCase());
				servicio.setServicio(servicio.getServicio().toUpperCase());
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
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			UserDao userDao = UserDao.getInstance();
			List<User> users = userDao.getAllUsers();

			for (User user: users) {
				user.setApellido1(user.getApellido1().toUpperCase());
				user.setApellido2(user.getApellido2().toUpperCase());
				user.setAreas(user.getAreas().toUpperCase());
				user.setDepartamento(user.getDepartamento().toUpperCase());
				user.setNombre(user.getNombre().toUpperCase());
				user.setPermisoStr(user.getPermisoStr().toUpperCase());
				userDao.createUser(user, "to mayus admin");
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
