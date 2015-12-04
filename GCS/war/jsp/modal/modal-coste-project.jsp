  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="modal_ajax nuevo_coste">
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
								<input type="text" value="${coste.cliente_name}" class="readonly" readonly  name="cliente" id="cliente_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Código de control:</span>
							<div class="input">
								<input type="text" value="${coste.num_control}" size="16" maxlength="25" name="numero_control" id="numero_control_modal">
							</div>
						</div>

						
						<div class="form-field">
							<span class="lbl">Fecha alta costes<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="${coste.str_fecha_alta}" size="16" maxlength="25" class="datepicker" data-target-id='fecha_alta_costes_modal' name="fecha_alta_costes" id="fecha_alta_costes_modal" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Número valoración:</span>
							<div class="input">
								<select name="num_valoracion" id="num_valoracion_modal" class="long selectpicker">
									
									<option value="1" <c:if test="${coste.num_valoracion eq '1'}">selected</c:if>>1</option>
									<option value="2" <c:if test="${coste.num_valoracion eq '2'}">selected</c:if>>2</option>
									<option value="3" <c:if test="${coste.num_valoracion eq '3'}">selected</c:if>>3</option>
									<option value="4" <c:if test="${coste.num_valoracion eq '4'}">selected</c:if>>4</option>
									<option value="5" <c:if test="${coste.num_valoracion eq '5'}">selected</c:if>>5</option>
									<option value="6" <c:if test="${coste.num_valoracion eq '6'}">selected</c:if>>6</option>
									<option value="7" <c:if test="${coste.num_valoracion eq '7'}">selected</c:if>>7</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha solicitud valoración<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="${coste.str_fecha_solicitud_valoracion}" size="16" maxlength="25" class="datepicker" data-target-id='fecha_solicitud_valoracion_modal' name="fecha_solicitud_valoracion" id="fecha_solicitud_valoracion_modal" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha recepción valoración<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="${coste.str_fecha_recepcion_valoracion}" size="16" maxlength="25" class="datepicker" data-target-id='fecha_recepcion_valoracion_modal' name="fecha_recepcion_valoracion" id="fecha_recepcion_valoracion_modal" required aria-required="true">
							</div>
						</div>

					</div>
					<div class="form-field-divider right">
						<div class="form-field">
							<span class="lbl">Nombre proyectos<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="${coste.project_name}" class="readonly" readonly name="project" id="project_modal">						
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Equipo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="equipo_modal" name="equipo" class="long selectpicker selected">
									<option value="INNOVERY" <c:if test="${coste.equipos eq 'INNOVERY'}">selected</c:if>>INNOVERY</option>
									<option value="CAPGEMINI" <c:if test="${coste.equipos eq 'CAPGEMINI'}">selected</c:if>>CAPGEMINI</option>
									<option value="SOLUTIONS" <c:if test="${coste.equipos eq 'SOLUTIONS'}">selected</c:if>>SOLUTIONS</option>
									<option value="SOPORTE GNC" <c:if test="${coste.equipos eq 'SOPORTE GNC'}">selected</c:if>>SOPORTE GNC</option>
									<option value="SOPORTE SWIFT" <c:if test="${coste.equipos eq 'SOPORTE SWIFT'}">selected</c:if>>SOPORTE SWIFT</option>
									<option value="IS" <c:if test="${coste.equipos eq 'IS'}">selected</c:if>>IS</option>
									<option value="TELEMÁTICOS" <c:if test="${coste.equipos eq 'TELEMÁTICOS'}">selected</c:if>>TELEMÁTICOS</option>
									<option value="GESTOR IT" <c:if test="${coste.equipos eq 'GESTOR IT'}">selected</c:if>>GESTOR IT</option>	
								</select>
							</div>
						</div>
										
						 <div class="form-field">
							<span class="lbl">Gestor IT-registro<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" id="gestor_modal" name="gestor_it" data-live-search="true">				
									 <c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}" <c:if test="${coste.gestor_it_key eq user.key.id}"> selected="selected" </c:if>>${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									 </c:forEach>
								</select>
							</div>
						</div> 
						
						
						<div class="form-field">
							<span class="lbl">Comentarios:</span>
							<div class="input">
								<textarea name="comentarios" id="comentarios_modal">${coste.comentarios}</textarea>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Control presupuestario:</span>
							<div class="input">
								<input type="text" size="16" maxlength="25" name="control_presupuestario" id="control_presupuestario_modal" value = "${coste.control_presupuestario}">
							</div>
						</div>
							
					</div>	
					<div class="form-field-down">
						<span class="lbl titulo">Horas/Fases:</span>
						<div class="form-field">
							<span class="lbl">An&aacute;lisis:</span>
							<div class="input">
								<input id="analisis_horas_modal" name="analisis_horas" class="horas number calcHorasMod" value="${coste.horas_analisis}"/>
								<input id="analisis_coste_modal" name="analisis_coste" class="coste number calcCosteMod" value="${coste.coste_analisis}"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Diseño:</span>
							<div class="input">
								<input id="disenio_horas_modal" name="diseño_horas" class="horas number calcHorasMod" value="${coste.horas_diseño}"/>
								<input id="disenio_coste_modal" name="diseño_coste" class="coste number calcCosteMod" value="${coste.coste_diseño}"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Construcción:</span>
							<div class="input">
								<input id="construccion_horas_modal" name="construccion_horas" class="horas calcHorasMod" value="${coste.horas_construccion}"/>
								<input id="construccion_coste_modal" name="construccion_coste" class="coste number calcCosteMod" value="${coste.coste_construccion}"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Pruebas:</span>
							<div class="input">
								<input id="pruebas_horas_modal" name="pruebas_horas" class="horas number calcHorasMod" value="${coste.horas_pruebas}"/>
								<input id="pruebas_coste_modal" name="pruebas_coste" class="coste number calcCosteMod" value="${coste.coste_pruebas}"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestión:</span>
							<div class="input">
								<input id="gestion_horas_modal" name="gestion_horas" class="horas number calcHorasMod" value="${coste.horas_gestion}"/>
								<input id="gestion_coste_modal" name="gestion_coste" class="coste number calcCosteMod" value="${coste.coste_gestion}"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field total">
						<span class="lbl">Total:</span>
							<div class="input">
								<input id="total_horas_modal" name="total_horas" class="horas number" value="${coste.horas_total}" required aria-required="true"/>
								<input id="total_coste_modal" name="total_coste" class="coste number" value="${coste.coste_total}"/>
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
	
////////////////////////////////////
	$('.calcCosteMod').on('change', function(e) {
		var a =parseFloat($('#analisis_coste_modal').val());
		if (isNaN(a))a=0;
		var b =parseFloat($('#disenio_coste_modal').val());
		if (isNaN(b))b=0;
		var c = parseFloat($('#construccion_coste_modal').val());
		if (isNaN(c))c=0;
		var d = parseFloat($('#pruebas_coste_modal').val());
		if (isNaN(d))d=0;
		var e = parseFloat($('#gestion_coste_modal').val());
		if (isNaN(e))e=0;
		var stringSuma = a+b+c+d+e;
		$('#total_coste_modal').val(stringSuma.toString());
	});
////////////////////////////////////
	
</script>