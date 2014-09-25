<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_cliente">

	<h1>Gestión costes</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	
	
	<hr/>
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Gestión de usuarios </span>
	</div>
	
	<div class="newUserbox">
		<c:if test="${sessionScope.permiso != 3 and sessionScope.permiso != 5 and sessionScope.permiso != 4}">
			<button id="newUserButton">
				Nuevo Cliente<a class="user_span"></a>
			</button>
		</c:if>
		
		<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
			<button id="newProjectButton" onclick="location.href = './gestionProyecto.do';">
				Nuevo Proyecto<a class="proyecto_span_fixed"></a>
			</button>	
		</c:if>	
		
		<button id="excel_btn" onclick="window.location.href='../../clienteServlet?accion=xls'">
			Descargar Tabla<a class="excel_span"></a>
		</button>


		<div class="new-user-form-holder">
			<form id="new-client-form" name="new-client-form" action="/clienteServlet"
				method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						
						
						
						
					</div>
					<div class="form-field-divider right">
						
							
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
							<th><span class="table-title">ID Cliente</span></th>
							<th><span class="table-title">Cliente</span></th>
							<th><span class="table-title">Referencia Global</span></th>
							<th><span class="table-title">Tipo</span></th>
							<th><span class="table-title">Criticidad</span></th>
							<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
								<th style="width: 110px;">&nbsp;</th>
							</c:if>
						</tr>
						<tr>
							<th class="search-th"><input class="search col0"></th>
							<th class="search-th"><input class="search col1"></th>
							<th class="search-th"><input class="search col2"></th>
							<th class="search-th"><input class="search col3"></th>
							<th class="search-th"><input class="search col4"></th>
							<th class="search-th"><input class="search col5"></th>
							<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
								<th style="width: 110px;">&nbsp;</th>
							</c:if>
						</tr>
					</thead>
					<tbody id="myTable" cellspacing="0">
						<c:choose>
							<c:when test="${empty clientes}">
								<tr>
									<td><span>No existen clientes.</span></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach items="${clientes}" var="cliente">
									<tr class="valid-result" id="row${cliente.key.id}" name="${cliente.key.id}" data-fecha-alta="${cliente.str_fecha_alta_cliente}" data-nombre="${cliente.nombre}" data-tipo="${cliente.tipo}" data-criticidad="${cliente.criticidad}" data-ref-global="${cliente.ref_global}" data-logo-url="${cliente.logo_url}" data-paises="${cliente.paises}">
										<td><span>${cliente.str_fecha_alta_cliente}</span></td>
										<td><span>${cliente.clientId}</span></td>
										<td><span>${cliente.nombre}</span></td>
										<td><span>${cliente.ref_global}</span></td>
										<td><span>${cliente.tipo}</span></td>
										<td><span>${cliente.criticidad}</span></td>
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
					<p>Se eliminar�n todos los proyectos asociados.<p>
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteClient">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>