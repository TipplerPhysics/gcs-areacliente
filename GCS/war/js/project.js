function LoadModalService(){
	id = $('#select_project_action').val();
	

	$('#select-service').modal('hide');
	$('#edit-service').modal({
		  remote: "../servicioModal.do?id="+id
		});
	$('#edit-service').modal('show');


	
}


function filteringProject(){
	var $form = $("#test-header-filter");
	var postData =  $form.serialize();
	window.location = "./gestionProyecto.do?"+postData;
}

function autocompleteSubtipo(tipo,target){
	target.empty();
	target.selectpicker("render");
	target.empty();
	if (tipo!="default"){

		target.empty();
		target.selectpicker("render");
		target.empty();
		target.append($("<option></option>").attr("value"," ").text("Seleccionar"));
		if(tipo=="MIGRACI\u00D3N"){
			target.append($("<option></option>").attr("value","NO APLICA").text("NO APLICA"));
			target.append($("<option></option>").attr("value","INTEGRACI\u00D3N AM\u00C9RICA").text("INTEGRACI\u00D3N AM\u00C9RICA"));
			target.append($("<option></option>").attr("value","PERIMETER SERVER").text("PERIMETER SERVER"));
			target.append($("<option></option>").attr("value","CHANNELING").text("CHANNELING"));
			target.append($("<option></option>").attr("value","ONE BANK").text("ONE BANK"));
		}else{
			target.append($("<option></option>").attr("value","NO APLICA").text("NO APLICA"));
		}		
							


	}
	target.selectpicker("refresh");
}

function loadCosteModal(){
	var radios = $('#costes-by-project-table').find(":radio");
	var a =0;
	var sel = false;
	var git;
	for (a=0; a<=radios.length-1;a++){
		var r = radios[a];
		if ($(r).parent().hasClass('on')){
			id = $(r).attr('id').split("radio_")[1];
			git = $(r).data("git");
			sel = true;
		}
	}
	
	if (sel){
		$('#edit-project').modal('hide');
		
		$('#edit-costo').modal({
			  remote: "../costeProjectModal.do?id="+id+"&git="+git
			  
			});
		$('#edit-costo').modal('show');
	}
}

function sendEditConectividad(){
	var $form = $('#edit-conectividad-form');
	var formURL = $form.attr("action");
	 var $formData = $form.serialize();
	 var postData= $formData+"&accion=new&project_id="+ id+"&conect_id=";
	 $.ajax(			
		{
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				if (data.success=="true"){
					$form.hide();
					$('#span_message_modal').html('La conectividad se ha guardado de forma correcta.');
					$('.modal-footer').hide();
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
					setTimeout(function() { 
						resetForm($form);
						$('#new-conectividad').modal('hide');
					}, 2000);
				}
			}
		});
}

