<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_demanda">

	<h1>Gesti&oacute;n de peticiones</h1>
	<!-- <button class="btn-atras" type="button">Atr&aacute;s</button>  -->
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	<hr/>
	
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Gesti�n de peticiones </span>
	</div>
	
	<div class="newUserbox">
		
		<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
			<button id="newUserButton">
				Nueva Petici&oacute;n<a class="demanda_span"></a>
			</button>
		</c:if>
		<button id="excel_btn" onclick="window.location.href='../../demandaServlet?accion=xls'">
			Descargar Tabla<a class="excel_span"></a>
		</button>


		<div class="new-user-form-holder">
			<form id="new-demanda-form" name="new-user-form" action="/demandaServlet"
				method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl">Fecha entrada petici&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker fromTo" data-target-id='fecha_solicitud_asignacion' name="fecha_entrada_peticion" id="fecha_entrada_peticion" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Hora petici&oacute;n<span class="required-asterisk">*</span>:</span>
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
								<!-- <![endif]-->
							</div>
						</div>
						<div class="form-field cliente">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="input_cliente" class="selectpicker selected" name="cliente" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<c:forEach items="${clientes}" var="cliente">	
										<option value="${cliente.key.id}">${cliente.nombre}</option>
									</c:forEach>
								</select>
								<button type="button" id="btn_new_client" data-target="#new-client" data-toggle="modal">Nuevo</button>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Gestor de negocio:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_negocio" name="gestor_negocio">
								    <option value="default" selected>Seleccionar</option>
									<c:forEach items="${gestores_negocio}" var="user">	
										<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
									</c:forEach>					
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Tipo petici&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="tipo" id="tipo" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<option value="Implantacion">Implantacion</option>
									<option value="Migracion IA">Migracion IA</option>
									<option value="Estandarizaci&oacute;n Clientes">Estandarizaci&oacute;n Clientes</option>
									<option value="Evolutivo">Evolutivo</option>
									<option value="Pruebas Cliente">Pruebas Cliente</option>
									<option value="SEPA">SEPA</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Devuelta<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="devuelta" required aria-required="true">
									<option value="NO">NO</option>
									<option value="SI">SI</option>
									
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="estado" id="estado" required aria-required="true">
									<option value="Pendiente de asignaci&oacute;n">Pendiente de asignaci&oacute;n</option>
									<option value="Asignada">Asignada</option>
									<option value="Devuelta">Devuelta</option>
									<option value="Parada">Parada</option>
									<option value="Desestimada">Desestimada</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Catalogaci&oacute;n de petici&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="catalogacion_peticion" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<option value="Estandar">Est&aacute;ndar</option>
									<option value="Compleja">Compleja</option>
									
								</select>
							</div>
						</div>
					</div><div class="form-field-divider right">
						<div class="form-field">
							<span class="align-top lbl">Motivo de catalogaci&oacute;n:</span>
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
							<span class="lbl">Fecha solicitud asignaci&oacute;n:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_solicitud_asignacion" id="fecha_solicitud_asignacion">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Hora solicitud asignaci&oacute;n:</span>
							<div class="input">
								<select class="selectpicker time" id="hora_solicitud_asignacion" name="hora_solicitud_asignacion">
										<option value="default"> </option>
									<c:forEach items="${horasList}" var="hora">
										<option value="${hora}">${hora}</option>
									</c:forEach>
								</select>
								<span class="time-span">:</span>
								<select class="selectpicker time" id="min_solicitud_asignacion" name="min_solicitud_asignacion">
								<option value="default"> </option>
									
									<c:forEach items="${minutosList}" var="min">
										<option value="${min}">${min}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Gestor IT pre-asignado:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_it" name="gestor_it">	
								<option value="default" selected>Seleccionar...</option>
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						
						<div class="form-field">
							<span class="lbl">Fecha comunicaci&oacute;n asignaci&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_comunicacion_asignacion" id="fecha_comunicacion_asignacion" required>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Hora comunicaci&oacute;n asignaci&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker time" id="hora_comunicacion_asignacion" name="hora_comunicacion_asignacion" required aria-required="true">
										
									<c:forEach items="${horasList}" var="hora">
										<option value="${hora}">${hora}</option>
									</c:forEach>
								</select>
								<span class="time-span">:</span>
								<select class="selectpicker time" id="min_comunicacion_asignacion" name="min_comunicacion_asignacion" required aria-required="true">
								
									
									<c:forEach items="${minutosList}" var="min">
										<option value="${min}">${min}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
					</div>
				</div>
			</form>
			<div class="message-container">
				<div id="message_div" class="message_div">
					<span id="span_message" span_message></span>
				</div>
			</div>
			<div id="buttons_new_demanda">
				<button type="submit" class="submit_form" id="submit_demanda_form">Aceptar</button>
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
							<th><span class="table-title">Tipo Petici&oacute;n</span></th>
							<th><span class="table-title">Estado</span></th>
							<th><span class="table-title">Cod. Petici&oacute;n</span></th>
							<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
								<th style="width: 110px;">&nbsp;</th>
							</c:if>
						</tr>
						<tr>
							<th class="search-th"><input class="search col0 search_anywhere"></th>
							<th class="search-th"><input class="search col1"></th>
							<th class="search-th"><input class="search col2"></th>
							<th class="search-th"><input class="search col3"></th>
							<th class="search-th"><input class="search col4"></th>
							<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
								<th style="width: 110px;">&nbsp;</th>
							</c:if>
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
									<tr class="valid-result" id="row${demanda.key.id}" data-gestor-it="${demanda.gestor_it}"  data-clienteid="${demanda.clientekey}" data-hora-solicitud="${demanda.hora_solicitud_asignacion}" data-fecha-solicitud="${demanda.str_fecha_solicitud_asignacion}" data-comentarios="${demanda.comentarios}" data-motivo="${demanda.motivo_catalogacion}" data-hora-peticion="${demanda.hora_entrada_peticion}" data-fecha-comun="${demanda.str_fecha_comunicacion_asignacion}" data-hora-comun="${demanda.hora_comunicacion_asignacion}" data-gestor-negocio="${demanda.gestor_negocio}" data-devuelta="${demanda.devuelta}" data-catalogacion="${demanda.catalogacion}">
										<td><span>${demanda.str_fecha_entrada_peticion}</span></td>
										<td><span>${demanda.clienteName}</span></td>
										<td><span>${demanda.tipo}</span></td>
										<td><span>${demanda.estado}</span></td>
										<td><span>${demanda.cod_peticion}</span></td>
										<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
											<td><img class="vs" src="../img/vs.png"><a class="lapiz" data-target="#edit-demanda" href="../../demandaModal.do?git=${demanda.gestor_it}&gn=${demanda.gestor_negocio}&client=${demanda.clientekey}" data-toggle="modal" name="${demanda.key.id}"	id="lapiz${demanda.key.id}"></a><a class="papelera" name="${demanda.key.id}" data-toggle="modal" data-target="#confirm-delete" id="papelera${demanda.key.id}"></a></td>
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

