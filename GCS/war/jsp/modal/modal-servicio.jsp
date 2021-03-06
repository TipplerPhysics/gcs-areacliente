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
							<span class="lbl">Cód. de proyecto<span class="required-asterisk">*</span>:</span>
							<div class="input">
							
							<input type="text" id="cod_proyecto_modal" value="${servicio.cod_proyecto}" class="readonly" readonly  name="cod_proyecto" class="long">
							
								
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">País<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="pais" id="pais_servicio_modal" required aria-required="true" data-live-search="true"> 
									<option value="default">Seleccionar</option>
									<c:forEach items="${paises}" var="t">							
										<option value="${t.name}" ${servicio.pais == t.name ? 'selected' : ''}>${t.name}</option>
									</c:forEach>																
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Servicio<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="servicio" id="servicio_modal" required aria-required="true" data-live-search="true">
									
									<c:forEach items="${servicios_pais}" var="s">	
										<option value="${s.name}" ${servicio.servicio == s.name ? 'selected' : ''}>${s.name}</option>
									</c:forEach>	
																					
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Extensi&oacuten<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="extension" id="extension_modal" required aria-required="true" data-live-search="true">
									<c:forEach items="${extensiones}" var="ext">	
										<option value="${ext}" ${servicio.extension == ext ? 'selected' : ''}>${ext}</option>
									</c:forEach>																
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Flujo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select name="flujo" id="flujo_modal" class="selectpicker selected" required aria-required="true">
									<option selected value="${servicio.flujo}">${servicio.flujo}</option>
									<option value="default">Seleccionar</option>
									<option value="Antiguo">Antiguo</option>
									<option value="B-C">B-C</option>
									<option value="C-B">C-B</option>
								</select>
							</div>
						</div>
												
						<div class="form-field">
							<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="estado" id="estado_modal" required aria-required="true" data-live-search="true"> 
									<option value="default">Seleccionar</option>
										<c:forEach items="${estados}" var="e">							
											<option value="${e.name}" ${servicio.estado == e.name ? 'selected' : ''}>${e.name}</option>
										</c:forEach>																
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestor Pruebas:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_it_modal" name="gestor_pruebas">
								<option value="default">${servicio.gestor_pruebas_name}</option>	
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
									
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Cód. Redmine:</span>
							<input type="text" id="cod_servicio_modal" value="${servicio.cod_servicio}" maxlength="25" name="cod_servicio" class="long">
						</div>
						
						<!-- <div class="form-field">
							<span class="lbl">Cód. Redmine<span class="required-asterisk">*</span>:</span>
							<input type="text" id="cod_servicio_modal" value="${servicio.cod_servicio}" maxlength="25" name="cod_servicio" class="long" required aria-required="true">
						</div> -->
						
						<div class="form-field">
							<span class="lbl">Formato de entrada/salida<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="formato_intermedio" id="formato_intermedio_modal" required aria-required="true" data-live-search="true"> 
									<option value="default">${servicio.formato_intermedio}</option>
										<c:forEach items="${formatos}" var="form">	
										<option value="${form}" ${servicio.formato_intermedio == form ? 'selected' : ''}>${form}</option>
									</c:forEach>																	
								</select>
							</div>
						</div>
						
						
						<div class="form-field">
							<span class="lbl">Formato local:</span>
							<input type="text" id="formato_local_modal" value="${servicio.formato_local}" maxlength="25" name="formato_local" class="long">
						</div>
						
						
						
						<div class="form-field">
							<span class="lbl">Referencia local integrado:</span>
							<input type="text" id="ref_local1_modal" value="${servicio.referencia_local1}" maxlength="25" name="ref_local1" class="long">
							
						</div>
						
						<div class="form-field">
							<span class="lbl">Referencia local producción:</span>
						<input type="text" id="ref_local2_modal" value="${servicio.referencia_local2}" maxlength="25" name="ref_local2" class="long">
						</div>
						
						<div class="form-field">
							<span class="lbl">BEI:</span>
						<input type="text" id="cod_bei_modal" value="${servicio.cod_bei}" maxlength="11" name="cod_bei" class="long">
						</div>
					</div>
					<div class="form-field-divider right">
						
						
						<%-- <div class="form-field">
							<span class="lbl">Fecha inicio aceptación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_aceptacion}" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_aceptacion_modal' name="fecha_inicio_aceptacion" id="fecha_inicio_aceptacion_modal">
							</div>
						</div> --%>
						
						<div class="form-field">
							<span class="lbl">Tipo Desarrollo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select name="tipo_desarrollo" id="tipo_desarrollo" class="long selectpicker selected" required aria-required="true">
									<option selected value="${servicio.tipo_desarrollo}">${servicio.tipo_desarrollo}</option>
									<option value="Antiguo">Antiguo</option>
									<option value="Soporte a Pruebas">Soporte a Pruebas</option>
									<option value="Redescargas">Redescargas</option>
									<option value="Normalización">Normalización</option>
									<option value="Respuestas Dedicadas">Respuestas Dedicadas</option>
									<option value="Normalización + Redescargas">Normalización + Redescargas</option>
									<option value="Normalización + Respuestas Dedicadas">Normalización + Respuestas Dedicadas</option>
									<option value="Redescarga + Respuestas Dedicadas">Redescarga + Respuestas Dedicadas</option>
									<option value="Normalización + Redescargas + Respuestas Dedicadas">Normalización + Redescargas + Respuestas Dedicadas</option>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Escenario OPI:</span>
							<div class="input">
								<select name="escenario_opi" id="escenario_opi_modal" class="selectpicker">
									<option value="${servicio.escenario_opi}">${servicio.escenario_opi}</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="OPI-1">OPI-1</option>
									<option value="OPI-2">OPI-2</option>
									<option value="OPI-3">OPI-3</option>
									<option value="OPI-4">OPI-4</option>
								</select>
							</div>
						</div>
						
						
						<div class="form-field">
							<span class="lbl">Fecha inicio integradas:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_integradas}" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_integradas_modal' name="fecha_inicio_integradas" id="fecha_inicio_integradas_modal">
							</div>
						</div>
						
						<div class="form-field">	
							<span class="lbl">Fecha fin aceptación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_fin_aceptacion}" size="16" maxlength="25" class="datepicker" name="fecha_fin_aceptacion" id="fecha_fin_aceptacion_modal">
							</div>
						</div>
						
						
						<%-- <div class="form-field">
							<span class="lbl">Fecha inicio validación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_validacion}" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_validacion_modal' name="fecha_inicio_validacion" id="fecha_inicio_validacion_modal">
							</div>
						</div> --%>
						
						<%-- <div class="form-field">
							<span class="lbl">Fecha fin validación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_fin_validacion}" size="16" maxlength="25" class="datepicker" name="fecha_fin_validacion" id="fecha_fin_validacion_modal">
							</div>
						</div> --%>
						
						<div class="form-field">
							<span class="lbl">Fecha Implantación-Producción:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_implantacion_produccion}" size="16" maxlength="25" class="datepicker" data-target-id='fecha_implantacion_produccion_modal' name="fecha_implantacion_produccion" id="fecha_implantacion_produccion_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio primera operación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_primera_operacion}" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_primera_op_modal' name="fecha_inicio_primera_op" id="fecha_inicio_primera_op_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin primera operación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_fin_primera_operacion}" size="16" maxlength="25" class="datepicker" name="fecha_fin_primera_op" id="fecha_fin_primera_op_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Observaciones:</span>
							<div class="input">
								<textarea name="observaciones"  maxlength="500" id="observaciones_modal">${servicio.observaciones}</textarea>
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