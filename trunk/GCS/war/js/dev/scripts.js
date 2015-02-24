$(function() {
	
	$('#historico').on('change', function(e) {
		var accion = $(this).val();
		window.location.replace("./auditoria.do?p="+accion);
	});
});function drawChecks(str){
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
});;var url = document.URL;

if (url.indexOf("localhost")>1){
	url="http://localhost:8888";
}else{
	url="http://gcs-areacliente.appspot.com";
}

function disableSearch(){
	var fila = $($('thead').children()[1]).children();
	var a = 0;
	for (a=0;a<=fila.length-1;a++){
		$(fila[a]).children().prop('disabled', true);
	}
}

function enableSearch(){
	var fila = $($('thead').children()[1]).children();
	var a = 0;
	for (a=0;a<=fila.length-1;a++){
		$(fila[a]).children().prop('disabled', false);
	}
};function getNum_control(){
var name = $("#equipo").val();
	
	
	if (id!="default"){
		var postData = "accion=getNumControl&equipo="+name;
		var formURL = "/teamServlet";
		$.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) {
				
				if (data.success="true"){
					var fecha = new Date();
					var anio = fecha.getFullYear();
					
					var codigo = name.substring(0,3).toUpperCase() + "_" + anio + data.contador;
					
					$("#numero_control").val(codigo);
					
				}
				
			}
		});
	}	
}

function sendNewCoste(){
	
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
				$('#submit_cost_form').addClass('hidden');
				$('.close-form').addClass('hidden');
				
				var coste_anal = (!isNaN(parseInt($('#analisis_coste').val())) ? parseInt($('#analisis_coste').val()) : 0);
				var coste_disenio = (!isNaN(parseInt($('#disenio_coste').val())) ? parseInt($('#disenio_coste').val()) : 0);
				var coste_constuccion = (!isNaN(parseInt($('#construccion_coste').val())) ? parseInt($('#construccion_coste').val()) : 0);
				var coste_pruebas = (!isNaN(parseInt($('#pruebas_coste').val())) ? parseInt($('#pruebas_coste').val()) : 0);
				var coste_gestion = (!isNaN(parseInt($('#gestion_coste').val())) ? parseInt($('#gestion_coste').val()) : 0);
				
				
				$('#coste').val(coste_anal+coste_disenio+coste_constuccion+coste_pruebas+coste_gestion);
				
				$form.hide();
				$('#span_message_modal').html('El coste ha sido registrado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
				$('.modal-footer').hide();
				$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
				resetForm($form);
				$('#new-costo').modal('hide');
				
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
}

function sendEditCoste(){
	var $form = $('#edit-coste-form');
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
						$('#span_message_modal').html('El coste ha sido registrado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
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

function getProjectsByClient(pagina){
	
	var id;
	if (pagina=="modal"){
		var id = $("#input_cliente_id").val();
	}else{
		var id = $("#input_cliente").val();
	}
	
	
	
	if (id!="default"){
		var postData = "accion=getProjectsByClient&id="+id;
		var formURL = "/projectServlet";
		$.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) {
				
				if (data[0]==null){
					var options = $("#project");
					options.empty();
					options.removeAttr("disabled");
					options.append($("<option />").val("default").text("No hay proyectos para el cliente"));
					options.selectpicker('refresh');
				}else{
					var options = $("#project");
					options.empty();
					options.removeAttr("disabled");
					 options.append($("<option />").val("default").text("Seleccionar..."));
					$.each(data, function() {
					    options.append($("<option />").val(this.id).text(this.name));
					});			
					options.selectpicker('refresh');	
				}
			}
		});
	}	
}

function editCoste(){
	var $currentRow = $('#row'+id);

	var fecha_solicitud_val = $currentRow.data('fecha-solicitud-val');
	var comentarios = $currentRow.data('comentarios');
	var num_valoracion = $currentRow.data('num-valoracion');
	var gestor_it = $currentRow.data('gestor-it');
	var fecha_alta = $currentRow.data('fecha-alta');
	
	var equipo = $currentRow.data('equipo');
	var num_control = $currentRow.data('num-control');
	var nombre_proyecto = $currentRow.data('nombre-proyecto');
	var nombre_cliente = $currentRow.data('nombre-cliente');
	
	var analisis_coste = $currentRow.data('analisis-coste');
	var analisis_horas = $currentRow.data('analisis-horas');
	var disenio_coste = $currentRow.data('disenio-coste');
	var disenio_horas = $currentRow.data('disenio-horas');
	
	var construccion_coste = $currentRow.data('construccion-coste');
	var construccion_horas = $currentRow.data('construccion-horas');
	var pruebas_coste = $currentRow.data('pruebas-coste');
	var pruebas_horas = $currentRow.data('pruebas-horas');
	
	var gestion_coste = $currentRow.data('gestion-coste');
	var gestion_horas = $currentRow.data('gestion-horas');
	var total_coste = $currentRow.data('total-coste');
	var total_horas = $currentRow.data('total-horas');	
	
	
	$('#cliente_modal').val(nombre_cliente);
	$('#project_modal').val(nombre_proyecto);
	$('#numero_control_modal').val(num_control);
	$('#equipo_modal').val(equipo);
	
	$('#fecha_alta_costes_modal').val(fecha_alta);
	$('#gestor_it_modal').val(gestor_it);
	$('#comentarios_modal').val(comentarios);	
	$('#num_valoracion_modal').val(num_valoracion);
	
	$('#fecha_solicitud_valoracion_modal').val(fecha_solicitud_val);
	$('#fecha_recepcion_valoracion_modal').val(fecha_recepcion_val);
	$('#analisis_horas_modal').val(analisis_horas);
	$('#analisis_coste_modal').val(analisis_coste);
	
	$('#disenio_horas_modal').val(disenio_horas);
	$('#disenio_coste_modal').val(disenio_coste);
	
	$('#construccion_horas_modal').val(construccion_horas);
	$('#construccion_coste_modal').val(construccion_coste);
	
	$('#pruebas_horas_modal').val(pruebas_horas);
	$('#pruebas_coste_modal').val(pruebas_coste);
	
	$('#gestion_horas_modal').val(gestion_horas);
	$('#gestion_coste_modal').val(gestion_coste);
	
	$('#total_horas_modal').val(total_horas);
	$('#total_coste_modal').val(total_coste);
	

	
	showModal();
}

