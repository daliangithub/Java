package com.briup.mytomcat;

import java.util.ArrayList;
import java.util.List;
/**
 * ����ģ�飬���������ʹ��/world�Ϳ��Է���mytomcat��������
 * 
 * */
public class ServletMappingConfig {
	public static List<ServletMapping> servletMappingList = new ArrayList<>();
	static{
		servletMappingList.add(new ServletMapping("HelloServlet", "/world", "com.briup.mytomcat.HelloServlet"));
	}
	
}
