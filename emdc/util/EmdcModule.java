package com.briup.emdc.util;
/*
 * Configuration 接口提供了配置模块的规范，配置模块的通过Gather,Client,Server,DBStore
 * 等模块的实现类进行实例传递，通过配置模块可以获取各个模块的对象
 * 
 * */

import java.util.Properties;

//获取日志模块的实现
public interface EmdcModule {
	
	public void init(Properties properties)throws Exception;
	public void setConfiguration(Configuration configuration)throws Exception;

}









