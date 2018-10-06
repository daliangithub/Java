package com.briup.mytomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyToncat {
	private int port = 8899;
	private Map<String, String> urlServletMap = new HashMap<String,String>();
	public MyToncat(int port) {
		this.port = port;
	}
	
	public void start(){
		initServletMapping();
		ServerSocket serverSocket = null;		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("mytomcat is start......");
			//这儿有一个循环bug问题尚未解决
//			while(true){
				Socket socket  =serverSocket.accept();
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				MyRequest myRequest = new MyRequest(inputStream);
				MyResponse myResponse = new MyResponse(outputStream);
				dispatch(myRequest, myResponse);
				socket.close();
//			}			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(serverSocket !=null){
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	//遍历集合,得到浏览器中传过来的url地址，放入一个集合中
	private void initServletMapping(){
		for(ServletMapping servletMapping : ServletMappingConfig.servletMappingList){
			urlServletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
		}		
	}
	//
	public void dispatch(MyRequest myRequest,MyResponse myResponse){
		String  clazz = urlServletMap.get(myRequest.getUrl());
		try {
			System.out.println("clazz-----"+clazz);
			Class<MyServlet> myServletClass = (Class<MyServlet>)Class.forName(clazz);
			MyServlet myServlet = myServletClass.newInstance();
			myServlet.service(myRequest, myResponse);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MyToncat(8899).start();
	}
	
}











