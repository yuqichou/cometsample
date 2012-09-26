<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>admin</title>
        
        <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.js"></script>
		<link href="css/bootstrap.css" rel="stylesheet" />
		<link href="css/bootstrap-responsive.css" rel="stylesheet" />
		
		<style>
			body {
				margin: 30px;
			}
		</style>
    </head>
    
    <body>
    	<div class="container-fluid">
    		
    		<div class="row-fluid">
			 	<div class="row">
					 <div class="row-fluid">
						<div class="span12">
							<select multiple="multiple" id="clients">
	    					</select>
						</div>
					</div>
				</div>
				<div class="row">
					 <div class="row-fluid">
						<div class="span12">
							<textarea id="message" rows="" cols=""></textarea>
						</div>
					</div>
				</div>
				<div class="row">
					 <div class="row-fluid">
						<div class="span12">
							<a class="btn" onclick="javascript:
								$.ajax({
						          	   type: 'POST',
						          	   url: 'addMessage?message='+$('#message').val()+'&id='+$('#clients').val(),
						          	   dataType: 'text',
						          	   success: function(msg){
						          		 	//alert('ok!');
						          	   }
						          	});
								" >提交</a>    	
						</div>
					</div>
				</div>
			</div>
    		
    		
	    	
	    	
			
    </body>
    
    <script type="text/javascript">
    	function pull(){
    		$.ajax({
          	   type: "POST",
          	   url: "addMessage?method=fetch_list",
          	   dataType: "text",
          	   success: function(msg){
          		 	$("#clients").empty();
          		   if(msg!=""){
          			 	var id=msg.split(";");
          			 	$("#clients").append("<option></option>");
          			 	for (i=0;i<id.length;i++){
          			 		if(id[i]!=""){
          			 			$("#clients").append("<option>"+id[i]+"</option>");
          			 		}
          			 	}
          			 	
          		   }
          		   setTimeout(pull,1000*60);
          	   }
          	});
    		
    	}
    	pull();
    </script>
    
</html>