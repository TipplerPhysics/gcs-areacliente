/* En estas variables guardo la informacion de la linea del grid que se esta editando */
var cnombre = "";
var cap1="";
var cap2="";
var cemail="";
var cpermiso="";
var areas;
var dto;

// on pageload complete
$(function() {
	var userBoxSize = 0;
	$('#newUserButton').click(function(e){
		if ($('#newUserButton').hasClass('white-btn')){
			$('#newUserButton').removeClass('white-btn');
			$('.user_span').removeClass('blue');

			userBoxSize = $('.new-user-form-holder.open').outerHeight();
			$('.new-user-form-holder.open').css('height', userBoxSize);
			setTimeout(function(){
				$('.new-user-form-holder.open').removeClass('open').css('height', '0px');
			}, 25);
			
		} else {
			$('#newUserButton').addClass('white-btn');
			$('.user_span').addClass('blue');
			$('.new-user-form-holder').addClass('open');
			if(userBoxSize > 0) {
				setTimeout(function(){
					$('.new-user-form-holder').css('height', userBoxSize);
				}, 25);
			}
			setTimeout(function(){
				$('.new-user-form-holder.open').css('height', 'auto');
			}, 1000);
		}
	});

	$('#confirm-delete').on('show.bs.modal', function(e) {
		 $(this).find('.danger').attr('href', $(e.relatedTarget).data('href'));
	});

	$('.papelera').on('click', function(e) {
		$('#deleteUser').attr('name',$(this).attr('name'));
	});

	$('#deleteUser').on('click', function(e) {
		var id= $(this).attr('name');
		 var formURL = url + "/usersServlet?";
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

	$('.lapiz').on('click', function(e) {
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

	$('.table_results').on('click', '.cancelar-ext', function (e) {
		var id= $('.editing').attr('name');
		var arr = [cnombre,cap1,cap2,cemail,cpermiso];
		$('#papelera'+id).attr("data-toggle","modal");
		undoRow(id,arr);
	});

	$('.table_results').on('click', '.guardar-ext', function (e) {
		var id= $('.editing').attr('name');
		var campo = $('.col'+id);
		var areas =  $('.dtos').find('input');
		var $fila= $('#row'+id);
		
		var nombre = $(campo[0]).val();
		var ap1 = $(campo[1]).val();
		var ap2 = $(campo[2]).val();
		var email = $(campo[3]).val();
		var permiso = $('#permiso_ed').val();
		var dto = $('#dtos_select option:selected').text();
		
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
				if (data.success==("true")){
					$('.extended-row').remove();
					$('#row'+id).removeClass('editing');

					updateRow(id);
				}
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
					var html=generateRow(postData,data.id,data.permiso);
					
					$('.table_results').prepend(html);
					
					$('#message_div').removeClass("error").addClass("success");
					if ($('.new-user-form-holder').height()<190){
						$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
					}
					$('#span_message').html("El usuario ha sido creado de forma correcta.");
					$('#message_div').css('display','block');
					
					resetUserForm();
					
					setTimeout(function() { 
						$('#user_form')[0].reset();
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

function generateRow(data,id,permiso){
	var a = data.split('&');
	var nombre = $('#nombre').val();
	var ap1 = $('#ap1').val();
	var ap2 = $('#ap2').val();
	var email = $('#email').val();
	dto = $('#dto').val();

	var html = "<tr id=row"+id+">"
		+ "<td><span>"+nombre+"</span></td>"
		+ "<td><span>"+ap1+"</span></td>"
		+ "<td><span>"+ap2+"</span></td>"
		+ "<td><span>"+email+"</span></td>"
		+ "<td><span>"+permiso+"</span></td>"
		+ "<td><img class='vs' src='../img/vs.png'>"
		+ "<a class='papelera' name="+id+" data-toggle='modal' data-target='#confirm-delete'> </a>"
		+ "<a class='lapiz' name="+id+"></a></td></tr>";
	
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
	$('.extended-row').remove();
	var celdas = $('#row'+id).children();
	for (var a =0; celdas.length-2 >= a;a++){
		var $celda = $(celdas[a]);
		var span = $celda.children().val();
		$celda.children().remove();
		$celda.prepend("<span>"+arr[a]+"</span>");
	}
	
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

	$('#row'+id).after("<tr class='extended-row'><td><div class='extended-div'></div></td><td></td><td></td><td></td><td></td><td></td></tr>");
	
	generateChecks("extended-user.html","extended-div");
	
	
	var celdas = $('#row'+id).children();
	cnombre = $(celdas[0]).children().html();
	cap1 = $(celdas[1]).children().html();
	cap2 = $(celdas[2]).children().html();
	cemail = $(celdas[3]).children().html();
	cpermiso = $(celdas[4]).children().html();
	
	for (var a =0; celdas.length-3 >= a;a++){
		var $celda = $(celdas[a]);
		var span = $celda.children().html();
		$celda.children().remove();
		$celda.prepend("<input type='text' class='edit_input col"+id+"' value=" + span +">");
	}

	var $celda = $(celdas[4]);
	var span = $celda.children().html();
	$celda.children().remove();
	$celda.prepend("<select name='permiso' id='permiso_ed' class='long'>"
		+ "	<option value='5'>Gestor IT</option>"
		+ "	<option value='4'>Gestor Demanda</option>"
		+ "	<option value='3'>User Admin</option>"
		+ "	<option value='2'>App Admin</option>"
		+ "	<option value='1'>Super</option>"
		+ "	</select>");	
	
	var value="0";
	
	if (span=="Gestor IT"){
		value="5";
	}else if (span=="Gestor Demanda"){
		value="4";
	}else if (span=="User Admin"){
		value="3";
	}else if (span=="App Admin"){
		value="2";
	}else if (span=="Super"){
		value="1";
	}
	
	$('#lapiz'+id).addClass('inactive');
	$('#papelera'+id).addClass('inactive');
	
	
	var lng = areas.length;
	
	if (lng==1 && areas[0]==""){
		lng=0;
	}
	
	setTimeout(function(){
		$('#dtos_select').val(dto);
		$('#permiso_ed').val(value);

		if (lng>0){
		for (var n=0; n<lng;n++){
			
			var name = areas[n];
			
			$('#e-'+name)[0].setAttribute("checked", "checked");
		}
	}},750);
}

function resetUserForm(){
	$('#nombre').val("");
	$('#ap1').val("");
	$('#ap2').val("");
	$('#email').val("");
	
}