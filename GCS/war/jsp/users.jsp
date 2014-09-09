<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="alta_usuario">


<img class="users_title_icon" src="../img/user.png">
<h1>Listado de usuarios</h1>
<span class="btn-atras" onclick="window.location.href='../../'"></span>

<hr />

<div class="newUserbox">
	<button id="newUserButton">
		Nuevo Usuario<a class="user_span"></a>
	</button>
	<button id="excel_btn" onclick="window.location.href='../../usersServlet?accion=xls'">
		Descargar Tabla<a class="excel_span"></a>
	</button>


	<div class="new-user-form-holder">
		<form id="new-user-form" name="new-user-form" action="/usersServlet"
			method="POST" novalidate="novalidate">
			<div class="form-container">
				<div class="form-field">
					<span for="nombre">Nombre<span class="required-asterisk">*</span>:</span><input class="long" type="text"
						name="nombre" id="nombre" required aria-required="true">
				</div>
				<div class="form-field">
					<span>Apellido 1<span class="required-asterisk">*</span>:</span><input class="long" type="text" name="ap1"
						id="ap1" required aria-required="true">
				</div>
				<div class="form-field">
					<span>Apellido 2:</span><input class="long" type="text" name="ap2"
						id="ap2">
				</div>
				<div class="form-field">
					<span>E-mail<span class="required-asterisk">*</span>:</span><input class="long email" type="text"
						name="email" id="email" required aria-required="true"
						data-type="email">
				</div>
				<div class="form-field">
					<span>Departamento<span class="required-asterisk">*</span>:</span><select id="dto_select"
						class="long selected selectpicker" name="dto" required>
						<option selected value="default">Seleccionar</option>
						<c:forEach items="${departamentos}" var="departamento">		         	
							<option value="${departamento.value}">${departamento.desc}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-field">
					<span>Perfil<span class="required-asterisk">*</span>:</span><select id="permiso_select" class="long selected selectpicker"
						name="permiso" required>
						<option selected value="default">Seleccionar</option>
						<c:forEach items="${permisos}" var="permiso">		         	
							<option value="${permiso.value}">${permiso.desc}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-fieldset">
					<span class="fieldset-title">&Aacute;reas:</span>
					<fieldset class='radio-container-holder'>
						<div class="radio-container">
							<input type="checkbox" name='areas' value="Onboarding"
								id="onboarding"><label for="onboarding"><span></span>Onboarding</label>
						</div>
						<div class="radio-container">
							<input type="checkbox" name='areas' value="Servicing"
								id="servicing"><label for="servicing"><span></span>Servicing</label>
						</div>
						<div class="radio-container">
							<input type="checkbox" name='areas' value="Clientes"
								id="clientes"><label for="clientes"><span></span>Clientes</label>
						</div>
						<div class="radio-container">
							<input type="checkbox" name='areas' value="ITCIB" id="itcib"><label
								for="itcib"><span></span>ITCIB</label>
						</div>
						<div class="radio-container">
							<input type="checkbox" name='areas'
								value="Global Customer Service" id="gcs"><label
								for="gcs"><span></span>Global Customer Service</label>
						</div>
						<div class="radio-container">
							<input type="checkbox" name='areas' value="Global Product"
								id="global-product"><label for="global-product"><span></span>Global
								Product</label>
						</div>
						
					</fieldset>
				</div>
				<div id="message_div">
					<span id="span_message"></span>
				</div>
			</div>

		</form>
		<button type="submit" id="submit_user_form">Aceptar</button>
		<button href="#" class="close-form">Cancelar</button>
	</div>
</div>


<div>	
	<div>
		<div class="table-responsive usersTable">
			<table class="table">
				<thead>
					<tr>
						<th><span class="table-title">Nombre</span></th>
						<th><span class="table-title">Apellido 1</span></th>
						<th><span class="table-title">Apellido 2</span></th>
						<th><span class="table-title">Departamento</span></th>
						<th><span class="table-title">Perfil</span></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
					<tr>
						<th class="search-th"><input class="search col0"></th>
						<th class="search-th"><input class="search col1"></th>
						<th class="search-th"><input class="search col2"></th>
						<th class="search-th"><input class="search col3"></th>
						<th class="search-th"><input class="search col4"></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="myTable" cellspacing="0">
					<c:choose>
						<c:when test="${empty userList}">
							<tr>
								<td><span>No existen usuarios.</span></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach items="${userList}" var="user">
								<tr class="valid-result" id="row${user.key.id}" data-area="${user.areas}" data-permiso="${user.permiso}" data-mail="${user.email}"
									data-dto="${user.departamento}">
									<td><span>${user.nombre}</span></td>
									<td><span>${user.apellido1}</span></td>
									<td><span>${user.apellido2}</span></td>
									<td><span>${user.departamento}</span></td>
									<td><span>${user.permisoStr}</span></td>
									<td><img class="vs" src="../img/vs.png"><a class="lapiz" name="${user.key.id}"	id="lapiz${user.key.id}" data-toggle="modal" data-target="#edit-user"></a><a class="papelera" name="${user.key.id}" data-toggle="modal"	data-target="#confirm-delete" id="papelera${user.key.id}"></a></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<div class="col-md-12 text-center">
			<ul class="pagination" id="myPager"></ul>
			<span class="pagesummary"></span>
		</div>
	</div>
