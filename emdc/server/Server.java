package com.briup.emdc.server;

import com.briup.emdc.util.EmdcModule;

/**
 * Server 接口是物联网数据中心项目网模块服务器的规范，Server的 作用是与客户端进行数据通信
 * 并且调用呢DBStore模块，将接收到的数据保存到数据库
 * 服务器端：
 * */

public interface Server extends EmdcModule{
	
	public void reciver() throws Exception;
	public void shutdown() throws Exception; 
	
}
