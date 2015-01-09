<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_implantacion">

	<h1>Registro de implantaciones</h1>
	<!-- <button class="btn-atras" type="button">Atr&aacute;s</button>  -->
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	<hr/>
	
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Registro de implantaciones </span>
	</div>
	
	<div class="newUserbox">
		
		<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
			<button id="sendMailButton" <c:if test="${puedeGenerarInforme == 'true'}"> disabled="true"</c:if> <c:if test="${implantacionEnCurso == 'false'}"> disabled="true"</c:if>>
				Enviar correo Negocio<span class="demanda_span"></span>
			</button>
			<button id="generarInformeButton" <c:if test="${puedeGenerarInforme == 'false'}"> disabled="true"</c:if>>
				Generar informe<span class="demanda_span"></span>
			</button>
		</c:if>
		<button id="excel_btn" onclick="window.location.href='../../implantacionServlet?accion=xls'">
			Descargar Tabla<span class="excel_span"></span>
		</button>
		
	</div>
	<div>	
		<div>
			<div class="table-responsive usersTable">
				<table class="table">
					<thead>
						<tr>
							<th><span class="table-title">&nbsp;</span></th>
							<th><span class="table-title">Fecha de Subida</span></th>
							<th><span class="table-title">Cliente</span></th>
							<th><span class="table-title">Estado</span></th>
							<th><span class="table-title">GestorIT</span></th>
							<th><span class="table-title">Servicio</span></th>
							<th><span class="table-title">Conectividad</span></th>
							<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
								<th style="width: 110px;">&nbsp;</th>
							</c:if>
						</tr>
						<tr>
							<th>
								<label for="check_all_implantaciones">&nbsp;</label>
								<input type="checkbox" name="check_all_implantaciones" id="check_all_implantaciones" <c:if test="${implantacionEnCurso == 'true'}"> checked="true" disabled="true"</c:if> />
							</th>
							<th class="search-th"><input class="search col0 search_anywhere"></th>
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
							<c:when test="${empty implantacionList}">
								<tr>
									<td><span>No existen registros.</span></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach items="${implantacionList}" var="implantacion" varStatus="loop">
									<tr class="valid-result" id="row${loop.index}" data-servicio-id="${implantacion.serviciokey}" data-conectividad-id="${implantacion.conectividadkey}" >
										<td>
											<label for="check${loop.index}">&nbsp;</label>
											<input type="checkbox" class="inner" name="check${loop.index}" value="" id="check${loop.index}" <c:if test="${implantacionEnCurso == true}"> checked="true" disabled="true"</c:if> />						
										</td>
										<td><span>${implantacion.fecha_implantacion_str}</span></td>
										<td><span>${implantacion.clienteName}</span></td>
										<td><span>${implantacion.estado}</span></td>
										<td><span>${implantacion.gestor_it_name}</span></td>
										<td><span>${implantacion.servicio}</span></td>
										<td><span>${implantacion.conectividad}</span></td>
										<c:if test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
											<td>
											<c:forEach items="${conectividades}" var="conectividad">
											<c:if test="${conectividad.key.id==implantacion.conectividadkey}">
											<c:if test="${conectividad.estadoImp eq 'Solicitado'}">
											hola
											</c:if>
											</c:if>
											</c:forEach>
											
											<c:forEach items="${servicios}" var="servicio">
											<c:if test="${servicio.key.id==implantacion.serviciokey}">
											<c:if test="${servicio.estadoImplantacion eq 'Solicitado'}">
											<a class="lapiz"  data-toggle="modal" data-target="#edit-client"></a>
											</c:if>
											</c:if>
											</c:forEach>
											</td>
										</c:if>
									</tr>
									<tr>
										<td></td>
										<td style='height:auto;'>
											<div style='width:400px'>
											<c:forEach items="${servicios}" var="servicio">
											<c:if test="${servicio.key.id==implantacion.serviciokey}">
											<c:if test="${servicio.estadoImplantacion eq 'Solicitado'}">
											<form id='updateimp${servicio.key.id}' 
												<div class='form-field'>
													<span>Estado subida:</span>
													<select id="estadoSubid" class="selectpicker selected" name="estadoSubid" >							
														<option value="OK" ${servicio.estadoSubida == 'OK' ? 'selected' : ''}>OK</option>									
														<option value="KO" ${servicio.estadoSubida == 'KO' ? 'selected' : ''}>KO</option>	
													</select>
												</div>
												
												<div class='form-field' style='height:20%;'>
													<span> Detalles:</span>
													<textarea placeholder='Introduzca texto'>${servicio.detalleSubida}</textarea>
												</div>
												<button type='button' data-id='${servicio.key.id}' onclick=sendEditImplementacion()> Aceptar</button>
											</form>
											</c:if>
											</c:if>
											</c:forEach>
												
											</div>
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
			</div>
		</div>
	</div>

<!--  Editar Demadna hola-->
		
	<div class="modal fade" id="send-email-implantacion" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" id="send_email_dialog">
			<div class="modal-content">
				
			</div>
		</div>
	</div>
	<div class="modal fade" id="redirect-informe" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" id="send_email_dialog">
			<div class="modal-content">
				
			</div>
		</div>
	</div>
	
</div>