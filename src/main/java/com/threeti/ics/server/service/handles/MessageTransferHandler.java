/**
 * @author Yuqi Chou
 * @version  Sep 27, 2012
 */
package com.threeti.ics.server.service.handles;

import java.io.IOException;
import java.util.Map;

import org.apache.mina.core.session.IoSession;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.threeti.ics.beans.ProtocolTypeEnum;
import com.threeti.ics.web.domain.MessagePushTypeEnum;
import com.threeti.util.JsonMapper;

/**
 * @author Yuqi Chou 
 * @version Sep 27, 2012  3:17:36 PM
 */
public class MessageTransferHandler extends BaseMessageHandler {

	/**
	 * @see com.threeti.ics.server.service.MessageHandler#getType()
	 * @author  Yuqi Chou
	 * @version Sep 27, 2012 3:17:36 PM
	 */
	@Override
	public String getType() {
		return ProtocolTypeEnum.MESSAGETRANSFER.getName();
	}

	/**
	 * @see com.threeti.ics.server.service.MessageHandler#handleMessage(java.lang.Object, org.apache.mina.core.session.IoSession)
	 * @author  Yuqi Chou
	 * @version Sep 27, 2012 3:17:36 PM
	 */
	@Override
	public Object handleMessage(Object object, IoSession ioSession) {
		if(object instanceof Map){
			Map map=(Map) object;
			Map message=(Map) map.get("message");
			String serviceToken=message.get("to").toString();
			map.put("messageType", MessagePushTypeEnum.MESSAGETRANSFER.getName());
			try {
				getMessageQueuePool().putMessage(serviceToken, JsonMapper.toJson(map, false));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
