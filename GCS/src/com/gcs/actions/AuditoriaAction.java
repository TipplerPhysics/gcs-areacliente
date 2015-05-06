package com.gcs.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Log;
import com.gcs.dao.ContadorAuditoriaDao;
import com.gcs.dao.LogsDao;
import com.gcs.utils.Utils;


public class AuditoriaAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp){
		
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1){
			String accion = req.getParameter("p");
			
			LogsDao lDao = LogsDao.getInstance();		
			List<Log> logs = new ArrayList<Log>();
			
			Calendar fecha = Calendar.getInstance();
			fecha.add(Calendar.DATE, 0);
			String hoy = Utils.CalendarConverterToStr(fecha);
			fecha.add(Calendar.DATE, -1);
			String ayer = Utils.CalendarConverterToStr(fecha);
			
			/*
			if("lastweek".equals(accion)){
				logs = lDao.getLogsByLastWeek();
			}else if("lastmonth".equals(accion)){
				logs = lDao.getLogsByLastMonth();
			}else if("lastthreemonths".equals(accion)){
				logs = lDao.getLogsByLast3Months();
			}else{
				logs = lDao.getLogsByLastWeek();
	
			}
			*/
			
			////////////////////////////////////////////////////////////
			String page = req.getParameter("page");
			int pageint = Utils.stringToInt(page);	
			
			logs = lDao.getAllLogPagin(pageint);
			ContadorAuditoriaDao caDao = ContadorAuditoriaDao.getInstance();
			Integer cont = caDao.getContadorValue();
			int numpages = (cont/LogsDao.DATA_SIZE) + 1;			
			req.setAttribute("numpages", numpages);
			
			boolean lastpage = (logs.size() < LogsDao.DATA_SIZE) ? true : false;
			req.setAttribute("lastpage", lastpage);
			req.setAttribute("page", pageint);		
			////////////////////////////////////////////////////////////
			
			req.setAttribute("accion", accion);
			req.setAttribute("logs", logs);
			req.setAttribute("hoy", hoy);
			req.setAttribute("ayer", ayer);
			
			return  mapping.findForward("ok");
		
		}else{
			return  mapping.findForward("notAllowed");
		}
		
	}
}
