package com.briup.emdc.client;

import java.util.Collection;

import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.EmdcModule;

public interface Gather extends EmdcModule{
	/*Gather �ӿڹ涨�زɼ�ģ����Ӧ�õķ�����
	��ʼ������������������Ŀ������Ϣ���вɼ������ɼ������ݷ�װ��Collection<Environment>
	*/
	
	
		public Collection<Environment> gather () throws Exception;
		
	
}
