package org.nerv.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;

public class MessagePool {
	
	static MessagePool ins;
	
	private Map<String,ClientInfo> clientMap;
	
	private MessagePool(){
		clientMap=Collections.synchronizedMap(new ConcurrentHashMap<String,ClientInfo>());
	}
	
	public static MessagePool getInstance(){
		if(ins==null){
			ins=new MessagePool();
		}
		return ins;
	}
	
	public void register(String id,HttpServletResponse response){
		ClientInfo value=new ClientInfo();
		value.setHttpServletResponse(response);
		value.setId(id);
		clientMap.put(id, value);
	}
	
	public void logOff(String id){
		synchronized (this) {
			clientMap.remove(id);
		}
	}
	
	
	
	public void addMessage(String id,String message){
		if(clientMap.containsKey(id)){
			clientMap.get(id).addMessage(message);
		}
	}
	
	public LinkedList<String> getMessage(String id){
		return clientMap.get(id).getMessage();
	}
	
	public boolean hasMessage(String id){
		if(!clientMap.containsKey(id)){
			return false;
		}
		return clientMap.get(id).getMessage().size()>0;
	}
	
	public boolean hasClient(){
		return clientMap.size()>0;
	}
	
	public boolean hasClient(String id){
		return clientMap.containsKey(id);
	}

	public void checkAndSend() {
		
		synchronized (this) {
			Set<String> ids = clientMap.keySet();
			for (String id : ids) {
				if(clientMap.get(id).hasMessage()){
					LinkedList<String> message = clientMap.get(id).getMessage();
					HttpServletResponse res = clientMap.get(id).getHttpServletResponse();
					try {
						PrintWriter writer = res.getWriter();
						for (String m : message) {
							writer.println(m);
						}
						writer.flush();
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

	public void broadcast(String message) {
		Set<String> ids = clientMap.keySet();
		for (String id : ids) {
			clientMap.get(id).addMessage(message);
		}
	}
	
	
	public Object[] getClientIdList(){
		return clientMap.keySet().toArray();
	}
	
}
