package com.gcs.actions;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.gcs.beans.Cliente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.dao.ClienteDao;

public class InformesAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		HttpSession sesion = req.getSession();
		String page = req.getParameter("page");
		ClienteDao clienteDao = ClienteDao.getInstance();
		List<Cliente> clientes = clienteDao.getAllNonDeletedClients();
		req.setAttribute("clients", clientes);
		
		req.setCharacterEncoding("UTF-8");
		return  mapping.findForward("ok");
	}
}
