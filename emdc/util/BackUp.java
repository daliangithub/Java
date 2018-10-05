package com.briup.emdc.util;

/**
 * 备份模块
 * 该接口提供了备份模块的规范级所用的方法，
 * 
 * */
public interface BackUp extends EmdcModule{
	//备份模块         保存数据时候，在原来的数据基础上追加
	public static final boolean STORE_APPEND = true;
	//覆盖数据
	public static final boolean STORE_OVERRIDE = false;
	//在读取数据的同时，删除备份文件
	public static final boolean  LOAD_REMORE =true;
	//
	public void store(String filePath, Object obj, boolean flag) throws Exception;
	
	//加载模块
	public  Object load(String filePath, boolean del) throws Exception;



	
}
