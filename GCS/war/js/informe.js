$(function(){
	$('#report-form').on('change','#informe_select_anyo', function (e){
		
		 var option = $(this).find(":selected");
		 var anio = option.val();
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
					for(cont=0;cont<x.length;++cont)
					x.remove(cont);
				}
				
		    	var option1 = document.createElement('option');
		    	option1.text = "Seleciona dia";
		    	x.add(option1);
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
	});
	$('#report-form').on('change','#informe_select_mes', function (e){
		
		 var option = $(this).find(":selected");
		 var month = option.val();
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
					x.remove(cont);
				}
				
		    	var option1 = document.createElement('option');
		    	option1.text = "Seleciona dia";
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
	});	 
	$('#report-form').on('change','#informe_select_dia', function (e){
				
				 var day = $(this).find(":selected").val();
				 var anio = $('#informe_select_anyo').find(":selected").val();
				 var month = $('#informe_select_mes').find(":selected").val();
				 
				 var formURL = "/informeServlet";
				 var postData="accion=getInforme&year="+ anio +"&month="+month+"&day="+day;
				 
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
	});
});