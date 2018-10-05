package com.briup.emdc.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.BackUp;
import com.briup.emdc.util.BackUpImpl;
import com.briup.emdc.util.Configuration;
import com.briup.emdc.util.Log;
import com.briup.emdc.util.LoggerImpl;

/**
 * 服务器端的实现类
 * 
 * */
class ServerThread extends Thread{
	private Socket socket;
	private InputStream inputStream = null;
	private BufferedInputStream bis;
	private ObjectInputStream ois = null;
	private BackUp backUp;
	private String backFile ;
	Collection<Environment> coll =null;
	private Log log;
	private Configuration configuration;
	
	public ServerThread() {}
	public ServerThread(Socket socket ,BackUp backUp,Log log,Configuration configuration,String backFile){
		this.socket= socket;
		this.backUp = backUp;
		this.log = log;
		this.configuration = configuration;
		this.backFile = backFile;
		System.out.println("backFile 文件路径： "+backFile);
	}
	
	@Override
	public void run() {
		try{
			//读取备份数据
			log.info("backFile文件的路径是否存在："+backFile);
			Collection<Environment> c = (Collection<Environment>)backUp.load(backFile, BackUp.LOAD_REMORE);
			
			inputStream = socket.getInputStream();
			 bis  = new BufferedInputStream(inputStream);
			 ois = new ObjectInputStream(bis);
			 coll = (Collection<Environment> )ois.readObject();
			log.info("集合的长度: "+coll.size());
			//加载备份文件中的数据
			log.info(getName()+"开始接收数据....");
			if(c!=null) {
				log.info("服务器正在加载备份文件....");
				coll.addAll(c);
				log.info("备份文件加载成功....");
			}
			log.info("数据接受数据成功......准备入库");
			//入库操作调用
//			configuration.getDBstore().saveDB(coll);			
			log.info("入库完成.....");
		}catch (Exception e) {
			//备份数据
			log.info("数据库发生异常，请检查，数据开始备份....");
			try {
				backUp.store(backFile,coll ,BackUp.STORE_OVERRIDE);
				log.info("数据备份成功....");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			
		//关闭资源
				try {
					if(socket!=null)socket.close();
					if(ois!=null) ois.close();
					if(bis!=null) bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
public  class ServerImpl implements Server{
	
	private ServerSocket ss =null;
	private Socket socket = null;
	private InputStream inputStream = null;
	private BufferedInputStream bis;
	private ObjectInputStream ois = null;
	private BackUp backUp ;
	private String backFile ;
	private Log log ;
	private int port;
	private Configuration configuration;

	@Override
	public void init(Properties properties) throws Exception {
		port = Integer.parseInt(properties.getProperty("port"));
		backFile = properties.getProperty("backup");
	}
	@Override
	public void setConfiguration(Configuration configuration) throws Exception {
		this.configuration = configuration;
		this.log = configuration.getLogger();
		this.backUp =configuration.getBackup();
	}
	@Override    
	public void reciver() throws Exception {
		System.out.println("backFile 文件路径： "+backFile);
		 ss = new ServerSocket(port);
		 System.out.println("服务端测试："+port);
		log.info("服务器启动成功.....");
		while(true){
			//监听
			 try {
				socket = ss.accept();
				 //调用多线程接受
				 new ServerThread(socket,backUp,log,configuration,backFile).start();
			} catch (Exception e1) {				
				//发生异常调用shutdown方法
				e1.printStackTrace();
				shutdown();
			}
		}
	}
	@Override
	public void shutdown() throws Exception {

		if(ois!=null) ois.close();
		if(bis!=null) bis.close();
		if(inputStream!=null) inputStream.close();
		if(socket!= null) socket.close();
		if(ss!=null) ss.close();
		log.info("服务器发生异常....网络断开...");
	}

}
