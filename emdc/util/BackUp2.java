package com.briup.emdc.util;

import java.util.Collection;

import com.briup.emdc.bean.Environment;

public interface BackUp2 {

	public void store(String fileName, Collection<Environment> environment,boolean flag) throws Exception ;
	
	public Environment load(String fileName, boolean flag) throws Exception ;
	
}
