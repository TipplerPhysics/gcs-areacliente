$(function(){
	$('#report-form').on('change','#informe_select_anyo', function (e){
		
		 var option = $(this).find(":selected");
		 var anio = option.val();
		 if(anio != "default"){
			 var formURL = "/informeServlet";
			 var postData="accion=getMonths&year="+ anio;
			 
			 $.ajax({
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					var Mes =data.Meses[0];
					var x = document.getElementById("informe_select_mes");
					/*Elimina las opciones presentadas hasta el momento*/
					if(Mes[0]!=""){
						var cont;
						var lim = x.options.length;
						for(cont=0;cont<=x.options.length;++cont)
						x.remove(0);
					}
					var y = document.getElementById("informe_select_dia");
					var cont;
					for(cont=0;cont<=y.options.length;++cont) y.remove(0);
					
					
			    	var option1 = document.createElement('option');
			    	option1.text = "Seleciona mes";
			    	option1.value = "default";
			    	x.add(option1);
			    	
			    	var option2 = document.createElement('option');
			    	option2.text = "Primero selecciona mes";
			    	option2.value = "default";
			    	y.add(option2);
					/*Aniade las nuevas opciones para el determinado anio*/
					var a;
				    for (a in Mes){
				    	var option = document.createElement('option');
				    	option.text = option.value = Mes[a];
				    	x.add(option);
				    }
				
				},
				error:function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);
					console.log("failure");
				}
			});
			}
	});
	$('#report-form').on('change','#informe_select_mes', function (e){
		
		 var option = $(this).find(":selected");
		 var month = option.val();
		 if (month !="default"){
			 var anio = $('#informe_select_anyo').find(":selected").val();
			 var formURL = "/informeServlet";
			 var postData="accion=getDays&year="+ anio +"&month="+month;
			 
			 $.ajax({
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					var Dias =data.Dias[0];
					var x = document.getElementById("informe_select_dia");
					/*Elimina las opciones presentadas hasta el momento*/
					if(Dias[0]!=""){
						var cont;
						for(cont=0;cont<x.length;++cont)
						x.remove(0);
					}
					
			    	var option1 = document.createElement('option');
			    	option1.text = "Seleciona dia";
			    	option1.value = "default";
			    	x.add(option1);
					/*Aniade las nuevas opciones para el determinado mes*/
					var a;
				    for (a in Dias){
				    	var option = document.createElement('option');
				    	option.text = option.value = Dias[a];
				    	x.add(option);
				    }
	
				
				},
				error:function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);
					console.log("failure");
				}
			});
			}
	});	 
	$('#report-form').on('change','#informe_select_dia', function (e){
				
				 var day = $(this).find(":selected").val();
				 var anio = $('#informe_select_anyo').find(":selected").val();
				 var month = $('#informe_select_mes').find(":selected").val();
				 var calendada = $('#informe_select_calendada').find(":selected").val();
				 if (calendada!=""&&calendada!=null&&calendada!="default"){
					 if (day!=""&&day!=null&&day!="default"){
						 var formURL = "/informeServlet";
						 var postData="accion=getInforme&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
						 
						 $.ajax({
							url : formURL,
							type: "GET",
							data : postData,
							success:function(data, textStatus, jqXHR) 
							{
								var informe =data.informe;
												
							},
							error:function(jqXHR, textStatus, errorThrown) {
								console.log(textStatus);
								console.log(errorThrown);
								console.log("failure");
							}
						 });
					 }
				 }else{
					 alert("Seleccione si es calendada o no");
				 }
				 
	});
	$('#report-form').on('change','#informe_select_calendada', function (e){
		var dia = $('#informe_select_dia').find(":selected").val();
		var calendada = $('#informe_select_calendada').find(":selected").val();
		if(dia!="" && dia!=null && dia!="default" &&calendada!="default"){
			 var anio = $('#informe_select_anyo').find(":selected").val();
			 var month = $('#informe_select_mes').find(":selected").val();
			 
			 var formURL = "/informeServlet";
			 var postData="accion=getInforme&year="+ anio +"&month="+month+"&day="+dia+"&calendada="+calendada;
			 
			 $.ajax({
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					var informe =data.informe;
									
				},
				error:function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);
					console.log("failure");
				}
			 });
		}
	});
});