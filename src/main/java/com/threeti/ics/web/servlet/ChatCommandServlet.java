package com.threeti.ics.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.threeti.ics.getway.MessageGateWayService;
import com.threeti.ics.server.service.MessagePackagingService;

public class ChatCommandServlet extends HttpServlet {
	
	MessageGateWayService messageGateWayService=MessageGateWayService.getInstance();
	
	String COOKIE_NAME="ICS_SERVICE_TOKEN";
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String command=request.getParameter("command");
		String appKey=request.getParameter("appKey");
		String serviceToken=request.getParameter("serviceToken");
		String conversationId=request.getParameter("conversationId");
		String to=request.getParameter("to");
		String messageBody=request.getParameter("messageBody");
		String uid=request.getParameter("uid");
		if(uid==null){
			uid=request.getSession(true).getId();
			Cookie cookie=new Cookie("uid",uid);
			cookie.setMaxAge(60*60*30);
			response.addCookie(cookie);
		}
		
		
		if("login".equals(command)){
			serviceToken=checkCookie(request, response);
			if(serviceToken==null || serviceToken.trim().length()<1){
				try {
					messageGateWayService.loginAndRegister(uid, appKey);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
		else if("buildconversion".equals(command)){
			if(serviceToken!=null && serviceToken.trim().length()>0){
				try {
					String prId=request.getParameter("prId");
					String description = request.getParameter("description");
					String picture = request.getParameter("picture");
					String productName =request.getParameter("productName"); 
					messageGateWayService.buildConversion(uid,prId, description,picture,productName,serviceToken);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		else if("transferMessage".equals(command)){
			if(serviceToken!=null && serviceToken.trim().length()>0){
				try {
					messageGateWayService.messageTransfer(conversationId,serviceToken,to,messageBody);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	
	}
	
	public String checkCookie(HttpServletRequest request, HttpServletResponse response){
		String serviceToken="";
		for (Cookie cookie : request.getCookies()) {
			if(COOKIE_NAME.equals(cookie.getName())){
				serviceToken=cookie.getValue();
			}
		}
		return serviceToken;
	}
	
	
	
	
	
}
