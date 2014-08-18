var url = document.URL;

if (url.indexOf("localhost")>1){
	url="http://localhost:8888";
}else{
	url="http://gcs-areacliente.appspot.com";
}

function disableSearch(){
	var fila = $($('thead').children()[1]).children();
	var a = 0;
	for (a=0;a<=fila.length-1;a++){
		$(fila[a]).children().prop('disabled', true);
	}
}

function enableSearch(){
	var fila = $($('thead').children()[1]).children();
	var a = 0;
	for (a=0;a<=fila.length-1;a++){
		$(fila[a]).children().prop('disabled', false);
	}
}