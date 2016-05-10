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

import com.gcs.beans.Demanda;
import com.gcs.beans.Pais;
import com.gcs.beans.ServicioFile;
import com.gcs.beans.User;
import com.gcs.beans.Departamentos;
import com.gcs.config.StaticConfig;
import com.gcs.dao.ContadorServicioFileDao;
import com.gcs.dao.ContadorUserDao;
import com.gcs.dao.DemandaDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.ServicioFileDao;
import com.gcs.dao.UserDao;
import com.gcs.dao.DepartamentosDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class GestionCatalogoAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1){
			try {
				//UserDao uDao = UserDao.getInstance();
				//List<User> usuarios = uDao.getAllNonDeletedUsers();
				
				PaisDao paisesDao = PaisDao.getInstance();
				List<Pais> paises = paisesDao.getAllPaises();
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
				
				////////////////////////////////////////////////////////////
				ServicioFileDao sfDao = ServicioFileDao.getInstance();
				List<ServicioFile> servicios = new ArrayList <ServicioFile>();
				
				String nombreFilter = req.getParameter("name");
				
				
				String page = req.getParameter("page");
				int pageint = Utils.stringToInt(page);	
				
				if(nombreFilter!=null){
					String paisFilter = req.getParameter("nombrePais");
					String extensionesFilter = req.getParameter("extensiones");
					String paisIdFilter=req.getParameter("paisId");
					
					
					servicios = sfDao.getServicioByAllParam(nombreFilter, paisFilter, pageint);
					int numpages = (Integer.parseInt(servicios.get(servicios.size()-1).getDetalle())/ServicioFileDao.DATA_SIZE)+1;
					servicios.remove(servicios.size()-1);
					boolean lastpage = (servicios.size() < ServicioFileDao.DATA_SIZE) ? true : false;
					
					req.setAttribute("lastpage", lastpage);
					req.setAttribute("numpages", numpages);
					req.setAttribute("name", nombreFilter);
					req.setAttribute("pais", paisFilter);
					req.setAttribute("extensiones", extensionesFilter);
					req.setAttribute("paisId", paisIdFilter);
					
				}else{
					servicios = sfDao.getAllServicesPagin(pageint);
					ContadorServicioFileDao csfDao = ContadorServicioFileDao.getInstance();
					Integer cont = csfDao.getContadorValue();
					int numpages = (cont/ServicioFileDao.DATA_SIZE);
					if((cont % ServicioFileDao.DATA_SIZE)>0){
						numpages ++;
					}
					int np = pageint + 1;
					boolean lastpage = (np < numpages) ? false : true;
					
					req.setAttribute("lastpage", lastpage);
					req.setAttribute("numpages", numpages);
				}
				
				
				req.setAttribute("page", pageint);
				
				////////////////////////////////////////////////////////////
				
				
				req.setAttribute("serviciosList", servicios);
				
				// req.setAttribute("departamentos", StaticConfig.departamentos);
				req.setAttribute("paises", paises);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			return mapping.findForward("ok");
		}else{
			return mapping.findForward("notAllowed");
		}
	}
}
