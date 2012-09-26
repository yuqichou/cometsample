package com.threeti.pushservice.domain.socketclient;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.threeti.ics.server.service.MessageHandlerService;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 31/08/12
 * Time: 19:27
 * To change this template use File | Settings | File Templates.
 */
public class SocketClientHandler implements IoHandler{
    private static final int HEARTBEAT = 1;
    private boolean sendHeartbeatEnabled = false;
    
    MessageHandlerService messageHandlerService=new MessageHandlerService();
    
    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
    	System.out.println("sessionCreated");
    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {
    	System.out.println("sessionOpened");
      
    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {
    	System.out.println("sessionClosed");
    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
    	System.out.println("sessionIdle");
//        if (idleStatus == IdleStatus.BOTH_IDLE) ioSession.write(HEARTBEAT);
    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
    	System.out.println("exceptionCaught");
        ioSession.close(true);
    }

    @Override
    public void messageReceived(IoSession ioSession, Object object) throws Exception {
    	System.out.println("messageReceived!!!");
    	System.out.println(object);
    	System.out.println("messageReceived end!!!");
    	
    	
    	
    	messageHandlerService.handleMessage(object);
    	ioSession.close(false);
    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
    	System.out.println("messageSent");
    }

    public SocketClientHandler setSendHeartbeatEnabled(boolean sendHeartbeatEnabled) {
        this.sendHeartbeatEnabled = sendHeartbeatEnabled;
        return this;
    }
}
