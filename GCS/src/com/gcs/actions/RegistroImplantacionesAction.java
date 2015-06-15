package com.gcs.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Conectividad;
import com.gcs.beans.Implantacion;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;
import com.gcs.utils.Utils;

public class RegistroImplantacionesAction extends Action {
	
	private static final String PENDIENTE_IMPL = "PDTE IMPLANTAR";
	
	private static final String CONFIRMADO = "CONFIRMADO";
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		
		HttpSession sesion = req.getSession();
		int sesionpermiso = (int) sesion.getAttribute("permiso");
		
		if(sesionpermiso==1||sesionpermiso==2||sesionpermiso==3){
			ConectividadDao cDao = ConectividadDao.getInstance();
			ServicioDao sDao = ServicioDao.getInstance();
			ProyectoDao pDao = ProyectoDao.getInstance();
			
			Boolean implantacionEnCurso = false;
			Boolean puedeGenerarInforme = false;
			
			List<Conectividad> conectividades = cDao.getConectividadesEnCurso();
			List<Servicio> servicios = sDao.getServiciosEnCurso();
			req.setAttribute("conectividades", conectividades);
			req.setAttribute("servicios", servicios);
			if(conectividades == null && servicios == null) {
				conectividades = cDao.getConectividadesByEstado(PENDIENTE_IMPL);
				servicios = sDao.getServiciosByEstado(PENDIENTE_IMPL);
			}
			else {
				implantacionEnCurso = true;
				
				// checkea si los servicios y conectividades en curso estan en estado confirmado
				List<Conectividad> conectividadesConfirmado = cDao.getConectividadesByEstadoImplantacion(CONFIRMADO);
				List<Servicio> servicioConfirmado = sDao.getServiciosByEstadoImplantacion(CONFIRMADO);
				if(conectividadesConfirmado != null || servicioConfirmado != null) {
					puedeGenerarInforme = true;
				}
			}
			
			List<Implantacion> implantaciones = new ArrayList<Implantacion>();
			
			if (conectividades !=null && !conectividades.isEmpty()) {
				Implantacion impl = null;
				Proyecto p = null;
				for (Conectividad c : conectividades) {
					p = pDao.getProjectbyId(c.getKey_proyecto());
					impl = new Implantacion();
					// clave conectividad
					impl.setConectividadkey(c.getKey().getId());				
					// clave proyecto
					impl.setProyectokey(c.getKey_proyecto());
					// fecha implantacion 
					Date fechaImplantacion = c.getFecha_implantacion();
					if(fechaImplantacion != null) {
						impl.setFecha_implantacion(fechaImplantacion);
						impl.setFecha_implantacion_str(Utils.dateConverterToStr(fechaImplantacion));
					}
					// nombre cliente
					impl.setClienteName(p.getClienteName());
					// estado
					impl.setEstado(c.getEstado());
					// gestor it
					impl.setGestor_it_name(p.getGestor_it_name());
					// nombre conectividad
					impl.setConectividad(p.getConectividad());
					// estado subida
					impl.setEstadoSubida(c.getEstadoSubida());
					// detalle subida
					impl.setDetalleSubida(c.getdetalleSubida());
					
					implantaciones.add(impl);
				}
			}
			
			if (servicios !=null && !servicios.isEmpty()) {
				Implantacion impl = null;
				Proyecto p = null;
				for (Servicio s : servicios) {
					p = pDao.getProjectbyId(s.getId_proyecto());
					impl = new Implantacion();
					// clave servicio
					impl.setServiciokey(s.getKey().getId());
					// clave proyecto
					impl.setProyectokey(s.getId_proyecto());
					// fecha implantacion 
					Date fechaImplantacion = s.getFecha_implantacion_produccion();
					if(fechaImplantacion != null) {
						impl.setFecha_implantacion(fechaImplantacion);
						impl.setFecha_implantacion_str(Utils.dateConverterToStr(fechaImplantacion));
					}
					// nombre cliente
					impl.setClienteName(p.getClienteName());
					// estado
					impl.setEstado(s.getEstado());
					// gestor it
					impl.setGestor_it_name(p.getGestor_it_name());
					// nombre servicio
					impl.setServicio(s.getServicio());
					// estado subida
					impl.setEstadoSubida(s.getEstadoSubida());
					// detalle subida
					impl.setDetalleSubida(s.getdetalleSubida());
					
					implantaciones.add(impl);
				}
			}
					
			req.setAttribute("implantacionEnCurso", implantacionEnCurso);
			req.setAttribute("puedeGenerarInforme", puedeGenerarInforme);		
			req.setAttribute("implantacionList", implantaciones);
			
			return mapping.findForward("ok");
		}else{
			return mapping.findForward("notAllowed");
		}
		
	}
}
