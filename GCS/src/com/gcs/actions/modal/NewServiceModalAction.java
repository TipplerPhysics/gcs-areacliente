package com.gcs.actions.modal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Estados;
import com.gcs.beans.Formatos;
import com.gcs.beans.Pais;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.beans.ServicioFile;
import com.gcs.beans.User;
import com.gcs.dao.EstadosDao;
import com.gcs.dao.FormatosDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.ServicioFileDao;
import com.gcs.dao.UserDao;

public class NewServiceModalAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		ProyectoDao pDao = ProyectoDao.getInstance();
		List<Proyecto> proyectos = pDao.getAllProjects();
		
		ServicioDao sDao = ServicioDao.getInstance();
		List<Servicio> servicios = sDao.getAllServicios();
		
		ServicioFileDao servFileDao = ServicioFileDao.getInstance();
		List<ServicioFile> serviciosFile = servFileDao.getAllServicios();
		
		PaisDao paisDao = PaisDao.getInstance();
		List<Pais> paises = paisDao.getAllPaises();
		
		EstadosDao estDao = EstadosDao.getInstance();
		List<Estados> estados = estDao.getAllEstados();
	
		// Obtengo lista de Gestores para rellenar la select
		UserDao uDao = UserDao.getInstance();		
		List<User> gestores_it = uDao.getUsersByPermisoStr(3);

		FormatosDao formatosDao = FormatosDao.getInstance();
		List<Formatos> formatos = formatosDao.getAllFormatos();
		req.setAttribute("formatos", formatos);

		
		req.setAttribute("proyectos", proyectos);
		req.setAttribute("servicios", servicios);
		req.setAttribute("serviciosFile", serviciosFile);
		req.setAttribute("paises", paises);
		req.setAttribute("estados", estados);
		req.setAttribute("gestores_it", gestores_it);



		return mapping.findForward("ok");
	}
}