<!--  Editar Demadna -->
	<div class="modal fade" id="edit-demanda" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				
			</div>
		</div>
	</div>


 <!--  Confirmar delete -->
	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="">
					<h2>Eliminar petici�n</h2>
					<hr />
				</div>
				<div class="">
					<p>&iquest;Est&aacute; seguro que desea eliminar la petici&oacute;n?
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteDemanda">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Nuevo Cliente -->
	
	<div class="modal fade" id="new-client" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" id="new_client_dialog">
			<div class="modal-content">
				<div class="">
					<h2>Nuevo Cliente</h2>
					<hr />
				</div>
				<div class="new-user-form-holder">
			<form id="new-client-form-modal" name="new-client-form" action="/clienteServlet"
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
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<input type="text" aria-required="true" required="" id="client_name" name="client_name" class="long">
						</div>
						<div class="form-field">
							<span class="lbl">Tipo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" id="tipo" name="tipo" required aria-required="true">
								    <option value="default">Seleccionar</option>
								    <option value="CIB">CIB</option>
								    <option value="BEC">BEC</option>
													
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Criticidad<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="criticidad" id="criticidad" required aria-required="true">
									<option value="Baja">Baja</option>
									<option value="Media">Media</option>
									<option value="Alta">Alta</option>			
									
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Referencia Global<span class="required-asterisk">*</span>:</span>
							<input type="text" aria-required="true" required="" id="ref_global" name="ref_global" class="long" maxlength="11">
						</div>
						
					</div><div class="form-field-divider right">
						
						<div class="form-field">
							<span class="lbl">Logo-url:</span>
							<input type="text" id="logo_url" name="logo_url" class="long">
						</div>
						
						<div class="form-field">	
							<span class="lbl">Paises<span class="required-asterisk">*</span>:</span>					
							<div class="radio-div">
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Argentina"
										id="argentina_check" class="require-one"><label for="argentina_check"><span></span>Argentina</label>
								</div>
								
								<div class="radio-container">
									<input type="checkbox" name='paises' value="B&eacute;lgica"
										id="belgica_check"><label for="belgica_check"><span></span>B&eacute;lgica</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Chile"
										id="chile_check"><label for="chile_check"><span></span>Chile</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Colombia"
										id="colombia_check"><label for="colombia_check"><span></span>Colombia</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Espa&ntilde;a"
										id="esp_check"><label for="esp_check"><span></span>Espa&ntilde;a</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Francia"
										id="francia_check"><label for="francia_check"><span></span>Francia</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Italia"
										id="italia_check"><label for="italia_check"><span></span>Italia</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Mexico"
										id="mexico_check"><label for="mexico_check"><span></span>M&eacute;xico</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Peru"
										id="peru_check"><label for="peru_check"><span></span>Per&uacute;</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Portugal"
										id="portugal_check"><label for="portugal_check"><span></span>Portugal</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Reino Unido"
										id="uk_check"><label for="uk_check"><span></span>Reino Unido</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Uruguay"
										id="uruguay_check"><label for="uruguay_check"><span></span>Uruguay</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="USA"
										id="usa_check"><label for="usa_check"><span></span>USA</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Venezuela"
										id="venezuela_check"><label for="venezuela_check"><span></span>Venezuela</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Redex"
										id="redex_check"><label for="redex_check"><span></span>Redex</label>
								</div>
								
							</div>
						</div>							
					</div>					
				</div>
			</form>
			<div id="message_div_cliente" class="message_div" style='margin-bottom:10px;'>
						<span id="span_message_cliente" class="span_message"></span>
			</div>
			
		</div>
				<div class="modal-footer">
					<button type="button" class="" id="submit_client_form_modal">Guardar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>