package com.threeti.ics.web.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


public class ClientInfoManager {
	
	private static ClientInfoManager instance;
	
	private static Map<String,ClientInfo> clientMap=null;
	
	private BiMap<String,String> keyMapper=HashBiMap.create();//uid=serviceToken
	
	
	private ClientInfoManager() {
		clientMap=Collections.synchronizedMap(new ConcurrentHashMap<String,ClientInfo>());
	}
	
	public static ClientInfoManager getInstance() {
		if(instance==null){
			instance=new ClientInfoManager();
		}
		return instance;
	}
	
	public BiMap<String, String> getKeyMapper() {
		return keyMapper;
	}
	
	public void addClient(String serviceToken,String uid){
		synchronized (clientMap) {
			ClientInfo client=new ClientInfo();
			client.setServiceToken(serviceToken);
			client.setUid(uid);
			clientMap.put(uid, client);
			keyMapper.put(uid, serviceToken);
		}
		
	}
	
	
	
	public void removeClientByUID(String uid){
		synchronized (clientMap) {
			clientMap.remove(uid);
			keyMapper.remove(uid);
		}
	}
	
	public void removeClientByServiceToken(String serviceToken){
		synchronized (clientMap) {
			String uid=keyMapper.inverse().get(serviceToken);
			clientMap.remove(uid);
			keyMapper.remove(uid);
		}
	}
	
	public ClientInfo getClientByUID(String key){
		synchronized (clientMap) {
			return clientMap.get(key);
		}
	}
	
	public ClientInfo getClientByServiceToken(String serviceToken){
		synchronized (clientMap) {
			String uid=keyMapper.inverse().get(serviceToken);
			return clientMap.get(uid);
		}
	}
	
	
	public boolean containsClient(String serviceToken){
		synchronized (clientMap) {
			String uid=keyMapper.inverse().get(serviceToken);
			if(uid==null){
				return false;
			}
			return clientMap.containsKey(uid);
		}
	}
	
	public List<String> getAllClients(){
		List<String> clientId=new ArrayList<String>();
		synchronized (clientMap) {
			Set<String> uids = clientMap.keySet();
			Iterator<String> it = uids.iterator();
			while(it.hasNext()){
				clientId.add(keyMapper.get(it.next()));
			}
		}
		return clientId;
	}
	
}
