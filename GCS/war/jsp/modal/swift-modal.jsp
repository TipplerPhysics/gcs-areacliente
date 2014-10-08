  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="modal_ajax">
		<div class="">
					<h2>Editar Conectividad</h2>
					<hr />
				</div>
				<div class="edit-user-form-holder">
				<form id="edit-conectividad-form" name="new-conectividad-form" action="/conectividadServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl">Proyecto:</span>
							<div class="input">
								<input type="text" class="readonly" readonly value="${project_name}"  name="project" id="proyecto_name_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio infraestructura:</span>
							<div class="input">
								<input type="text" id="fecha_ini_infra" name="fecha_ini_infra" data-target-id="fecha_ini_infra" class="datepicker hasDatepicker" size="16" value="${conectividad.str_fecha_ini_infraestructura}" readonly="">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin infraestructura:</span>
							<div class="input">
								<input type="text" id="fecha_fin_infra" name="fecha_fin_infra" data-target-id="fecha_fin_infra" class="datepicker hasDatepicker" size="16" value="${conectividad.str_fecha_fin_infraestructura}" readonly="">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha implantación</span>
							<div class="input">
								<input type="text" id="fecha_implantacion" name="fecha_implantacion" data-target-id="fecha_implantacion" class="datepicker hasDatepicker" size="16" value="" readonly="${conectividad.str_fecha_implantacion}">
							</div>
						</div>

					</div>
					<div class="form-field-divider right">
						
						
						<div class="form-field">
							<span class="lbl">Seguridad:</span>
							<div class="input">
								<select name="seguridad" id="seguridad" class="long selectpicker">
									 	<c:if test="${sessionScope.permiso == 1 or sessionScope.permiso == 2}"></c:if>
																	
									<option value="default">Seleccionar</option>
									<option value="PGP" <c:if test="${conectividad.seguridad eq 'PGP'}">selected</c:if>>PGP</option>
									<option value="3KEY" <c:if test="${conectividad.seguridad eq '3KEY'}">selected</c:if> >3KEY</option>
									<option value="Host to Web" <c:if test="${conectividad.seguridad eq 'Host to Web'}">selected</c:if> >Host to Web</option>
									<option value="PGP-H2W" <c:if test="${conectividad.seguridad eq 'PGP-H2W'}">selected</c:if> >PGP-H2W</option>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio seguridad:</span>
							<div class="input">
								<input type="text" id="ini_seguridad" name="ini_seguridad" data-target-id="ini_seguridad" class="datepicker hasDatepicker" size="16" value="${conectividad.fecha_ini_seguridad}" readonly="">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin seguridad:</span>
							<div class="input">
								<input type="text" id="fin_seguridad" name="fin_seguridad" data-target-id="fin_seguridad" class="datepicker hasDatepicker" size="16" value="${conectividad.fecha_fin_seguridad}" readonly="">
							</div>
						</div>
						
					
						
							
					</div>	
							 
					
				</div>
				
			</form>
			<div id="message_div_modal" class="message_div" style='margin-bottom:10px;'>
						<span id="span_message_modal" class="span_message"></span>
			</div>
			
		</div>
				<div class="modal-footer">
					<button type="button" class="" id="edit_conectividad_form_modal" onclick="sendEditConectividad();">Guardar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
		</div>
