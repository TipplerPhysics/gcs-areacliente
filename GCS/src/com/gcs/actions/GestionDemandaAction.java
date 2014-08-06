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
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.DemandaDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;

public class GestionDemandaAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UserDao uDao = UserDao.getInstance();
		DemandaDao dDao = DemandaDao.getInstance();
		ClienteDao cDao = ClienteDao.getInstance();
		List<User> gestores_demanda = uDao.getUsersByPermisoStr(4);
		List<User> gestores_it = uDao.getUsersByPermisoStr(5);
		List<User> gestores_negocio = uDao.getUsersByPermisoStr(6);
		List<Cliente> clientes = cDao.getAllCliente();

		String gestoresStr = "";

		if (!gestores_it.isEmpty()) {
			for (User g : gestores_it) {
				gestoresStr += g.getNombre() + " " + g.getApellido1() + " "
						+ g.getApellido2() + "(" + g.getKey().getId() + ")"
						+ "-";
			}

		}

		req.setAttribute("clientes", clientes);
		req.setAttribute("gestores_demanda", gestores_demanda);
		req.setAttribute("gestores_it", gestores_it);
		req.setAttribute("gestoresStr", gestoresStr);
		
		req.setAttribute("gestores_negocio", gestores_negocio);

		req.setAttribute("horasList", Utils.getHorasList());
		req.setAttribute("minutosList", Utils.getMinutosList());

		req.setAttribute("demandaList", dDao.getAllDemandas());

		return mapping.findForward("ok");
	}
}
