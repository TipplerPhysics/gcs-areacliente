	  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<div class="modal_ajax">
		<div class="">
					<h2>Editar Cliente</h2>
					<hr />
				</div>
				<div class="edit-user-form-holder">
			<form id="edit-client-form" name="edit-client-form" action="/clienteServlet"
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
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<input type="text" aria-required="true" required="" id="client_name_modal" name="client_name" class="long">
						</div>
						<div class="form-field">
							<span class="lbl">Tipo<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" id="tipo_modal" name="tipo" required aria-required="true">
								  
								    <option value="CIB">CIB</option>
								    <option value="BEC">BEC</option>
													
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Criticidad<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="criticidad" id="criticidad_modal" required aria-required="true">
									<option value="Baja">Baja</option>
									<option value="Media">Media</option>
									<option value="Alta">Alta</option>			
									
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Referencia Global:</span>
							<input type="text" minlength="11" id="ref_global_modal" name="ref_global" class="long" maxlength="11">
						</div>
						
					</div><div class="form-field-divider right">
					
						
						
						
						
						<div class="form-field">
							<span class="lbl">Logo-url:</span>
							<input type="text" id="logo_url_modal" name="logo_url" class="long">
						</div>
						
						<div class="form-field">	
							<span class="lbl">Paises<span class="required-asterisk">*</span>:</span>					
							<div class="radio-div">
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Argentina"
										id="argentina_check_modal" class="require-one"><label for="argentina_check_modal"><span></span>Argentina</label>
								</div>
								
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Bélgica"
										id="belgica_check_modal"><label for="belgica_check_modal"><span></span>B&eacute;lgica</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Chile"
										id="chile_check_modal"><label for="chile_check_modal"><span></span>Chile</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="China"
										id="china_check_modal"><label for="china_check_modal"><span></span>China</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Colombia"
										id="colombia_check_modal"><label for="colombia_check_modal"><span></span>Colombia</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Espa&ntilde;a"
										id="esp_check_modal"><label for="esp_check_modal"><span></span>Espa&ntilde;a</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Francia"
										id="francia_check_modal"><label for="francia_check_modal"><span></span>Francia</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Italia"
										id="italia_check_modal"><label for="italia_check_modal"><span></span>Italia</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="México"
										id="mexico_check_modal"><label for="mexico_check_modal"><span></span>M&eacute;xico</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Perú"
										id="peru_check_modal"><label for="peru_check_modal"><span></span>Per&uacute;</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Portugal"
										id="portugal_check_modal"><label for="portugal_check_modal"><span></span>Portugal</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Reino Unido"
										id="uk_check_modal"><label for="uk_check_modal"><span></span>Reino Unido</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Uruguay"
										id="uruguay_check_modal"><label for="uruguay_check_modal"><span></span>Uruguay</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="USA"
										id="usa_check_modal"><label for="usa_check_modal"><span></span>USA</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Venezuela"
										id="venezuela_check_modal"><label for="venezuela_check_modal"><span></span>Venezuela</label>
								</div>
								<div class="radio-container">
									<input type="checkbox" name='paises' value="Redex"
										id="redex_check_modal"><label for="redex_check_modal"><span></span>Redex</label>
								</div>								
							</div>
						</div>				
					</div>					
				</div>
			</form>
			<div id="message_div_cliente_modal" class="message_div" style='margin-bottom:10px;'>
						<span id="span_message_cliente_modal" class="span_message"></span>
			</div>
			
		</div>
				<div class="modal-footer">
					<button type="button" class="" id="edit_client_form_modal" onclick="sendEditClient();">Guardar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
		</div>
