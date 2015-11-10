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
import com.gcs.beans.Pais;
import com.gcs.beans.Servicio;
import com.gcs.beans.ServicioFile;
import com.gcs.beans.User;
import com.gcs.dao.EstadosDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.ServicioFileDao;
import com.gcs.dao.UserDao;
import com.gcs.servlet.ServicioServlet;

public class ServicioModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			
			String id= req.getParameter("id");
			
			EstadosDao estDao = EstadosDao.getInstance();
			List<Estados> estados = estDao.getAllEstados();
			
			req.setAttribute("estados", estados);
			
			ServicioDao sDao = ServicioDao.getInstance();
			
			Servicio s = sDao.getServicioById(Long.parseLong(id));
			ServicioFileDao serviciosFileDao = ServicioFileDao.getInstance();
			PaisDao paisDao = PaisDao.getInstance();
			Pais pais = paisDao.getPaisByName(s.getPais());
			List<ServicioFile> servicios_pais = serviciosFileDao.getAllServiciosForPais(pais);
			
			req.setAttribute("servicios_pais", servicios_pais);
			req.setAttribute("servicio", s);		
			
			List<Pais> paises = paisDao.getAllPaises();
			req.setAttribute("paises", paises);
			
			ServicioFile servicioFile = serviciosFileDao.getServicioFileByNamePais(s.getServicio(),s.getPais());
			ArrayList<String> extensiones = servicioFile.getExtensiones();
			req.setAttribute("extensiones", extensiones);
			
			
			List<Servicio> formatos = sDao.getAllFormatos();
			req.setAttribute("formatos", formatos);
			
			UserDao uDao = UserDao.getInstance();
			
			List<User> gestores_it = uDao.getUsersByPermisoStr(3);
			
			
			
			req.setAttribute("gestores_it", gestores_it);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("ok");
	}
}
