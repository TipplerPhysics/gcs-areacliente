$(function(){	
	$("#variableInf").on('change', function(e) {
		if($("#variableInf").val()=="Cartera"){
			$('#fechas-cartera-filter').removeClass("hidden");
		}else{
			$('#fechas-cartera-filter').addClass("hidden");
		}
		
	});
	
	$("#input_secase").on('change', function(e) {actualizartabla();});
	$("#input_tricase").on('change', function(e) {actualizartabla();});
	$("#input_firstcase").on('change', function(e) {actualizartabla();});
			
			
			
	

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


function actualizartabla() {
	
	var $form = $("#formulariocomple");
	var $formData = $form.serialize();
	var postData= $formData+"&accion=consult";
	var formURL = $form.attr("action"); 
	$("#tablacompleja").html("");
	$("#tablacompleja").html("<p style='margin-left:45% ;'><img  src=\"../img/ajax-loader.gif\" height=\"87\" width=\"87\" /></p>");
	 $.ajax(			
		{
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				
				if (data.success=="true"){
					var tabla = '';
					var tableArray = data.tableArray[0];
					var tamano = tableArray.length;
					var inhead = false;
					for (var i = 0 ; i < tamano;i++){
						
						if(tableArray[i]!="<(*)finLine(*)>"&&tableArray[i]!="<(*)head(*)>"&&tableArray[i]!="<(*)finHead(*)>"&&tableArray[i]!="<(*)finTable(*)>"){
							if(inhead){
								tabla+="<th>";
								tabla+=tableArray[i];
								tabla+="<th>";
							}else{
								tabla+="<td>";
								tabla+=tableArray[i];
								tabla+="<td>";									
							}
						}else{
							if(tableArray[i]=="<(*)finLine(*)>"){
								tabla+="</tr><tr>";
							}else{	
								if(tableArray[i]=="<(*)head(*)>"){
									inhead=true;
									tabla+="<table class=\"table\"><thead><tr>";
								}else{
									if(tableArray[i]=="<(*)finHead(*)>"){
										inhead=false;
										tabla+="</tr></thead><tbody><tr>";
									}else{
										if(tableArray[i]=="<(*)finTable(*)>"){
											tabla+="</tr></tbody></table>";
										}
									}
								}	
							}
						}
					}
					$("#tablacompleja").html("");
					$("#tablacompleja").html(tabla);						
				}
			}
		});
}