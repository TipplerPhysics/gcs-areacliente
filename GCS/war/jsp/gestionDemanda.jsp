<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_demanda">

	<img class="users_title_icon" src="../img/user.png"><h1>Alta petición</h1>
	<hr/>
	
	<div class="newUserbox">
		<button id="newUserButton">
			Nueva Petición<a class="demanda_span"></a>
		</button>
		<button id="excel_btn" onclick="window.location.href='../../demandaServlet?accion=xls'">
			Descargar Tabla<a class="excel_span"></a>
		</button>


		<div class="new-user-form-holder">
			<form id="new-demanda-form" name="new-user-form" action="/demandaServlet"
				method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Fecha entrada petición:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker datefuture fromTo" data-target-id='fecha_solicitud_asignacion' name="fecha_entrada_peticion" id="fecha_entrada_peticion" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Hora petición:</span>
							<div class="input">
								<select class="selectpicker selected time" id="hora_peticion" name="hora_peticion" required aria-required="true">
									<c:forEach items="${horasList}" var="hora">
									<option value="${hora}">${hora}</option>
									</c:forEach>
								</select>
								<span class="time-span">:</span>
								<select class="selectpicker selected time" id="min_peticion" name="min_peticion" required aria-required="true">
									<c:forEach items="${minutosList}" var="min">
									<option value="${min}">${min}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Cliente:</span>
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
							<span class="lbl">Gestor de negocio:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_negocio" name="gestor_negocio">
								    <option value="default" selected>Seleccionar</option>
									<c:forEach items="${gestores_demanda}" var="user">	
										<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
									</c:forEach>					
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Tipo:</span>
							<div class="input">
								<select class="selectpicker selected" name="tipo" id="tipo" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<option value="CIB">CIB</option>
									<option value="BEC">BEC</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Devuelta:</span>
							<div class="input">
								<select class="selectpicker selected" name="devuelta" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<option value="SI">SI</option>
									<option value="NO">NO</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Estado:</span>
							<div class="input">
								<select class="selectpicker selected" name="estado" id="estado" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<option value="Pendiente de asignación">Pendiente de asignación</option>
									<option value="Asignada">Asignada</option>
									<option value="Devuelta">Devuelta</option>
									<option value="Parada">Parada</option>
									<option value="Desestimada">Desestimada</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Catalogación de petición:</span>
							<div class="input">
								<select class="selectpicker selected" name="catalogacion_peticion" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<option value="Implantación">Implantación</option>
									<option value="SEPA">SEPA</option>
									<option value="Migración IA">Migración IA</option>
									<option value="Estandarización Clientes">Estandarización Clientes</option>
									<option value="Evolutivo">Evolutivo</option>
									<option value="Pruebas Cliente">Pruebas Cliente</option>
								</select>
							</div>
						</div>
					</div><div class="form-field-divider right">
						<div class="form-field">
							<span class="align-top lbl">Motivo de catalogación:</span>
							<div class='input'>
								<textarea name="motivo_catalogacion" id="motivo_catalogacion" class="long"></textarea>
							</div>
						</div>
						<div class="form-field">
							<span class="align-top lbl">Comentarios:</span>
							<div class='input'>
								<textarea id="comentarios" name="comentarios" class="long"></textarea>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha solicitud asignación:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_solicitud_asignacion" id="fecha_solicitud_asignacion">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Hora solicitud asignación:</span>
							<div class="input">
								<select class="selectpicker time" id="hora_solicitud_asignacion" name="hora_solicitud_asignacion">
									<c:forEach items="${horasList}" var="hora">
										<option value="${hora}">${hora}</option>
									</c:forEach>
								</select>
								<span class="time-span">:</span>
								<select class="selectpicker time" id="min_solicitud_asignacion" name="min_solicitud_asignacion">
									<c:forEach items="${minutosList}" var="min">
										<option value="${min}">${min}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Gestor IT pre-asignado:</span>
							<div class="input">
								<select class="selectpicker selected" id="gestor_it" name="gestor_it" required aria-required="true">	
								<option value="default" selected>Seleccionar...</option>
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div id="message_div">
						<span id="span_message"></span>
					</div>
				</div>
			</form>
			<button type="submit" class="submit_form" id="submit_demanda_form">Aceptar</button>
			<button href="#" class="close-form">Cancelar</button>
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
							<th><span class="table-title">Tipo</span></th>
							<th><span class="table-title">Estado</span></th>
							<th><span class="table-title">Cod. Petición</span></th>
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
							<c:when test="${empty demandaList}">
								<tr>
									<td><span>No existen peticiones.</span></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach items="${demandaList}" var="demanda">
									<tr class="valid-result" id="row${demanda.key.id}" data-fecha-comun="${demanda.str_fecha_solicitud_asignacion}" data-hora-comun="${demanda.hora_solicitud_asignacion}" data-gestor-asig="${demanda.gestor_it}">
									<tr class="valid-result" id="row${demanda.key.id}" data-fecha-entrada="${demanda.str_fecha_entrada_peticion}" data-hora-entrada="${demanda.hora_entrada_peticion}" data-gestor-asig="${demanda.gestor_it}">
										<td><span>${demanda.str_fecha_entrada_peticion}</span></td>
										<td><span>${demanda.clienteName}</span></td>
										<td><span>${demanda.tipo}</span></td>
										<td><span>${demanda.estado}</span></td>
										<td><span>${demanda.cod_peticion}</span></td>
										<td><img class="vs" src="../img/vs.png"><a class="lapiz" name="${demanda.key.id}"	id="lapiz${demanda.key.id}"></a><a class="papelera" name="${demanda.key.id}" data-toggle="modal" data-target="#confirm-delete" id="papelera${demanda.key.id}"></a></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="col-md-12 text-center">
				<ul class="pagination" id="myPager"></ul>
			</div>
		</div>
	</div>



	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="">
					<h2>Eliminar petición</h2>
					<hr />
				</div>
				<div class="">
					<p>&iquest;Est&aacute; seguro que desea eliminar la petición?
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteDemanda">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>