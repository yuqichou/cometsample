/**
 * @author Yuqi Chou
 * @version  Sep 26, 2012
 */
package com.threeti.ics.getway;

import java.io.IOException;

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
	
	private SocketClient client;
	
	private ClientInfoManager clientInfoManager;
	
	public MessageGateWayService() {
		client=SocketClient.getInstance();
		clientInfoManager=ClientInfoManager.getInstance();
	}
	
	public static MessageGateWayService getInstance() {
		if(instance==null){
			instance=new MessageGateWayService();
		}
		return instance;
	}
	
	
	
	public void loginAndRegister(String uid,String appKey) throws Exception{
		String message = MessagePackagingService.packageServiceTokenMessage(uid, appKey);
		client.sendMessage(message);
	}

	public void buildConversion(String uid,String prId, String description,String picture, String productName, String serviceToken) throws JsonMappingException, JsonGenerationException, IOException {
		if(!clientInfoManager.containsClient(serviceToken)){
			clientInfoManager.addClient(serviceToken, uid);
		}
		
		String message = MessagePackagingService.packageBuildConversation(prId, description, picture, productName, serviceToken);
		client.sendMessage(message);
	}

	public void messageTransfer(String conversationId, String serviceToken,String to, String messageBody) throws JsonMappingException, JsonGenerationException, IOException {
		String message = MessagePackagingService.packageMessageTransfer(conversationId, serviceToken, to, messageBody);
		client.sendMessage(message);
	}
	
	
	
	
}
