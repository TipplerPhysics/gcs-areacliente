<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="clients">

	<img class="users_title_icon" src="../img/user.png"><h1>Listado de Clientes</h1>
	<hr/>
	<div class="leyenda_clientes">
		<div class="color alta"></div><span>Criticidad Alta</span>
		<div class="color media"></div><span>Criticidad Media</span>
		<div class="color baja"></div><span>Criticidad Baja</span>
	</div>
	<c:if test="${sessionScope.permiso <= 3}">
			<button onclick="location.href = './dashboard/gestionDemanda.do';" id="btn_gestion_demanda">Gestión de demanda<img src="../img/gestion.png"></button>  
		<!-- 	<button  id="btn_gestion_demanda">Gestión de demanda<img src="../img/gestion.png"></button> -->
			 
	</c:if>
	<c:if test="${sessionScope.permiso <= 3}">
			<button onclick="location.href = './dashboard/gestionCliente.do';" id="btn_alta_cliente">Alta nuevo cliente<img src="../img/new-user-white.png"></button> 
	</c:if>
	
	<div>
		<span>Tipo de Criticidad</span>
		<select class="selectpicker">
			<option value="0">Todas</option>
			<option value="3">Alta</option>
			<option value="2">Media</option>
			<option value="1">Baja</option>
		</select>
		<span>Tipo de Cliente</span>
		<select class="selectpicker">
			<option value="0">Todos</option>
		</select>
	</div>	
	<div class="abc">
		<div>
			<a href="#"><span>A</span></a>
			<a href="#"><span>B</span></a>
			<a href="#"><span>C</span></a>
			<a href="#"><span>D</span></a>
			<a href="#"><span>E</span></a>
			<a href="#"><span>F</span></a>
			<a href="#"><span>G</span></a>
			<a href="#"><span>H</span></a>
			<a href="#"><span>I</span></a>
			<a href="#"><span>J</span></a>
			<a href="#"><span>K</span></a>
			<a href="#"><span>L</span></a>
			<a href="#"><span>M</span></a>
			<a href="#"><span>N</span></a>
			<a href="#"><span>Ñ</span></a>
			<a href="#"><span>O</span></a>
			<a href="#"><span>P</span></a>
			<a href="#"><span>Q</span></a>
			<a href="#"><span>R</span></a>
			<a href="#"><span>S</span></a>
			<a href="#"><span>T</span></a>
			<a href="#"><span>U</span></a>
			<a href="#"><span>V</span></a>
			<a href="#"><span>W</span></a>
			<a href="#"><span>X</span></a>
			<a href="#"><span>Y</span></a>
			<a href="#"><span>Z</span></a>
		</div>
	</div>
		
</div>

<script type="text/javascript">
  $(function() {
       $('.selectpicker').selectpicker();
  });
</script>