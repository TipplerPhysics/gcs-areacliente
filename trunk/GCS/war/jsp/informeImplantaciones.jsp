<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_coste">

	<h1>Pase a producci&oacute;n</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	<hr/>
	
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Pase a producci&oacute;n </span>
	</div>
	<form id="report-form" name="report-form" action="/InformeServlet"
				method="POST" novalidate="novalidate">
				
		
		<select id="informe_select_calendada">
			<option value="default">Tipo de subida</option>
			<option value="Calendada">Calendada</option>
			<option value="No Calendada">No calendada</option>
		</select>
		<select id="informe_select_anyo" name="anyo_sel">
				<c:choose>
					<c:when test="${empty anyos}">
						<option value="default">No hay informes</option>
					</c:when>
				<c:otherwise>
					<option value="default">Seleccionar</option>
					<c:forEach items="${anyos}" var="t">							
						<option value="${t}" >${t}</option>
					</c:forEach>
				</c:otherwise>
				</c:choose>
		</select>
		
		<select id="informe_select_mes">
			<option value="default">Primero selecciona a&ntildeo</option>
		</select>
				
		<select id="informe_select_dia" name="dia">
			<option value="default">Primero selecciona mes</option>
		</select>
		
		</form>

	
</div>