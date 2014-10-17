package com.gcs.actions.modal;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Servicio;
import com.gcs.dao.ServicioDao;
import com.gcs.servlet.ServicioServlet;

public class ServicioModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			
			String id= req.getParameter("id");
			
			ServicioDao sDao = ServicioDao.getInstance();
			Servicio s = sDao.getServicioById(Long.parseLong(id));
			
			ArrayList<String> servicios_pais = ServicioServlet.getServicesByCountryJSON(s.getPais());
			
			
			req.setAttribute("servicios_pais", servicios_pais);
			req.setAttribute("servicio", s);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("ok");
	}
}
