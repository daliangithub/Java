package com.briup.emdc.server;

import java.util.Collection;

import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.EmdcModule;

public interface DBStrore extends EmdcModule {
	
	/**
	 * DBStore�ṩ�����ģ�飬�ýӿ�ʵ���ཫEnvironment���Ͻ����س־û�
	 * 
	 * */
	
	public void saveDB(Collection<Environment> coll) throws Exception;
	
}
