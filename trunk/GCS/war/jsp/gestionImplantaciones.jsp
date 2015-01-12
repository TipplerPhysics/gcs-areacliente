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
											<c:if test="${conectividad.estadoImplantacion eq 'Solicitado'}">
											<a class="lapiz dropbutton" data-id=${conectividad.key.id}></a>
											</c:if>
											</c:if>
											</c:forEach>
											
											<c:forEach items="${servicios}" var="servicio">
											<c:if test="${servicio.key.id==implantacion.serviciokey}">
											<c:if test="${servicio.estadoImplantacion eq 'Solicitado'}">
											<a class="lapiz dropbutton" data-id=${servicio.key.id} ></a>
											</c:if>
											</c:if>
											</c:forEach>
											</td>
										</c:if>
									</tr>
										<c:forEach items="${conectividades}" var="conectividad">
										<c:if test="${conectividad.key.id==implantacion.conectividadkey}">
										<c:if test="${conectividad.estadoImplantacion eq 'Solicitado'}">
											<tr id="line${conectividad.key.id}" class="modifHolder oculto">
												<td class="edit-impl-row" colspan="8">
													<form id='updateimp${conectividad.key.id}' >
														<div class="controls">
															<div class='form-field'>
																<span>Estado subida:</span>
																<select id="estadoSubid" class="selectpicker selected" name="estadoSubid" >							
																	<option value="OK" ${conectividad.estadoSubida == 'OK' ? 'selected' : ''}>OK</option>									
																	<option value="KO" ${conectividad.estadoSubida == 'KO' ? 'selected' : ''}>KO</option>	
																</select>
															</div>
															
															<div class='form-field'>
																<span class="top"> Detalles:</span>
																<textarea placeholder='Introduzca texto' name="detalleSubid">${conectividad.detalleSubida}</textarea>
															</div>
														</div>
														<div class="buttons">
																<button type='button' data-id='${conectividad.key.id}' data-tipo='conectividad' class="subidaModifImp"> Aceptar</button>
																<button type='button' class="dropbutton" data-id=${conectividad.key.id}>Cancelar</button>
														</div>
													</form>													
												</td>
											</tr>
										</c:if>
										</c:if>
										</c:forEach>
									<c:forEach items="${servicios}" var="servicio">
									<c:if test="${servicio.key.id==implantacion.serviciokey}">
									<c:if test="${servicio.estadoImplantacion eq 'Solicitado'}">
										
										
										<tr id="line${servicio.key.id}" class="modifHolder oculto">
											<td class="edit-impl-row" colspan="8">
												<form id='updateimp${servicio.key.id}' 
													<div class="controls">
														<div class='form-field'>
															<span>Estado subida:</span>
															<select id="estadoSubid" class="selectpicker selected" name="estadoSubid" >							
																<option value="OK" ${servicio.estadoSubida == 'OK' ? 'selected' : ''}>OK</option>									
																<option value="KO" ${servicio.estadoSubida == 'KO' ? 'selected' : ''}>KO</option>	
															</select>
														</div>
														
														<div class='form-field'>
															<span class="top"> Detalles:</span>
															<textarea placeholder='Introduzca texto' name="detalleSubid">${servicio.detalleSubida}</textarea>
														</div>
													</div>
													<div class="buttons">
														<button type='button' data-id='${servicio.key.id}' data-tipo='servicio' class="subidaModifImp"> Aceptar</button>
														<button type='button' class=" dropbutton" data-id=${servicio.key.id}>Cancelar</button>
													</div>
												</form>
											</td>
										</tr>
									</c:if>
									</c:if>
									</c:forEach>
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