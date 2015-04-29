package com.gcs.actions;

import java.io.IOException;
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
import com.gcs.utils.Utils;




public class GestionServicioAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1||sesionpermiso==2||sesionpermiso==3){
				
			ProyectoDao pDao = ProyectoDao.getInstance();
			List<Proyecto> proyectos = pDao.getAllProjects();
			
			
			////////////////////////////////////////////////////////////
			ServicioDao sDao = ServicioDao.getInstance();
			//List<Servicio> servicios = sDao.getAllServicios();
			List<Servicio> servicios = new ArrayList <Servicio>();
			
			String codProyectoFilter = req.getParameter("codigo");
			
			
			String page = req.getParameter("page");
			int pageint = Utils.stringToInt(page);	
			
			if(codProyectoFilter!=null){
				String codServicioFilter = req.getParameter("servicio");
				String estadoFilter = req.getParameter("estado");
				String gestorItNameFilter = req.getParameter("gestorIt");
				String gestorNegocioNameFilter = req.getParameter("gestorNeg");
				String clienteNameFilter = req.getParameter("cliente");
				
				servicios = sDao.getServicioByAllParam(codProyectoFilter, codServicioFilter, estadoFilter, gestorItNameFilter, gestorNegocioNameFilter, clienteNameFilter,  pageint);
				int numpages = (Integer.parseInt(servicios.get(servicios.size()-1).getDetalle())/ServicioDao.DATA_SIZE)+1;
				servicios.remove(servicios.size()-1);
				req.setAttribute("numpages", numpages);
				req.setAttribute("codigo", codProyectoFilter);
				req.setAttribute("servicio", codServicioFilter);
				req.setAttribute("estado", estadoFilter);
				req.setAttribute("gestorIt", gestorItNameFilter);
				req.setAttribute("gestorNeg", gestorNegocioNameFilter);
				req.setAttribute("cliente", clienteNameFilter);
			}else{
				servicios = sDao.getAllServicioPagin(pageint);
				//ContadorClienteDao ccDao = ContadorClienteDao.getInstance();
				//Integer cont = ccDao.getContadorValue();
				//int numpages = (cont/ClienteDao.DATA_SIZE) + 1;			
				//req.setAttribute("numpages", numpages);
			}
			
			boolean lastpage = (servicios.size() < ServicioDao.DATA_SIZE) ? true : false;
			req.setAttribute("lastpage", lastpage);
			req.setAttribute("page", pageint);		
			////////////////////////////////////////////////////////////
			
			//req.setAttribute("servicios", servicios);
			
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
