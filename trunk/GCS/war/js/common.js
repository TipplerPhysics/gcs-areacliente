var url = document.URL;

if (url.contains("localhost")){
	url="http://localhost:8888";
}else{
	url="http://gcs-areacliente.appspot.com";
}