function editServicio(id){	
	showModal();
}

function sendEditServicio(){
	var $form = $('#edit-servicio-form');
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
					if (data.success="true"){
						$form.hide();
						$('#span_message_modal').html('El servicio ha sido registrado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
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

function ajaxServicios(pais,target,targetExt){
	if (pais!="default"){
		 var formURL = "/serviceServlet?";
		 var postData="accion=getServicesByCountry&pais="+pais;
		 $.ajax(			
			{
				url : formURL,
				type: "POST",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					if (data.success=="true"){
						var servicios = data.servicios[0];
						targetExt.empty();
						targetExt.selectpicker("render");
						targetExt.empty();
						targetExt.append($("<option></option>").attr("value","default").text("-"));
						targetExt.selectpicker("refresh");
						
						target.empty();
						target.selectpicker("render");
						target.empty();
						target.append($("<option></option>").attr("value","default").text("Seleccionar"));
						
						
						var tamano = servicios.length;
						for (var i = 0 ; i < tamano; i=i+4){
							var id = servicios[i];
							var name = servicios [i+1];
							var paisId = servicios[i+2];
							var extensionesarr = servicios[i+3];
							var extensiones = "";
							extensionesarr.forEach(function(entry){
								extensiones = extensiones+" " + entry;
							});
							target.append($("<option></option>").attr("value",String(id)).text(name)).attr("data-pais",String(paisId)).attr("data-extensiones",extensiones);
						}
						target.selectpicker("refresh");
						//

					}					
				}
			});
	}
}
function ajaxExtensiones(servicio,target){
	if (servicio!="default"){
		 var formURL = "/serviceServlet?";
		 var postData="accion=getExtensionesByService&service="+servicio;
		 $.ajax(			
			{
				url : formURL,
				type: "POST",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					if (data.success=="true"){
						var extensiones = data.extensiones[0];
						target.empty();
						target.selectpicker("render");
						target.empty();
						target.append($("<option></option>").attr("value","default").text("Seleccionar"));
						$.each(extensiones,function(index,value){
								target.append($("<option></option>").attr("value",value).text(value));	
						});
						
						
					}
					target.selectpicker("refresh");
						//

				}					
				
			});
	}
}

$(function(){
	
	$('#edit-service').on('loaded.bs.modal', function () {
		editServicio(id);
	})
	
	$('.gestion_servicio').on('click', '.lapiz', function(e) {
		id= $(this).attr('name');		
	});
	
	$('#deleteServicio').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = "/serviceServlet?";
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
	
	$('.gestion_servicio').on('click', '.papelera', function(e) {
		$('#deleteServicio').attr('name',$(this).attr('name'));
	});
	
/*	$('#pais_servicio').change(function(e){
		var pais = $('#pais_servicio').val();
		var target = $('#servicio');
		
		ajaxServicios(pais,target);
		
	}); */
	
	
	$('#new-service-form').on('change', '#pais_servicio', function(e) {
		var pais = $('#pais_servicio').val();
		var target = $('#servicio');
		var targetExt = $('#extension');
		ajaxServicios(pais,target,targetExt);		
	});
	
	
	$('#new-service-form').on('change', '#servicio', function(e) {
		var servicio = $('#servicio').val();
		var target = $('#extension');
		ajaxExtensiones(servicio,target);
				
	});
	

	
	$('#new-servicio').on('change', '#pais_servicio', function(e) {
		var pais = $('#pais_servicio').val();
		var target = $('#servicio');
		var targetExt = $('#extension');
		ajaxServicios(pais,target,targetExt);		
	});
	
	$('#new-servicio').on('change', '#servicio', function(e) {
		var servicio = $('#servicio').val();
		var target = $('#extension');
		ajaxExtensiones(servicio,target);
				
	});
	
	$('html').on('change', '#pais_servicio_modal', function(e) {
		var pais = $('#pais_servicio_modal').val();
		var target = $('#servicio_modal');
		var targetExt = $('#extension_modal');
		
		ajaxServicios(pais,target,targetExt);
		
	});
	
	$('html').on('change', '#servicio_modal', function(e) {
		var servicio = $('#servicio_modal').val();
		var target = $('#extension_modal');
		
		
		ajaxExtensiones(servicio,target);
		
	});
	
	
	
	
	
	$('body').on('click', '#submit_service_form', function(e) {
		e.preventDefault(); //STOP default action
		var $form = $("#new-service-form");
		
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
					
					$('.message_div').removeClass("error").addClass("success");
					if ($('.new-user-form-holder').height()<190){
						$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
					}
					$('.span_message').html("El servicio ha sido creado de forma correcta.");
					$('.message_div').css('display','block');
					
					$('#buttons_new').css('display','none');
					$('.message-container').css('display','block');
					resetForm($form);
					$form.hide();
					
					setTimeout(function() { 
						$( ".message_div" ).fadeOut( "slow", function() {
							$('.span_message').html("");
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
		$('.selectpicker').selectpicker('refresh');
		return false;
	});
});