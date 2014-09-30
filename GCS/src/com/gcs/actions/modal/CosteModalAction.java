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

import com.gcs.beans.User;
import com.gcs.config.StaticConfig;
import com.gcs.dao.UserDao;

public class CosteModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
			UserDao uDao = UserDao.getInstance();

			
			String git_str = req.getParameter("git");
			User git = uDao.getUserbyId(Long.parseLong(git_str));
			
			List<User> gestores_it_jdo = uDao.getUsersByPermisoStr(3);
			
			
			List<User> gestores_it = new ArrayList<User>();
			gestores_it.addAll(gestores_it_jdo);
				
			if (!gestores_it.contains(git)){
				gestores_it.add(git);
			}
			
			req.setAttribute("gestores_it", gestores_it);

		

		return mapping.findForward("ok");
	}
}
