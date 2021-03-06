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
import com.gcs.beans.Pais;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ContadorPagClienteDao;
import com.gcs.dao.LogsDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ServicioDao;
import com.gcs.utils.Utils;

public class GestionClienteAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ParseException {
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1||sesionpermiso==2||sesionpermiso==3){
		//ClienteDao cDao = ClienteDao.getInstance();
		//List<Cliente> clientes = cDao.getAllNonDeletedClients();
		
		PaisDao paisDao = PaisDao.getInstance();
		List<Pais> paises = paisDao.getAllPaises();
		req.setAttribute("paises", paises);
	
		////////////////////////////////////////////////////////////
			ClienteDao cDao = ClienteDao.getInstance();
			List<Cliente> clientes = new ArrayList <Cliente>();
				
			String fechaDia = req.getParameter("fechaDia");
			
					
			String page = req.getParameter("page");
			int pageint = Utils.stringToInt(page);	
					
			if(fechaDia!=null){
				String idClienteFilter = req.getParameter("idCliente");
				String fechaMes = req.getParameter("fechaMes");
				String fechaAnio = req.getParameter("fechaAnio");
				String nClienteFilter = req.getParameter("cliente");
				String refGlobalFilter = req.getParameter("referencia");
				String tipoFilter = req.getParameter("tipo");
				String criticidadFilter = req.getParameter("criticidad");
				clientes = cDao.getClienteByAllParam(fechaDia, fechaMes, fechaAnio, idClienteFilter, nClienteFilter, refGlobalFilter, tipoFilter, criticidadFilter,  pageint);
				int numpages = (Integer.parseInt(clientes.get(clientes.size()-1).getDetalle())/ClienteDao.DATA_SIZE)+1;
				clientes.remove(clientes.size()-1);
				boolean lastpage = (clientes.size() < ServicioDao.DATA_SIZE) ? true : false;
				
				req.setAttribute("lastpage", lastpage);
				req.setAttribute("numpages", numpages);
				req.setAttribute("fechaDia", fechaDia);
				req.setAttribute("fechaMes", fechaMes);
				req.setAttribute("fechaAnio", fechaAnio);
				req.setAttribute("idCliente", idClienteFilter);
				req.setAttribute("cliente", nClienteFilter);
				req.setAttribute("referencia", refGlobalFilter);
				req.setAttribute("tipo", tipoFilter);
				req.setAttribute("criticidad", criticidadFilter);
			}else{
				clientes = cDao.getAllClientePagin(pageint);
				ContadorPagClienteDao ccDao = ContadorPagClienteDao.getInstance();
				Integer cont = ccDao.getContadorValue();
				int numpages = (cont/ClienteDao.DATA_SIZE);
				if((cont % ClienteDao.DATA_SIZE)>0){
					numpages ++;
				}
				int np = pageint + 1;
				boolean lastpage = (np < numpages) ? false : true;
				
				req.setAttribute("lastpage", lastpage);
				req.setAttribute("numpages", numpages);
			}
			
			req.setAttribute("page", pageint);		
		////////////////////////////////////////////////////////////
		
		req.setAttribute("clientes",clientes);
		req.setCharacterEncoding("UTF-8");

		return mapping.findForward("ok");
		
		}else{
			return mapping.findForward("notAllowed");	
		}
	}

}
