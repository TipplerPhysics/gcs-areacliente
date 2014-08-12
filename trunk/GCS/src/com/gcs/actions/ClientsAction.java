package com.gcs.actions;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Cliente;
import com.gcs.dao.ClienteDao;

public class ClientsAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllClientes();
		
		
		
		Set<String> letras = new HashSet<String>();
		
		for (Cliente c:clientes){
			letras.add(c.getNombre().substring(0,1).toUpperCase());
		}

		String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 
		String alpha = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
		
		req.setAttribute("clientes", clientes);
		req.setAttribute("letras", letras.toString());
		req.setAttribute("alphabet", alphabet);
		req.setAttribute("alpha", alpha);
		
		return mapping.findForward("ok");
	}
}
