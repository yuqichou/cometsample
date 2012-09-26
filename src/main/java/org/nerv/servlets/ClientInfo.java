package org.nerv.servlets;

import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

public class ClientInfo {

	String id;

	HttpServletResponse httpServletResponse;

	LinkedList<String> message=new LinkedList<String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}

	public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}

	public LinkedList<String> getMessage() {
		return message;
	}

	public void setMessage(LinkedList<String> message) {
		this.message = message;
	}

	public void addMessage(String message) {
		this.message.add(message);
	}
	
	public boolean hasMessage(){
		return message.size()>0;
	}
	
}
