/* En estas variables guardo la informacion de la linea del grid que se esta editando */
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
	areas= areas.split("-");
	dto= $currentRow.attr('data-dto');
	// Current known values from item.
	var celdas = $currentRow.children();
	cnombre = $(celdas[0]).children().html();
	cap1 = $(celdas[1]).children().html();
	cap2 = $(celdas[2]).children().html();
	cemail = $(celdas[3]).children().html();
	cpermiso =  $currentRow.attr('data-permiso');
	var email = $currentRow.attr('data-mail');
	// load the external html in to the page.
	$.get('../html/extended-user.html',null,function(result) {
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
		 $(".extended-div").html(result); // Or whatever you need to insert the result
		// The form we're editing in.
		var $editForm = $('#edit-item-holder').find('form#edit-item');
		// Adding select options.
		$editForm.find('select#dto_ed').append(
			"<option value='Negocio - Global Customer Service (Incluye HDR)'>Negocio - Global Customer Service (Incluye HDR)</option>"
			+ "<option value='Negocio - Global Product'>Negocio - Global Product</option>"
			+ "<option value='Negocio - Global Sales'>Negocio - Global Sales</option>"
			+ "<option value='IT C&IB - CTO - Soluciones T&eacute;cnicas'>IT C&IB - CTO - Soluciones T&eacute;cnicas</option>"
			+ "<option value='IT C&IB - CTO - Arquitectura Funcional'>IT C&IB - CTO - Arquitectura Funcional</option>"
			+ "<option value='IT C&IB - CTO - Operaciones y Soporte (Sop Swift, CAU)'>IT C&IB - CTO - Operaciones y Soporte (Sop Swift, CAU)</option>"
			+ "<option value='IT C&IB - Control y Gesti&oacute;n'>IT C&IB - Control y Gesti&oacute;n</option>"
			+ "<option value='IT C&IB - E- commerce C&IB'>IT C&IB - E- commerce C&IB</option>"
			+ "<option value='IT C&IB - GCC Lending GTB & CFO'>IT C&IB - GCC Lending GTB & CFO</option>"
			+ "<option value='IT C&IB - GTB - Global Customer Solutions'>IT C&IB - GTB - Global Customer Solutions</option>"
			+ "<option value='IT C&IB - Global Transactional Product'>IT C&IB - Global Transactional Product</option>"
			+ "<option value='IT C&IB - B2B Global Support'>IT C&IB - B2B Global Support</option>").val(dto);
		$editForm.find('select#permiso_ed').append(
			"<option value='5'>Gestor IT</option>"
			+ "	<option value='4'>Gestor Demanda</option>"
			+ "	<option value='3'>User Admin</option>"
			+ "	<option value='2'>App Admin</option>"
			+ "	<option value='1'>Super</option>").val(cpermiso);
		// Inserting values
		$editForm.find('.edit_input.nombre').val(cnombre);
		$editForm.find('.edit_input.apellido1').val(cap1);
		$editForm.find('.edit_input.apellido2').val(cap2);
		$editForm.find('.edit_input.email').val(email);
		$.each(areas, function(i, value){
			console.log('ok');
			console.log(value);
			$editForm.find('.dtos').find('.radio-container').each(function(){
				var input = $(this).find('input#e-' + value);
				if(input.length > 0){
					input.prop('checked', true);
					$(this).find('label').addClass('checked');
				}
			});
		});
		// Activate the selectpickers.
		$editForm.find('.selectpicker').selectpicker();
		// Click event for the cancel button.
		$editForm.on('click', '.cancelar-ext', function (e) {
			$('#row'+$currentOpenEdit.data('row-id')).css({display:'table-row'});
			$currentOpenEdit.remove();
			return false;
		});
	},'html');
}