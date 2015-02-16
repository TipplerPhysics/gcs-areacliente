package com.gcs.actions.modal;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Cliente;
import com.gcs.beans.Pais;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.PaisDao;

public class ClienteModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	
		PaisDao paisDao = PaisDao.getInstance();
		List<Pais> paises = paisDao.getAllPaises();
		req.setAttribute("paisesModal", paises);
		String str_id = req.getParameter("id");
		ClienteDao cliDao = ClienteDao.getInstance();
		Cliente cliente = cliDao.getClienteById(Long.parseLong(str_id));
		req.setAttribute("clienteModal", cliente);
		
		Set<String> paisesCliente =cliente.getPaises();
		req.setAttribute("paisesCliente", paisesCliente);
		return mapping.findForward("ok");
	}
}
