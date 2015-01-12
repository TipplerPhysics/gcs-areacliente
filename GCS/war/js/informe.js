$(function(){
	
	if($('#iframepdf').length > 0) {
		var calendada = $('#iframepdf').find("src").val();
		var y = document.getElementById("iframepdf");
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
				var anios = data.Anios;
				var meses = data.Meses;
				var dias = data.Dias;
				var calendada = data.Calendada;
				if(calendada!="Calendada"&&calendada!="No calendada"){
					$('#iframepdf').attr('title',"No se encuentra el PDF");
				}
				else{
					
					var ca = document.getElementById("informe_select_calendada");					
					var x = document.getElementById("informe_select_anyo");
					var y = document.getElementById("informe_select_mes");
					var z = document.getElementById("informe_select_dia");
					var cont;
					var deb = x.options.length;
					for(cont=0;cont<deb;++cont) x.remove(0);
					deb = y.options.length;
					for(cont=0;cont<deb;++cont) y.remove(0);
					deb = z.options.length;
					for(cont=0;cont<deb;++cont){
						z.remove(0);
					}
					
					$(ca).find('option').each(function() {
						if($(this).prop('value') == calendada) {
							$(this).attr('selected', 'selected');
						}
					});
					
					$(x).append('<option value="default">Selecciona a&ntilde;o</option>');
					for(cont=0;cont<anios[0].length;++cont){
						if(parseInt(anios[0][cont]) == parseInt(anio)) {
							$(x).append('<option value="' + anios[0][cont] + '" selected>'+ anios[0][cont] +'</option>');
						}
						else {
							$(x).append('<option value="' + anios[0][cont] + '">'+ anios[0][cont] +'</option>');
						}
					}
					
					$(y).append('<option value="default">Selecciona mes</option>');
					for(cont=0;cont<meses[0].length;++cont){
						if(parseInt(meses[0][cont]) == parseInt(month)) {
							$(y).append('<option value="' + meses[0][cont] + '" selected>'+ meses[0][cont] +'</option>');
						}
						else {
							$(y).append('<option value="' + meses[0][cont] + '">'+ meses[0][cont] +'</option>');
						}
					}
					
					$(z).append('<option value="default">Selecciona d&iacute;a</option>');
					for(cont=0;cont<dias[0].length;++cont){
						if(parseInt(dias[0][cont]) == parseInt(day)) {
							$(z).append('<option value="' + dias[0][cont] + '" selected>'+ dias[0][cont] +'</option>');
						}
						else {
							$(z).append('<option value="' + dias[0][cont] + '">'+ dias[0][cont] +'</option>');
						}
					}
					
			    	/*var op2 = document.createElement('option');
			    	op2.text = anio;
			    	op2.value = "default";
			    	x.add(op2);*/
					
			    	/*var op3 = document.createElement('option');
			    	op3.text = month;
			    	op3.value = "default";
			    	y.add(op3);
			    	
			    	var op4 = document.createElement('option');
			    	op4.text = day;
			    	op4.value = "default";
			    	z.add(op4);*/
			    	
					var formURL = "/informeServlet?"+"accion=getInforme&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
					$('#iframepdf').attr('src',formURL);
					 var descarg = "/informeServlet?"+"accion=getInformeDown&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
					$('#down_btn').attr('href',descarg);
				}
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
						for(cont=0;cont<lim;++cont)
						x.remove(0);
					}
					var y = document.getElementById("informe_select_dia");
					var cont;
					var lim =y.options.length;
					for(cont=0;cont<lim;++cont) y.remove(0);
					
					
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
		}else{

			var y = document.getElementById("informe_select_mes");
			var z = document.getElementById("informe_select_dia");
			var cont;

			var deb = y.options.length;
			for(cont=0;cont<deb;++cont) y.remove(0);
			deb = z.options.length;
			for(cont=0;cont<deb;++cont){
				z.remove(0);
			}

			
	    	var option2 = document.createElement('option');
	    	option2.text = "Primero selecciona a\u00f1o";
	    	option2.value = "default";
	    	y.add(option2);
	    	
	    	var option3 = document.createElement('option');
	    	option3.text = "Primero selecciona mes";
	    	option3.value = "default";
	    	z.add(option3);
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
						var deb =x.options.length;
						for(cont=0;cont<deb;++cont)x.remove(0);
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
		}else{
			var z = document.getElementById("informe_select_dia");
			var cont;

			var deb = z.options.length;
			for(cont=0;cont<deb;++cont){
				z.remove(0);
			}
	    	
	    	var option3 = document.createElement('option');
	    	option3.text = "Primero selecciona mes";
	    	option3.value = "default";
	    	z.add(option3);
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
						 var formURL = "/informeServlet?"+"accion=getInforme&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
						 $('#iframepdf').attr('src',formURL);
						 var descarg = "/informeServlet?"+"accion=getInformeDown&year="+ anio +"&month="+month+"&day="+day+"&calendada="+calendada;
							$('#down_btn').attr('href',descarg);
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
					var y = document.getElementById("informe_select_mes");
					var z = document.getElementById("informe_select_dia");
					var Anios = data.Anios[0];
					if(Anios[0]==""||Anios[0]==null){
						alert("No existen informes con esas caracteristicas");
						var deb = x.options.length;
						for(cont=0;cont<deb;++cont) x.remove(0);
						deb = y.options.length;
						for(cont=0;cont<deb;++cont) y.remove(0);
						deb = z.options.length;
						for(cont=0;cont<deb;++cont){
							z.remove(0);
						}
						
				    	var option1 = document.createElement('option');
				    	option1.text = "Primero selecciona tipo subida";
				    	option1.value = "default";
				    	x.add(option1);
				    	
				    	var option2 = document.createElement('option');
				    	option2.text = "Primero selecciona a\u00f1o";
				    	option2.value = "default";
				    	y.add(option2);
				    	
				    	var option3 = document.createElement('option');
				    	option3.text = "Primero selecciona mes";
				    	option3.value = "default";
				    	z.add(option3);
				    	
					}else{
						var cont;
						var deb = x.options.length;
						for(cont=0;cont<deb;++cont) x.remove(0);
						deb = y.options.length;
						for(cont=0;cont<deb;++cont) y.remove(0);
						deb = z.options.length;
						for(cont=0;cont<deb;++cont){
							z.remove(0);
						}
						
				    	var option1 = document.createElement('option');
				    	option1.text = "Seleciona a\u00f1o";
				    	option1.value = "default";
				    	x.add(option1);
				    	
				    	var option2 = document.createElement('option');
				    	option2.text = "Primero selecciona a\u00f1o";
				    	option2.value = "default";
				    	y.add(option2);
				    	
				    	var option3 = document.createElement('option');
				    	option3.text = "Primero selecciona mes";
				    	option3.value = "default";
				    	z.add(option3);
				    	
						var a;
					    for (a in Anios){
					    	var option = document.createElement('option');
					    	option.text = option.value = Anios[a];
					    	x.add(option);
					    }
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
			var deb = x.options.length;
			for(cont=0;cont<deb;++cont) x.remove(0);
			deb = y.options.length;
			for(cont=0;cont<deb;++cont) y.remove(0);
			deb = z.options.length;
			for(cont=0;cont<deb;++cont){
				z.remove(0);
			}
			
	    	var option1 = document.createElement('option');
	    	option1.text = "Primero selecciona tipo subida";
	    	option1.value = "default";
	    	x.add(option1);
			
	    	var option2 = document.createElement('option');
	    	option2.text = "Primero selecciona a\u00f1o";
	    	option2.value = "default";
	    	y.add(option2);
	    	
	    	var option3 = document.createElement('option');
	    	option3.text = "Primero selecciona mes";
	    	option3.value = "default";
	    	z.add(option3);
		}
	});
});