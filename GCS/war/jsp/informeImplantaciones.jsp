<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="modal_ajax" id="nuevo_servicio_modal"> 
		
				<div class="edit-user-form-holder">
				<form id="new-service-form" name="new-service-form" action="/serviceServlet" method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field">
							<span class="lbl">Cod. de proyecto<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select class="selectpicker selected" name="cod_proyecto" id="cod_proyecto" required aria-required="true">
									<option value="default">Seleccionar</option>	
									<c:forEach items="${proyectos}" var="p">	
										<option value="${p.key.id}">${p.cod_proyecto}</option>
									</c:forEach>								
								</select>
							</div>
						</div>
						</div>
						</div>
		</form>
			
			
<br/>
		
			

</div>
</div>