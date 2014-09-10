package com.gcs.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Cliente;
import com.gcs.dao.ClienteDao;

public class ClientProfileAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp){
		
		ClienteDao cDao = ClienteDao.getInstance();
		
		try{
			String id_str = req.getParameter("id");
			
			Cliente c = cDao.getClienteById(Long.parseLong(id_str));
			
			req.setAttribute("cliente", c);
			
			return mapping.findForward("ok");
		}catch(Exception e){
			return  mapping.findForward("ko");
		}

		
	}
}