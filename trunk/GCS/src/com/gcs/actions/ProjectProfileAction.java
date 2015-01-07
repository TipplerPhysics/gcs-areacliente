package com.gcs.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Cliente;
import com.gcs.beans.Proyecto;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ProyectoDao;

public class ProjectProfileAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		ClienteDao cDao = ClienteDao.getInstance();
		

		String id_str = req.getParameter("idCli");
		
		Cliente c = cDao.getClienteById(Long.parseLong(id_str));
		
		req.setAttribute("cliente", c);
		
		ProyectoDao proDao = ProyectoDao.getInstance();

		String idPro = req.getParameter("id");
		
		Proyecto proyecto = proDao.getProjectbyId(Long.parseLong(idPro));
		
		req.setAttribute("proyecto", proyecto);
		return mapping.findForward("ok");
	}
}
