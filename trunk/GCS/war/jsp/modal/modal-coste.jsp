  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="modal_ajax">
		<div class="">
					<h2>Editar Coste</h2>
					<hr />
				</div>
				<div class="edit-user-form-holder">
				<form id="edit-coste-form" name="new-coste-form" action="/costeServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
					
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" class="readonly" readonly  name="cliente" id="cliente_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Código de control:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25"  name="numero_control" id="numero_control_modal">
							</div>
						</div>

						
						<div class="form-field">
							<span class="lbl">Fecha alta costes<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_alta_costes_modal' name="fecha_alta_costes" id="fecha_alta_costes_modal" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Número valoración<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select name="num_valoracion" id="num_valoracion_modal" class="long selectpicker selected" required aria-required="true">
									<option value="default">Seleccionar</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha solicitud valoración<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_solicitud_valoracion_modal' name="fecha_solicitud_valoracion" id="fecha_solicitud_valoracion_modal" required aria-required="true">
							</div>
						</div>
						

					</div>
					<div class="form-field-divider right">
						<div class="form-field">
							<span class="lbl">Nombre proyectos<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" class="readonly" readonly name="project" id="project_modal">						
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Equipo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="equipo_modal" name="equipo" class="long selectpicker selected">
									<option value="Innovery">Innovery</option>
									<option value="Capgemini">Capgemini</option>
									<option value="Gestor IT">Gestor IT</option>
									<option value="Innovery">Innovery</option>
									<option value="IS">IS</option>									
									<option value="Solutions">Solutions</option>
									<option value="Soporte Swift">Soporte Swift</option>									
									<option value="Telemáticos">Telemáticos</option>
								</select>
							</div>
						</div>
										
						<div class="form-field">
							<span class="lbl">Gestor IT-registro<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_it_modal" name="gestor_it">	
								
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Comentarios:</span>
							<div class="input">
								<textarea name="comentarios" id="comentarios_modal"></textarea>
							</div>
						</div>
						
							
					</div>	
					<div class="form-field-down">
						<span class="lbl">Horas/Fases:</span>
						<div class="form-field">
							<span class="lbl">An&aacute;lisis:</span>
							<div class="input">
								<input id="analisis_horas_modal" name="analisis_horas" class="horas calcHorasMod"/>
								<input id="analisis_coste_modal" name="analisis_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Diseño:</span>
							<div class="input">
								<input id="disenio_horas_modal" name="diseño_horas" class="horas calcHorasMod"/>
								<input id="disenio_coste_modal" name="diseño_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Construcción:</span>
							<div class="input">
								<input id="construccion_horas_modal" name="construccion_horas" class="horas calcHorasMod"/>
								<input id="construccion_coste_modal" name="construccion_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Pruebas:</span>
							<div class="input">
								<input id="pruebas_horas_modal" name="pruebas_horas" class="horas calcHorasMod"/>
								<input id="pruebas_coste_modal" name="pruebas_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestión:</span>
							<div class="input">
								<input id="gestion_horas_modal" name="gestion_horas" class="horas calcHorasMod"/>
								<input id="gestion_coste_modal" name="gestion_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field total">
						<span class="lbl">Total:</span>
							<div class="input">
								<input id="total_horas_modal" name="total_horas" class="horas" required aria-required="true"/>
								<input id="total_coste_modal" name="total_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl req">Horas<span class="required-asterisk">*</span></span><span class="lbl">Coste</span>
							</div>
						</div>
					</div>					 
					
				</div>
				
			</form>
			<div id="message_div_modal" class="message_div" style='margin-bottom:10px;'>
						<span id="span_message_modal" class="span_message"></span>
			</div>
			
		</div>
				<div class="modal-footer">
					<button type="button" class="" id="edit_coste_form_modal" onclick="sendEditCoste();">Guardar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
		</div>
<div class="ajax_loader" id="ajax_loader">
	<img src="../../img/ajax-loader.gif" />
</div>
<script>

	$('.calcHorasMod').on('change', function(e) {
		var a =parseInt($('#analisis_horas_modal').val());
		if (isNaN(a))a=0;
		var b =parseInt($('#disenio_horas_modal').val());
		if (isNaN(b))b=0;
		var c = parseInt($('#construccion_horas_modal').val());
		if (isNaN(c))c=0;
		var d = parseInt($('#pruebas_horas_modal').val());
		if (isNaN(d))d=0;
		var e = parseInt($('#gestion_horas_modal').val());
		if (isNaN(e))e=0;
		var stringSuma = a+b+c+d+e;
		$('#total_horas_modal').val(stringSuma.toString());
	});
	
</script>