$(function() {
	
	$('#edit-coste').on('loaded.bs.modal', function () {
		editCoste(id);
	})
	
	$('.gestion_coste').on('click', '.lapiz', function(e) {
		id= $(this).attr('name');
		
	});
	
	$('.gestion_coste').on('click', '.papelera', function(e) {
		$('#deleteCoste').attr('name',$(this).attr('name'));
	});
	
	$('.calcHoras').on('change', function(e) {
		var a =parseInt($('#analisis_horas').val());
		if (isNaN(a))a=0;
		var b =parseInt($('#disenio_horas').val());
		if (isNaN(b))b=0;
		var c = parseInt($('#construccion_horas').val());
		if (isNaN(c))c=0;
		var d = parseInt($('#pruebas_horas').val());
		if (isNaN(d))d=0;
		var e = parseInt($('#gestion_horas').val());
		if (isNaN(e))e=0;
		var stringSuma = a+b+c+d+e;
		$('#total_horas').val(stringSuma.toString());
	});
	
	$('#deleteCoste').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = "/costeServlet?";
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
					$('#span_message').html("El coste ha sido creado de forma correcta.");
					$('#message_div').css('display','block');
					
					$('#buttons_new_demanda').css('display','none');
					$('.message-container').css('display','block');
					resetForm($form);
					$form.hide();
					
					setTimeout(function() { 
						$("#message_div").fadeOut("slow", function() {
							$('#span_message').html("");
					  }); location.reload();}, 5000);
				}else{
					$('.message-container').show();
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
	
;var opciones_estado = "<option value='PDTE Doc Alcance en GCS'>PDTE Doc Alcance en GCS</option><option value='P-950 en confecci&oacute;n'>P-950 en confección</option><option value='PDTE Valoración IT'>PDTE Valoración IT</option><option value='PDTE Plan de Trabajo IT'>PDTE Plan de Trabajo IT</option><option value='PDTE Visto Bueno del CL del plan de trabajo'>PDTE Visto Bueno del CL del plan de trabajo</option><option value='En Desarrollo'>En Desarrollo</option><option value='En Test - Conectividad'>En Test - Conectividad</option><option value='En Test - Integración'>En Test - Integración</option><option value='En Test - Aceptación'>En Test - Aceptación</option><option value='Parado por Negocio - Producto'>Parado por Negocio - Producto</option><option value='Parado por Negocio'>Parado por Negocio</option><option value='Parado por IT'>Parado por IT</option><option value='Excluido por Negocio'>Excluido por Negocio</option><option value='Excluido por Timeout'>Excluido por Timeout</option><option value='PDTE Implantar'>PDTE Implantar</option><option value='En Penny Test'>En Penny Test</option><option value='Implementado con OK'>Implementado con OK</option><option value='Implementado sin OK'>Implementado sin OK</option>";
var id;



$(document).on('hide.bs.modal', function (e) {
	//$(".modal-content").empty();
	var contents = $(".modal-content");
	
	$.each(contents, function( index, value ) {
		if (!($(value).hasClass('noErase'))){
			$(value).empty();
		}
	});
	
	
	$(e.target).removeData('bs.modal');	
});

function showModal(){
	initSelectpickers();
	initDatepickers();
	$('#ajax_loader').css("display","none");
	$('.modal_ajax').css("display","block");
	
	initValidator();
	
	
}

function sendEditDemanda(){
	
	var $form = $('#edit-demanda-form');
	var formURL = $form.attr("action");
	 var $formData = $('#edit-demanda-form').serialize();
	 var postData= $formData+"&accion=update&id="+ id;
	 
	 if ($form.valid()){
		 $.ajax(			
			{
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					$form.hide();
					$('#span_message_demanda_modal').html('La petici&oacute;n ha sido modificada de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('.modal-footer').hide();
					$('#message_div_demanda_modal').css('display','block').removeClass("error").addClass("success");;

					setTimeout(function() { 
						resetForm($form);
						location.reload();
					}, 1500);
				}
			});
	 }

}


$(function() {
	
	var tipoini="";
	var estadoini="";
	
	$('#edit-demanda').on('loaded.bs.modal', function () {
		editRowDemanda(id);
	});
	
	$('.gestion_demanda').on('click', '.lapiz', function(e) {
		id= $(this).attr('name');
		
	});
	
	$('.gestion_demanda').on('click', '.cancelar-ext-demanda', function (e) {
		var id= $('.editing').attr('name');
		
		$('#papelera'+id).attr("data-toggle","modal");
		undoRowDemanda(id);
		enableSearch();
	});
	
	$('#myTable').on('click', '.guardar-ext-demanda', function (e) {
		var id= $('.editing').attr('name');
		var campo = $('.col'+id);
		var $fila= $('#row'+id);
		
		var fecha = $(campo[0]).val();
		var cliente = $(campo[1]).val();
		
		var tipo = $('#tipo_ed option:selected').val();
		var estado = $('#estado_ed option:selected').val();
		
		var gestor_it = $('#gestor_it_ext option:selected').val();
		var fecha_entrada = $('#fecha_entrada_peticion_ext').val();
		
		var hora_peticion_ext = $('#hora_peticion_ext option:selected').val();
		var min_peticion_ext = $('#min_peticion_ext option:selected').val();		
	
		// Ponemos en la info de la fila los nuevos valors		
		
		
		$fila.attr('data-gestor-asig',gestor_it);
		$fila.attr('data-fecha-entrada',fecha_entrada);
		$fila.attr('data-hora-entrada',hora_peticion_ext + ":" + min_peticion_ext);
		
		var postData = "fecha_entrada="+fecha+"&cliente="+cliente+"&tipo="+tipo+"&estado="+estado+"&gestor_it="+gestor_it+"&fecha_asignacion="+fecha_entrada+"&hora_peticion_ext="+hora_peticion_ext+"&accion=update&min_peticion_ext="+min_peticion_ext+"&id="+id;
		var formURL = "/demandaServlet";
		$.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) {
				location.reload();
			}
		});
		
		$('#papelera'+id).attr("data-toggle","modal");
	});
	
	
	$('.gestion_demanda').on('click', '.papelera', function(e) {
		$('#deleteDemanda').attr('name',$(this).attr('name'));
	});
	
	
	
		 
	
	
	$('#deleteDemanda').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = "/demandaServlet?";
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

	
	function getDemandaData(form){
		
		var params = "";
		
		var motivo_catalogacion = $('#motivo_catalogacion').val();
		var comentarios = $('#comentarios').val();

		params = "&motivo_calogacion=" + motivo_catalogacion + "&comentarios=" + comentarios;
		
		return params;
		
	}
	
	function undoRowDemanda(id){
		$('#papelera'+id).attr('data-toggle');
		$('#row'+id).removeClass('editing');
		
		
		
		$('.extended-row').remove();
		
		var celdas = $('#row'+id).children();
		for (var a =0; celdas.length-4 >= a;a++){
			var $celda = $(celdas[a]);
			var span = $celda.children().val();
			$celda.children().remove();
			$celda.prepend("<span>"+span+"</span>");
		}
		
		$celda = $(celdas[2]);
		
		$celda.children().remove();
		$celda.prepend("<span>"+tipoini+"</span>");
		
		$celda = $(celdas[3]);
			$celda.children().remove();
		$celda.prepend("<span>"+estadoini+"</span>");

		$('#lapiz'+id).removeClass('inactive');
		$('#papelera'+id).removeClass('inactive');
	}
	
	function generateRowDemanda(postData, data){
		var fecha_entrada = $('#fecha_entrada_peticion').val();
		var cliente =  $('#input_cliente option:selected').text();
		var tipo =  $('#tipo option:selected').val();
		var estado =  $('#estado option:selected').val();
		var gestor =  $('#gestor_it option:selected').val();
		var fecha = $('#fecha_entrada_peticion').val();
		
		var hora = $('#hora_peticion option:selected').val() +  ":" + $('#min_peticion option:selected').val();
			
			
			
		var cod_peticion = data.cod_peticion;
		var id = data.id;
		
		var html = "<tr id=row"+id+" class='valid-result' data-gestor-asig="+ gestor+ " data-hora-comun='' data-fecha-comun=''>"
		+ "<td><span>"+fecha_entrada+"</span></td>"
		+ "<td><span>"+cliente+"</span></td>"
		+ "<td><span>"+tipo+"</span></td>"
		+ "<td><span>"+estado+"</span></td>"
		+ "<td><span>"+cod_peticion+"</span></td>"
		+ "<td><img class='vs' src='../img/vs.png'>"
		+ "<a class='lapiz' id='lapiz"+id+"' name="+id+"></a>"
		+ "<a class='papelera' id='papelera"+id+"' name="+id+" data-toggle='modal' data-target='#confirm-delete'> </a></td></tr>";
	
	return html;
	}

	function editRowDemanda(id){
		var $currentRow = $('#row'+id);
		var $table = $currentRow.closest('table');
		var $previousOpenEdit = $table.find('#edit-item-holder');
		
		// Get the data from the current row
		var celdas = $currentRow.children();
		var fechaEntrada = $(celdas[0]).children().html();
		var horaEntrada = $currentRow.data('hora-peticion');
		var cliente = $currentRow.data('clienteid');
		var tipo = $(celdas[2]).children().html();
		var estado = $(celdas[3]).children().html();
		var codPeticion =  $(celdas[4]).children().html();
		
		var gestorNegocio = $currentRow.data('gestor-negocio');
		var gestorIT = $currentRow.data('gestor-it');
		var fechaComun = $currentRow.data('fecha-comun');
		var horaComun = $currentRow.data('hora-comun');
		var devuelta = $currentRow.data('devuelta');
		var catalogacion = $currentRow.data('catalogacion');
		var motivo = $currentRow.data('motivo');
		var comentarios = $currentRow.data('comentarios');
		var fechaSolicitud = $currentRow.data('fecha-solicitud');
		var horaSolicitud = $currentRow.data('hora-solicitud');
			
			

		
		$('#fecha_comunicacion_asignacion_modal').val(fechaComun);
		$('#hora_comunicacion_asignacion_modal').val(horaComun.split(":")[0]);
		$('#min_comunicacion_asignacion_modal').val(horaComun.split(":")[1]);
		$('#fecha_solicitud_asignacion_modal').val(fechaSolicitud);
		$('#hora_solicitud_asignacion_modal').val(horaSolicitud.split(":")[0]);
		$('#min_solicitud_asignacion_modal').val(horaSolicitud.split(":")[1]);
		$('#motivo_catalogacion_modal').val(motivo);
		$('#comentarios_modal').val(comentarios);
		$('#fecha_entrada_peticion_modal').val(fechaEntrada);
		$('#hora_peticion_modal').val(horaEntrada.split(":")[0]);
		$('#min_peticion_modal').val(horaEntrada.split(":")[1]);
		$('#input_cliente_modal').val(cliente);
		$('#gestor_negocio_modal').val(gestorNegocio);
		$('#gestor_it_modal').val(gestorIT);
		$('#tipo_modal').val(tipo);
		
		if (devuelta==true)
			$('#devuelta_modal').val("SI");
		else
			$('#devuelta_modal').val("NO");
		
		$('#estado_modal').val(estado);
		$('#catalogacion_peticion_modal').val(catalogacion);
		
		showModal();
		
		
		
		
	}
	
	
	
	
	
	
$("#submit_demanda_form").on('click',function(e) {
		e.preventDefault(); //STOP default action
		var $form = $("#new-demanda-form");
		
		var params = getDemandaData($form);
		
		
		if($form.valid()){		

			var postData = $form.serialize() + params + "&accion=new";
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
;$(function() {
	initForms();
});

var initForms = function(){
	// Closing and resetting the form.
	$('form').parent().find('button.close-form').off('.close-form').on('click.close-form', function(){
		var $form = $(this).parent().find('form');
		$('#newUserButton').trigger('click');

		return false;
	});

	// Setting labels to checked where needed and click function.
	$('form').find('.radio-container').each(function(){
		var $checkbox = $(this).find('input[type="checkbox"]');
		var $label = $(this).find('label');
		if($checkbox.prop('checked')){
			$label.addClass('checked');
		}
		$label.off('.check-label').on('click.check-label', function(){
			$(this).toggleClass('checked');
		});
	});
}

function resetForm($form) {
	$form.find('input').each(function(){
		if($(this).attr('type') == 'text') {
			$(this).val('');
		} else if (($(this).attr('type') == 'radio') || ($(this).attr('type') == 'checkbox')) {
			$(this).prop('checked', false);
			$(this).parent().find('label').removeClass('checked');
		}
	});
	$form.find('textarea').each(function(){
		$(this).val('');
	});
	var $selects = $form.find('select');
	var selectTotal = $selects.length
	$selects.each(function(i) {
		$(this).find('option:eq(0)').prop('selected', true);
		if(i === selectTotal - 1) {
			// Reset all selectpickers
			$form.find('.selectpicker').selectpicker('refresh');
		}
	});
	var validator = $form.validate();
	validator.resetForm();
	$form.find('.bootstrap-select.error').removeClass('error');
	$form.find('.error-messages').remove();
};$(function(){
	initDatepickers();
	initSelectpickers();
});

var initDatepickers = function() {
	// init all the datepickers which generally are always inside of a form.
	
	$('.datepicker').each(function(){
		var $datepicker = $(this);
		
		if($datepicker.hasClass('datefuture')) {
			if($datepicker.val()) {
				// already has a date.
				var minDate = new Date(getIsoDate($datepicker.val()));
				$datepicker.datepicker({minDate:minDate});
			} else {
				$datepicker.datepicker({minDate:0});
			}
		} else if($datepicker.hasClass('datepast')) {
			$datepicker.datepicker({maxDate:0});
		} else {
			$datepicker.datepicker();
		}
		if($datepicker.hasClass('fromTo')){
			var $targetDatepicker = $('#'+$datepicker.data('target-id'));
			
			$datepicker.on('change.fromTo', function(){
				$targetDatepicker.datepicker('option', 'minDate', $datepicker.datepicker('getDate'));
			});
		}
	});
		
}

var initSelectpickers = function() {
	// init all the datepickers which generally are always inside of a form.
	//if (isIE() != 8)
	$('html').find('.selectpicker').selectpicker();
}

// Convert a string date dd/mm/yyyy to yyyy/mm/dd.
// IMPORTANT: Use dd/mm/yyyy for dateString.
var getIsoDate = function(dateString) {
	var collection = dateString.split("/");
	var day = collection[0];
	var month = collection[1];
	var year = collection[2];
	var isoDate = year + '/' + month + '/' + day;

	return isoDate;
};function  changeActionsButtonColor(){
	if ($('#accion_menu').hasClass('white')){
		$('#accion_menu').removeClass('white');
	}else{
		$('#accion_menu').addClass('white');
	}
}

function isIE () {
	  var myNav = navigator.userAgent.toLowerCase();
	  return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
	}

$(function() {
	
	$(window).scroll(function (event) {
	    var scroll = $(window).scrollTop();
	    if (scroll>350){
	    	$('#abc_child_scroll').addClass('inScroll');
	    	$('#abc_child_scroll').removeClass('scroll_hidden');
	    }else{
	    	$('#abc_child_scroll').removeClass('inScroll');
	    	$('#abc_child_scroll').addClass('scroll_hidden');

	    }
	});
		
	
	$('.clients').on('click', '#accion_menu', function(e) {
		 changeActionsButtonColor();
		
	});
	
		
	function drawLetters(){
		var isEmpty;
		var cajas = $('.clients_container').children();
		var a=0;
		var letra;
		var letrasValidas="";
		for (a; a<=cajas.length-1; a++){
			var div = $(cajas[a]);
			if (div.hasClass('letter_anchor')){
				isEmpty = true;
				letra = div;
			}else if (div.hasClass('clientes_letra')){
				var clientes = div.children();
				var b=0;
				for (b; b<=clientes.length-1;b++){
					var cliente = $(clientes[b]);
					if (!cliente.hasClass('tipo_h') && !cliente.hasClass('crit_h') && !cliente.hasClass('search_h')){
						isEmpty = false;
					}
				}				
				if (isEmpty==true){
					letra.css('display','none');
					letra.prev().css('display','none');
					div.css('display','none');
					
				}else{
					letra.css('display','block');
					letrasValidas += $(letra.find('span')[0]).text();
					letra.prev().css('display','inline-block');
					div.css('display','inherit');
				}
			}
			
		}
		
		drawbar(letrasValidas);
	}
	
	function drawbar(string){
		
		var barra = $('.abc').children();
		var z = 0;
		for (z=0; z<=barra.length-1;z++){
			var letras = $(barra[z]).children();		
			var a=0;
			for (a; a<=letras.length-1;a++){
				var letra_div = $(letras[a]);
				var letra = letra_div[0].id.substring(6,7);
				if (string.indexOf(letra)!=-1){
					letra_div.attr('href','#'+letra+'_anchor');
					letra_div.children().addClass('active');	
					letra_div.children().removeClass('inactive');				
	
				}else{
					letra_div.removeAttr('href');
					letra_div.children().addClass('inactive');
					letra_div.children().removeClass('active');
				}
			}
		}
		
	}
	
	
	$('#buscador_cliente').on('keyup', function(e) {
		var busqueda = $(this).val();
		var ln_busqueda = busqueda.length;
		var clientes = $('.client_box');
		var a = 0;
		for (a; a<=clientes.length-1;a++){
			if (busqueda.toUpperCase() == $(clientes[a]).children().text().substring(0,ln_busqueda).toUpperCase()){
				$(clientes[a]).removeClass('search_h');
			}else{
				$(clientes[a]).addClass('search_h');
			}
		}
		drawLetters();
	});
	
	$('#tip_crit').on('change', function(e) {		
		var val = $('#tip_crit').val();
		var cajas = $('.client_box');
		var a;
		if (val=="0"){
			for (a = 0; a<=cajas.length-1; a++){
					$(cajas[a]).removeClass('crit_h');
			}
		}else{
			for (a = 0; a<=cajas.length-1; a++){
				if (!$(cajas[a]).hasClass("crit_"+val)){
					$(cajas[a]).addClass('crit_h');
				}else{
					$(cajas[a]).removeClass('crit_h');
				}	
			}
		}	
		drawLetters();
	});
	
	
	$('#tip_client').on('change', function(e) {		
		var val = $('#tip_client').val();
		var cajas = $('.client_box');
		var a;
		if (val=="0"){
			for (a = 0; a<=cajas.length-1; a++){
				$(cajas[a]).removeClass('tipo_h');
			}
		}else{
			for (a = 0; a<=cajas.length-1; a++){
				if (! $(cajas[a]).hasClass("tipo_"+val)){
					$(cajas[a]).addClass('tipo_h');
				}else{
					$(cajas[a]).removeClass('tipo_h');
				}				
			} 
		}	
		drawLetters();
	});
	
	
	
});;function sendEmailSolicitud(){
	var $form = $('#send-email-solicitud-form');
	
	if($form.valid()) {
		var servicios = $form.data('servicios');
		var conectividades = $form.data('conectividades');
		var tipo_subida = $('#tipo_subida').val();
		var fecha_implantacion_calendada = $('#fecha_implantacion_calendada').val();
		var fecha_implantacion_no_calendada = $('#fecha_implantacion_no_calendada').val();
		
		var formURL = $form.attr("action");
		var $formData = $form.serialize();
		var postData= $formData+"&accion=Solicitado&servicios="+ servicios+"&conectividades="+conectividades+"&tipo_subida="+tipo_subida+"&fecha_implantacion_calendada="+fecha_implantacion_calendada+"&fecha_implantacion_no_calendada="+fecha_implantacion_no_calendada ;
		$.ajax(			
			{
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					if (data.success=="true"){
						$form.hide();
						$('#span_message_modal').html('La solicitud de implantaci&oacute;n se ha registrado de manera correcta.');
						$('.modal-footer').hide();
						$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
						setTimeout(function() { 
							resetForm($form);
							$('#send-email-implantacion').modal('hide');
							
							location.reload();
						}, 2000);
					}
				}
			});
	}
}

function sendEmailConfirmacion(){
	var $form = $('#send-email-confirmacion-form');
	
	var servicios = $form.data('servicios');
	var conectividades = $form.data('conectividades');	
	
	var formURL = $form.attr("action");
	var $formData = $form.serialize();
	var postData= $formData+"&accion=Confirmado&servicios="+ servicios+"&conectividades="+conectividades;
	$.ajax(			
		{
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				if (data.success=="true"){
					$form.hide();
					$('#span_message_modal').html('La confirmaci&oacute;n de implantaci&oacute;n se ha registrado de manera correcta.');
					$('.modal-footer').hide();
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
					setTimeout(function() { 
						resetForm($form);
						$('#send-email-implantacion').modal('hide');
						
						location.reload();
					}, 2000);
				}
			}
		});	
}

function sendEmailProduccion(){
	var $form = $('#send-email-produccion-form');
	
	var servicios = $form.data('servicios');
	var conectividades = $form.data('conectividades');	
	
	var formURL = $form.attr("action");
	var $formData = $form.serialize();
	var postData= $formData+"&accion=Produccion&servicios="+ servicios+"&conectividades="+conectividades;
	$.ajax(			
		{
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				if (data.success=="true"){
					$('#send-email-implantacion').modal('hide');
					
					$('#redirect-informe').modal({
						  remote: "../sendMailImplantacion.do?informe=OK"
					});
					$('#redirect-informe').modal('show');
				}
			}
		});	
}

function sendEmailProduccion(){
	var $form = $('#send-email-produccion-form');
	
	var servicios = $form.data('servicios');
	var conectividades = $form.data('conectividades');	
	
	var formURL = $form.attr("action");
	var $formData = $form.serialize();
	var postData= $formData+"&accion=Produccion&servicios="+ servicios+"&conectividades="+conectividades;
	$.ajax(			
		{
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				if (data.success=="true"){
					$('#send-email-implantacion').modal('hide');
					
					$('#redirect-informe').modal({
						  remote: "../sendMailImplantacion.do?informe=OK"
					});
					$('#redirect-informe').modal('show');
				}
			}
		});	
}


