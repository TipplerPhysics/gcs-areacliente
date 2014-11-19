<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="gestion_servicio">

	<h1>Gesti&oacute;n servicio</h1>
	<span class="btn-atras" onclick="window.location.href='${entorno}/dashboard/gestionProyecto.do'"></span>
	
	
	<hr/>
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span onclick="window.location.href='./gestionCliente.do' ">Gestión de clientes</span>  > <span onclick="window.location.href='./gestionProyecto.do' ">Gestión de proyecto</span> > <span> Gestión de servicio </span>
	</div>
	
	<div class="newUserbox">
		<button id="newUserButton">
			Nuevo Servicio<span class="service_span"></span>
		</button>
		
		
		<button id="excel_btn" onclick="window.location.href='../../serviceServlet?accion=xls'">
			Descargar Tabla<span class="excel_span"></span>
		</button>


		<div class="new-user-form-holder">
			<form id="new-service-form" name="new-service-form" action="/serviceServlet"
				method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl">Cód. de proyecto<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="cod_proyecto" id="cod_proyecto" required aria-required="true">
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
								<select class="selectpicker selected" name="pais" id="pais_servicio" required aria-required="true">
									<option value="default">Seleccionar</option>
									<option value="Argentina">Argentina</option>
									<option value="Bélgica">Bélgica</option>
									<option value="Chile">Chile</option>
									<option value="Colombia">Colombia</option>
									<option value="España">España</option>									
									<option value="EEUU">EEUU</option>
									<option value="Francia">Francia</option>
									<option value="Perú">Perú</option>
									<option value="Portugal">Portugal</option>
									<option value="UK">UK</option>
									<option value="Venezuela">Venezuela</option>																		
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Servicio<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="servicio" id="servicio" required aria-required="true">
									<option value="default">-</option>																	
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="estado" id="estado" required aria-required="true">
									<option value="default">Seleccionar</option>
									<option value="PDTE Doc Alcance en GCS">PDTE Doc Alcance en GCS</option>
									<option value="C100 en confección">C100 en confección</option>
									<option value="PDTE Valoración IT">PDTE Valoración IT</option>	
									<option value="PDTE Plan de Trabajo IT">PDTE Plan de Trabajo IT</option>
									<option value="PDTE Visto Bueno del CL del plan de trabajo">PDTE Visto Bueno del CL del plan de trabajo</option>
									<option value="En Desarrollo">En Desarrollo</option>
									<option value="En Test - Conectividad">En Test - Conectividad</option>
									<option value="En Test - Integración">En Test - Integración</option>	
									<option value="En Test - Aceptación">En Test - Aceptación</option>	
									<option value="Parado por producto">Parado por producto</option>	
									<option value="Parado por negocio">Parado por negocio</option>	
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
						
						<div class="form-field">
							<span class="lbl">Cód. servicio<span class="required-asterisk">*</span>:</span>
							<input type="text" id="cod_servicio" name="cod_servicio" class="long" required aria-required="true">
						</div>
						
						<div class="form-field">
							<span class="lbl">Observaciones:</span>
							<div class="input">
								<textarea name="observaciones" maxlength="160" id="observaciones"></textarea>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Formato intermedio:</span>
							<input type="text" id="formato_intermedio" name="formato_intermedio" class="long">
						</div>
						
						<div class="form-field">
							<span class="lbl">Formato local:</span>
							<input type="text" id="formato_local" name="formato_local" class="long">
						</div>
						
						<div class="form-field">
							<span class="lbl">Referencia local:</span>
							<input type="text" id="ref_local1" name="ref_local1" class="long">
							<input type="text" id="ref_local2" name="ref_local2" class="long">
						</div>
						<div class="form-field">
							<span class="lbl">Fecha inicio integradas:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker fromTo" data-target-id='fecha_fin_integradas' name="fecha_inicio_integradas" id="fecha_inicio_integradas">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin integradas:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" name="fecha_fin_integradas" id="fecha_fin_integradas">
							</div>
						</div>
						
						
						
					</div>
					<div class="form-field-divider right">
						
						<div class="form-field">
							<span class="lbl">Fecha inicio aceptación:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker fromTo" data-target-id='fecha_fin_aceptacion' name="fecha_inicio_aceptacion" id="fecha_inicio_aceptacion">
							</div>
						</div>
						
						<div class="form-field">	
							<span class="lbl">Fecha fin aceptación:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" name="fecha_fin_aceptacion" id="fecha_fin_aceptacion">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio validación:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker fromTo" data-target-id='fecha_fin_validacion' name="fecha_inicio_validacion" id="fecha_inicio_validacion">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin validación:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" name="fecha_fin_validacion" id="fecha_fin_validacion">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha Implantación-Producción:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_implantacion_produccion' name="fecha_implantacion_produccion" id="fecha_implantacion_produccion">
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
								<input type="text" value="" size="16" class="datepicker"  name="fecha_fin_primera_op" id="fecha_fin_primera_op">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha inicio operación cliente:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" name="fecha_inicio_op_cliente" id="fecha_inicio_op_cliente">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha paso ANS:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" data-target-id='ans' name="ans" id="ans">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha estimada pruebas:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker fromTo" data-target-id='fecha_fin_pruebas' name="fecha_inicio_pruebas" id="fecha_inicio_pruebas">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha prevista fin pruebas:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" name="fecha_fin_pruebas" id="fecha_fin_pruebas">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha migración Channeling:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_mig_channeling' name="fecha_mig_channeling" id="fecha_mig_channeling">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha migración infraestructura:</span>
							<div class="input">
								<input type="text" value="" size="16" class="datepicker" data-target-id='fecha_mig_infraestructura' name="fecha_mig_infraestructura" id="fecha_mig_infraestructura">
							</div>
						</div>
					</div>					
				</div>
				
			</form>
			<div class="message-container">
				<div class="message_div" id="message_div">
					<span class="span_message" id="span_message"></span>
				</div>
			</div>
			<div id="buttons_new">
				<button type="submit" class="submit_form" id="submit_service_form">Aceptar</button>
				<button href="#" class="close-form">Cancelar</button>
			</div>
		</div>
	</div>
	<div>	
		<div>
			<div class="table-responsive usersTable">
				<table class="table">
					<thead>
						<tr>
							<th><span class="table-title">Cód. Proyecto</span></th>
							<th><span class="table-title">Servicio</span></th>
							<th><span class="table-title">Estado</span></th>
							<th><span class="table-title">Gestor IT</span></th>
							<th><span class="table-title">Gestor Negocio</span></th>
							<th><span class="table-title">Cliente</span></th>
							
							<th style="width: 110px;">&nbsp;</th>
						</tr>
						<tr>
							<th class="search-th"><input class="search col0"></th>
							<th class="search-th"><input class="search col1"></th>
							<th class="search-th"><input class="search col2"></th>
							<th class="search-th"><input class="search col3"></th>
							<th class="search-th"><input class="search col4"></th>	
							<th class="search-th"><input class="search col5"></th>						
							<th style="width: 110px;">&nbsp;</th>
						</tr>
					</thead>
					<tbody id="myTable" cellspacing="0">
					
						<c:forEach items="${servicios}" var="servicio">
							<tr class="valid-result" id="row${servicio.key.id}" name="${servicio.key.id}">
								<td><span>${servicio.cod_proyecto}</span></td>
								<td><span>${servicio.servicio}</span></td>
								<td><span>${servicio.estado}</span></td>										
								<td><span>${servicio.gestor_it_name}</span></td>
								<td><span>${servicio.gestor_negocio_name}</span></td>
								<td><span>${servicio.cliente_name}</span></td>
								
								<td>										
									<img class="vs" src="../img/vs.png">								
									<a class="lapiz" name="${servicio.key.id}" href="../servicioModal.do?id=${servicio.key.id}"	id="lapiz${servicio.key.id}" data-toggle="modal" data-target="#edit-service"></a>
									<a class="papelera" name="${servicio.key.id}" data-toggle="modal" data-target="#confirm-delete" id="papelera${servicio.key.id}"></a>
								</td>
								
							</tr>
						</c:forEach>
						
					</tbody>
				</table>
			</div>
			<div class="col-md-12 text-center">
				<ul class="pagination" id="myPager"></ul>
				<span class="pagesummary"></span>
			</div>
		</div>
	</div>

	<div class="modal fade" id="edit-service" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" id="edit_service_dialog">
			<div class="modal-content">
		
			</div>
		</div>
	</div>

	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content noErase">
				<div class="">
					<h2>Eliminar Servicio</h2>
					<hr />
				</div>
				<div class="">
					<p>&iquest;Est&aacute; seguro que desea eliminar el servicio?<p>
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteServicio">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>