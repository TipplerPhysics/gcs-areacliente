<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="clients">

	<h1>Listado de Clientes</h1>
	<hr/>
	<div class="leyenda_clientes">
		<div class="color alta"></div><span>Criticidad Alta</span>
		<div class="color media"></div><span>Criticidad Media</span>
		<div class="color baja"></div><span>Criticidad Baja</span>
		<div class="selects">
		<span>Tipo de Criticidad</span>
		<select class="selectpicker" id="tip_crit">
			<option value="0">Todas</option>
			<option value="Alta">Alta</option>
			<option value="Media">Media</option>
			<option value="Baja">Baja</option>
		</select>
		<span>Tipo</span>
		<select class="selectpicker" id="tip_client">
			<option value="0">Todos</option>
			<option value="CIB">CIF</option>
			<option value="BEC">NIF</option>
		</select>
		<button onclick=href="http://google.es">Llamada Ajax</button>
		<button id="newUserButton">
				Nuevo formulario<span class="demanda_span"></span>
			</button>
		<div class="new-user-form-holder">
			<form id="new-demanda-form" name="new-user-form" action="/demandaServlet"
				method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl">Fecha entrada petici&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" maxlength="25" class="datepicker fromTo" data-target-id='fecha_solicitud_asignacion' name="fecha_entrada_peticion" id="fecha_entrada_peticion" required aria-required="true">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Hora petici&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
														
								<select class="selectpicker selected time" id="hora_peticion" name="hora_peticion" required aria-required="true">
									<c:forEach items="${horasList}" var="hora">
									<option value="${hora}">${hora}</option>
									</c:forEach>
								</select>
								<span class="time-span">:</span>
								<select class="selectpicker selected time" id="min_peticion" name="min_peticion" required aria-required="true">
									<c:forEach items="${minutosList}" var="min">
									<option value="${min}">${min}</option>
									</c:forEach>
								</select>
								<!-- <![endif]-->
							</div>
						</div>
						<div class="form-field cliente">
							<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="input_cliente" class="selectpicker selected" name="cliente" required aria-required="true">
									<option value="default">Seleccionar</option>
									<c:forEach items="${clientes}" var="cliente">	
										<option value="${cliente.key.id}">${cliente.nombre}</option>
									</c:forEach>
								</select>
								<button type="button" id="btn_new_client" data-target="#new-client" data-toggle="modal">Nuevo</button>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Gestor de negocio:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_negocio" name="gestor_negocio">
								    <option value="default" selected>Seleccionar</option>
									<c:forEach items="${gestores_negocio}" var="user">	
										<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
									</c:forEach>					
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Tipo petici&oacute;n<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="tipo" id="tipo" required aria-required="true">
									<option value="default">Seleccionar</option>								
									<option value="PRUC">PRUC</option>
									<option value="CONS">CONS</option>
									<option value="VIAB">VIAB</option>
									<option value="IMPL">IMPL</option>
									<option value="SEPA">SEPA</option>
									<option value="MIGR-IA">MIGR-IA</option>
									<option value="EVOL-C">EVOL-C</option>
									<option value="IMPL-OB-C">IMPL-OB-C</option>
									<option value="SEPA-OB-C">SEPA-OB-C</option>
									<option value="MIGR-OB-C">MIGR-OB-C</option>	
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Devuelta<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="devuelta" required aria-required="true">
									<option value="NO">NO</option>
									<option value="SI">SI</option>
									
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="estado" id="estado" required aria-required="true">
									<option value="Pendiente de asignaci&oacute;n">Pendiente de asignaci&oacute;n</option>
									<option value="Asignada">Asignada</option>
									<option value="Devuelta">Devuelta</option>
									<option value="Parada">Parada</option>
									<option value="Desestimada">Desestimada</option>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Catalogaci&oacute;n de petici&oacute;n:</span>
							<div class="input">
								<select class="selectpicker" name="catalogacion_peticion">
									<option value="default">Seleccionar</option>
									<option value="Estandar">Est&aacute;ndar</option>
									<option value="Compleja">Compleja</option>
									
								</select>
							</div>
						</div>
					</div><div class="form-field-divider right">
						<div class="form-field">
							<span class="align-top lbl">Motivo de catalogaci&oacute;n:</span>
							<div class='input'>
								<textarea name="motivo_catalogacion" id="motivo_catalogacion" class="long"></textarea>
							</div>
						</div>
						<div class="form-field">
							<span class="align-top lbl">Comentarios:</span>
							<div class='input'>
								<textarea id="comentarios" name="comentarios" class="long"></textarea>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Fecha solicitud asignaci&oacute;n:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" maxlength="25" class="datepicker" name="fecha_solicitud_asignacion" id="fecha_solicitud_asignacion">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Hora solicitud asignaci&oacute;n:</span>
							<div class="input">
								<select class="selectpicker time" id="hora_solicitud_asignacion" name="hora_solicitud_asignacion">
										<option value="default"> </option>
									<c:forEach items="${horasList}" var="hora">
										<option value="${hora}">${hora}</option>
									</c:forEach>
								</select>
								<span class="time-span">:</span>
								<select class="selectpicker time" id="min_solicitud_asignacion" name="min_solicitud_asignacion">
								<option value="default"> </option>
									
									<c:forEach items="${minutosList}" var="min">
										<option value="${min}">${min}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Gestor IT pre-asignado:</span>
							<div class="input">
								<select class="selectpicker" id="gestor_it" name="gestor_it">	
								<option value="default" selected>Seleccionar</option>
									<c:forEach items="${gestores_it}" var="user">
										<option value="${user.key.id}">${user.nombre} ${user.apellido1}<c:if test="${not empty user.apellido2}"> ${user.apellido2}</c:if></option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						
						<div class="form-field">
							<span class="lbl">Fecha comunicaci&oacute;n asignaci&oacute;n:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" maxlength="25" class="datepicker" name="fecha_comunicacion_asignacion" id="fecha_comunicacion_asignacion">
							</div>
						</div>
						<div class="form-field">
							<span class="lbl">Hora comunicaci&oacute;n asignaci&oacute;n:</span>
							<div class="input">
								<select class="selectpicker time" id="hora_comunicacion_asignacion" name="hora_comunicacion_asignacion">
										
									<c:forEach items="${horasList}" var="hora">
										<option value="${hora}">${hora}</option>
									</c:forEach>
								</select>
								<span class="time-span">:</span>
								<select class="selectpicker time" id="min_comunicacion_asignacion" name="min_comunicacion_asignacion">
								
									
									<c:forEach items="${minutosList}" var="min">
										<option value="${min}">${min}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
					</div>
				</div>
			</form>
			<div class="message-container">
				<div id="message_div" class="message_div">
					<span id="span_message" class="span_message"></span>
				</div>
			</div>
			<div id="buttons_new_demanda">
				<button type="submit" class="submit_form" id="submit_demanda_form">Aceptar</button>
				<button href="#" class="close-form">Cancelar</button>
			</div>
		</div>
			</div>	
	</div>
	<div class="search_div">
	<!-- 
		<c:if test="${sessionScope.permiso <= 6}">
				<button onclick="location.href = "./dashboard/gestionDemanda.do";" id="btn_gestion_demanda">Gestión de demanda<img src="../img/gestion.png"></button>  
			
				 
		</c:if>
	 -->

 
	<input type="text" class="long" name="buscador_cliente" id="buscador_cliente" placeholder="Introduzca cliente a buscar">
	
	
 	<c:choose>
	 	<c:when test="${sessionScope.permiso != 5 and sessionScope.permiso != 4}">
		 	<div class="btn-group">
				  <button id="accion_menu" class="btn dropdown-toggle"
				          type="button" data-toggle="dropdown">
				    Acci&oacute;n <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu">
  				    	<li><a href="./dashboard/gestionDemanda.do"><span class="demanda_span blue"></span>Gestión Demanda</a></li>				  
				    	<li><a href="./dashboard/gestionCliente.do"><span class="cliente_span blue"></span>Gestión Clientes</a></li>
				    	<li><a href="./dashboard/informeImplantacion.do"><span class="demanda_span blue"></span>Registro implantaciones</a></li>
				    	<li><a href="./dashboard/gestionProyecto.do"><span class="proyecto_span blue"></span>Gestión Proyectos</a></li>
				    	<li><a href="./dashboard/gestionServicio.do"><span class="service_span blue"></span>Gestión Servicios</a></li>
				    	<li><a href="./dashboard/gestionCostes.do"><span class="coste_span blue"></span>Gestión Costes</a></li>
				    	
				    	
				  </ul>
			</div>
		</c:when>
	 	<c:otherwise> 			
			<button onclick="location.href = './dashboard/gestionCliente.do';" id="btn_alta_cliente">Gestión clientes<img src="../img/new-user-white.png"></button> 
	 
	 	</c:otherwise>
 	</c:choose>
 	
 	<!-- 
 	 
	<button onclick="location.href = './dashboard/gestionCliente.do';" id="btn_alta_cliente">Gestión clientes<img src="../img/new-user-white.png"></button> 
 	  -->
 	
	</div>
	
	
	
	
	<div class="abc">
		<div id="abc_child_scroll" class="scroll_hidden">
			 <c:forEach items="${alphabet}" var="char">
			 	<c:choose>
					<c:when test="${fn:indexOf(letras,char)>0}">
		 				<a id="letra_${char}" href="#${char}_anchor"><span class="active">${char}</span></a>
		 			</c:when>
		 			<c:otherwise>
		 				<a id="letra_${char}"><span class="inactive">${char}</span></a>
		 			</c:otherwise>
		 		</c:choose>
			 </c:forEach>			
		</div>
		<div id="abc_child">
			 <c:forEach items="${alphabet}" var="char">
			 	<c:choose>
					<c:when test="${fn:indexOf(letras,char)>0}">
		 				<a id="letra_${char}" href="#${char}_anchor"><span class="active">${char}</span></a>
		 			</c:when>
		 			<c:otherwise>
		 				<a id="letra_${char}"><span class="inactive">${char}</span></a>
		 			</c:otherwise>
		 		</c:choose>
			 </c:forEach>
			
		</div>
	</div>
	<div class="clients_container">
	
		<c:set var="letra_anterior" value="0" />
		<c:forEach items="${clientes}" var="c">
			<c:set var="letra" value="${fn:substring(c.nombre, 0, 1)}" />
			
			<c:choose>
				<c:when test="${letra ne letra_anterior}">
					<c:choose>
						<c:when test="${letra_anterior ne '0'}">
							</div>
						</c:when>
					</c:choose>
					<div class="l_anchor" id="${letra}_anchor"></div>
					<div class="letter_anchor">
						<span>${letra}</span>	
						<hr/>
					</div>
					<div class="clientes_letra">
				</c:when>
			</c:choose>
				
				<div class="client_box crit_${c.criticidad} tipo_${c.tipo}" data-id="${c.key.id}">
					<p>${c.nombre}</p>
				</div>
			<c:set var="letra_anterior" value="${letra}" />	
		</c:forEach>
		</div>
	</div>	
</div>

<script type="text/javascript">
  $(function() {
       $('.selectpicker').selectpicker();
  });
</script>