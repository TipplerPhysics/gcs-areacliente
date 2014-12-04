  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<div class="modal_ajax">
		<div class="">
			<h2>Envio correo confirmaci&oacute;n</h2>
			<hr />
		</div>
		<div class="new-user-form-holder">
			<form id="send-email-confirmacion-form" name="send-email-confirmacion-form" action="/implantacionServlet" method="POST" novalidate="novalidate" data-servicios="${servicios}" data-conectividades="${conectividades}">
				<div class="form-container">
					<span class="info">Se debe enviar el correo de confirmaci&oacute;n bajo la siguiente fecha para poder completar el proceso de pase a producci&oacute;n:</span>
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl mini">Fecha:</span>
							<div class="input">
								<input type="text" id="fecha_implantacion" name="fecha_implantacion" class="datepicker " size="16" maxlength="25"  readonly="true" disabled value="${fecha_implantacion}">
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
			<button type="button" class="" id="send_email_confirmacion_form_modal" onclick="sendEmailConfirmacion();">Aceptar</button>
			<button type="button" class="" data-dismiss="modal">Cancelar</button>
		</div>
</div>
	
