$(function() {
	
	$(window).scroll(function (event) {
	    var scroll = $(window).scrollTop();
	    if (scroll>350){
	    	$('#abc_child_scroll').addClass('inScroll');
	    	$('#abc_child_scroll').removeClass('scroll_hidden');
	    }else{
	    	$('#abc_child_scroll').removeClass('inScroll');
	    	$('#abc_child_scroll').addClass('scroll_hidden');

	    }
	});
	
	function drawLetters(){
		var isEmpty;
		var cajas = $('.clients_container').children();
		var a=0;
		var letra;
		var letrasValidas="";
		for (a; a<=cajas.length-1; a++){
			var div = $(cajas[a]);
			if (div.hasClass('letter_anchor')){
				isEmpty = true;
				letra = div;
			}else if (div.hasClass('clientes_letra')){
				var clientes = div.children();
				var b=0;
				for (b; b<=clientes.length-1;b++){
					var cliente = $(clientes[b]);
					if (!cliente.hasClass('tipo_h') && !cliente.hasClass('crit_h') && !cliente.hasClass('search_h')){
						isEmpty = false;
					}
				}				
				if (isEmpty==true){
					letra.css('display','none');
					letra.prev().css('display','none');
					div.css('display','none');
					
				}else{
					letra.css('display','block');
					letrasValidas += $(letra.find('span')[0]).text();
					letra.prev().css('display','inline-block');
					div.css('display','inherit');
				}
			}
			
		}
		
		drawbar(letrasValidas);
	}
	
	function drawbar(string){
		
		var barra = $('.abc').children();
		var z = 0;
		for (z=0; z<=barra.length-1;z++){
			var letras = $(barra[z]).children();		
			var a=0;
			for (a; a<=letras.length-1;a++){
				var letra_div = $(letras[a]);
				var letra = letra_div[0].id.substring(6,7);
				if (string.indexOf(letra)!=-1){
					letra_div.attr('href','#'+letra+'_anchor');
					letra_div.children().addClass('active');	
					letra_div.children().removeClass('inactive');				
	
				}else{
					letra_div.removeAttr('href');
					letra_div.children().addClass('inactive');
					letra_div.children().removeClass('active');
				}
			}
		}
		
	}
	
	
	$('#buscador_cliente').on('keyup', function(e) {
		var busqueda = $(this).val();
		var ln_busqueda = busqueda.length;
		var clientes = $('.client_box');
		var a = 0;
		for (a; a<=clientes.length-1;a++){
			if (busqueda.toUpperCase() == $(clientes[a]).children().text().substring(0,ln_busqueda).toUpperCase()){
				$(clientes[a]).removeClass('search_h');
			}else{
				$(clientes[a]).addClass('search_h');
			}
		}
		drawLetters();
	});
	
	$('#tip_crit').on('change', function(e) {		
		var val = $('#tip_crit').val();
		var cajas = $('.client_box');
		var a;
		if (val=="0"){
			for (a = 0; a<=cajas.length-1; a++){
				$(cajas[a]).removeClass('crit_h');
			}
		}else{
			for (a = 0; a<=cajas.length-1; a++){
				if (! $(cajas[a]).hasClass("crit_"+val)){
					$(cajas[a]).addClass('crit_h');
				}else{
					$(cajas[a]).removeClass('crit_h');
				}				
			} 
		}	
		
		drawLetters();
	});
	
	$('#tip_client').on('change', function(e) {		
		var val = $('#tip_client').val();
		var cajas = $('.client_box');
		var a;
		if (val=="0"){
			for (a = 0; a<=cajas.length-1; a++){
				$(cajas[a]).removeClass('tipo_h');
			}
		}else{
			for (a = 0; a<=cajas.length-1; a++){
				if (! $(cajas[a]).hasClass("tipo_"+val)){
					$(cajas[a]).addClass('tipo_h');
				}else{
					$(cajas[a]).removeClass('tipo_h');
				}				
			} 
		}	
		drawLetters();
	});
	
	
	
});