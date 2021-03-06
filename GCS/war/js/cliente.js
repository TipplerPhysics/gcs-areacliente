function drawChecks(str){
		str = str.toLowerCase();
		if (str.indexOf("argentina")!=-1){
			$('#argentina_check_modal').attr("checked","checked");
			$('#argentina_check_modal').next().addClass("checked");
		}else{
			$('#argentina_check_modal').removeClass("checked");
			$('#argentina_check_modal').next().removeClass("checked");
		}				
		if (str.indexOf("lgica")!=-1){
			$('#belgica_check_modal').attr("checked","checked");
			$('#belgica_check_modal').next().addClass("checked");
		}else{
			$('#belgica_check_modal').removeClass("checked");
			$('#belgica_check_modal').next().removeClass("checked");
		}				
		if (str.indexOf("chile")!=-1){
			$('#chile_check_modal').attr("checked","checked");
			$('#chile_check_modal').next().addClass("checked");
		}else{
			$('#chile_check_modal').removeClass("checked");
			$('#chile_check_modal').next().removeClass("checked");
		}		
		if (str.indexOf("hong")!=-1){
			$('#hong_kong_check_modal').attr("checked","checked");
			$('#hong_kong_check_modal').next().addClass("checked");
		}else{
			$('#hong_kong_check_modal').removeClass("checked");
			$('#hong_kong_check_modal').next().removeClass("checked");
		}		
		if (str.indexOf("colombia")!=-1){
			$('#colombia_check_modal').attr("checked","checked");
			$('#colombia_check_modal').next().addClass("checked");
		}else{
			$('#colombia_check_modal').removeClass("checked");
			$('#colombia_check_modal').next().removeClass("checked");
		}	
		if (str.indexOf("espa")!=-1){
			$('#esp_check_modal').attr("checked","checked");
			$('#esp_check_modal').next().addClass("checked");
		}else{
			$('#esp_check_modal').removeClass("checked");
			$('#esp_check_modal').next().removeClass("checked");
		}	
		if (str.indexOf("francia")!=-1){
			$('#francia_check_modal').attr("checked","checked");
			$('#francia_check_modal').next().addClass("checked");
		}else{
			$('#francia_check_modal').removeClass("checked");
			$('#francia_check_modal').next().removeClass("checked");
		}	
		if (str.indexOf("italia")!=-1){
			$('#italia_check_modal').attr("checked","checked");
			$('#italia_check_modal').next().addClass("checked");
		}else{
			$('#italia_check_modal').removeClass("checked");
			$('#italia_check_modal').next().removeClass("checked");
		}
		if (str.indexOf("xico")!=-1){
			$('#mexico_check_modal').attr("checked","checked");
			$('#mexico_check_modal').next().addClass("checked");
		}else{
			$('#mexico_check_modal').removeClass("checked");
			$('#mexico_check_modal').next().removeClass("checked");
		}
		if (str.indexOf("per")!=-1){
			$('#peru_check_modal').attr("checked","checked");
			$('#peru_check_modal').next().addClass("checked");
		}else{
			$('#peru_check_modal').removeClass("checked");
			$('#peru_check_modal').next().removeClass("checked");
		}
		if (str.indexOf("portugal")!=-1){
			$('#portugal_check_modal').attr("checked","checked");
			$('#portugal_check_modal').next().addClass("checked");
		}else{
			$('#portugal_check_modal').removeClass("checked");
			$('#portugal_check_modal').next().removeClass("checked");
		}
		if (str.indexOf("reino")!=-1){
			$('#uk_check_modal').attr("checked","checked");
			$('#uk_check_modal').next().addClass("checked");
		}else{
			$('#uk_check_modal').removeClass("checked");
			$('#uk_check_modal').next().removeClass("checked");
		}
		if (str.indexOf("uruguay")!=-1){
			$('#uruguay_check_modal').attr("checked","checked");
			$('#uruguay_check_modal').next().addClass("checked");
		}else{
			$('#uruguay_check_modal').removeClass("checked");
			$('#uruguay_check_modal').next().removeClass("checked");
		}
		
		if (str.indexOf("curasao")!=-1){
			$('#curasao_check_modal').attr("checked","checked");
			$('#curasao_check_modal').next().addClass("checked");
		}else{
			$('#curasao_check_modal').removeClass("checked");
			$('#curasao_check_modal').next().removeClass("checked");
		}
		if (str.indexOf("usa")!=-1){
			$('#usa_check_modal').attr("checked","checked");
			$('#usa_check_modal').next().addClass("checked");
		}else{
			$('#usa_check_modal').removeClass("checked");
			$('#usa_check_modal').next().removeClass("checked");
		}
		if (str.indexOf("venezuela")!=-1){
			$('#venezuela_check_modal').attr("checked","checked");
			$('#venezuela_check_modal').next().addClass("checked");
		}else{
			$('#venezuela_check_modal').removeClass("checked");
			$('#venezuela_check_modal').next().removeClass("checked");
		}
		if (str.indexOf("redex")!=-1){
			$('#redex_check_modal').attr("checked","checked");
			$('#redex_check_modal').next().addClass("checked");
		}else{
			$('#redex_check_modal').removeClass("checked");
			$('#redex_check_modal').next().removeClass("checked");
		}		
	}

