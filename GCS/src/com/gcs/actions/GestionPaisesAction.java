package com.gcs.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Pais;
import com.gcs.config.StaticConfig;
import com.gcs.dao.ContadorPaisDao;
import com.gcs.dao.ContadorUserDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class GestionPaisesAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1){
			try {
				
				////////////////////////////////////////////////////////////

				PaisDao paisesDao = PaisDao.getInstance();
				List<Pais> paises = paisesDao.getAllPaises();
				
				String nombreFilter = req.getParameter("name");
				
				String page = req.getParameter("page");
				int pageint = Utils.stringToInt(page);	
			if (nombreFilter != null )
				{
						paises = paisesDao.getPaisesByAllParam(nombreFilter.toUpperCase(), pageint);
						int numpages = (Integer.parseInt(paises.get(paises.size()-1).getDetalle())/PaisDao.DATA_SIZE)+1;
						paises.remove(paises.size()-1);
						boolean lastpage = (paises.size() < paisesDao.DATA_SIZE) ? true : false;
						
						req.setAttribute("lastpage", lastpage);
						req.setAttribute("numpages", numpages);
						req.setAttribute("name", nombreFilter);
					}
			else 
				{
					paises  = paisesDao.getAllPaisesPagin(pageint);
					ContadorPaisDao contPaisDao =  ContadorPaisDao.getInstance();
					Integer cont = contPaisDao.getContadorValue();
					int numpages = (cont/PaisDao.DATA_SIZE);
					if((cont % PaisDao.DATA_SIZE)>0){
						numpages ++;
					}
					int np = pageint + 1;
					boolean lastpage = (np < numpages) ? false : true;
					
					req.setAttribute("lastpage", lastpage);
					req.setAttribute("numpages", numpages);
				}
				
				
				req.setAttribute("page", pageint);
				
				////////////////////////////////////////////////////////////
	
				
				req.setAttribute("paisesList", paises);

			} catch (Exception e) {
				e.printStackTrace();
			}
	
			return mapping.findForward("ok");
		}else{
			return mapping.findForward("notAllowed");
		}
	}
}
