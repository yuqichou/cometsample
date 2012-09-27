package com.threeti.ics.server.service.handles;

import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.threeti.ics.beans.ProtocolTypeEnum;
import com.threeti.ics.web.domain.ClientInfo;
import com.threeti.ics.web.domain.MessagePushTypeEnum;
import com.threeti.util.JsonMapper;

public class BuildConversationHandler extends BaseMessageHandler{

	@Override
	public String getType() {
		return ProtocolTypeEnum.BUILDCONVERSATION.getName();
	}

	@Override
	public Object handleMessage(Object object, IoSession ioSession) {
		if(object instanceof Map){
			Map m=(Map) object;
			String serviceToken=(String) m.get("serviceToken");
			if(serviceToken==null){
				return null;
			}
			if(getClientInfoManager().containsClient(serviceToken)){
				ClientInfo client = getClientInfoManager().getClientByServiceToken(serviceToken);
				client.setServiceToken(serviceToken);
			}else{
				getClientInfoManager().addClient(serviceToken,null);
			}
			
			try {
				m.put("messageType", MessagePushTypeEnum.BUILDCONVERSATION.getName());
				getMessageQueuePool().putMessage(serviceToken, JsonMapper.toJson(m, false));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		return null;
	}

}
