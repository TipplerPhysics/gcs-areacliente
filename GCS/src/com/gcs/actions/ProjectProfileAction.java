package com.gcs.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Cliente;
import com.gcs.beans.Conectividad;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;

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
		
		ServicioDao servicioDao = ServicioDao.getInstance();
		List<Servicio> servicios = servicioDao.getServiciosByProject(Long.parseLong(idPro));
		Servicio servicio = new Servicio();
		if (!servicios.isEmpty())servicio=servicios.get(0);
		
		ConectividadDao conectDao = ConectividadDao.getInstance();
		List<Conectividad> conectividades = conectDao.getConectividadesByProject(Long.parseLong(idPro));
		Conectividad conectividad = new Conectividad();
		if (!conectividades.isEmpty())conectividad= conectividades.get(0);
		
		if(conectividad.getEstado().equals("")||conectividad.getEstado().equals(null)){
			if(servicio.getEstado().equals("")||servicio.getEstado().equals(null)){
				conectividad.setEstado(" ");
				req.setAttribute("conectserv", conectividad);
			}else{
				req.setAttribute("conectserv", servicio);
			}
		}else{
		req.setAttribute("conectserv", conectividad);
		}
		
		
		return mapping.findForward("ok");
		
	}
}
