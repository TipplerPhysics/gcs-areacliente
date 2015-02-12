  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<script>
		$(function() {
			initSelectpickers();
			initDatepickers();
			initValidator();
		});
	</script>

	<div class="modal_ajax">
		<div class="">
			<h2>Envio correo solicitud</h2>
			<hr />
		</div>
		<div class="new-user-form-holder">
			<form id="send-email-solicitud-form" name="send-email-solicitud-form" action="/implantacionServlet" method="POST" novalidate="novalidate" data-servicios="${servicios}" data-conectividades="${conectividades}">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl">Indique el tipo de subida<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select name="tipo_subida" id="tipo_subida" class="long selectpicker">	
									<option value="default">Seleccione</option>					
									<option value="Calendada">Calendada</option>
									<option value="No Calendada">No Calendada</option>									
								</select>
							</div>
						</div>
						<div class="form-field" id='field-container-Default'>
							<span class="lbl">Fecha<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" disabled />
							</div>
						</div>
						<div class="form-field hidden" id='field-container-fechaCalendada'>
							<span class="lbl">Fecha<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="fecha_implantacion_calendada" name="fecha_implantacion_calendada"  class="long selectpicker" data-live-search="true">																	
									<c:forEach items='${fechasStr}' var="fecha">
										<option value="${fecha}">${fecha}</option>
									</c:forEach>								
								</select>
							</div>
						</div>
						<div class="form-field hidden" id='field-container-fechaNoCalendada'>
							<span class="lbl">Fecha<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" id="fecha_implantacion_no_calendada" name="fecha_implantacion" class="datepicker datefuture" size="16" maxlength="25"  readonly="" required aria-required="true" >
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
			<button type="button" class="" id="send_email_solicitud_form_modal" onclick="sendEmailSolicitud();">Aceptar</button>
			<button type="button" class="" data-dismiss="modal">Cancelar</button>
		</div>
</div>
<script>
	$('#tipo_subida').on('change',function(e){
		var tipo= $(this).find(':selected').val();
		if(tipo == 'Calendada'){
			$('#field-container-fechaCalendada').removeClass('hidden');
			$('#field-container-Default').addClass('hidden');
			$('#field-container-fechaNoCalendada').addClass('hidden');
		}else{
			if(tipo == 'No Calendada'){
				$('#field-container-fechaNoCalendada').removeClass('hidden');
				$('#field-container-Default').addClass('hidden');
				$('#field-container-fechaCalendada').addClass('hidden');
			}else{
				$('#field-container-Default').removeClass('hidden');
				$('#field-container-fechaNoCalendada').addClass('hidden');
				$('#field-container-fechaCalendada').addClass('hidden');
			}
		}
		
		
	});
</script>
