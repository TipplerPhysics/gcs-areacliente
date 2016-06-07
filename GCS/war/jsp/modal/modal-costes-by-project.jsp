<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal_ajax" id="modal_costes_by_project">
	<div class="">
		<h2>Seleccione el coste que desea editar</h2>
		<hr />
	</div>
	<div class="table-responsive usersTable costes">
		<table class="table">
			<thead>
				<tr>
					<th>
						<span class="table-title"></span>
					</th>
					<th>
						<span class="table-title">Número de coste</span>
					</th>
					<th>
						<span class="table-title">Fecha solicitud valoración</span>
					</th>
					<th>
						<span class="table-title">Equipo</span>
					</th>
					<th>
						<span class="table-title">Total coste (€)</span>
					</th>
				</tr>
			</thead>
			<tbody id="costes-by-project-table" cellspacing="0">
				<c:forEach items="${costes}" var="coste">
					<tr>
						<td>							
							<label class="ui-marmots-label-radio" for="radio_${coste.key.id}">
								<input name="radio" id="radio_${coste.key.id}" data-git="${coste.gestor_it_key}" type="radio"/>
							</label>
						</td>
						<td><span>${coste.num_control}</span></td> 
						<td><span>${coste.str_fecha_solicitud_valoracion}</span></td> 
						<td><span>${coste.equipos}</span></td>
						<td><span>${coste.coste_total}</span></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</div>
<div class="modal-footer">
	<button type="button" class="" onclick="loadCosteModal();">Aceptar</button>
	<button type="button" class="" data-dismiss="modal">Cancelar</button>
</div>

<script>
$(document).ready(function(){
	$('input:checkbox, input:radio').checkbox();

});
</script>