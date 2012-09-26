package org.nerv.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandServlet extends HttpServlet{
	
	MessagePool messagePool=MessagePool.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse arg1)throws ServletException, IOException {
		String method=request.getParameter("method");
		if("fetch_list".equals(method)){
			fetchList(request, arg1);
		}else{
			sendMessage(request, arg1);
		}
		
	}
	
	private void fetchList(HttpServletRequest request, HttpServletResponse arg1){
		PrintWriter writer;
		try {
			writer = arg1.getWriter();
			for (Object iterable_element : messagePool.getClientIdList()) {
				writer.print(iterable_element.toString());
				writer.print(";");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessage(HttpServletRequest request, HttpServletResponse arg1){
		String id=request.getParameter("id");
		String message=request.getParameter("message");
		synchronized (messagePool) {
			if(id==null || id.trim().length()<1){
				messagePool.broadcast(message);
			}else if(messagePool.hasClient(id)){
				messagePool.addMessage(id, message);
			}
		}
	}
	
	
	
	
}
