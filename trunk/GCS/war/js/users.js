/* En estas variables guardo la informacion de la linea del grid que se esta editando */
var cnombre = "";
var cap1="";
var cap2="";
var cemail="";
var cpermiso="";
var areas;
var dto;

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

$('#newUserButton').click(function(e){
	if ($('#newUserButton').hasClass('white-btn')){
		$('#newUserButton').removeClass('white-btn');
		$('.user_span').removeClass('blue');

		$('.new-user-form-holder').animate({height: "0px"}, 500, function(){
			$('.new-user-form-holder').css('display','none');
		});
	} else {
		$('#newUserButton').addClass('white-btn');
		$('.user_span').addClass('blue');
		$('.new-user-form-holder').css('display','inline-block');
		$('.new-user-form-holder').animate({height: "385px"}, 500);
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

	var postData = "&nombre="+nombre+"&id="+id+"&dto="+dto+"&permiso="+permiso+"&email="+email+"&ap1="+ap1+"&ap2="+ap2+"&accion=update&areas="+areas;
    var formURL = "/usersServlet";
	 $.ajax({
		        url : formURL,
		        type: "POST",
		        data : postData,
		        success:function(data, textStatus, jqXHR) 
		        {
		            //data: return data from server
		        	if (data.success==("true")){
		        		$('.extended-row').remove();
		        		$('#row'+id).removeClass('editing');
		        		
		        		updateRow(id);
		        	}
		        }
		    });
});

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

/*	$('#return'+id).css('display','none');
	$('#guardar'+id).css('display','none');
	$('#papelera'+id).css('display','inline-block');
	$('#lapiz'+id).css('display','inline-block'); */
	
	$('#lapiz'+id).removeClass('inactive');
	$('#papelera'+id).removeClass('inactive');
}

function undoEditRow(id){
	$('#papelera'+id).attr('data-toggle','modal');
	$('#row'+id).removeClass('editing');
	
	
	$('.extended-row').remove();
	
	updateRow(id);
}

function generateRow(pagina,destino){
	$.get('../html/'+pagina,null,function(result) {
	    $(".extended-div").html(result); // Or whatever you need to insert the result
	    for (var e=0; e<areas.length; e++){
			if (areas[e].indexOf("Onboarding")!=-1){
				$('#onboarding').attr('checked',"true");
			}else if (areas[e].indexOf("Servicing")!=-1){
				$('#servicing').attr('checked',"true");
			}else if (areas[e].indexOf("ITCIB")!=-1){
				$('#itcib').attr('checked',"true");
			}else if (areas[e].indexOf("Customer")!=-1){
				$('#gcs').attr('checked',"true");
			}else if (areas[e].indexOf("Product")!=-1){
				$('#global-product').attr('checked',"true");
			}else if (areas[e].indexOf("Clientes")!=-1){
				$('#clientes').attr('checked',"true");
			}
		}
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
	
	generateRow("extended-user.html","extended-div");
	
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
	
	
	
	$('#permiso_ed option:selected').val(value);
	$('#dtos_select').val(dto);
	
	
	
	var lng = areas.length;
	
	if (lng==1 && areas[0]==""){
		lng=0;
	}
	
	setTimeout(function(){if (lng>0){
		for (var n=0; n<lng;n++){
			console.log(areas);
			console.log("n= " + n + " lng=" + lng);
			var name = areas[n];
			console.log(name);
			console.log("$('#e-"+name+"')");
			console.log($('#e-'+name));
			$('#e-'+name)[0].setAttribute("checked", "checked");
		}
	}},500);
}

$(function() {
	// Submit for creating a new user.
	$("#new-user-form").submit(function(e) {
		e.preventDefault(); //STOP default action

		var areas = "";
		 if ($('#onboarding')[0].checked==true){
			 areas += "Onboarding-"
		 }
		 if ($('#servicing')[0].checked==true){
			 areas += "Servicing-"
		 }
		 if ($('#itcib')[0].checked==true){
			 areas += "ITCIB-"
		 }
		 if ($('#gcs')[0].checked==true){
			 areas += "Global Customer Service-"
		 }
		 if ($('#global-product')[0].checked==true){
			 areas += "Global product-"
		 }
		 if ($('#clientes')[0].checked==true){
			 areas += "Clientes-"
		 }
		 
		var servicing = $('#servicing').val();
		
	    var postData = $(this).serialize() + "&accion=new&areas="+areas;
	    var formURL = $(this).attr("action");
	    $.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	        success:function(data, textStatus, jqXHR) 
	        {
	        	alert('succes');
	        	console.log($(data));
	            //data: return data from server
	        	if (data.success==("true")){
	        		var html=generateRow(postData,data.id,data.permiso);
	        		
	        		$('.table_results').prepend(html);
	        		
	        		$('#message_div').removeClass("error");
	        		$('#message_div').addClass("success");
	        		if ($('.new-user-form-holder').height()<190){
		        		$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);
	        		}
	        		$('#span_message').html("El usuario ha sido creado de forma correcta.")
	        		$('#message_div').css('display','block');
	        		setTimeout(function() { 
	        			$('#user_form')[0].reset();
	        			$( "#message_div" ).fadeOut( "slow", function() {
	        				$('#span_message').html("");
	        		  }); }, 5000);
	        	}else{
	        		$('#message_div').removeClass("success");
	        		$('#message_div').addClass("error");
	        		if ($('.new-user-form-holder').height()<190){
		        		$('.new-user-form-holder').height($('.new-user-form-holder').height()+35);

	        		}
	        		$('#span_message').html(data.error);
	        		$('#message_div').css('display','block');
	        		
	        	}
	        },
	        error: function(jqXHR, textStatus, errorThrown) 
	        {
	        	if (data.failure==("true")){
	        		$('#span_message').html(data.errormessage)
	        		$('#message_div').css('display','block');
	        		
	        		setTimeout(function() { 
	        			$( "#message_div" ).fadeOut( "slow", function() {
	        				$('#span_message').html("");
	        				
	        		  }); }, 5000);
	        	}    
	        }
	    });
	});
	// Setup form validation on the #register-form element
	$('form').validate({

	    // Specify the validation rules
	    rules: {
	        email: {
	            required: true,
	            email: true
	        },
	        password: {
	            required: true,
	            minlength: 5
	        },
	        agree: "required"
	    },
	    
	    // Specify the validation error messages
	    messages: {
	        firstname: "Please enter your first name",
	        lastname: "Please enter your last name",
	        password: {
	            required: "Please provide a password",
	            minlength: "Your password must be at least 5 characters long"
	        },
	        email: "Please enter a valid email address",
	        agree: "Please accept our policy"
	    },
	    
	    submitHandler: function(form) {
	        form.submit();
	    }
	});

	$.extend($.validator.messages, {
		required: "Este campo es obligatorio.",
		remote: "Por favor, rellena este campo.",
		email: "Por favor, escribe una dirección de correo válida.",
		url: "Por favor, escribe una URL válida.",
		date: "Por favor, escribe una fecha válida.",
		dateISO: "Por favor, escribe una fecha (ISO) válida.",
		number: "Por favor, escribe un número válido.",
		digits: "Por favor, escribe sólo dígitos.",
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
});