function loadEditModal(){
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
	////////////////////////	
	}else if (accion=='costes'){
		$('#edit-action').modal('hide');
		$('#edit-project').modal({
			  remote: "../costesByModal.do?project_id="+id
			});
	///////////////////	
		
		
		
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

function  sendEditProject(){
	//$('#new_project_form_modal').addClass('hidden');
	 var $form = $('#edit-project-form');
	 var formURL = $form.attr("action");
	 var $formData = $form.serialize();
	 var postData= $formData+"&accion=update&id="+ id;	 
	 if($form.valid()){	

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
					$('.modal-footer').hide();
					$('#message_div_demanda_modal').css('display','block').removeClass("success").addClass("error");
				}
				
			}
		});
	 }
}
function sendCloneProject() {
	$('#new_project_form_modal').addClass('hidden');
	var $form = $('#edit-project-form');
	

	if($form.valid()){		

		var postData = $form.serialize() + "&accion=clone";
		var formURL = $form.attr("action");
		$.ajax(
		{
			 url : formURL,
			  type: "GET",
			  data : postData,
			  success:function(data, textStatus, jqXHR) 
			  {
					//data: return data from server
					if (data.success=="true"){
						$form.hide();
						$('#span_message_demanda_modal').html('Se ha creado un proyecto con los datos contenidos en el formulario anterior correctamente.');
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
}
function modalCliente(){
	
	if($('#edit-project-form').length > 0) {
		// carga datos al formulario de edicion de proyecto
		$('#edit_project_form_modal').data("id",id);	
		var $currentRow = $('#row'+id);
		var fecha_alta = $currentRow.attr('data-fecha-alta');
		var nombre = $currentRow.attr('data-nombre');
		var tipo = $currentRow.attr('data-tipo');
		var cliente = $currentRow.attr('data-cliente');
		var cliente_name = $currentRow.attr('data-cliente-name');
		var clasificacion = $currentRow.attr('data-clasificacion');
		var gestor_it = $currentRow.attr('data-gestor-it');
		var gestor_negocio = $currentRow.attr('data-gestor-negocio');
		var coste = $currentRow.attr('data-coste');
		var subtipo = $currentRow.attr('data-subtipo');		
		var producto = $currentRow.attr('data-producto');
		var conectividad = $currentRow.attr('data-conectividad');
		var servicio = $currentRow.attr('data-servicio');	
		var fecha_ini_valoracion = $currentRow.attr('data-fecha-ini-valoracion');
		var fecha_fin_valoracion = $currentRow.attr('data-fecha-fin-valoracion');	
		var fecha_ini_viabilidad = $currentRow.attr('data-fecha-ini-viabilidad');
		var fecha_fin_viabilidad = $currentRow.attr('data-fecha-fin-viabilidad');
		var url_doc_google_drive = $currentRow.attr('data-url-doc-google-drive');
		
		
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
		$('#subtipo_modal').val(subtipo);
		$('#input_cliente_id').val(cliente);
		$('#input_cliente_modal').val(cliente_name);
		$('#clasificacion_modal').val(clasificacion);
		$('#gestor_it_modal').val(gestor_it);
		$('#gestor_negocio_modal').val(gestor_negocio);
		$('#coste_modal').val(coste);
		$('#url_doc_google_drive_modal').val(url_doc_google_drive);
		
		////////////////////////////////////////////////////////////
		
		
		
		var ok_negocio_check = $currentRow.attr('ok-negocio-check');	
		var envio_c100 = $currentRow.attr('data-envio-c100');
		var ok_negocio = $currentRow.attr('data-ok-negocio');
		var fecha_plan_trabajo = $currentRow.attr('data-fecha-plan-trabajo');
		var fecha_disponible_conectividad = $currentRow.attr('data-fecha-disponible-conectividad');
		
		$('#ok_negocio_check').val(ok_negocio_check);
		$('#envio_c100_modal').val(envio_c100);
		$('#ok_negocio_modal').val(ok_negocio);
		$('#fecha_plan_trabajo_modal').val(fecha_plan_trabajo);
		$('#fecha_disponible_conectividad_modal').val(fecha_disponible_conectividad);
		
		
		////////////////////////////////////////////////////////////
		
		
	}
	
	showModal();
	
	//window.setTimeout(setTimeout(function(){showModal()}, 1000));
}





$(function() {
	
	
	$('#new-project-form').on('change', '#tipo-imp-proj', function(e) {
		var tipo = $('#tipo-imp-proj').val();
		var target = $("#subtipo_imp");
		autocompleteSubtipo(tipo,target);
				
	});
	
	$('#alta_proyecto').on('click', '#edit_service_modal_button', function(e) {		
		id= $('#select_project_action').val();
		
		$('#project-servicio').modal('hide');
		
		$('#new-servicio').modal({
			  remote: "../loadService.do?id="+id
			});
		$('#new-servicio').modal('show');
		
		});
	
	$('#alta_proyecto').on('click', '#edit_project_modal_button', function(e) {		
		id= $('#select_project_action').val();
		
		$('#project-conectividad').modal('hide');
		
		$('#new-conectividad').modal({
			  remote: "../loadConectivity.do?id="+id
			});
		$('#new-conectividad').modal('show');
		
		});
	
	//Nuevo proyecto
	$('#producto').change(function(e){
		//vaciamos el select
		$("#conectividad").empty();
		
		/*target.append($("<option></option>").attr("value","default").text("-"));}
		target.selectpicker("refresh");
		if ($('#producto').val().indexOf("H2H") >= 0) {
			//case H2H / H2H-bancoRelay		
			$("#conectividad").append($("<option></option>").attr("value","default").text("Seleccionar"));
			$("#conectividad").append($("<option></option>").attr("value","AS2").text("AS2"));
			$("#conectividad").append($("<option></option>").attr("value","AS2").text("FTP"));
			$("#conectividad").append($("<option></option>").attr("value","FTPS").text("FTPS"));
			$("#conectividad").append($("<option></option>").attr("value","SFTP").text("SFTP"));
			$("#conectividad").append($("<option></option>").attr("value","Webservices").text("Webservices"));
			
			
			
		} else if (($('#producto').val().indexOf("H2H") < 0) && ($('#producto').val().indexOf("default") < 0)) {
			//case Swift-bancoRelay/ Swift Fileact		
			$("#conectividad").append($("<option></option>").attr("value","default").text("Seleccionar"));
			$("#conectividad").append($("<option></option>").attr("value","Score").text("Score"));
			$("#conectividad").append($("<option></option>").attr("value","Macug").text("Macug"));
			
		} else {
			//case nada seleccionado
			$("#conectividad").empty();
			$("#conectividad").append($("<option></option>").attr("value","Seleccionar").text("default"));
		}
		*/
		
		//repintamos el combo
		$("#conectividad").selectpicker("refresh");	
			 var formURL = "/projectServlet?";
			 var postData="accion=getConectividades&producto="+$('#producto').val();
			 $.ajax(			
				{
					url : formURL,
					type: "POST",
					data : postData,
					success:function(data, textStatus, jqXHR) 
					{
						if (data.success=="true"){
							var conectividades = data.jarray[0];

							

							$("#conectividad").append($("<option></option>").attr("value","default").text("Seleccionar"));
							
							
							var tamano = conectividades.length;
							for (var i = 0 ; i < tamano; i++){
								$("#conectividad").append($("<option></option>").attr("value",conectividades[i]).text(conectividades[i]));
								
								
							}
							$("#conectividad").selectpicker("refresh");
							//

						}					
					}
				});
		
		
		target.selectpicker("refresh");
	});
	

	
	$('#alta_proyecto').on('click', '.lapiz', function(e) {		
		id= $(this).attr('name');
	});
	
	$('#alta_proyecto').on('loaded.bs.modal', function (e) {
		modalCliente(id);
		$('#edit_project_form_modal').data("id",id);	
		var $currentRow = $('#row'+id);
		var producto = $currentRow.attr('data-producto');		
		$('#producto_modal').val(producto);
		
//////////////////////////////////////////////////////////////////////////
				
		var fecha_alta = $currentRow.attr('data-fecha-alta');
		var nombre = $currentRow.attr('data-nombre');
		var tipo = $currentRow.attr('data-tipo');
		var subtipo = $currentRow.attr('data-subtipo');	
		var cliente = $currentRow.attr('data-cliente');
		var cliente_name = $currentRow.attr('data-cliente-name');
		var clasificacion = $currentRow.attr('data-clasificacion');
		var gestor_it = $currentRow.attr('data-gestor-it');
		var gestor_negocio = $currentRow.attr('data-gestor-negocio');
		var coste = $currentRow.attr('data-coste');
		var ok_negocio_check = $currentRow.attr('ok-negocio-check');		
		var fecha_ini_valoracion = $currentRow.attr('data-fecha-ini-valoracion');
		var fecha_fin_valoracion = $currentRow.attr('data-fecha-fin-valoracion');		
		var fecha_ini_viabilidad = $currentRow.attr('data-fecha-ini-viabilidad');
		var fecha_fin_viabilidad = $currentRow.attr('data-fecha-fin-viabilidad');
		var url_doc_google_drive = $currentRow.attr('data-url-doc-google-drive');	
		var envio_c100 = $currentRow.attr('data-envio-c100');
		var ok_negocio = $currentRow.attr('data-ok-negocio');
		var fecha_plan_trabajo = $currentRow.attr('data-fecha-plan-trabajo');
		var fecha_disponible_conectividad = $currentRow.attr('data-fecha-disponible-conectividad');
		
		$('#fecha_alta_cliente_modal').val(fecha_alta);
		$('#project_name_modal').val(nombre);
		$('#tipo_modal').val(tipo);
		$('#subtipo_modal').val(subtipo);
		$('#input_cliente_id').val(cliente);
		$('#input_cliente_modal').val(cliente_name);
		$('#clasificacion_modal').val(clasificacion);
		$('#clasificacion_modal').selectpicker("refresh");
		$('#gestor_it_modal').val(gestor_it);
		$('#gestor_it_modal').selectpicker("refresh");
		$('#gestor_negocio_modal').val(gestor_negocio);
		$('#gestor_negocio_modal').selectpicker("refresh");
		$('#coste_modal').val(coste);
		$('#ok_negocio_check').val(ok_negocio_check);
		$('#url_doc_google_drive_modal').val(url_doc_google_drive);
		$('#fecha_inicio_valoracion_modal').val(fecha_ini_valoracion);
		$('#fecha_fin_valoracion_modal').val(fecha_fin_valoracion);	
		$('#fecha_inicio_viabilidad_modal').val(fecha_ini_viabilidad);
		$('#fecha_fin_viabilidad_modal').val(fecha_fin_viabilidad);
		$('#envio_c100_modal').val(envio_c100);
		$('#ok_negocio_modal').val(ok_negocio);
		$('#fecha_plan_trabajo_modal').val(fecha_plan_trabajo);
		$('#fecha_disponible_conectividad_modal').val(fecha_disponible_conectividad);
		
//////////////////////////////////////////////////////////////////////////
		
		
		var conectividad = $currentRow.attr('data-conectividad');	
		
		/*$('#conectividad_modal').val(conectividad);
		$('#conectividad_modal').selectpicker("refresh");*/
		
		setTimeout(function(){ 		
			$('#conectividad_modal').val(conectividad);
			$('#conectividad_modal').selectpicker("refresh");
		}, 500);

/////////////////////////////////////////////////////////////////////////////////////////////////
		
		$('#producto_modal').change(function(e){
			
			//vaciamos el select
			$("#conectividad_modal").empty();
			
			//repintamos el combo
			$("#conectividad_modal").selectpicker("refresh");	
				 var formURL = "/projectServlet?";
				 var postData="accion=getConectividades&producto="+$('#producto_modal').val();
				 $.ajax({
						url : formURL,
						type: "POST",
						data : postData,
						success:function(data, textStatus, jqXHR) 
						{
							if (data.success=="true"){
								var conectividades = data.jarray[0];
								
								
								$("#conectividad_modal").empty();
								
								//repintamos el combo
								$("#conectividad_modal").selectpicker("refresh");	
								
								$("#conectividad_modal").append($("<option></option>").attr("value","default").text("Seleccionar"));
									
								var tamano = conectividades.length;
								for (var i = 0 ; i < tamano; i++){
									$("#conectividad_modal").append($("<option></option>").attr("value",conectividades[i]).text(conectividades[i]));
									
									
								}
								$("#conectividad_modal").selectpicker("refresh");
								//
	
							}					
						}
				 });
		});
		
/////////////////////////////////////////////////////////////////////////////////////////////////		
		
		
		/*//Editar proyecto modal
		
		$('#producto_modal').change(function(e){
			//vaciamos el select
			$("#conectividad_modal").empty();
			if ($('#producto_modal').val().indexOf("H2H") >= 0) {
				//case H2H / H2H-bancoRelay		
				$("#conectividad_modal").append(new Option("Seleccionar", "default"));
				$("#conectividad_modal").append(new Option("AS2", "AS2"));
				$("#conectividad_modal").append(new Option("FTP", "FTP"));
				$("#conectividad_modal").append(new Option("FTPS", "FTPS"));
				$("#conectividad_modal").append(new Option("SFTP", "SFTP"));
				$("#conectividad_modal").append(new Option("WEBSERVICES", "WEBSERVICES"));
			}else {
				if($('#producto_modal').val().indexOf("GLOBAL NETCASH") >= 0){
					//case GLOBAL NETCASH
					$("#conectividad_modal").append(new Option("Seleccionar", "default"));	
					$("#conectividad_modal").append(new Option("GLOBAL NETCASH", "GLOBAL NETCASH"));	
			    }else{
				//case Swift-bancoRelay/ Swift Fileact			
					$("#conectividad_modal").append(new Option("Seleccionar", "default"));	
					$("#conectividad_modal").append(new Option("SCORE", "SCORE"));	
					$("#conectividad_modal").append(new Option("MACUG", "MACUG"));	
			    }
			}
			//repintamos el combo
			$("#conectividad_modal").selectpicker("refresh");
			
		});*/
			
	});

////////////////////////////////////////////////////////////////////////////////////////////////
	
	
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
							perPage : 10
						});
					});
					$('#confirm-delete').modal('hide');
				}
			});
	});
//////////////////////////////////////////////////////////////////////////
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
						$('.relatedOptions').hide(0);
						$('#span_message').html("El proyecto se ha creado de forma correcta.");
						$('#message_div').css('display','block');
						$('#buttons_holder').css('display','none');
						
						resetForm($form);
						setTimeout(function() { 
							$( "#message_div" ).fadeOut( "fast", function() {
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
//////////////////////////////////////////////////////////////////////////
/*
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
						$('.relatedOptions').hide(0);
						$('#span_message').html("El proyecto se ha creado de forma correcta.");
						$('#message_div').css('display','block');
						$('#buttons_holder').css('display','none');
						
						resetForm($form);
						setTimeout(function() { 
							$( "#message_div" ).fadeOut( "fast", function() {
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
*/	
//////////////////////////////////////////////////////////////////////////
	
});