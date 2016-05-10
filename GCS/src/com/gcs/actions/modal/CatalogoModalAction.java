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
import com.gcs.beans.ServicioFile;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ServicioFileDao;


public class CatalogoModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			
			
			String id= req.getParameter("id");
			
			
			ServicioFileDao sfDao = ServicioFileDao.getInstance();
			
			
			
			ServicioFile sf = sfDao.getServicioFileById(Long.parseLong(id));
			
			
			
			
			
			
			PaisDao paisDao = PaisDao.getInstance();
			
			String paisNombre = paisDao.getSPaisById(sf.getPaisId());
			
			sf.setNombrePais(paisNombre);
			
			req.setAttribute("servicio", sf);		
			
			List<Pais> paises = paisDao.getAllPaises();
			req.setAttribute("paises", paises);
			
			ServicioFile servicioFile = sfDao.getServicioFileByNamePais(sf.getName(),sf.getNombrePais());
			ArrayList<String> extensiones = servicioFile.getExtensiones();
			req.setAttribute("extensiones", extensiones);
			
			
					} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("ok");
	}
}
