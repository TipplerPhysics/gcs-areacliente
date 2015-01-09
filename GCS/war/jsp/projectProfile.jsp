<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<div class="profile_cont" id="user_profile">
	<h1>Ficha Proyecto</h1>
	<span class="btn-atras" onclick="window.location.href='./clientProfile.do?id=${cliente.key.id}'"></span>
	<hr/>
	<div class="breadcrumbs">
		<span onclick="window.location.href='../' ">Home</span> > <span onclick="window.location.href='../clientProfile.do?id=${cliente.key.id}'"> Ficha cliente </span> > <span>Detalle Proyecto</span>
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
				<li><span class="title">Código de proyecto:</span><span class="info">${proyecto.cod_proyecto}</span></li>
				<li><span class="title">Gestor de negocio:</span><span class="info">${proyecto.gestor_negocio_name}</span></li>
				<li><span class="title">Gestor IT:</span><span class="info">${proyecto.gestor_it_name}</span></li>
				<li><span class="title">Estado:</span><span class="info">${conectserv.estado}</span></li>
				<li><span class="title">Fecha implantación:</span><span class="info">
				
				<c:set var="string" value="${proyecto.okNegocio}" />
				
				${fn:substring(string, 8, 11)}
				${fn:substring(string, 4, 8)}
				${fn:substring(string, 30, 34)}
				</span></li>
			</ul>
			<c:if test="${proyecto.url_doc_google_drive != null}">
				<a class="drive-link" href="${proyecto.url_doc_google_drive}" target="_blank">Abrir Documentación - Google Drive ></a>
			</c:if>
		</div>
	</div>
	

</div>