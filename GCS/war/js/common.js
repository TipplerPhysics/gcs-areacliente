var url = document.URL;

if (url.indexOf("localhost")>1){
	url="http://localhost:8888";
}else{
	url="http://gcs-areacliente.appspot.com";
}