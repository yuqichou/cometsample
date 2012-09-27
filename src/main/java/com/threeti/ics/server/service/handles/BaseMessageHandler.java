package com.threeti.ics.server.service.handles;

import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import com.threeti.ics.server.service.MessageHandler;
import com.threeti.ics.web.domain.ClientInfoManager;
import com.threeti.ics.web.domain.MessageQueuePool;

public abstract class BaseMessageHandler implements MessageHandler {
	
	private Set<IoSession> registerSessions=new HashSet<IoSession>();
	
	private ClientInfoManager clientInfoManager=ClientInfoManager.getInstance();
	
	private MessageQueuePool messageQueuePool=MessageQueuePool.getInstance();
	
	public ClientInfoManager getClientInfoManager() {
		return clientInfoManager;
	}
	
	public MessageQueuePool getMessageQueuePool() {
		return messageQueuePool;
	}
	
	public void registerSession(IoSession ioSession){
		registerSessions.add(ioSession);
	}
	
	
}
