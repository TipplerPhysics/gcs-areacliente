  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<div class="modal_ajax">
		<div class="new-user-form-holder">
		
			<div>
				<span>El informe se ha generado correctamente.</span>
			</div>
			<div>
				<span>¿Desea consultarlo en el repositorio?</span>
			</div>			
			
		</div>
		<div class="modal-footer">
			<button type="button" class="" id="show_informe_modal" onclick="showInforme();">Aceptar</button>
			<button type="button" class="" data-dismiss="modal" onclick="reloadImplantaciones();">Cancelar</button>
		</div>
</div>
	
