/**
 * @author Yuqi Chou
 * @version  Sep 26, 2012
 */
package com.threeti.ics.server.service.handles;

import java.io.IOException;
import java.util.Map;

import org.apache.mina.core.session.IoSession;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.threeti.ics.beans.ProtocolTypeEnum;
import com.threeti.ics.web.domain.ClientInfo;
import com.threeti.ics.web.domain.MessagePushTypeEnum;
import com.threeti.util.JsonMapper;

/**
 * @author Yuqi Chou 
 * @version Sep 26, 2012  8:55:27 PM
 */
public class SdkVerificationHandler extends BaseMessageHandler{

	@Override
	public String getType() {
		return ProtocolTypeEnum.SDKVERIFICATION.getName();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object handleMessage(Object object, IoSession ioSession) {
		
		if(object instanceof Map){
			Map m=(Map) object;
			String serviceToken=(String) m.get("serviceToken");
			String uid=(String) m.get("uid");
			if(getClientInfoManager().containsClient(serviceToken)){
				ClientInfo client = getClientInfoManager().getClientByUID(uid);
				client.setServiceToken(serviceToken);
			}else{
				getClientInfoManager().addClient(serviceToken,uid);
			}
			
			try {
				m.put("messageType", MessagePushTypeEnum.SDKVERIFICATION.getName());
				getMessageQueuePool().putMessage(serviceToken, JsonMapper.toJson(m, false));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return null;
	}

}
