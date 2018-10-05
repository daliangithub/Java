package com.briup.emdc.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import com.briup.emdc.bean.Environment;
import com.briup.emdc.util.Configuration;
import com.briup.emdc.util.ConfigurationImpl;
import com.briup.emdc.util.Log;
import com.briup.emdc.util.LoggerImpl;

public class GatherImpl  implements Gather {
	//保存获取的对象数据
	private Collection<Environment> coll = new ArrayList<Environment>();
	//采集的原始文件地址
	private String path;
	//保存上一次读取的字节数 
	private String path2 ;
	private Log log = new LoggerImpl();
	private static DataInputStream dis;
	private Configuration configuration ;
	
	@Override
	public void init(Properties properties) throws Exception {
		path = properties.getProperty("src-file");
		path2 = properties.getProperty("recored-file");
	}
	
	@Override
	public void setConfiguration(Configuration configuration) throws Exception {
		this.log = configuration.getLogger();
	}
	//采集模块方法，，返回的是一个含有采集对象的集合
	public Collection<Environment> gather() throws Exception {
//		configuration = new ConfigurationImpl();
//		log = configuration.getLogger();
		
		//1.从path2中制定的路径读取保存上一次读取到的字节数   第一次文件不存在，需要分情况判断
		//2.读取radwtmp文件的有小字节数，将返回的值保存到path2指定文件中
		//3.先略过上一次的字节数，再进行本次解析

		File file = new File(path2);    //创建目标文件，，保存记录
		long num = 0;	
		if(file.exists()){
			//读取文件
			 dis = new DataInputStream(new FileInputStream(file));
			//获取要读到的文件的长度 也就是字节数，将这个记录下来，便于下一次跳过
			num = dis.readLong();
		}
		//文件不存在，，创建文件
		RandomAccessFile raf = new RandomAccessFile(path,"r");
		//获取要读到的文件的长度
		Long num2= raf.length();
		raf.seek(num);
		DataOutputStream dos =new DataOutputStream(new FileOutputStream(path2));
		//将文件长度记录到文件当中
		dos.writeLong(num2);
		
		/**
		 * 1.使用RandomAccessFile按行读取数据，
		 * 2.根据| 分割字符串，根据第四个字符的不同分别赋予适度，温度，二氧化碳，光照强度的环境名称
		 * 3.第七个字段，表示16进制环境值，将16进制的环境值转换成10进制，如果是温度和适度数据，前两个字节是
		 * 温度数据，中间两个字节数湿度数据，如果是二氧化碳值和光照强度前两个字节就是对应数据
		 * 4，湿度和温度是同一行记录，读取一行需要创建两个Environment 对象，并且分别赋予值
		 * 5，封装好的对象添加到Coll集合，统计各个环境的条数
		 * */	
		String  s = null;
		String[] str = null;
		Environment environment  =null;
		//count统计湿度，温度的条数    str[3] 是16
		int count = 0;
		//count2统计光照强度的条数     str[3] 是256 
		int count2 = 0;
		//count3统计二氧化碳的条数    str[3] 是1280
		int count3 = 0;
		
		while((s= raf.readLine())!=null){
			environment = new Environment();
			str = s.split("\\|");
			environment.setSrcId(str[0]);
			environment.setDesId(str[1]);
			environment.setDevId(str[2]);
			environment.setSersorAddress(str[3]);
			environment.setCount(Integer.parseInt(str[4]));
			environment.setCmd(str[5]);
			environment.setStatus(Integer.parseInt(str[7]));
			Long time = new Long(str[8]);
			Timestamp gather_date =  new Timestamp(time);
			environment.setGather_date(gather_date);			
			
			if("16".equals(str[3])){        //温度或者湿度
				float value = (float)((Integer.parseInt(str[6].substring(0, 4),16)*0.00268127)-46.85);
				//创建温度
				environment.setName("温度");
				environment.setData(value);
				coll.add(environment);
				//创建湿度     为湿度赋值
				environment = new  Environment();
				environment.setSrcId(str[0]);
				environment.setDesId(str[1]);
				environment.setDevId(str[2]);
				environment.setSersorAddress(str[3]);
				environment.setCount(Integer.parseInt(str[4]));
				environment.setCmd(str[5]);
				environment.setStatus(Integer.parseInt(str[7]));
				environment.setGather_date(gather_date);
				
				float value2 = (float)((Integer.parseInt(str[6].substring(4, 8),16)*0.00190735)-6);			
				environment.setData(value2);
				environment.setName("湿度");
				coll.add(environment);				
				count++;		
			}else {
				float value = Integer.parseInt(str[6].substring(0, 4),16);
				if("256".equals(str[3])){
					environment.setName("光照强度");
					environment.setData(value);
					coll.add(environment);				
					count2++;
				}
				if("1280".equals(str[3])){
					environment.setName("二氧化碳");
					environment.setData(value);
					coll.add(environment);			
					count3++;
				}
			}
		}			
		
//		System.out.println("温度/湿度："+count+"条记录");
//		System.out.println("光照强度： "+count2+"条记录");
//		System.out.println("二氧化碳:"+count3+"条记录");
		
		log.info("温度/湿度："+count);
		log.info("光照强度： "+count2);
		log.info("二氧化碳："+count3);
		return coll;
	}
	
	public static void main(String[] args) {	
		try {
//			new GatherImpl().gather();			
			new ConfigurationImpl().getGather().gather();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
















