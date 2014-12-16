$(function(){
	
	
	var calendada = $('#iframepdf').find("src").val();
	var y = document.getElementById("iframepdf");
	if (true){
		var formURL = "/informeServlet";
		 var postData="accion=getDefault";
		 
		 $.ajax({
			url : formURL,
			type: "GET",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				var anio = data.Anio;
				var month = data.Mes;
				var day = data.Dia;
				var calendada = data.Calendada;
				var formURL = "/informeServlet?"+"accion=getInforme&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
				$('#iframepdf').attr('src',formURL);
			},
			error:function(jqXHR, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
				console.log("failure");
			}
		});
	}
	
	$('#report-form').on('change','#informe_select_anyo', function (e){
		
		 var option = $(this).find(":selected");
		 var anio = option.val();
		 var calendada = $('#informe_select_calendada').find(":selected").val();
		 
		 
		 if(anio != "default"){
			 var formURL = "/informeServlet";
			 var postData="accion=getMonths&year="+ anio+"&calendada="+calendada;
			 
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
		 var calendada = $('#informe_select_calendada').find(":selected").val();
		 var month = option.val();
		 if (month !="default"){
			 var anio = $('#informe_select_anyo').find(":selected").val();
			 var formURL = "/informeServlet";
			 var postData="accion=getDays&year="+ anio +"&month="+month+"&calendada="+calendada;
			 
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
						for(cont=0;cont<=x.options.length;++cont)
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
							type: "POST",
							data : postData,
							success:function(data, textStatus, jqXHR) 
							{
										var formURL = "/informeServlet?"+"accion=getInforme&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
										$('#iframepdf').attr('src',formURL);
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
		var calendada = $('#informe_select_calendada').find(":selected").val();
		if(calendada!="default"){
			
			 var formURL = "/informeServlet";
			 var postData="accion=getYears"+"&calendada="+calendada;
			 
			 $.ajax({
				url : formURL,
				type: "GET",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					var x = document.getElementById("informe_select_anyo");
					var Anios = data.Anios[0];
					if(Anios[0]==""||Anios[0]==null)alert("No existen informes con esas caracteristicas");
					var cont;
					for(cont=0;cont<=x.options.length;++cont)
					x.remove(0);
					
			    	var option1 = document.createElement('option');
			    	option1.text = "Seleciona a\u00f1o";
			    	option1.value = "default";
			    	x.add(option1);
			    	
					var a;
				    for (a in Anios){
				    	var option = document.createElement('option');
				    	option.text = option.value = Anios[a];
				    	x.add(option);
				    }
					
				},
				error:function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);
					console.log("failure");
				}
			 });
			
		}else{
			var x = document.getElementById("informe_select_anyo");
			var y = document.getElementById("informe_select_mes");
			var z = document.getElementById("informe_select_dia");
			var cont;
			for(cont=0;cont<=x.options.length;++cont) x.remove(0);
			for(cont=0;cont<=y.options.length;++cont) y.remove(0);
			for(cont=0;cont<=z.options.length;++cont) z.remove(0);
			
	    	var option1 = document.createElement('option');
	    	option1.text = "Primero selecciona tipo subida";
	    	option1.value = "default";
	    	x.add(option1);
			
	    	var option1 = document.createElement('option');
	    	option1.text = "Primero selecciona a\u00f1o";
	    	option1.value = "default";
	    	y.add(option1);
	    	
	    	var option2 = document.createElement('option');
	    	option2.text = "Primero selecciona mes";
	    	option2.value = "default";
	    	z.add(option2);
		}
	});
});