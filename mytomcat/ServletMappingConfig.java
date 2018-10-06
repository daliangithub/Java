package com.briup.mytomcat;

import java.util.ArrayList;
import java.util.List;
/**
 * 配置模块，在浏览器中使用/world就可以访问mytomcat服务器呢
 * 
 * */
public class ServletMappingConfig {
	public static List<ServletMapping> servletMappingList = new ArrayList<>();
	static{
		servletMappingList.add(new ServletMapping("HelloServlet", "/world", "com.briup.mytomcat.HelloServlet"));
	}
	
}