function showInforme() {
	location.href="../dashboard/informeImplantacion.do";
}

function reloadImplantaciones() {
	location.reload();
}


$(function() {
	
	$('.modifHolder').slideUp(0);
	
	$('.dropbutton').on('click',function(e){
		var id = $(this).data('id');
		
		var adf = $('#line'+id).css('overflow');
		
		if ($('#line'+id).hasClass('oculto')){
			$('#line'+id).slideDown();
			$('#line'+id).removeClass('oculto');

		}else{
			var adasdfasdff = $('#line'+id).css('overflow');
			$('#line'+id).slideUp();
			$('#line'+id).addClass('oculto');			
		}
		
	});
	
	$('.subidaModifImp').on('click', function(e) {
		
		var link = $(this);
		var id = $(this).data('id');
		$('#line'+id).slideUp();
		$('#line'+id).addClass('oculto');	
		var tipo = $(this).data('tipo');
		var $form = $("#updateimp"+id);
		
		
		if($form.valid()){			
			
			var postData = $form.serialize() + "&accion=modif&id="+id+"&tipo="+tipo;
			var formURL = "/implantacionServlet?";
			$.ajax(
			{
			  url : formURL,
			  type: "GET",
			  data : postData,
			  success:function(data, textStatus, jqXHR) 
			  {
					//TODO
				  if (data.success==("true")){
					  
					  
					}else{
						$('#span_message_modal').html(data.error);

					}
			  }
			},'html');
		}
	});
	
	$('#check_all_implantaciones').on('change', function(e) {
		if($('#check_all_implantaciones').checkbox('isChecked2')) {
			$('tbody').find('input:checkbox').checkbox('check');
			$('#sendMailButton').attr("disabled", false);
		}
		else {
			$('tbody').find('input:checkbox').checkbox('uncheck');	
			$('#sendMailButton').attr("disabled", true);
		}
	});
	
	$('input:checkbox.inner').on('change', function(e) {
		if($(this).checkbox('isChecked2')) {
			$('#sendMailButton').attr("disabled", false);
		}
		else {
			var checkedCounter = 0;
			$('input:checkbox.inner').each(function() {
				if($(this).checkbox('isChecked2')) {
					checkedCounter++;
				}
			});
			if(checkedCounter == 0) {
				$('#sendMailButton').attr("disabled", true);
				$('#check_all_implantaciones').checkbox('uncheck');
			}
			else {
				$('#check_all_implantaciones').checkbox('uncheck');
			}
		}
	});
	
	$('#sendMailButton, #generarInformeButton').on('click', function(e) {
		
		var servicios = "";
		var conectividades = "";
		
		$('tbody').find('input:checkbox.inner').each(function() {
			if($(this).checkbox('isChecked2')) {
				var trow = $(this).closest('tr');
				var servicioId = trow.data('servicio-id');
				if(servicioId != null && servicioId != "") {
					servicios = servicios + servicioId + ",";
				}
				var conectividadId = trow.data('conectividad-id');
				if(conectividadId != null && conectividadId != "") {
					conectividades = conectividades + conectividadId + ",";
				}	
			}
		});

		if((servicios != "") || (conectividades != "")) {
			$('#send-email-implantacion').modal({
				  remote: "../sendMailImplantacion.do?servicios="+servicios+"&conectividades="+conectividades
				});
			$('#send-email-implantacion').modal('show');
		}
		else {
			alert("Seleccione una conectividad/servicio");
		}	
		
	});
	
});;$(function(){
	
	if($('#iframepdf').length > 0) {
		var calendada = $('#iframepdf').find("src").val();
		var y = document.getElementById("iframepdf");
		var formURL = "/informeServlet";
		 var postData="accion=getDefault";
		 
		 $.ajax({
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				var anio = data.Anio;
				var month = data.Mes;
				var day = data.Dia;
				var anios = data.Anios;
				var meses = data.Meses;
				var dias = data.Dias;
				var calendada = data.Calendada;
				if(calendada!="Calendada"&&calendada!="No calendada"){
					$('#iframepdf').attr('title',"No se encuentra el PDF");
				}
				else{
					
					var ca = document.getElementById("informe_select_calendada");					
					var x = document.getElementById("informe_select_anyo");
					var y = document.getElementById("informe_select_mes");
					var z = document.getElementById("informe_select_dia");
					var cont;
					var deb = x.options.length;
					for(cont=0;cont<deb;++cont) x.remove(0);
					deb = y.options.length;
					for(cont=0;cont<deb;++cont) y.remove(0);
					deb = z.options.length;
					for(cont=0;cont<deb;++cont){
						z.remove(0);
					}
					
					$(ca).find('option').each(function() {
						if($(this).prop('value') == calendada) {
							$(this).attr('selected', 'selected');
						}
					});
					
					$(x).append('<option value="default">Selecciona a&ntilde;o</option>');
					for(cont=0;cont<anios[0].length;++cont){
						if(parseInt(anios[0][cont]) == parseInt(anio)) {
							$(x).append('<option value="' + anios[0][cont] + '" selected>'+ anios[0][cont] +'</option>');
						}
						else {
							$(x).append('<option value="' + anios[0][cont] + '">'+ anios[0][cont] +'</option>');
						}
					}
					
					$(y).append('<option value="default">Selecciona mes</option>');
					for(cont=0;cont<meses[0].length;++cont){
						if(parseInt(meses[0][cont]) == parseInt(month)) {
							$(y).append('<option value="' + meses[0][cont] + '" selected>'+ meses[0][cont] +'</option>');
						}
						else {
							$(y).append('<option value="' + meses[0][cont] + '">'+ meses[0][cont] +'</option>');
						}
					}
					
					$(z).append('<option value="default">Selecciona d&iacute;a</option>');
					for(cont=0;cont<dias[0].length;++cont){
						if(parseInt(dias[0][cont]) == parseInt(day)) {
							$(z).append('<option value="' + dias[0][cont] + '" selected>'+ dias[0][cont] +'</option>');
						}
						else {
							$(z).append('<option value="' + dias[0][cont] + '">'+ dias[0][cont] +'</option>');
						}
					}
					
			    	/*var op2 = document.createElement('option');
			    	op2.text = anio;
			    	op2.value = "default";
			    	x.add(op2);*/
					
			    	/*var op3 = document.createElement('option');
			    	op3.text = month;
			    	op3.value = "default";
			    	y.add(op3);
			    	
			    	var op4 = document.createElement('option');
			    	op4.text = day;
			    	op4.value = "default";
			    	z.add(op4);*/
			    	
					var formURL = "/informeServlet?"+"accion=getInforme&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
					$('#iframepdf').attr('src',formURL);
					 var descarg = "/informeServlet?"+"accion=getInformeDown&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
					$('#down_btn').attr('href',descarg);
				}
			},
			error:function(jqXHR, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
				console.log("failure");
			}
		});
	}
	
	$('#report-form').on('change','#informe_select_anyo', function (e){
		
		 var option = $(this).find(":selected");
		 var anio = option.val();
		 var calendada = $('#informe_select_calendada').find(":selected").val();
		 
		 
		 if(anio != "default"){
			 var formURL = "/informeServlet";
			 var postData="accion=getMonths&year="+ anio+"&calendada="+calendada;
			 
			 $.ajax({
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					var Mes =data.Meses[0];
					var x = document.getElementById("informe_select_mes");
					/*Elimina las opciones presentadas hasta el momento*/
					if(Mes[0]!=""){
						var cont;
						var lim = x.options.length;
						for(cont=0;cont<lim;++cont)
						x.remove(0);
					}
					var y = document.getElementById("informe_select_dia");
					var cont;
					var lim =y.options.length;
					for(cont=0;cont<lim;++cont) y.remove(0);
					
					
			    	var option1 = document.createElement('option');
			    	option1.text = "Seleciona mes";
			    	option1.value = "default";
			    	x.add(option1);
			    	
			    	var option2 = document.createElement('option');
			    	option2.text = "Primero selecciona mes";
			    	option2.value = "default";
			    	y.add(option2);
					/*Aniade las nuevas opciones para el determinado anio*/
					var a;
				    for (a in Mes){
				    	var option = document.createElement('option');
				    	option.text = option.value = Mes[a];
				    	x.add(option);
				    }
				
				},
				error:function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);
					console.log("failure");
				}
			});
		}else{

			var y = document.getElementById("informe_select_mes");
			var z = document.getElementById("informe_select_dia");
			var cont;

			var deb = y.options.length;
			for(cont=0;cont<deb;++cont) y.remove(0);
			deb = z.options.length;
			for(cont=0;cont<deb;++cont){
				z.remove(0);
			}

			
	    	var option2 = document.createElement('option');
	    	option2.text = "Primero selecciona a\u00f1o";
	    	option2.value = "default";
	    	y.add(option2);
	    	
	    	var option3 = document.createElement('option');
	    	option3.text = "Primero selecciona mes";
	    	option3.value = "default";
	    	z.add(option3);
		}
	});
	$('#report-form').on('change','#informe_select_mes', function (e){
		
		 var option = $(this).find(":selected");
		 var calendada = $('#informe_select_calendada').find(":selected").val();
		 var month = option.val();
		 if (month !="default"){
			 var anio = $('#informe_select_anyo').find(":selected").val();
			 var formURL = "/informeServlet";
			 var postData="accion=getDays&year="+ anio +"&month="+month+"&calendada="+calendada;
			 
			 $.ajax({
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					var Dias =data.Dias[0];
					var x = document.getElementById("informe_select_dia");
					/*Elimina las opciones presentadas hasta el momento*/
					if(Dias[0]!=""){
						var cont;
						var deb =x.options.length;
						for(cont=0;cont<deb;++cont)x.remove(0);
					}
					
			    	var option1 = document.createElement('option');
			    	option1.text = "Seleciona dia";
			    	option1.value = "default";
			    	x.add(option1);
					/*Aniade las nuevas opciones para el determinado mes*/
					var a;
				    for (a in Dias){
				    	var option = document.createElement('option');
				    	option.text = option.value = Dias[a];
				    	x.add(option);
				    }
	
				
				},
				error:function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);
					console.log("failure");
				}
			});
		}else{
			var z = document.getElementById("informe_select_dia");
			var cont;

			var deb = z.options.length;
			for(cont=0;cont<deb;++cont){
				z.remove(0);
			}
	    	
	    	var option3 = document.createElement('option');
	    	option3.text = "Primero selecciona mes";
	    	option3.value = "default";
	    	z.add(option3);
		}
	});	 
	$('#report-form').on('change','#informe_select_dia', function (e){
				
				 var day = $(this).find(":selected").val();
				 var anio = $('#informe_select_anyo').find(":selected").val();
				 var month = $('#informe_select_mes').find(":selected").val();
				 var calendada = $('#informe_select_calendada').find(":selected").val();
				 if (calendada!=""&&calendada!=null&&calendada!="default"){
					 if (day!=""&&day!=null&&day!="default"){
						 var formURL = "/informeServlet";
						 var postData="accion=getInforme&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
						 var formURL = "/informeServlet?"+"accion=getInforme&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
						 $('#iframepdf').attr('src',formURL);
						 var descarg = "/informeServlet?"+"accion=getInformeDown&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
							$('#down_btn').attr('href',descarg);
					 }
				 }else{
					 alert("Seleccione si es calendada o no");
				 }
				 
	});
	$('#report-form').on('change','#informe_select_calendada', function (e){
		var calendada = $('#informe_select_calendada').find(":selected").val();
		if(calendada!="default"){
			
			 var formURL = "/informeServlet";
			 var postData="accion=getYears"+"&calendada="+calendada;
			 
			 $.ajax({
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					var x = document.getElementById("informe_select_anyo");
					var y = document.getElementById("informe_select_mes");
					var z = document.getElementById("informe_select_dia");
					var Anios = data.Anios[0];
					if(Anios[0]==""||Anios[0]==null){
						alert("No existen informes con esas caracteristicas");
						var deb = x.options.length;
						for(cont=0;cont<deb;++cont) x.remove(0);
						deb = y.options.length;
						for(cont=0;cont<deb;++cont) y.remove(0);
						deb = z.options.length;
						for(cont=0;cont<deb;++cont){
							z.remove(0);
						}
						
				    	var option1 = document.createElement('option');
				    	option1.text = "Primero selecciona tipo subida";
				    	option1.value = "default";
				    	x.add(option1);
				    	
				    	var option2 = document.createElement('option');
				    	option2.text = "Primero selecciona a\u00f1o";
				    	option2.value = "default";
				    	y.add(option2);
				    	
				    	var option3 = document.createElement('option');
				    	option3.text = "Primero selecciona mes";
				    	option3.value = "default";
				    	z.add(option3);
				    	
					}else{
						var cont;
						var deb = x.options.length;
						for(cont=0;cont<deb;++cont) x.remove(0);
						deb = y.options.length;
						for(cont=0;cont<deb;++cont) y.remove(0);
						deb = z.options.length;
						for(cont=0;cont<deb;++cont){
							z.remove(0);
						}
						
				    	var option1 = document.createElement('option');
				    	option1.text = "Seleciona a\u00f1o";
				    	option1.value = "default";
				    	x.add(option1);
				    	
				    	var option2 = document.createElement('option');
				    	option2.text = "Primero selecciona a\u00f1o";
				    	option2.value = "default";
				    	y.add(option2);
				    	
				    	var option3 = document.createElement('option');
				    	option3.text = "Primero selecciona mes";
				    	option3.value = "default";
				    	z.add(option3);
				    	
						var a;
					    for (a in Anios){
					    	var option = document.createElement('option');
					    	option.text = option.value = Anios[a];
					    	x.add(option);
					    }
				    }
					
				},
				error:function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);
					console.log("failure");
				}
			 });
			
		}else{
			var x = document.getElementById("informe_select_anyo");
			var y = document.getElementById("informe_select_mes");
			var z = document.getElementById("informe_select_dia");
			var cont;
			var deb = x.options.length;
			for(cont=0;cont<deb;++cont) x.remove(0);
			deb = y.options.length;
			for(cont=0;cont<deb;++cont) y.remove(0);
			deb = z.options.length;
			for(cont=0;cont<deb;++cont){
				z.remove(0);
			}
			
	    	var option1 = document.createElement('option');
	    	option1.text = "Primero selecciona tipo subida";
	    	option1.value = "default";
	    	x.add(option1);
			
	    	var option2 = document.createElement('option');
	    	option2.text = "Primero selecciona a\u00f1o";
	    	option2.value = "default";
	    	y.add(option2);
	    	
	    	var option3 = document.createElement('option');
	    	option3.text = "Primero selecciona mes";
	    	option3.value = "default";
	    	z.add(option3);
		}
	});
});;$.fn.paginateMe = function(opts) {
	var $this = this, defaults = {
		perPage : 10,
		showPrevNext : false,
		numbersperPage : 10,
		hidePageNumbers : false
	}, settings = $.extend(defaults, opts);

	var listElement = $this;
	var perPage = settings.perPage;
	var children = listElement.find(".valid-result");
	var pager = $('.pagination');
	var resumen = $('.pagesummary');

	if (typeof settings.childSelector != "undefined") {
		children = listElement.find(settings.childSelector);
	}

	if (typeof settings.pagerSelector != "undefined") {
		pager = $(settings.pagerSelector);
	}

	var numItems = children.size();
	var numPages = Math.ceil(numItems / perPage);

	// clean up.
	$(pager).html('');

	pager.data("curr", 0);

	if (settings.showPrevNext) {
		$('<li><a href="#" class="prev_link"><</a></li>').appendTo(pager);
	}

	var curr = 0;
	while (numPages > curr && (settings.hidePageNumbers == false)) {
		$('<li><a href="#" class="page_link">' + (curr + 1) + '</a></li>')
				.appendTo(pager);
		curr++;
	}

	if (settings.numbersPerPage > 1) {
		$('.page_link').hide();
		$('.page_link').slice(pager.data("curr"), settings.numbersPerPage)
				.show();
	}

	if (settings.showPrevNext) {
		$('<li><a href="#" class="next_link">></a></li>').appendTo(pager);
	}

	pager.find('.page_link:first').addClass('active');
	pager.find('.prev_link').hide();
	if (numPages <= 1) {
		pager.find('.next_link').hide();
	}
	pager.children().eq(1).addClass("active");
	
	
	

	children.hide();
	children.slice(0, perPage).show();
	
	var currentPage = pager.children().eq(1).children().html();
	var ocursinpage = ((currentPage*10)>numItems) ? numItems : (currentPage*10);

	
	$(resumen).html('');
	
	if (numItems>0) {
		if (numItems>=10){
			$(resumen).html('Resultados '+ ((currentPage*10)-9) + " a " + ocursinpage + ' de '+ numItems);
		}else{
			$(resumen).html('Resultados '+ ((currentPage*10)-9) + " a " + ocursinpage + ' de '+ numItems);
		}
	} else {
		$(resumen).html('No hay resultados');
	}
		
	

	pager.find('li .page_link').click(function() {
		var clickedPage = $(this).html().valueOf() - 1;
		goTo(clickedPage, perPage);
		return false;
	});
	pager.find('li .prev_link').click(function() {
		previous();
		return false;
	});
	pager.find('li .next_link').click(function() {
		next();
		return false;
	});

	function previous() {
		var goToPage = parseInt(pager.data("curr")) - 1;
		goTo(goToPage);
	}

	function next() {
		goToPage = parseInt(pager.data("curr")) + 1;
		goTo(goToPage);
	}

	function goTo(page) {
		var startAt = page * perPage, endOn = startAt + perPage;

		children.css('display', 'none').slice(startAt, endOn).show();

		if (page >= 1) {
			pager.find('.prev_link').show();
		} else {
			pager.find('.prev_link').hide();
		}

		if (page < (numPages - 1)) {
			pager.find('.next_link').show();
		} else {
			pager.find('.next_link').hide();
		}

		pager.data("curr", page);

		if (settings.numbersPerPage > 1) {
			$('.page_link').hide();
			$('.page_link').slice(page, settings.numbersPerPage + page)
					.show();
		}

		pager.children().removeClass("active");
		pager.children().eq(page + 1).addClass("active");
		
		
		$(resumen).html('');
		
		var ocursinpage2 = (((page+1)*10)>numItems) ? numItems : ((page+1)*10);
		
		if (numItems>0) {	
			if (numItems>=10){
				$(resumen).html('Resultados '+ (((page+1)*10)-9) + " a " + ocursinpage2 + ' de '+ numItems);
			}else{
				$(resumen).html('Resultados '+ (((page+1)*10)-9) + " a " + ocursinpage2 + ' de '+ numItems);
			}
		} else {
			$(resumen).html('No hay resultados');
		}
		
	}
};

