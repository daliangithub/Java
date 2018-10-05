package com.briup.emdc.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class BackUpImpl implements BackUp{
	//����ģ��..ע����Ϣ
	@Override
	public void init(Properties properties) throws Exception {
		
	}

	@Override
	public void setConfiguration(Configuration configuration) throws Exception {
		
	}
	
	//����ģ��ķ�������
	@Override
	public void store(String filePath, Object obj, boolean flag) throws Exception {
		File file = new File(filePath);
		if(!file.exists()) {
			file.createNewFile();
		}
		OutputStream os = new FileOutputStream(file,flag);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		os.close();
	}
	
	//�ӱ����ļ�������������ȡ��������
	@Override
	public Object load(String filePath, boolean del) throws Exception {
		File file = new File(filePath);
		if(!file.exists()) return null;
		InputStream is = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(is);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object o = ois.readObject();
		ois.close();
		is.close();
		//����ļ����ڣ���ɾ���ļ�
		if(del == BackUp.LOAD_REMORE){
			file.delete();
		}		
		return o;
	}
	
}
