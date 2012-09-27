package com.threeti.ics.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.mina.core.session.IoSession;

import com.threeti.util.JsonMapper;

public class MessageHandlerService {
	
	private static MessageHandlerService instance;
	
	private static List<MessageHandler> handlers;
	
	private MessageHandlerService() {
		handlers=new ArrayList<MessageHandler>();
	}
	
	public static MessageHandlerService getInstance() {
		if(instance==null){
			instance=new MessageHandlerService();
		}
		return instance;
	}
	
	public void addHandler(MessageHandler handler){
		handlers.add(handler);
	}
	
	
	@SuppressWarnings("unchecked")
	public void handleMessage(Object object,IoSession ioSession){
		if(object==null){
			return;
		}
		
		HashMap<String,Object> responseMap=null;
		try {
			responseMap=(HashMap<String, Object>) JsonMapper.fromJson(object.toString(), HashMap.class);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		String type=responseMap.get("type").toString();
		Object messageData=responseMap.get("data");
		
		for (MessageHandler handler : handlers) {
			if(handler.getType().equals(type)){
				try {
					handler.handleMessage(messageData,ioSession);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}
