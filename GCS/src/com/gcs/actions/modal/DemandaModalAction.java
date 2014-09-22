package com.gcs.actions.modal;

import java.io.IOException;
import java.util.ArrayList;
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
import com.gcs.dao.DemandaDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;

public class DemandaModalAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		
		String git_str = req.getParameter("git");
		String gn_str = req.getParameter("gn");

		UserDao uDao = UserDao.getInstance();
		DemandaDao dDao = DemandaDao.getInstance();
		ClienteDao cDao = ClienteDao.getInstance();
		
		User git = uDao.getUserbyId(Long.parseLong(git_str));
		User gn = uDao.getUserbyId(Long.parseLong(gn_str));
		
		List<User> gestores_it_jdo = uDao.getUsersByPermisoStr(3);
		List<User> gestores_negocio_jdo = uDao.getUsersByPermisoStr(4);
		
		List<User> gestores_it = new ArrayList<User>();
		gestores_it.addAll(gestores_it_jdo);
		
		List<User> gestores_negocio = new ArrayList<User>();
		gestores_negocio.addAll(gestores_negocio_jdo);
		
		List<Cliente> clientes = cDao.getAllClientes();
		
		if (!gestores_it.contains(git)){
			gestores_it.add(git);
		}
		
		
		if (!gestores_negocio.contains(gn)){
			gestores_negocio.add(gn);
		}

		String gestoresStr = "";

		if (!gestores_it.isEmpty()) {
			for (User g : gestores_it) {
				gestoresStr += g.getNombre() + " " + g.getApellido1() + " "
						+ g.getApellido2() + "(" + g.getKey().getId() + ")"
						+ "-";
			}

		}

		req.setAttribute("clientes", clientes);
		//req.setAttribute("gestores_demanda", gestores_demanda);
		req.setAttribute("gestores_it", gestores_it);
		req.setAttribute("gestoresStr", gestoresStr);
		
		req.setAttribute("gestores_negocio", gestores_negocio);

		req.setAttribute("horasList", Utils.getHorasList());
		req.setAttribute("minutosList", Utils.getMinutosList());

		req.setAttribute("demandaList", dDao.getAllDemandas());

		return mapping.findForward("ok");
	}
}
