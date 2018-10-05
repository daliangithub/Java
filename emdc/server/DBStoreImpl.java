package com.briup.emdc.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.Configuration;
import com.briup.emdc.util.Log;

/**
 * ���ģ���ʵ����
 * */

public class DBStoreImpl implements DBStrore {
	private Collection<Environment> coll;
	private static  String DRIVER ;
	private static  String URL ;
	private static  String username ;
	private static  String password ;
	private static PreparedStatement ps;
	private static Connection conn;
	private int batchSize ;
	private Log log;

	//������
	public DBStoreImpl() {}
	public DBStoreImpl(Collection<Environment> coll) {
		this.coll = coll;
	}
	//����ģ��
	@Override
	public void init(Properties properties) throws Exception {
		DRIVER =properties.getProperty("driver");
		URL = properties.getProperty("url");
		username = properties.getProperty("username");
		password = properties.getProperty("password");
		batchSize = Integer.parseInt(properties.getProperty("batch-size"));		
	}
	@Override
	public void setConfiguration(Configuration configuration) throws Exception {
		this.log = configuration.getLogger();		
	}	
	//���ģ�鷽��
	@Override
	public void saveDB(Collection<Environment> coll) throws Exception {
		Class.forName(DRIVER);
		conn = DriverManager.getConnection(URL,username,password);
		//��ʾ��ǰ�������е�sql�����
		int count = 0;
		//Ĭ�ϵ�ǰ��������Ϊ0;
		int day = 0;
		for(Environment e: coll){      			
			/*��������£��������µ�ps����
			 * 1.ps = null   ��һ�ν���forѭ��
			 * 2.�����ڷ����仯��ʱ�򣬣�day!= ��ǰday
			 * �ɼ�������ע��: environmen.getGather_data() ���ص�Timestamp����
			 * Timestamp.getDate() �������ص���day of month ,Timestamp.getDay()�������ص���
			 * 0~6��Ӧ������
			 * */	
			if(ps==null ||day!=e.getGather_date().getDate()){
				day = e.getGather_date().getDate();
				System.out.println("������������: "+day);
				
				/**
				 * ���ڷ����仯1�ű��2�ţ���ֹ1���д���û�д����sql ��䣬
				 * ��Ҫ�ֶ��ύ
				 * */
					if(ps!=null){
					//����ǰһ������
					ps.executeBatch();
					//��ջ���
					ps.clearBatch();
					//�ر�ps,���´���sql����ps����
					ps.close();
				}
				//�������ڵĲ�ͬ����������ͬ��Sql���
				String sql = "insert into e_detail_"+day+" values(?,?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
			}
				ps.setString(1, e.getName());
				ps.setString(2, e.getSrcId());
				ps.setString(3, e.getDesId());
				ps.setString(4, e.getDevId());
				ps.setString(5, e.getSersorAddress());
				ps.setString(6, e.getCmd());
				ps.setInt(7,e.getCount());
				ps.setFloat(8, e.getData());
				ps.setInt(9, e.getStatus());
				ps.setTimestamp(10,e.getGather_date());
				ps.addBatch();
				/**
				 * ��¼��ǰ��������sql�������������������������Ҫ��ʱ���ύ�������forѭ���д���δ������ϵ�
				 * sql��䣬Ҳ������forѭ�������ǣ�����������Ȼ��δ�����sql��䣬ͬ���ύ
				 * */
				count++;
				if(count%batchSize ==0){
					ps.executeBatch();
					ps.clearBatch();
				}
		}	
			if(ps!=null){
				ps.executeBatch();
				ps.clearBatch();
				ps.close();
			}	
	}
}
