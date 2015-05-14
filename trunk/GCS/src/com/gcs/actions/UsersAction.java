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
import com.gcs.beans.User;
import com.gcs.beans.Departamentos;
import com.gcs.config.StaticConfig;
import com.gcs.dao.ContadorUserDao;
import com.gcs.dao.DemandaDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.UserDao;
import com.gcs.dao.DepartamentosDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class UsersAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1){
			try {
				//UserDao uDao = UserDao.getInstance();
				//List<User> usuarios = uDao.getAllNonDeletedUsers();
				
				DepartamentosDao dptDao = DepartamentosDao.getInstance();
				List<Departamentos> departamentos = dptDao.getAllDepartamentos();
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
				UserDao uDao = UserDao.getInstance();
				List<User> usuarios = new ArrayList <User>();
				
				String nombreFilter = req.getParameter("nombre");
				
				
				String page = req.getParameter("page");
				int pageint = Utils.stringToInt(page);	
				
				if(nombreFilter!=null){
					String apellido1Filter = req.getParameter("apellido1");
					String apellido2Filter = req.getParameter("apellido2");
					String departamentoFilter = req.getParameter("departamento");
					String permisoStrFilter = req.getParameter("permisoStr");
					
					usuarios = uDao.getUserByAllParam(nombreFilter, apellido1Filter, apellido2Filter, departamentoFilter, permisoStrFilter, pageint);
					int numpages = (Integer.parseInt(usuarios.get(usuarios.size()-1).getDetalle())/UserDao.DATA_SIZE)+1;
					usuarios.remove(usuarios.size()-1);
					boolean lastpage = (usuarios.size() < UserDao.DATA_SIZE) ? true : false;
					
					req.setAttribute("lastpage", lastpage);
					req.setAttribute("numpages", numpages);
					req.setAttribute("nombre", nombreFilter);
					req.setAttribute("apellido1", apellido1Filter);
					req.setAttribute("apellido2", apellido2Filter);
					req.setAttribute("departamento", departamentoFilter);
					req.setAttribute("permisoStr", permisoStrFilter);
				}else{
					usuarios = uDao.getAllUserPagin(pageint);
					ContadorUserDao cuDao = ContadorUserDao.getInstance();
					Integer cont = cuDao.getContadorValue();
					int numpages = (cont/UserDao.DATA_SIZE);
					if((cont % UserDao.DATA_SIZE)>0){
						numpages ++;
					}
					int np = pageint + 1;
					boolean lastpage = (np < numpages) ? false : true;
					
					req.setAttribute("lastpage", lastpage);
					req.setAttribute("numpages", numpages);
				}
				
				
				req.setAttribute("page", pageint);
				
				////////////////////////////////////////////////////////////
	
				
				req.setAttribute("userList", usuarios);
				req.setAttribute("permisos", StaticConfig.permisos);
				// req.setAttribute("departamentos", StaticConfig.departamentos);
				req.setAttribute("departamentos", departamentos);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			return mapping.findForward("ok");
		}else{
			return mapping.findForward("notAllowed");
		}
	}
}
