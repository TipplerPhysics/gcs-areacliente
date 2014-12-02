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

import com.gcs.beans.Cliente;
import com.gcs.beans.Conectividad;
import com.gcs.beans.Servicio;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.UserDao;


public class ImplantacionModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {	
		
		String serviciosParam = req.getParameter("servicios");
		String conectividadesParam = req.getParameter("conectividades");
		
		//Proceso parametros de codigos de conectividades y de servicios
		String[] conectividadesArray = conectividadesParam.split(",");
		String[] serviciosArray = serviciosParam.split(",");
		List<String> conectividadesList = Arrays.asList(conectividadesArray);
		List<String> serviciosList = Arrays.asList(serviciosArray);
		
		//Declaro variables para guardar el último estado de Conectividades y Servicios
		String ultimoEstadoServicio = "";
		String ultimoEstadoConectividad = "";
		
		//Recuperar los servicios y las conectividades
		List<Conectividad> conectividades = new ArrayList<>();
		List<Servicio> servicios = new ArrayList<>();
		
		//FOR de Conectividades
		ConectividadDao cDao = ConectividadDao.getInstance();
		
		for(String c : conectividadesList) {
			Conectividad cObj = cDao.getConectividadById(c);
			if(cObj != null) {
				conectividades.add(cObj);
				ultimoEstadoConectividad = cObj.getEstadoImplantacion();
			}
		}
		
		//FOR de Servicios
		ServicioDao sDao = ServicioDao.getInstance();
		
		for(String s : serviciosList) {
			Servicio sObj = sDao.getServicioById(s);
			if(sObj != null) {
				servicios.add(sObj);
				ultimoEstadoServicio = sObj.getEstadoImplantacion();
			}
		}
		
		//comprobar el estado de las conectividades y servicios - - tiene que ser igual. EQUAL 		
		if( (ultimoEstadoConectividad == null) && (ultimoEstadoServicio == null) ) {
			req.setAttribute("servicios", serviciosParam);
			req.setAttribute("conectividades", conectividadesParam);
			
			return mapping.findForward("email1");
		}
		else if( ultimoEstadoConectividad.equals(ultimoEstadoServicio) && ultimoEstadoConectividad.equals("Solicitado") ){
			return mapping.findForward("email2");
			
		}
		else if( ultimoEstadoConectividad.equals(ultimoEstadoServicio) && ultimoEstadoConectividad.equals("Confirmado") ){
			return mapping.findForward("email3");			
		}
		else{
			//msg de error ya que tenemos Implementaciones en diferentes estados de implantacion
			 System.out.println("Error implementaciones en diferentes estados de implantacion");
			 return null;
		}
		
		
		/*ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllNonDeletedClients();
		
		UserDao uDao = UserDao.getInstance();
		List<User> gestores_it = uDao.getUsersByPermisoStr(3);
		
		req.setAttribute("clientes", clientes);
		req.setAttribute("gestores_it", gestores_it);*/

		
	}
}
