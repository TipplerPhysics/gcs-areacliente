<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="gestion_coste">

	<h1>Pase a producci&oacute;n</h1>
	<!-- <button class="btn-atras" type="button">Atr&aacute;s</button>  -->
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	<hr/>
	
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Pase a producci&oacute;n </span>
	</div>
	<form id="report-form" name="report-form" action="/InformeServlet"
				method="POST" novalidate="novalidate">
		<select id="informe_select_anyo" class="long selected selectpicker" name="anyo">
				<c:forEach items="${Anyos}" var="anyo">		         	
					<option value="${anyo.value}">${anyo.value}</option>
				</c:forEach>
		</select>
		
		<select id="informe_select_mes" class="long selected selectpicker" name="mes">
				<c:forEach items="${Meses}" var="mes">		         	
					<option value="${mes.value}">${mes.value}</option>
				</c:forEach>
		</select>
		
		<select id="informe_select_dia" class="long selected selectpicker" name="dia">
				<c:forEach items="${Dias}" var="dia">		         	
					<option value="${dia.value}">${dia.value}</option>
				</c:forEach>
		</select>
		
		<select id="tipo_subida" class="long selected selectpicker" name="tipo">
				<c:forEach items="${Tipo}" var="tipo">		         	
					<option value="${tipo.value}">${tipo.value}</option>
				</c:forEach>
		</select>
		</form>
		
		<div id="ultimo_informe">
			<!--Preview del último informe-->
		
		</div>
	
</div>