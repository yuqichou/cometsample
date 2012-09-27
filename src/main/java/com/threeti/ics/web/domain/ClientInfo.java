package com.threeti.ics.web.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mina.core.session.IoSession;

public class ClientInfo {

	private String serviceToken;
	
	private String uid;

	private IoSession ioSession;
	
	private String conversionId;


	public String getServiceToken() {
		return serviceToken;
	}

	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

	public IoSession getIoSession() {
		return ioSession;
	}

	public void setIoSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}

	
	public void setConversionId(String conversionId) {
		this.conversionId = conversionId;
	}
	
	public String getConversionId() {
		return conversionId;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getUid() {
		return uid;
	}
	
	
}
