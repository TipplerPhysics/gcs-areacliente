<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
							<input type="text" readonly="" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_alta_cliente' name="fecha_alta_cliente" id="fecha_alta_cliente" required aria-required="true">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Código Proyecto:</span>
						<input type="text" id="project_name" name="project_name" class="long readonly" unselectable="on" readonly="true" maxlength="25">
					</div>
					
					<div class="form-field">
							<span class="lbl">Tipo implementacion<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="tipo" id="tipo-imp-proj" required aria-required="true">
									<option value="default">Seleccionar</option>
									<option value="Implementación">Implementaci&oacuten</option>
									<option value="Evolutivo">Evolutivo</option>
									<option value="Prueba cliente">Prueba cliente</option>
									<option value="Migración">Migraci&oacuten</option>
									<option value="Consulta">Consulta</option>
									<option value="Viabilidad">Viabilidad</option>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Subtipo:</span>
							<div class="input">
								<select class="selectpicker selected" name="subtipo" id="subtipo_imp" required aria-required="true">
									<option value=" ">-</option>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="input_cliente" class="selectpicker selected" name="cliente" required aria-required="true">
									<option value="default">Seleccionar</option>
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
									<option value="default">Seleccionar</option>
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
								<option value="default">Seleccionar</option>
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
						<span class="lbl">Fecha inicio especificaciones:</span>
						<div class="input">
							<input type="text" value="" size="16" maxlength="25" class="datepicker fromTo"  data-target-id='fecha_fin_valoracion' name="fecha_inicio_valoracion" id="fecha_inicio_valoracion">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin especificaciones:</span>
						<div class="input">
							<input type="text" value="" size="16" maxlength="25" class="datepicker" name="fecha_fin_valoracion" id="fecha_fin_valoracion">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha inicio viabilidad:</span>
						<div class="input">
							<input type="text" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_viabilidad' name="fecha_inicio_viabilidad" id="fecha_inicio_viabilidad">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin viabilidad:</span>
						<div class="input">
							<input type="text" value="" size="16" maxlength="25" class="datepicker" name="fecha_fin_viabilidad" id="fecha_fin_viabilidad">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha envio C100:</span>
						<div class="input">
							<input type="text" value="" size="16" maxlength="25" class="datepicker" name="envio_c100" id="envio_c100">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha OK Negocio:</span>
						<div class="input">
							<input type="text" value="" size="16" maxlength="25" class="datepicker" name="ok_negocio" id="ok_negocio">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Url carpeta Google Drive:</span>
						<input type="text" id="url_doc_google_drive" name="url_doc_google_drive" class="long" maxlength="500">
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
			
				<fieldset>
					<span class="lbl">Coste</span>
					<button type="button" data-target="#new-costo" data-toggle="modal" href="../newCosteModal.do" id="newCostoModalButton">Nuevo</button>
					<button id="newCostoButton" onclick="window.location.href='./gestionCostes.do'">
						Ver
					</button>
				</fieldset>
				
				<fieldset>
					<span class="lbl">Servicio</span>
					<button type="button" data-target="#new-servicio" data-toggle="modal" href="../newService.do" id="newServiceModalButton">Nuevo</button>
					<button id="newServicioButton" onclick="window.location.href='./gestionServicio.do'">
						Ver<span class="servicio_span"></span>
					</button>
				</fieldset>
				
				<fieldset>
					<span class="lbl">Conectividad</span>
					<button type="button" data-target="#project-conectividad" data-toggle="modal" href="../projectConectivity.do" id="newConectividadModalButton">Nuevo</button>
				</fieldset>
				
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
									<tr class="valid-result" id="row${proyecto.key.id}" name="${proyecto.key.id}" data-servicio="${proyecto.servicio}" data-conectividad="${proyecto.conectividad}" data-producto="${proyecto.producto}" data-fecha-ini-viabilidad="${proyecto.str_fecha_inicio_viabilidad}" data-subtipo="${proyecto.subtipo}" data-fecha-fin-viabilidad="${proyecto.str_fecha_fin_viabilidad}" data-fecha-fin-valoracion="${proyecto.str_fecha_fin_valoracion}" data-fecha-ini-valoracion="${proyecto.str_fecha_inicio_valoracion}" data-fecha-alta="${proyecto.fecha_alta_str}" data-coste="${proyecto.coste}" data-cliente="${proyecto.clienteKey}" data-cliente-name="${proyecto.clienteName}" data-nombre="${proyecto.cod_proyecto}" data-tipo="${proyecto.tipo}" data-clasificacion="${proyecto.clasificacion}" data-gestor-it="${proyecto.gestor_it}" data-gestor-negocio="${proyecto.gestor_negocio}"  data-url-doc-google-drive="${proyecto.url_doc_google_drive}">
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

<div class="modal fade" id="edit-service" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="edit_service_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>

<div class="modal fade" id="select-service" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="select_service_dialog">
		<div class="modal-content">
			
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

<div class="modal fade" id="new-servicio" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="new_servicio_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>

<div class="modal fade" id="project-conectividad" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="project-conectividad_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>

<div class="modal fade" id="project-servicio" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="project-servicio_dialog">
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

<div class="modal fade" id="edit-costo" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="edit_costo_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>

<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content noErase">
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
