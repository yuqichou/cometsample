<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comet</title>
        
        <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.js"></script>
		<link href="css/bootstrap.css" rel="stylesheet" />
		<link href="css/bootstrap-responsive.css" rel="stylesheet" />
        
        <SCRIPT TYPE="text/javascript">
        	function a(){
        		$.ajax({
             	   type: "POST",
             	   url: "ChatServlet",
             	  	dataType: "text",
             	   success: function(msg){
					document.getElementById("forecasts").innerHTML=document.getElementById("forecasts").innerHTML+msg+"<br>";
             	    a();
             	   }
             	});
        	}
        	
        </SCRIPT>
    </head>
    
    <body>
        <h1>sample</h1>
        <input type="button" onclick="a()" value="Go!"></input>
        <div id="forecasts"></div>
    </body>
    
</html>