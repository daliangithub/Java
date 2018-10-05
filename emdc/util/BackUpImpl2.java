package com.briup.emdc.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;

import com.briup.emdc.bean.Environment;
/**
 * ����ģ��ʵ����
 * @author whl
 * 
 * */
public class BackUpImpl2 implements BackUp2{

	private static String fName;	
	//����ģ��
	@Override
	public void store(String fileName, Collection<Environment> environment,boolean flag) throws Exception {
//		File file = new File("src/books.txt");
		//���ö����������������     ------> ���ļ�����д����
		FileOutputStream fos = new FileOutputStream(fileName);
		BufferedOutputStream bos= new BufferedOutputStream(fos);
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(environment);
		oos.close();
		bos.close();
		fos.close();		
		
	}
	@Override
	public Environment load(String fileName, boolean flag) throws Exception {
		
		File file = new File("src/book.txt");
		//���ö�����������ȡ����
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		ObjectInputStream ois = new ObjectInputStream(bis);
		
		 Environment environment = (Environment)ois.readObject();
		 
		 
		return environment;
		
//		for(String e: map.keySet()){
//				Environment environment = map.get(fileName);
//		}
		
	}
	
}


