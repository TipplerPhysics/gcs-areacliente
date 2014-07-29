<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_demanda">

	<img class="users_title_icon" src="../img/user.png"><h1>Alta solicitud gestión de demanda</h1>
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
					<div class="form-field">
						<div class="fecha">
							<span class="lbl"><span class="required-asterisk">*</span>Fecha entrada petición:</span>
							<div data-date-format="dd-mm-yyyy" data-date="12-02-2012" id="dp1" class="input-append date">
								<input type="text" readonly="" value="" size="16" class="datepicker datefuture" name="fecha_entrada_peticion" id="fecha_entrada_peticion" required aria-required="true">
						  	</div>
						  </div>
					  <div class="tiempo">
						<span class="lbl"><span class="required-asterisk">*</span>Hora petición:</span>
						
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
					
					<div class="form-field textarea">
						<span class="align-top lbl">Motivo de catalogación:</span><textarea name="motivo_catalogacion" id="motivo_catalogacion" class="long"></textarea>
					</div>
					<div class="form-field double">
						<div>
							<span class="lbl"><span class="required-asterisk">*</span>Cliente:</span>
							<select class="selectpicker" id="cliente" name="cliente">
							<!--select class="selectpicker selected" id="cliente" name="cliente" required aria-required="true"-->
								<option value="default">Seleccionar...</option>
							</select>					
						</div>
						
						<div>
							<span class="lbl">Gestor de negocio:</span>
							<select class="selectpicker" id="gestor_negocio" name="gestor_negocio">
							    <option value="default" selected>Seleccionar</option>
								<c:forEach items="${gestores_demanda}" var="user">	
									<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
								</c:forEach>					
						
							</select>
						</div>	
					</div>
					
					<div class="form-field textarea">
						<span class="align-top lbl">Comentarios:</span><textarea id="comentarios" name="comentarios" class="long"></textarea>
					</div>
					
					<div class="form-field double">
						<div>
						<span class="lbl"><span class="required-asterisk">*</span>Tipo:</span>
							<select class="selectpicker selected" name="tipo" id="tipo" required aria-required="true">
								<option value="default">Seleccionar...</option>
								<option value="CIB">CIB</option>
								<option value="BEC">BEC</option>
							</select>
						</div>
						<div>						
							<span class="lbl"><span class="required-asterisk">*</span>Devuelta:</span>
							<select class="selectpicker selected" name="devuelta" required aria-required="true">
								<option value="default">Seleccionar...</option>
								<option value="SI">SI</option>
								<option value="NO">NO</option>
							</select>					
						</div>	
					</div>
					
					<div class="form-field">
						<div class="fecha">
							<span class="lbl">Fecha solicitud asignación:</span>
							<div data-date-format="dd-mm-yyyy" data-date="12-02-2012" id="dp1" class="input-append date">
								<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_solicitud_asignacion" id="fecha_solicitud_asignacion">
						  	</div>
						  </div>
					  	<div class="tiempo">
							<span class="lbl">Hora solicitud asignación:</span>
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
						<span class="lbl"><span class="required-asterisk">*</span>Estado:</span>
						<select class="selectpicker selected" name="estado" id="estado" required aria-required="true">
							<option value="default">Seleccionar...</option>
							<option value="PDTE Doc Alcance en GCS">PDTE Doc Alcance en GCS</option>
							<option value="P-950 en confección">P-950 en confección</option>
							<option value="PDTE Valoración IT">PDTE Valoración IT</option>
							<option value="PDTE Plan de Trabajo IT">PDTE Plan de Trabajo IT</option>
							<option value="PDTE Visto Bueno del CL del plan de trabajo">PDTE Visto Bueno del CL del plan de trabajo</option>
							<option value="En Desarrollo">En Desarrollo</option>
							<option value="En Test - Conectividad">En Test - Conectividad</option>
							<option value="En Test - Integración">En Test - Integración</option>
							<option value="En Test - Aceptación">En Test - Aceptación</option>
							<option value="Parado por Negocio - Producto">Parado por Negocio - Producto</option>
							<option value="Parado por Negocio">Parado por Negocio</option>
							<option value="Parado por IT">Parado por IT</option>
							<option value="Excluido por Negocio">Excluido por Negocio</option>
							<option value="Excluido por Timeout">Excluido por Timeout</option>
							<option value="PDTE Implantar">PDTE Implantar</option>						
							<option value="En Penny Test">En Penny Test</option>
							<option value="Implementado con OK">Implementado con OK</option>						
							<option value="Implementado sin OK">Implementado sin OK</option>						
						</select>
					</div>
					
					<div class="form-field">
						<span class="lbl"><span class="required-asterisk">*</span>Gestor IT pre-asignado:</span>
						<select class="selectpicker selected" name="gestor_it" required aria-required="true">					
						    <option value="default" selected>Seleccionar...</option>
							<c:forEach items="${gestores_it}" var="user">	
								<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
							</c:forEach>					
					
						</select>
					</div>
					
					<div class="form-field">
						<span class="lbl"><span class="required-asterisk">*</span>Catalogación de petición:</span>
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
					<div class="form-field"></div>
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
									<tr class="valid-result" id="row${demanda.key.id}" data-fecha-entrada="${demanda.str_fecha_entrada_peticion}" data-hora-entrada="${demanda.hora_entrada_peticion}" data-gestor-asig="${demanda.gestor_it}" data-gestores-list="${gestoresStr}">
										<td><span>${demanda.str_fecha_entrada_peticion}</span></td>
										<td><span>${demanda.clientekey}</span></td>
										<td><span>${demanda.tipo}</span></td>
										<td><span>${demanda.estado}</span></td>
										<td><span>${demanda.cod_peticion}</span></td>
										<td><img class="vs" src="../img/vs.png"><a class="papelera" name="${demanda.key.id}" data-toggle="modal"	data-target="#confirm-delete" id="papelera${demanda.key.id}"></a><a class="lapiz" name="${demanda.key.id}"	id="lapiz${demanda.key.id}"></a></td>
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