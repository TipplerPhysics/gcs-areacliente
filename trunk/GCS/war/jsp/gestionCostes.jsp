<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_coste">
	<h1>Gesti�n costes</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	
	<hr/>
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Gesti�n de costes </span>
	</div>
	
	<div class="newUserbox">		
	
		<button id="newUserButton">
			Nuevo coste<a class="coste_span"></a>
		</button>

		<button id="excel_btn" onclick="window.location.href='../../clienteServlet?accion=xls'">
			Descargar Tabla<a class="excel_span"></a>
		</button>

		<div class="new-user-form-holder">
			<form id="new-coste-form" name="new-coste-form" action="/costeServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
					
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="input_cliente" class="selectpicker selected" name="cliente" required aria-required="true" onchange="getProjectsByClient();">
									<option value="default">Seleccionar...</option>
									<c:forEach items="${clientes}" var="cliente">	
										<option value="${cliente.key.id}">${cliente.nombre}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">N�mero de control:</span>
							<div class="input">
								<input type="text" value="" size="16" disabled name="numero_control" id="numero_control">
							</div>
						</div>

						
						<div class="form-field">
							<span class="lbl">Fecha alta costes<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_alta_costes' name="fecha_alta_costes" id="fecha_alta_costes" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">N�mero valoraci�n:</span>
							<div class="input">
								<select name="num_valoracion" class="long selectpicker">
									<option value="default">Seleccionar...</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha solicitud valoraci�n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_solicitud_valoracion' name="fecha_solicitud_valoracion" id="fecha_solicitud_valoracion" required aria-required="true">
							</div>
						</div>
						

					</div>
					<div class="form-field-divider right">
						<div class="form-field">
							<span class="lbl">Nombre proyectos<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="project" name="project" required aria-required="true"  class="long selected selectpicker">
									<option value="default">-</option>
								</select>						
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Equipo:</span>
							<div class="input">
								<select id="equipo" name="equipo" class="long selectpicker">
									<option value="default">Seleccionar...</option>
									<option value="Innovery">Innovery</option>
									<option value="Capgemini">Capgemini</option>
									<option value="Solutions">Solutions</option>
									<option value="Soporte Swift">Soporte Swift</option>
									<option value="IS">IS</option>
									<option value="Telem�ticos">Telem�ticos</option>
									<option value="Gestor IT">Gestor IT</option>
									
								</select>
							</div>
						</div>
										
						<div class="form-field">
							<span class="lbl">Gestor IT-registro:</span>
							<div class="input">
								<select class="selectpicker" required aria-required="true"  id="gestor_it" name="gestor_it">	
								<option value="default" selected>Seleccionar...</option>
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Comentarios:</span>
							<div class="input">
								<textarea name="comentarios" id="comentarios"></textarea>
							</div>
						</div>
						
							
					</div>	
					<div class="form-field-down">
						<span class="lbl">Horas/Fases:</span>
						<div class="form-field">
							<span class="lbl">Analisis:</span>
							<div class="input">
								<input id="analisis_horas" name="analisis_horas" class="horas"/><input id="analisis_coste" name="analisis_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Dise�o:</span>
							<div class="input">
								<input id="dise�o_horas" name="dise�o_horas" class="horas"/><input id="dise�o_coste" name="dise�o_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Construcci�n:</span>
							<div class="input">
								<input id="construccion_horas" name="construccion_horas" class="horas"/><input id="construccion_coste" name="construccion_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Pruebas:</span>
							<div class="input">
								<input id="pruebas_horas" name="pruebas_horas" class="horas"/><input id="pruebas_coste" name="pruebas_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gesti�n:</span>
							<div class="input">
								<input id="gestion_horas" name="gestion_horas" class="horas"/><input id="gestion_coste" name="gestion_coste" class="coste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field total">
						<span class="lbl"></span>
							<div class="input total">
								<input id="total_horas" name="total_horas" class="horas"/><input id="total_coste" name="total_coste" class="coste"/>
							</div>
							<div class="input">
								<span class="lbl">Total Horas</span><span class="lbl">Total Coste</span>
							</div>
						</div>
					</div>					 
					<div id="message_div" class="message_div">
						<span id="span_message" class="span_message"></span>
					</div>
				</div>
				
			</form>
			<div id="buttons_new">
				<button type="submit" class="submit_form" id="submit_cost_form">Aceptar</button>
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
							<th><span class="table-title">Equipo</span></th>
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
								<c:forEach items="${costes}" var="coste">
									<tr class="valid-result" id="row${coste.key.id}" name="${coste.key.id}">
										<td><span>${coste.str_fecha_alta}</span></td>
										<td><span>${coste.cliente_name}</span></td>
										<td><span>${coste.str_fecha_alta}</span></td>
										<td><span>${coste.str_fecha_alta}</span></td>
										<td><span>${coste.str_fecha_alta}</span></td>
										<td><span>${coste.str_fecha_alta}</span></td>
										<td>										
											<img class="vs" src="../img/vs.png">								
											<a class="lapiz" name="${coste.key.id}" href="../clienteModal.do"	id="lapiz${coste.key.id}" data-toggle="modal" data-target="#edit-coste"></a>
									
											<a class="papelera" name="${coste.key.id}" data-toggle="modal" data-target="#confirm-delete" id="papelera${coste.key.id}"></a>
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
					<p>Se eliminarán todos los proyectos asociados.<p>
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteClient">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>