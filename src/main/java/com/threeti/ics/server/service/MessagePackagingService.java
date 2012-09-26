package com.threeti.ics.server.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.threeti.ics.beans.ProtocolTypeEnum;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.SdkVerificationRequest;
import com.threeti.util.JsonMapper;

public class MessagePackagingService {
	
	/**
	 * 注册获取service token
	 * @author Yuqi Chou
	 * @param uid
	 * @param appKey
	 * @return
	 * @throws Exception
	 * @version Sep 26, 2012  1:59:53 PM
	 */
	public static String getServiceTokenMessage(String uid,String appKey) throws Exception{
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
	
	
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static String buildMessageMap(Object data,String type) throws JsonMappingException, JsonGenerationException, IOException{
		Map dataMap=new HashMap();
		dataMap.put("data", data);
		dataMap.put("type", type);
		return JsonMapper.toJson(dataMap, false);
	}
}
