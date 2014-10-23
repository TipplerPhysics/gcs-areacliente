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
import com.gcs.dao.ProyectoDao;

public class SelectProjectModalAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	
		ProyectoDao pDao = ProyectoDao.getInstance();
		List<Proyecto> projects = pDao.getAllProjects();	
		
		req.setAttribute("projects", projects);

		return mapping.findForward("ok");
	}

}
