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
import com.gcs.beans.Coste;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.CosteDao;
import com.gcs.dao.UserDao;



public class GestionCostesAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

	ClienteDao cDao = ClienteDao.getInstance();
	UserDao uDao = UserDao.getInstance();
	CosteDao coDao = CosteDao.getInstance();
	
	List<Cliente> clientes = cDao.getAllNonDeletedClients();
	List<User> gestores_it = uDao.getUsersByPermisoStr(3);
	List<Coste> costes = coDao.getAllCostes();
	
	req.setAttribute("costes", costes);
	req.setAttribute("clientes", clientes);
	req.setAttribute("gestores_it", gestores_it);

	return mapping.findForward("ok");
	}

}
