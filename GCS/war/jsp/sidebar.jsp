<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="sidebar">
	<div class="side-menu">
		<ul>
			<c:if test="${sessionScope.permiso == 1}">
				<li><a href="/admin/users.do"><span class="usuarios">Usuarios</span></a></li>
			</c:if>
			
			<!-- <li><a href="/dashboard/gestionDemanda.do"><span class="demanda">Demanda</span></a></li>  -->
			
			<c:if test="${sessionScope.permiso == 1}">
				<li><a href="/dashboard/gestionImplantaciones.do"><span class="informes">Registro</span></a></li>
				<li><a href="/admin/auditoria.do"><span class="logs">Auditoría</span></a></li>	
				<li><a href="/admin/informes.do"><span class="demanda">Informes</span></a></li>
				<li><a href="/admin/catalogo.do"><span class="catalogo">Catalogo S.</span></a></li>
			</c:if>		
		</ul>
	</div>
</div>