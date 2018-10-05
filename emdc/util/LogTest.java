package com.briup.emdc.util;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
public class LogTest {

	public static void main(String[ ] args) {
		//加载日志
		PropertyConfigurator.configure("src/com/briup/emdc/util/log4j.properties");
		//获取
		Logger logger = Logger.getRootLogger();
		logger.info("this is info");

		
		
		
	}
	
	
}
