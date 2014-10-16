<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="alta_proyecto">



<h1>Gestión proyecto</h1>
<span class="btn-atras" onclick="window.location.href='../dashboard/gestionCliente.do'"></span>

<hr/>
<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span onclick="window.location.href='./gestionCliente.do' ">Gestión de clientes</span> > <span> Gestión de proyecto </span>
	</div>
<div class="newUserbox">
	<button id="newUserButton">
		Nuevo Proyecto<span class="proyecto_span"></span>
	</button>
	<button id="newCostoButton" onclick="window.location.href='./gestionCostes.do'">
			Nuevo coste<span class="coste_span"></span>
		</button>
	<button id="excel_btn" onclick="window.location.href='../../projectServlet?accion=xls'">
		Descargar Tabla<span class="excel_span"></span>
	</button>


	<div class="new-user-form-holder">
		<form id="new-project-form" name="new-project-form" action="/projectServlet"
			method="POST" novalidate="novalidate">
			<div class="form-container">
				
				<div class="form-field-divider left">
					<div class="form-field">
						<span class="lbl">Fecha alta proyecto<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" readonly="" value="" size="16" class="datepicker" data-target-id='fecha_alta_cliente' name="fecha_alta_cliente" id="fecha_alta_cliente" required aria-required="true">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Código Proyecto:</span>
						<input type="text" id="project_name" name="project_name" class="long readonly" unselectable="on" readonly="true">
					</div>
					
					<div class="form-field">
							<span class="lbl">Tipo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="tipo" id="tipo" required aria-required="true">
									<option value="default">Seleccionar</option>
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
								    <option value="default">Seleccionar...</option>
									<c:forEach items="${gestores_negocio}" var="user">	
										<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
									</c:forEach>					
								</select>
							</div>
						</div>
						
					<div class="form-field">
						<span class="lbl coste">Coste:</span>
						<input type="text" readonly="" id="coste" name="coste" class="long euro money readonly">
					</div>
				</div>
				
				<div class="form-field-divider right">
					
					
					<div class="form-field">
						<span class="lbl">Producto<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select class="selectpicker selected" id="producto" name="producto" required aria-required="true">
							    <option value="default">Seleccionar</option>
							    <option value="Global Netcash">Global Netcash</option>
							    <option value="H2H">H2H</option>
							    <option value="H2H-BancoRelay">H2H-BancoRelay</option>
							    <option value="Swift-BancoRelay">Swift-BancoRelay</option>
							    <option value="Swift-Fileact">Swift-Fileact</option>												
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Conectividad:</span>
						<div class="input">
							<select class="selectpicker" id="conectividad" name="conectividad">
							    <option value="default">-</option>
							   												
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha inicio valoración:</span>
						<div class="input">
							<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_inicio_valoracion' name="fecha_inicio_valoracion" id="fecha_inicio_valoracion">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin valoración:</span>
						<div class="input">
							<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_fin_valoracion' name="fecha_fin_valoracion" id="fecha_fin_valoracion">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha inicio viabilidad:</span>
						<div class="input">
							<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_inicio_viabilidad' name="fecha_inicio_viabilidad" id="fecha_inicio_viabilidad">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin viabilidad:</span>
						<div class="input">
							<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_fin_viabilidad' name="fecha_fin_viabilidad" id="fecha_fin_viabilidad">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Servicio:</span>
						<div class="input">
							<select class="selectpicker" id="servicio" name="servicio">
							    <option value="default">Seleccionar</option>
							    <option value="PDTE Doc Alcance en GCS">PDTE Doc Alcance en GCS</option>
							    <option value="C100 en confección">C100 en confección</option>
							    <option value="PDTE Valoración IT">PDTE Valoración IT</option>
							    <option value="PDTE Plan de Trabajo IT">PDTE Plan de Trabajo IT</option>
							    <option value="PDTE Visto Bueno del CL del plan de trabajo">PDTE Visto Bueno del CL del plan de trabajo</option>								    
							    <option value="En Desarrollo">En Desarrollo</option>
							    <option value="En Test - Conectividad">En Test - Conectividad</option>
							    <option value="En Test - Integración">En Test - Integración</option>
							    <option value="En Test - Aceptación">En Test - Aceptación</option>
							    <option value="Parado por Producto">Parado por Producto</option>
							    <option value="Parado por Negocio">Parado por Negocio</option>
							    <option value="Parado por IT">Parado por IT</option>
							    <option value="Excluido por negocio">Excluido por negocio</option>							    
							    <option value="Excluido por Timeout">Excluido por Timeout</option>
							    <option value="PDTE Implantar">PDTE Implantar</option>
							    <option value="En Penny Test">En Penny Test</option>
							    <option value="Implementado con OK">Implementado con OK</option>
							    <option value="Implementado sin OK">Implementado sin OK</option>												
							</select>
						</div>
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
		
		<div class="form-container relatedOptions">
			<h3>Otras acciones relacionadas:</h3>
			<div id="buttons_holder">
				<button type="button" data-target="#new-costo" data-toggle="modal" href="../newCosteModal.do" id="newCostoModalButton">Nuevo coste<span class="coste_span"></span></button>
				<button type="button" data-target="#new-conectividad" data-toggle="modal" href="../jsp/modal/modal-select-project.jsp" id="newConectividadModalButton">Nueva conectividad</button>
			</div>
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
						<th><span class="table-title">Código proyecto</span></th>
						<th><span class="table-title">Cliente</span></th>
						<th><span class="table-title">Clasificación</span></th>
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
									<tr class="valid-result" id="row${proyecto.key.id}" name="${proyecto.key.id}" data-servicio="${proyecto.servicio}" data-conectividad="${proyecto.conectividad}" data-producto="${proyecto.producto}" data-fecha-ini-viabilidad="${proyecto.str_fecha_inicio_viabilidad}" data-fecha-fin-viabilidad="${proyecto.str_fecha_fin_viabilidad}" data-fecha-fin-valoracion="${proyecto.str_fecha_fin_valoracion}" data-fecha-ini-valoracion="${proyecto.str_fecha_inicio_valoracion}" data-fecha-alta="${proyecto.fecha_alta_str}" data-coste="${proyecto.coste}" data-cliente="${proyecto.clienteKey}" data-nombre="${proyecto.cod_proyecto}" data-tipo="${proyecto.tipo}" data-clasificacion="${proyecto.clasificacion}" data-gestor-it="${proyecto.gestor_it}" data-gestor-negocio="${proyecto.gestor_negocio}" >
										<td><span>${proyecto.fecha_alta_str}</span></td>
										<td><span>${proyecto.cod_proyecto}</span></td>
										<td><span>${proyecto.clienteName}</span></td>
										<td><span>${proyecto.clasificacion}</span></td>
										<td><span>${proyecto.tipo}</span></td>
										<td><span>${proyecto.coste}<c:if test="${proyecto.coste eq ''}">0</c:if> &#8364;</span></td>
										<c:if test="${sessionScope.permiso != 5}">
										<td>										
											<img class="vs" src="../img/vs.png">								
										<!-- 	<a class="lapiz" name="${proyecto.key.id}" href="../projectModal.do?git=${proyecto.gestor_it}&gn=${proyecto.gestor_negocio}&client=${proyecto.clienteKey}"	id="lapiz${proyecto.key.id}" data-toggle="modal" data-target="#edit-project"></a>  -->
											<a class="lapiz" name="${proyecto.key.id}" href="../jsp/modal/edit-action.jsp" id="lapiz${proyecto.key.id}" data-toggle="modal" data-target="#edit-action"></a>				
											<a class="papelera" name="${proyecto.key.id}" data-toggle="modal" data-target="#confirm-delete" id="papelera${proyecto.key.id}"></a>
											
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


<div class="modal fade" id="edit-project" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="edit_user_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>

<div class="modal fade" id="new-costo" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="new_costo_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>

<div class="modal fade" id="coste-by-project" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="coste_by_project_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>


<div class="modal fade" id="new-conectividad" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="new_conectividad_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>

<div class="modal fade" id="edit-action" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="edit_action_dialog">
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
				<p>Se eliminarán todos los servicios asociados.</p>
				
			</div>
			<div class="modal-footer">
				<button type="button" class="pink-btn" id="deleteProject">Eliminar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>

</div>
