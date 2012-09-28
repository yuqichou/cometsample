package com.threeti.test;

import java.util.Map;

import org.apache.mina.core.session.IoSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.threeti.ics.beans.ProtocolTypeEnum;
import com.threeti.ics.server.service.MessageHandler;
import com.threeti.ics.server.service.MessageHandlerService;
import com.threeti.ics.server.service.MessagePackagingService;
import com.threeti.pushservice.domain.socketclient.SocketClient;



public class TestMain {
	
	
	static String prId = "1111";
	static String description = "翔傲信息科技（上海）有限公司";
	static String picture = "http://61.155.169.168:8088/imageUpload/sutravel/20120806175113144-0.jpg";
	static String productName = "翔傲客服1111";
	
	static String appKey="8890fgdkj90fdg89f88";
	
	static String uid="ebcc3a180bd9451f9ce5a98e18b8c9e4";
	
	static String serviceToken="0c049d91dddcea8d51299e2fd9e5a20f";
	
	SocketClient client;
	
	MessageHandlerService messageHandlerService=MessageHandlerService.getInstance();
	
	protected String conversationId;
	
	@Before
	public void init(){
		client=new SocketClient();
	}
	
	@Test
	public void test_SdkVerification() throws Exception {
		String jsonMessage = MessagePackagingService.packageServiceTokenMessage(uid, appKey);
		
		messageHandlerService.addHandler(new MessageHandler() {
			public String getType() {
				return ProtocolTypeEnum.SDKVERIFICATION.getName();
			}
			public Object handleMessage(Object object,IoSession ioSession) {
				System.out.println("test_SdkVerification====+++++++++++++++"+object);
				return null;
			}
			@Override
			public String getHanderName() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		client.sendMessage(jsonMessage);
		
		Thread.sleep(10000);
	}
	
	@Test
	public void test_buildconversation() throws Exception{
		
		System.out.println("test_buildconversation<><><><><><><>");
		
		messageHandlerService.addHandler(new MessageHandler() {
			public String getType() {
				return ProtocolTypeEnum.BUILDCONVERSATION.getName();
			}
			public Object handleMessage(Object object,IoSession ioSession) {
				Map m=(Map)object;
				conversationId=m.get("conversationId").toString();
				System.out.println("test_buildconversation============="+object);
				
				try {
					ioSession.write(test_messagetransfer());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				return null;
			}
			@Override
			public String getHanderName() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		
		messageHandlerService.addHandler(new MessageHandler() {
			public String getType() {
				return ProtocolTypeEnum.MESSAGETRANSFER.getName();
			}
			public Object handleMessage(Object object,IoSession ioSession) {
				System.out.println("test_messagetransfer============="+object);
				return null;
			}
			@Override
			public String getHanderName() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		
		
		
		
		
		
		
		
		String jsonMessage = MessagePackagingService.packageBuildConversation(prId, description,picture,productName,serviceToken);
		client.sendMessage(jsonMessage);
		
		Thread.currentThread().join();
	}
	
	
	
	public String test_messagetransfer() throws Exception{
		
		System.out.println("test_messagetransfer>>>>>>>>>>>>>>>>>>>>>>");
		
		String from=serviceToken;
		String messageBody="测试的数据from PC 123";
		String to=null;
		String conversationId="1";
		
		
		System.out.println("test_messagetransfer+++++AABBCC+++++++++++"+conversationId);
		
		return  MessagePackagingService.packageMessageTransfer(conversationId,from,to,messageBody);
//		client.sendMessage(jsonMessage);
	}
	
	
	@Test
	public void test_messagelist() throws Exception{
		
		messageHandlerService.addHandler(new MessageHandler() {
			public String getType() {
				return ProtocolTypeEnum.MESSAGELIST.getName();
			}
			public Object handleMessage(Object object,IoSession ioSession) {
				System.out.println("test_messagelist============="+object);
				return null;
			}
			@Override
			public String getHanderName() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		
		int pageFrom=1;
		int pageSize=20;
		String conversationId="1";
		String jsonMessage = MessagePackagingService.packageMessageList(conversationId,pageFrom,pageSize);
		client.sendMessage(jsonMessage);
		
		
		
	}
	
	
	
	
	
	
	@After
	public void after() throws InterruptedException{
		System.out.println("after");
	}
	
	
	
	
	
}
