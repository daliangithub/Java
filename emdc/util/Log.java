package com.briup.emdc.util;

/**
 * 	�ýӿ��ṩ����־ģ��Ĺ淶����־ģ�齫��־��Ϣ����־����ֳ�5������ ��ͬ�������־��¼��ʽ������ͬ����
 * */

public interface Log extends EmdcModule {	
	//��¼debug�������־
	public void debug(String message);
	//��¼info�������־
	public void info(String message);
	//��¼warn�������־
	public void warn(String message);
	//��¼error�������־
	public void error(String message);
	//��¼fatal�������־
	public void fatal(String message);	
	
}
