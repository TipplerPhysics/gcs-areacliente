package com.gcs.actions.modal;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Departamentos;
import com.gcs.beans.User;
import com.gcs.config.StaticConfig;
import com.gcs.dao.DepartamentosDao;
import com.gcs.dao.UserDao;

public class UserModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			UserDao uDao = UserDao.getInstance();
			List<User> usuarios = uDao.getAllUsers();
			
			DepartamentosDao dptDao = DepartamentosDao.getInstance();
			List<Departamentos> departamentos = dptDao.getAllDepartamentos();
			
			req.setAttribute("userList", usuarios);
			req.setAttribute("permisos", StaticConfig.permisos);
			req.setAttribute("departamentos", departamentos);
			//req.setAttribute("departamentos", StaticConfig.departamentos);			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("ok");
	}
}
