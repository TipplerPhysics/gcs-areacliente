function getNum_control(){
var name = $("#equipo").val();
	
	
	if (id!="default"){
		var postData = "accion=getNumControl&equipo="+name;
		var formURL = "/teamServlet";
		$.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) {
				
				if (data.success="true"){
					var fecha = new Date();
					var anio = fecha.getFullYear();
					
					var codigo = name.substring(0,3).toUpperCase() + "_" + anio + data.contador;
					
					$("#numero_control").val(codigo);
					
				}
				
			}
		});
	}	
}

function sendNewCoste(){
	
	var $form = $("#new-coste-form");		
	
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
				
				var coste_anal = (!isNaN(parseInt($('#analisis_coste').val())) ? parseInt($('#analisis_coste').val()) : 0);
				var coste_disenio = (!isNaN(parseInt($('#disenio_coste').val())) ? parseInt($('#disenio_coste').val()) : 0);
				var coste_constuccion = (!isNaN(parseInt($('#construccion_coste').val())) ? parseInt($('#construccion_coste').val()) : 0);
				var coste_pruebas = (!isNaN(parseInt($('#pruebas_coste').val())) ? parseInt($('#pruebas_coste').val()) : 0);
				var coste_gestion = (!isNaN(parseInt($('#gestion_coste').val())) ? parseInt($('#gestion_coste').val()) : 0);
				
				
				$('#coste').val(coste_anal+coste_disenio+coste_constuccion+coste_pruebas+coste_gestion);
				
				$form.hide();
				$('#span_message_modal').html('El coste ha sido registrado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
				$('.modal-footer').hide();
				$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
				resetForm($form);
				$('#new-costo').modal('hide');
				
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
	return false;
}

function sendEditCoste(){
	var $form = $('#edit-coste-form');
	var formURL = $form.attr("action");
	 var $formData = $form.serialize();
	 var postData= $formData+"&accion=update&id="+ id;
	 
	 console.log($('#gestor_it_modal').val());
	 console.log($('#equipo_modal').val());
	 
	 if($form.valid()){
		 $.ajax(			
			{
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					if (data.success="true"){
						$form.hide();
						$('#span_message_modal').html('El coste ha sido registrado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
						$('.modal-footer').hide();
						$('#message_div_modal').css('display','block').removeClass("error").addClass("success");

						setTimeout(function() { 
							resetForm($form);
							location.reload();
						}, 1500);
					}else{
						$('#span_message_modal').html(data.error);
						$('.modal-footer').hide();
						$('#message_div_modal').css('display','block').addClass("error");
					}
					
				}
			});
	 }
	 
	 
}

function getProjectsByClient(pagina){
	
	var id;
	if (pagina=="modal"){
		var id = $("#input_cliente_modal").val();
	}else{
		var id = $("#input_cliente").val();
	}
	
	
	
	if (id!="default"){
		var postData = "accion=getProjectsByClient&id="+id;
		var formURL = "/projectServlet";
		$.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) {
				
				
				var options = $("#project");
				options.empty();
				options.removeAttr("disabled");
				 options.append($("<option />").val("default").text("Seleccionar..."));
				$.each(data, function() {
				    options.append($("<option />").val(this.id).text(this.name));
				});			
				
				options.selectpicker('refresh');
				//$("#project_name").selectpicker();
				//initSelectpickers();
			}
		});
	}	
}

function editCoste(){
	var $currentRow = $('#row'+id);

	var fecha_solicitud_val = $currentRow.data('fecha-solicitud-val');
	var comentarios = $currentRow.data('comentarios');
	var num_valoracion = $currentRow.data('num-valoracion');
	var gestor_it = $currentRow.data('gestor-it');
	var fecha_alta = $currentRow.data('fecha-alta');
	
	var equipo = $currentRow.data('equipo');
	var num_control = $currentRow.data('num-control');
	var nombre_proyecto = $currentRow.data('nombre-proyecto');
	var nombre_cliente = $currentRow.data('nombre-cliente');
	
	var analisis_coste = $currentRow.data('analisis-coste');
	var analisis_horas = $currentRow.data('analisis-horas');
	var disenio_coste = $currentRow.data('disenio-coste');
	var disenio_horas = $currentRow.data('disenio-horas');
	
	var construccion_coste = $currentRow.data('construccion-coste');
	var construccion_horas = $currentRow.data('construccion-horas');
	var pruebas_coste = $currentRow.data('pruebas-coste');
	var pruebas_horas = $currentRow.data('pruebas-horas');
	
	var gestion_coste = $currentRow.data('gestion-coste');
	var gestion_horas = $currentRow.data('gestion-horas');
	var total_coste = $currentRow.data('total-coste');
	var total_horas = $currentRow.data('total-horas');	
	
	
	$('#cliente_modal').val(nombre_cliente);
	$('#project_modal').val(nombre_proyecto);
	$('#numero_control_modal').val(num_control);
	$('#equipo_modal').val(equipo);
	
	$('#fecha_alta_costes_modal').val(fecha_alta);
	$('#gestor_it_modal').val(gestor_it);
	console.log('editCoste ' + gestor_it);
	$('#comentarios_modal').val(comentarios);	
	$('#num_valoracion_modal').val(num_valoracion);
	
	$('#fecha_solicitud_valoracion_modal').val(fecha_solicitud_val);
	$('#analisis_horas_modal').val(analisis_horas);
	$('#analisis_coste_modal').val(analisis_coste);
	
	$('#disenio_horas_modal').val(disenio_horas);
	$('#disenio_coste_modal').val(disenio_coste);
	
	$('#construccion_horas_modal').val(construccion_horas);
	$('#construccion_coste_modal').val(construccion_coste);
	
	$('#pruebas_horas_modal').val(pruebas_horas);
	$('#pruebas_coste_modal').val(pruebas_coste);
	
	$('#gestion_horas_modal').val(gestion_horas);
	$('#gestion_coste_modal').val(gestion_coste);
	
	$('#total_horas_modal').val(total_horas);
	$('#total_coste_modal').val(total_coste);
	

	
	showModal();
}

$(function() {
	
	$('#edit-coste').on('loaded.bs.modal', function () {
		editCoste(id);
	})
	
	$('.gestion_coste').on('click', '.lapiz', function(e) {
		id= $(this).attr('name');
		
	});
	
	$('.gestion_coste').on('click', '.papelera', function(e) {
		$('#deleteCoste').attr('name',$(this).attr('name'));
	});
	
	
	
	$('#deleteCoste').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = "/costeServlet?";
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
							perPage : 10
						});
					});
					$('#confirm-delete').modal('hide');
				}
			});
	});
	
	$("#submit_cost_form").on('click',function(e) {
		e.preventDefault(); //STOP default action
		var $form = $("#new-coste-form");		
		
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
					$('#span_message').html("El coste ha sido creado de forma correcta.");
					$('#message_div').css('display','block');
					
					$('#buttons_new_demanda').css('display','none');
					$('.message-container').css('display','block');
					resetForm($form);
					$form.hide();
					
					setTimeout(function() { 
						$("#message_div").fadeOut("slow", function() {
							$('#span_message').html("");
					  }); location.reload();}, 5000);
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
		return false;
	});
});
	
