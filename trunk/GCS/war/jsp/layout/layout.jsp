<!DOCTYPE html>


<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Portal GCS</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	
      <script src="../tools/jquery-2.1.1.min.js" type="text/javascript"></script>
      <script src="../tools/jquery.validate.js" type="text/javascript"></script>      
	  <script src="../components/bootstrap/js/bootstrap.js" type="text/javascript"></script>	
	  <link rel="stylesheet" type="text/css" href="../components/bootstrap/css/bootstrap.min.css" />
	   <link rel="stylesheet" type="text/css" href="../css/main.css" /> 
	  
	  
	  
		
      <script src="../js/common.js" type="text/javascript"></script>
		
		<link rel="stylesheet" type="text/css" href="../css/layout.css">
		<link rel="stylesheet" type="text/css" href="../css/body.css">
		
	</head>
	<body>
	
		<tiles:insert attribute="header"/>
		<div class="body-layout">
			<div class="body-container"> 
				<tiles:insert attribute="body"/> 
			</div>
		</div>	
		 
	 	<c:if test="${sessionScope.permiso == '1' || sessionScope.permiso == '2' || sessionScope.permiso == '3'}">
				<tiles:insert attribute="side-bar"/> 
		</c:if>
		
		<!--   <tiles:insert attribute="footer"/>  -->
	</body>
</html>