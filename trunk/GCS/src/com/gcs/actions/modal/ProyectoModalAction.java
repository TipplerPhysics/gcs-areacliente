package com.gcs.actions.modal;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.UserDao;

public class ProyectoModalAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		ClienteDao cDao = ClienteDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		
		
		List<User> gestores_it = uDao.getUsersByPermisoStr(3);
		List<User> gestores_negocio = uDao.getUsersByPermisoStr(4);
		
		req.setAttribute("clientes", cDao.getAllClientes());
		req.setAttribute("gestores_it", gestores_it);
		req.setAttribute("gestores_negocio", gestores_negocio);

		return mapping.findForward("ok");
		
	}	
}
