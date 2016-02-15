<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_coste">
	<h1>Gestión costes</h1>
	<span class="btn-atras" onclick="window.location.href='./gestionProyecto.do'"></span>
	
	<hr/>
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span onclick="window.location.href='./gestionCliente.do' ">Gestión de clientes</span> > <span onclick="window.location.href='./gestionProyecto.do' ">Gestión de proyectos</span> > <span> Gestión de costes </span>
	</div>
	
	<div class="newUserbox">		
	
		<button id="newUserButton">
			Nuevo coste<span class="coste_span"></span>
		</button>

		<button id="excel_btn" onclick="window.location.href='../../costeServlet?accion=xls'">
			Descargar Tabla<span class="excel_span"></span>
		</button>

		<div class="new-user-form-holder">
			<form id="new-coste-form" name="new-coste-form" action="/costeServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
					
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select data-live-search="true" id="input_cliente" class="selectpicker selected" name="cliente" required aria-required="true" onchange="getProjectsByClient('');">
									<option value="default">Seleccionar</option>
									<c:forEach items="${clientes}" var="cliente">	
										<option value="${cliente.key.id}">${cliente.nombre}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Código de control:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" name="numero_control" id="numero_control" class="long readonly" unselectable="on" readonly="true">
							</div>
						</div>

						
						<div class="form-field">
							<span class="lbl">Fecha alta costes<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_alta_costes' name="fecha_alta_costes" id="fecha_alta_costes" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Número valoración<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select name="num_valoracion" class="long selectpicker selected" required aria-required="true">
									<option value="default">Seleccionar</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
								</select>
							</div>
						</div>	
						 <div class="form-field">
							<span class="lbl">Tipo Coste<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select name="tipo_coste" class="long selectpicker selected" required aria-required="true">
									<option value="default">Seleccionar</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha solicitud valoración<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_recepcion_valoracion' name="fecha_solicitud_valoracion" id="fecha_solicitud_valoracion" required aria-required="true">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha recepción valoración:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker"  name="fecha_recepcion_valoracion" id="fecha_recepcion_valoracion">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Plazo estimado:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker"  name="plazo_estimado" id="plazo_estimado">
							</div>
						</div>

					</div>
					<div class="form-field-divider right">
						<div class="form-field">
							<span class="lbl">Nombre proyectos<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="project" name="project" required aria-required="true"  class="long selected selectpicker" data-live-search="true">
									<option value="default">-</option>
								</select>						
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Equipo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="equipo" name="equipo" class="long selectpicker selected" required aria-required="true" data-live-search="true">
									<option value="default">Seleccionar</option>
									<option value="CAPGEMINI">CAPGEMINI</option>
									<option value="GESTOR IT">GESTOR IT</option>
									<option value="INNOVERY">INNOVERY</option>
									<option value="IS">IS</option>									
									<option value="SOLUTIONS">SOLUTIONS</option>
									<option value="SOPORTE GNC">SOPORTE GNC</option>
									<option value="SOPORTE SWIFT">SOPORTE SWIFT</option>									
									<option value="TELEMÁTICOS">TELEMÁTICOS</option>
									
									
								</select>
							</div>
						</div>
										
						<div class="form-field">
							<span class="lbl">Gestor IT-registro<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" required aria-required="true"  id="gestor_it" name="gestor_it" data-live-search="true">	
								<option value="default" selected>Seleccionar</option>
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
						
						<div class="form-field">
							<span class="lbl">Asunto:</span>
							<div class="input">
								<textarea name="asunto" id="asunto"></textarea>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Control Presupuestario:</span>
							<div class="input">
								<input type="text" size="16" maxlength="25" name="control_presupuestario" id="control_presupuestario">
							</div>
						</div>		
						
							
					</div>	
					<div class="form-field-down">
						<span class="lbl">Horas/Fases:</span>
						<div class="form-field">
							<span class="lbl">Análisis:</span>
							<div class="input">
								<input id="analisis_horas" name="analisis_horas" class="horas number calcHoras"/><input id="analisis_coste" name="analisis_coste" class="coste number calcCoste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Diseño:</span>
							<div class="input">
								<input id="disenio_horas" name="diseño_horas" class="horas number calcHoras disenio_horeas"/><input id="disenio_coste" name="diseño_coste" class="coste number calcCoste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Construcción:</span>
							<div class="input">
								<input id="construccion_horas" name="construccion_horas" class="horas number calcHoras"/><input id="construccion_coste" name="construccion_coste" class="coste number calcCoste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Pruebas:</span>
							<div class="input">
								<input id="pruebas_horas" name="pruebas_horas" class="horas number calcHoras"/><input id="pruebas_coste" name="pruebas_coste" class="coste number calcCoste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestión:</span>
							<div class="input">
								<input id="gestion_horas" name="gestion_horas" class="horas calcHoras number"/><input id="gestion_coste" name="gestion_coste" class="coste number calcCoste"/>
							</div>
							<div class="input labels">
								<span class="lbl">Horas</span><span class="lbl">Coste</span>
							</div>
						</div>
						
						<div class="form-field total">
						<span class="lbl"></span>
							<div class="input total">
								<input id="total_horas" required name="total_horas" class="horas number " value="0"/><input id="total_coste" name="total_coste" class="coste number"/>
							</div>
							<div class="input">
								<span class="lbl">Total Horas<span class="required-asterisk"> </span></span><span class="lbl">Total Coste</span>
							</div>
						</div>
					</div>					 
					
				</div>
				
			</form>
			<div class="message-container">
				<div id="message_div" class="message_div">
					<span id="span_message" class="span_message"></span>
				</div>
			</div>
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
							<th><span class="table-title">Código Proyecto</span></th>
							<th><span class="table-title">Equipo</span></th>
							<th><span class="table-title">Gestor IT-Registro</span></th>
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
							
								<th style='width:18%;' class="search-th"><input style='width:28%;' name='fechaDia' value='${fechaDia}'><input style='width:28%;' name='fechaMes' value='${fechaMes}'><input style='width:44%;' name='fechaAnio' value='${fechaAnio}'></th>
								<th class="search-th"><input name='cliente' value='${cliente}'></th>
								<th class="search-th"><input name='codigoPro' value='${codigoPro}'></th>
								<th class="search-th"><input name='equipo' value='${equipo}'></th>
								<th class="search-th"><input name='gestorIt' value='${gestorIt}'></th>
								<th><button type='button' onclick='filteringCostes();'>  FILTRAR  </button></th>
							</form>
						</tr>
						
					</thead>
					
					<tbody id="myTable" cellspacing="0" data-page="${page}" data-lastpage="${lastpage}" data-numpages="${numpages}">
					
						<c:choose>
							<c:when test="${empty costes}">
								<tr>
									<td><span>No existen costes.</span></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach items="${costes}" var="coste">
									<tr class="valid-result" id="row${coste.key.id}" data-num-control="${coste.num_control}" data-control-presupuestario="${coste.control_presupuestario}" data-total-horas="${coste.horas_total}" data-total-coste="${coste.coste_total}" data-gestion-horas="${coste.horas_gestion}" data-gestion-coste="${coste.coste_gestion}" data-pruebas-horas="${coste.horas_pruebas}" data-pruebas-coste="${coste.coste_pruebas}" data-construccion-horas="${coste.horas_construccion}" data-construccion-coste="${coste.coste_construccion}" data-disenio-horas="${coste.horas_diseño}" data-disenio-coste="${coste.coste_diseño}" data-analisis-horas="${coste.horas_analisis}" data-analisis-coste="${coste.coste_analisis}" name="${coste.key.id}" data-nombre-cliente="${coste.cliente_name}" data-nombre-proyecto="${coste.project_name}" data-num-control="${coste.num_control}" data-equipo="${coste.equipos}" data-fecha-alta="${coste.str_fecha_alta}" data-gestor-it="${coste.gestor_it_key}" data-num-valoracion="${coste.num_valoracion}" data-comentarios="${coste.comentarios}" data-fecha-solicitud-val="${coste.str_fecha_solicitud_valoracion}" data-fecha-recepcion-val="${coste.str_fecha_recepcion_valoracion}">
										<td><span>${coste.str_fecha_alta}</span></td>
										<td><span>${coste.cliente_name}</span></td>
										<td><span>${coste.project_name}</span></td>
										<td><span>${coste.equipos}</span></td>
										<td><span>${coste.gestor_it_name}</span></td>
										
										<td>										
											<img class="vs" src="../img/vs.png">								
											<a class="lapiz" name="${coste.key.id}" href="../costeModal.do?git=${coste.gestor_it_key}&id=${coste.key.id}"	id="lapiz${coste.key.id}" data-toggle="modal" data-target="#edit-coste"></a>
									
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
				<div class="paginationGoto" />
			</div>
		</div>
	</div>

	<div class="modal fade" id="edit-coste" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" id="edit_coste_dialog">
			<div class="modal-content">
		
			</div>
		</div>
	</div>

	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content noErase">
				<div class="">
					<h2>Eliminar Coste</h2>
					<hr />
				</div>
				<div class="">
					<p>¿Está seguro que desea eliminar el coste?<p>
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteCoste">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>