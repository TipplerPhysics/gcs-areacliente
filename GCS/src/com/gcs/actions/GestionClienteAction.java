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
import com.gcs.dao.ClienteDao;

public class GestionClienteAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllNonDeletedClients();
		
		req.setAttribute("clientes", clientes);
		req.setCharacterEncoding("UTF-8");
				

		return mapping.findForward("ok");
	}

}
