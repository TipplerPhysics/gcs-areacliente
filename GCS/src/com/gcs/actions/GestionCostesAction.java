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
import com.gcs.beans.Coste;
import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ContadorCosteDao;
import com.gcs.dao.CosteDao;
import com.gcs.dao.LogsDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;



public class GestionCostesAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ParseException {
	HttpSession sesion = req.getSession();
	int sesionpermiso = (int) sesion.getAttribute("permiso");
			
	if(sesionpermiso==1||sesionpermiso==2||sesionpermiso==3){
		
		ClienteDao cDao = ClienteDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		CosteDao coDao = CosteDao.getInstance();
			
		List<Cliente> clientes = cDao.getAllNonDeletedClients();
		List<User> gestores_it = uDao.getUsersByPermisoStr(3);
		//List<Coste> costes = coDao.getAllCostes();
			
		////////////////////////////////////////////////////////////
		List<Coste> costes = new ArrayList <Coste>();
			
		String fechaDia = req.getParameter("fechaDia");
			
		String page = req.getParameter("page");
		int pageint = Utils.stringToInt(page);	
			
		if(fechaDia!=null){
			String fechaMes = req.getParameter("fechaMes");
			String fechaAnio = req.getParameter("fechaAnio");
			String clienteNameFilter = req.getParameter("cliente");
			String projectNameFilter = req.getParameter("codigoPro");
			String equiposFilter = req.getParameter("equipo");
			String gestorItNameFilter = req.getParameter("gestorIt");
				
			costes = coDao.getCosteByAllParam(fechaDia, fechaMes, fechaAnio, clienteNameFilter, projectNameFilter, equiposFilter, gestorItNameFilter, pageint);
			int numpages = (Integer.parseInt(costes.get(costes.size()-1).getDetalle())/CosteDao.DATA_SIZE)+1;
			costes.remove(costes.size()-1);
			boolean lastpage = (costes.size() < CosteDao.DATA_SIZE) ? true : false;
			
			req.setAttribute("lastpage", lastpage);
			req.setAttribute("numpages", numpages);
			req.setAttribute("fechaDia", fechaDia);
			req.setAttribute("fechaMes", fechaMes);
			req.setAttribute("fechaAnio", fechaAnio);
			req.setAttribute("cliente", clienteNameFilter);
			req.setAttribute("codigoPro", projectNameFilter);
			req.setAttribute("equipo", equiposFilter);
			req.setAttribute("gestorIt", gestorItNameFilter);
			
		}else{
			costes = coDao.getAllCostePagin(pageint);
			ContadorCosteDao ccDao = ContadorCosteDao.getInstance();
			Integer cont = ccDao.getContadorValue();
			int numpages = (cont/CosteDao.DATA_SIZE);
			if((cont % CosteDao.DATA_SIZE)>0){
				numpages ++;
			}
			int np = pageint + 1;
			boolean lastpage = (np < numpages) ? false : true;
			
			req.setAttribute("lastpage", lastpage);
			req.setAttribute("numpages", numpages);
		}
		
		req.setAttribute("page", pageint);		
		////////////////////////////////////////////////////////////
			
		req.setAttribute("costes", costes);
		req.setAttribute("clientes", clientes);
		req.setAttribute("gestores_it", gestores_it);
		
		return mapping.findForward("ok");
	}else{
		return mapping.findForward("notAllowed");
	}
	}
}