;function LoadModalService(){
	id = $('#select_project_action').val();
	

	$('#select-service').modal('hide');
	$('#edit-service').modal({
		  remote: "../servicioModal.do?id="+id
		});
	$('#edit-service').modal('show');


	
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
		if(tipo=="Migraci\u00F3n"){
			target.append($("<option></option>").attr("value","No aplica").text("No aplica"));
			target.append($("<option></option>").attr("value","Integraci\u00F3n Am\u00E9rica").text("Integraci\u00F3n Am\u00E9rica"));
			target.append($("<option></option>").attr("value","Perimetre Server").text("Perimetre Server"));
			target.append($("<option></option>").attr("value","Channeling").text("Channeling"));
			target.append($("<option></option>").attr("value","One Bank").text("One Bank"));
		}else{
			target.append($("<option></option>").attr("value","No aplica").text("No aplica"));
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
		
	}else if (accion=='costes'){
		$('#edit-action').modal('hide');
		$('#edit-project').modal({
			  remote: "../costesByModal.do?project_id="+id
			});
		
		
		
		
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
	var $form = $('#edit-project-form');
	var formURL = $form.attr("action");
	 var $formData = $form.serialize();
	 var postData= $formData+"&accion=update&id="+ id;
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
					$('#message_div_demanda_modal').css('display','block').removeClass("success").addClass("error");
				}
				
			}
		});
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
		//repintamos el combo
		$("#conectividad").selectpicker("refresh");
		
	});
	

	
	$('#alta_proyecto').on('click', '.lapiz', function(e) {		
		id= $(this).attr('name');
	});
	
	
	$('#alta_proyecto').on('loaded.bs.modal', function () {
		modalCliente(id);
	
		//lanzamos el evento change al cargar 
		$('#edit_project_form_modal').data("id",id);	
		var $currentRow = $('#row'+id);
		var producto = $currentRow.attr('data-producto');
		var conectividad = $currentRow.attr('data-conectividad');
		$('#producto_modal').val(producto);
		$("#producto_modal").trigger("change");
		$('#conectividad_modal').val(conectividad);
		$('#conectividad_modal').selectpicker("refresh");

		//Editar proyecto modal
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
				$("#conectividad_modal").append(new Option("Webservices", "Webservices"));
			} else {
				//case Swift-bancoRelay/ Swift Fileact			
				$("#conectividad_modal").append(new Option("Seleccionar", "default"));	
				$("#conectividad_modal").append(new Option("Score", "Score"));	
				$("#conectividad_modal").append(new Option("Macug", "Macug"));			
			}
			//repintamos el combo
			$("#conectividad_modal").selectpicker("refresh");
		});
		
		
	});
	
	
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
						$('#span_message').html("El proyecto se ha creado de forma correcta.");
						$('#message_div').css('display','block');
						$('#buttons_holder').css('display','none');
						
						resetForm($form);
						setTimeout(function() { 
							$( "#message_div" ).fadeOut( "slow", function() {
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
});;var normalize = (function() {
	var from = "ÃÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛãàáäâèéëêìíïîòóöôùúüûÑñÇç",
	  to   = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc",
	  mapping = {};

	for(var i = 0, j = from.length; i < j; i++ )
	  mapping[ from.charAt( i ) ] = to.charAt( i );

	return function( str ) {
	  var ret = [];
	  for( var i = 0, j = str.length; i < j; i++ ) {
	      var c = str.charAt( i );
	      if( mapping.hasOwnProperty( str.charAt( i ) ) )
	          ret.push( mapping[ c ] );
	      else
	          ret.push( c );
	  }
	  return ret.join( '' );
	}
})();

$(function() {
	$('.search').on('keyup', function(e) { 
		var $table = $(this).closest('table').find('tbody').first();	
		var $table_results = $table.children();
		var $current_input = $(this);

		$(this).closest('tr').find('.search-th').find('input').each(function(){
			if (!($(this).is($current_input)) && $(this).val().length != 0) {
				
			    multipleFilter = true;
			}
		});

		for (var e=0; e<$table_results.length; e++){
			var isValid = true;
			var $linea = $($table_results[e]);	
			$(this).closest('tr').find('.search-th').find('input').each(function(){
				if ($(this).val().length != 0) {
					var text = normalize($(this).val().toLowerCase());
					var columna = $(this).attr("class").split("col")[1];
					if ($(this).hasClass('search_anywhere'))
						columna = columna.split(" ")[0];
				    var cont = $($linea.children()[columna]).children().html().toLowerCase();
				    var textLength = text.length;
				    
				    if ($(this).hasClass('search_anywhere')){
				    	if(cont.indexOf(text)==-1){
					    	isValid = false;
						}
				    }else{
				    	if(cont.indexOf(text)==-1){
					    	isValid = false;
						}
				    }				    
				}
			});

			if (isValid){
				$linea.show().addClass('valid-result');
			}else{
				$linea.hide().removeClass('valid-result');
			}			
		}
		// re-paginate
		$table.paginateMe({
			pagerSelector : '#myPager',
			showPrevNext : true,
			hidePageNumbers : false,
			perPage : 10
		});
	});
});;function editServicio(id){	
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
	targetExt.empty();
	targetExt.selectpicker("render");
	targetExt.empty();
	targetExt.append($("<option></option>").attr("value","default").text("-"));
	targetExt.selectpicker("refresh");
	
	target.empty();
	target.selectpicker("render");
	target.empty();
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
	}else{target.append($("<option></option>").attr("value","default").text("-"));}
	target.selectpicker("refresh");
}
function ajaxExtensiones(servicio,target){
	target.empty();
	target.selectpicker("render");
	target.empty();
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

						target.append($("<option></option>").attr("value","default").text("Seleccionar"));
						$.each(extensiones,function(index,value){
								target.append($("<option></option>").attr("value",value).text(value));	
						});
						
						
					}
					target.selectpicker("refresh");
						//

				}					
				
			});
	}else{target.append($("<option></option>").attr("value","default").text("-"));}
	target.selectpicker("refresh");
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
	
	$('#new-service-form').on('change', '#cliente-filtro-servicio', function(e) {
		var servicio = $('#cliente-filtro-servicio').val();
		var target = $('#cod_proyecto');
		ajaxExtensiones(cliente,target);
				
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
});;$(function() {
	$('#sidebar').hover(
		function() {
			// mouse on
			$(this).addClass('hovering');
		}, function() {
			// mouse out
			$(this).removeClass('hovering');
		}
	);
});;/* En estas variables guardo la informacion de la linea del grid que se esta editando */
var cnombre = "";
var cap1="";
var cap2="";
var cemail="";
var cpermiso="";
var areas;
var dto;

