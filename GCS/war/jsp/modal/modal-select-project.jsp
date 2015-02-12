<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div class="modal_ajax" id="edit_project_modal">
	<div class="">
		<h2>Seleccione el proyecto</h2>
		<hr />
	</div>
	<div class="edit-user-form-holder">
		<div class="form-field">
			<span class="lbl">Proyecto<span class="required-asterisk">*</span>:</span>
			<div class="input">
				<select class="selectpicker selected" id="select_project_action" name="select_project_action" data-live-search="true">	
					<option value="default">Seleccione</option>	
					<c:forEach items="${projects}" var="p">	
						<option value="${p.key.id}">${p.cod_proyecto}</option>
					</c:forEach>						
				</select>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="" id="edit_project_modal_button" onclick="">Aceptar</button>
		<button type="button" class="" data-dismiss="modal">Cancelar</button>
	</div>
</div>
