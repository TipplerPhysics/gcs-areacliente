<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_cliente">

	<img class="users_title_icon" src="../img/user.png"><h1>Alta cliente</h1>
	<hr/>
	
	<div class="newUserbox">
		<button id="newUserButton">
			Alta Cliente<a class="user_span"></a>
		</button>
		
		<button id="newProjectButton">
			Alta Proyecto<a class="demanda_span"></a>
		</button>		
		
		<button id="excel_btn" onclick="window.location.href='../../?accion=xls'">
			Descargar Tabla<a class="excel_span"></a>
		</button>


		<div class="new-user-form-holder">
			<form id="new-client-form" name="new-client-form" action="/demandaServlet"
				method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Fecha alta cliente:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker datefuture fromTo" data-target-id='fecha_alta_cliente' name="fecha_alta_cliente" id="fecha_alta_cliente" required aria-required="true">
							</div>
						</div>						
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Cliente:</span>
							<div class="input">
								<select id="input_cliente" class="selectpicker selected" name="cliente" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<c:forEach items="${clientes}" var="cliente">	
										<option value="${cliente.key.id}">${cliente.nombre} ${cliente.apellido1} ${cliente.apellido2}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Tipo:</span>
							<div class="input">
								<select class="selectpicker" id="tipo" name="tipo">
								    <option value="default" selected>Seleccionar</option>
									<c:forEach items="${gestores_demanda}" var="user">	
										<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
									</c:forEach>					
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Criticidad:</span>
							<div class="input">
								<select class="selectpicker selected" name="criticidad" id="criticidad" required aria-required="true">
									<option value="default">Seleccionar...</option>
									<option value="Alta">Alta</option>
									<option value="Media">Media</option>
									<option value="Baja">Baja</option>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Referencia Global:</span>
							<input type="text" aria-required="true" required="" id="ref_global" name="ref_global" class="long">
						</div>
						
						<div class="form-field">
							<span class="lbl">Referencia Local:</span>
							<input type="text" aria-required="true" required="" id="ref_local" name="ref_local" class="long">
						</div>
					</div><div class="form-field-divider right">
						
						<div class="form-field">
							<span class="lbl">Logo-url:</span>
							<input type="text" aria-required="true" required="" id="logo_url" name="logo_url" class="long">
						</div>
						
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Workflow:</span>
							<div class="radio-field">
								<input type="radio" name="workflow" value="si"><span>Si</span>
								<input type="radio" name="workflow" value="no"><span>No</span>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Paises:</span>
							
						</div>
						
						<div class="form-field">						
							<div class="radio-div">
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Lorem 1"
										id="Lorem1"><label for="Lorem1"><span></span>Lorem 1</label>
								</div>
								
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Lorem 2"
										id="Lorem2"><label for="Lorem2"><span></span>Lorem 2</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Lorem 3"
										id="Lorem3"><label for="Lorem3"><span></span>Lorem 3</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Lorem 4"
										id="Lorem4"><label for="Lorem4"><span></span>Lorem 4</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Lorem 5"
										id="Lorem5"><label for="Lorem5"><span></span>Lorem 5</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Lorem 6"
										id="Lorem6"><label for="Lorem6"><span></span>Lorem 6</label>
								</div>
							</div>
						</div>
						
					</div>
					<div id="message_div">
						<span id="span_message"></span>
					</div>
				</div>
			</form>
			<button type="submit" class="submit_form" id="submit_client_form">Aceptar</button>
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
							<th><span class="table-title">ID Cliente</span></th>
							<th><span class="table-title">Cliente</span></th>
							<th><span class="table-title">Referencia Global</span></th>
							<th><span class="table-title">Tipo</span></th>
							<th><span class="table-title">Criticidad</span></th>
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
							<c:when test="${empty demandaList}">
								<tr>
									<td><span>No existen clientes.</span></td>
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
					<h2>Eliminar petici�n</h2>
					<hr />
				</div>
				<div class="">
					<p>&iquest;Est&aacute; seguro que desea eliminar la petici�n?
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteDemanda">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>