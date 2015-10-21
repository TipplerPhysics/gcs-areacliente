package com.gcs.servlet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.beans.ConectividadProyecto;
import com.gcs.beans.Departamentos;
import com.gcs.beans.Equipo;
import com.gcs.beans.EstadoPeticion;
import com.gcs.beans.Estados;
import com.gcs.beans.FechaCalendada;
import com.gcs.beans.ProductoProyecto;
import com.gcs.beans.Seguridad;
import com.gcs.beans.ServicioFile;
import com.gcs.dao.ConectividadProyectoDao;
import com.gcs.dao.DepartamentosDao;
import com.gcs.dao.EquipoDao;
import com.gcs.dao.EstadoPeticionDao;
import com.gcs.dao.EstadosDao;
import com.gcs.dao.FechaCalendadaDao;
import com.gcs.dao.ProductoProyectoDao;
import com.gcs.dao.SeguridadDao;
import com.gcs.dao.ServicioFileDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class newElementsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = new JSONObject();

		String accion = req.getParameter("accion");
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		String usermail = (String)sesion.getAttribute("usermail");
		try {
			if (sesionpermiso != 1) {
				json.append("failure", "true");
				json.append("error",
						"No tienes los permisos para realizar esta operación");
	
				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("application/json");
				resp.getWriter().println(json);
			}else{
				if(accion.equals("newEquipo"))createEquipo(req, resp);
				if(accion.equals("ServicioFile"))createServicioFile(req, resp);
				if(accion.equals("extension"))createExtension(req, resp);
				if(accion.equals("ProductoProyecto"))createProducto(req, resp);
				if(accion.equals("ConectividadProyecto"))createConectividad(req, resp);
				if(accion.equals("Depapartamento"))createDepartamento(req, resp);
				if(accion.equals("seguridad"))createSeguridad(req, resp);
				if(accion.equals("estado"))createEstado(req, resp);
				if(accion.equals("estadoPeticion"))createEstadoPeticion(req, resp);
				if(accion.equals("fechaCalendada"))createFechaCalendada(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		doGet(req, resp);
	}
	
	private void createEquipo(HttpServletRequest req, HttpServletResponse resp){
		EquipoDao equipoDao = EquipoDao.getInstance();
		Equipo equipo = new Equipo();
		String nombre = req.getParameter("nombre");
		equipo.setNombre(nombre);
		equipo.setContador(1);
		equipo.setUltima_escritura(new Date());
		equipoDao.createEquipo(equipo);
	}
	
	private void createServicioFile(HttpServletRequest req, HttpServletResponse resp){
		ServicioFileDao servicioFileDao = ServicioFileDao.getInstance();
		ServicioFile servicioFile = new ServicioFile();
		String nombre = req.getParameter("nombre");
		servicioFile.setName(nombre);
		String[] extensiones = req.getParameterValues("conectividad");
		ArrayList<String> ext = new ArrayList<String>();
		for(String extension: extensiones){
			ext.add(extension);
		}
		servicioFile.setExtensiones(ext);
		String paisId = req.getParameter("paisId");
		servicioFile.setPaisId(Long.parseLong(paisId));
		servicioFileDao.createServicioFile(servicioFile);
	}
	
	private void createExtension(HttpServletRequest req, HttpServletResponse resp){
		
	}
	
	private void createProducto(HttpServletRequest req, HttpServletResponse resp){
		ProductoProyectoDao productoDao = ProductoProyectoDao.getInstance();
		ProductoProyecto productoProyecto = new ProductoProyecto();
		String nombre = req.getParameter("nombre");
		productoProyecto.setNme(nombre);
		productoDao.createProductoProyecto(productoProyecto);
	}
	
	private void createConectividad(HttpServletRequest req, HttpServletResponse resp){
		ConectividadProyectoDao conectividadProyectoDao = ConectividadProyectoDao.getInstance();
		ConectividadProyecto conectividadProyecto = new ConectividadProyecto();
		String nombre = req.getParameter("nombre");
		String productoId = req.getParameter("productoId");
		conectividadProyecto.setName(nombre);
		conectividadProyecto.setProductoId(Long.parseLong(productoId));
		conectividadProyectoDao.createConectividadProyecto(conectividadProyecto);
	}
	private void createDepartamento(HttpServletRequest req, HttpServletResponse resp){
		DepartamentosDao departamentoDao = DepartamentosDao.getInstance();
		String nombre = req.getParameter("nombre");
		Departamentos departamento = new Departamentos();
		departamento.setNme(nombre);
		departamentoDao.createDepartamentos(departamento);
	}
	private void createSeguridad(HttpServletRequest req, HttpServletResponse resp){
		SeguridadDao seguridadDao = SeguridadDao.getInstance();
		Seguridad seguridad = new Seguridad();
		String nombre = req.getParameter("nombre");
		seguridad.setNme(nombre);
		seguridadDao.createSeguridad(seguridad);
	}
	private void createEstado(HttpServletRequest req, HttpServletResponse resp){
		EstadosDao estadoDao = EstadosDao.getInstance();
		Estados estado = new Estados();
		String nombre = req.getParameter("nombre");
		estado.setNme(nombre);
		estadoDao.createEstados(estado);
	}
	private void createEstadoPeticion(HttpServletRequest req, HttpServletResponse resp){
		EstadoPeticionDao estadoDao = EstadoPeticionDao.getInstance();
		EstadoPeticion estado = new EstadoPeticion();
		String nombre = req.getParameter("nombre");
		estado.setNme(nombre);
		estadoDao.createEstadoPeticion(estado);
	}
	private void createFechaCalendada(HttpServletRequest req, HttpServletResponse resp) throws ParseException{
		FechaCalendadaDao fechaCalendadaDao = FechaCalendadaDao.getInstance();
		FechaCalendada fechaCalendada = new FechaCalendada();
		String fecha = req.getParameter("fecha");
		Date fechaDate = Utils.dateConverter(fecha);
		fechaCalendada.setFecha(fechaDate);
		fechaCalendada.setStr_fecha(fecha);
		fechaCalendadaDao.createFechaCalendada(fechaCalendada);
	}
	
}