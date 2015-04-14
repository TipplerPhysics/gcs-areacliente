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
	<span>Aqu&iacute puedes ejecutar los informes predefinidos del &aacuterea.</span>
		<div id='filtro-informe-variable' >
			<span class="lbl">Informe a generar<span class="required-asterisk">*</span>:</span>
			<select id='variableInf' class="selectpicker selected" name='variableInf'>
				<option value='Cartera'>Cartera/Paises </option>
				<option value='trabajo'>Carga de trabajo </option>
				<option value='implementaciones'> Estado de implementaciones </option>
				<option value=''> Consulta avanzada </option>
			</select>
			
			<button style="margin-bottom:1%;" onclick="verinforme();">  Ver  </button>
		</div>
		
	<span class="title">Consulta din&aacutemica de datos </span>	
	<hr class="titleBorder"/>
	<span> Mediante los siguientes filtros, puedes combinarlos para hacer una consulta online de los datos de clientes.</span>
	<div id="chart_div" style="width:400; height:300"></div>			
		
	</div>
	

   

	<iframe id="iframexls" class="hidden"  width="100%" height="1600px"></iframe>
</div>