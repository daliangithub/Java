package com.briup.emdc.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerImpl implements Log{
	private static Logger logger ;
	private Configuration configuration;
	private String logFile;
//	static{
//		PropertyConfigurator.configure("src/com/briup/emdc/util/log4j.properties");
//		logger = Logger.getRootLogger();
//	}
	
	//配置模块部分注入信息部分
	@Override
	public void init(Properties properties) throws Exception {
		 logFile = properties.getProperty("log-file");
	}

	@Override
	public void setConfiguration(Configuration configuration) throws Exception {
		this.configuration = configuration;
		PropertyConfigurator.configure(logFile);
		logger = Logger.getRootLogger();
	}
	//日志模块部分
	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void warn(String message) {
		logger.warn(message);
	}

	@Override
	public void error(String message) {
		logger.error(message);
	}

	@Override
	public void fatal(String message) {
		logger.fatal(message);
	}

}
