<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="modal_ajax">
		<div class="">
		
		<button onclick="sendCloneService();" id="new_service_form_modal" class="" type="button" style="float:right">Crear un nuevo servicio con estos datos</button>
					<h2>Editar Servicio</h2>
					<hr />
				</div>
				<div class="edit-user-form-holder">
				<form id="edit-servicio-form" name="edit-servicio-form" action="/serviceServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
					
						<div class="form-field">
							<span class="lbl">Servicio<span class="required-asterisk">*</span>:</span>
							<div class="input">
							
							<input type="text" id="name_modal" value="${servicio.name}"  name="name" class="long"  required aria-required="true" data-live-search="true">
							
								
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">País<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="pais" id="pais_servicio_modal" required aria-required="true" data-live-search="true"> 
									<option value="default">Seleccionar</option>
									<c:forEach items="${paises}" var="t">							
										<option value="${t.name}" ${servicio.nombrePais == t.name ? 'selected' : ''}>${t.name}</option>
									</c:forEach>																
								</select>
							</div>
						</div>
						
						
						
						
						</div>
						
						
												
						
						
						
						
						
					<div class="form-field-divider right">
						
						<div class="form-field">
							<span class="lbl">Extensiones<span class="required-asterisk">*</span>:</span>
							<div class="input">
							
							<input type="text" id="extensiones_modal" value="${servicio.extensiones}"  name="extensiones" class="long"  required aria-required="true" data-live-search="true">
							
								
							</div>
						</div>
						
						
						
						<%-- <div class="form-field">
							<span class="lbl">Fecha inicio operación cliente:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_op_cliente}" size="16" maxlength="25" class="datepicker" data-target-id='fecha_inicio_op_cliente_modal' name="fecha_inicio_op_cliente" id="fecha_inicio_op_cliente_modal">
							</div>
						</div> --%>
						
						<%-- <div class="form-field">
							<span class="lbl">Fecha paso ANS:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ANS}" size="16" maxlength="25" class="datepicker" data-target-id='ans_modal' name="ans" id="ans_modal">
							</div>
						</div> --%>
						
						<%-- <div class="form-field">
							<span class="lbl">Fecha estimada pruebas:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_pruebas}" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_pruebas_modal' name="fecha_inicio_pruebas" id="fecha_inicio_pruebas_modal">
							</div>
						</div> --%>
						
						<%-- <div class="form-field">
							<span class="lbl">Fecha prevista fin pruebas:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_fin_pruebas}" size="16" maxlength="25" class="datepicker" name="fecha_fin_pruebas" id="fecha_fin_pruebas_modal">
							</div>
						</div> --%>
						
						
						
						<%-- <div class="form-field">
							<span class="lbl">Fecha fin integradas:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_fin_integradas}" size="16" maxlength="25" class="datepicker" name="fecha_fin_integradas" id="fecha_fin_integradas_modal">
							</div>
						</div> --%>
						</div>					 
					
				</div>
			</form>
			<div id="message_div_modal" class="message_div" style='margin-bottom:10px;'>
				<span id="span_message_modal" class="span_message"></span>
			</div>
			
		</div>
			<div class="modal-footer">
				<button type="button" class="" id="edit_servicio_form_modal" onclick="sendEditServicio();">Guardar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
<div class="ajax_loader" id="ajax_loader">
	<img src="../../img/ajax-loader.gif" />
</div>