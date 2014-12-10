function cargaMeses(id){	
	var $form = $('#report-form');
	var formURL = $form.attr("action");
	//var $formData = $form.serialize();
	//var postData= $formData+"&accion=update&id="+ id;
	
	if($form.valid()){
		 $.ajax(	
				 
			{
				url : formURL,
				type: "POST",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					
					$('#mes-pruebas').
				}
			}

	}
}