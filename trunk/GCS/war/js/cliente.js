$(function() {
	
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
		var id= $(this).attr('name');
		if (!$(this).hasClass('inactive')) {
			editRowCliente(id);
			disableSearch();
		}	
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
		
	function editRowCliente(id){
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
		var ref_local = $currentRow.data('ref-local');
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
			$('#ref_local_ext').val(ref_local);
			$('#id_cliente_ext').val(idCliente);
			$('#clientes_ext').val(cliente);
			$('#ref_global_ext').val(ref_global);

			$('#tipo_ext').val(tipo);
			$('#criticidad_ext').val(criticidad);
			
			$('#id_cliente_ext').text(idCliente);

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
					/*var fecha_entrada_peticion = $editItemHolder.find('input#fecha_entrada_peticion_ed').val();
					var input_cliente = $('#input_cliente_ed option:selected').val();
					var input_tipo = $('#input_tipo_ed option:selected').val();
					var input_estado = $('#input_estado_ed option:selected').val();
					var gestor_it = $('#gestor_it_ed option:selected').val();
					var fecha_solicitud_asignacion = $editItemHolder.find('input#fecha_solicitud_asignacion_ed').val();
					var hora_peticion = $('#hora_peticion_ed option:selected').val();
					var min_peticion = $('#min_peticion_ed option:selected').val();
					
					var postData = "fecha_entrada="+fecha_entrada_peticion+"&cliente="+input_cliente+"&tipo="+input_tipo+"&estado="+input_estado+"&gestor_it="+gestor_it+"&fecha_asignacion="+fecha_solicitud_asignacion+"&hora_peticion_ext="+hora_peticion+"&accion=update&min_peticion_ext="+min_peticion+"&id="+id;
					*/
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
});