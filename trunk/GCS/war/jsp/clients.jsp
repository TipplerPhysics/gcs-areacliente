<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<div class="clients">

	<img class="users_title_icon" src="../img/user.png"><h1>Listado de Clientes</h1>
	<hr/>
	<div class="leyenda_clientes">
		<div class="color alta"></div><span>Criticidad Alta</span>
		<div class="color media"></div><span>Criticidad Media</span>
		<div class="color baja"></div><span>Criticidad Baja</span>
		<div class="selects">
		<span>Tipo de Criticidad</span>
		<select class="selectpicker" id="tip_crit">
			<option value="0">Todas</option>
			<option value="Alta">Alta</option>
			<option value="Media">Media</option>
			<option value="Baja">Baja</option>
		</select>
		<span>Tipo de Cliente</span>
		<select class="selectpicker" id="tip_client">
			<option value="0">Todos</option>
			<option value="CIB">CIB</option>
			<option value="BEC">BEC</option>
		</select>
	</div>	
	</div>
	<div class="search_div">
	<!-- 
		<c:if test="${sessionScope.permiso <= 6}">
				<button onclick="location.href = './dashboard/gestionDemanda.do';" id="btn_gestion_demanda">Gestión de demanda<img src="../img/gestion.png"></button>  
			
				 
		</c:if>
	 -->
	
		<button onclick="location.href = './dashboard/gestionCliente.do';" id="btn_alta_cliente">Gestión clientes<img src="../img/new-user-white.png"></button> 

		<input type="text" class="long" name="buscador_cliente" id="buscador_cliente" placeholder="Introduzca cliente a buscar">
	</div>
	
	
	
	
	<div class="abc">
		<div id="abc_child_scroll" class="scroll_hidden">
			 <c:forEach items="${alphabet}" var="char">
			 	<c:choose>
					<c:when test="${fn:indexOf(letras,char)>0}">
		 				<a id="letra_${char}" href="#${char}_anchor"><span class="active">${char}</span></a>
		 			</c:when>
		 			<c:otherwise>
		 				<a id="letra_${char}"><span class="inactive">${char}</span></a>
		 			</c:otherwise>
		 		</c:choose>
			 </c:forEach>			
		</div>
		<div id="abc_child">
			 <c:forEach items="${alphabet}" var="char">
			 	<c:choose>
					<c:when test="${fn:indexOf(letras,char)>0}">
		 				<a id="letra_${char}" href="#${char}_anchor"><span class="active">${char}</span></a>
		 			</c:when>
		 			<c:otherwise>
		 				<a id="letra_${char}"><span class="inactive">${char}</span></a>
		 			</c:otherwise>
		 		</c:choose>
			 </c:forEach>
			
		</div>
	</div>
	<div class="clients_container">
	
		<c:set var="letra_anterior" value="0" />
		<c:forEach items="${clientes}" var="c">
			<c:set var="letra" value="${fn:substring(c.nombre, 0, 1)}" />
			
			<c:choose>
				<c:when test="${letra ne letra_anterior}">
					<c:choose>
						<c:when test="${letra_anterior ne '0'}">
							</div>
						</c:when>
					</c:choose>
					<div class="l_anchor" id="${letra}_anchor"></div>
					<div class="letter_anchor">
						<span>${letra}</span>	
						<hr/>
					</div>
					<div class="clientes_letra">
				</c:when>
			</c:choose>
				
				<div class="client_box crit_${c.criticidad} tipo_${c.tipo}" data-id="${c.key.id}">
					<p>${c.nombre}</p>
				</div>
			<c:set var="letra_anterior" value="${letra}" />	
		</c:forEach>
		</div>
	</div>	
</div>

<script type="text/javascript">
  $(function() {
       $('.selectpicker').selectpicker();
  });
</script>