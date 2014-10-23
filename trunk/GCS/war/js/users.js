/* En estas variables guardo la informacion de la linea del grid que se esta editando */
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




