<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_cliente">

	<img class="users_title_icon" src="../img/user.png"><h1>Alta cliente</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	
	
	<hr/>
	
	<div class="newUserbox">
		<button id="newUserButton">
			Alta Cliente<a class="user_span"></a>
		</button>
		
		<button id="newProjectButton">
			Alta Proyecto<a class="proyecto_span"></a>
		</button>		
		
		<button id="excel_btn" onclick="window.location.href='../../?accion=xls'">
			Descargar Tabla<a class="excel_span"></a>
		</button>


		<div class="new-user-form-holder">
			<form id="new-client-form" name="new-client-form" action="/clienteServlet"
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
							<input type="text" aria-required="true" required="" id="client_name" name="client_name" class="long">
						</div>
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Tipo:</span>
							<div class="input">
								<select class="selectpicker selected" id="tipo" name="tipo" required aria-required="true">
								    <option value="default">Seleccionar</option>
								    <option value="CIB">CIB</option>
								    <option value="BEC">BEC</option>
													
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
							<input type="text" aria-required="true" required="" id="ref_global" name="ref_global" class="long" maxlength="11">
						</div>
						
						<div class="form-field">
							<span class="lbl">Referencia Local:</span>
							<input type="text" id="ref_local" name="ref_local" class="long" maxlength="24">
						</div>
					</div><div class="form-field-divider right">
						
						<div class="form-field">
							<span class="lbl">Logo-url:</span>
							<input type="text" id="logo_url" name="logo_url" class="long">
						</div>
						
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Workflow:</span>
							<div class="radio-field">
								<label class="lbl"><input type="radio" name="workflow" value="SI"><span class="overlay"></span> Si</label>
								<label class="lbl"><input type="radio" name="workflow" value="NO" checked="checked"><span class="overlay"></span> No</label>								
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl"><span class="required-asterisk">*</span>Paises:</span>
							
						</div>
						
						<div class="form-field">						
							<div class="radio-div">
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Lorem 1"
										id="Lorem1" class="require-one"><label for="Lorem1"><span></span>Lorem 1</label>
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
							<c:when test="${empty clientes}">
								<tr>
									<td><span>No existen clientes.</span></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach items="${clientes}" var="cliente">
									<tr class="valid-result" id="row${cliente.key.id}" name="${cliente.key.id}" data-logo-url="${cliente.logo_url}" data-ref-local="${cliente.ref_local}" data-workflow="${cliente.workflow}">
										<td><span>${cliente.str_fecha_alta_cliente}</span></td>
										<td><span>${cliente.clientId}</span></td>
										<td><span>${cliente.nombre}</span></td>
										<td><span>${cliente.ref_global}</span></td>
										<td><span>${cliente.tipo}</span></td>
										<td><span>${cliente.criticidad}</span></td>
										<td><img class="vs" src="../img/vs.png"><a class="lapiz" name="${cliente.key.id}"	id="lapiz${cliente.key.id}"></a><a class="papelera" name="${cliente.key.id}" data-toggle="modal" data-target="#confirm-delete" id="papelera${cliente.key.id}"></a></td>
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
					<h2>Eliminar Cliente</h2>
					<hr />
				</div>
				<div class="">
					<p>&iquest;Est&aacute; seguro que desea eliminar el cliente?
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteCliente">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>