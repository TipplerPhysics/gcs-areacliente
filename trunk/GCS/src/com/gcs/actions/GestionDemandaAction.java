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
import com.gcs.dao.DemandaDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;

public class GestionDemandaAction extends Action {
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		UserDao uDao = UserDao.getInstance();
		DemandaDao dDao = DemandaDao.getInstance();
		List<User> gestores_demanda = uDao.getUsersByPermisoStr(4);
		List<User> gestores_it = uDao.getUsersByPermisoStr(5);
			
		req.setAttribute("gestores_demanda", gestores_demanda);
		req.setAttribute("gestores_it", gestores_it);
		
		req.setAttribute("horasList", Utils.getHorasList());
		req.setAttribute("minutosList", Utils.getMinutosList());
		
		req.setAttribute("demandaList", dDao.getAllDemandas());
		
		 return mapping.findForward("ok");
	 }
}
