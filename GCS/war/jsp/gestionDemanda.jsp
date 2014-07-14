<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_demanda">

	<img class="users_title_icon" src="../img/user.png"><h1>Alta solicitud gesti�n de demanda</h1>
	<hr/>
	<form id="newuser_form" name="user_form" action="/usersServlet" method="POST">
	
		<h2>Alta solicitud gesti�n de demanda</h2>
		<div class="user-form">
			<div class="form-container">				
				<div class="form-field">
					<span>Nombre de cliente</span><input class="long" type="text" >
				</div>
				
				<div class="form-field">
					<span>Tipo de petici�n</span>
					<select>
						<option value="CIB">CIB</option>
						<option value="BEC">BEC</option>
					</select>
					
				</div>
				<div class="form-field">
					<span>Estado de la petici�n</span>
					<select>
						<option value="0">Seleccionar</option>
						<option value="PDTE Doc Alcance en GCS">PDTE Doc Alcance en GCS</option>
						<option value="P-950 en confecci�n">P-950 en confecci�n</option>
						<option value="PDTE Valoraci�n IT">PDTE Valoraci�n IT</option>
						<option value="PDTE Plan de Trabajo IT">PDTE Plan de Trabajo IT</option>
						<option value="PDTE Visto Bueno del CL del plan de trabajo">PDTE Visto Bueno del CL del plan de trabajo</option>
						<option value="En Desarrollo">En Desarrollo</option>
						<option value="En Test - Conectividad">En Test - Conectividad</option>
						<option value="En Test - Integraci�n">En Test - Integraci�n</option>
						<option value="En Test - Aceptaci�n">En Test - Aceptaci�n</option>
						<option value="Parado por Negocio - Producto">Parado por Negocio - Producto</option>
						<option value="Parado por Negocio">Parado por Negocio</option>
						<option value="Parado por IT">Parado por IT</option>
						<option value="Excluido por Negocio">Excluido por Negocio</option>
						<option value="Excluido por Timeout">Excluido por Timeout</option>
						<option value="PDTE Implantar">PDTE Implantar</option>						
						<option value="En Penny Test">En Penny Test</option>
						<option value="Implementado con OK">Implementado con OK</option>						
						<option value="Implementado sin OK">Implementado sin OK</option>						
					</select>
				</div>
				<div class="form-field">
					<span>Catalogaci�n de petici�n</span>
					<select>
						<option value="Implantaci�n">Implantaci�n</option>
						<option value="SEPA">SEPA</option>
						<option value="Migraci�n IA">Migraci�n IA</option>
						<option value="Estandarizaci�n Clientes">Estandarizaci�n Clientes</option>
						<option value="Evolutivo">Evolutivo</option>
						<option value="Pruebas Cliente">Pruebas Cliente</option>
					</select>
				</div>
				<div class="form-field textarea">
					<span class="align-top">Motivo de catalogaci�n de petici�n</span><textarea class="long"></textarea>
				</div>
				
				
				<div class="form-field datetime">
					<span>Fecha petici�n</span>
					<div data-date-format="dd-mm-yyyy" data-date="12-02-2012" id="dp1" class="input-append date">
						<input type="text" readonly="" value="" size="16" class="span2">
						<span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span>
	                    </span>
					  </div>
				</div>
				
				<div class="form-field datetime">
					<span>Fecha solicitud asignaci�n</span>
					<div data-date-format="dd-mm-yyyy" data-date="12-02-2012" id="dp2" class="input-append date">
						<input type="text" readonly="" value="" size="16" class="span2">
						<span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span>
	                    </span>
					  </div>
				</div>
				
			</div>
			<div class="form-container">
				<div class="form-field">
					<span>Nombre Gestor Negocio asignado</span>
					<select>					
					    <option value="0" selected>Seleccionar</option>
						<c:forEach items="${gestores_demanda}" var="user">	
							<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
						</c:forEach>					
				
					</select>
				</div>	
				<div class="form-field">
					<span>Nombre Gestor IT asignado</span>
					<select>					
					    <option value="0" selected>Seleccionar</option>
						<c:forEach items="${gestores_it}" var="user">	
							<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
						</c:forEach>					
				
					</select>
				</div>
				
				<div class="form-field">
				</div>
				<div class="form-field">
					<span>Devoluci�n de petici�n</span><input class="long" type="text" >
				</div>
					
				<div class="form-field textarea">
					<span class="align-top">Motivo de devoluci�n de petici�n</span><textarea class="long"></textarea>
				</div>
				
				<div class="form-field datetime">
					<span>Hora petici�n</span>
					<select>					
					    <option value="0" selected>Seleccionar</option>
						<c:forEach items="${gestores_it}" var="user">	
							<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
						</c:forEach>					
				
					</select>
				</div>
				
				<div class="form-field datetime">
					<span>Hora solicitud asignaci�n</span>
					<select>					
					    <option value="0" selected>Seleccionar</option>
						<c:forEach items="${gestores_it}" var="user">	
							<option value="${user.key.id}">${user.nombre} ${user.apellido1} ${user.apellido2}</option>
						</c:forEach>					
				
					</select>
				</div>
				
			</div>				
		</div>
		
		
		
		<button id="newUser_submit">Guardar</button>
	</form>
	
</div>

 
<script type="text/javascript">
  $(function() {
    $('.date').datepicker();
  });
</script>

