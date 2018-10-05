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
 * �ͻ��˵�ʵ����
 * */
public class ClientImpl implements Client{

	//�������ݶ���
	private BackUp backUp ;
	//�ļ���
	private String backFile ;
	private Log log ;
	private String ip;
	private int port;
	private Configuration configuration;

	//����ע��ģ�飬��
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

	//��������������ݣ����������һ������ļ���
	@Override
	public void send(Collection<Environment> collection) throws Exception {
		
		try { 
			//���ȶ�ȡ�����ļ��е�����
			Collection<Environment> c = (Collection<Environment>)backUp.load(backFile, BackUp.LOAD_REMORE);
			if(c!=null){
				log.info("�ҵ��������ݣ���׼����ӵ�ԭ���ļ����У�����һ���¼���");
				collection.addAll(c);
				log.info("�������ݶ�ȡ���.....");
			}			
			//����������������
			Socket socket = new Socket(ip, port);
			log.info("�ͻ������ڽ���.....");
			OutputStream outputStream = socket.getOutputStream();
			log.info("�ͻ��˺ͷ���������ɹ�.....");
			//����������
			BufferedOutputStream bos = new BufferedOutputStream(outputStream);
			ObjectOutputStream oos = new ObjectOutputStream(bos);	
			log.info("���ڷ������ݣ��������Ժ�........");
			oos.writeObject(collection);
			oos.flush();
			log.info("�ͻ��˷������......");
			oos.close();
			outputStream.close();
		} catch (Exception e) {
			//�����쳣֮�󣬣��Ϳ�ʼ��������
			log.info("д������ʧ��.....");
			backUp.store(backFile, collection,BackUp.STORE_OVERRIDE);
			log.info("�ͻ��˱����������.........");
			e.printStackTrace();
		}
	}
	
}
