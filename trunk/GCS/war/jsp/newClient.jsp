<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="newClient">

	<img class="users_title_icon" src="../img/user.png"><h1>Nuevo cliente</h1>
	<hr/>
	<form id="newuser_form" name="user_form" action="/usersServlet" method="POST">
	
		<h2>Datos del cliente</h2>
		<div class="user-form">
			<div class="form-container">				
				<div class="form-field">
					<span>IDCliente</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>IDCliente Global</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>Nacionalidad</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>Actividad</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>Correo Electrónico</span><input class="long" type="text" >
				</div>
			</div>
			<div class="form-container">	
				<div class="form-field">
					<span>Criticidad</span>
					<select>
						<option value="0" selected>Ninguna</option>
						<option value="1">Alta</option>
						<option value="2">Media</option>
						<option value="3">Baja</option>					
					</select>
				</div>
				<div class="form-field">
					<span>Estado</span><input class="long" type="text" >
				</div>
				<div class="form-field">
					<span>Paises</span><input class="long" type="text" >
				</div>
			</div>				
		</div>
		<h2>Proyecto</h2>
		<div class="newUser-form">
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
					<span>Estado</span><input class="long" type="text" >
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