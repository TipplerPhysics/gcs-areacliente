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
		var id= $(this).attr('name');
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
	areas= areas.split("_");
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
		// Add the result to the element
		$currentOpenEdit.find(".extended-div").html(result);
		// The form we're editing in.
		var $editForm = $currentOpenEdit.find('form#edit-item');
		// copia options de select de formulario de creacion
		var $dtoOptions = $('select#dto_select option').clone();
		$editForm.find('select#dto_ed').append($dtoOptions).val(dto);
		$editForm.find('select#dto_ed option').first().remove();		
		// copia options de select de creacion
		var $permisoOptions = $('select#permiso_select option').clone();
		$editForm.find('select#permiso_ed').append($permisoOptions).val(cpermiso);
		$editForm.find('select#permiso_ed option').first().remove();
		// Inserting values
		$editForm.find('.edit_input.nombre').val(cnombre);
		$editForm.find('.edit_input.apellido1').val(cap1);
		$editForm.find('.edit_input.apellido2').val(cap2);
		$editForm.find('.edit_input.email').val(email);
		$.each(areas, function(i, value){
			$editForm.find('.areas').find('.radio-container').each(function(){
				var input = $(this).find('input#e-' + value);
				if(input.length > 0){
					input.prop('checked', true);
					$(this).find('label').addClass('checked');
				}
			});
		});
		// Activate everything.
		$editForm.find('.selectpicker').selectpicker();
		initForms();
		initValidator();

		// Click event for the cancel button.
		$editForm.on('click', '.cancelar-ext', function (e) {
			$('#row'+$currentOpenEdit.data('row-id')).css({display:'table-row'});
			$currentOpenEdit.remove();
			return false;
		});
		// Click event for the save button.
		$editForm.on('click', '.guardar-ext', function (e) {
			if($editForm.valid()){
				var $editItemHolder = $(this).closest('#edit-item-holder');
				// Collect all the information.
				var id= $editItemHolder.data('row-id');
				var nombre = $editItemHolder.find('input.edit_input.nombre').val();
				var ap1 = $editItemHolder.find('input.edit_input.apellido1').val();
				var ap2 = $editItemHolder.find('input.edit_input.apellido2').val();
				var email = $editItemHolder.find('input.edit_input.email').val();
				var permiso = $('#permiso_ed').val();
				var dto = $('#dto_ed option:selected').text();
				dto = dto.replace('&','#');
				// Get all checked boxes in to one string, devided by _ (underscore).
				var $checkedBoxes = $editItemHolder.find('.areas').find('.radio-container').find('input:checked');
				var areas="";
				$checkedBoxes.each(function(i){
					areas += $(this).val();
					if(i < $checkedBoxes.length - 1){
						areas += "_";
					}
				});
				// Update the database.
				var postData = "&nombre="+nombre+"&id="+id+"&dto="+dto+"&permiso="+permiso+"&email="+email+"&ap1="+ap1+"&ap2="+ap2+"&accion=update&areas="+areas;
				var formURL = "/usersServlet";
				$.ajax({
					url : formURL,
					type: "POST",
					data : postData,
					success:function(data, textStatus, jqXHR) {
						location.reload();
					}
				});
			}
			return false;
		});
	},'html');
}