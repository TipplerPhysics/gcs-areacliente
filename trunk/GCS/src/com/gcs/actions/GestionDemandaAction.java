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

import com.gcs.beans.Cliente;
import com.gcs.beans.Demanda;
import com.gcs.beans.EstadoPeticion;
import com.gcs.beans.User;
import com.gcs.beans.TipoPeticion;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ContadorDemandaDao;
import com.gcs.dao.DemandaDao;
import com.gcs.dao.EstadoPeticionDao;
import com.gcs.dao.UserDao;
import com.gcs.dao.TipoPeticionDao;
import com.gcs.utils.Utils;

public class GestionDemandaAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1||sesionpermiso==2||sesionpermiso==3){
			EstadoPeticionDao estPeticionDao = EstadoPeticionDao.getInstance();
			List<EstadoPeticion> estadoPeticion = estPeticionDao.getAllEstadoPeticion();
			
			TipoPeticionDao tpPeticionDao = TipoPeticionDao.getInstance();
			List<TipoPeticion> tipoPeticion = tpPeticionDao.getAllTipoPeticion();
			
			UserDao uDao = UserDao.getInstance();
			//DemandaDao dDao = DemandaDao.getInstance();
			ClienteDao cDao = ClienteDao.getInstance();
			List<User> gestores_demanda = uDao.getUsersByPermisoStr(2);
			List<User> gestores_it = uDao.getUsersByPermisoStr(3);
			List<User> gestores_negocio = uDao.getUsersByPermisoStr(4);
			List<Cliente> clientes = cDao.getAllClientes();
	
			String gestoresStr = "";
	
			if (!gestores_it.isEmpty()) {
				for (User g : gestores_it) {
					gestoresStr += g.getNombre() + " " + g.getApellido1() + " "
							+ g.getApellido2() + "(" + g.getKey().getId() + ")"
							+ "-";
				}
	
			}
			
			////////////////////////////////////////////////////////////
			DemandaDao dDao = DemandaDao.getInstance();
			List<Demanda> demandas = new ArrayList <Demanda>();
			
			String fechaEntradaFilter = req.getParameter("fecha");
			
			
			String page = req.getParameter("page");
			int pageint = Utils.stringToInt(page);	
			
			if(fechaEntradaFilter!=null){
				String nclienteFilter = req.getParameter("cliente");
				String tipoFilter = req.getParameter("tipo");
				String estadoFilter = req.getParameter("estado");
				String codPeticionFilter = req.getParameter("cPeticion");
				
				demandas = dDao.getDemandaByAllParam(fechaEntradaFilter, nclienteFilter, tipoFilter, estadoFilter, codPeticionFilter, pageint);
				int numpages = (Integer.parseInt(demandas.get(demandas.size()-1).getDetalle())/DemandaDao.DATA_SIZE)+1;
				demandas.remove(demandas.size()-1);
				boolean lastpage = (demandas.size() < DemandaDao.DATA_SIZE) ? true : false;
				
				req.setAttribute("lastpage", lastpage);
				req.setAttribute("numpages", numpages);
				req.setAttribute("fecha", fechaEntradaFilter);
				req.setAttribute("cliente", nclienteFilter);
				req.setAttribute("tipo", tipoFilter);
				req.setAttribute("estado", estadoFilter);
				req.setAttribute("cPeticion", codPeticionFilter);
			}else{
				demandas = dDao.getAllDemandaPagin(pageint);
				ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();
				Integer cont = cdDao.getContadorValue();
				int numpages = (cont/DemandaDao.DATA_SIZE);
				if((cont % DemandaDao.DATA_SIZE)>0){
					numpages ++;
				}
				int np = pageint + 1;
				boolean lastpage = (np < numpages) ? false : true;
				
				req.setAttribute("lastpage", lastpage);
				req.setAttribute("numpages", numpages);
			}
			
			req.setAttribute("page", pageint);
			
			////////////////////////////////////////////////////////////
	
			req.setAttribute("clientes", clientes);
			req.setAttribute("gestores_demanda", gestores_demanda);
			req.setAttribute("gestores_it", gestores_it);
			req.setAttribute("gestoresStr", gestoresStr);
			
			req.setAttribute("gestores_negocio", gestores_negocio);
	
			req.setAttribute("horasList", Utils.getHorasList());
			req.setAttribute("minutosList", Utils.getMinutosList());
	
			//req.setAttribute("demandaList", dDao.getAllDemandas());
			req.setAttribute("demandaList", demandas);
			req.setAttribute("estadoPeticion", estadoPeticion);
			req.setAttribute("tipoPeticion", tipoPeticion);
	
			return mapping.findForward("ok");
			
		}else{
			return mapping.findForward("notAllowed");
		}
	}
}
