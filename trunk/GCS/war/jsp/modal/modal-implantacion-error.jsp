  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<div class="modal_ajax">
		<div class="">
			<h2>Error</h2>
			<hr />
		</div>
		<div class="new-user-form-holder">
			<span>Servicios: </span><span>${servicios}</span><br>
			<span>ultimoEstadoServicio: </span><span>${ultimoEstadoServicio}</span><br>
			<span>Conectividades: </span><span>${conectividades}</span><br>
			<span>ultimoEstadoConectividad: </span><span>${ultimoEstadoConectividad}</span>
			
		</div>
		<div class="modal-footer">
			<button type="button" class="" data-dismiss="modal">Cerrar</button>
		</div>
</div>
	
