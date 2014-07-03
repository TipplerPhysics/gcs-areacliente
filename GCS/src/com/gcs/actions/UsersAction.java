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
import com.gcs.dao.UserDao;

public class UsersAction extends Action{

	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse resp) throws IOException{		
		 
		UserDao uDao = UserDao.getInstance();
		List<User> usuarios = uDao.getAllUsers();
		
		//req.getSession().setAttribute("userList", usuarios);
		req.setAttribute("userList", usuarios);
		
		
		
		return mapping.findForward("ok");
	 }
}
