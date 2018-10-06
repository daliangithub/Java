package com.briup.mytomcat;

import java.io.IOException;

public class HelloServlet extends MyServlet {

	@Override
	public void doGet(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("hello 你好，我有响应呢啊....");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("hello 我有响应呢啊...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
