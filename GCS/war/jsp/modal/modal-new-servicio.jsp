<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="modal_ajax" id="nuevo_servicio_modal"> 
		<div class="">
					<h2>Nuevo Servicio</h2>
					<hr />
				</div>
				<div class="edit-user-form-holder">
				<form id="new-service-form" name="new-service-form" action="/serviceServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl">Cod. de proyecto<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="cod_proyecto" id="cod_proyecto" required aria-required="true" data-live-search="true">
									<option value="default">Seleccionar</option>	
									<c:forEach items="${proyectos}" var="p">	
										<option value="${p.key.id}">${p.cod_proyecto}</option>
									</c:forEach>								
								</select>
							</div>
						</div>
						
						
						<div class="form-field">
							<span class="lbl">País<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="pais" id="pais_servicio_new_inform" required aria-required="true" data-live-search="true">
									<option value="default">Seleccionar</option>
									<c:forEach items="${paises}" var="t">							
										<option value="${t.name}">${t.name}</option>
									</c:forEach>																											
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Servicio<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="servicio" id="servicio_new_inform" required aria-required="true" data-live-search="true">
									<option value="default">-</option>																	
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Extensi&oacuten<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="extension" id="extension_new_inform" required aria-required="true" data-live-search="true">
									<option value="default">-</option>																	
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="estado" id="estado" data-live-search="true">
									<option selected value="default">Seleccionar</option>
										<c:forEach items="${estados}" var="estado">
											<option value="${estado.name}">${estado.name}</option>
										</c:forEach>
								</select>
							</div>
						</div>
						
			
						<div class="form-field">
							<span class="lbl">Cod. Redmine<span class="required-asterisk">*</span>:</span>
							<input type="text" id="cod_servicio" name="cod_servicio" maxlength="25" class="long" required aria-required="true">
						</div>
						
						<div class="form-field">
							<span class="lbl">Observaciones:</span>
							<div class="input">
								<textarea name="observaciones"  maxlength="160" id="observaciones"></textarea>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Formato intermedio:</span>
							<input type="text" id="formato_intermedio" name="formato_intermedio" maxlength="25" class="long">
						</div>
						
						<div class="form-field">
							<span class="lbl">Formato local:</span>
							<input type="text" id="formato_local" name="formato_local" maxlength="25" class="long">
						</div>
						
						<div class="form-field">
							<span class="lbl">Referencia local integrado:</span>
							<input type="text" id="ref_local1" name="ref_local1" maxlength="25" class="long">
							
						</div>

						<div class="form-field">
							<span class="lbl">Referencia local producción:</span>
							<input type="text" id="ref_local2" name="ref_local2" maxlength="25" class="long">
						</div>
						
					</div>
					<div class="form-field-divider right">
						
						<div class="form-field">
							<span class="lbl">Fecha inicio aceptación:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_aceptacion' name="fecha_inicio_aceptacion" id="fecha_inicio_aceptacion">
							</div>
						</div>
						
						<div class="form-field">	
							<span class="lbl">Fecha fin aceptación:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" name="fecha_fin_aceptacion" id="fecha_fin_aceptacion">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio validación:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_validacion' name="fecha_inicio_validacion" id="fecha_inicio_validacion">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin validación:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" name="fecha_fin_validacion" id="fecha_fin_validacion">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha Implantación-Producción:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_implantacion_produccion' name="fecha_implantacion_produccion" id="fecha_implantacion_produccion">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio primera operación:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker fromTo" data-target-id='fecha_fin_primera_op' name="fecha_inicio_primera_op" id="fecha_inicio_primera_op">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin primera operación:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" name="fecha_fin_primera_op" id="fecha_fin_primera_op">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio operación cliente:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='fecha_inicio_op_cliente' name="fecha_inicio_op_cliente" id="fecha_inicio_op_cliente">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha paso ANS:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" data-target-id='ans' name="ans" id="ans">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha estimada pruebas:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_pruebas' name="fecha_inicio_pruebas" id="fecha_inicio_pruebas">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha prevista fin pruebas:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" name="fecha_fin_pruebas" id="fecha_fin_pruebas">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio integradas:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_fin_integradas' name="fecha_inicio_integradas" id="fecha_inicio_integradas">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin integradas:</span>
							<div class="input">
								<input type="text" value="" size="16" maxlength="25" class="datepicker" name="fecha_fin_integradas" id="fecha_fin_integradas">
							</div>
						</div>

					</div>	
					
				</div>
				
			</form>
			
			
		</div>
		<br/>
		
			
		</div>
		<div class="modal-footer" id="buttons_new">
				<button type="button" class="" id="submit_service_form" >Guardar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		<div id="message_div" class="message_div" style='margin-bottom:10px;'>
			<span id="span_message" class="span_message"></span>
		</div>
