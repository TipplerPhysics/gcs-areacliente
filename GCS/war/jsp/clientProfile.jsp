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
				<li>En producci&oacuten: <span>
				<c:choose>
					<c:when test="${enproduccion}">
						S&iacute
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
				
</span></li>
				<li>En implementaci&oacuten: <span>
								<c:choose>
					<c:when test="${enimplementacion}">
						S&iacute
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
				</span></li>
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
						<a href='../projectProfile.do?id=${proyecto.key.id}&idCli=${cliente.key.id}'>${proyecto.cod_proyecto}</a>&nbsp
					</c:forEach>
				</span></li>
			</ul>
			
		</div>
	</div>
<h2> Conectividades y servicios asociados</h2>
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
			<tr class='body'>	
			<c:forEach items="${servicios}" var="servicio">
				<c:forEach items="${projects}" var="pro">
					<c:choose>
						<c:when test="${pro.key.id==servicio.id_proyecto}">
							<tr class="valid-result" id="row${servicio.key.id}">
					 		 <td>${pro.producto}</td>
							 <td></td>
					 		 <td>
					 		 	<c:forEach items="${cliente.paises}" var="pais">
									${pais}&nbsp
								</c:forEach>
							 </td>
					 		 <td>${servicio.cod_servicio}</td>
					 		 <td></td>
					 		 <td>${servicio.estado} con ${servicio.estadoSubida}</td>
					 		 <td><a href='../projectProfile.do?id=${pro.key.id}&idCli=${cliente.key.id}'>${pro.cod_proyecto}</a></td>
					 		</tr>
						</c:when>
					</c:choose>	
				</c:forEach>
			</c:forEach>
			<c:forEach items="${conectividades}" var="conectividad">
				<c:forEach items="${projects}" var="pro">
					<c:choose>
						<c:when test="${pro.key.id==conectividad.key_proyecto}">
							<tr class="valid-result" id="row${conectividad.key.id}">
					 		 <td>${pro.producto}</td>
							 <td>${pro.conectividad}</td>
					 		 <td>
					 		 	<c:forEach items="${cliente.paises}" var="pais">
									${pais}&nbsp
								</c:forEach>
							 </td>
					 		 <td></td>
					 		 <td></td>
					 		 <td>${conectividad.estado} </td>
					 		 <td><a href='../projectProfile.do?id=${pro.key.id}&idCli=${cliente.key.id}'>${pro.cod_proyecto}</a></td>
					 		</tr>
						</c:when>
					</c:choose>	
				</c:forEach>
			</c:forEach>		
			</tr>		
		</table>
	</div>
</div>