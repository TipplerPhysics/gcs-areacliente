package com.gcs.actions.modal;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Coste;
import com.gcs.dao.CosteDao;

public class CostesByProjectModalAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String project_id = req.getParameter("project_id");
		
		CosteDao cDao = CosteDao.getInstance();
		List<Coste> costes = cDao.getCostesByProject(Long.parseLong(project_id));
		
		req.setAttribute("costes", costes);
		
		return mapping.findForward("ok");
	}
}
