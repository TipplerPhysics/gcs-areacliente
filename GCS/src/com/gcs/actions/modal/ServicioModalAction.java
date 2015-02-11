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

import com.gcs.beans.Pais;
import com.gcs.beans.Servicio;
import com.gcs.beans.ServicioFile;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.ServicioFileDao;
import com.gcs.servlet.ServicioServlet;

public class ServicioModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			
			String id= req.getParameter("id");
			
			ServicioDao sDao = ServicioDao.getInstance();
			Servicio s = sDao.getServicioById(Long.parseLong(id));
			ServicioFileDao serviciosFileDao = ServicioFileDao.getInstance();
			PaisDao paisDao = PaisDao.getInstance();
			Pais pais = paisDao.getPaisById(Long.parseLong(s.getPais()));
			List<ServicioFile> servicios_pais = serviciosFileDao.getAllServiciosForPais(pais);
			
			req.setAttribute("servicios_pais", servicios_pais);
			req.setAttribute("servicio", s);			
			
			List<Pais> paises = paisDao.getAllPaises();
			req.setAttribute("paises", paises);
			
			ServicioFile servicioFile = serviciosFileDao.getServicioFileById(Long.parseLong(s.getServicio()));
			ArrayList<String> extensiones = servicioFile.getExtensiones();
			req.setAttribute("extensiones", extensiones);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("ok");
	}
}