function filteringCliente(){
	var $form = $("#test-header-filter");
	var postData =  $form.serialize();
	window.location = "./gestionCliente.do?"+postData;
}

function editRowCliente(id){
		
		var line = $('#row'+id);
	
		
		var ref_local = line.data('ref-local');
		var logo_url = line.data('logo-url');
		var ref_global = line.data('ref-global');
		var crit = line.data('criticidad');
		var tipo = line.data('tipo');
		var nombre = line.data('nombre');
		var fecha_alta = line.data('fecha-alta');
		var paises = line.data('paises');
		if (paises.length!=0){
			paises = paises.replace("[","");
			paises = paises.replace("]","");
			paises = paises.replace(/, /g,"-");
			drawChecks(paises);
		}
		
		
		$('#fecha_alta_cliente_modal').val(fecha_alta);
		$('#client_name_modal').val(nombre);
		$('#ref_global_modal').val(ref_global);
		$('#ref_local_modal').val(ref_local);
		$('#logo_url_modal').val(logo_url);
		
		$('#tipo_modal').val(tipo);
		$('#criticidad_modal').val(crit);
		
		
		
		$('#edit_client_form_modal').data("id",id);		
		
		showModal();
		
		
	}

function sendEditClient(){
	var $form = $("#edit-client-form");

	
	
	if($form.valid()){		

		var postData = $form.serialize() + "&accion=edit&id="+id;
		var formURL = $form.attr("action");
		$.ajax({
			url : formURL,
			  type: "GET",
			  data : postData,
			  success:function(data, textStatus, jqXHR) 
			  {
					//data: return data from server
				if (data.success==("true")){
					$('#message_div_cliente_modal').removeClass("error").addClass("success");
					if ($('.edit-user-form-holder').height()<190){
						$('.edit-user-form-holder').height($('.new-user-form-holder').height()+35);
					}
					
					$form.find('.form-container').find('div:not(#message_div)').hide(0);
					$('#span_message_cliente_modal').html("El cliente se ha modificado de forma correcta.");
					$('#message_div_cliente_modal').css('display','block');
					
					$(".modal-footer").hide();
					
					resetForm($form);
					setTimeout(function() { 
						$( "#message_div" ).fadeOut( "slow", function() {
							$('#span_message').html("");
					  }); 
					
					location.reload();

					}, 5000);
				}else{
					$('#message_div_cliente_modal').removeClass("success").addClass("error");
					if ($('.edit-user-form-holder').height()<190){
						$('.edit-user-form-holder').height($('.new-user-form-holder').height()+35);
					}
					$('#span_message_cliente_modal').html(data.error);
					$('#message_div_cliente_modal').css('display','block');
				}
			  },
			  
			  error:function(data, textStatus, jqXHR){
				  if (errorThrown.length > 0){
						$('#span_message_cliente_modal').html(errorThrown);
						$('#message_div_cliente_modal').addClass('error').removeClass('success');
					}
			  }
		});
		
	}
}

