package org.nerv.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometProcessor;

	
	
public class ChatServlet extends HttpServlet implements CometProcessor {
	
	/**
	 * <code>serialVersionUID</code> 
	 */
	private static final long serialVersionUID = 1L;

	MessagePool messagePool=MessagePool.getInstance();
	
	protected MessageSender messageSender = null;
    
    public void init() throws ServletException {
        messageSender = new MessageSender();
        Thread messageSenderThread = new Thread(messageSender, "MessageSender[" + getServletContext().getContextPath() + "]");
        messageSenderThread.setDaemon(true);
        messageSenderThread.start();
    }

    public void destroy() {
        messageSender.stop();
        messageSender = null;
    }
	
	public void event(CometEvent event) throws IOException, ServletException {
		HttpServletRequest request = event.getHttpServletRequest();
        HttpServletResponse response = event.getHttpServletResponse();
        String id = request.getSession(true).getId();
        event.setTimeout(5*60*1000);
        
		if (event.getEventType() == CometEvent.EventType.BEGIN) {
            log("Begin for session: " + id);
            
            messagePool.register(id, response);
            
        } else if (event.getEventType() == CometEvent.EventType.ERROR) {
            log("Error for session: " + id+" =="+event.getEventSubType().name());
            messagePool.logOff(id);
            event.close();
        } else if (event.getEventType() == CometEvent.EventType.END) {
            log("End for session: " + id);
            messagePool.logOff(id);
            event.close();
        } 
	}
	
	
	public class MessageSender implements Runnable {

        protected boolean running = true;
        
        public MessageSender() {
        }
        
        public void stop() {
            running = false;
        }


        public void run() {
            while (running) {
                synchronized (messagePool) {
                	if(!messagePool.hasClient()){
                		continue;
                	}else{
                		messagePool.checkAndSend();
                	}
                }
            }
        }
    }
	
}