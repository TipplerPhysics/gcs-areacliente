$(function(){	
	
	
})


function verinforme(){
	var variable =$('#variableInf').find(":selected").val();
	$('#iframexls').attr('src',"/informeServlet?accion="+variable);
}