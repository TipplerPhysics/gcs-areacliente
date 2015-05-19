<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div id="informes">


	<h1>Informes</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
<hr />

	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Informes </span>
	</div>
	<hr style='visibility:hidden;'/>
	
	
	<div id='filter-box'>



	<span class="title"> Consulta informes del &aacuterea</span>	
	<hr class="titleBorder"/>
	<span>Aqu&iacute puedes generar los informes predefinidos del &aacuterea.</span>
		<div id='filtro-informe-variable' >
			<div id='informesfilt'>
				<span class="lbl">Informe a generar<span class="required-asterisk">*</span>:</span>
				<select id='variableInf' class="selectpicker selected" name='variableInf'>
					<option value='Paises'>Paises </option>
					<option value='Cartera'>Cartera </option>
					<option value='trabajo'>Carga de trabajo </option>
					<option value='implementaciones'> Estado de implementaciones </option>
					<option value='coste'> Costes </option>
				</select>
				
				<button style="margin-bottom:1%;" onclick="verinforme();">  Ver  </button>
			</div>
			<div id="fechas-cartera-filter" class='hidden'>
				<span class="lbl">Seleccione rango de fechas para procesar el informe:</span> 
				<input readonly class="selectpicker datepicker fromTo" data-target-id='hastaFilterInf' id='desdeFilterInf' name="fecha-desde" />
				<input readonly class="selectpicker datepicker" name="fecha-hasta" id='hastaFilterInf'/>	
			</div> 
			
			<div id='errorTime' class='hidden' style='
			
					display: block;
					padding: 5px 10px;
					margin: 0 0 10px;
					border-radius: 4px;
					border: 1px solid #ca0161;
					background-color: #f7e9e9;
					color: #c4136c;
					font-size: 1em;
					font-weight: normal;
			
			'>
			<span class='lbl'>Has seleccionado un periodo con demasiados valores</span>
			</div>
		</div>

				
		
	<span class="title">Consulta din&aacutemica de datos </span>	
	<hr class="titleBorder"/>
	<span> Mediante los siguientes filtros, puedes combinarlos para hacer una consulta online de los datos de clientes.</span>
	<div id="chart_div" style="width:400; height:300">
	<form id='formulariocomple' style='margin-top:1%;margin-bottom:3%;' action='/informeExcelServlet'>
			<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
			<select id="input_firstcase" class="selectpicker selected" name="cliente" multiple required aria-required="true" data-live-search="true" title='Seleccione'>
				<option value="default">Todos los clientes</option>
				<c:forEach items="${clients}" var="cliente">
					<option value="${cliente.clientId}">${cliente.nombre}</option>
				</c:forEach>
			</select>
			
			<select id="input_secase" class="selectpicker selected" multiple name="entidades" required aria-required="true" title='Seleccione'>
				<option value="proyectos">Proyectos</option>
				<option value="servicio">Servicios</option>
				<option value="conectividad">Conectividad</option>
				<option value="tipoCliente">Tipo de Cliente</option>
			</select>
			
			<select id="input_tricase" class="selectpicker selected" name="variables" multiple required aria-required="true" title='Seleccione'>
				<optgroup label="Proyecto">
				<option value="fechaInicio">Fecha inicio</option>
				<option value="implementacion">Tipo implementaci&oacuten</option>
				<option value="producto">Producto</option>
				<optgroup label="Servicio">
				<option value="fechaImplementacion">Fecha implantaci&oacuten</option>
				<option value="paises">Pa&iacutes</option>
				
				<option value="estadosServ">Estados</option>
				<optgroup label="Conectividad">
				<option value="estadosConect">Estados</option>
				
			</select>
			<button type='button' onclick="verinforme2();">  Descargar Excel </button>
	</form>
		
	</div>	
	
	
	
	<div id='tablacompleja'></div>

   

	<iframe id="iframexls" class="hidden"  width="100%" height="1600px"></iframe>
</div>