package com.briup.emdc.client;

import java.util.Collection;

import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.EmdcModule;

public interface Gather extends EmdcModule{
	/*Gather 接口规定呢采集模块所应用的方法，
	开始对物联网数据中心项目环境信息进行采集，将采集的数据封装成Collection<Environment>
	*/
	
	
		public Collection<Environment> gather () throws Exception;
		
	
}
