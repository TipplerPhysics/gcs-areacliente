<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<div class="profile_cont" id="user_profile">
	<h1>Ficha Cliente</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	<hr/>
	<div class="breadcrumbs">
		<span onclick="window.location.href='../' ">Home</span> > <span> Ficha cliente </span>
	</div>
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
				<li>Cliente: <span>${cliente.nombre}</span></li>
				<li>IDGLOBAL: <span>${cliente.clientId}</span></li>
				<li>Referencia Global: <span>${cliente.ref_global}</span></li>
				<li>Tipo: <span>${cliente.tipo}</span></li>
				<li>Criticidad: <span>${cliente.criticidad}</span></li>
			</ul>
			
		</div>
	</div>
	<h2>Proyectos asociados</h2>
	<button id="newProjectButton" onclick="location.href = './dashboard/gestionProyecto.do';">
				Gesti&oacute;n Proyecto<a class="proyecto_span_fixed"></a>
			</button>	
	<hr/>
	<div class="table">
		<table style="width:100%">
			<tr class="header">
			  <td>Nombre proyecto</td>
			  <td>Tipo de proyecto</td> 
			  <td>Gestor IT</td>
			  <td>Gestor Negocio</td>
			  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>	
			<c:forEach items="${projects}" var="p">
				<tr class="body">
			 		 <td>${p.cod_proyecto}</td>
			 		 <td>${p.tipo}</td>
			 		 <td>${p.gestor_it_name}</td>
			 		 <td>${p.gestor_negocio_name}</td>
			 		 <td><a class="mas" name="${p.key.id}"></a></td>
			</c:forEach>		
		</table>
	</div>
</div>