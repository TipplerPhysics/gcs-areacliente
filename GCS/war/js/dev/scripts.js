var url = document.URL;

if (url.indexOf("localhost")>1){
	url="http://localhost:8888";
}else{
	url="http://gcs-areacliente.appspot.com";
};var opciones_estado = "<option value='PDTE Doc Alcance en GCS'>PDTE Doc Alcance en GCS</option><option value='P-950 en confecci&oacute;n'>P-950 en confección</option><option value='PDTE Valoración IT'>PDTE Valoración IT</option><option value='PDTE Plan de Trabajo IT'>PDTE Plan de Trabajo IT</option><option value='PDTE Visto Bueno del CL del plan de trabajo'>PDTE Visto Bueno del CL del plan de trabajo</option><option value='En Desarrollo'>En Desarrollo</option><option value='En Test - Conectividad'>En Test - Conectividad</option><option value='En Test - Integración'>En Test - Integración</option><option value='En Test - Aceptación'>En Test - Aceptación</option><option value='Parado por Negocio - Producto'>Parado por Negocio - Producto</option><option value='Parado por Negocio'>Parado por Negocio</option><option value='Parado por IT'>Parado por IT</option><option value='Excluido por Negocio'>Excluido por Negocio</option><option value='Excluido por Timeout'>Excluido por Timeout</option><option value='PDTE Implantar'>PDTE Implantar</option><option value='En Penny Test'>En Penny Test</option><option value='Implementado con OK'>Implementado con OK</option><option value='Implementado sin OK'>Implementado sin OK</option>";


