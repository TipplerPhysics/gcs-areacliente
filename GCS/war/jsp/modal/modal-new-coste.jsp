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
							<span class="lbl">Tipo Petición:</span>
							<div class="input">
								<select name="tipo_coste" class="long selectpicker">
									<option value="default">Seleccionar</option>
									<option value="N/A">N/A</option>
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
							<span class="lbl">Tipo Desarrollo:</span>
							<div class="input">
								<select name="tipo_desarrollo" class="long selectpicker">
									<option value="default">Seleccionar</option>
									<option value="N/A">N/A</option>
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
						<div class="form-field">
							<span class="lbl">Plazo estimado :</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker"  name="plazo_estimado" id="plazo_estimado">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Componentes modificados :</span>
							<div class="input">
								<textarea name="componentes_modificados"  id="componentes_modificados" ></textarea>
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
									<option value="CAPGEMINI">CAPGEMINI</option>
									<option value="GESTOR IT">GESTOR IT</option>
									<option value="INNOVERY">INNOVERY</option>
									<option value="IS">IS</option>									
									<option value="SOLUTIONS">SOLUTIONS</option>
									<option value="SOPORTE GNC">SOPORTE GNC</option>
									<option value="SOPORTE SWIFT">SOPORTE SWIFT</option>									
									<option value="TELEMÁTICOS">TELEMÁTICOS</option>
									<option value="SOFTTEK">SOFTTEK</option>
								</select>
							</div>
						</div>
										
						<div class="form-field">
							<span class="lbl">Gestor IT-solicitante<span class="required-asterisk">*</span>:</span>
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
							<span class="lbl">:</span>
							<div class="input">
								<textarea name="comentarios" id="comentarios"></textarea>
							</div>
						</div>	
						<div class="form-field">
							<span class="lbl">:</span>
							<div class="input">
								<textarea name="asunto" id="asunto"></textarea>
							</div>
						</div>	
						<div class="form-field">
							<span class="lbl">Control Presupuestario:</span>
							<div class="input">
								<input type="text" value=""  size="16" maxlength="25" name="control_presupuestario" id="control_presupuestario">
							</div>
						</div>	
						<div class="form-field">
							<span class="lbl">Fecha implantación:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" name="fecha_implantacion" data-target-id='fecha_implantacion' id="fecha_implantacion">
							</div>
						</div>

						<div class="form-field">
							<span class="lbl">Fecha de garantía:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="readonly" readonly name="fecha_garantia" data-target-id='fecha_garantia' id="fecha_garantia">
							</div>
						</div>													
					</div>	
					
					<div class="form-field-down">
					
						<span class="lbl">Horas/Fases:</span>
						<div class="form-field">
							<span class="lbl">Análisis:</span>
							<div class="input">
								<input id="analisis_horas" name="analisis_horas" class="horas number calcHorasMod"/><input id="analisis_coste" name="analisis_coste" class="coste number calcCosteMod"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Diseño:</span>
							<div class="input">
								<input id="disenio_horas" name="diseño_horas" class="horas number calcHorasMod"/><input id="disenio_coste" name="diseño_coste" class="coste number calcCosteMod"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Construcción:</span>
							<div class="input">
								<input id="construccion_horas" name="construccion_horas" class="horas number calcHorasMod"/><input id="construccion_coste" name="construccion_coste" class="coste number calcCosteMod"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Pruebas:</span>
							<div class="input">
								<input id="pruebas_horas" name="pruebas_horas" class="horas number calcHorasMod"/><input id="pruebas_coste" name="pruebas_coste" class="coste number calcCosteMod"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestión:</span>
							<div class="input">
								<input id="gestion_horas" name="gestion_horas" class="horas number calcHorasMod"/><input id="gestion_coste" name="gestion_coste" class="coste number calcCosteMod"/>
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
	
////////////////////////////////////
	$('.calcCosteMod').on('change', function(e) {
		var a =parseFloat($('#analisis_coste').val());
		if (isNaN(a))a=0;
		var b =parseFloat($('#disenio_coste').val());
		if (isNaN(b))b=0;
		var c = parseFloat($('#construccion_coste').val());
		if (isNaN(c))c=0;
		var d = parseFloat($('#pruebas_coste').val());
		if (isNaN(d))d=0;
		var e = parseFloat($('#gestion_coste').val());
		if (isNaN(e))e=0;
		var stringSuma = a+b+c+d+e;
		$('#total_coste').val(stringSuma.toString());
	});
////////////////////////////////////
	$('#fecha_implantacion').on('change', function(e) {	
		var fechaImp = $('#fecha_implantacion').val();
		var fechaImpDia = fechaImp.substr(0, 2); 
		var fechaImpMes = fechaImp.substr(3, 2);  
		var fechaImpAnnio = fechaImp.substr(6, 4);
		var fechaGarantia = "";
		var strfechaImpDia = "";
		var strfechaImpMes = "";
		var d = new Date(fechaImpAnnio, fechaImpMes, fechaImpDia);	
		d.setMonth(d.getMonth() + 6);
		fechaImpDia = d.getDate();
		fechaImpMes = d.getMonth()+1;
		fechaImpAnnio = d.getFullYear();
		strfechaImpDia = fechaImpDia.toString();
		strfechaImpMes = fechaImpMes.toString();
		if (strfechaImpDia.length < 2) strfechaImpDia = "0"+strfechaImpDia;
		if (strfechaImpMes.length < 2) strfechaImpMes = "0"+strfechaImpMes;

		fechaGarantia = strfechaImpDia+"/"+strfechaImpMes+"/"+fechaImpAnnio.toString();
		$('#fecha_garantia').val(fechaGarantia);
		
	});
	
	
</script>