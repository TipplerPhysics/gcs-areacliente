function cargaMeses(){	
	var $form = $('#report-form');
	var formURL = $form.attr("action");
	var $formData = $form.serialize();
	var postData= $formData+"&accion=update&id="+ id;
	
	
	if($form.valid()){
		 $.ajax(	
				 
			{
				url : formURL,
				type: "POST",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				
				{
					var informes = $('#informes');
					var anyoselect = $('#anyo-pruebas').attr("data-en");
					var meses="";
					// Add options
				    for (var i in informes {
				    	if(anyoselect==i.anyo_implantacion){
				    		if(meses.indexOf(i.mes_implantacion)!=-1){
				    			$('#mes-pruebas').append('<option value=' + i.mes_implantacion + '>' + i.mes_implantacion + '</option>');
				    			meses +=i.mes_implantacion+", ";
				    		}
				    	}
				    }
				},
				 error:function(data, textStatus, jqXHR){
					  if (errorThrown.length > 0){
							$('#span_message_informe').html(errorThrown);
							$('#message_div_informe').addClass('error').removeClass('success');
						}
				  }
				
			});

	}
}

function cargaDias(){	
	var $form = $('#report-form');
	var formURL = $form.attr("action");
	var $formData = $form.serialize();
	var postData= $formData+"&accion=update&id="+ id;
	
	
	if($form.valid()){
		 $.ajax(	
				 
			{
				url : formURL,
				type: "POST",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					var informes = $('#informes');
					var messelect = $('#mes-pruebas').attr("data-en");
					var dias="";
					// Add options
				    for (var i in informes {
				    	if(messelect==i.mes_implantacion){
				    		if(dias.indexOf(i.dia_implantacion)!=-1){
				    			$('#dia-pruebas').append('<option value=' + i.dia_implantacion + '>' + i.dia_implantacion + '</option>');
				    			dias +=i.dia_implantacion+", ";
				    		}
				    	}
				    }
				},
				 error:function(data, textStatus, jqXHR){
					  if (errorThrown.length > 0){
							$('#span_message_informe').html(errorThrown);
							$('#message_div_informe').addClass('error').removeClass('success');
						}
				  }
			});

	}
}

function cargaTipos(){	
	var $form = $('#report-form');
	var formURL = $form.attr("action");
	var $formData = $form.serialize();
	var postData= $formData+"&accion=update&id="+ id;
	
	
	if($form.valid()){
		 $.ajax(	
				 
			{
				url : formURL,
				type: "POST",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					var informes = $('#informes');
					var diaselect = $('#dia-pruebas').attr("data-en");
					var tiposubida="";
					// Add options
				    for (var i in informes) {
				    	if(diaselect==i.dia_implantacion){
				    		if(tiposubida.indexOf(i.tipo_subida)!=-1){
				    			$('#tipo-pruebas').append('<option value=' + i.tipo_subida + '>' + i.tipo_subida + '</option>');
				    			tiposubida += i.tipo_subida+", ";
				    		}
				    	}
				    }
				},
				 error:function(data, textStatus, jqXHR){
					  if (errorThrown.length > 0){
							$('#span_message_informe').html(errorThrown);
							$('#message_div_informe').addClass('error').removeClass('success');
						}
				  }
			});

	}
}