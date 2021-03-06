  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="modal_ajax">
		<div class="">
		
		<!--<button onclick="sendCloneCoste();" id="new_coste_form_modal" class="" type="button" style="float:right">Crear un nuevo coste con estos datos</button>-->
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
								<input type="text" size="16" maxlength="25"  name="numero_control" id="numero_control_modal" class="readonly" readonly >
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
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Tipo Petición<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select name="tipo_coste" id="tipo_coste_modal" class="long selectpicker selected" required aria-required="true">
									<option value="Antiguo">Antiguo</option>
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
							<span class="lbl">Tipo Desarrollo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select name="tipo_desarrollo" id="tipo_desarrollo_modal" class="long selectpicker selected" required aria-required="true">
									<option value="default">Seleccionar</option>
									<option value="N/A">N/A</option>
									<option value="Validaciones específicas cliente">Validaciones específicas cliente</option>
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
								<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_solicitud_valoracion_modal' name="fecha_solicitud_valoracion" id="fecha_solicitud_valoracion_modal" required aria-required="true">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha recepción valoración<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_recepcion_valoracion_modal' name="fecha_recepcion_valoracion" id="fecha_recepcion_valoracion_modal" >
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Plazo estimado :</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='plazo_estimado_modal' name="plazo_estimado" id="plazo_estimado_modal" >
							</div>
						</div>

						<div class="form-field">
							<span class="lbl">Componentes modificados :</span>
							<div class="input">
								<textarea name="componentes_modificados"  id="componentes_modificados_modal">${costeMod.componentes_modificados}</textarea>
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
								<select class="selectpicker" id="gestor_it_modal" name="gestor_it" data-live-search="true">	
								
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Comentarios:</span>
							<div class="input">
								<textarea name="comentarios" id="comentarios_modal">${costeMod.comentarios}</textarea>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Asunto:</span>
							<div class="input">
								<textarea name="asunto" id="asunto_modal"></textarea>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Control Presupuestario:</span>
							<div class="input">
								<input type="text"size="16" maxlength="25" name="control_presupuestario" id="control_presupuestario_modal">
							</div>
						</div>		

						<div class="form-field">
							<span class="lbl">Fecha implantación :</span>
							<div class="input">
								<input type="text" size="16" maxlength="25" class="datepicker"  name="fecha_implantacion" data-target-id='fecha_implantacion_modal' id="fecha_implantacion_modal">
							</div>
						</div>

						<div class="form-field">
							<span class="lbl">Fecha de garantía:</span>
							<div class="input">
								<input type="text" size="16" maxlength="25" class="readonly" readonly name="fecha_garantia"  id="fecha_garantia_modal">
							</div>
						</div>
						
							
					</div>	
					<div class="form-field-down">
						<span class="lbl">Horas/Fases:</span>
						<div class="form-field">
							<span class="lbl">An&aacute;lisis:</span>
							<div class="input">
								<input id="analisis_horas_modal" name="analisis_horas" class="horas number calcHorasMod"/>
								<input id="analisis_coste_modal" name="analisis_coste" class="coste number calcCosteMod"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Diseño:</span>
							<div class="input">
								<input id="disenio_horas_modal" name="diseño_horas" class="horas number calcHorasMod"/>
								<input id="disenio_coste_modal" name="diseño_coste" class="coste number calcCosteMod"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Construcción:</span>
							<div class="input">
								<input id="construccion_horas_modal" name="construccion_horas" class="horas number calcHorasMod"/>
								<input id="construccion_coste_modal" name="construccion_coste" class="coste number calcCosteMod"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Pruebas:</span>
							<div class="input">
								<input id="pruebas_horas_modal" name="pruebas_horas" class="horas number calcHorasMod"/>
								<input id="pruebas_coste_modal" name="pruebas_coste" class="coste number calcCosteMod"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestión:</span>
							<div class="input">
								<input id="gestion_horas_modal" name="gestion_horas" class="horas number calcHorasMod"/>
								<input id="gestion_coste_modal" name="gestion_coste" class="coste number calcCosteMod"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field total">
						<span class="lbl">Total:</span>
							<div class="input">
								<input id="total_horas_modal" name="total_horas" class="horas number" required aria-required="true"/>
								<input id="total_coste_modal" name="total_coste" class="coste number"/>
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

 	$('#fecha_implantacion_modal').on('change', function(e) {	
		var fechaImp = $('#fecha_implantacion_modal').val();
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
		$('#fecha_garantia_modal').val(fechaGarantia);
		
	});
 /////////////////////////////////////

</script>