function editCountry(id){	
	showModal();
}

function filteringPaises(){
	var $form = $("#test-header-filter");
	var postData =  $form.serialize();
	window.location = "./paises.do?"+postData;
}

function sendEditCountry(){
//	$('#new_service_form_modal').addClass('hidden');
	var nombre = $('#nombrePais').val();
	var $form = $('#edit-country-form');
	var formURL = $form.attr("action");
	 var $formData = $form.serialize();
	 var postData= $formData+"&accion=update&id="+ id+'&name='+nombre;
	 
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
						$('#span_message_modal').html('El pa&iacute ha sido registrado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
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

$(function(){
	
	$('#edit-country').on('loaded.bs.modal', function () {
		editCountry(id);
	})
	
	$('.gestion_pais').on('click', '.lapiz', function(e) {
		id= $(this).attr('name');		
	});
	
	$('.gestion_pais').on('click', '.papelera', function(e) {
		$('#deletePais').attr('name',$(this).attr('name'));
	});
	
	$('#deletePais').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = "/paisesServlet?";
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
					location.reload();
			}
			});
	});
	

	
	
	$('body').on('click', '#submit_country_form', function(e) {
		e.preventDefault(); //STOP default action
		var $form = $("#new-country-form");
		
		if($form.valid()){		
			$('#submit_country_form').addClass('hidden');
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
					
					$form.find('.form-container').find('div:not(#message_div)').hide(0);
					$form.find('#span_message').html('El pa&iacutes ha sido creado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('#message_div').css('display','block').removeClass("error").addClass("success");;

						setTimeout(function() { 
						resetForm($form);
						location.reload();
					}, 1500);				
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
