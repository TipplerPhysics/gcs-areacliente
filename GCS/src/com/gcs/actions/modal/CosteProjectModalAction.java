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

import com.gcs.beans.Coste;
import com.gcs.beans.User;
import com.gcs.dao.CosteDao;
import com.gcs.dao.UserDao;

public class CosteProjectModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
			UserDao uDao = UserDao.getInstance();

			String id = req.getParameter("id");
			
			CosteDao cDao = CosteDao.getInstance();
			Coste c = cDao.getCostebyId(Long.parseLong(id));
			
			
				
			
			List<User> gestores_it_jdo = uDao.getUsersByPermisoStr(3);
			
			
			List<User> gestores_it = new ArrayList<User>();
			gestores_it.addAll(gestores_it_jdo);
				
			String git_str = req.getParameter("git");
			if (!"undefined".equals(git_str)){
				User git = uDao.getUserbyId(Long.parseLong(git_str));
				if (!gestores_it.contains(git)){
					gestores_it.add(git);
				}
			}
			
			req.setAttribute("gestores_it", gestores_it);
			req.setAttribute("coste", c);

		

		return mapping.findForward("ok");
	}
}
