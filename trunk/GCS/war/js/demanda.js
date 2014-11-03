var opciones_estado = "<option value='PDTE Doc Alcance en GCS'>PDTE Doc Alcance en GCS</option><option value='P-950 en confecci&oacute;n'>P-950 en confección</option><option value='PDTE Valoración IT'>PDTE Valoración IT</option><option value='PDTE Plan de Trabajo IT'>PDTE Plan de Trabajo IT</option><option value='PDTE Visto Bueno del CL del plan de trabajo'>PDTE Visto Bueno del CL del plan de trabajo</option><option value='En Desarrollo'>En Desarrollo</option><option value='En Test - Conectividad'>En Test - Conectividad</option><option value='En Test - Integración'>En Test - Integración</option><option value='En Test - Aceptación'>En Test - Aceptación</option><option value='Parado por Negocio - Producto'>Parado por Negocio - Producto</option><option value='Parado por Negocio'>Parado por Negocio</option><option value='Parado por IT'>Parado por IT</option><option value='Excluido por Negocio'>Excluido por Negocio</option><option value='Excluido por Timeout'>Excluido por Timeout</option><option value='PDTE Implantar'>PDTE Implantar</option><option value='En Penny Test'>En Penny Test</option><option value='Implementado con OK'>Implementado con OK</option><option value='Implementado sin OK'>Implementado sin OK</option>";
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
