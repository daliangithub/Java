package com.briup.mytomcat;

import java.io.IOException;

public class HelloServlet extends MyServlet {

	@Override
	public void doGet(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("hello ��ã�������Ӧ�ذ�....");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("hello ������Ӧ�ذ�...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
