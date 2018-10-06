package com.briup.mytomcat;

import java.io.IOException;
import java.io.InputStream;
/**
 * 		封装请求对象
 * 		浏览器---->服务器端(解析浏览器发过来的http协议)
 * */
public class MyRequest {
	
	private String url;
	private String method;
	
	public MyRequest(InputStream inputStream) throws IOException  {
		String httpRequest = "";
		byte[] httpRequestBytes = new byte[1024];
		int length =0;
		if((length =inputStream.read(httpRequestBytes))>0){
			httpRequest = new String(httpRequestBytes, 0, length);
		}
		String httpHead = httpRequest.split("\n")[0];
		url = httpHead.split("\\s")[1];
		method = httpHead.split("\\s")[0];
		System.out.println("哈哈...我是MyRequest : "+this);
	}
	public String getUrl(){
		return url;
	}
	public String getMethod(){
		return method;
	}
	
}
