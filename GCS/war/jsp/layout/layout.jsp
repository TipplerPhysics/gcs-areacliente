<!DOCTYPE html>


<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Portal GCS</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
      	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
      	<script src="../tools/jquery.validate.js" type="text/javascript"></script> 
		<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	  	<script src="../js/dev/scripts.js" type="text/javascript"></script>
		<script src="../components/bootstrap/datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
		<script src="../components/bootstrap/select/bootstrap-select.min.js" type="text/javascript"></script>

		<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
	  	<link rel="stylesheet" type="text/css" href="../css/styles.min.css" /> 
  		<link rel="stylesheet" type="text/css" href="../components/bootstrap/datetimepicker/css/bootstrap-datetimepicker.min.css" />
  		<link rel="stylesheet" type="text/css" href="../components/bootstrap/select/bootstrap-select.css" />
	  	
	</head>
	<body>
		<tiles:insert attribute="header"/>
		<div class="body-layout">
			<div class="body-container"> 
				<tiles:insert attribute="body"/> 
			</div>
		</div>	
		 
	 	<c:if test="${sessionScope.permiso <= 3}">
				<tiles:insert attribute="side-bar"/> 
		</c:if>
		
		<!--   <tiles:insert attribute="footer"/>  -->
		
		<!-- TODO: REMOVE THIS IS FOR DEV -->
		<script src="//localhost:35729/livereload.js" type="text/javascript"></script>
	</body>
</html>