package com.gcs.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.actionForms.ClientsForm;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class ClientsAction extends Action {
	/**
	 * Author: David Martin
	 * @throws IOException 
	 */
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse resp) throws IOException{
			
		 return mapping.findForward("ok");
	 }
}
