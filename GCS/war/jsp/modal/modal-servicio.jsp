<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="modal_ajax">
		<div class="">
					<h2>Editar Servicio</h2>
					<hr />
				</div>
				<div class="edit-user-form-holder">
				<form id="edit-servicio-form" name="edit-servicio-form" action="/serviceServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
					
						<div class="form-field">
							<span class="lbl">Cod. de proyecto<span class="required-asterisk">*</span>:</span>
							<div class="input">
							
							<input type="text" id="cod_proyecto_modal" value="${servicio.cod_proyecto}" class="readonly" readonly  name="cod_proyecto" class="long">
							
								
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Pais<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="pais" id="pais_servicio_modal" required aria-required="true">
									<option value="Argentina"  ${servicio.pais == 'Argentina' ? 'selected' : ''}>Argentina</option>
									<option value="Bélgica" ${servicio.pais == 'Bélgica' ? 'selected' : ''}>Bélgica</option>
									<option value="Chile" ${servicio.pais == 'Chile' ? 'selected' : ''}>Chile</option>
									<option value="Colombia" ${servicio.pais == 'Colombia' ? 'selected' : ''}>Colombia</option>
									<option value="España" ${servicio.pais == 'España' ? 'selected' : ''}>España</option>									
									<option value="EEUU" ${servicio.pais == 'EEUU' ? 'selected' : ''}>EEUU</option>
									<option value="Francia" ${servicio.pais == 'Francia' ? 'selected' : ''}>Francia</option>
									<option value="Perú" ${servicio.pais == 'Perú' ? 'selected' : ''}>Perú</option>
									<option value="Portugal" ${servicio.pais == 'Portugal' ? 'selected' : ''}>Portugal</option>
									<option value="UK" ${servicio.pais == 'UK' ? 'selected' : ''}>UK</option>
									<option value="Venezuela" ${servicio.pais == 'Venezuela' ? 'selected' : ''}>Venezuela</option>																		
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Servicio<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="servicio" id="servicio_modal" required aria-required="true">
									
									<c:forEach items="${servicios_pais}" var="s">	
										<option value="${s}" ${servicio.servicio == s ? 'selected' : ''}>${s}</option>
									</c:forEach>	
																					
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="estado" id="estado_modal" required aria-required="true">
									<option value="PDTE Doc Alcance en GCS" ${servicio.estado == 'PDTE Doc Alcance en GCS' ? 'selected' : ''}>PDTE Doc Alcance en GCS</option>
									<option value="C100 en confección" ${servicio.estado == 'C100 en confección' ? 'selected' : ''}>C100 en confección</option>
									<option value="PDTE Valoración IT" ${servicio.estado == 'PDTE Valoración IT' ? 'selected' : ''}>PDTE Valoración IT</option>	
									<option value="PDTE Plan de Trabajo IT" ${servicio.estado == 'PDTE Plan de Trabajo IT' ? 'selected' : ''}>PDTE Plan de Trabajo IT</option>
									<option value="PDTE Visto Bueno del CL del plan de trabajo" ${servicio.estado == 'PDTE Visto Bueno del CL del plan de trabajo' ? 'selected' : ''}>PDTE Visto Bueno del CL del plan de trabajo</option>
									<option value="En Desarrollo" ${servicio.estado == 'En Desarrollo' ? 'selected' : ''}>En Desarrollo</option>
									<option value="En Test - Conectividad" ${servicio.estado == 'En Test - Conectividad' ? 'selected' : ''}>En Test - Conectividad</option>
									<option value="En Test - Integración" ${servicio.estado == 'En Test - Integración' ? 'selected' : ''}>En Test - Integración</option>	
									<option value="En Test - Aceptación" ${servicio.estado == 'En Test - Aceptación' ? 'selected' : ''}>En Test - Aceptación</option>	
									<option value="Parado por producto" ${servicio.estado == 'Parado por producto' ? 'selected' : ''}>Parado por producto</option>	
									<option value="Parado por negocio" ${servicio.estado == 'Parado por negocio' ? 'selected' : ''}>Parado por negocio</option>	
									<option value="Parado por IT" ${servicio.estado == 'Parado por IT' ? 'selected' : ''}>Parado por IT</option>
									<option value="Excluido por negocio" ${servicio.estado == 'Excluido por negocio' ? 'selected' : ''}>Excluido por negocio</option>	
									<option value="Excluido por Timeout" ${servicio.estado == 'Excluido por Timeout' ? 'selected' : ''}>Excluido por Timeout</option>	
									<option value="PDTE Implantar" ${servicio.estado == 'PDTE Implantar' ? 'selected' : ''}>PDTE Implantar</option>	
									<option value="En Penny Test" ${servicio.estado == 'En Penny Test' ? 'selected' : ''}>En Penny Test</option>
									<option value="Implementado con OK" ${servicio.estado == 'Implementado con OK' ? 'selected' : ''}>Implementado con OK</option>
									<option value="Implementado sin OK" ${servicio.estado == 'Implementado sin OK' ? 'selected' : ''}>Implementado sin OK</option>										
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Cod. servicio<span class="required-asterisk">*</span>:</span>
							<input type="text" id="cod_servicio_modal" value="${servicio.cod_servicio}" name="cod_servicio" class="long" required aria-required="true">

						</div>
						
						<div class="form-field">
							<span class="lbl">Observaciones:</span>
							<div class="input">
								<textarea name="observaciones"  maxlength="160" id="observaciones_modal">${servicio.observaciones}</textarea>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Formato intermedio:</span>
							<input type="text" id="formato_intermedio_modal" value="${servicio.formato_intermedio}" name="formato_intermedio" class="long">
						</div>
						
						<div class="form-field">
							<span class="lbl">Formato local:</span>
							<input type="text" id="formato_local_modal" value="${servicio.formato_local}" name="formato_local" class="long">
						</div>
						
						<div class="form-field">
							<span class="lbl">Referencia local:</span>
							<input type="text" id="ref_local1_modal" value="${servicio.referencia_local1}" name="ref_local1" class="long">
							<input type="text" id="ref_local2_modal" value="${servicio.referencia_local2}" name="ref_local2" class="long">
						</div>
						<div class="form-field">
							<span class="lbl">Fecha inicio integradas:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_integradas}" size="16" class="datepicker" data-target-id='fecha_inicio_integradas_modal' name="fecha_inicio_integradas" id="fecha_inicio_integradas_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin integradas:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_fin_integradas}" size="16" class="datepicker" data-target-id='fecha_fin_integradas_modal' name="fecha_fin_integradas" id="fecha_fin_integradas_modal">
							</div>
						</div>
						

					</div>
					<div class="form-field-divider right">
						
						
						<div class="form-field">
							<span class="lbl">Fecha inicio aceptación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_aceptacion}" size="16" class="datepicker" data-target-id='fecha_inicio_aceptacion_modal' name="fecha_inicio_aceptacion" id="fecha_inicio_aceptacion_modal">
							</div>
						</div>
						
						<div class="form-field">	
							<span class="lbl">Fecha fin aceptación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_fin_aceptacion}" size="16" class="datepicker" data-target-id='fecha_fin_aceptacion_modal' name="fecha_fin_aceptacion" id="fecha_fin_aceptacion_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio validación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_validacion}" size="16" class="datepicker" data-target-id='fecha_inicio_validacion_modal' name="fecha_inicio_validacion" id="fecha_inicio_validacion_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin validación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_fin_validacion}" size="16" class="datepicker" data-target-id='fecha_fin_validacion_modal' name="fecha_fin_validacion" id="fecha_fin_validacion_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha Implantación-Producción:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_implantacion_produccion}" size="16" class="datepicker" data-target-id='fecha_implantacion_produccion_modal' name="fecha_implantacion_produccion" id="fecha_implantacion_produccion_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio primera operación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_primera_operacion}" size="16" class="datepicker" data-target-id='fecha_inicio_primera_op_modal' name="fecha_inicio_primera_op" id="fecha_inicio_primera_op_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin primera operación:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_fin_primera_operacion}" size="16" class="datepicker" data-target-id='fecha_fin_primera_op_modal' name="fecha_fin_primera_op" id="fecha_fin_primera_op_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio operación cliente:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_op_cliente}" size="16" class="datepicker" data-target-id='fecha_inicio_op_cliente_modal' name="fecha_inicio_op_cliente" id="fecha_inicio_op_cliente_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha paso ANS:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ANS}" size="16" class="datepicker" data-target-id='ans_modal' name="ans" id="ans_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha estimada pruebas:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_ini_pruebas}" size="16" class="datepicker" data-target-id='fecha_inicio_pruebas_modal' name="fecha_inicio_pruebas" id="fecha_inicio_pruebas_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha prevista fin pruebas:</span>
							<div class="input">
								<input type="text" value="${servicio.str_fecha_fin_pruebas}" size="16" class="datepicker" data-target-id='fecha_fin_pruebas_modal' name="fecha_fin_pruebas" id="fecha_fin_pruebas_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha migración Channeling:</span>
							<div class="input">
								<input type="text" value="${servicio.str_migracion_channeling}" size="16" class="datepicker" data-target-id='fecha_mig_channeling_modal' name="fecha_mig_channeling" id="fecha_mig_channeling_modal">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha migración infraestructura:</span>
							<div class="input">
								<input type="text" value="${servicio.str_migracion_infra}" size="16" class="datepicker" data-target-id='fecha_mig_infraestructura_modal' name="fecha_mig_infraestructura" id="fecha_mig_infraestructura_modal">
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
				<button type="button" class="" id="edit_servicio_form_modal" onclick="sendEditServicio();">Guardar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
<div class="ajax_loader" id="ajax_loader">
	<img src="../../img/ajax-loader.gif" />
</div>