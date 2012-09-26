package com.threeti.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.threeti.ics.beans.ProtocolTypeEnum;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.SdkVerificationRequest;
import com.threeti.ics.server.service.MessagePackagingService;
import com.threeti.pushservice.domain.socketclient.SocketClient;
import com.threeti.util.JsonMapper;



public class TestMain {
	
	
	static String prId = "1";
	static String description = "翔傲信息科技（上海）有限公司";
	static String picture = "http://61.155.169.168:8088/imageUpload/sutravel/20120806175113144-0.jpg";
	static String ProductName = "翔傲客服001";
	
	static String appKey="8890fgdkj90fdg89f88";
	
	static String uid="ebcc3a180bd9451f9ce5a98e18b8c9e4";
	
	static String serviceToken="0c049d91dddcea8d51299e2fd9e5a20f";
	
	SocketClient client;
	
	@Before
	public void init(){
		client=new SocketClient();
	}
	
	@Test
	public void test_SdkVerification() throws Exception {
		String jsonMessage = MessagePackagingService.getServiceTokenMessage(uid, appKey);
		client.sendMessage(jsonMessage);
		Thread.currentThread().join();
	}
	
	
	
	
	
	
	
	
	
	@After
	public void sleep() throws InterruptedException{
		client.closeConnection();
	}
	
	
	
	
	
	
	
	
	
}
