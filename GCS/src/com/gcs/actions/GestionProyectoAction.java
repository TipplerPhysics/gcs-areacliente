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
import com.gcs.beans.ProductoProyecto;
import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ProductoProyectoDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.UserDao;

public class GestionProyectoAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1||sesionpermiso==2||sesionpermiso==3){
			
			String clienteid = req.getParameter("id");
			ClienteDao cDao = ClienteDao.getInstance();
			UserDao uDao = UserDao.getInstance();
			
			ProyectoDao pDao = ProyectoDao.getInstance();
			
			List<Proyecto> projects;
			if(clienteid==null||clienteid.equals("")){
				projects = pDao.getAllProjects();
			}else{
				projects = pDao.getProjectsByClient(Long.parseLong(clienteid));
			}
			
			List<Cliente> clientes = cDao.getAllNonDeletedClients();
			
			req.setAttribute("clientes", clientes);
			
			List<User> gestores_it = uDao.getUsersByPermisoStr(3);
			List<User> gestores_negocio = uDao.getUsersByPermisoStr(4);
			
			List<ProductoProyecto> productoProyecto = ProductoProyectoDao.getInstance().getAllProductoProyectoes();
			
			req.setAttribute("productos", productoProyecto);
			req.setAttribute("clientes", clientes);
			req.setAttribute("proyectos", projects);
			req.setAttribute("gestores_it", gestores_it);
			req.setAttribute("gestores_negocio", gestores_negocio);
			
	
			return mapping.findForward("ok");
		
		}else{
			return mapping.findForward("notAllowed");
		}
	}
}
