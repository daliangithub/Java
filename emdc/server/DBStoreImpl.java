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
 * 入库模块的实现类
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

	//构造器
	public DBStoreImpl() {}
	public DBStoreImpl(Collection<Environment> coll) {
		this.coll = coll;
	}
	//配置模块
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
	//入库模块方法
	@Override
	public void saveDB(Collection<Environment> coll) throws Exception {
		Class.forName(DRIVER);
		conn = DriverManager.getConnection(URL,username,password);
		//表示当前批处理当中的sql语句数
		int count = 0;
		//默认当前天数，，为0;
		int day = 0;
		for(Environment e: coll){      			
			/*两种情况下，，创建新的ps对象
			 * 1.ps = null   第一次进入for循环
			 * 2.当日期发生变化的时候，，day!= 当前day
			 * 采集天数，注意: environmen.getGather_data() 返回的Timestamp类型
			 * Timestamp.getDate() 方法返回的是day of month ,Timestamp.getDay()方法返回的是
			 * 0~6对应的星期
			 * */	
			if(ps==null ||day!=e.getGather_date().getDate()){
				day = e.getGather_date().getDate();
				System.out.println("数据入库的天数: "+day);
				
				/**
				 * 日期发生变化1号变成2号，防止1号中存在没有处理的sql 语句，
				 * 需要手动提交
				 * */
					if(ps!=null){
					//处理前一天的语句
					ps.executeBatch();
					//清空缓存
					ps.clearBatch();
					//关闭ps,重新创建sql语句的ps对象
					ps.close();
				}
				//根据日期的不同，，创建不同的Sql语句
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
				 * 记录当前批处理中sql语句数量，当满足批处理条数要求时候提交或者如果for循环中存在未处理完毕的
				 * sql语句，也就是在for循环结束是，批处理中任然有未处理的sql语句，同样提交
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
