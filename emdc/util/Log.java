package com.briup.emdc.util;

/**
 * 	该接口提供呢日志模块的规范，日志模块将日志信息，日志级别分成5个级别 不同级别的日志记录格式不尽相同，，
 * */

public interface Log extends EmdcModule {	
	//记录debug级别的日志
	public void debug(String message);
	//记录info级别的日志
	public void info(String message);
	//记录warn级别的日志
	public void warn(String message);
	//记录error级别的日志
	public void error(String message);
	//记录fatal级别的日志
	public void fatal(String message);	
	
}
