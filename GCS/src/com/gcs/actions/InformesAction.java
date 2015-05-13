package com.gcs.actions;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.gcs.beans.Cliente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.dao.ClienteDao;

public class InformesAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		
		ClienteDao clienteDao = ClienteDao.getInstance();
		List<Cliente> clientes = clienteDao.getAllNonDeletedClients();
		req.setAttribute("clientes", clientes);
		
		req.setCharacterEncoding("UTF-8");
		return  mapping.findForward("ok");
	}
}
