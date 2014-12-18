<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="informes">
	
	<h1>Pase a producci&oacute;n</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	<hr/>
	
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Pase a producci&oacute;n </span>
	</div>
	
	<form id="report-form" name="report-form" action="/InformeServlet"	method="POST" novalidate="novalidate">
		<div class="form-container">
			<div class="form-field-divider left">
				<div class="form-field">
				
					<span class="lbl">Seleccione tipo de subida:<span class="required-asterisk">*</span></span>
                
                    <div class="input">
                    		<select id="informe_select_calendada" class="selectInforme">
								<option value="default">Tipo de subida</option>
								<option value="Calendada">Calendada</option>
								<option value="No calendada">No calendada</option>
							</select>
						
					</div>
				</div>
				<div class="form-field">
					<span class="lbl">Seleccione a&ntilde;o:<span class="required-asterisk">*</span></span>
						<div class="input">
							<select id="informe_select_anyo" class="selectInforme"  name="anyo_sel">
								<option value="default">Seleccione primero tipo subida</option>
							</select>
						</div>
				</div>	
	
				
		</div>
		<div class="form-field-divider right">

			<div class="form-field">
	            <span class="lbl informe">Seleccione mes:<span class="required-asterisk">*</span></span>
	                <div class="input">
						<select id="informe_select_mes" class="selectInforme">
							<option value="default">Primero selecciona a&ntildeo</option>
						</select>
					</div>
			</div>	
			<div class="form-field">
                <span class="lbl informe">Seleccione d&iacute;a:<span class="required-asterisk">*</span></span>
                    <div class="input">
						<select id="informe_select_dia" name="dia" class="selectInforme">
							<option value="default">Primero selecciona mes</option>
						</select>
					</div>
			</div>
		</div>
		 <a id="down_btn"> Descargar</a>
	</div>
	
<iframe id="iframepdf" width="100%" height="800px"></iframe>





</form>

</div>	