</div>


<div class="modal fade" id="edit-user" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="edit_user_dialog">
		<div class="modal-content">
			<div class="">
				<h2>Editar usuario</h2>
				<hr />
			</div>
			<div class="newUserbox">
				<div class="edit-user-form-holder open" id="edit_user">
								<form id="edit-user-form" name="edit-user-form" action="/usersServlet"
						method="POST" novalidate="novalidate">
						<div class="form-container">
							<div class="form-field">
								<span for="nombre" class="lbl">Nombre<span class="required-asterisk">*</span>:</span><input class="long" type="text"
									name="nombre" id="nombre_modal" required aria-required="true">
							</div>
							<div class="form-field">
								<span  class="lbl">Apellido 1<span class="required-asterisk">*</span>:</span><input class="long" type="text" name="ap1"
									id="ap1_modal" required aria-required="true">
							</div>
							<div class="form-field">
								<span class="lbl">Apellido 2:</span><input class="long" type="text" name="ap2"
									id="ap2_modal">
							</div>
							<div class="form-field">
								<span class="lbl">E-mail<span class="required-asterisk">*</span>:</span><input class="long email" type="text"
									name="email" id="email_modal" required aria-required="true"
									data-type="email">
							</div>
							<div class="form-field">
								<span class="lbl">Departamento<span class="required-asterisk">*</span>:</span>
								<select id="dto_select_modal"
									class="long selected selectpicker" name="dto">
									<c:forEach items="${departamentos}" var="departamento">		         	
										<option value="${departamento.value}">${departamento.desc}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-field">
								<span class="lbl">Perfil<span class="required-asterisk">*</span>:</span>
								<select id="permiso_select_modal" class="long selected selectpicker"
									name="permiso">
									<c:forEach items="${permisos}" var="permiso">		         	
										<option value="${permiso.value}">${permiso.desc}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-fieldset">
								<span class="fieldset-title lbl">&Aacute;reas:</span>
								<fieldset class='radio-container-holder'>
									<div class="radio-container">
										<input type="checkbox" name='areas' value="Onboarding"
											id="onboarding_modal"><label for="onboarding"><span></span>Onboarding</label>
									</div>
									<div class="radio-container">
										<input type="checkbox" name='areas' value="Servicing"
											id="servicing_modal"><label for="servicing"><span></span>Servicing</label>
									</div>
									<div class="radio-container">
										<input type="checkbox" name='areas' value="Clientes"
											id="clientes_modal"><label for="clientes"><span></span>Clientes</label>
									</div>
									<div class="radio-container">
										<input type="checkbox" name='areas' value="ITCIB" id="itcib_modal"><label
											for="itcib"><span></span>ITCIB</label>
									</div>
									<div class="radio-container">
										<input type="checkbox" name='areas'
											value="Global Customer Service" id="gcs_modal"><label
											for="gcs"><span></span>Global Customer Service</label>
									</div>
									<div class="radio-container">
										<input type="checkbox" name='areas' value="Global Product"
											id="global-product_modal"><label for="global-product"><span></span>Global
											Product</label>
									</div>
									
								</fieldset>
							</div>
							<div id="message_div">
								<span id="span_message"></span>
							</div>
						</div>
					</form>
					<button type="button" id="submit_edit_user_form">Aceptar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>	
		</div>
	</div>
</div>
</div>


<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="">
				<h2>Eliminar usuario</h2>
				<hr />
			</div>
			<div class="">
				<p>&iquest;Est&aacute; seguro que desea eliminar al usuario?
			</div>
			<div class="modal-footer">
				<button type="button" class="pink-btn" id="deleteUser">Eliminar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>
</div>