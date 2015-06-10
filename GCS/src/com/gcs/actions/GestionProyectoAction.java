package com.gcs.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
import com.gcs.dao.ContadorProyectoDao;
import com.gcs.dao.DemandaDao;
import com.gcs.dao.ProductoProyectoDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;

public class GestionProyectoAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ParseException   {
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1||sesionpermiso==2||sesionpermiso==3){
			ClienteDao cDao = ClienteDao.getInstance();
			UserDao uDao = UserDao.getInstance();
			
			ProyectoDao pDao = ProyectoDao.getInstance();
			//List<Proyecto> projects = pDao.getAllProjects();
			
			////////////////////////////////////////////////////////////
			List<Proyecto> projects = new ArrayList <Proyecto>();
			
			String fechaDia = req.getParameter("fechaDia");
			
			String page = req.getParameter("page");
			int pageint = Utils.stringToInt(page);	
			
			if(fechaDia!=null){
				String fechaMes = req.getParameter("fechaMes");
				String fechaAnio = req.getParameter("fechaAnio");
				String codproyectoFilter = req.getParameter("codigo");
				String clienteNameFilter = req.getParameter("cliente");
				String clasificacionFilter = req.getParameter("clasificacion");
				String tipoFilter = req.getParameter("tipo");
				String costeFilter = req.getParameter("coste");
				
				projects = pDao.getProyectoByAllParam(fechaDia, fechaMes, fechaAnio, codproyectoFilter, clienteNameFilter, clasificacionFilter, tipoFilter, costeFilter,  pageint);
				int numpages = (Integer.parseInt(projects.get(projects.size()-1).getDetalle())/ProyectoDao.DATA_SIZE)+1;
				projects.remove(projects.size()-1);
				boolean lastpage = (projects.size() < ProyectoDao.DATA_SIZE) ? true : false;
				
				req.setAttribute("lastpage", lastpage);
				req.setAttribute("numpages", numpages);
				req.setAttribute("fechaDia", fechaDia);
				req.setAttribute("fechaMes", fechaMes);
				req.setAttribute("fechaAnio", fechaAnio);
				req.setAttribute("codigo", codproyectoFilter);
				req.setAttribute("cliente", clienteNameFilter);
				req.setAttribute("clasificacion", clasificacionFilter);
				req.setAttribute("tipo", tipoFilter);
				req.setAttribute("coste", costeFilter);
				
			}else{
				projects = pDao.getAllProyectoPagin(pageint);
				ContadorProyectoDao cpDao = ContadorProyectoDao.getInstance();
				Integer cont = cpDao.getContadorValue();
				int numpages = (cont/ProyectoDao.DATA_SIZE);
				if((cont % ProyectoDao.DATA_SIZE)>0){
					numpages ++;
				}
				
				int np = pageint + 1;
				boolean lastpage = (np < numpages) ? false : true;
				
				req.setAttribute("lastpage", lastpage);
				req.setAttribute("numpages", numpages);
			}
			
			
			req.setAttribute("page", pageint);		
			////////////////////////////////////////////////////////////
			
			
			
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
