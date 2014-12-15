<!DOCTYPE html>


<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Portal GCS</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15"  />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		
		<link rel="stylesheet" type="text/css" href="../components/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="../tools/css/jquery-ui.min.css" />
		<link rel="stylesheet" type="text/css" href="../tools/css/jquery-ui.theme.min.css" />
		<link rel="stylesheet" type="text/css" href="../tools/css/jquery-ui.structure.min.css" />
  		<link rel="stylesheet" type="text/css" href="../components/bootstrap/select/bootstrap-select.css" />
  		<link rel="stylesheet" type="text/css" href="../components/checkbox/jquery.marmots-checkbox.css" />
  		<link rel="stylesheet" type="text/css" href="../tools/web/viewer.css" />
  		
	  	<link rel="stylesheet" type="text/css" href="../css/styles.min.css" /> 
	  		
	  	<!--[if lte IE 9]>
  			<link rel="stylesheet" type="text/css" href="../css/internetExplorer.css" /> 	
  			
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

	  	 <![endif]-->
		
		
      	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
      	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
      	<!-- Custom UI: datepicker only -->
      	<script src="../tools/jquery-ui.min.js" type="text/javascript"></script>
      	<script src="../tools/jquery-ui-datepicker-es.js" type="text/javascript"></script>
      	<script src="../tools/jquery.validate.js" type="text/javascript"></script> 
		<script src="../components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="../components/checkbox/jquery.marmots-checkbox.js" type="text/javascript"></script>
		<script src="../components/bootstrap/select/bootstrap-select.min.js" type="text/javascript"></script>
		<script src="../js/dev/scripts.js" type="text/javascript"></script>
		<script src="../tools/web/viewer.js" type="text/javascript"></script>
			

		
	  	
	</head>
	<body>
		<tiles:insert attribute="header"/>
		<div class="body-layout">
			<div class="body-container"> 
				<tiles:insert attribute="body"/> 
			</div>
		</div>	
		 
	 	<c:if test="${sessionScope.permiso == 1 or sessionScope.permiso == 2}">
				<tiles:insert attribute="side-bar"/> 
		</c:if>
		
		<!--   <tiles:insert attribute="footer"/>  -->
		
		<!-- TODO: REMOVE THIS IS FOR DEV -->
		<script src="//localhost:35729/livereload.js" type="text/javascript"></script>
	</body>
</html>