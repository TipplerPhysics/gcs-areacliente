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

public class ClientsAction3 extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllNonDeletedClientsAlphabet();
		
				
		Set<String> letras = new HashSet<String>();
		
		for (Cliente c:clientes){
			letras.add(c.getNombre().substring(0,1).toUpperCase());
		}

		
		req.setAttribute("clientes", clientes);
		req.setAttribute("letras", letras.toString());
		
	
		
		
		return mapping.findForward("ko");
	}
}
