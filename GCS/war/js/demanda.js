var opciones_estado = "<option value='PDTE Doc Alcance en GCS'>PDTE Doc Alcance en GCS</option><option value='P-950 en confecci&oacute;n'>P-950 en confección</option><option value='PDTE Valoración IT'>PDTE Valoración IT</option><option value='PDTE Plan de Trabajo IT'>PDTE Plan de Trabajo IT</option><option value='PDTE Visto Bueno del CL del plan de trabajo'>PDTE Visto Bueno del CL del plan de trabajo</option><option value='En Desarrollo'>En Desarrollo</option><option value='En Test - Conectividad'>En Test - Conectividad</option><option value='En Test - Integración'>En Test - Integración</option><option value='En Test - Aceptación'>En Test - Aceptación</option><option value='Parado por Negocio - Producto'>Parado por Negocio - Producto</option><option value='Parado por Negocio'>Parado por Negocio</option><option value='Parado por IT'>Parado por IT</option><option value='Excluido por Negocio'>Excluido por Negocio</option><option value='Excluido por Timeout'>Excluido por Timeout</option><option value='PDTE Implantar'>PDTE Implantar</option><option value='En Penny Test'>En Penny Test</option><option value='Implementado con OK'>Implementado con OK</option><option value='Implementado sin OK'>Implementado sin OK</option>";


