package com.gcs.actions.modal;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Conectividad;
import com.gcs.beans.Seguridad;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Estados;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.SeguridadDao;
import com.gcs.dao.EstadosDao;

public class LoadConectividadAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		ProyectoDao pDao = ProyectoDao.getInstance();
		ConectividadDao cDao = ConectividadDao.getInstance();
		
		SeguridadDao sgdDao = SeguridadDao.getInstance();
		List<Seguridad> seguridad = sgdDao.getAllSeguridad();
		
		EstadosDao estDao = EstadosDao.getInstance();
		List<Estados> estados = estDao.getAllEstados();
		
		String id = req.getParameter("id");
		
		Proyecto p = pDao.getProjectbyId(Long.parseLong(id));
		Conectividad c = cDao.getConectividadByProject(Long.parseLong(id));
		
		
		req.setAttribute("conectividad", c);
		req.setAttribute("project_name", p.getCod_proyecto());
		req.setAttribute("seguridad", seguridad);
		req.setAttribute("estados", estados);
		
		if (p.getProducto().contains("Swift")){			
			return mapping.findForward("swift");
		}else{
			return mapping.findForward("h2h");
		}
		
		
		
		

	}
}
