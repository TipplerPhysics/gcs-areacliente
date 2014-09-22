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
						<span class="lbl">Fecha alta cliente<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" readonly="" value="" size="16" class="datepicker" data-target-id='fecha_alta_cliente' name="fecha_alta_cliente" id="fecha_alta_cliente_modal" required aria-required="true">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Nombre Proyecto:</span>
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
				</div>
				
				<div class="form-field-divider right">
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
						<input type="text" aria-required="true" required="" id="coste_modal" name="coste" class="long euro">
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