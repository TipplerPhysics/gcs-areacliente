function getProjectsByClient(){
	var id = $("#input_cliente").val();
	
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

$(function() {
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
					$('#span_message').html("La petici&oacute;n ha sido creada de forma correcta con el c&oacute;digo de petici&oacute;n num: " + data.cod_peticion);
					$('#message_div').css('display','block');
					
					$('#buttons_new_demanda').css('display','none');
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
	
