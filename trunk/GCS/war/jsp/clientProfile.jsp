<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<div class="profile_cont" id="user_profile">
	<h1>Ficha Cliente</h1>
	<hr/>
	<div class="summary">
		<div class="img_div">
		<c:choose>
			<c:when test="${cliente.logo_url eq ''}">
				<img src="http://dummyimage.com/225x145/000/ffffff.png&text=Client+logo" alt="img_logo">
			</c:when>
			<c:otherwise>
				<img src="${cliente.logo_url}" alt="cliente logo">
			</c:otherwise>
		</c:choose>
		</div>
		<div>
			<ul>
				<li>En producción:</li>
				<li>En Implementación:</li>
				<li>Países:</li>
				<li>Producto:</li>
				<li>Proyecto:</li>
			</ul>
		</div>
	</div>
	<div class="table">
		<table style="width:100%">
			<tr class="header">
			  <td>PRODUCTO</td>
			  <td>CONECTIVIDAD</td> 
			  <td>PAÍS</td>
			  <td>FORMATO FICHERO</td>
			  <td>SERVICIO</td>
			  <td>ESTADO</td>
			  <td>CODIGO DE PROYECTO</td>
			</tr>
			
		</table>
	</div>


</div>