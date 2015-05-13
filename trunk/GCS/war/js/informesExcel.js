$(function(){	
	$("#variableInf").on('change', function(e) {
		if($("#variableInf").val()=="Cartera"){
			$('#fechas-cartera-filter').removeClass("hidden");
		}else{
			$('#fechas-cartera-filter').addClass("hidden");
		}
		
	});

})


function verinforme(){
	var variable =$('#variableInf').find(":selected").val();
	var desde =$('#desdeFilterInf').val();
	var hasta =$('#hastaFilterInf').val();
	$('#iframexls').attr('src',"/informeExcelServlet?accion="+variable+"&desde="+desde+"&hasta="+hasta);
}

function verinforme2(){
	var $form = $("#formulariocomple");
	var postData =  $form.serialize();
	$('#iframexls').attr('src',"/informeExcelServlet?accion=complejo&"+postData);
}