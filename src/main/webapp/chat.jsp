<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat</title>
        
        <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.js"></script>
		<script type="text/javascript" src="js/jquery.cookie.js"></script>
		
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
					<div class="span3">
							<fieldset>
								
								<div class="control-group">
									<label class="control-label" for="input01">
											<span class="label label-important">prId</span>
									</label>
									<div class="controls">
										<input id="prId" value="2222" type="text" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="input01">
											<span class="label label-important">appKey</span>
									</label>
									<div class="controls">
										<input id="appKey" value="8890fgdkj90fdg89f88" type="text" />
									</div>
								</div>
								
								
								<div class="control-group">
									<label class="control-label" for="input01">
											<span class="label label-important">description</span>
									</label>
									<div class="controls">
										<input id="description" value="翔傲信息科技（上海）有限公司" type="text" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="input01">
											<span class="label label-important">picture</span>
									</label>
									<div class="controls">
										<input id="picture" value="http://61.155.169.168:8088/imageUpload/sutravel/20120806175113144-0.jpg" type="text" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="input01">
											<span class="label label-important">productName</span>
									</label>
									<div class="controls">
										<input id="productName" value="测试Chou" type="text" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="input01">
											<span class="label label-important">uid</span>
									</label>
									<div class="controls">
										<input id="uid" type="text" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="input01">
											<span class="label label-important">conversationId</span>
									</label>
									<div class="controls">
										<input id="conversationId" type="text" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="input01">
											<span class="label label-important">service token</span>
									</label>
									<div class="controls">
										<input id="serviceToken" type="text" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="input01">
											<span class="label label-important">to</span>
									</label>
									<div class="controls">
										<input id="to" type="text" />
									</div>
								</div>
								
								
								<div class="form-actions">
									<a id="startBtn"  onclick="start()" class="btn btn-primary">START</a>
									<a id="connBtn" style="display: none;" class="btn btn-primary">连接中</a>
					          	</div>
							</fieldset>
					</div>
					<div class="span9">
					<fieldset>
								<div class="control-group">
									<div class="controls">
										<textarea readonly="readonly" style="width:100%" id="content" rows="16" cols="70"></textarea>
									</div>
									<div class="controls">
										<textarea id="sendContent" style="width:40%" rows="5" cols=""></textarea>
									</div>
									<div class="controls">
										<a id="sendBtn" style="display: none;" onclick="sendMessage()" class="btn btn-primary">发送</a>
									</div>
								</div>
								</fieldset>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
    </body>
    
    <script type="text/javascript">
   		 $("#uid").val($.cookie('uid'));
    </script>
    <script type="text/javascript">
	    function debug(obj){
			var s="";
			for(var i in obj){
				s+=i+":"+obj[i]+"\n";
			}
			return s;
		}
    
    
    	function start(){
    		$("#startBtn").hide();
    		$("#connBtn").show();
    		
    		$.ajax({
          	   type: "POST",
          	   url: "sendCommand",
          	   data: "command=login&appKey="+$('#appKey').val(),
          	   success: function(msg){
					$("#uid").val($.cookie('uid'));
					pull();
          	   }
          	});
    	}
    	
    	function buildConversation(){
    		$.ajax({
           	   type: "POST",
           	   url: "sendCommand",
           	   data: "command=buildconversion&uid="+$('#uid').val()+"&serviceToken="+$("#serviceToken").val()
           	   		  +"&prId="+$("#prId").val()+"&description="+$('#description').val()+"&picture="+$('#picture').val()
           	   		  +"&productName="+$('#productName').val(),
           	   success: function(msg){
					//None           		   
           	   }
           	});
    	}
    	
    	function sendMessage(){
			//conversationId,,to,messageBody
			appendMessage({
				sendName:"我",
				textBody:$("#sendContent").val(),
				dateString:""
			});
			
			$.ajax({
	           	   type: "POST",
	           	   url: "sendCommand",
	           	   data: "command=transferMessage&uid="+$('#uid').val()+"&serviceToken="+$("#serviceToken").val()
	           	   		  +"&conversationId="+$("#conversationId").val()+"&to="+$('#to').val()+"&messageBody="+$('#sendContent').val(),
	           	   success: function(msg){
	           			$('#sendContent').val("");  
	           	   }
	        });
    	}
    	
    	function appendMessage(obj){
    		var sendName=obj.sendName;
			var textBody=obj.textBody;
			var dateString=obj.dateString;
			var mes="";
			mes+=sendName+"  说:\n";
			mes+=textBody;
			
    		$("#content").val($("#content").val()+mes);
    		$("#content").val($("#content").val()+"\n\n");
    	}
    	
    	
    	function pull(){
    		$.ajax({
           	   type: "POST",
           	   url: "pullMessage",
           	   dataType:"json",
           	   data: "uid="+$('#uid').val(),
           	   success: function(msg){
           		   if(msg!=null){
           			   handleJsonData(msg);
           		   }
 				   pull();
           	   }
           	});
    	}
    	
    	function handleJsonData(jsonData){
    		if(jsonData.messageType=="sdkverification"){
    			handleSdkVerification(jsonData);
    		}
    		if(jsonData.messageType=="buildconversation"){
    			handleBuildconversion(jsonData);
    		}
    		if(jsonData.messageType=="messagetransfer"){
    			handleMessageTransfer(jsonData);
    		}
    	}
    	
    	function handleMessageTransfer(jsonData){
    		var message=jsonData.message;
    		
    		var id=message.id;
    		var conversationId=message.conversationId;
    		var from=message.from;
    		var to=message.to;
    		var messageBody=message.messageBody;
    		var status=message.status;
    		var type=message.type;
    		var dateString=message.dateString;
    		
    		$("#to").val(from);
    		
    		appendMessage({
				sendName:from,
				textBody:messageBody,
				dateString:dateString
			});
    	}
    	
    	function handleSdkVerification(jsonData){
    		var serviceToken=jsonData.serviceToken;
    		if(serviceToken!=null){
    			$("#serviceToken").val(serviceToken);
    			buildConversation();
    		}
    	}
    	
    	function handleBuildconversion(jsonData){
    		var conversationId=jsonData.conversationId;
    		if(conversationId!=null){
    			$("#conversationId").val(conversationId);
    			$("#connBtn").hide();
    			$("#sendBtn").show();
    			alert("可以聊天了!");
    		}
    	}
    	
    </script>
    
    
    
</html>