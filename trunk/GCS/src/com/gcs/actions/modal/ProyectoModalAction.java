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

import com.gcs.beans.Cliente;
import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.UserDao;

public class ProyectoModalAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		ClienteDao cDao = ClienteDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		
		String git_str = req.getParameter("git");
		String gn_str = req.getParameter("gn");
		
		String id = req.getParameter("id");
		
		
		String cliente_str = req.getParameter("client");
		Cliente cliente = cDao.getClienteById(Long.parseLong(cliente_str));
		
		List<Cliente> clientes_jdo = cDao.getAllNonDeletedClients();
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		
		
		clientes.addAll(clientes_jdo);
		
		
		
		User git = uDao.getUserbyId(Long.parseLong(git_str));
		User gn = uDao.getUserbyId(Long.parseLong(gn_str));
		
		List<User> gestores_it_jdo = uDao.getUsersByPermisoStr(3);
		List<User> gestores_negocio_jdo = uDao.getUsersByPermisoStr(4);
		
		List<User> gestores_it = new ArrayList<User>();
		gestores_it.addAll(gestores_it_jdo);
		
		List<User> gestores_negocio = new ArrayList<User>();
		gestores_negocio.addAll(gestores_negocio_jdo);
		
		if (!gestores_it.contains(git)){
			gestores_it.add(git);
		}
		
		
		if (!gestores_negocio.contains(gn)){
			gestores_negocio.add(gn);
		}
		
		if (!clientes.contains(cliente)){
			clientes.add(cliente);
		}
		
		String coste="";
		Proyecto p = new Proyecto();
		
		if (!"".equals(id)){
			ProyectoDao pDao = ProyectoDao.getInstance();
			p = pDao.getProjectbyId(Long.parseLong(id));
			
			coste = pDao.getCoste(p);
			
			
		}
		
		req.setAttribute("clientes", clientes);
		req.setAttribute("gestores_it", gestores_it);
		req.setAttribute("gestores_negocio", gestores_negocio);
		req.setAttribute("coste", coste);
		req.setAttribute("proyecto", p);
		
		

		return mapping.findForward("ok");
		
	}	
}
