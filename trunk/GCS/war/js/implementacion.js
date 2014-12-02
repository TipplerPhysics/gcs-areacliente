function sendEmailSolicitud(){
	var $form = $('#send-email-solicitud-form');
	
	if($form.valid()) {
		var servicios = $form.data('servicios');
		var conectividades = $form.data('conectividades');
		var tipo_subida = $('#tipo_subida').val();
		var fecha_implantacion = $('#fecha_implantacion').val();
		
		var formURL = $form.attr("action");
		var $formData = $form.serialize();
		var postData= $formData+"&accion=solicitud&servicios="+ servicios+"&conectividades="+conectividades+"&tipo_subida="+tipo_subida+"&fecha_implantacion="+fecha_implantacion ;
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

$(function() {
	
	$('#check_all_implantaciones').on('change', function(e) {
		if($('#check_all_implantaciones:checked').length == 0) {
			$('tbody').find('input:checkbox').attr("checked", true);
			$('tbody').find('label').addClass('on');
		}
		else {
			$('tbody').find('input:checkbox').attr("checked", false);
			$('tbody').find('label').removeClass('on');
		}
	});
	
	$('#sendMailButton').on('click', function(e) {
		console.log("sendMail");
		
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
			alert("Seleccione una conectividad/servicio")
		}	
		
	});
	
	
/*	function loadEditModal(){
		var accion = $('#select_edit_action').val();
		
		var git = $('#row'+id).data('gestor-it');
		var gn = $('#row'+id).data('gestor-negocio');
		var client = $('#row'+id).data('cliente');
		
		if (accion=='proyecto'){
			$('#edit-action').modal('hide');
			$('#edit-project').modal({
				  remote: "../projectModal.do?git="+git+"&gn="+gn+"&client="+client+"&id="+id
				});
			$('#edit-project').modal('show');
			
		}else if (accion=='costes'){
			$('#edit-action').modal('hide');
			$('#edit-project').modal({
				  remote: "../costesByModal.do?project_id="+id
				});
			
			
			
			
		}else if (accion=='conectividad'){
			$('#edit-action').modal('hide');
			$('#new-conectividad').modal({
				  remote: "../loadConectivity.do?id="+id
				});
			$('#new-conectividad').modal('show');
		
			
			
		}else if (accion=='servicios'){
			$('#edit-action').modal('hide');
			$('#select-service').modal({
				  remote: "../projectService.do?id="+id
				});
			$('#select-service').modal('show');
		}
	}
	*/
});