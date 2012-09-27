package com.threeti.pushservice.domain.socketclient;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.threeti.ServerConfig;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 03/09/12
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */
public class SocketClient {
    private static final String SERVER_HOST = ServerConfig.SERVER_HOST;
    private static final int PORT = ServerConfig.PORT;
    private static final int TIME_OUT_MILLIS = ServerConfig.TIME_OUT_MILLIS;
    private static NioSocketConnector connector = new NioSocketConnector();
    
    private static SocketClient instance;
    
    private ConnectFuture cf;
    
    private SocketClient() {
	}
    
    public static SocketClient getInstance() {
    	if(instance==null || !connector.isActive()){
    		instance=new SocketClient();
    	}
		return instance;
	}
    
    
    private ConnectFuture createConnection() {
    	if(cf!=null){
    		return cf;
    	}
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue())));
        connector.setConnectTimeoutMillis(TIME_OUT_MILLIS);
        connector.setHandler(new SocketClientHandler());
        cf = connector.connect(new InetSocketAddress(SERVER_HOST, PORT));
        cf.awaitUninterruptibly();
        return cf;
    }

    public void sendMessage(final String message) {
        ConnectFuture cf = createConnection();
        IoSession session = cf.getSession();
        
        
        System.out.println("messageSend:===>  "+message);
        session.write(message);
//        session.close(true);
        //closeConnection();
    }

    public void closeConnection() {
        if (connector.isActive()) connector.dispose();
    }
}
