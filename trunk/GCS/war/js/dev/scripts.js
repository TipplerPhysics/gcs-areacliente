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
		
		window.setTimeout(function(){showModal()}, 500);
		
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
	
	$('#edit-client').on('shown.bs.modal', function () {
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
};var opciones_estado = "<option value='PDTE Doc Alcance en GCS'>PDTE Doc Alcance en GCS</option><option value='P-950 en confecci&oacute;n'>P-950 en confección</option><option value='PDTE Valoración IT'>PDTE Valoración IT</option><option value='PDTE Plan de Trabajo IT'>PDTE Plan de Trabajo IT</option><option value='PDTE Visto Bueno del CL del plan de trabajo'>PDTE Visto Bueno del CL del plan de trabajo</option><option value='En Desarrollo'>En Desarrollo</option><option value='En Test - Conectividad'>En Test - Conectividad</option><option value='En Test - Integración'>En Test - Integración</option><option value='En Test - Aceptación'>En Test - Aceptación</option><option value='Parado por Negocio - Producto'>Parado por Negocio - Producto</option><option value='Parado por Negocio'>Parado por Negocio</option><option value='Parado por IT'>Parado por IT</option><option value='Excluido por Negocio'>Excluido por Negocio</option><option value='Excluido por Timeout'>Excluido por Timeout</option><option value='PDTE Implantar'>PDTE Implantar</option><option value='En Penny Test'>En Penny Test</option><option value='Implementado con OK'>Implementado con OK</option><option value='Implementado sin OK'>Implementado sin OK</option>";
var id;

$(document).on('hidden.bs.modal', function (e) {
	$(".modal-content").html();
	$(e.target).removeData('bs.modal');	
});

function showModal(){
	initSelectpickers();
	initDatepickers();
	$('.modal_ajax').css("display","block");
}

function sendEditDemanda(){
	
	var $form = $('#edit-demanda-form');
	var formURL = $form.attr("action");
	 var $formData = $('#edit-demanda-form').serialize();
	 var postData= $formData+"&accion=update&id="+ id;
	 $.ajax(			
		{
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				$form.hide();
				$('#span_message_demanda_modal').html('La demanda ha sido modificada de forma correcta.<br/>En breve volvemos a la pagina.');
				$('.modal-footer').hide();
				$('#message_div_demanda_modal').css('display','block').removeClass("error").addClass("success");;

				setTimeout(function() { 
					resetForm($form);
					location.reload();
				}, 1500);
			}
		});
}


$(function() {
	
	var tipoini="";
	var estadoini="";
	
	$('#edit-demanda').on('shown.bs.modal', function () {
		editRowDemanda(id);
	})
	
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
							perPage : 5
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
		
		window.setTimeout(function(){showModal()}, 500);
		
		
		
		
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
};$(function(){
	initDatepickers();
	initSelectpickers();
});

var initDatepickers = function() {
	// init all the datepickers which generally are always inside of a form.
	$('form').find('.datepicker').each(function(){
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
	$('form').find('.selectpicker').selectpicker();
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
};$(function() {
	
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
				if (! $(cajas[a]).hasClass("crit_"+val)){
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
	
	
	
});;$.fn.paginateMe = function(opts) {
	var $this = this, defaults = {
		perPage : 5,
		showPrevNext : false,
		numbersPerPage : 5,
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
	$(resumen).html('');
	if (numItems>=5){
		$(resumen).html('Resultados '+ ((currentPage*5)-4) + " a " + (currentPage*5) + ' de '+ numItems);
	}else{
		$(resumen).html('Resultados '+ ((currentPage*5)-4) + " a " + (numItems) + ' de '+ numItems);
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
		
		if (numItems>=5){
			$(resumen).html('Resultados '+ (((page+1)*5)-4) + " a " + ((page+1)*5) + ' de '+ numItems);
		}else{
			$(resumen).html('Resultados '+ (((page+1)*5)-4) + " a " + numItems + ' de '+ numItems);
		}
		
	}
};

;var normalize = (function() {
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
				console.log('multi');
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
				    	if(text != cont.substring(0, textLength)){
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
			perPage : 5
		});
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
					$form.find('#span_message_modal').html('El usuario ha sido modificado de forma correcta.<br/>En breve volvemos a la pagina.');
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
		perPage : 5
	});
	
	$('.alta_usuario').on('shown.bs.modal', function () {
		editRow(id);
	})

	$('.selectpicker').selectpicker();

	var userBoxSize = 0;
	$('#newUserButton').click(function(e){
		var $newUserButton = $(this);
		if ($newUserButton.hasClass('white-btn')){
			if ($('.new-user-form-holder').css('overflow')=="visible"){
				$('.new-user-form-holder').css('overflow','hidden');
				userBoxSize = $('.new-user-form-holder.open').outerHeight();
				$('.new-user-form-holder.open').css('height', userBoxSize);
				setTimeout(function(){
					$('.new-user-form-holder.open').removeClass('open').css('height', '0px');
				}, 25);
				setTimeout(function(){
					$('#newUserButton').removeClass('white-btn');	
					$('.user_span').removeClass('blue');
					$('.demanda_span').removeClass('blue');

					var $form = $newUserButton.parent().find('form');
					resetForm($form);
				}, 1000);
			}
		} else {
			if ($('.new-user-form-holder').css('overflow')=="hidden"){
				$('#newUserButton').addClass('white-btn');
				$('.user_span').addClass('blue');
				$('.demanda_span').addClass('blue');
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
						perPage : 5
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
						$form.find('#span_message_modal').html('El usuario ha sido creado de forma correcta.<br/>En breve volvemos a la pagina.');
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
						perPage : 5
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
					$form.find('#span_message').html('El usuario ha sido creado de forma correcta.<br/>En breve volvemos a la pagina.');
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
	areas= $currentRow.attr('data-area');
	
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
	
	
	window.setTimeout(function(){showModal()}, 500);
	
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
		email: "Por favor, escribe una direcci&oacuten de correo v&aacutelida.(Terminada en @bbva.com)",
		url: "Por favor, escribe una URL válida.",
		date: "Por favor, escribe una fecha válida.",
		dateISO: "Por favor, escribe una fecha (ISO) válida.",
		number: "Por favor, escribe un n&uacutemero v&aacutelido.",
		digits: "Por favor, escribe s&oacutelo dígitos.",
		creditcard: "Por favor, escribe un número de tarjeta válido.",
		equalTo: "Por favor, escribe el mismo valor de nuevo.",
		extension: "Por favor, escribe un valor con una extensión aceptada.",
		maxlength: $.validator.format("Por favor, no escribas más de {0} caracteres."),
		minlength: $.validator.format("Por favor, no escribas menos de {0} caracteres."),
		rangelength: $.validator.format("Por favor, escribe un valor entre {0} y {1} caracteres."),
		range: $.validator.format("Por favor, escribe un valor entre {0} y {1}."),
		max: $.validator.format("Por favor, escribe un valor menor o igual a {0}."),
		min: $.validator.format("Por favor, escribe un valor mayor o igual a {0}."),
		nifES: "Por favor, escribe un NIF válido.",
		nieES: "Por favor, escribe un NIE válido.",
		cifES: "Por favor, escribe un CIF válido."
	});

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
	}, "Por favor, selecciona un valor.");

	$.validator.addMethod('require-one', function(value, element) {
		var valid = false;
		var selector = 'input[name=' + $(element).attr('name') + ']';

		$(element).closest('form').find(selector).each(function() {
			if($(this).is(":checked")) {
				valid = true;
			}
		});
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
			},
			success: function(label) {
				label.closest('.error-messages').remove();
			}
		});
	});
}
