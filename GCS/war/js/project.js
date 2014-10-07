function  sendEditProject(){
	var $form = $('#edit-project-form');
	var formURL = $form.attr("action");
	 var $formData = $form.serialize();
	 var postData= $formData+"&accion=update&id="+ id;
	 $.ajax(			
		{
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				if (data.success=="true"){
					$form.hide();
					$('#span_message_demanda_modal').html('El proyecto ha sido modificado de forma correcta.');
					$('.modal-footer').hide();
					$('#message_div_demanda_modal').css('display','block').removeClass("error").addClass("success");

					setTimeout(function() { 
						resetForm($form);
						location.reload();
					}, 1500);
				}else{
					$('#span_message_demanda_modal').html(data.error);
					$('#message_div_demanda_modal').css('display','block').removeClass("success").addClass("error");
				}
				
			}
		});
}

function modalCliente(){
	
	$('#edit_project_form_modal').data("id",id);	
	
	var $currentRow = $('#row'+id);
	var fecha_alta = $currentRow.attr('data-fecha-alta');
	var nombre = $currentRow.attr('data-nombre');
	var tipo = $currentRow.attr('data-tipo');
	var cliente = $currentRow.attr('data-cliente');
	var clasificacion = $currentRow.attr('data-clasificacion');
	var gestor_it = $currentRow.attr('data-gestor-it');
	var gestor_negocio = $currentRow.attr('data-gestor-negocio');
	var coste = $currentRow.attr('data-coste');
	
	var producto = $currentRow.attr('data-producto');
	var conectividad = $currentRow.attr('data-conectividad');
	var servicio = $currentRow.attr('data-servicio');
	
	var fecha_ini_valoracion = $currentRow.attr('data-fecha-ini-valoracion');
	var fecha_fin_valoracion = $currentRow.attr('data-fecha-fin-valoracion');
	
	var fecha_ini_viabilidad = $currentRow.attr('data-fecha-ini-viabilidad');
	var fecha_fin_viabilidad = $currentRow.attr('data-fecha-fin-viabilidad');
	
	
	$('#producto_modal').val(producto);
	$('#conectividad_modal').val(conectividad);
	$('#servicio_modal').val(servicio);
	
	$('#fecha_inicio_valoracion_modal').val(fecha_ini_valoracion);
	$('#fecha_fin_valoracion_modal').val(fecha_fin_valoracion);
	
	$('#fecha_inicio_viabilidad_modal').val(fecha_ini_viabilidad);
	$('#fecha_fin_viabilidad_modal').val(fecha_fin_viabilidad);
	
	$('#fecha_alta_cliente_modal').val(fecha_alta);
	$('#project_name_modal').val(nombre);
	$('#tipo_modal').val(tipo);
	$('#input_cliente_modal').val(cliente);
	$('#clasificacion_modal').val(clasificacion);
	$('#gestor_it_modal').val(gestor_it);
	$('#gestor_negocio_modal').val(gestor_negocio);
	$('#coste_modal').val(coste);
	
	showModal();
	
	//window.setTimeout(setTimeout(function(){showModal()}, 1000));
}


$(function() {
	$('#alta_proyecto').on('click', '.lapiz', function(e) {		
		id= $(this).attr('name');	
	});
	
	$('#alta_proyecto').on('loaded.bs.modal', function () {
		modalCliente(id);
		
	});
	
	$('#alta_proyecto').on('click', '.papelera', function(e) {
		$('#deleteProject').attr('name',$(this).attr('name'));
	});
	
	
	$('#deleteProject').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = "/projectServlet?";
		 var postData="accion=delete&id="+ id;
		 $.ajax(			
			{
				url : formURL,
				type: "POST",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					$('#row'+id).fadeOut("fast", function(){
						$(this).remove();
						$('#myTable').paginateMe({
							pagerSelector : '#myPager',
							showPrevNext : true,
							hidePageNumbers : false,
							perPage : 5
						});
					});
					$('#confirm-delete').modal('hide');
				}
			});
	});
	
	$("#submit_project_form").on('click',function(e) {
		e.preventDefault(); //STOP default action
		var $form = $("#new-project-form");
		
	
		if($form.valid()){		

			var postData = $form.serialize() + "&accion=new";
			var formURL = $form.attr("action");
			$.ajax(
			{
				 url : formURL,
				  type: "GET",
				  data : postData,
				  success:function(data, textStatus, jqXHR) 
				  {
						//data: return data from server
					if (data.success==("true")){
						
						
						$('#message_div').removeClass("error").addClass("success");
						if ($('.new-user-form-holder').height()<190){
							$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
						}
						
						$form.find('.form-container').find('div:not(#message_div)').hide(0);
						$('#span_message').html("El proyecto se ha creado de forma correcta.");
						$('#message_div').css('display','block');
						$('#buttons_holder').css('display','none');
						
						resetForm($form);
						setTimeout(function() { 
							$( "#message_div" ).fadeOut( "slow", function() {
								$('#span_message').html("");
						  }); 
						//$('#newUserButton').click();	
						location.reload();

						}, 3000);
					}else{
						$('#message_div').removeClass("success").addClass("error");
						if ($('.new-user-form-holder').height()<190){
							$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
						}
						$('#span_message').html(data.error);
						$('#message_div').css('display','block');
					
						}
				  },
			  error: function(jqXHR, textStatus, errorThrown) 
			  {
				if (errorThrown.length > 0){
					$('#span_message').html(errorThrown);
					$('#message_div').addClass('error').removeClass('success');
				}
			  }
			});
		}			
	});
});