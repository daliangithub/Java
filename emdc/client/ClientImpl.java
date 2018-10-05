package com.briup.emdc.client;

import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.BackUp;
import com.briup.emdc.util.Configuration;
import com.briup.emdc.util.Log;

/**
 * 客户端的实现类
 * */
public class ClientImpl implements Client{

	//声明备份对象
	private BackUp backUp ;
	//文件名
	private String backFile ;
	private Log log ;
	private String ip;
	private int port;
	private Configuration configuration;

	//配置注入模块，，
	@Override
	public void init(Properties properties) throws Exception {
			ip =properties.getProperty("ip");
			port = Integer.parseInt(properties.getProperty("port"));
			backFile = properties.getProperty("backup");
	}

	@Override
	public void setConfiguration(Configuration configuration) throws Exception {
			this.configuration = configuration;
			this.log = configuration.getLogger();
			this.backUp = configuration.getBackup();
	}

	//向服务器发送数据，这个数据是一个对象的集合
	@Override
	public void send(Collection<Environment> collection) throws Exception {
		
		try { 
			//首先读取备份文件中的数据
			Collection<Environment> c = (Collection<Environment>)backUp.load(backFile, BackUp.LOAD_REMORE);
			if(c!=null){
				log.info("找到备份数据，，准备添加到原来的集合中，构建一个新集合");
				collection.addAll(c);
				log.info("备份数据读取完毕.....");
			}			
			//建立服务器的连接
			Socket socket = new Socket(ip, port);
			log.info("客户端正在建立.....");
			OutputStream outputStream = socket.getOutputStream();
			log.info("客户端和服务器连解成功.....");
			//创建缓冲流
			BufferedOutputStream bos = new BufferedOutputStream(outputStream);
			ObjectOutputStream oos = new ObjectOutputStream(bos);	
			log.info("正在发送数据，，，请稍后........");
			oos.writeObject(collection);
			oos.flush();
			log.info("客户端发送完成......");
			oos.close();
			outputStream.close();
		} catch (Exception e) {
			//发生异常之后，，就开始备份数据
			log.info("写入数据失败.....");
			backUp.store(backFile, collection,BackUp.STORE_OVERRIDE);
			log.info("客户端备份数据完成.........");
			e.printStackTrace();
		}
	}
	
}
