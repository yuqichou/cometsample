/**
 * @author Yuqi Chou
 * @version  Sep 26, 2012
 */
package com.threeti.ics.server.service;

import org.apache.mina.core.session.IoSession;

/**
 * @author Yuqi Chou 
 * @version Sep 26, 2012  2:42:23 PM
 */
public interface MessageHandler {
	
	public String getHanderName();
	
	public String getType();
	
	public Object handleMessage(Object object,IoSession ioSession);
	
	
}
