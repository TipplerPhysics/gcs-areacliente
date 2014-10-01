<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal_ajax">
<div class="">
				<h2>Editar usuario</h2>
				<hr />
			</div>
			<div class="newUserbox">
				<div class="edit-user-form-holder open" id="edit_user">
								<form id="edit-user-form" name="edit-user-form" action="/usersServlet"
						method="POST" novalidate="novalidate">
						<input type="hidden" id="id_modal" name="id" value="">
						<div class="form-container">
							<div class="form-field">
								<span for="nombre" class="lbl">Nombre<span class="required-asterisk">*</span>:</span><input class="long" type="text"
									name="nombre" id="nombre_modal" required aria-required="true">
							</div>
							<div class="form-field">
								<span  class="lbl">Apellido 1<span class="required-asterisk">*</span>:</span><input class="long" type="text" name="ap1"
									id="ap1_modal" required aria-required="true">
							</div>
							<div class="form-field">
								<span class="lbl">Apellido 2:</span><input class="long" type="text" name="ap2"
									id="ap2_modal">
							</div>
							<div class="form-field">
								<span class="lbl">E-mail<span class="required-asterisk">*</span>:</span><input class="long email no_message_error" type="text"
									name="email" id="email_modal" required aria-required="true"
									data-type="email">
							</div>
							<div class="form-field">
								<span class="lbl">Departamento<span class="required-asterisk">*</span>:</span>
								<select id="dto_select_modal"
									class="long selected selectpicker" name="dto">
									<c:forEach items="${departamentos}" var="departamento">		         	
										<option value="${departamento.value}">${departamento.desc}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-field">
								<span class="lbl">Perfil<span class="required-asterisk">*</span>:</span>
								<select id="permiso_select_modal" class="long selected selectpicker"
									name="permiso">
									<c:forEach items="${permisos}" var="permiso">		         	
										<option value="${permiso.value}">${permiso.desc}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-fieldset">
								<span class="fieldset-title lbl">&Aacute;reas:</span>
								<fieldset class='radio-container-holder' id='radio-container-holder_modal'>
									<div class="radio-container" id='radio-container_modal'>
										<input type="checkbox" name='areas' value="Onboarding"
											id="onboarding_modal"><label for="onboarding_modal"><span></span>Onboarding</label>
									</div>
									<div class="radio-container" id='radio-container_modal'>
										<input type="checkbox" name='areas' value="Servicing"
											id="servicing_modal"><label for="servicing_modal"><span></span>Servicing</label>
									</div>
									<div class="radio-container" id='radio-container_modal'>
										<input type="checkbox" name='areas' value="Clientes"
											id="clientes_modal"><label for="clientes_modal"><span></span>Clientes</label>
									</div>
									<div class="radio-container" id='radio-container_modal'>
										<input type="checkbox" name='areas' value="ITCIB" id="itcib_modal"><label
											for="itcib_modal"><span></span>ITCIB</label>
									</div>
									<div class="radio-container" id='radio-container_modal'>
										<input type="checkbox" name='areas'
											value="Global Customer Service" id="gcs_modal"><label
											for="gcs_modal"><span></span>Global Customer Service</label>
									</div>
									<div class="radio-container" id='radio-container_modal'>
										<input type="checkbox" name='areas' value="Global Product"
											id="global-product_modal"><label for="global-product_modal"><span></span>Global
											Product</label>
									</div>
									
								</fieldset>
							</div>
							<div id="message_div_modal" class="message_div">
								<span id="span_message_modal"></span>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer" id="modal-footer_submit">
					<button type="button" id="submit_edit_user_form" onclick="sendEditUser();">Aceptar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>	
		</div>
		
<div class="ajax_loader" id="ajax_loader">
	<img src="../../img/ajax-loader.gif" />
</div>