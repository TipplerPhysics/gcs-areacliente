<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_pais">

	<h1>Gesti&oacute;n pais</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	
	
	<hr/>
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Gesti&oacute;n de paises </span>
	</div>

	<div class="newUserbox">
		<button id="newUserButton">
			Nuevo País<span class="user_span"></span>
		</button>
		<button id="excel_btn"
			onclick="window.location.href='../../paisesServlet?accion=xls'">
			Descargar Tabla<span class="excel_span"></span>
		</button>

	
		<div class="new-user-form-holder">
			<form id="new-country-form" name="new-country-form" action="/paisesServlet"
				method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field">
						<span for="nombre">Nombre<span class="required-asterisk">*</span>:</span>
							<input class="long" type="text" name="nombre" id="nombre" maxlength="25" required aria-required="true">
					</div>
					<div id="message_div">
						<span id="span_message"></span>
					</div>
				</div>
			</form>
			<button type="submit" id="submit_country_form">Aceptar</button>
			<button href="#" class="close-form">Cancelar</button>
		</div>
	</div>


	<div>
		<div>
			<div class="table-responsive usersTable">
				<table class="table">
					<thead>
						<tr>
							<th><span class="table-title">Id País</span></th>
							<th><span class="table-title">Nombre</span></th>
							<th style="width: 110px;">&nbsp;</th>
						</tr>

						<tr>
							<form id='test-header-filter' action="">
								<th class="search-th"></th> 						
								<th class="search-th"><input name='name' value='${name}'></th>
								<th><button type='button' onclick='filteringPaises();'>  FILTRAR  </button></th>
							</form>
						</tr>
					</thead>
					<tbody id="myTable" cellspacing="0" data-page="${page}" data-lastpage="${lastpage}" data-numpages="${numpages}">
						<c:choose>
							<c:when test="${empty paisesList}">
								<tr>
									<td><span>No existen paises.</span></td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${paisesList}" var="pais">
									<tr class="valid-result" id="row${pais.key.id}"	data-id="${pais.key.id}" data-nombre="${pais.name}">
										<td><span>${pais.key.id}</span></td>
										<td><span>${pais.name}</span></td>
										<td>
											<img class="vs" src="../img/vs.png">
												<a class="lapiz" name="${pais.key.id}" href="../paisModal.do?id=${pais.key.id}" id="lapiz${pais.key.id}" data-toggle="modal" 
												 data-target="#edit-country"></a>
												<a class="papelera"	name="${pais.key.id}" data-toggle="modal" 
												 data-target="#confirm-delete" id="papelera${pais.key.id}"></a>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="col-md-12 text-center">
				<ul class="pagination" id="myPager"></ul>
				<span class="pagesummary"></span>
				<div class="paginationGoto" />
			</div>
		</div>
	</div>


	<div class="modal fade" id="edit-country" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" id="edit_country_dialog">
			<div class="modal-content"></div>
		</div>
	</div>


	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content noErase">
				<div class="">
					<h2>Eliminar pais</h2>
					<hr />
				</div>
				<div class="">
					<p>&iquest;Est&aacute; seguro que desea eliminar el pais?</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deletePais">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>