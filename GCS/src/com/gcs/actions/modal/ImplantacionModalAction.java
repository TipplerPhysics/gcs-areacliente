package com.gcs.actions.modal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Conectividad;
import com.gcs.beans.FechaCalendada;
import com.gcs.beans.Servicio;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.FechaCalendadaDao;
import com.gcs.dao.ServicioDao;


public class ImplantacionModalAction extends Action {
	
	private static final String VACIO = "";
	private static final String SOLICITADO = "SOLICITADO";
	private static final String CONFIRMADO = "CONFIRMADO";
	private static final String PRODUCCION = "PRODUCCION";
	private static final String OK = "OK";
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {	
		
		// Modal de notificacion de informe generado.
		String informe = req.getParameter("informe");		
		if(OK.equals(informe)) {
			return mapping.findForward("informe");			
		}
		
		// Resto de modales de envío de emails.
		String serviciosParam = req.getParameter("servicios");
		String conectividadesParam = req.getParameter("conectividades");
		
		//Proceso parametros de codigos de conectividades y de servicios
		List<String> conectividadesList = new ArrayList<>();
		if(!VACIO.equals(conectividadesParam)) {
			String[] conectividadesArray = conectividadesParam.split(",");
			conectividadesList = Arrays.asList(conectividadesArray);
		}
		
		List<String> serviciosList = new ArrayList<>();
		if(!VACIO.equals(serviciosParam)) {
			String[] serviciosArray = serviciosParam.split(",");
			serviciosList = Arrays.asList(serviciosArray);
		}
		
		//Declaro variables para guardar el último estado de Conectividades y Servicios
		String ultimoEstadoServicio = null;
		String ultimoEstadoConectividad = null;
		String ultimaFechaImplantacion = null;
		
		//Recuperar los servicios y las conectividades
		List<Conectividad> conectividades = new ArrayList<>();
		List<Servicio> servicios = new ArrayList<>();
		
		//FOR de Conectividades
		if(conectividadesList.size() > 0) {
			ConectividadDao cDao = ConectividadDao.getInstance();
			for(String c : conectividadesList) {
				Conectividad cObj = cDao.getConectividadById(c);
				if(cObj != null) {
					conectividades.add(cObj);
					ultimoEstadoConectividad = cObj.getEstadoImplantacion();
					ultimaFechaImplantacion = cObj.getStr_fecha_implantacion();
				}
			}
		}
		
		//FOR de Servicios
		if(serviciosList.size() > 0) {
			ServicioDao sDao = ServicioDao.getInstance();
			for(String s : serviciosList) {
				Servicio sObj = sDao.getServicioById(s);
				if(sObj != null) {
					servicios.add(sObj);
					ultimoEstadoServicio = sObj.getEstadoImplantacion();
					ultimaFechaImplantacion = sObj.getStr_fecha_implantacion_produccion();
				}
			}
		}
		
		//comprobar el estado de las conectividades y servicios - - tiene que ser igual. EQUAL 		
		if( (ultimoEstadoConectividad == null) && (ultimoEstadoServicio == null) ) {
			
			FechaCalendadaDao fechDao = FechaCalendadaDao.getInstance();
			List<FechaCalendada> fechas = fechDao.getAllFutureFechas();
			List<String> fechasStr = fechDao.getAllFutureFechasStr();
			req.setAttribute("fechas",fechas);
			req.setAttribute("fechasStr",fechasStr);
			req.setAttribute("servicios", serviciosParam);
			req.setAttribute("conectividades", conectividadesParam);
			
			return mapping.findForward("email1");
		}
		else if( SOLICITADO.equals(ultimoEstadoServicio) || SOLICITADO.equals(ultimoEstadoConectividad) ){
			req.setAttribute("servicios", serviciosParam);
			req.setAttribute("conectividades", conectividadesParam);
			req.setAttribute("fecha_implantacion", ultimaFechaImplantacion);
			
			return mapping.findForward("email2");			
		}
		else if( CONFIRMADO.equals(ultimoEstadoServicio) || CONFIRMADO.equals(ultimoEstadoConectividad) ){
			req.setAttribute("servicios", serviciosParam);
			req.setAttribute("conectividades", conectividadesParam);
			req.setAttribute("fecha_implantacion", ultimaFechaImplantacion);
			
			return mapping.findForward("email3");			
		}
		else if( PRODUCCION.equals(ultimoEstadoServicio) || PRODUCCION.equals(ultimoEstadoConectividad) ){
			FechaCalendadaDao fechDao = FechaCalendadaDao.getInstance();
			List<FechaCalendada> fechas = fechDao.getAllFutureFechas();
			List<String> fechasStr = fechDao.getAllFutureFechasStr();
			req.setAttribute("fechas",fechas);
			req.setAttribute("fechasStr",fechasStr);
			req.setAttribute("servicios", serviciosParam);
			req.setAttribute("conectividades", conectividadesParam);
			
			
			return mapping.findForward("email1");			
		}
		else{
			//msg de error ya que tenemos Implementaciones en diferentes estados de implantacion
			 System.out.println("Error implementaciones en diferentes estados de implantacion");
			 req.setAttribute("servicios", serviciosParam);
			 req.setAttribute("conectividades", conectividadesParam);
			 req.setAttribute("ultimoEstadoConectividad", ultimoEstadoConectividad);
			 req.setAttribute("ultimoEstadoServicio", ultimoEstadoServicio);
			 return mapping.findForward("error");
		}
	}
}
