package com.threeti.ics.web.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageQueuePool {
	
	private static MessageQueuePool instance;
	
	private ClientInfoManager clientInfoManager;
	
	private Map<String,ClientMessage> clientMessageMap;
	
	private MessageQueuePool() {
		clientInfoManager=ClientInfoManager.getInstance();
		clientMessageMap=Collections.synchronizedMap(new ConcurrentHashMap<String,ClientMessage>());
	}
	
	public static MessageQueuePool getInstance() {
		if(instance==null){
			instance=new MessageQueuePool();
		}
		return instance;
	}
	
	
	public void putMessage(String serviceToken,String message){
		if(!clientInfoManager.containsClient(serviceToken)){
			return;
		}
		
		ClientMessage cm=null;
		if(!clientMessageMap.containsKey(serviceToken)){
			cm=new ClientMessage();
		}else{
			cm=clientMessageMap.get(serviceToken);
		}
		
		cm.putMessage(message);
		clientMessageMap.put(serviceToken, cm);
	}
	
	
	public String popMessage(String serviceToken){
		if(!clientInfoManager.containsClient(serviceToken)){
			return null;
		}
		ClientMessage cm=clientMessageMap.get(serviceToken);
		if(cm!=null){
			return cm.popMessage();
		}else{
			return null;
		}
	}
	
	
	private class ClientMessage{
		LinkedList<String> messageList=new LinkedList<String>();
		public String popMessage(){
			return messageList.poll();
		}
		public void putMessage(String message){
			messageList.add(message);
		}
	}
	
	
	
}
