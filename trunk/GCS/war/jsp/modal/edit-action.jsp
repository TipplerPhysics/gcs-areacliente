 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal_ajax" id="edit_action_modal">
	<div class="">
		<h2>Seleccione la opción que desee editar</h2>
		<hr />
	</div>
	<div class="edit-user-form-holder">
		<div class="form-field">
			<span class="lbl">Acción:</span>
			<div class="input">
				<select class="selectpicker selected" id="select_edit_action" name="select_edit_action">
				  	<option value="default">Seleccionar</option>
				  	<option value="proyecto">Proyecto</option>
				    <option value="costes">Costes</option>
				    <option value="conectividad">Conectividad</option>
				    <option value="servicios">Servicios</option>									
				</select>
			</div>
		</div>
		
	</div>
	<div class="modal-footer">
		<button type="button" class="" id="edit_action_modal" onclick="loadEditModal();">Aceptar</button>
		<button type="button" class="" data-dismiss="modal">Cancelar</button>
	</div>
</div>
