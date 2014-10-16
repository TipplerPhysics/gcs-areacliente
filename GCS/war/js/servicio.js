$(function(){
	
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
							perPage : 5
						});
					});
					$('#confirm-delete').modal('hide');
				}
			});
	});
	
	$('.gestion_servicio').on('click', '.papelera', function(e) {
		$('#deleteServicio').attr('name',$(this).attr('name'));
	});
	
	$('#pais_servicio').change(function(e){
		var pais = $('#pais_servicio').val();
		
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
							$("#servicio").empty();
							$("#servicio").append(new Option("Seleccionar", "default"));
							$.each(servicios, function (index, value) {
								$("#servicio").append(new Option(value, value));
						    });	
							$("#servicio").selectpicker("refresh");
						}					
					}
				});
		}
	});
	
	
	$("#submit_service_form").on('click',function(e) {
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
					
					$('#message_div').removeClass("error").addClass("success");
					if ($('.new-user-form-holder').height()<190){
						$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
					}
					$('#span_message').html("El servicio ha sido creado de forma correcta.");
					$('#message_div').css('display','block');
					
					$('#buttons_new').css('display','none');
					$('.message-container').css('display','block');
					resetForm($form);
					$form.hide();
					
					setTimeout(function() { 
						$( "#message_div" ).fadeOut( "slow", function() {
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