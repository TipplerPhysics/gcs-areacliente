package com.gcs.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.User;
import com.gcs.config.StaticConfig;
import com.gcs.dao.UserDao;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class UsersAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			UserDao uDao = UserDao.getInstance();
			List<User> usuarios = uDao.getAllUsers();

			// req.getSession().setAttribute("userList", usuarios);

			/*
			 * for (User u:usuarios){ JSONObject jsonUser = new JSONObject();
			 * jsonUser.put("name", u.getNombre()); jsonUser.put("ap1",
			 * u.getApellido1()); jsonUser.put("ap2", u.getApellido2());
			 * jsonUser.put("email", u.getEmail()); jsonUser.put("permiso",
			 * u.getPermiso()); jsonUser.put("permisoStr", u.getPermisoStr());
			 * jsonUser.put("areas", u.getAreas()); jsonUser.put("email",
			 * u.getDepartamento()); jsonUser.put("id", u.getKey().getId());
			 * jsonArray.put(jsonUser); } req.setAttribute("userJson",
			 * jsonArray);
			 */
			req.setAttribute("userList", usuarios);
			req.setAttribute("permisos", StaticConfig.permisos);
			req.setAttribute("departamentos", StaticConfig.departamentos);			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("ok");
	}
}
