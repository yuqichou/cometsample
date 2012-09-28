/**
 * @author Yuqi Chou
 * @version  Sep 26, 2012
 */
package com.threeti.ics.getway;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.threeti.ics.server.service.MessagePackagingService;
import com.threeti.ics.web.domain.ClientInfoManager;
import com.threeti.pushservice.domain.socketclient.SocketClient;

/**
 * @author Yuqi Chou 
 * @version Sep 26, 2012  5:44:30 PM
 */
public class MessageGateWayService {
	
	private static MessageGateWayService instance;
	
	private Map<String,SocketClient> clientMapper=Collections.synchronizedMap(new ConcurrentHashMap<String,SocketClient>());
	
	private ClientInfoManager clientInfoManager;
	
	public MessageGateWayService() {
		clientInfoManager=ClientInfoManager.getInstance();
	}
	
	public static MessageGateWayService getInstance() {
		if(instance==null){
			instance=new MessageGateWayService();
		}
		return instance;
	}
	
	
	
	public void loginAndRegister(String uid,String appKey) throws Exception{
		SocketClient client=null;
		synchronized (clientMapper) {
			if(clientMapper.containsKey(uid)){
				client=clientMapper.get(uid);
			}else{
				client=new SocketClient();
				clientMapper.put(uid, client);
			}
		}
		String message = MessagePackagingService.packageServiceTokenMessage(uid, appKey);
		client.sendMessage(message);
	}

	public void buildConversion(String uid,String prId, String description,String picture, String productName, String serviceToken) throws JsonMappingException, JsonGenerationException, IOException {
		SocketClient client=null;
		synchronized (clientMapper) {
			if(clientMapper.containsKey(uid)){
				client=clientMapper.get(uid);
			}else{
				System.err.println("SocketClient for "+uid+" not found");
				return;
			}
		}
		
		if(!clientInfoManager.containsClient(serviceToken)){
			clientInfoManager.addClient(serviceToken, uid);
		}
		String message = MessagePackagingService.packageBuildConversation(prId, description, picture, productName, serviceToken);
		client.sendMessage(message);
	}

	public void messageTransfer(String conversationId, String serviceToken,String to, String messageBody) throws JsonMappingException, JsonGenerationException, IOException {
		
		String uid=clientInfoManager.getKeyMapper().inverse().get(serviceToken);
		
		SocketClient client=null;
		synchronized (clientMapper) {
			if(clientMapper.containsKey(uid)){
				client=clientMapper.get(uid);
			}else{
				System.err.println("SocketClient for "+uid+" not found");
				return;
			}
		}
		
		String message = MessagePackagingService.packageMessageTransfer(conversationId, serviceToken, to, messageBody);
		client.sendMessage(message);
	}
	
	
	
	
}
