<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_demanda">

	<img class="users_title_icon" src="../img/user.png"><h1>Alta solicitud gestión de demanda</h1>
	<hr/>
	<form id="newuser_form" name="user_form" action="/usersServlet" method="POST">
	
		<h2>Alta solicitud gestión de demanda</h2>
		<div class="user-form">
			<div class="form-container">				
				<div class="form-field">
					<span>Nombre de cliente</span><input class="long" type="text" >
				</div>
				
				<div class="form-field">
					<span>Tipo de petición</span>
					<select>
						<option value="0">Seleccionar</option>
						<option value="PDTE Doc Alcance en GCS">PDTE Doc Alcance en GCS</option>
						<option value="P-950 en confección">P-950 en confección</option>
						<option value="PDTE Valoración IT">PDTE Valoración IT</option>
						<option value="PDTE Plan de Trabajo IT">PDTE Plan de Trabajo IT</option>
						<option value="PDTE Visto Bueno del CL del plan de trabajo">PDTE Visto Bueno del CL del plan de trabajo</option>
						<option value="En Desarrollo">En Desarrollo</option>
						<option value="En Test - Conectividad">En Test - Conectividad</option>
						<option value="En Test - Integración">En Test - Integración</option>
						<option value="En Test - Aceptación">En Test - Aceptación</option>
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
					<span>Catalogación de petición</span><input class="long" type="text" >
				</div>
				<div class="form-field">
				</div>
				<div class="form-field">
					<span>Devolución de petición</span><input class="long" type="text" >
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
					<span>Estado de la petición</span><input class="long" type="text" >
				</div>
				<div class="form-field textarea">
					<span class="align-top">Motivo de catalogación de petición</span><textarea class="long"></textarea>
				</div>
				<div class="form-field textarea">
					<span class="align-top">Motivo de devolución de petición</span><textarea class="long"></textarea>
				</div>
			</div>				
		</div>
		<h2>Proyecto</h2>
		<div>
			<div class="form-container">				
				<div class="form-field">
					<span>Nombre</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>Producto</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>Conectividad</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>Formato Fichero</span>
					<select>
						<option value="0" selected>Seleccionar</option>
					</select>
				</div>
				<div class="form-field">
					<span>Servicio</span><input class="long" type="text" >
				</div>
			</div>
			<div class="form-container">				
				<div class="form-field">
					<span>Código de Proyecto</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>Estado de petición</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>Fecha implantación</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>Descripción</span><input class="long" type="text" >
				</div>
			</div>
		</div>
		<hr/>
		
		<button id="newUser_submit">Guardar</button>
	</form>
	
</div>