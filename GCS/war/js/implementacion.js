function sendEmailSolicitud(){
	var $form = $('#send-email-solicitud-form');
	
	if($form.valid()) {
		var servicios = $form.data('servicios');
		var conectividades = $form.data('conectividades');
		var tipo_subida = $('#tipo_subida').val();
		var fecha_implantacion = $('#fecha_implantacion').val();
		
		var formURL = $form.attr("action");
		var $formData = $form.serialize();
		var postData= $formData+"&accion=Solicitado&servicios="+ servicios+"&conectividades="+conectividades+"&tipo_subida="+tipo_subida+"&fecha_implantacion="+fecha_implantacion ;
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

function sendEditImplementacion(){
	
	var id = $(this).data('id');
	var $form = $("#updateimp"+id);
	
	if($form.valid()){			
		
		var postData = $form.serialize() + "&accion=update&id="+id;
		var formURL = $form.attr("action");
		$.ajax(
		{
		  url : formURL,
		  type: "GET",
		  data : postData,
		  success:function(data, textStatus, jqXHR) 
		  {
				//TODO
			  if (data.success==("true")){
					if ($('.edit-user-form-holder').height()<190){
						$('.edit-user-form-holder').height($('.edit-user-form-holder').height()+35);
					}
					$form.find('.form-container').find('div:not(#message_div_modal)').hide(0);
					
					$form.find('#span_message_modal').html('La implementaci&oacuten ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('#modal-footer_submit').css('display','none');
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");;

					setTimeout(function() { 
						resetForm($form);
						location.reload();
					}, 1500);
				}else{
					$('#message_div_modal').removeClass("success").addClass("error");
					if ($('.edit-user-form-holder').height()<190){
						$('.edit-user-form-holder').height($('.edit-user-form-holder').height()+35);
					}
					$('#span_message_modal').html(data.error);
					$('#message_div_modal').css('display','block');
				}
		  }
		},'html');
	}
}

function showInforme() {
	location.href="../dashboard/informeImplantacion.do";
}

function reloadImplantaciones() {
	location.reload();
}

$(function() {
	
	$('#check_all_implantaciones').on('change', function(e) {
		if($('#check_all_implantaciones').checkbox('checked')) {
			$('tbody').find('input:checkbox').checkbox('uncheck');	
			$('#sendMailButton').attr("disabled", true);
		}
		else {
			$('tbody').find('input:checkbox').checkbox('check');
			$('#sendMailButton').attr("disabled", false);
		}
	});
	
	$('input:checkbox.inner').on('change', function(e) {
		if($(this).checkbox('checked')) {
			$('#sendMailButton').attr("disabled", true);
			$('#check_all_implantaciones').checkbox('uncheck');
		}
		else {
			$('#sendMailButton').attr("disabled", false);			
		}
	});
	
	$('#sendMailButton, #generarInformeButton').on('click', function(e) {
		
		var servicios = "";
		var conectividades = "";
		
		$('tbody').find('input:checkbox:checked').each(function() {
			var trow = $(this).closest('tr');
			var servicioId = trow.data('servicio-id');
			if(servicioId != null && servicioId != "") {
				servicios = servicios + servicioId + ",";
			}
			var conectividadId = trow.data('conectividad-id');
			if(conectividadId != null && conectividadId != "") {
				conectividades = conectividades + conectividadId + ",";
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