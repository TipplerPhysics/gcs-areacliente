  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="modal_ajax">
	
		<div>
					<h2>Editar Petici&oacute;n</h2>
					<hr>
				</div>
				<div class="edit-demanda-form-holder">
					<form id="edit-demanda-form" name="new-user-form" action="/demandaServlet"
				method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl">Fecha entrada petici&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_solicitud_asignacion' name="fecha_entrada_peticion" id="fecha_entrada_peticion_modal" required aria-required="true">
							</div>
						</div>
						<div class="form-field horas">
							<span class="lbl">Hora petici&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
							
								
								<select class="selectpicker  time hora" id="hora_peticion_modal" name="hora_peticion" required aria-required="true" data-live-search="true">
									<c:forEach items="${horasList}" var="hora">
									<option value="${hora}">${hora}</option>
									</c:forEach>
								</select>
								<span class="time-span">:</span>
								<select class="selectpicker  time minutos" id="min_peticion_modal" name="min_peticion" required aria-required="true" data-live-search="true">
									<c:forEach items="${minutosList}" var="min">
									<option value="${min}">${min}</option>
									</c:forEach>
								</select>
								<!-- <![endif]-->
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="input_cliente_modal" class="selectpicker selected" name="cliente" required aria-required="true" data-live-search="true">
									
									<c:forEach items="${clientes}" var="cliente">	
										<option value="${cliente.key.id}">${cliente.nombre}</option>
									</c:forEach>
								</select>
								
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Gestor de negocio:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_negocio_modal" name="gestor_negocio" data-live-search="true">
								  
									<c:forEach items="${gestores_negocio}" var="user">	
										<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
									</c:forEach>					
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Tipo petici&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="tipo" id="tipo_modal" required aria-required="true" >
									<c:forEach items="${tipoPeticion}" var="tipoPeticion">
										<option value="${tipoPeticion.name}">${tipoPeticion.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					<!--
						<div class="form-field">
							<span class="lbl">Tipo petici&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="tipo" id="tipo_modal" required aria-required="true" >							
									<option value="PRUC">PRUC</option>
									<option value="CONS">CONS</option>
									<option value="VIAB">VIAB</option>
									<option value="IMPL">IMPL</option>
									<option value="SEPA">SEPA</option>
									<option value="MIGR-IA">MIGR-IA</option>
									<option value="EVOL-C">EVOL-C</option>
									<option value="IMPL-OB-C">IMPL-OB-C</option>
									<option value="SEPA-OB-C">SEPA-OB-C</option>
									<option value="MIGR-OB-C">MIGR-OB-C</option>
								</select>
							</div>
						</div>
					-->
						<div class="form-field">
							<span class="lbl">Devuelta<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="devuelta" id="devuelta_modal" required aria-required="true">
									<option value="NO">NO</option>
									<option value="SI">SI</option>
									
								</select>
							</div>
						</div>
						
						
						<div class="form-field">
							<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
								<div class="input">
									<select class="selectpicker selected" name="estado" id="estado_modal" required aria-required="true">
										<c:forEach items="${estadoPeticion}" var="estadoPeticion">
											<option value="${estadoPeticion.name}">${estadoPeticion.name}</option>
										</c:forEach>
									</select>
								</div>
						</div>
					

						<div class="form-field">
							<span class="lbl">Catalogaci&oacute;n de petici&oacute;n:</span>
							<div class="input">
								<select class="selectpicker selected" name="catalogacion_peticion" id="catalogacion_peticion_modal">
									
									<option value="ESTANDAR">Est&aacute;ndar</option>
									<option value="COMPLEJA">Compleja</option>
									
								</select>
							</div>
						</div>
					</div><div class="form-field-divider right">
						<div class="form-field">
							<span class="align-top lbl">Motivo de catalogaci&oacute;n:</span>
							<div class='input'>
								<textarea name="motivo_catalogacion" id="motivo_catalogacion_modal" class="long"></textarea>
							</div>
						</div>
						<div class="form-field">
							<span class="align-top lbl">Comentarios:</span>
							<div class='input'>
								<textarea id="comentarios_modal" name="comentarios" class="long"></textarea>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha solicitud asignaci&oacute;n:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" maxlength="25" class="datepicker" name="fecha_solicitud_asignacion" id="fecha_solicitud_asignacion_modal">
							</div>
						</div>
						<div class="form-field horas">
							<span class="lbl">Hora solicitud asignaci&oacute;n:</span>
							<div class="input">
								<select class="selectpicker time hora" id="hora_solicitud_asignacion_modal" name="hora_solicitud_asignacion" data-live-search="true">
										
									<c:forEach items="${horasList}" var="hora">
										<option value="${hora}">${hora}</option>
									</c:forEach>
								</select>
								<span class="time-span">:</span>
								<select class="selectpicker time minutos" id="min_solicitud_asignacion_modal" name="min_solicitud_asignacion" data-live-search="true">
								
									
									<c:forEach items="${minutosList}" var="min">
										<option value="${min}">${min}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Gestor IT pre-asignado:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_it_modal" name="gestor_it" data-live-search="true">	
								
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						
						<div class="form-field">
							<span class="lbl">Fecha comunicaci&oacute;n asignaci&oacute;n:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" maxlength="25" class="datepicker" name="fecha_comunicacion_asignacion" id="fecha_comunicacion_asignacion_modal">
							</div>
						</div>
						<div class="form-field horas">
							<span class="lbl">Hora comunicaci&oacute;n asignaci&oacute;n:</span>
							<div class="input">
								<select class="selectpicker time hora" id="hora_comunicacion_asignacion_modal" name="hora_comunicacion_asignacion" data-live-search="true">
										
									<c:forEach items="${horasList}" var="hora">
										<option value="${hora}">${hora}</option>
									</c:forEach>
								</select>
								<span class="time-span">:</span>
								<select class="selectpicker time minutos" id="min_comunicacion_asignacion_modal" name="min_comunicacion_asignacion"  data-live-search="true">
								
									
									<c:forEach items="${minutosList}" var="min">
										<option value="${min}">${min}</option>
									</c:forEach>
								</select>
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
				<button onclick="sendEditDemanda();" id="edit_demanda_form_modal" class="" type="button">Guardar</button>
				<button data-dismiss="modal" class="dismis_edit_demanda" type="button">Cancelar</button>
			</div>	
		</div>
<div class="ajax_loader" id="ajax_loader">
	<img src="../../img/ajax-loader.gif" />
</div>