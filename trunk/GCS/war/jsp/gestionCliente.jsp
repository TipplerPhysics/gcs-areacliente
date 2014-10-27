<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_cliente">

	<h1>Gesti&oacute;n cliente</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	
	
	<hr/>
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Gesti&oacute;n de clientes </span>
	</div>
	
	<div class="newUserbox">
		<c:if test="${sessionScope.permiso != 3 and sessionScope.permiso != 5 and sessionScope.permiso != 4}">
			<button id="newUserButton">
				Nuevo Cliente<span class="user_span"></span>
			</button>
		</c:if>
		
		<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
			<button id="newProjectButton" onclick="location.href = './gestionProyecto.do';">
				Gesti&oacute;n Proyecto<span class="proyecto_span_fixed"></span>
			</button>	
		</c:if>	
		
		<button id="excel_btn" onclick="window.location.href='../../clienteServlet?accion=xls'">
			Descargar Tabla<span class="excel_span"></span>
		</button>


		<div class="new-user-form-holder">
			<form id="new-client-form" name="new-client-form" action="/clienteServlet"
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
							<span class="lbl">Referencia Global:</span>
							<input type="text" minlength="11" id="ref_global" name="ref_global" class="long" maxlength="11">
						</div>
						
						
						
					</div><div class="form-field-divider right">
						
						<div class="form-field">
							<span class="lbl">Logo-url:</span>
							<input type="text" id="logo_url" name="logo_url" class="long">
						</div>
						
						
						
					
						<div class="form-field paises">	
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
									<input type="checkbox" name='paises' value="China"
										id="china_check"><label for="china_check"><span></span>China</label>
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
									<input type="checkbox" name='paises' value="M&eacute;xico"
										id="mexico_check"><label for="mexico_check"><span></span>M&eacute;xico</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Per&uacute;"
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
					<div id="message_div_cliente" class="message_div">
						<span id="span_message_cliente" class="span_message"></span>
					</div>
				</div>
			</form>
			<div id="buttons_new_client">
				<button type="submit" class="submit_form" id="submit_client_form">Aceptar</button>
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
							<th><span class="table-title">ID Cliente</span></th>
							<th><span class="table-title">Cliente</span></th>
							<th><span class="table-title">Referencia Global</span></th>
							<th><span class="table-title">Tipo</span></th>
							<th><span class="table-title">Criticidad</span></th>
							<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
								<th style="width: 110px;">&nbsp;</th>
							</c:if>
						</tr>
						<tr>
							<th class="search-th"><input class="search col0"></th>
							<th class="search-th"><input class="search col1"></th>
							<th class="search-th"><input class="search col2"></th>
							<th class="search-th"><input class="search col3"></th>
							<th class="search-th"><input class="search col4"></th>
							<th class="search-th"><input class="search col5"></th>
							<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
								<th style="width: 110px;">&nbsp;</th>
							</c:if>
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
									<tr class="valid-result" id="row${cliente.key.id}" name="${cliente.key.id}" data-fecha-alta="${cliente.str_fecha_alta_cliente}" data-nombre="${cliente.nombre}" data-tipo="${cliente.tipo}" data-criticidad="${cliente.criticidad}" data-ref-global="${cliente.ref_global}" data-logo-url="${cliente.logo_url}" data-paises="${cliente.paises}">
										<td><span>${cliente.str_fecha_alta_cliente}</span></td>
										<td><span>${cliente.clientId}</span></td>
										<td><span>${cliente.nombre}</span></td>
										<td><span>${cliente.ref_global}</span></td>
										<td><span>${cliente.tipo}</span></td>
										<td><span>${cliente.criticidad}</span></td>
										<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
										<td>										
											<img class="vs" src="../img/vs.png">								
											<a class="lapiz" name="${cliente.key.id}" href="../clienteModal.do"	id="lapiz${cliente.key.id}" data-toggle="modal" data-target="#edit-client"></a>
									
											<c:if test="${sessionScope.permiso != 3}">
												<a class="papelera" name="${cliente.key.id}" data-toggle="modal" data-target="#confirm-delete" id="papelera${cliente.key.id}"></a>
											</c:if>
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
					<p>&iquest;Est&aacute; seguro que desea eliminar el cliente?<p>
					<p>Se eliminar&aacute;n todos los proyectos asociados.<p>
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteClient">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>