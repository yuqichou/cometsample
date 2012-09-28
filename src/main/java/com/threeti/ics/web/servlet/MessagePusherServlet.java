package com.threeti.ics.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometProcessor;

import com.threeti.ics.web.domain.ClientInfoManager;
import com.threeti.ics.web.domain.MessageQueuePool;

public class MessagePusherServlet extends HttpServlet implements CometProcessor{
	
	private MessageQueuePool messageQueuePool;
	private ClientInfoManager clientInfoManager;
	private MessageSenderThread messageSenderThread;
	private Map<String,HttpServletResponse> onlineMap;
	
	public void init(ServletConfig config) throws ServletException {
		clientInfoManager=ClientInfoManager.getInstance();
		messageQueuePool=MessageQueuePool.getInstance();
		onlineMap=Collections.synchronizedMap(new ConcurrentHashMap<String,HttpServletResponse>());
		
		messageSenderThread=new MessageSenderThread();
		Thread thread=new Thread(messageSenderThread);
		thread.setDaemon(true);
		thread.start();
	}
	
	private class MessageSenderThread extends Thread{
		public void run() {
			while(true){
				for (String serviceToken : clientInfoManager.getAllClients()) {
					String message=messageQueuePool.popMessage(serviceToken);
					if(message==null){
						continue;
					}
					synchronized (onlineMap) {
						System.out.println("current online user: "+onlineMap.size());
						HttpServletResponse response = onlineMap.get(serviceToken);
						if(response!=null){
							try {
								response.setCharacterEncoding("UTF-8");
								PrintWriter writer = response.getWriter();
								writer.write(message);
								writer.flush();
								writer.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}else{
							//不在线 放回消息池
							messageQueuePool.putMessage(serviceToken, message);
						}
					}
					
				}
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void event(CometEvent event) throws IOException, ServletException {
		HttpServletRequest request = event.getHttpServletRequest();
        HttpServletResponse response = event.getHttpServletResponse();
        String uid = request.getParameter("uid");
        String serviceToken = null;
        event.setTimeout(1000*15);
        
        if(uid==null){
        	response.getWriter().close();
        	return;
        }else{
        	if(clientInfoManager.getKeyMapper().containsKey(uid)){
        		serviceToken=clientInfoManager.getKeyMapper().get(uid);
        		if(serviceToken==null){
        			response.getWriter().close();
                	return;
        		}
        	}else{
        		response.getWriter().close();
            	return;
        	}
        }
        
        
        if (event.getEventType() == CometEvent.EventType.BEGIN) {
            synchronized (onlineMap) {
            	onlineMap.put(serviceToken, response);
			}
        } else if (event.getEventType() == CometEvent.EventType.ERROR) {
        	System.out.println("Error for session: " + serviceToken+" =="+event.getEventSubType().name());
            synchronized (onlineMap) {
            	onlineMap.remove(serviceToken);
			}
            event.close();
        } else if (event.getEventType() == CometEvent.EventType.END) {
            synchronized (onlineMap) {
            	onlineMap.remove(serviceToken);
			}
            event.close();
        } 
	}
}
