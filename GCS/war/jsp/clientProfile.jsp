<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
				<li>En producci&oacuten: <span>Unknow</span></li>
				<li>En implementaci&oacuten: <span>Unknow</span></li>
				<li>Pa&iacuteses: <span>
					<c:forEach items="${cliente.paises}" var="pais">
						${pais}&nbsp
					</c:forEach>
				</span></li>
				<li>Producto:<span><ul>
					<c:forEach items="${productos}" var="producto">
						<li>${producto}</li>
					</c:forEach>
				</ul></span></li><c:forEach items="${productos}"><br /></c:forEach>
				<li>Proyecto:<br />&nbsp&nbsp&nbsp&nbsp&nbsp <span>
					<c:forEach items="${projects}" var="proyecto">
						<a href='../'>${proyecto.cod_proyecto}</a>&nbsp
					</c:forEach>
				</span></li>
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
			  <td>Producto</td>
			  <td>Conectividad</td> 
			  <td>Pa&iacutes</td>
			  <td>Formato fichero</td>
			  <td>Servicio</td>
			  <td>Estado</td>
			  <td>Codigo proyecto</td>
			  
			</tr>	
			<c:forEach items="${projects}" var="p">
				<tr class="body">
			 		 <td>${p.producto}</td>
					 <td>${p.conectividad}</td>
			 		 <td>
			 		 	<c:forEach items="${cliente.paises}" var="pais">
							${pais}&nbsp
						</c:forEach>
					 </td>
			 		 <td>un</td>
			 		
			 		 <td>
			 		 	<c:forEach items="${servicios}" var="servicio">
							${servicio.cod_servicio}&nbsp
						</c:forEach>
					 </td>
			 		 <td>${p.servicio}</td>
			 		 <td><a href='../'>${p.cod_proyecto}</a>&nbsp</td>
			 		 
			 		 <!--<td><a class="mas" name="${p.key.id}"></a></td>-->
			</c:forEach>		
		</table>
	</div>
</div>