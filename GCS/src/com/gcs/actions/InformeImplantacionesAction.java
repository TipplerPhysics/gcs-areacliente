package com.gcs.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gcs.beans.Cliente;
import com.gcs.beans.Informe;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.InformeDao;

public class InformeImplantacionesAction extends Action {
		
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// TODO: obtener valores del select de a�os. Obtener todos los a�os para los que hay informes.
		
		InformeDao iDao = InformeDao.getInstance();
		List<Informe> informes = iDao.getAllInformes();
		List<String> anyos= new ArrayList<String>();
		
		if(informes.isEmpty()){
			
			return mapping.findForward("ko");
			
		}else{
		
			for (Informe i : informes) {
			
				boolean existe = false;
				for (String anio:anyos){
					if(anio.equals(i.getAnyoImplantacion()))existe=true;
				}
				if (!existe)anyos.add(i.getAnyoImplantacion());
			
			}
			//Bloque para ordenar sino viniera ordenado desde el Dao
		
			/*Collections.sort(Anyos);
			Comparator<String> comparador = Collections.reverseOrder();
			Collections.sort(Anyos, comparador);*/
			
			req.setAttribute("anyos", anyos);
		
			// TODO: obtener el ultimo informe generado
			Informe ultimo = informes.get(informes.size()-1);
			
			req.setAttribute("ultimo_informe", ultimo);
			
			
		
			
			
			// El resto de combos se cargan via AJAX a trav�s de un servlet
		
		
			return mapping.findForward("ok");
		}
	}
}
