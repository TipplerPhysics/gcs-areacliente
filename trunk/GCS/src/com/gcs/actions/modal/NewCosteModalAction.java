package com.gcs.actions.modal;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Cliente;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.UserDao;


public class NewCosteModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {	
		
		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllNonDeletedClients();
		
		UserDao uDao = UserDao.getInstance();
		List<User> gestores_it = uDao.getUsersByPermisoStr(3);
		
		req.setAttribute("clientes", clientes);
		req.setAttribute("gestores_it", gestores_it);
	

		return mapping.findForward("ok");
	}
}
