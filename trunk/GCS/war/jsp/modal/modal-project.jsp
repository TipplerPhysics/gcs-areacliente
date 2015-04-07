  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal_ajax" id="project_modal">

	
<!--<button onclick="sendCloneProject();" id="new_project_form_modal" class="" type="button" style="float:right">Crear un nuevo proyecto con estos datos</button>-->
	<div>
		<h2>Editar Proyecto</h2>
		<hr>
	</div>
	
	
	<div class="new-user-form-holder">
		<form id="edit-project-form" name="edit-project-form" action="/projectServlet"
			method="POST" novalidate="novalidate">
			<div class="form-container">
				
				<div class="form-field-divider left">
					<div class="form-field">
						<span class="lbl">Fecha alta proyecto<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" id="fecha_alta_cliente_modal" readonly="" name="fecha_alta_cliente" class="readonly" maxlength="25">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">C&oacute;digo Proyecto<span class="required-asterisk">*</span>:</span>
						<input type="text" id="project_name_modal" readonly="" name="project_name" class="readonly" maxlength="25">
					</div>
					
					<div class="form-field">
							<span class="lbl">Tipo implementaci&oacuten<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" id="tipo_modal" name="tipo" readonly="" class="readonly" maxlength="25">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Subtipo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" id="subtipo_modal" name="subtipo" readonly="" class="readonly" maxlength="25">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">	
								<input type="hidden" id="input_cliente_id"  readonly name="cliente_id" class="readonly" value="hiddenValue" maxlength="25">							
								<input type="text" id="input_cliente_modal"  readonly name="cliente" class="readonly" maxlength="25">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Clasificaci&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker" name="clasificacion" id="clasificacion_modal" required aria-required="true">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>									
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestor IT<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_it_modal" name="gestor_it" required aria-required="true" data-live-search="true">	
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestor de negocio<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_negocio_modal" name="gestor_negocio" required aria-required="true" data-live-search="true">
									<c:forEach items="${gestores_negocio}" var="user">	
										<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
									</c:forEach>					
								</select>
							</div>
						</div>
						
					<div class="form-field">
						<span class="lbl">Coste<span class="required-asterisk">*</span>:</span>
						<input type="text" aria-required="true" readonly="" value="${coste}" id="coste_modal" name="coste" class="long euro readonly">
					</div>
					
					<div class="form-field">
							<span class="lbl">OK negocio valoración:</span>
							<input type="text" id="ok_negocio_check" value="${proyecto.str_OKNegocioCheck}" name="ok_negocio_check" class="long" maxlength="500">
					</div>
					
					<div class="form-field">
						<span class="lbl">Url carpeta Google Drive:</span>
						<input type="text" value="${proyecto.url_doc_google_drive}" id="url_doc_google_drive_modal" name="url_doc_google_drive" class="long">
					</div>

				</div>
				
				<div class="form-field-divider right">
					
					
					
					<div class="form-field">
						<span class="lbl">Producto<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select class="selectpicker selected" id="producto_modal" name="producto" required aria-required="true">
							    <option value="default">Seleccionar</option>
							    <c:forEach items="${productos}" var="producto">
							    	<option value="${producto.name}">${producto.name}</option>
								</c:forEach>											
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Conectividad:</span>
						<div class="input">
							<select class="selectpicker" id="conectividad_modal" name="conectividad" data-live-search="true">
							    <c:forEach items="${conectividades}" var="conectividad">
							    <option value="${conectividad.name}">${conectividad.name}</option>
								</c:forEach>												
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha inicio especificaciones:</span>
						<div class="input">
							<input type="text" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_valoracion_modal' name="fecha_inicio_valoracion" id="fecha_inicio_valoracion_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin especificaciones:</span>
						<div class="input">
							<input type="text" value="" size="16" maxlength="25" class="datepicker" name="fecha_fin_valoracion" id="fecha_fin_valoracion_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha inicio viabilidad:</span>
						<div class="input">
							<input type="text" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_viabilidad_modal' name="fecha_inicio_viabilidad" id="fecha_inicio_viabilidad_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin viabilidad:</span>
						<div class="input">
							<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_fin_viabilidad_modal' name="fecha_fin_viabilidad" id="fecha_fin_viabilidad_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha envio C100:</span>
						<div class="input">
							<input type="text" value="${proyecto.str_envioC100}" size="16" maxlength="25" class="datepicker" name="envio_c100" id="envio_c100_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha OK Negocio:</span>
						<div class="input">
							<input type="text" value="${proyecto.str_OKNegocio}" size="16" maxlength="25" class="datepicker" name="ok_negocio" id="ok_negocio_modal">
						</div>
					</div>
					

					<div class="form-field">
						<span class="lbl">Fecha envio plan trabajo:</span>
						<div class="input">
							<input type="text" value="${proyecto.str_fecha_plan_trabajo}" size="16" maxlength="25" class="datepicker "  name="fecha_plan_trabajo" id="fecha_plan_trabajo_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha disponible conectividad:</span>
						<div class="input">
							<input type="text" value="${proyecto.str_fecha_disponible_conectividad}" size="16" maxlength="25" class="datepicker" name="fecha_disponible_conectividad" id="fecha_disponible_conectividad_modal">
						</div>
					</div>
					
					
				</div>
				
				<div id="message_div">
					<span id="span_message"></span>
				</div>
			</div>
				
		

		</form>
	
		
			
	
	<div style="margin-bottom:10px;" class="message_div" id="message_div_demanda_modal">
		<span class="span_message" id="span_message_demanda_modal"></span>
	</div>
			
			
	</div>		
	<div class="modal-footer">
		<button onclick="sendEditProject();" id="edit_project_form_modal" class="" type="button">Guardar</button>
		<button data-dismiss="modal" class="dismis_edit_project" type="button">Cancelar</button>
	</div>	
</div>
