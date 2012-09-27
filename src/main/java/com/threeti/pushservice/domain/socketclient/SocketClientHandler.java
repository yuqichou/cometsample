package com.threeti.pushservice.domain.socketclient;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.threeti.ics.server.service.MessageHandlerService;
import com.threeti.ics.server.service.handles.BuildConversationHandler;
import com.threeti.ics.server.service.handles.MessageTransferHandler;
import com.threeti.ics.server.service.handles.SdkVerificationHandler;

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
    
    MessageHandlerService messageHandlerService=MessageHandlerService.getInstance();
    
    public SocketClientHandler() {
    	messageHandlerService.addHandler(new SdkVerificationHandler());
    	messageHandlerService.addHandler(new BuildConversationHandler());
    	messageHandlerService.addHandler(new MessageTransferHandler());
	}
    
    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {
    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {
    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
//        if (idleStatus == IdleStatus.BOTH_IDLE) ioSession.write(HEARTBEAT);
    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
    	throwable.printStackTrace();
        ioSession.close(true);
    }

    @Override
    public void messageReceived(IoSession ioSession, Object object) throws Exception {
    	System.out.println("messageReceived:===>  "+object);
    	try {
    		messageHandlerService.handleMessage(object,ioSession);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
    }

    public SocketClientHandler setSendHeartbeatEnabled(boolean sendHeartbeatEnabled) {
        this.sendHeartbeatEnabled = sendHeartbeatEnabled;
        return this;
    }
}
