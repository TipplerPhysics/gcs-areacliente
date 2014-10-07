  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal_ajax" id="project_modal">

	<div>
		<h2>Editar Proyecto</h2>
		<hr>
	</div>
	
	
	<div class="new-user-form-holder">
		<form id="edit-project-form" name="edit-project-form" action="/projectServlet"
			method="POST" novalidate="novalidate">
			<div class="form-container">
				
				<div class="form-field-divider left">
					<div class="form-field">
						<span class="lbl">Fecha alta proyecto<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" readonly="" value="" size="16" class="datepicker" data-target-id='fecha_alta_cliente' name="fecha_alta_cliente" id="fecha_alta_cliente_modal" required aria-required="true">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">C&oacute;digo Proyecto:</span>
						<input type="text" id="project_name_modal" name="project_name" class="long readonly" readonly="true">
					</div>
					
					<div class="form-field">
							<span class="lbl">Tipo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="tipo" id="tipo_modal" required aria-required="true">
									
									<option value="IMPL">IMPL</option>
									<option value="SEPA">SEPA</option>
									<option value="IMPL-OB-C">IMPL-OB-C</option>
									<option value="IMPL-OB-S">IMPL-OB-S</option>
									<option value="SEPA-OB-C">SEPA-OB-C</option>
									<option value="SEPA-OB-S">SEPA-OB-S</option>
									<option value="MIGR-IA">MIGR-IA</option>
									<option value="MIGR-OB">MIGR-OB</option>
									<option value="MIGR-CH">MIGR-CH</option>
									<option value="MIGR-INF">MIGR-INF</option>
									<option value="EVOL-C">EVOL-C</option>
									<option value="EVOL-S">EVOL-S</option>
									<option value="PRUC">PRUC</option>
									<option value="CONS">CONS</option>
									<option value="VIAB">VIAB</option>
									
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="input_cliente_modal" class="selectpicker" name="cliente" required aria-required="true">
					
									<c:forEach items="${clientes}" var="cliente">	
										<option value="${cliente.key.id}">${cliente.nombre}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Clasificaci&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker" name="clasificacion" id="clasificacion_modal" required aria-required="true">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>									
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestor IT<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_it_modal" name="gestor_it" required aria-required="true">	
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestor de negocio<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_negocio_modal" name="gestor_negocio" required aria-required="true">
									<c:forEach items="${gestores_negocio}" var="user">	
										<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
									</c:forEach>					
								</select>
							</div>
						</div>
						
						<div class="form-field">
						<span class="lbl">Coste<span class="required-asterisk">*</span>:</span>
						<input type="text" aria-required="true" readonly="" required="" id="coste_modal" name="coste" class="long euro readonly">
					</div>
				</div>
				
				<div class="form-field-divider right">
					
					
					
					<div class="form-field">
						<span class="lbl">Producto<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select class="selectpicker selected" id="producto_modal" name="producto" required aria-required="true">
							   
							    <option value="Global Netcash">Global Netcash</option>
							    <option value="H2H">H2H</option>
							    <option value="H2H-BancoRelay">H2H-BancoRelay</option>
							    <option value="Swift-BancoRelay">Swift-BancoRelay</option>
							    <option value="Swift-Fileact">Swift-Fileact</option>												
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Conectividad:</span>
						<div class="input">
							<select class="selectpicker" id="conectividad_modal" name="conectividad">
							    <option value="default">Seleccionar</option>
							    <option value="AS2">AS2</option>
							    <option value="Fileact">Fileact</option>
							    <option value="FTP">FTP</option>
							    <option value="FTPS">FTPS</option>
							    <option value="Host to Web">Host to Web</option>								    
							    <option value="HTTPS">HTTPS</option>
							    <option value="Macug">Macug</option>
							    <option value="Score">Score</option>
							    <option value="SFTP">SFTP</option>
							    <option value="Webservices">Webservices</option>												
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha inicio valoraci&oacute;n:</span>
						<div class="input">
							<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_inicio_valoracion_modal' name="fecha_inicio_valoracion" id="fecha_inicio_valoracion_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin valoraci&oacute;n:</span>
						<div class="input">
							<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_fin_valoracion_modal' name="fecha_fin_valoracion" id="fecha_fin_valoracion_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha inicio viabilidad:</span>
						<div class="input">
							<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_inicio_viabilidad_modal' name="fecha_inicio_viabilidad" id="fecha_inicio_viabilidad_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin viabilidad:</span>
						<div class="input">
							<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_fin_viabilidad_modal' name="fecha_fin_viabilidad" id="fecha_fin_viabilidad_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Servicio:</span>
						<div class="input">
							<select class="selectpicker" id="servicio_modal" name="servicio">
							    <option value="default">Seleccionar</option>
							    <option value="PDTE Doc Alcance en GCS">PDTE Doc Alcance en GCS</option>
							    <option value="C100 en confecci&oacute;n">C100 en confecci&oacute;n</option>
							    <option value="PDTE Valoraci&oacute;n IT">PDTE Valoraci&oacute;n IT</option>
							    <option value="PDTE Plan de Trabajo IT">PDTE Plan de Trabajo IT</option>
							    <option value="PDTE Visto Bueno del CL del plan de trabajo">PDTE Visto Bueno del CL del plan de trabajo</option>								    
							    <option value="En Desarrollo">En Desarrollo</option>
							    <option value="En Test - Conectividad">En Test - Conectividad</option>
							    <option value="En Test - Integraci&oacute;n">En Test - Integraci&oacute;n</option>
							    <option value="En Test - Aceptaci&oacute;n">En Test - Aceptaci&oacute;n</option>
							    <option value="Parado por Producto">Parado por Producto</option>
							    <option value="Parado por Negocio">Parado por Negocio</option>
							    <option value="Parado por IT">Parado por IT</option>
							    <option value="Excluido por negocio">Excluido por negocio</option>							    
							    <option value="Excluido por Timeout">Excluido por Timeout</option>
							    <option value="PDTE Implantar">PDTE Implantar</option>
							    <option value="En Penny Test">En Penny Test</option>
							    <option value="Implementado con OK">Implementado con OK</option>
							    <option value="Implementado sin OK">Implementado sin OK</option>												
							</select>
						</div>
					</div>
				</div>
				
				<div id="message_div">
					<span id="span_message"></span>
				</div>
			</div>
				
		

		</form>
	
		
			
	
	<div style="margin-bottom:10px;" class="message_div" id="message_div_demanda_modal">
		<span class="span_message" id="span_message_demanda_modal"></span>
	</div>
			
			
	</div>		
	<div class="modal-footer">
		<button onclick="sendEditProject();" id="edit_project_form_modal" class="" type="button">Guardar</button>
		<button data-dismiss="modal" class="dismis_edit_project" type="button">Cancelar</button>
	</div>	
</div>
<div class="ajax_loader" id="ajax_loader">
	<img src="../../img/ajax-loader.gif" />
</div>