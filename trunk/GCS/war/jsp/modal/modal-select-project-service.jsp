<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div class="modal_ajax" id="edit_project_modal">
	<div class="">
		<h2>Seleccione el servicio</h2>
		<hr />
	</div>
	<div class="edit-user-form-holder">
		<div class="form-field">
			<span class="lbl">Servicio:</span>
			<div class="input">
				<select class="selectpicker selected" id="select_project_action" name="select_project_action" data-live-search="true">	
					<c:choose>
					<c:when test="${empty services}">
					<option value="default">No hay servicios</option>
					</c:when>
					<c:otherwise>
					<c:forEach items="${services}" var="s">	
						<option value="${s.key.id}">${s.cod_servicio}</option>
					</c:forEach>
					</c:otherwise>	
					</c:choose>					
				</select>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="" onclick="LoadModalService();">Aceptar</button>
		<button type="button" class="" data-dismiss="modal">Cancelar</button>
	</div>
</div>
