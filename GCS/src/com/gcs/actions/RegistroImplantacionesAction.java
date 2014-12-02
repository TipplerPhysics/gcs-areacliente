package com.gcs.actions;

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
import com.gcs.beans.Conectividad;
import com.gcs.beans.Implantacion;
import com.gcs.beans.Servicio;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.ImplantacionDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;

public class RegistroImplantacionesAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		ConectividadDao cDao = ConectividadDao.getInstance();
		ServicioDao sDao = ServicioDao.getInstance();
		
		List<Conectividad> conectividades = cDao.getConectividadEnCurso();
		List<Servicio> servicios = sDao.getServicioEnCurso();
		
		if(conectividades == null && servicios == null) {
			conectividades = cDao.getConectividadesByEstado("PDTE Implantar");
			servicios = sDao.getServiciosByEstado("PDTE Implantar");
		}
		
		List<Implantacion> implantaciones = new ArrayList<Implantacion>();
		
		if (!conectividades.isEmpty()) {
			Implantacion impl = null;
			for (Conectividad c : conectividades) {
				impl = new Implantacion();
				//impl.setConectividadKey(c.getKey().getId());
				// clave conectividad
				// clave proyecto
				// fecha implantacion 
				// nombre cliente
				// estado
				// gestor it
				// nombre conectividad
				// estado subida
				// detalle subida
				implantaciones.add(impl);
			}
		}
		
		if (!servicios.isEmpty()) {
			Implantacion impl = null;
			for (Servicio s : servicios) {
				impl = new Implantacion();
				// clave servicio
				// clave proyecto
				// fecha implantacion 
				// nombre cliente
				// estado
				// gestor it
				// nombre servicio
				// estado subida
				// detalle subida
				implantaciones.add(impl);
			}
		}
		
		
		req.setAttribute("implantacionList", implantaciones);
		
		
		
		/*UserDao uDao = UserDao.getInstance();
		ImplantacionDao dDao = ImplantacionDao.getInstance();
		ClienteDao cDao = ClienteDao.getInstance();
		List<User> gestores_demanda = uDao.getUsersByPermisoStr(2);
		List<User> gestores_it = uDao.getUsersByPermisoStr(3);
		List<User> gestores_negocio = uDao.getUsersByPermisoStr(4);
		List<Cliente> clientes = cDao.getAllClientes();

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
		//req.setAttribute("gestoresStr", gestoresStr);
		
		req.setAttribute("gestores_negocio", gestores_negocio);

		//req.setAttribute("horasList", Utils.getHorasList());
		//req.setAttribute("minutosList", Utils.getMinutosList());*/

		//req.setAttribute("implantacionList", dDao.getAllImplantaciones());

		return mapping.findForward("ok");
	}
}
