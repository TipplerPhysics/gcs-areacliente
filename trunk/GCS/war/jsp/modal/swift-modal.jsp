  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="modal_ajax">
		<div class="">
					<h2>Editar Conectividad</h2>
					<hr />
				</div>
				<div class="new-user-form-holder">
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
								<input type="text" id="fecha_ini_infra_modal" name="fecha_ini_infra" data-target-id="fecha_fin_infra_modal" class="datepicker fromTo" size="16" maxlength="25" value="${conectividad.str_fecha_ini_infraestructura}" readonly="">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin infraestructura:</span>
							<div class="input">
								<input type="text" id="fecha_fin_infra_modal" name="fecha_fin_infra" class="datepicker" size="16" maxlength="25" value="${conectividad.str_fecha_fin_infraestructura}" readonly="">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha implantación</span>
							<div class="input">
								<input type="text" id="fecha_implantacion_modal" name="fecha_implantacion" data-target-id="fecha_implantacion_modal" class="datepicker" size="16" maxlength="25" value="${conectividad.str_fecha_implantacion}" readonly="">
							</div>
						</div>

					</div>
					<div class="form-field-divider right">
						
						<div class="form-field">
							<span class="lbl">Seguridad:</span>
							<div class="input">
								<select name="seguridad" id="seguridad" class="long selectpicker">
									<option selected value="default">Seleccionar</option>
									<c:forEach items="${seguridad}" var="segur">							
										<option value="${segur.name}" ${conectividad.seguridad == segur.name ? 'selected' : ''}>${segur.name}</option>
									</c:forEach>																
								</select>
							</div>
						</div>
						
				
						<div class="form-field">
							<span class="lbl">Fecha inicio seguridad:</span>
							<div class="input">
								<input type="text" id="ini_seguridad_modal" name="ini_seguridad" data-target-id="fin_seguridad_modal" class="datepicker fromTo" size="16" maxlength="25" value="${conectividad.str_fecha_ini_seguridad}" readonly="">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin seguridad:</span>
							<div class="input">
								<input type="text" id="fin_seguridad_modal" name="fin_seguridad" class="datepicker" size="16" maxlength="25" value="${conectividad.str_fecha_fin_seguridad}" readonly="">
							</div>
						</div>	
						
						<div class="form-field">
							<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="estado" id="estado" data-live-search="true">
									<option selected value="default">Seleccionar</option>
										<c:forEach items="${estados}" var="est">							
											<option value="${est.name}" ${conectividad.estado == est.name ? 'selected' : ''}>${est.name}</option>
										</c:forEach>																
								</select>
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
	
