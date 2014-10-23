package com.gcs.actions.modal;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;

public class NewServiceModalAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		ProyectoDao pDao = ProyectoDao.getInstance();
		List<Proyecto> proyectos = pDao.getAllProjects();
		
		ServicioDao sDao = ServicioDao.getInstance();
		
		List<Servicio> servicios = sDao.getAllServicios();
		
		req.setAttribute("servicios", servicios);
		req.setAttribute("proyectos", proyectos);
	

		return mapping.findForward("ok");
	}
}
