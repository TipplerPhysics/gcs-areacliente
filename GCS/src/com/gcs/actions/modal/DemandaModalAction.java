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
import com.gcs.beans.EstadoPeticion;
import com.gcs.beans.TipoPeticion;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.DemandaDao;
import com.gcs.dao.EstadoPeticionDao;
import com.gcs.dao.TipoPeticionDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;

public class DemandaModalAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		
		String git_str = req.getParameter("git");
		String gn_str = req.getParameter("gn");
		String cliente_str = req.getParameter("client");

		UserDao uDao = UserDao.getInstance();
		DemandaDao dDao = DemandaDao.getInstance();
		ClienteDao cDao = ClienteDao.getInstance();
		
		EstadoPeticionDao estPeticionDao = EstadoPeticionDao.getInstance();
		List<EstadoPeticion> estadoPeticion = estPeticionDao.getAllEstadoPeticion();
		
		TipoPeticionDao tpPeticionDao = TipoPeticionDao.getInstance();
		List<TipoPeticion> tipoPeticion = tpPeticionDao.getAllTipoPeticion();
		
		Cliente cliente = cDao.getClienteById(Long.parseLong(cliente_str));
		
		List<User> gestores_it_jdo = uDao.getUsersByPermisoStr(3);
		List<User> gestores_negocio_jdo = uDao.getUsersByPermisoStr(4);
		
		List<User> gestores_it = new ArrayList<User>();
		gestores_it.addAll(gestores_it_jdo);
		
		List<User> gestores_negocio = new ArrayList<User>();
		gestores_negocio.addAll(gestores_negocio_jdo);
		
		List<Cliente> clientes_jdo = cDao.getAllNonDeletedClients();
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes.addAll(clientes_jdo);
		
		if (!"".equals(git_str)){
			User git = uDao.getUserbyId(Long.parseLong(git_str));
			if (!gestores_it.contains(git)){
				gestores_it.add(git);
			}
		}
		
		
		if (!"".equals(gn_str) && Utils.isNumeric(gn_str)){
			User gn = uDao.getUserbyId(Long.parseLong(gn_str));
			if (!gestores_negocio.contains(gn)){
				gestores_negocio.add(gn);
			}
		}
		
		
		
		
		if (!clientes.contains(cliente)){
			clientes.add(cliente);
		}

		String gestoresStr = "";

		if (!gestores_it.isEmpty()) {
			/*for (User g : gestores_it) {
				gestoresStr += g.getNombre() + " " + g.getApellido1() + " "
						+ g.getApellido2() + "(" + g.getKey().getId() + ")"
						+ "-";
			} */

		}

		req.setAttribute("clientes", clientes);
		//req.setAttribute("gestores_demanda", gestores_demanda);
		req.setAttribute("gestores_it", gestores_it);
		//req.setAttribute("gestoresStr", gestoresStr);
		
		req.setAttribute("gestores_negocio", gestores_negocio);

		req.setAttribute("horasList", Utils.getHorasList());
		req.setAttribute("minutosList", Utils.getMinutosList());

		req.setAttribute("demandaList", dDao.getAllDemandas());
		
		req.setAttribute("estadoPeticion", estadoPeticion);
		req.setAttribute("tipoPeticion", tipoPeticion);

		return mapping.findForward("ok");
	}
}
