package com.briup.emdc.server;

import com.briup.emdc.util.EmdcModule;

/**
 * Server �ӿ�������������������Ŀ��ģ��������Ĺ淶��Server�� ��������ͻ��˽�������ͨ��
 * ���ҵ�����DBStoreģ�飬�����յ������ݱ��浽���ݿ�
 * �������ˣ�
 * */

public interface Server extends EmdcModule{
	
	public void reciver() throws Exception;
	public void shutdown() throws Exception; 
	
}
