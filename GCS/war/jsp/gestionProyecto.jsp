<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="alta_proyecto">


<img class="users_title_icon" src="../img/user.png">
<h1>Gestion proyecto</h1>
<span class="btn-atras" onclick="window.location.href='../../'"></span>

<hr />

<div class="newUserbox">
	<button id="newUserButton">
		Nueva Proyecto<a class="demanda_span"></a>
	</button>
	<button id="excel_btn" onclick="window.location.href='../../usersServlet?accion=xls'">
		Descargar Tabla<a class="excel_span"></a>
	</button>


	<div class="new-user-form-holder">
		<form id="new-project-form" name="new-project-form" action="/projectServlet"
			method="POST" novalidate="novalidate">
			<div class="form-container">
				
				</div>
				<div id="message_div">
					<span id="span_message"></span>
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
						<th><span class="table-title">Fecha proyecto</span></th>
						<th><span class="table-title">Código proyecto</span></th>
						<th><span class="table-title">Cliente</span></th>
						<th><span class="table-title">Clasificación</span></th>
						<th><span class="table-title">Tipo</span></th>
						<th><span class="table-title">Coste</span></th>
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
					
				</tbody>
			</table>
		</div>
		<div class="col-md-12 text-center">
			<ul class="pagination" id="myPager"></ul>
			<span class="pagesummary"></span>
		</div>
	</div>
</div>


<div class="modal fade" id="edit-project" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="edit_user_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>


<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="">
				<h2>Eliminar proyecto</h2>
				<hr />
			</div>
			<div class="">
				<p>&iquest;Est&aacute; seguro que desea eliminar al proyecto?</p>
				
			</div>
			<div class="modal-footer">
				<button type="button" class="pink-btn" id="deleteProject">Eliminar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>
