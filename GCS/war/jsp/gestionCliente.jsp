<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
								<input type="text" readonly="" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_alta_cliente' name="fecha_alta_cliente" id="fecha_alta_cliente" required aria-required="true">
							</div>
						</div>						
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<input type="text" aria-required="true" required="" id="client_name" name="client_name" maxlength="25" class="long">
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
									<option value="BAJA">BAJA</option>
									<option value="MEDIA">MEDIA</option>
									<option value="ALTA">ALTA</option>			
									
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Referencia Global B2B:</span>
							<input type="text" minlength="11" id="ref_global" name="ref_global" class="long" maxlength="30">
						</div>
						
						
						
					</div><div class="form-field-divider right">
						
						<div class="form-field">
							<span class="lbl">Logo-url:</span>
							<input type="text" id="logo_url" name="logo_url" class="long">
						</div>
						
						<div class="form-field paises">	
							<span class="lbl">Paises<span class="required-asterisk">*</span>:</span>					
							<div class="radio-div">	
								<c:forEach items="${paises}" var="pais">	
									<div class="radio-container">
										<input type="checkbox" name='paises' value="${pais.name}"
										id="${pais.name}_check"><label for="${pais.name}_check"><span></span>${pais.name}</label>
									</div>
								</c:forEach>
								
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
							<th><span class="table-title">Referencia Global B2B</span></th>
							<th><span class="table-title">Tipo</span></th>
							<th><span class="table-title">Criticidad</span></th>
							<th style="width: 110px;">&nbsp;</th>

						</tr>
						<tr>
							<form id='test-header-filter' action="">
								<th style='width:18%;' class="search-th"><input style='width:28%;' name='fechaDia' value='${fechaDia}'><input style='width:28%;' name='fechaMes' value='${fechaMes}'><input style='width:44%;' name='fechaAnio' value='${fechaAnio}'></th>
								<th class="search-th"><input name='idCliente' value='${idCliente}'></th>
								<th class="search-th"><input name='cliente' value='${cliente}'></th>
								<th class="search-th"><input name='referencia' value='${referencia}'></th>
								<th class="search-th"><input name='tipo' value='${tipo}'></th>
								<th class="search-th"><input name='criticidad' value='${criticidad}'></th>
								<th><button type='button' onclick='filteringCliente();'>  FILTRAR  </button></th>
							
							</form>
						</tr>
					</thead>
					<tbody id="myTable" cellspacing="0" data-page="${page}" data-lastpage="${lastpage}" data-numpages="${numpages}">
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
											<a class="lapiz" name="${cliente.key.id}" href="../clienteModal.do?id=${cliente.key.id}"	id="lapiz${cliente.key.id}" data-toggle="modal" data-target="#edit-client"></a>
									
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
				<div class="paginationGoto" />
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
			<div class="modal-content noErase">
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