$(function() {
	
	$('#edit-client').on('loaded.bs.modal', function () {
		editRowCliente(id);
	})
	
	$('.client_box').on('click', function(e) {
		var id = this.getAttribute("data-id");
		window.location.replace("../clientProfile.do?id="+id);
	});
	
	$('#deleteCliente').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = "/clienteServlet?";
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
	
	
	
	$("#submit_client_form_modal").on('click',function(e) {
		e.preventDefault(); //STOP default action
		var $form = $("#new-client-form-modal");
		
		var params = getClientData($form);
		
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
						/*var html=generateRowCliente(postData,data);
						
						$('#myTable').prepend(html);
						
						$('#myTable').paginateMe({
							pagerSelector : '#myPager',
							showPrevNext : true,
							hidePageNumbers : false,
							perPage : 10
						});*/
						
						var options = $("#input_cliente");
						
						 options.append($("<option selected/>").val(data.id).text($('#client_name').val()));
								
						
						options.selectpicker('refresh');
						
						$('#message_div_cliente').removeClass("error").addClass("success");
						if ($('.new-user-form-holder').height()<190){
							$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
						}
						$('#span_message_cliente').html("El cliente se ha creado de forma correcta.");
						$('#message_div_cliente').css('display','block');
						
						resetForm($form);
						setTimeout(function() { 
							$( "#message_div_cliente" ).fadeOut( "slow", function() {
								$('#span_message_cliente').html("");
						  });
							$('#new-client').modal('toggle');	
						}, 3500);
						
					}else{
						$('#message_div_cliente').removeClass("success").addClass("error");
						if ($('.new-user-form-holder').height()<190){
							$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
						}
						$('#span_message_cliente').html(data.error);
						$('#message_div_cliente').css('display','block');
					
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
	
	$('#edit_client_form_modal').on('click',function(e){
		
		var $form = $("#edit-client-form");
		var id = $(this).data("id");
		
		//var params = getClientData($form);
		
		
		if($form.valid()){		

			var postData = $form.serialize() + "&accion=edit&id="+id;
			var formURL = $form.attr("action");
			$.ajax({
				url : formURL,
				  type: "GET",
				  data : postData,
				  success:function(data, textStatus, jqXHR) 
				  {
						//data: return data from server
					if (data.success==("true")){
						$('#message_div_cliente_modal').removeClass("error").addClass("success");
						if ($('.edit-user-form-holder').height()<190){
							$('.edit-user-form-holder').height($('.new-user-form-holder').height()+35);
						}
						
						$form.find('.form-container').find('div:not(#message_div)').hide(0);
						$('#span_message_cliente_modal').html("El cliente se ha creado de forma correcta.");
						$('#message_div_cliente_modal').css('display','block');
						$('.modal-footer').css('display','none');
						
						resetForm($form);
						setTimeout(function() { 
							$( "#message_div" ).fadeOut( "slow", function() {
								$('#span_message').html("");
						  }); 
						
						location.reload();

						}, 3500);
					}else{
						$('#message_div_cliente_modal').removeClass("success").addClass("error");
						if ($('.edit-user-form-holder').height()<190){
							$('.edit-user-form-holder').height($('.new-user-form-holder').height()+35);
						}
						$('#span_message_cliente_modal').html(data.error);
						$('#message_div_cliente_modal').css('display','block');
					}
				  },
				  error:function(data, textStatus, jqXHR){
					  if (errorThrown.length > 0){
							$('#span_message_cliente_modal').html(errorThrown);
							$('#message_div_cliente_modal').addClass('error').removeClass('success');
						}
				  }
			});
			
		}
	});
	
	$("#submit_client_form").on('click',function(e) {
		e.preventDefault(); //STOP default action
		var $form = $("#new-client-form");
		
		var params = getClientData($form);
		
		
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
						/*var html=generateRowCliente(postData,data);
						
						$('#myTable').prepend(html);
						
						$('#myTable').paginateMe({
							pagerSelector : '#myPager',
							showPrevNext : true,
							hidePageNumbers : false,
							perPage : 10
						});*/
						
						$('#message_div_cliente').removeClass("error").addClass("success");
						if ($('.new-user-form-holder').height()<190){
							$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
						}
						
						$form.find('.form-container').find('div:not(#message_div)').hide(0);
						$('#span_message_cliente').html("El cliente se ha creado de forma correcta.");
						$('#message_div_cliente').css('display','block');
						$('#buttons_new_client').css('display','none');
						
						resetForm($form);
						setTimeout(function() { 
							$( "#message_div" ).fadeOut( "slow", function() {
								$('#span_message').html("");
						  }); 
						$('#newUserButton').click();	
						location.reload();

						}, 5000);
					}else{
						$('#message_div_cliente').removeClass("success").addClass("error");
						if ($('.new-user-form-holder').height()<190){
							$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
						}
						$('#span_message_cliente').html(data.error);
						$('#message_div_cliente').css('display','block');
					
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
	
	function getClientData(form){
		
		var params = "";
		
		var cliente = $('#input_cliente').val();
		var tipo = $('#tipo').val();
		var criticidad = $('#criticidad').val();
	}
	
	
	
	$('.gestion_cliente').on('click', '.lapiz', function(e) {		
		id= $(this).attr('name');	
	});
	
	
	
	function checkPaises(paises){
		if (paises.indexOf("Argentina")!=-1){
			$('#argentina_span').addClass('checked');
		}
		if (paises.indexOf("Belgica")!=-1){
			$('#belgica_span').addClass('checked');
		}
		if (paises.indexOf("Chile")!=-1){
			$('#chile_span').addClass('checked');
		}
		if (paises.indexOf("Colombia")!=-1){
			$('#colombia_span').addClass('checked');
		}
		if (paises.indexOf("Espa")!=-1){
			$('#espania_span').addClass('checked');
		}
		if (paises.indexOf("Francia")!=-1){
			$('#francia_span').addClass('checked');
		}
		if (paises.indexOf("Italia")!=-1){
			$('#italia_span').addClass('checked');
		}
		if (paises.indexOf("Mexico")!=-1){
			$('#mexico_span').addClass('checked');
		}
		if (paises.indexOf("Peru")!=-1){
			$('#peru_span').addClass('checked');
		}
		if (paises.indexOf("Portugal")!=-1){
			$('#portugal_span').addClass('checked');
		}
		if (paises.indexOf("Reino Unido")!=-1){
			$('#reino_unido_span').addClass('checked');
		}
		if (paises.indexOf("Uruguay")!=-1){
			$('#uruguay_span').addClass('checked');
		}
		if (paises.indexOf("USA")!=-1){
			$('#usa_span').addClass('checked');
		}
		if (paises.indexOf("Venezuela")!=-1){
			$('#venezuela_span').addClass('checked');
		}
		if (paises.indexOf("Redex")!=-1){
			$('#redex_span').addClass('checked');
		}
	}
		

});