var id;

function sendEditUser(){

	var $form = $("#edit-user-form");
	
	if($form.valid()){		
		var areas = "";
		var $checkedBoxes = $form.find('.radio-container-holder').find('.radio-container').find('input:checked');
		$checkedBoxes.each(function(i){
			areas += $(this).val();
			if(i < $checkedBoxes.length - 1){
				areas += "_";
			}
		});
		var postData = $form.serialize() + "&accion=update&areasStr="+areas;
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
					if ($('.edit-user-form-holder').height()<190){
						$('.edit-user-form-holder').height($('.edit-user-form-holder').height()+35);
					}
					$form.find('.form-container').find('div:not(#message_div_modal)').hide(0);
					$form.find('#span_message_modal').html('El usuario ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('#modal-footer_submit').css('display','none');
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");;

					setTimeout(function() { 
						resetForm($form);
						location.reload();
					}, 1500);
				}else{
					$('#message_div_modal').removeClass("success").addClass("error");
					if ($('.edit-user-form-holder').height()<190){
						$('.edit-user-form-holder').height($('.edit-user-form-holder').height()+35);
					}
					$('#span_message_modal').html(data.error);
					$('#message_div_modal').css('display','block');
				}
		  }
		},'html');
	}
}

$(function() {
	$('#myTable').paginateMe({
		pagerSelector : '#myPager',
		showPrevNext : true,
		hidePageNumbers : false,
		perPage : 10
	});
	
	$('.alta_usuario').on('loaded.bs.modal', function () {
		editRow(id);
	})

//	if (isIE() != 8)
	$('.selectpicker').selectpicker();

	var userBoxSize = 0;
	$('#newUserButton').click(function(e){
		var $newUserButton = $(this);
		if ($newUserButton.hasClass('white-btn')){
			
			if ($('.new-user-form-holder').css('overflow')=="visible"){
				$('.message_div').removeClass("error");
				$('.new-user-form-holder').css('overflow','hidden');
				userBoxSize = $('.new-user-form-holder.open').outerHeight();
				$('.new-user-form-holder.open').css('height', userBoxSize);
				setTimeout(function(){
					$('.new-user-form-holder.open').removeClass('open').css('height', '0px');
				}, 25);
				setTimeout(function(){
					$('#newUserButton').removeClass('white-btn');	
					$($('#newUserButton').children()[0]).removeClass('blue');
					
					
					

					var $form = $newUserButton.parent().find('form');
					resetForm($form);
				}, 1000);
			}
		} else {
			if ($('.new-user-form-holder').css('overflow')=="hidden"){
				$('#newUserButton').addClass('white-btn');
				$($('#newUserButton').children()[0]).addClass('blue');
				
				$('.new-user-form-holder').addClass('open');
				if(userBoxSize > 0) {
					setTimeout(function(){
						$('.new-user-form-holder').css('height', userBoxSize);
					}, 25);
				}
				setTimeout(function(){
					$('.new-user-form-holder.open').css('height', 'auto');
				}, 1000);
				setTimeout( function(){ 
					$('.new-user-form-holder').css('overflow','visible');

				  }
				 , 1000 );
			}
		}
	});
	
	

	$('#confirm-delete').on('show.bs.modal', function(e) {
		 $(this).find('.danger').attr('href', $(e.relatedTarget).data('href'));
	});

	$('.alta_usuario').on('click', '.papelera', function(e) {
		$('#deleteUser').attr('name',$(this).attr('name'));
	});
	
	$('.gestion_cliente').on('click', '.papelera', function(e) {
		$('#deleteClient').attr('name',$(this).attr('name'));
	});
	
	$('#deleteClient').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = "/clienteServlet?";
		 var postData="accion=delete&id="+ id;
		 $.ajax({
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
	
	$('#submit_edit_user_form').on('click', function (e) {
		var $form = $("#edit-user-form");
		
		if($form.valid()){		
			var areas = "";
			var $checkedBoxes = $form.find('.radio-container-holder').find('.radio-container').find('input:checked');
			$checkedBoxes.each(function(i){
				areas += $(this).val();
				if(i < $checkedBoxes.length - 1){
					areas += "_";
				}
			});
			var postData = $form.serialize() + "&accion=update&areasStr="+areas;
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
						if ($('.edit-user-form-holder').height()<190){
							$('.edit-user-form-holder').height($('.edit-user-form-holder').height()+35);
						}
						$form.find('.form-container').find('div:not(#message_div_modal)').hide(0);
						$form.find('#span_message_modal').html('El usuario ha sido creado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
						$('#modal-footer_submit').css('display','none');
						$('#message_div_modal').css('display','block').removeClass("error").addClass("success");;

						setTimeout(function() { 
							resetForm($form);
							location.reload();
						}, 1500);
					}else{
						$('#message_div_modal').removeClass("success").addClass("error");
						if ($('.edit-user-form-holder').height()<190){
							$('.edit-user-form-holder').height($('.edit-user-form-holder').height()+35);
						}
						$('#span_message_modal').html(data.error);
						$('#message_div_modal').css('display','block');
					}
			  }
			},'html');
		}
	});

	$('#deleteUser').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = "/usersServlet?";
		 var postData="accion=delete&id="+ id;
		 $.ajax({
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

	
	$('.alta_usuario').on('click', '.lapiz', function(e) {
		id= $(this).attr('name');
		if (!$(this).hasClass('inactive')) {
			editRow(id);
			

		}	
	});

	// Submit for creating a new user.
	$("#submit_user_form").on('click',function(e) {
		e.preventDefault(); //STOP default action
		var $form = $("#new-user-form");
		if($form.valid()){
			var areas = "";
			var $checkedBoxes = $form.find('.radio-container-holder').find('.radio-container').find('input:checked');
			$checkedBoxes.each(function(i){
				areas += $(this).val();
				if(i < $checkedBoxes.length - 1){
					areas += "_";
				}
			});
			
			var postData = $form.serialize() + "&accion=new&areasStr="+areas;
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
					if ($('.new-user-form-holder').height()<190){
						$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
					}
					$form.find('.form-container').find('div:not(#message_div)').hide(0);
					$form.find('#span_message').html('El usuario ha sido creado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
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
		return false;
	});
});

function generateRow(data ,id, permiso, permisoid, dto, area){
	var a = data.split('&');
	var nombre = $('#nombre').val();
	var ap1 = $('#ap1').val();
	var ap2 = $('#ap2').val();
	var email = $('#email').val();
	var html = "<tr id=row"+id+" class='valid-result' data-dto='"+dto+"' data-mail='"+email+"' data-permiso='"+permisoid+"' data-area='"+area+"'>"
		+ "<td><span>"+nombre+"</span></td>"
		+ "<td><span>"+ap1+"</span></td>"
		+ "<td><span>"+ap2+"</span></td>"
		+ "<td><span>"+dto+"</span></td>"
		+ "<td><span>"+permiso+"</span></td>"
		+ "<td><img class='vs' src='../img/vs.png'>"
		+ "<a class='lapiz' id='lapiz"+id+"' name="+id+"></a>"
		+ "<a class='papelera' id='papelera"+id+"' name="+id+" data-toggle='modal' data-target='#confirm-delete'> </a></td></tr>";
	
	return html;
}

function updateRow(id){
	var celdas = $('#row'+id).children();
	for (var a =0; celdas.length-3 >= a;a++){
		var $celda = $(celdas[a]);
		var span = $celda.children().val();
		$celda.children().remove();
		$celda.prepend("<span>"+span+"</span>");
	}
	var $celda = $(celdas[4]);
	var span = $celda.children().find(":selected").text();
	$celda.children().remove();
	$celda.prepend("<span>"+span+"</span>");
				
	$('#lapiz'+id).removeClass('inactive');
	$('#papelera'+id).removeClass('inactive');
}

function undoRow(id,arr){
	$('#papelera'+id).attr('data-toggle');
	$('#row'+id).removeClass('editing');
	var dto = $('#dto_ed option:selected').text();
	var perfil = $('#permiso_ed option:selected').text();
	
	
	$('.extended-row').remove();
	
	var celdas = $('#row'+id).children();
	for (var a =0; celdas.length-4 >= a;a++){
		var $celda = $(celdas[a]);
		var span = $celda.children().val();
		$celda.children().remove();
		$celda.prepend("<span>"+arr[a]+"</span>");
	}
	
	$celda = $(celdas[3]);
	
	$celda.children().remove();
	$celda.prepend("<span>"+dto+"</span>");
	
	$celda = $(celdas[4]);
		$celda.children().remove();
	$celda.prepend("<span>"+perfil+"</span>");

	$('#lapiz'+id).removeClass('inactive');
	$('#papelera'+id).removeClass('inactive');
}

function undoEditRow(id){
	$('#row'+id).removeClass('editing');
	$('.extended-row').remove();

	updateRow(id);
}

function generateChecks(pagina,destino){
	$.get('../html/'+pagina,null,function(result) {
		 $(".extended-div").html(result); // Or whatever you need to insert the result
	},'html');
}

function editRow(id){
	var $currentRow = $('#row'+id);
	var $table = $currentRow.closest('table');
	var $previousOpenEdit = $table.find('#edit-item-holder');
	// Get the select box values.
	var areas= $currentRow.attr('data-area');
	
	if (areas.indexOf("Global Customer Service")!=-1){
		areas= areas.replace("Global Customer Service","gcs");
	}
	if (areas.indexOf("Global Product")!=-1){
		areas = areas.replace("Global Product","globalproduct");
	}
	if(areas.lenght!=0){
		areas= areas.split("_");
		drawChecksAreas(areas);
	}
	dto= $currentRow.attr('data-dto');
	// Current known values from item.
	var celdas = $currentRow.children();
	cnombre = $(celdas[0]).children().html();
	cap1 = $(celdas[1]).children().html();
	cap2 = $(celdas[2]).children().html();
	cdepartamento = $currentRow.data('dto');
	cpermiso = $currentRow.data('permiso');
	email = $currentRow.attr('data-mail');
	
	$("#id_modal").val(id);
	$("#nombre_modal").val(cnombre);
	$("#ap1_modal").val(cap1);
	$("#ap2_modal").val(cap2);
	$("#email_modal").val(email);
	$("#dto_select_modal").val(cdepartamento);
	$("#permiso_select_modal").val(cpermiso);
	
	
	showModal();
	
}

function drawChecksAreas(str){
	for (x=0;x<str.length;x++){
		str[x] = str[x].toLowerCase();
		if (str[x].indexOf("onboarding")!=-1){
			$('#onboarding_modal').attr("checked","checked");
			$('#onboarding_modal').next().addClass("checked");
		}else{
			$('#onboarding_modal').removeClass("checked");
			$('#onboarding_modal').next().removeClass("checked");
		}		
		if (str[x].indexOf("servicing")!=-1){
			$('#servicing_modal').attr("checked","checked");
			$('#servicing_modal').next().addClass("checked");
		}else{
			$('#servicing_modal').removeClass("checked");
			$('#servicing_modal').next().removeClass("checked");
		}				
		if (str[x].indexOf("clientes")!=-1){
			$('#clientes_modal').attr("checked","checked");
			$('#clientes_modal').next().addClass("checked");
		}else{
			$('#clientes_modal').removeClass("checked");
			$('#clientes_modal').next().removeClass("checked");
		}			
		if (str[x].indexOf("itcib")!=-1){
			$('#itcib_modal').attr("checked","checked");
			$('#itcib_modal').next().addClass("checked");
		}else{
			$('#itcib_modal').removeClass("checked");
			$('#itcib_modal').next().removeClass("checked");
		}
		if (str[x].indexOf("gcs")!=-1){
			$('#gcs_modal').attr("checked","checked");
			$('#gcs_modal').next().addClass("checked");
		}else{
			$('#gcs_modal').removeClass("checked");
			$('#gcs_modal').next().removeClass("checked");
		}
		if (str[x].indexOf("globalproduct")!=-1){
			$('#global-product_modal').attr("checked","checked");
			$('#global-product_modal').next().addClass("checked");
		}else{
			$('#global-product_modal').removeClass("checked");
			$('#global-product_modal').next().removeClass("checked");
		}
	}
}




;$(function() {
	$.extend($.validator.messages, {
		required: "Este campo es obligatorio.",
		remote: "Por favor, rellena este campo.",
		email: "Por favor, escribe una direcci&oacute;n de correo v&aacute;lida.(Terminada en @bbva.com)",
		url: "Por favor, escribe una URL v&aacute;lida.",
		date: "Por favor, escribe una fecha v�lida.",
		dateISO: "Por favor, escribe una fecha (ISO) v�lida.",
		number: "Por favor, escribe un n&uacutemero v&aacute;lido.",
		digits: "Por favor, escribe s&oacute;lo d&iacute;gitos.",
		creditcard: "Por favor, escribe un n&uacute;mero de tarjeta v&aacute;lido.",
		equalTo: "Por favor, escribe el mismo valor de nuevo.",
		extension: "Por favor, escribe un valor con una extensi&oacute;n aceptada.",
		maxlength: $.validator.format("Por favor, no escribas m&aacute;s de {0} caracteres."),
		minlength: $.validator.format("Por favor, no escribas menos de {0} caracteres."),
		rangelength: $.validator.format("Por favor, escribe un valor entre {0} y {1} caracteres."),
		range: $.validator.format("Por favor, escribe un valor entre {0} y {1}."),
		max: $.validator.format("Por favor, escribe un valor menor o igual a {0}."),
		min: $.validator.format("Por favor, escribe un valor mayor o igual a {0}."),
		nifES: "Por favor, escribe un NIF v&aacute;lido.",
		nieES: "Por favor, escribe un NIE v&aacute;lido.",
		cifES: "Por favor, escribe un CIF v&aacute;lido."
	});

	
	
	$.validator.addMethod("money", function(value, element) {
		 return this.optional(element) || /^-?(?:\d+|\d{1,3}(?:[\s\.,]\d{3})+)(?:[\.,]\d+)?$/.test(value);
		}, "Por favor, introduce una cifra v&aacute;lida");
	

	
	// One to rule the ... selects
	$.validator.addMethod("selected", function(value, element){
		var valid = false;

		if(value != 'default') {
			valid = true;
			$(element).parent().find('.bootstrap-select').removeClass('error');
		}else{
			$(element).parent().find('.bootstrap-select').addClass('error');
		}
		return valid;
	}, "Este campo es obligatorio.");

	$.validator.addMethod('require-one', function(value, element) {
		var valid = false;
		var selector = 'input[name=' + $(element).attr('name') + ']';

		$(element).closest('form').find(selector).each(function() {
			if($(this).is(":checked")) {
				valid = true;
			}
		});
		
		var modal =  $(element).attr('id').indexOf('modal') != -1;
		if (valid==false) {
			if (modal) {
				$('#message_div_cliente_modal').addClass('error');
				$('#span_message_cliente_modal').text('Debe seleccionar un pa\u00EDs');
			} else {
				$('#message_div_cliente').addClass('error');
				$('#span_message_cliente').text('Debe seleccionar un pa\u00EDs');
			}
		} else {
			if (modal) {
				$('#message_div_cliente_modal').removeClass('error');
				$('#span_message_cliente_modal').text('');
			} else {
				$('#message_div_cliente').removeClass('error');
				$('#span_message_cliente').text('');
				
			}
		}
		
			
			
		return valid;
	},'Por favor, selecciona una opci&oacute;n.');

	initValidator();
});

var initValidator = function() {
	// Setup form validation on all the form elements.
	$('form').each(function(){
		$(this).validate({
			ignore: ":hidden:not(select):not([type='radio']):not([type='checkbox'])",
			focusCleanup: false,
			onkeyup: false,
		    submitHandler: function(form) {
		        form.submit();
		    },
		    errorPlacement: function(error, $element) {
		    	var $target = $element.parent();
		    	$target.find('.error-messages').remove();
				var $container = '';

				if($element.hasClass('selectpicker')){
					$element = $target.find('.bootstrap-select');
				}
				// overwritable when using the tag data-error-show-style = tooltip
				if(($element.is(':checkbox') || $element.is(':radio')) && $element.parent().hasClass('radio-container')) {
					var $target = $element.closest('.radio-container-holder');
					var $container = $target.find('#error-messages');
					if($container.length == 0){
						$container = $('<div id="error-messages" class="block-error server-errors"><ul></ul></div>');
						$element.closest('.radio-container-holder').prepend($container);
					}

					
					// Create error element and append it to error container
					var $errorelement = $('<li>');
					$errorelement.append(error);
					$container.find('ul').append($errorelement);

				} else {
					$container = $('<div class="error-messages"><ul></ul></div>');
					$target.css({position:'relative'}).prepend($container);
				}
				
				if (!$element.hasClass("no_message_error")){
					// Create error element and append it to error container
					var $errorelement = $('<li>');
					$errorelement.append(error);
					$container.find('ul').append($errorelement);
					var leftPosition = 0;
					if ($element.outerWidth() < $container.outerWidth()) {
						// Error message is bigger than element.
						leftPosition = ($element.outerWidth() - $container.outerWidth()) / 2;
					} else if ($element.outerWidth() > $container.outerWidth()) {
						// Error message is smaller than element.
						leftPosition = ($element.outerWidth() - $container.outerWidth()) / 2;
					}
					// In two steps so the element can have a real height to work with.
					$container.css({left: ($element.position().left + leftPosition) + 'px', marginLeft: $element.css('margin-left'), maxWidth:'200px'});
					$container.css({top:'-' + ($container.outerHeight() + 10) + 'px'});

					$element.hover(
					  function() {
					    $container.addClass("hover");
					  }, function() {
					    $container.removeClass("hover");
					  }
					);
				}
				
			},
			success: function(label) {
				label.closest('.error-messages').remove();
			}
		});
	});
}
