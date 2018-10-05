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
 * �������˵�ʵ����
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
		System.out.println("backFile �ļ�·���� "+backFile);
	}
	
	@Override
	public void run() {
		try{
			//��ȡ��������
			log.info("backFile�ļ���·���Ƿ���ڣ�"+backFile);
			Collection<Environment> c = (Collection<Environment>)backUp.load(backFile, BackUp.LOAD_REMORE);
			
			inputStream = socket.getInputStream();
			 bis  = new BufferedInputStream(inputStream);
			 ois = new ObjectInputStream(bis);
			 coll = (Collection<Environment> )ois.readObject();
			log.info("���ϵĳ���: "+coll.size());
			//���ر����ļ��е�����
			log.info(getName()+"��ʼ��������....");
			if(c!=null) {
				log.info("���������ڼ��ر����ļ�....");
				coll.addAll(c);
				log.info("�����ļ����سɹ�....");
			}
			log.info("���ݽ������ݳɹ�......׼�����");
			//����������
//			configuration.getDBstore().saveDB(coll);			
			log.info("������.....");
		}catch (Exception e) {
			//��������
			log.info("���ݿⷢ���쳣�����飬���ݿ�ʼ����....");
			try {
				backUp.store(backFile,coll ,BackUp.STORE_OVERRIDE);
				log.info("���ݱ��ݳɹ�....");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			
		//�ر���Դ
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
		System.out.println("backFile �ļ�·���� "+backFile);
		 ss = new ServerSocket(port);
		 System.out.println("����˲��ԣ�"+port);
		log.info("�����������ɹ�.....");
		while(true){
			//����
			 try {
				socket = ss.accept();
				 //���ö��߳̽���
				 new ServerThread(socket,backUp,log,configuration,backFile).start();
			} catch (Exception e1) {				
				//�����쳣����shutdown����
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
		log.info("�����������쳣....����Ͽ�...");
	}

}