$(function() {
	
	var tipoini="";
	var estadoini="";
	
	$('.gestion_demanda').on('click', '.lapiz', function(e) {
		var id= $(this).attr('name');
		if ($('.editing')[0] != undefined && !$(this).hasClass('inactive'))
		{
			var rowid = $('.editing').attr('id').split("w")[1];
			undoEditRow(rowid);
			editRowDemanda(id);
		}else if (!$(this).hasClass('inactive')){
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
				//data: return data from server

				//	$('.extended-row').remove();
				//	$('#row'+id).removeClass('editing');

				//	updateRow(id);
				
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
		var cliente =  $('#cliente option:selected').val();
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
		+ "<a class='papelera' id='papelera"+id+"' name="+id+" data-toggle='modal' data-target='#confirm-delete'> </a>"
		+ "<a class='lapiz' id='lapiz"+id+"' name="+id+"></a></td></tr>";
	
	return html;
	}
	
	
	
	function editRowDemanda(id){
		$('#papelera'+id).removeAttr('data-toggle');
		$('#row'+id).addClass('editing');
		$('#row'+id).attr('name',id);
		
		

		

		$('#row'+id).after("<tr class='extended-row' style='display: table-row;'><td colspan='6'><div class='extended-div'></div></td></tr>");
		
		generateChecks("extended-demanda.html","extended-div");

		 
		
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
		

		
		$('#lapiz'+id).addClass('inactive');
		$('#papelera'+id).addClass('inactive');		
		
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
;function resetForm($form) {
	$form.find('input').each(function(){
		if($(this).attr('type') == 'text') {
			$(this).val('');
		} else if (($(this).attr('type') == 'radio') || ($(this).attr('type') == 'checkbox')) {
			$(this).prop('checked', false);
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
}

$(function() {
	$('form').parent().find('button.close-form').on('click', function(){
		var $form = $(this).parent().find('form');
		$('#newUserButton').trigger('click');
		setTimeout(function(){
			resetForm($form);
		},750);

		return false;
	});
});;$(function(){
	// init all the datepickers which generally are always inside of a form.
	$('form').find('.datepicker').each(function(){
		if($(this).hasClass('datefuture')) {
			$(this).datepicker({minDate:0});
		} else if($(this).hasClass('datepast')) {
			$(this).datepicker({maxDate:0});
		} else {
			$(this).datepicker();
		}
	});

	// init all the datepickers which generally are always inside of a form.
	$('form').find('.selectpicker').selectpicker();
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
			if (!($(this).is($current_input)) && $(this).val().trim().length != 0) {
			    multipleFilter = true;
			}
		});

		for (var e=0; e<$table_results.length; e++){
			var isValid = true;
			var $linea = $($table_results[e]);	
			$(this).closest('tr').find('.search-th').find('input').each(function(){
				if ($(this).val().trim().length != 0) {
					var text = normalize($(this).val().toLowerCase());
					var columna = $(this).attr("class").split("col")[1];
				    var cont = $($linea.children()[columna]).children().html().toLowerCase();
				    var textLength = text.length;
				    if(text != cont.substring(0, textLength)){
				    	isValid = false;
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

$(function() {

	$('#myTable').paginateMe({
		pagerSelector : '#myPager',
		showPrevNext : true,
		hidePageNumbers : false,
		perPage : 5
	});

	$('.selectpicker').selectpicker();

	var userBoxSize = 0;
	$('#newUserButton').click(function(e){
		if ($('#newUserButton').hasClass('white-btn')){
			
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

	$('#deleteUser').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = "/usersServlet?";
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

	
	$('.alta_usuario').on('click', '.lapiz', function(e) {
		var id= $(this).attr('name');
		if ($('.editing')[0] != undefined && !$(this).hasClass('inactive'))
		{
			var rowid = $('.editing').attr('id').split("w")[1];
			undoEditRow(rowid);
			editRow(id);
		}else if (!$(this).hasClass('inactive')){
			editRow(id);
		}	
	});

	$('#myTable').on('click', '.cancelar-ext', function (e) {
		var id= $('.editing').attr('name');
		var arr = [cnombre,cap1,cap2,cemail,cpermiso];
		$('#papelera'+id).attr("data-toggle","modal");
		undoRow(id,arr);
	});

	$('#myTable').on('click', '.guardar-ext', function (e) {
		var id= $('.editing').attr('name');
		var campo = $('.col'+id);
		var areas =  $('.dtos').find('input');
		var $fila= $('#row'+id);
		
		var nombre = $(campo[0]).val();
		var ap1 = $(campo[1]).val();
		var ap2 = $(campo[2]).val();
		var email = $('#email_ext').val();
		var permiso = $('#permiso_ed').val();
		var dto = $('#dto_ed option:selected').text();
		
		dto = dto.replace('&','#');
		
		
		var areascont = $('.dtos').children();
		var areas="";
		
		for (var i=0; i<areascont.length;i++){
			var $item = $($(areascont[i]).children()[0]);
			if ($item[0].checked==true){
				areas += $item.val() + "-";
			}		
		}
		
		// Ponemos en la info de la fila los valores nuevos de area y dto		
		if (areas.length>1){
			$fila.attr('data-area',areas.substring(0,areas.length-1));			
		}else{
			$fila.attr('data-area',"");
		}
		$fila.attr('data-dto',dto);

		var postData = "&nombre="+nombre+"&id="+id+"&dto="+dto+"&permiso="+permiso+"&email="+email+"&ap1="+ap1+"&ap2="+ap2+"&accion=update&areas="+areas;
		var formURL = "/usersServlet";
		$.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) {
				//data: return data from server
				//$('.extended-row').remove();
				//$('#row'+id).removeClass('editing');
				//updateRow(id);

				location.reload();
			}
		});
		
		$('#papelera'+id).attr("data-toggle","modal");
	});

	// Submit for creating a new user.
	$("#submit_user_form").on('click',function(e) {
		e.preventDefault(); //STOP default action
		var $form = $("#new-user-form");
		
		if($form.valid()){
			var areas = "";
			if ($('#onboarding').prop('checked')==true){
			 areas += "Onboarding-"
			}
			if ($('#servicing').prop('checked')==true){
			 areas += "Servicing-"
			}
			if ($('#itcib').prop('checked')==true){
			 areas += "ITCIB-"
			}
			if ($('#gcs').prop('checked')==true){
			 areas += "Global Customer Service-"
			}
			if ($('#global-product').prop('checked')==true){
			 areas += "Global product-"
			}
			if ($('#clientes').prop('checked')==true){
			 areas += "Clientes-"
			}

			var servicing = $('#servicing').val();

			var postData = $form.serialize() + "&accion=new&areas="+areas;
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
					var html=generateRow(postData,data.id,data.permiso,data.permisoid, data.dto, data.area);
					
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

function generateRow(data,id,permiso,permisoid,dto,area){
	var a = data.split('&');
	var nombre = $('#nombre').val();
	var ap1 = $('#ap1').val();
	var ap2 = $('#ap2').val();
	var email = $('#email').val();
	var html = "<tr id=row"+id+" class='valid-result' data-dto='"+dto+"' data-mail='"+email+"' data-permiso='"+permisoid+"' data-area='"+area+"'>"
		+ "<td><span>"+nombre+"</span></td>"
		+ "<td><span>"+ap1+"</span></td>"
		+ "<td><span>"+ap2+"</span></td>"
		+ "<td><span>"+email+"</span></td>"
		+ "<td><span>"+permiso+"</span></td>"
		+ "<td><img class='vs' src='../img/vs.png'>"
		+ "<a class='papelera' id='papelera"+id+"' name="+id+" data-toggle='modal' data-target='#confirm-delete'> </a>"
		+ "<a class='lapiz' id='lapiz"+id+"' name="+id+"></a></td></tr>";
	
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
	$('#papelera'+id).attr('data-toggle','modal');
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
	$('#papelera'+id).removeAttr('data-toggle');
	$('#row'+id).addClass('editing');
	$('#row'+id).attr('name',id);

	areas= $('#row'+id).attr('data-area');

	if (areas.indexOf("Global Customer Service")!=-1){
		areas= areas.replace("Global Customer Service","gcs");
	}

	if (areas.indexOf("Global Product")!=-1){
		areas = areas.replace("Global Product","globalproduct");
	}

	areas= areas.split("-");
	dto= $('#row'+id).attr('data-dto');

	$('#row'+id).after("<tr class='extended-row' style='display: table-row;'><td colspan='6'><div class='extended-div'></div></td></tr>");
	
	generateChecks("extended-user.html","extended-div");

	 
	
	var celdas = $('#row'+id).children();
	cnombre = $(celdas[0]).children().html();
	cap1 = $(celdas[1]).children().html();
	cap2 = $(celdas[2]).children().html();
	cemail = $(celdas[3]).children().html();
	cpermiso =  $('#row'+id).attr('data-permiso');
	
	for (var a =0; celdas.length-2 >= a;a++){
		var $celda = $(celdas[a]);
		var span = $celda.children().html();
		$celda.children().remove();
		if (span.indexOf('&amp;')!=-1){
			span = span.replace('&amp;','&');
		}
		$celda.prepend("<input type='text' class='edit_input col"+id+"' value='" + span +"'>");
	}

	// DEPARTAMENTO
	
	var $celda = $(celdas[3]);
	var span = $celda.children().html();
	$celda.children().remove();
	$celda.prepend("<select name='dto_ext' id='dto_ed' class='long selectpicker'>" +
			"<option value='Negocio - Global Customer Service (Incluye HDR)'>Negocio - Global Customer Service (Incluye HDR)</option>" +
			"<option value='Negocio - Global Product'>Negocio - Global Product</option>" +
			"<option value='Negocio - Global Sales'>Negocio - Global Sales</option>" +
			"<option value='IT C&IB - CTO - Soluciones T&eacute;cnicas'>IT C&IB - CTO - Soluciones T&eacute;cnicas</option>" +
			"<option value='IT C&IB - CTO - Arquitectura Funcional'>IT C&IB - CTO - Arquitectura Funcional</option>" +
			"<option value='IT C&IB - CTO - Operaciones y Soporte (Sop Swift, CAU)'>IT C&IB - CTO - Operaciones y Soporte (Sop Swift, CAU)</option>" +
			"<option value='IT C&IB - Control y Gesti&oacute;n'>IT C&IB - Control y Gesti&oacute;n</option>" +
			"<option value='IT C&IB - E- commerce C&IB'>IT C&IB - E- commerce C&IB</option>" +
			"<option value='IT C&IB - GCC Lending GTB & CFO'>IT C&IB - GCC Lending GTB & CFO</option>" +
			"<option value='IT C&IB - GTB - Global Customer Solutions'>IT C&IB - GTB - Global Customer Solutions</option>" +
			"<option value='IT C&IB - Global Transactional Product'>IT C&IB - Global Transactional Product</option>" +
			"<option value='IT C&IB - B2B Global Support'>IT C&IB - B2B Global Support</option></select>");	
	
	
	var value_dto="0";
	
	// PERFIL
	var $celda = $(celdas[4]);
	var span = $celda.children().val();
	$celda.children().remove();
	$celda.prepend("<select name='permiso' id='permiso_ed' class='permiso_ed long selectpicker'>"
		+ "	<option value='5'>Gestor IT</option>"
		+ "	<option value='4'>Gestor Demanda</option>"
		+ "	<option value='3'>User Admin</option>"
		+ "	<option value='2'>App Admin</option>"
		+ "	<option value='1'>Super</option>"
		+ "	</select>");	
	

	
	$('#lapiz'+id).addClass('inactive');
	$('#papelera'+id).addClass('inactive');
	
	
	
	var lng = areas.length;
	
	if (lng==1 && areas[0]==""){
		lng=0;
	}
	
	setTimeout(function(){
		
		$('#dto_ed').val(dto);
		$('#dto_ed').selectpicker('render');
		
		$('#permiso_ed').val(cpermiso);
		$('#permiso_ed').selectpicker('render');
		
		
		
		$('#email_ext').val($('#row'+id).attr('data-mail'));		
		$('#email_ext').selectpicker('render');
		
		

		if (lng>0){
		for (var n=0; n<lng;n++){
			
			var name = areas[n];
			
			$('#e-'+name)[0].setAttribute("checked", "checked");
		}
	}},750);
};$(function() {
	$.extend($.validator.messages, {
		required: "Este campo es obligatorio.",
		remote: "Por favor, rellena este campo.",
		email: "Por favor, escribe una direcci&oacuten de correo v&aacutelida.",
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


	// Setup form validation on the #register-form element
	$('form').validate({
		ignore: ":hidden:not(select)",
		focusCleanup: false,
	    submitHandler: function(form) {
	        form.submit();
	    },
	    errorPlacement: function(error, $element) {
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
				var $target = $element.closest('.form-container');
				var $container = $target.find('#error-messages');

				if($container.length == 0){
					$container = $('<div id="error-messages" class="block-error server-errors detail"><ul></ul></div>');
					$target.prepend($container);
				}

				// Create error element and append it to error container
				var $errorelement = $('<li>');
				$errorelement.append(error);
				$container.find('ul').append($errorelement);
			}
		}
	});
});