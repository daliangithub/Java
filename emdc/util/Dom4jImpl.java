package com.briup.emdc.util;

import java.io.File;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ����dom4j����configuration.xml�ļ���������֮���װ��һ������
 * 
 * */

public class Dom4jImpl {

	private File file;
	private Document document;
	
	public Dom4jImpl() {}
	public Dom4jImpl(File file){
		this.file = file;
		
	}
	//����һ��xml�ĵ�
	public Document  getDocument() throws DocumentException{
		SAXReader reader = new SAXReader();
		document = reader.read(file);
		return document;
	}
	//ͨ��һ����������������ĵ�
	public void readDocByIterator() throws DocumentException{
		Element root = getDocument().getRootElement();
		System.out.println("���ڵ��ǣ� "+root);
		
		//����һ���ӽڵ�
		for(Iterator ele = root.elementIterator(); ele.hasNext();){
			Element element = (Element)ele.next();
			//��������
			
		}
		
		
		
	}
	
	
}

























