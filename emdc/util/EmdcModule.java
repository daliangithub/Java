package com.briup.emdc.util;
/*
 * Configuration �ӿ��ṩ������ģ��Ĺ淶������ģ���ͨ��Gather,Client,Server,DBStore
 * ��ģ���ʵ�������ʵ�����ݣ�ͨ������ģ����Ի�ȡ����ģ��Ķ���
 * 
 * */

import java.util.Properties;

//��ȡ��־ģ���ʵ��
public interface EmdcModule {
	
	public void init(Properties properties)throws Exception;
	public void setConfiguration(Configuration configuration)throws Exception;

}









