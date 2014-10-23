package com.gcs.actions.modal;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Servicio;
import com.gcs.dao.ServicioDao;

public class SelectServiceModalAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String id_proj = req.getParameter("id");
	
		ServicioDao sDao = ServicioDao.getInstance();
		List<Servicio> services = sDao.getServiciosByProject(Long.parseLong(id_proj));
		
		req.setAttribute("services", services);

		return mapping.findForward("ok");
	}

}
