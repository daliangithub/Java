package com.briup.emdc.util;

import com.briup.emdc.client.Client;
import com.briup.emdc.client.Gather;
import com.briup.emdc.server.DBStrore;
import com.briup.emdc.server.Server;

public interface Configuration {

	public Log getLogger() throws Exception;
	//获取服务器端的实现
	public Server getServer() throws Exception;
	public Client getClient() throws Exception;
	public DBStrore getDBstore() throws Exception;
	public Gather getGather() throws Exception;
	public BackUp getBackup() throws Exception;

}
