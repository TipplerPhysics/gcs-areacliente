<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div class="gestion_coste">
<h1>Pase a producci&oacute;n</h1>
	<!-- <button class="btn-atras" type="button">Atr&aacute;s</button>  -->
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
	<hr/>

	<!--<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Pase a producci&oacute;n </span>
	</div>-->
	<br clear="all">
	
		<form id="new-service-form" name="new-service-form" action="/serviceServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl">Seleccione a&ntilde;o<span class="required-asterisk">*</span>:</span>
							<div class="input">
							 <select class="selectpicker selected" name="anyos" data-en="this.options[this.selectedIndex].value" id="anyo-pruebas" data-live-search="true" onchange="cargaMeses()">
                            
                                <c:choose>
                                        <c:when test="${empty informes}">
                                            <option value="default">Seleccionar</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="default">Seleccionar</option>
                                            <c:forEach items="${informes}" var="i">                            
                                                <option value="${i.anyo_implantacion}" data-informeid="${i.key.id}">${i.anyo_implantacion}</option>
                                            </c:forEach>
                                        </c:otherwise>
                                </c:choose>
                            </select>
							</div>
						</div>
					<br clear="all">
					<br/>
					<div class="form-field">
                        <span class="lbl">Seleccione mes:<span class="required-asterisk">*</span>:</span>
                        <div class="input">
                        
                            <select class="selectpicker selected" name="meses" data-en="this.options[this.selectedIndex].value" id="mes-pruebas" data-live-search="true" onchange="cargaDias()">
                            
                                
                                       
                                        
                                            <option value="default">Seleccionar</option>
                                           
                                            <c:forEach items="${informes}" var="i">                            
                                                <option value="${i.mes_implantacion}" data-informeid="${i.key.id}">${i.mes_implantacion}</option>
                                            </c:forEach>
                                        
                                		
                            </select>
                        </div>
					</div>	
			</div>
			<div class="form-field-divider right">
			<div class="form-field">
                        <span class="lbl">Seleccione tipo de subida:<span class="required-asterisk">*</span>:</span>
                        <div class="input">
                        
                            <select class="selectpicker selected" name="tipos" data-en="this.options[this.selectedIndex].value" id="tipo-pruebas" data-live-search="true">
                            
                                
                                        
                                         
                                            <option value="default">Seleccionar</option>
                                            
                                            <c:forEach items="${informes}" var="i">                            
                                                <option value="${i.tipo_subida}" data-informeid="${i.key.id}">${i.tipo_subida}</option>
                                            </c:forEach>
                                        
                               
                            </select>
                        </div>
		</div><br clear="all"><br/>
			<div class="form-field">
                        <span class="lbl">Seleccione d&iacute;a:<span class="required-asterisk">*</span>:</span>
                        <div class="input">
                        
                            <select class="selectpicker selected" name="dias" data-en="this.options[this.selectedIndex].value" id="dia-pruebas" data-live-search="true" onchange="cargaTipo()">
                            
                                
                                        
                                        
                                            <option value="default">Seleccionar</option>
                                        
                                            <c:forEach items="${informes}" var="i">                            
                                                <option value="${i.dia_implantacion}" data-informeid="${i.key.id}">${i.dia_implantacion}</option>
                                            </c:forEach>
                                        
                                
                            </select>
                        </div>
		</div>
		
			
			
			</div>	
		</div>			
</form>

	<div id="message_div_informe" class="message_div" style='margin-bottom:10px;'>
				<span id="span_message_informe" class="span_message"></span>
	</div>
		
		<div id="ultimo_informe">
			<!--Preview del último informe-->
		
		</div>		
</div>
