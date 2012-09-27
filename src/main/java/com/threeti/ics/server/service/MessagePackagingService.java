package com.threeti.ics.server.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.threeti.ics.beans.ProtocolTypeEnum;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.BuildConversationRequest;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.MessageListRequest;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.SdkVerificationRequest;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.domain.protocoldefinition.message.MessageStatus;
import com.threeti.util.JsonMapper;

public class MessagePackagingService {
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static String buildMessageMap(Object data,String type) throws JsonMappingException, JsonGenerationException, IOException{
		Map dataMap=new HashMap();
		dataMap.put("data", data);
		dataMap.put("type", type);
		return JsonMapper.toJson(dataMap, false);
	}
	
	/**
	 * 注册获取service token
	 * @author Yuqi Chou
	 * @param uid
	 * @param appKey
	 * @return
	 * @throws Exception
	 * @version Sep 26, 2012  1:59:53 PM
	 */
	public static String packageServiceTokenMessage(String uid,String appKey) throws Exception{
		SdkVerificationRequest request=new SdkVerificationRequest();
		request.setAppKey(appKey);
		request.setUid(uid);
		request.setCarrier("");
		request.setCpu("");
		request.setModel("");
		request.setNetwork("");
		request.setResolution("");
		return buildMessageMap(request, ProtocolTypeEnum.SDKVERIFICATION.getName());
	}




	public static String packageBuildConversation(String prId,String description, String picture, 
												  String productName,String serviceToken) throws JsonMappingException, JsonGenerationException, IOException {
		
		BuildConversationRequest bc=new BuildConversationRequest();
		
		bc.setDescription(description);
		bc.setPicture(picture);
		bc.setProductId(prId);
		bc.setProductName(productName);
		bc.setServiceToken(serviceToken);
		
		return buildMessageMap(bc, ProtocolTypeEnum.BUILDCONVERSATION.getName());
	}

	public static String packageMessageTransfer(String conversationId,
												String from,
												String to, 
												String messageBody) throws JsonMappingException, JsonGenerationException, IOException {
		Message mes=new Message();
		
		mes.setConversationId(conversationId);
		mes.setFrom(from);
		if(to!=null && to.trim().length()>1){
			mes.setTo(to);
		}
		mes.setMessageBody(messageBody);
		mes.setType("text");
		mes.setStatus(MessageStatus.SENT);
		
		return buildMessageMap(mes, ProtocolTypeEnum.MESSAGETRANSFER.getName());
	}

	public static String packageMessageList(String conversationId,int pageFrom, int pageSize) throws JsonMappingException, JsonGenerationException, IOException {
		
		MessageListRequest request=new MessageListRequest();
		
		request.setConversationId(conversationId);
		request.setPageFrom(pageFrom);
		request.setPageSize(pageSize);
		
		return buildMessageMap(request, ProtocolTypeEnum.MESSAGELIST.getName());
	}
}
