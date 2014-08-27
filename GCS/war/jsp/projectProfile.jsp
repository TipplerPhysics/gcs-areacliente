<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<div class="profile_cont" id="user_profile">
	<h1>Ficha Cliente</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
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
				<li>Código de proyecto:</li>
				<li>Gestor de negocio:</li>
				<li>Gestor IT:</li>
				<li>Estado:</li>
				<li>Fecha implantación:</li>
			</ul>
		</div>
	</div>
	

</div>