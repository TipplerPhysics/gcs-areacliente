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
import com.gcs.dao.UserDao;


public class ImplementacionModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {	
		
		String serviciosParam = "6082498324856832,6082498324856832";
		String conectividadesParam = "6645448278278144,6645448278278144";
		
		List<String> conectividadesList = new ArrayList<>();
		List<String> serviciosList = new ArrayList<>();
		// Procesas parametros para obtener un listado de codigos de conectividades y un listado de codigos de servicios
		String[] conectividadesArray = conectividadesParam.split(",");
		String[] serviciosArray = serviciosParam.split(",");
		conectividadesList = Arrays.asList(conectividadesArray);
		serviciosList = Arrays.asList(serviciosArray);
		
		String ultimoEstadoServicio = "";
		String ultimoEstadoConectividad = "";
		
		// TODO: Recuperar los servicios y las conectividades
		List<Conectividad> conectividades = new ArrayList<>();
		// FOR de conectividades
		ConectividadDao cDao = ConectividadDao.getInstance();
		for(String c : conectividadesList) {
			//Conectividad cObj = cDao.getConectividadById(c);
			//conectividades.add(cObj);
			//ultimoEstadoConectividad = cObj.getEstadoImplantacion();
		}
		
		List<Servicio> servicios = new ArrayList<>();
		// FOR de conectividades
		
		
		// TODO: comprobar el estado de las conectividades y servicios - - tiene que ser igual. EQUAL 
			
		// TODO: Dependiendo del esta hacer un forward u otro: email1, email2, email3
		return mapping.findForward("email1");
		
		
		
		/*ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllNonDeletedClients();
		
		UserDao uDao = UserDao.getInstance();
		List<User> gestores_it = uDao.getUsersByPermisoStr(3);
		
		req.setAttribute("clientes", clientes);
		req.setAttribute("gestores_it", gestores_it);*/

		
	}
}
