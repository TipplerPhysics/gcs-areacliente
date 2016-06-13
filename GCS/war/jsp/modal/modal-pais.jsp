<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="modal_ajax">
		<div class="">
					<h2>Editar País</h2>
					<hr />
				</div>
				<div class="edit-user-form-holder">
					<form id="edit-country-form" name="edit-country-form" action="/paisesServlet" method="POST" novalidate="novalidate">
						<div class="form-container">
								<div class="form-field">
									<span class="lbl">Nombre País<span class="required-asterisk">*</span>:</span>
									<input type="text" id="nombrePais" value="${pais.name}"  name="nombrePais" class="long"  required aria-required="true" data-live-search="true">
							
								</div>
						</div>
					</form>
				<div id="message_div_modal" class="message_div" style='margin-bottom:10px;'>
					<span id="span_message_modal" class="span_message"></span>
				</div>
			
				</div>
					<div class="modal-footer">
						<button type="button" class="" id="edit_country_form_modal" onclick="sendEditCountry();">Guardar</button>
						<button type="button" class="" data-dismiss="modal">Cancelar</button>
					</div>
				</div>
<div class="ajax_loader" id="ajax_loader">
	<img src="../../img/ajax-loader.gif" />
</div>