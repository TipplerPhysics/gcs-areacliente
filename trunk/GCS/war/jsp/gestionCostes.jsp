<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_cliente">
	<h1>Gestión costes</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	
	<hr/>
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Gestión de costes </span>
	</div>
	
	<div class="newUserbox">		
	
		<button id="newUserButton">
			Nuevo coste<a class="user_span"></a>
		</button>

		<button id="excel_btn" onclick="window.location.href='../../clienteServlet?accion=xls'">
			Descargar Tabla<a class="excel_span"></a>
		</button>

		<div class="new-user-form-holder">
			<form id="new-coste-form" name="new-client-form" action="/costeServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">

						<div class="form-field">
							<span class="lbl">Nombre proyectos<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly value="" size="16" name="nombre_proyecto" id="nombre_proyecto">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha alta costes<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker" data-target-id='fecha_alta_costes' name="fecha_alta_costes" id="fecha_alta_costes" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Número valoración:</span>
							<div class="input">
								<select>
									<option>Dade capullo</option>
									<option>Dade capullo</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha solicitud valoración<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker" data-target-id='fecha_solicitud_valoracion' name="fecha_solicitud_valoracion" id="fecha_solicitud_valoracion" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Gestión de cambio:</span>
							<div class="input">
								<select>
									<option>Dade capullo</option>
									<option>Dade capullo</option>
								</select>
							</div>
						</div>

					</div>
					<div class="form-field-divider right">
					
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly value="" size="16" name="nombre_cliente" id="nombre_cliente">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Gestión de cambio:</span>
							<div class="input">
								<select>
									<option>Dade capullo</option>
									<option>Dade capullo</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Gestor IT-registro:</span>
							<div class="input">
								<input type="text" readonly value="" size="16" name="nombre_gestor" id="nombre_gestor">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Comentarios:</span>
							<div class="input">
								<textarea rows="4" cols="50" name="comentarios" id="comentarios">
								
								</textarea>
							</div>
						</div>
							
					</div>						 
					<div id="message_div_cliente" class="message_div">
						<span id="span_message_cliente" class="span_message"></span>
					</div>
				</div>
			</form>
			<div id="buttons_new_client">
				<button type="submit" class="submit_form" id="submit_client_form">Aceptar</button>
				<button href="#" class="close-form">Cancelar</button>
			</div>
		</div>
	</div>
	<div>	
		<div>
			<div class="table-responsive usersTable">
				<table class="table">
					<thead>
						<tr>
							<th><span class="table-title">Fecha Entrada</span></th>
							<th><span class="table-title">Cliente</span></th>
							<th><span class="table-title">Nombre Proyecto</span></th>
							<th><span class="table-title">Proveedor GMM</span></th>
							<th><span class="table-title">Gestor registrado</span></th>
							<th style="width: 110px;">&nbsp;</th>
						</tr>
						<tr>
							<th class="search-th"><input class="search col0"></th>
							<th class="search-th"><input class="search col1"></th>
							<th class="search-th"><input class="search col2"></th>
							<th class="search-th"><input class="search col3"></th>
							<th class="search-th"><input class="search col4"></th>
							<th style="width: 110px;">&nbsp;</th>
						</tr>
					</thead>
					<tbody id="myTable" cellspacing="0">
						<c:choose>
							<c:when test="${empty costes}">
								<tr>
									<td><span>No existen costes.</span></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach items="${costes}" var="costes">
									<tr class="valid-result" id="row${costes.key.id}" name="${costes.key.id}">
										<td><span></span></td>
										<td><span></span></td>
										<td><span></span></td>
										<td><span></span></td>
										<td><span></span></td>
										<td><span></span></td>
										<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
										<td>										
											<img class="vs" src="../img/vs.png">								
											<a class="lapiz" name="${cliente.key.id}" href="../clienteModal.do"	id="lapiz${cliente.key.id}" data-toggle="modal" data-target="#edit-client"></a>
									
											<c:if test="${sessionScope.permiso != 3}">
												<a class="papelera" name="${cliente.key.id}" data-toggle="modal" data-target="#confirm-delete" id="papelera${cliente.key.id}"></a>
											</c:if>
										</td>
										</c:if>
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
			</div>
		</div>
	</div>

	<div class="modal fade" id="edit-client" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" id="edit_client_dialog">
			<div class="modal-content">
		
			</div>
		</div>
	</div>

	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="">
					<h2>Eliminar Cliente</h2>
					<hr />
				</div>
				<div class="">
					<p>Se eliminarÃ¡n todos los proyectos asociados.<p>
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteClient">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>