package com.gcs.actions.modal;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Conectividad;
import com.gcs.beans.Proyecto;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.ProyectoDao;

public class LoadConectividadAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		ProyectoDao pDao = ProyectoDao.getInstance();
		ConectividadDao cDao = ConectividadDao.getInstance();
		
		String id = req.getParameter("id");
		
		Proyecto p = pDao.getProjectbyId(Long.parseLong(id));
		Conectividad c = cDao.getConectividadByProject(id);
		
		req.setAttribute("conectividad", c);
		
		if (p.getProducto().contains("Swift")){
			
			req.setAttribute("project_name", p.getCod_proyecto());
			return mapping.findForward("swift");
		}else{
			return mapping.findForward("h2h");
		}
		
		
		
		

	}
}
