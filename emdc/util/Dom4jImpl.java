package com.briup.emdc.util;

import java.io.File;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 利用dom4j解析configuration.xml文件，，解析之后封装成一个对象，
 * 
 * */

public class Dom4jImpl {

	private File file;
	private Document document;
	
	public Dom4jImpl() {}
	public Dom4jImpl(File file){
		this.file = file;
		
	}
	//创建一个xml文档
	public Document  getDocument() throws DocumentException{
		SAXReader reader = new SAXReader();
		document = reader.read(file);
		return document;
	}
	//通过一个迭代器遍历这个文档
	public void readDocByIterator() throws DocumentException{
		Element root = getDocument().getRootElement();
		System.out.println("根节点是： "+root);
		
		//遍历一级子节点
		for(Iterator ele = root.elementIterator(); ele.hasNext();){
			Element element = (Element)ele.next();
			//遍历属性
			
		}
		
		
		
	}
	
	
}

























