package com.briup.emdc.client;

import java.util.Collection;

import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.EmdcModule;

/**
 * Client是物联网数据中心项目网络模块客户端
 * 的规范，Client的作用就是与服务器进行通信传递数据
 * 
 * 客户端： 
 * */

public interface Client extends EmdcModule{
	
	public void send(Collection<Environment> collection) throws Exception ;
	
}
