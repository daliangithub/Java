package com.briup.emdc.server;

import java.util.Collection;

import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.EmdcModule;

public interface DBStrore extends EmdcModule {
	
	/**
	 * DBStore提供呢入库模块，该接口实现类将Environment集合进行呢持久化
	 * 
	 * */
	
	public void saveDB(Collection<Environment> coll) throws Exception;
	
}
