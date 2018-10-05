/*package com.briup.emdc.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.BackUp;
import com.briup.emdc.util.Log;
import com.briup.emdc.util.LoggerImpl;

public class Demo2 implements Server{
	private ServerSocket ss=null;
	private Socket socket =null;
	private int port = 8099;
	private BackUp backup;
	private Log log = new LoggerImpl();
	@Override
	public void reciver() throws Exception {
		System.out.println("�ȴ��ͻ�������");
		ss = new ServerSocket(port);
		while(true) {
			try {
				//�������Ϳͻ�������
				socket = ss.accept();
				//���ö��߳̽��տͻ��˷��͵�����
				new ServerThread12(socket,backup,log).start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				shutdown();
			}
		}
	}

	@Override
	public void shutdown() throws Exception {
		if(socket!=null) socket.close();
		if(ss!=null) ss.close();
		System.out.println("�����������쳣������Ͽ�");
	}
	public static void main(String[] args) {
		try {
			new Demo2().reciver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class ServerThread12 extends Thread{
	private BackUp backUp2;
	private String backfile = "src/serverBackssss";
	private Socket socket;
	private Log log;
	private InputStream is =  null;
	private BufferedInputStream bis =null;
	private ObjectInputStream ois = null;
	private Collection<Environment> coll = null;
	public  ServerThread12() {	}
	public ServerThread12(Socket socket,BackUp backup2,Log log) {
		this.socket = socket;
		this.backUp2 = backup2;
		this.log = log;
	}
	@Override
	public void run() {
		try {
			//��ȡ�����ļ�
			is = socket.getInputStream();
			bis = new BufferedInputStream(is);
			ois = new ObjectInputStream(bis);
			coll = (Collection<Environment>)ois.readObject();
			System.out.println("&&&&&&&&&&&"+coll.size());
//			Collection<Environment> c = (Collection<Environment>)backUp2.load(backfile, BackUp.LOAD_REMORE);

			System.out.println("========="+backfile.length());
//			if(c!=null) {
//				System.out.println("���������ڼ��ر����ļ�");
//				coll.addAll(c);
//				System.out.println("�����ļ��������");
//				}
			System.out.println("======"+coll.size());
			System.out.println(getName()+"����������ϣ��ܹ���"+",׼�����");
			new DBStoreImpl().saveDB(coll);
			System.out.println("����������");
		} catch (Exception e) {
			e.printStackTrace();
			//��������
			System.out.println("��ʼ���ݷ�����������");
			try {
				backUp2.store(backfile,coll,BackUp.STORE_OVERRIDE);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}finally {
				try {
					if(ois!=null) ois.close();
					if(bis!=null) bis.close();
					if(is!=null) is.close();
					if(socket!=null)socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}
	
}*/