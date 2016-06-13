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




import com.gcs.beans.Pais;
import com.gcs.dao.PaisDao;


public class PaisModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			
			
			String id= req.getParameter("id");
			
			
			PaisDao pDao = PaisDao.getInstance();
			
			
			
			Pais p = pDao.getPaisById(Long.parseLong(id));
			
		    req.setAttribute("pais", p);
			
			
					} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("ok");
	}
}
