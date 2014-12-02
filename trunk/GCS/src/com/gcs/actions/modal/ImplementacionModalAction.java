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


public class ImplementacionModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {	
		
		String serviciosParam = "6082498324856832,6082498324856832";
		String conectividadesParam = "6645448278278144,6645448278278144";
		
		List<String> conectividadesList = new ArrayList<>();
		List<String> serviciosList = new ArrayList<>();
		
		//Proceso parametros para obtener un listado de codigos de conectividades y un listado de codigos de servicios
		
		String[] conectividadesArray = conectividadesParam.split(",");
		String[] serviciosArray = serviciosParam.split(",");
		
		//Convertimos ArrayList de Strings en Lista de Strings
		
		conectividadesList = Arrays.asList(conectividadesArray);
		serviciosList = Arrays.asList(serviciosArray);
		
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
		if((ultimoEstadoConectividad == ultimoEstadoServicio)&&(ultimoEstadoConectividad.equals("Solicitado"))){
			
			return mapping.findForward("email1");
		
		}else if((ultimoEstadoConectividad.equals(ultimoEstadoServicio))&&(ultimoEstadoConectividad.equals("Confirmado"))){
			
			return mapping.findForward("email2");
			
		}else if((ultimoEstadoConectividad.equals(ultimoEstadoServicio))&&(ultimoEstadoConectividad.equals("Produccion"))){
			
			return mapping.findForward("email3");
			
		}else if(ultimoEstadoConectividad.equals(ultimoEstadoServicio)){
			
			return mapping.findForward("email4");
			
		}else{
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
