<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="alta_usuario">

	<h1>Gesti&oacute;n de catalogo de servicios</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>

	<hr />

	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span>
			Gestión de catalogo </span>
	</div>

	<div class="newUserbox">
		<button id="newUserButton">
			Nuevo Servicio<span class="user_span"></span>
		</button>
		<button id="excel_btn"
			onclick="window.location.href='../../catalogoServlet?accion=xls'">
			Descargar Tabla<span class="excel_span"></span>
		</button>


		<div class="new-user-form-holder">
			<form id="new-service-form" name="new-service-form" action="/catalogoServlet"
				method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						
						<div class="form-field">
							<span for="nombre">Nombre<span class="required-asterisk">*</span>:
							</span><input class="long" type="text" name="nombre" id="nombre"
								maxlength="25" required aria-required="true">
						</div>
						
						<div class="form-field">
							<span>Pais<span class="required-asterisk">*</span>:
							</span><select id="pais_select" class="long selected selectpicker"
								name="pais" required data-live-search="true">
								<option selected value="default">Seleccionar</option>
								<c:forEach items="${paises}" var="paises">
									<option value="${paises.key.id}">${paises.name}</option>
								</c:forEach>
							</select>
						</div>
					
					</div>
					
					<div class="form-field-divider right">
					
						<div class="form-field">
							<span for="extensiones">Extensiones<span class="required-asterisk">*</span>:
							</span><input class="long" type="text" name="extensiones" id="extensiones"
								maxlength="25" required aria-required="true">
						</div>
						
					</div>
					<div id="message_div">
						<span id="span_message"></span>
					</div>
				</div>
			</form>
			<button type="submit" id="submit_service_form">Aceptar</button>
			<button href="#" class="close-form">Cancelar</button>
		</div>
	</div>


	<div>
		<div>
			<div class="table-responsive usersTable">
				<table class="table">
					<thead>
						<tr>
							
							
							<th><span class="table-title">Pais Id</span></th>
							<th><span class="table-title">Nombre Pais</span></th>
							<th><span class="table-title">Nombre</span></th>
							<th><span class="table-title">Extensiones</span></th>
							
							<th style="width: 110px;">&nbsp;</th>
						</tr>
					<!--
						<tr>
							<th class="search-th"><input class="search col0"></th>
							<th class="search-th"><input class="search col1"></th>
							<th class="search-th"><input class="search col2"></th>
							<th class="search-th"><input class="search col3"></th>
							<th class="search-th"><input class="search col4"></th>
							<th style="width: 110px;">&nbsp;</th>
						</tr>
					-->
						<tr>
							<form id='test-header-filter' action="">
							
								
								
								<th class="search-th"><input name='idPais' value='${paisId}'></th>
								<th class="search-th"><input name='nombrePais' value='${nombrePais}'></th>
								<th class="search-th"><input name='name' value='${name}'></th>
								<th class="search-th"><input name='extensiones' value='${extensiones}'></th>
								
								<th><button type='button' onclick='filteringCatalogo();'>  FILTRAR  </button></th>
							</form>
						</tr>
					</thead>
					<tbody id="myTable" cellspacing="0" data-page="${page}" data-lastpage="${lastpage}" data-numpages="${numpages}">
						<c:choose>
							<c:when test="${empty serviciosList}">
								<tr>
									<td><span>No existen servicios.</span></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach items="${serviciosList}" var="servicio">
									<tr class="valid-result" id="row${servicio.key.id}" name="${servicio.key.id}"
										data-name="${servicio.name}" data-pais="${servicio.paisId}" data-extensiones="${servicio.extensiones}" data-paisNombre="${servicio.nombrePais}">
										<td><span>${servicio.paisId}</span></td>
										<td><span>${servicio.nombrePais}</span></td> 
										<td><span>${servicio.name}</span></td>
										<td><span>${servicio.extensiones}</span></td>

										<td>										
									<img class="vs" src="../img/vs.png">								
									<a class="lapiz" name="${servicio.key.id}" href="../catalogoModal.do?id=${servicio.key.id}"	id="lapiz${servicio.key.id}" data-toggle="modal" data-target="#edit-service"></a>
									<a class="papelera" name="${servicio.key.id}" data-toggle="modal" data-target="#confirm-delete" id="papelera${servicio.key.id}"></a>
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

	<div class="modal fade" id="edit-service" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" id="edit_service_dialog">
			<div class="modal-content">
		
			</div>
		</div>
	</div>

	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content noErase">
				<div class="">
					<h2>Eliminar Servicio</h2>
					<hr />
				</div>
				<div class="">
					<p>&iquest;Est&aacute; seguro que desea eliminar el servicio?<p>
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteServicio">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>