<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="modal_ajax nuevo_coste">
	
		<div>
			<h2>Nuevo Coste</h2>
			<hr>
		</div>
		<div class="edit-demanda-form-holder">
			<form id="new-coste-form" name="new-coste-form" action="/costeServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
					
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="input_cliente_id" class="selectpicker selected" name="cliente" required aria-required="true" onchange="getProjectsByClient('modal');" data-live-search="true">
									<option value="default">Seleccionar</option>
									<c:forEach items="${clientes}" var="cliente">	
										<option value="${cliente.key.id}">${cliente.nombre}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Código de control:</span>
							<div class="input">
								<input type="text" value=""  size="16" maxlength="25" name="numero_control" id="numero_control">
							</div>
						</div>

						
						<div class="form-field">
							<span class="lbl">Fecha alta costes<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_alta_costes' name="fecha_alta_costes" id="fecha_alta_costes" required aria-required="true">
							</div>
						</div>
						
						

						
						
						<div class="form-field">
							<span class="lbl">Número valoración:</span>
							<div class="input">
								<select name="num_valoracion" class="long selectpicker">
									<option value="default">Seleccionar</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha solicitud valoración<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_recepcion_valoracion' name="fecha_solicitud_valoracion" id="fecha_solicitud_valoracion" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha recepción valoración<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker"  name="fecha_recepcion_valoracion" id="fecha_recepcion_valoracion" required aria-required="true">
							</div>
						</div>
					</div>
					

					<div class="form-field-divider right">
						<div class="form-field">
							<span class="lbl">Nombre proyectos<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="project" name="project" required aria-required="true"  class="long selected selectpicker" data-live-search="true">
									<option value="default">-</option>
								</select>						
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Equipo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="equipo" name="equipo" class="long selectpicker selected" required aria-required="true">
									<option value="default">Seleccionar</option>
									<option value="Innovery">Innovery</option>
									<option value="Capgemini">Capgemini</option>
									<option value="Solutions">Solutions</option>
									<option value="Soporte Swift">Soporte Swift</option>
									<option value="IS">IS</option>
									<option value="Telemáticos">Telemáticos</option>
									<option value="Gestor IT">Gestor IT</option>
									
								</select>
							</div>
						</div>
										
						<div class="form-field">
							<span class="lbl">Gestor IT-registro<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" required aria-required="true"  id="gestor_it" name="gestor_it" data-live-search="true">	
								<option value="default" selected>Seleccionar</option>
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Comentarios:</span>
							<div class="input">
								<textarea name="comentarios" id="comentarios"></textarea>
							</div>
						</div>							
					</div>	
					<div class="form-field-down">
					
						<span class="lbl">Horas/Fases:</span>
						<div class="form-field">
							<span class="lbl">Análisis:</span>
							<div class="input">
								<input id="analisis_horas" name="analisis_horas" class="horas number calcHorasMod"/><input id="analisis_coste" name="analisis_coste" class="coste number"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Diseño:</span>
							<div class="input">
								<input id="disenio_horas" name="diseño_horas" class="horas number calcHorasMod"/><input id="disenio_coste" name="diseño_coste" class="coste number"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Construcción:</span>
							<div class="input">
								<input id="construccion_horas" name="construccion_horas" class="horas number calcHorasMod"/><input id="construccion_coste" name="construccion_coste" class="coste number"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Pruebas:</span>
							<div class="input">
								<input id="pruebas_horas" name="pruebas_horas" class="horas number calcHorasMod"/><input id="pruebas_coste" name="pruebas_coste" class="coste number"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestión:</span>
							<div class="input">
								<input id="gestion_horas" name="gestion_horas" class="horas number calcHorasMod"/><input id="gestion_coste" name="gestion_coste" class="coste number"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
						<span class="lbl">Total:</span>
							<div class="input">
								<input id="total_horas" name="total_horas" class="horas number" required aria-required="true"/><input id="total_coste" name="total_coste" class="coste number"/>
							</div>
							<div class="input labels">
								<span class="lbl req">Horas<span class="required-asterisk">*</span></span><span class="lbl">Coste</span>
							</div>
						</div>					
					</div>						
				</div>				
			</form>
			
			<div style="margin-bottom:10px;" class="message_div" id="message_div_modal">
				<span class="span_message" id="span_message_modal"></span>
			</div>
					
					
		</div>		
		<div class="modal-footer">
			<button onclick="sendNewCoste();" id="new_coste_form_modal" class="" type="button">Guardar</button>
			<button data-dismiss="modal" class="dismis_new_coste" type="button">Cancelar</button>
		</div>	
	</div>
	

<script>

	$('.calcHorasMod').on('change', function(e) {
		var a =parseInt($('#analisis_horas').val());
		if (isNaN(a))a=0;
		var b =parseInt($('#disenio_horas').val());
		if (isNaN(b))b=0;
		var c = parseInt($('#construccion_horas').val());
		if (isNaN(c))c=0;
		var d = parseInt($('#pruebas_horas').val());
		if (isNaN(d))d=0;
		var e = parseInt($('#gestion_horas').val());
		if (isNaN(e))e=0;
		var stringSuma = a+b+c+d+e;
		$('#total_horas').val(stringSuma.toString());
	});
	
</script>