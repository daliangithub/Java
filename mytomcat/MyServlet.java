package com.briup.mytomcat;
/**
 * 提供doGet()和doPost()和service()方法
 * */
public abstract class MyServlet {
		public abstract void doGet(MyRequest myRequest,MyResponse myResponse);
		public abstract void doPost(MyRequest myRequest,MyResponse myResponse);
		public void service(MyRequest myRequest ,MyResponse myResponse){
			if(myRequest.getMethod().equalsIgnoreCase("POST")){
				doPost(myRequest, myResponse);
			}else if(myRequest.getMethod().equalsIgnoreCase("GET")){
				doGet(myRequest, myResponse);
			}			
		}
		
}
