package com.briup.emdc.util;

/**
 * ����ģ��
 * �ýӿ��ṩ�˱���ģ��Ĺ淶�����õķ�����
 * 
 * */
public interface BackUp extends EmdcModule{
	//����ģ��         ��������ʱ����ԭ�������ݻ�����׷��
	public static final boolean STORE_APPEND = true;
	//��������
	public static final boolean STORE_OVERRIDE = false;
	//�ڶ�ȡ���ݵ�ͬʱ��ɾ�������ļ�
	public static final boolean  LOAD_REMORE =true;
	//
	public void store(String filePath, Object obj, boolean flag) throws Exception;
	
	//����ģ��
	public  Object load(String filePath, boolean del) throws Exception;



	
}
