package com.briup.emdc.client;

import java.util.Collection;

import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.EmdcModule;

/**
 * Client������������������Ŀ����ģ��ͻ���
 * �Ĺ淶��Client�����þ��������������ͨ�Ŵ�������
 * 
 * �ͻ��ˣ� 
 * */

public interface Client extends EmdcModule{
	
	public void send(Collection<Environment> collection) throws Exception ;
	
}
