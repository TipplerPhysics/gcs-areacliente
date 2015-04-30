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
					<option value=''> Consulta avanzada </option>
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
	<div id="chart_div" style="width:400; height:300"></div>			
		
	</div>
	

   

	<iframe id="iframexls" class="hidden"  width="100%" height="1600px"></iframe>
</div>