$(function() {
	
	var tipoini="";
	var estadoini="";
	
	$('.gestion_demanda').on('click', '.lapiz', function(e) {
		var id= $(this).attr('name');
		if (!$(this).hasClass('inactive')) {
			editRowDemanda(id);
		}	
	});
	
	$('.gestion_demanda').on('click', '.cancelar-ext-demanda', function (e) {
		var id= $('.editing').attr('name');
		
		$('#papelera'+id).attr("data-toggle","modal");
		undoRowDemanda(id);
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
					$('#row'+id).fadeOut("slow");
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
		var cod_peticion = data.cod_peticion;
		var id = data.id;
		
		var html = "<tr id=row"+id+" class='valid-result'>"
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
		var cliente = $(celdas[1]).children().html();
		var tipo = $(celdas[2]).children().html();
		var estado = $(celdas[3]).children().html();
		var codPeticion =  $(celdas[4]).children().html();
		var gestorAsignado = $currentRow.data('gestor-asig');
		var fechaComun = $currentRow.data('fecha-comun');
		var horaComun = $currentRow.data('hora-comun');

		$.get('../html/extended-demanda.html',null,function(result) {
			// Close other editing field and show the row.
			if($previousOpenEdit.length > 0){
				$('#row'+$previousOpenEdit.data('row-id')).css({display:'table-row'});
				$previousOpenEdit.remove();
			}
			// Hide current row.
			$currentRow.css({display:'none'});
			// Adds the item holder row for editing the item.
			$currentRow.after("<tr id='edit-item-holder' class='extended-row' style='display: table-row;'><td colspan='6'><div class='extended-div'></div></td></tr>");
			var $currentOpenEdit = $table.find('#edit-item-holder');
			$currentOpenEdit.data('row-id', id);
			// Add the result to the element
			$(".extended-div").html(result);
			// The form we're editing in.
			var $editForm = $currentOpenEdit.find('form#edit-item');
			// Add info stuff ... errr ... 0_o.
			$editForm.find('#fecha_entrada_peticion_ed').val(fechaEntrada);
			// copia options de select de formulario de creacion
			var $clienteOptions = $('select#input_cliente option').clone();
			$editForm.find('select#input_cliente_ed').append($clienteOptions).val(cliente);
			$editForm.find('select#input_cliente_ed option').first().remove();

			var $tipoOptions = $('select#tipo option').clone();
			$editForm.find('select#input_tipo_ed').append($tipoOptions).val(tipo);
			$editForm.find('select#input_tipo_ed option').first().remove();

			var $tipoOptions = $('select#estado option').clone();
			$editForm.find('select#input_estado_ed').append($tipoOptions).val(estado);
			$editForm.find('select#input_estado_ed option').first().remove();

			$editForm.find('#cod_peticion_ed').html(codPeticion);

			var $gestorOptions = $('select#gestor_it option').clone();
			$editForm.find('select#gestor_it_ed').append($gestorOptions).val(gestorAsignado);
			$editForm.find('select#gestor_it_ed option').first().remove();

			// If it has a fecha it might have a time too.
			if(fechaComun.length > 0) {
				fecha_solicitud_asignacion_ed.val(fechaComun);
				$editForm.find('select#hora_peticion_ed').val(horaComun.substring(0, 2));
				$editForm.find('select#min_peticion_ed').val(horaComun.substring(3, 5));
			}

			// Activate everything.
			initDatepickers();
			initSelectpickers();
			initForms();
			initValidator();
			// Click event for the cancel button.
			$editForm.on('click.close-form', '.cancelar-ext', function (e) {
				$('#row'+$currentOpenEdit.data('row-id')).css({display:'table-row'});
				$currentOpenEdit.remove();
				return false;
			});
			// Click event for the save button.
			$editForm.on('click', '.guardar-ext', function (e) {
				if($editForm.valid()){

				}
				return false;
			});
		});
/*
		var celdas = $('#row'+id).children();
		cnombre = $(celdas[0]).children().html();

		for (var a =0; celdas.length-5 >= a;a++){
			var $celda = $(celdas[a]);
			var span = $celda.children().html();
			$celda.children().remove();
			if (span.indexOf('&amp;')!=-1){
				span = span.replace('&amp;','&');
			}
			$celda.prepend("<input type='text' class='edit_input col"+id+"' value='" + span +"'>");
		}
		// TIPO
		var $celda = $(celdas[2]);
		tipoini = $celda.children().html();
		$celda.children().remove();
		$celda.prepend("<select name='tipo_ed' id='tipo_ed' class='selectpicker tipo_ed'>" +
				"<option value='CIB'>CIB</option>" +
				"<option value='BEC'>BEC</option></select>");
		
		$('#tipo_ed').val(tipoini);
		
		// ESTADO
		
		$celda = $(celdas[3]);
		estadoini = $celda.children().html();
		$celda.children().remove();
		$celda.prepend("<select name='estado_ed' id='estado_ed' class='selectpicker estado_ed'>" +opciones_estado + "</select>");			
		
		$('#estado_ed').val(estadoini);
		
		setTimeout(function(){
			
			//$('#dp1').datepicker();
			$('#fecha_entrada_peticion_ext').val($('#row'+id).attr('data-fecha-entrada'));
			
			var gestores_it = $('#row'+id).attr('data-gestores-list').split("-");
			var ges_it = $('#row'+id).attr('data-gestor-asig').split("-");
			var data_time = $('#row'+id).attr('data-hora-entrada').split("-")[0];
			var hora = data_time.split(":")[0];
			var minutos = data_time.split(":")[1];
			var $gestoresSelect = $('#gestor_it_ext');

			for (var a=0; a<gestores_it.length-1; a++){
				var gestor_name = gestores_it[a].split("(")[0];
				var gestor_id = gestores_it[a].split("(")[1].split(")")[0];
				$gestoresSelect.append("<option value='"+gestor_id+"'>"+gestor_name+"</option>")
			}
			
			$('.selectpicker').selectpicker('refresh');
			$('#gestor_it_ext').val(ges_it);
			$('#hora_peticion_ext').val(hora);
			$('#min_peticion_ext').val(minutos);
			$('.selectpicker').selectpicker('render');
		},750);
*/
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
					var html=generateRowDemanda(postData,data);
					
					$('#myTable').prepend(html);
					
					$('#myTable').paginateMe({
						pagerSelector : '#myPager',
						showPrevNext : true,
						hidePageNumbers : false,
						perPage : 5
					});
					
					$('#message_div').removeClass("error").addClass("success");
					if ($('.new-user-form-holder').height()<190){
						$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
					}
					$('#span_message').html("El usuario ha sido creado de forma correcta.");
					$('#message_div').css('display','block');
					
					resetForm($form);
					
					setTimeout(function() { 
						$( "#message_div" ).fadeOut( "slow", function() {
							$('#span_message').html("");
					  }); }, 5000);
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
