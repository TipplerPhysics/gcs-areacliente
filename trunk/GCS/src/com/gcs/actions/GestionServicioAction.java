package com.gcs.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Cliente;
import com.gcs.beans.Estados;
import com.gcs.beans.Pais;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.beans.ServicioFile;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.EstadosDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.ServicioFileDao;




public class GestionServicioAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1||sesionpermiso==2||sesionpermiso==3){
			
			ProyectoDao pDao = ProyectoDao.getInstance();
			List<Proyecto> proyectos = pDao.getAllProjects();
			
			ServicioDao sDao = ServicioDao.getInstance();
			List<Servicio> servicios = sDao.getAllServicios();
			
			
			req.setAttribute("servicios", servicios);
			
			ClienteDao cliDao = ClienteDao.getInstance();
			List<Cliente> clientes = cliDao.getAllClientes();
			req.setAttribute("clientes", clientes);
		
			ServicioFileDao servFileDao = ServicioFileDao.getInstance();
			List<ServicioFile> serviciosFile = servFileDao.getAllServicios();
			
			PaisDao paisDao = PaisDao.getInstance();
			List<Pais> paises = paisDao.getAllPaises();
			
			EstadosDao estDao = EstadosDao.getInstance();
			List<Estados> estados = estDao.getAllEstados();
			
			req.setAttribute("proyectos", proyectos);
			req.setAttribute("servicios", servicios);
			req.setAttribute("clientes", clientes);
			req.setAttribute("serviciosFile", serviciosFile);
			req.setAttribute("paises", paises);
			req.setAttribute("estados", estados);
			
			return mapping.findForward("ok");
		}else{
			return mapping.findForward("notAllowed");
		}
	}
}
