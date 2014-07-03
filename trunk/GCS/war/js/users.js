function generateRow(data,id,permiso){
	
	var a = data.split('&');
	
	var nombre = $('#nombre').val();
	var ap1 = $('#ap1').val();
	var ap2 = $('#ap2').val();
	var email = $('#email').val();
	var dto = $('#dto').val();
	
	
	var html = "<tr id=row"+id+">"
	+ "<td><span>"+nombre+"</span></td>"
	+ "<td><span>"+ap1+"</span></td>"
	+ "<td><span>"+ap2+"</span></td>"
	+ "<td><span>"+email+"</span></td>"
	+ "<td><span>"+permiso+"</span></td>"
	+ "<td><img class='vs' src='../img/vs.png'>"
	+ "<a class='papelera' name="+id+"  data-toggle='modal' data-target='#confirm-delete'> </a>"
	+ "<a class='lapiz' name="+id+"></a></td></tr>" 
			return html;
}

$('#newUserButton').click(function(e){
	if ($('#newUserButton').hasClass('white-btn')){
		$('#newUserButton').removeClass('white-btn');
		$('.user_span').removeClass('blue');

		$('.newUser-form').animate({height: "0px"}, 500, function(){
			$('.newUser-form').css('display','none');
		});
	}else{
		$('#newUserButton').addClass('white-btn');
		$('.user_span').addClass('blue');
		$('.newUser-form').css('display','inline-block');
		$('.newUser-form').animate({height: "260px"}, 500);
	}	
});


$('#submit_user_form').click(function(e){
	
	$('#submit_user_form').validate({
		rules: {
			email: {
	              required: true,
	              email: true
	            },
	            
            nombre: {
	              required: true,
	            }
		},
		messages: {
	        email:{
	            required: "Please enter name!"
	        }
		}
	});
	
	$('#submit_user_form').valid();
	
	$("#user_form").submit(function(e)
			{
			    var postData = $(this).serialize() + "&accion=new";
			    var formURL = $(this).attr("action");
			    $.ajax(
			    {
			        url : formURL,
			        type: "POST",
			        data : postData,
			        success:function(data, textStatus, jqXHR) 
			        {
			            //data: return data from server
			        	if (data.success==("true")){
			        		
			        		
			        		var html=generateRow(postData,data.id,data.permiso);
			        		
			        		$('.table_results').prepend(html);
			        		
			        		$('#message_div').removeClass("error");
			        		$('#message_div').addClass("success");
			        		if ($('.newUser-form').height()<190){
				        		$('.newUser-form').height($('.newUser-form').height()+35);
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
			        		if ($('.newUser-form').height()<190){
				        		$('.newUser-form').height($('.newUser-form').height()+35);

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
			    e.preventDefault(); //STOP default action
			});
			 
			$("#user_form").submit(); //Submit  the FORM
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
	if ($('.editing')[0] != undefined)
	{
		var rowid = $('.editing').attr('id').split("w")[1];
		undoEditRow(rowid);
		editRow(id);
	}else{
		editRow(id);
	}
	
		
});

function undoEditRow(id){
	$('#row'+id).removeClass('editing');
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
	
	
}


function editRow(id){
	$('#row'+id).addClass('editing');
	var celdas = $('#row'+id).children();
	for (var a =0; celdas.length-3 >= a;a++){
		var $celda = $(celdas[a]);
		var span = $celda.children().html();
		$celda.children().remove();
		$celda.prepend("<input type='text' class='edit_input' id='col"+id+"' value=" + span +">");
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
	
	$('#permiso_ed').val(value);
}