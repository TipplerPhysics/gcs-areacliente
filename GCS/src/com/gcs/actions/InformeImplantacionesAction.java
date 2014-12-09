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

		// TODO: obtener valores del select de años. Obtener todos los años para los que hay informes.
		
		InformeDao iDao = InformeDao.getInstance();
		List<Informe> informes = iDao.getAllInformes();
		List<String> Anyos= new ArrayList<String>();
		
		if(informes.isEmpty()){
			
			return mapping.findForward("ko");
			
		}else{
		
			for (Informe i : informes) {
			
				if(Anyos.indexOf(i.getAnyoImplantacion())==-1){
					Anyos.add(i.getAnyoImplantacion());
				}
			
			}
			//Bloque para ordenar sino viniera ordenado desde el Dao
		
			/*Collections.sort(Anyos);
			Comparator<String> comparador = Collections.reverseOrder();
			Collections.sort(Anyos, comparador);*/
			
			req.setAttribute("informe_select_anyo", Anyos);
		
			// TODO: obtener el ultimo informe generado
			Informe ultimo = informes.get(informes.size()-1);
			
			req.setAttribute("ultimo_informe", ultimo);
			
			
		
			
			
			// El resto de combos se cargan via AJAX a través de un servlet
		
		
			return mapping.findForward("ok");
		}
	}
}
