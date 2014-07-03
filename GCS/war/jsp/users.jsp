<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<img class="users_title_icon" src="../img/user.png"><h1>Listado de usuarios</h1>
<hr/>
<h2>Usuarios</h2>
	<div class="usersTable">
		<table class="sortable">
			<tbody>
				<tr>
					<th><span class="table-title">Nombre</span></th>
					<th><span class="table-title">Apellido 1</span></th>
					<th><span class="table-title">Apellido 2</span></th>
					<th><span class="table-title">Email</span></th>
					<th><span class="table-title">Permisos</span></th>
					<th style="width:110px;">&nbsp;</th>
				</tr>
			</tbody>
		</table>
		<table class="users">
			<tbody class="table_results">
			
		
			
			<c:choose>
		      <c:when test="${empty userList}">
		     	 <tr>
						<td><span>No existen usuarios.</span></td>
				</tr>
		      </c:when>
		
		      <c:otherwise>
		      	<c:forEach items="${userList}" var="user">		            	
		            <tr id="row${user.key.id}">
						<td><span>${user.nombre}</span></td>
						<td><span>${user.apellido1}</span></td>
						<td><span>${user.apellido2}</span></td>
						<td><span>${user.email}</span></td>
						<td><span>${user.permisoStr}</span></td>
						<td>
							<img class="vs" src="../img/vs.png">
							<a class="papelera" name="${user.key.id}"  data-toggle="modal" data-target="#confirm-delete"> </a>
							<a class="lapiz" name="${user.key.id}"></a>									
						</td>
					</tr>
			    </c:forEach>
		      </c:otherwise>
			</c:choose>			 
			</tbody>
		</table>
	</div>
	<div class="newUserbox">
		<button id="newUserButton">
			Nuevo Usuario<a class="user_span"></a>			
		</button>
		
		<div class="newUser-form">
			<form id="user_form" name="user_form" action="/usersServlet" method="POST">
				<div class="form-container">				
					<div class="form-field">
						<span for="nombre">Nombre</span><input class="long required" type="text" name="nombre" id="nombre">
					</div>
					<div class="form-field">
						<span>Apellido 1</span><input class="long" type="text" name="ap1" id="ap1">
					</div>
					<div class="form-field">
						<span>Apellido 2</span><input class="long" type="text" name="ap2" id="ap2">
					</div>
					<div class="form-field">
						<span>E-mail</span><input class="long" type="text" name="email" id="email">
					</div>
						<div class="form-field">
						<span>Departamento</span><input class="long" type="text" name="dto" id="dto">
					</div>
					<div class="form-field">
						<span>Permisos</span>
						<select class="long" name="permiso">
							<option selected value="0">Seleccionar</option>
							<option value="5">Gestor IT</option>
							<option value="4">Gestor Demanda</option>
							<option value="3">User Admin</option>
							<option value="2">App Admin</option>
							<option value="1">Super</option>
						</select>
					</div>
					<div id="message_div"><span id="span_message"></span>
					</div>
				</div>
				</form>
			<button id="submit_user_form">Aceptar</button> 
			
		</div>
	</div>
	
	
	
	
	
	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="">
	                <h2>Eliminar usuario</h2>
	                <hr/>
	            </div>
	            <div class="">
	                <p>¿Está seguro que desea eliminar al usuario?
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="" data-dismiss="modal">Cancelar</button>
   	                <button type="button" class="pink-btn" id="deleteUser">Borrar</button>
	                
	            </div>
	        </div>
	    </div>
	</div>


	
		<script src="../js/users.js" type="text/javascript"></script>
	
