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
								<select class="selectpicker selected" name="estado" id="estado">
									<option value="PDTE Doc Alcance en GCS" ${conectividad.estado == 'PDTE Doc Alcance en GCS' ? 'selected' : ''}>PDTE Doc Alcance en GCS</option>
									<option value="C100 en confección" ${conectividad.estado == 'C100 en confección' ? 'selected' : ''}>C100 en confección</option>
									<option value="PDTE Valoración IT" ${conectividad.estado == 'PDTE Valoración IT' ? 'selected' : ''}>PDTE Valoración IT</option>	
									<option value="PDTE Plan de Trabajo IT" ${conectividad.estado == 'PDTE Plan de Trabajo IT' ? 'selected' : ''}>PDTE Plan de Trabajo IT</option>
									<option value="PDTE Visto Bueno del CL del plan de trabajo" ${conectividad.estado == 'PDTE Visto Bueno del CL del plan de trabajo' ? 'selected' : ''}>PDTE Visto Bueno del CL del plan de trabajo</option>
									<option value="En Desarrollo" ${conectividad.estado == 'En Desarrollo' ? 'selected' : ''}>En Desarrollo</option>
									<option value="En Test - Conectividad" ${conectividad.estado == 'En Test - Conectividad' ? 'selected' : ''}>En Test - Conectividad</option>
									<option value="En Test - Integración" ${conectividad.estado == 'En Test - Integración' ? 'selected' : ''}>En Test - Integración</option>	
									<option value="En Test - Aceptación" ${conectividad.estado == 'En Test - Aceptación' ? 'selected' : ''}>En Test - Aceptación</option>	
									<option value="Parado por producto" ${conectividad.estado == 'Parado por producto' ? 'selected' : ''}>Parado por producto</option>	
									<option value="Parado por negocio" ${conectividad.estado == 'Parado por negocio' ? 'selected' : ''}>Parado por negocio</option>	
									<option value="Parado por IT" ${conectividad.estado == 'Parado por IT' ? 'selected' : ''}>Parado por IT</option>
									<option value="Excluido por negocio" ${conectividad.estado == 'Excluido por negocio' ? 'selected' : ''}>Excluido por negocio</option>	
									<option value="Excluido por Timeout" ${conectividad.estado == 'Excluido por Timeout' ? 'selected' : ''}>Excluido por Timeout</option>	
									<option value="PDTE Implantar" ${conectividad.estado == 'PDTE Implantar' ? 'selected' : ''}>PDTE Implantar</option>	
									<option value="En Penny Test" ${conectividad.estado == 'En Penny Test' ? 'selected' : ''}>En Penny Test</option>
									<option value="Implementado con OK" ${conectividad.estado == 'Implementado con OK' ? 'selected' : ''}>Implementado con OK</option>
									<option value="Implementado sin OK" ${conectividad.estado == 'Implementado sin OK' ? 'selected' : ''}>Implementado sin OK</option>										
								</select>							
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
	
