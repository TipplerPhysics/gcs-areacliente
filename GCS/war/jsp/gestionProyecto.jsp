<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="alta_proyecto">


<img class="users_title_icon" src="../img/user.png">
<h1>Gesti�n proyecto</h1>
<span class="btn-atras" onclick="window.location.href='../../'"></span>

<hr/>

<div class="newUserbox">
	<button id="newUserButton">
		Nuevo Proyecto<a class="proyecto_span"></a>
	</button>
	<button id="excel_btn" onclick="window.location.href='../../usersServlet?accion=xls'">
		Descargar Tabla<a class="excel_span"></a>
	</button>


	<div class="new-user-form-holder">
		<form id="new-project-form" name="new-project-form" action="/projectServlet"
			method="POST" novalidate="novalidate">
			<div class="form-container">
				
				<div class="form-field-divider left">
					<div class="form-field">
						<span class="lbl">Fecha alta cliente<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" readonly="" value="" size="16" class="datepicker" data-target-id='fecha_alta_cliente' name="fecha_alta_cliente" id="fecha_alta_cliente" required aria-required="true">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Nombre Proyecto<span class="required-asterisk">*</span>:</span>
						<input type="text" aria-required="true" required="" id="project_name" name="project_name" class="long">
					</div>
					
					<div class="form-field">
							<span class="lbl">Tipo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="tipo" id="tipo" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<option value="IMPL">IMPL</option>
									<option value="SEPA">SEPA</option>
									<option value="IMPL-OB-C">IMPL-OB-C</option>
									<option value="IMPL-OB-S">IMPL-OB-S</option>
									<option value="SEPA-OB-C">SEPA-OB-C</option>
									<option value="SEPA-OB-S">SEPA-OB-S</option>
									<option value="MIGR-IA">MIGR-IA</option>
									<option value="MIGR-OB">MIGR-OB</option>
									<option value="MIGR-CH">MIGR-CH</option>
									<option value="MIGR-INF">MIGR-INF</option>
									<option value="EVOL-C">EVOL-C</option>
									<option value="EVOL-S">EVOL-S</option>
									<option value="PRUC">PRUC</option>
									<option value="CONS">CONS</option>
									<option value="VIAB">VIAB</option>
									
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="input_cliente" class="selectpicker selected" name="cliente" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<c:forEach items="${clientes}" var="cliente">	
										<option value="${cliente.key.id}">${cliente.nombre}</option>
									</c:forEach>
								</select>
							</div>
						</div>
				</div>
				
				<div class="form-field-divider right">
					<div class="form-field">
							<span class="lbl">Clasificaci&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="clasificacion" id="clasificacion" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>									
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestor IT<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" id="gestor_it" name="gestor_it" required aria-required="true">	
								<option value="default">Seleccionar...</option>
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestor de negocio<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" id="gestor_negocio" name="gestor_negocio" required aria-required="true">
								    <option value="default">Seleccionar</option>
									<c:forEach items="${gestores_negocio}" var="user">	
										<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
									</c:forEach>					
								</select>
							</div>
						</div>
						
						<div class="form-field">
						<span class="lbl">Coste<span class="required-asterisk">*</span>:</span>
						<input type="text" aria-required="true" required="" id="coste" name="coste" class="long euro money">
					</div>
				</div>
				
				<div id="message_div">
					<span id="span_message"></span>
				</div>
			</div>
				
		

		</form>
		<div id="buttons_holder">
			<button type="submit" id="submit_project_form">Aceptar</button>
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
						<th><span class="table-title">Fecha proyecto</span></th>
						<th><span class="table-title">C�digo proyecto</span></th>
						<th><span class="table-title">Cliente</span></th>
						<th><span class="table-title">Clasificaci�n</span></th>
						<th><span class="table-title">Tipo</span></th>
						<th><span class="table-title">Coste</span></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
					<tr>
						<th class="search-th"><input class="search col0"></th>
						<th class="search-th"><input class="search col1"></th>
						<th class="search-th"><input class="search col2"></th>
						<th class="search-th"><input class="search col3"></th>
						<th class="search-th"><input class="search col4"></th>
						<th class="search-th"><input class="search col5"></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="myTable" cellspacing="0">
					<c:choose>
							<c:when test="${empty clientes}">
								<tr>
									<td><span>No existen proyectos.</span></td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${proyectos}" var="proyecto">
									<tr class="valid-result" id="row${proyecto.key.id}" name="${proyecto.key.id}" data-fecha-alta="${proyecto.fecha_alta_str}" data-nombre="${proyecto.nombre}" data-tipo="${proyecto.tipo}" data-clasificacion="${proyecto.clasificacion}" data-gestor-it="${proyecto.gestor_it}" data-gestor-negocio="${proyecto.gestor_negocio}" >
										<td><span>${proyecto.fecha_alta_str}</span></td>
										<td><span>${proyecto.fecha_alta_str}</span></td>
										<td><span>${proyecto.fecha_alta_str}</span></td>
										<td><span>${proyecto.fecha_alta_str}</span></td>
										<td><span>${proyecto.fecha_alta_str}</span></td>
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


<div class="modal fade" id="edit-project" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="edit_user_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>


<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="">
				<h2>Eliminar proyecto</h2>
				<hr />
			</div>
			<div class="">
				<p>&iquest;Est&aacute; seguro que desea eliminar al proyecto?</p>
				
			</div>
			<div class="modal-footer">
				<button type="button" class="pink-btn" id="deleteProject">Eliminar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>
