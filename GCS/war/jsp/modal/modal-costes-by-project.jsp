<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="">
<div class="modal_ajax">
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
						<span class="table-title">Num. de coste</span>
					</th>
					<th>
						<span class="table-title">Equipo</span>
					</th>
					<th>
						<span class="table-title">Nbr. proyecto</span>
					</th>
				</tr>
			</thead>
			<tbody id="costes-by-project-table" cellspacing="0">
				<c:forEach items="${costes}" var="coste">
					<tr>
						<td>							
							<label class="ui-marmots-label-radio" for="radio_${coste.key.id}">
								<input name="radio" id="radio_${coste.key.id}" type="radio"/>
							</label>
						</td>
						<td><span>${coste.num_control}</span></td>
						<td><span>${coste.equipos}</span></td>
						<td><span>${coste.project_name}</span></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</div>
<div class="modal-footer">
	<button type="button" class="" onclick="loadCosteModal();">Guardar</button>
	<button type="button" class="" data-dismiss="modal">Cancelar</button>
</div>
</form>