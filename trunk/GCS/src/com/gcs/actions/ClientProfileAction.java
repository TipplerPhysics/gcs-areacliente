package com.gcs.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Cliente;
import com.gcs.beans.Conectividad;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;

public class ClientProfileAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp){
		
		ClienteDao cDao = ClienteDao.getInstance();
		ProyectoDao pDao = ProyectoDao.getInstance();
		ServicioDao servDao = ServicioDao.getInstance();
		ConectividadDao conectDao = ConectividadDao.getInstance();
		
		try{
			String id_str = req.getParameter("id");
			
			Cliente c = cDao.getClienteById(Long.parseLong(id_str));
			List<Proyecto> projects = pDao.getProjectsByClient(c.getKey().getId());
			
			req.setAttribute("projects", projects);
			req.setAttribute("cliente", c);

			
			
			List<Servicio> servicios = new ArrayList<Servicio>();
			List<Conectividad> conectividades = new ArrayList<Conectividad>();
			List<String> productos = new ArrayList<String>();
			
			for (Proyecto proyect : projects){
				List<Servicio> serviciosAux = servDao.getServiciosByProject(proyect.getKey().getId());
				servicios.addAll(serviciosAux);
				
				List<Conectividad> conectividadesAux = conectDao.getConectividadesByProject(proyect.getKey().getId());
				conectividades.addAll(conectividadesAux);
				
				if(!productos.contains(proyect.getProducto())) productos.add(proyect.getProducto());
				
			}
			
			
			req.setAttribute("servicios", servicios);
			req.setAttribute("conectividades", conectividades);
			req.setAttribute("productos", productos);
			
			boolean enproduccion = false;
			boolean enimplementacion = true;
			
			for (Servicio ser : servicios){
				if(ser.getEstadoImplantacion()!=null&&ser.getEstado()!=null){
					if(ser.getEstadoImplantacion().equals("Produccion"))enproduccion=true;
					if(ser.getEstado().equals("Parado")||ser.getEstado().equals("Excluido"))enimplementacion=false;
				}
			}
			
			for (Conectividad ser : conectividades){
				if(ser.getEstadoImplantacion()!=null&&ser.getEstado()!=null){
					if(ser.getEstadoImplantacion().equals("Produccion"))enproduccion=true;
					if(ser.getEstado().equals("Parado")||ser.getEstado().equals("Excluido"))enimplementacion=false;
				}
			}
			
			
			req.setAttribute("enproduccion", enproduccion);
			req.setAttribute("enimplementacion", enimplementacion);
			
			
			
			return mapping.findForward("ok");
		}catch(Exception e){
			return  mapping.findForward("ko");
		}

		
	}
}
