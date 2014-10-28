<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.auditoria .btn-atras, .gestion_cliente .btn-atras {
	float: right;
	margin-top: 20px;
}

.auditoria .form-field.historico {
	padding-bottom:30px;
}
</style>
<div class="auditoria">
<h1>Auditor&iacute;a</h1>
<span class="btn-atras" onclick="window.location.href='../../'"></span>
<hr />
<div class="breadcrumbs"> <span onclick="window.location.href='../../' ">Home</span> > <span> Auditor&iacute;a </span> </div>
<div class="newUserbox">
</div>

<div class="form-field historico">
<span class="lbl">Hist&oacute;rico:</span>
	<select id="historico" class="long selected selectpicker" name="historico">
		<option selected value="default">Seleccionar</option>
		<option value="lastweek">Última semana</option>
		<option value="lastmonth">Último mes</option>
		<option value="lastthreemonths">Últimos tres meses</option>
	</select>
	
	  <button id="excel_btn" onclick="window.location.href='../../usersServlet?accion=xls'"> Descargar Tabla<span class="excel_span"></span> </button>
	
</div>

<div>
  <div>
    <div class="table-responsive usersTable">
      <table class="table">
        <thead>
          <tr>
            <th><span class="table-title">Fecha</span></th> 
            <th><span class="table-title">Nombre</span></th>
            <th><span class="table-title">Acción</span></th>
            <th style="width: 110px;">&nbsp;</th>
          </tr>
          <tr>
            <th class="search-th"><input class="search col0"></th>
            <th class="search-th"><input class="search col1"></th>
            <th class="search-th"><input class="search col2"></th>
            <th style="width: 110px;">&nbsp;</th>
          </tr>
        </thead>
        <tbody id="myTable" cellspacing="0">
          <c:choose>
            <c:when test="${empty userList}">
              <tr>
                <td><span>No existen revisiones.</span></td>
              </tr>
            </c:when>
            <c:otherwise>
              <c:forEach items="${userList}" var="user">
                <tr class="valid-result" id="row${user.key.id}" data-area="${user.areas}" data-permiso="${user.permiso}" data-mail="${user.email}" data-dto="${user.departamento}">
                  <td><span>${user.nombre}</span></td>
                  <td><span>${user.apellido1}</span></td>
                  <td><img class="vs" src="../img/vs.png"><a class="lapiz" name="${user.key.id}" href="../userModal.do"	id="lapiz${user.key.id}" data-toggle="modal" data-target="#edit-user" ></a><a class="papelera" name="${user.key.id}" data-toggle="modal"	data-target="#confirm-delete" id="papelera${user.key.id}"></a></td>
                </tr>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>
    </div>
    <div class="col-md-12 text-center">
      <ul class="pagination" id="myPager">
      </ul>
      <span class="pagesummary"></span> </div>
  </div>
</div>
