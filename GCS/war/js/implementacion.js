function sendEmailSolicitud(){
	var $form = $('#send-email-solicitud-form');
	
	if($form.valid()) {
		var servicios = $form.data('servicios');
		var conectividades = $form.data('conectividades');
		var tipo_subida = $('#tipo_subida').val();
		var fecha_implantacion_calendada = $('#fecha_implantacion_calendada').val();
		var fecha_implantacion_no_calendada = $('#fecha_implantacion_no_calendada').val();
		
		var formURL = $form.attr("action");
		var $formData = $form.serialize();
		var postData= $formData+"&accion=Solicitado&servicios="+ servicios+"&conectividades="+conectividades+"&tipo_subida="+tipo_subida+"&fecha_implantacion_calendada="+fecha_implantacion_calendada+"&fecha_implantacion_no_calendada="+fecha_implantacion_no_calendada ;
		$.ajax(			
			{
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					if (data.success=="true"){
						$form.hide();
						$('#span_message_modal').html('La solicitud de implantaci&oacute;n se ha registrado de manera correcta.');
						$('.modal-footer').hide();
						$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
						setTimeout(function() { 
							resetForm($form);
							$('#send-email-implantacion').modal('hide');
							
							location.reload();
						}, 2000);
					}
				}
			});
	}
}

function sendEmailConfirmacion(){
	var $form = $('#send-email-confirmacion-form');
	
	var servicios = $form.data('servicios');
	var conectividades = $form.data('conectividades');	
	
	var formURL = $form.attr("action");
	var $formData = $form.serialize();
	var postData= $formData+"&accion=Confirmado&servicios="+ servicios+"&conectividades="+conectividades;
	$.ajax(			
		{
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				if (data.success=="true"){
					$form.hide();
					$('#span_message_modal').html('La confirmaci&oacute;n de implantaci&oacute;n se ha registrado de manera correcta.');
					$('.modal-footer').hide();
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
					setTimeout(function() { 
						resetForm($form);
						$('#send-email-implantacion').modal('hide');
						
						location.reload();
					}, 2000);
				}
			}
		});	
}

function sendEmailProduccion(){
	var $form = $('#send-email-produccion-form');
	
	var servicios = $form.data('servicios');
	var conectividades = $form.data('conectividades');	
	
	var formURL = $form.attr("action");
	var $formData = $form.serialize();
	var postData= $formData+"&accion=Produccion&servicios="+ servicios+"&conectividades="+conectividades;
	$.ajax(			
		{
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				if (data.success=="true"){
					$('#send-email-implantacion').modal('hide');
					
					$('#redirect-informe').modal({
						  remote: "../sendMailImplantacion.do?informe=OK"
					});
					$('#redirect-informe').modal('show');
				}
			}
		});	
}

function sendEmailProduccion(){
	var $form = $('#send-email-produccion-form');
	
	var servicios = $form.data('servicios');
	var conectividades = $form.data('conectividades');	
	
	var formURL = $form.attr("action");
	var $formData = $form.serialize();
	var postData= $formData+"&accion=Produccion&servicios="+ servicios+"&conectividades="+conectividades;
	$.ajax(			
		{
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				if (data.success=="true"){
					$('#send-email-implantacion').modal('hide');
					
					$('#redirect-informe').modal({
						  remote: "../sendMailImplantacion.do?informe=OK"
					});
					$('#redirect-informe').modal('show');
				}
			}
		});	
}


function showInforme() {
	location.href="../dashboard/informeImplantacion.do";
}

function reloadImplantaciones() {
	location.reload();
}


$(function() {
	
	$('.modifHolder').slideUp(0);
	
	$('.dropbutton').on('click',function(e){
		var id = $(this).data('id');
		
		var adf = $('#line'+id).css('overflow');
		
		if ($('#line'+id).hasClass('oculto')){
			$('#line'+id).slideDown();
			$('#line'+id).removeClass('oculto');

		}else{
			var adasdfasdff = $('#line'+id).css('overflow');
			$('#line'+id).slideUp();
			$('#line'+id).addClass('oculto');			
		}
		
	});
	
	$('.subidaModifImp').on('click', function(e) {
		
		var link = $(this);
		var id = $(this).data('id');
		$('#line'+id).slideUp();
		$('#line'+id).addClass('oculto');	
		var tipo = $(this).data('tipo');
		var $form = $("#updateimp"+id);
		
		
		if($form.valid()){			
			
			var postData = $form.serialize() + "&accion=modif&id="+id+"&tipo="+tipo;
			var formURL = "/implantacionServlet?";
			$.ajax(
			{
			  url : formURL,
			  type: "GET",
			  data : postData,
			  success:function(data, textStatus, jqXHR) 
			  {
					//TODO
				  if (data.success==("true")){
					  
					  
					}else{
						$('#span_message_modal').html(data.error);

					}
			  }
			},'html');
		}
	});
	
	$('#check_all_implantaciones').on('change', function(e) {
		if($('#check_all_implantaciones').checkbox('isChecked2')) {
			$('tbody').find('input:checkbox').checkbox('check');
			$('#sendMailButton').attr("disabled", false);
		}
		else {
			$('tbody').find('input:checkbox').checkbox('uncheck');	
			$('#sendMailButton').attr("disabled", true);
		}
	});
	
	$('input:checkbox.inner').on('change', function(e) {
		if($(this).checkbox('isChecked2')) {
			$('#sendMailButton').attr("disabled", false);
		}
		else {
			var checkedCounter = 0;
			$('input:checkbox.inner').each(function() {
				if($(this).checkbox('isChecked2')) {
					checkedCounter++;
				}
			});
			if(checkedCounter == 0) {
				$('#sendMailButton').attr("disabled", true);
				$('#check_all_implantaciones').checkbox('uncheck');
			}
			else {
				$('#check_all_implantaciones').checkbox('uncheck');
			}
		}
	});
	
	$('#sendMailButton, #generarInformeButton').on('click', function(e) {
		
		var servicios = "";
		var conectividades = "";
		
		$('tbody').find('input:checkbox.inner').each(function() {
			if($(this).checkbox('isChecked2')) {
				var trow = $(this).closest('tr');
				var servicioId = trow.data('servicio-id');
				if(servicioId != null && servicioId != "") {
					servicios = servicios + servicioId + ",";
				}
				var conectividadId = trow.data('conectividad-id');
				if(conectividadId != null && conectividadId != "") {
					conectividades = conectividades + conectividadId + ",";
				}	
			}
		});

		if((servicios != "") || (conectividades != "")) {
			$('#send-email-implantacion').modal({
				  remote: "../sendMailImplantacion.do?servicios="+servicios+"&conectividades="+conectividades
				});
			$('#send-email-implantacion').modal('show');
		}
		else {
			alert("Seleccione una conectividad/servicio");
		}	
		
	});
	
});