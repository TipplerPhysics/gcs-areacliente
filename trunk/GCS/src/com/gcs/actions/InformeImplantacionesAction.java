package com.gcs.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InformeImplantacionesAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// TODO: obtener valores del select de años. Obtener todos los años para los que hay informes.
		// El resto de combos se cargan via AJAX a través de un servlet
		
		// TODO: obtener el ultimo informe generado
		
		return mapping.findForward("ok");
	}
}
