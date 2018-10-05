package com.briup.emdc.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.briup.emdc.client.Client;
import com.briup.emdc.client.Gather;
import com.briup.emdc.server.DBStrore;
import com.briup.emdc.server.Server;

public class ConfigurationImpl implements Configuration{
	
	private static Map<String, EmdcModule>  map =  new HashMap<String, EmdcModule>();
	
	public ConfigurationImpl()  {
		this("src\\com\\briup\\emdc\\util\\comfiguration.xml");
	}
	
	public static void main(String[] args)  {
		
		ConfigurationImpl configuration = new ConfigurationImpl();
		try {
			System.out.println(configuration.getBackup());
			System.out.println(configuration.getClient());
			System.out.println(configuration.getDBstore());
			System.out.println(configuration.getGather());
			System.out.println(configuration.getLogger());
			System.out.println(configuration.getServer());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Set<Entry<String,EmdcModule>> set = map.entrySet();
//		for(Entry<String,EmdcModule> entry :set){
//			System.out.println(entry);
//		}
	}
	public ConfigurationImpl(String path) {
		//解析configuration.xml文件 ,将标签名作为key，class 指定的实例对象作为值存储到map集合中
		//将map放如使用map.put(key,value);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		File file = new File(path);
		DocumentBuilder builder;
		try {
			/*
			 * 遍历根节点
			 * 获取根节点之后的所有子节点
			 * 遍历子节点，根据结点类型获取元素结点
			 * 遍历元素结点，获取结点名称和结点属性的值
			 * 将获取到的属性节点值进行实例化
			 * Class.forNane(value).newInstance();
			 * map按照元素节点名称为key，实例化对象
			 * */
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			Element e = document.getDocumentElement();
			NodeList nl = e.getChildNodes();
			
			for(int i = 0;i<nl.getLength();i++){
				
				if(nl.item(i).getNodeType() ==1){
					Element e2 = (Element)nl.item(i);
					String tagName = e2.getNodeName();
					String attValue = e2.getAttribute("class");
					System.out.println("tagName:"+tagName);
					System.out.println("attValue:"+attValue);
					/**
					 * 1.获取当前节点的子节点，构建properties 对象
					 * 2.根据节点类型判断获取元素节点
					 * 3.获取元素节点的名称为key,文件节点的内容为值
					 * 4.properties 按照节点名称为key,文本节点内容为值，进行保存
					 * 5.EmdcModule对象调用init()方法博爱村properties对象
					 * */
					EmdcModule module = (EmdcModule) Class.forName(attValue).newInstance();
					map.put(tagName, module);
					NodeList nl2 = e2.getChildNodes();
					Properties properties = new Properties();
					for(int j=0;j<nl2.getLength();j++){
						if(nl2.item(j).getNodeType() ==1){
							Element e3 = (Element)nl2.item(j);
							String tagName2 = e3.getNodeName();
							String tagValue2 = e3.getTextContent();
							properties.put(tagName2, tagValue2);					
						}			
					}
					module.init(properties);
				}
			}		
			for(EmdcModule em :map.values()){
				em.setConfiguration(this);
			}			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
				
	}
	
	@Override
	public Log getLogger() throws Exception {
		// TODO Auto-generated method stub
		return (Log) map.get("log");
	}

	@Override
	public Server getServer() throws Exception {
		// TODO Auto-generated method stub
		return (Server) map.get("server");
	}

	@Override
	public Client getClient() throws Exception {
		// TODO Auto-generated method stub
		return (Client) map.get("client");
	}

	@Override
	public DBStrore getDBstore() throws Exception {
		// TODO Auto-generated method stub
		return (DBStrore) map.get("dbstore");
	}

	@Override
	public Gather getGather() throws Exception {
		// TODO Auto-generated method stub
		return (Gather) map.get("gather");
	}

	@Override
	public BackUp getBackup() throws Exception {
		// TODO Auto-generated method stub
		return (BackUp) map.get("backup");
	}

}
