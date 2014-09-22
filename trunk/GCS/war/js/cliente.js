function drawChecks(str){
		str = str.toLowerCase();
		if (str.indexOf("argentina")!=-1){
			$('#argentina_check_modal').attr("checked","checked");
			$('#argentina_check_modal').next().addClass("checked");
		}else{
			$('#argentina_check_modal').removeClass("checked");
			$('#argentina_check_modal').next().removeClass("checked");
		}				
		if (str.indexOf("belgica")!=-1){
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
		if (str.indexOf("mexico")!=-1){
			$('#mexico_check_modal').attr("checked","checked");
			$('#mexico_check_modal').next().addClass("checked");
		}else{
			$('#mexico_check_modal').removeClass("checked");
			$('#mexico_check_modal').next().removeClass("checked");
		}
		if (str.indexOf("peru")!=-1){
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
							perPage : 5
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
							perPage : 5
						});*/
						
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
							perPage : 5
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
		
/*	function editRowCliente(id){
		var $currentRow = $('#row'+id);
		var $table = $currentRow.closest('table');
		var $previousOpenEdit = $table.find('#edit-item-holder');
		
		// Get the data from the current row
		var celdas = $currentRow.children();
		var fechaEntrada = $(celdas[0]).children().html();
		var idCliente = $(celdas[1]).children().html();
		var cliente = $(celdas[2]).children().html();
		var ref_global = $(celdas[3]).children().html();
		var tipo =  $(celdas[4]).children().html();
		var criticidad =  $(celdas[5]).children().html();
		var logo_url = $currentRow.data('logo-url');
		var workflow = $currentRow.data('workflow');
		var paises = $currentRow.data('paises');

		$.get('../html/extended-gestion-cliente.html',null,function(result) {
			// Close other editing field and show the row.
			if($previousOpenEdit.length > 0){
				$('#row'+$previousOpenEdit.data('row-id')).css({display:'table-row'});
				$previousOpenEdit.remove();
			}
			// Hide current row.
			$currentRow.css({display:'none'});
			// Adds the item holder row for editing the item.
			$currentRow.after("<tr id='edit-item-holder' class='extended-row' style='display: table-row;'><td colspan='7'><div class='extended-div'></div></td></tr>");
			var $currentOpenEdit = $table.find('#edit-item-holder');
			$currentOpenEdit.data('row-id', id);
			// Add the result to the element
			$(".extended-div").html(result);
			// The form we're editing in.
			var $editForm = $currentOpenEdit.find('form#edit-item');
			// Add info stuff ... errr ... 0_o.
			$editForm.find('#fecha_entrada_peticion_ed').val(fechaEntrada);
			
			// copia options de select de formulario de creacion
			var $tipoOptions = $('select#tipo option').clone();			
			$editForm.find('select#tipo_ext').append($tipoOptions);
			$('#tipo_ext option').first().remove();
			
			var $criticidadOptions = $('select#criticidad option').clone();
			$editForm.find('select#criticidad_ext').append($criticidadOptions);
			
			$('#logo_url_ext').val(logo_url);
			$('#id_cliente_ext').val(idCliente);
			$('#clientes_ext').val(cliente);
			$('#ref_global_ext').val(ref_global);

			$('#tipo_ext').val(tipo);
			$('#criticidad_ext').val(criticidad);
			
			$('#id_cliente_ext').text(idCliente);

			if (paises!=undefined)
				checkPaises(paises);
			
			if (workflow==true){
				$('#workflow_true').attr("checked","checked");
			}else{
				$('#workflow_false').attr("checked","checked");
			}
	
			// Activate everything.
			initDatepickers();
			initSelectpickers();
			initForms();
			initValidator();
			// Click event for the cancel button.
			$editForm.on('click', '.cancelar-ext', function (e) {
				$('#row'+$currentOpenEdit.data('row-id')).css({display:'table-row'});
				$currentOpenEdit.remove();
				enableSearch();
				return false;
				
			});
			// Click event for the save button.
			$editForm.on('click', '.guardar-ext', function (e) {
				if($editForm.valid()){
					var $editItemHolder = $(this).closest('#edit-item-holder');
					// Collect all the information.
					var id= $editItemHolder.data('row-id');
				
					var formURL = "/clienteServlet?accion=edit";
					var postData = $editForm.serialize()+"&id="+id;

					$.ajax({
						url : formURL,
						type: "POST",
						data : postData,
						success:function(data, textStatus, jqXHR) {
							enableSearch();
							location.reload();
						}
					});
				}
				return false;
			});
		});
	}
 */
});