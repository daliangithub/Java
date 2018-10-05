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
	//�����ȡ�Ķ�������
	private Collection<Environment> coll = new ArrayList<Environment>();
	//�ɼ���ԭʼ�ļ���ַ
	private String path;
	//������һ�ζ�ȡ���ֽ��� 
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
	//�ɼ�ģ�鷽���������ص���һ�����вɼ�����ļ���
	public Collection<Environment> gather() throws Exception {
//		configuration = new ConfigurationImpl();
//		log = configuration.getLogger();
		
		//1.��path2���ƶ���·����ȡ������һ�ζ�ȡ�����ֽ���   ��һ���ļ������ڣ���Ҫ������ж�
		//2.��ȡradwtmp�ļ�����С�ֽ����������ص�ֵ���浽path2ָ���ļ���
		//3.���Թ���һ�ε��ֽ������ٽ��б��ν���

		File file = new File(path2);    //����Ŀ���ļ����������¼
		long num = 0;	
		if(file.exists()){
			//��ȡ�ļ�
			 dis = new DataInputStream(new FileInputStream(file));
			//��ȡҪ�������ļ��ĳ��� Ҳ�����ֽ������������¼������������һ������
			num = dis.readLong();
		}
		//�ļ������ڣ��������ļ�
		RandomAccessFile raf = new RandomAccessFile(path,"r");
		//��ȡҪ�������ļ��ĳ���
		Long num2= raf.length();
		raf.seek(num);
		DataOutputStream dos =new DataOutputStream(new FileOutputStream(path2));
		//���ļ����ȼ�¼���ļ�����
		dos.writeLong(num2);
		
		/**
		 * 1.ʹ��RandomAccessFile���ж�ȡ���ݣ�
		 * 2.����| �ָ��ַ��������ݵ��ĸ��ַ��Ĳ�ͬ�ֱ����ʶȣ��¶ȣ�������̼������ǿ�ȵĻ�������
		 * 3.���߸��ֶΣ���ʾ16���ƻ���ֵ����16���ƵĻ���ֵת����10���ƣ�������¶Ⱥ��ʶ����ݣ�ǰ�����ֽ���
		 * �¶����ݣ��м������ֽ���ʪ�����ݣ�����Ƕ�����ֵ̼�͹���ǿ��ǰ�����ֽھ��Ƕ�Ӧ����
		 * 4��ʪ�Ⱥ��¶���ͬһ�м�¼����ȡһ����Ҫ��������Environment ���󣬲��ҷֱ���ֵ
		 * 5����װ�õĶ�����ӵ�Coll���ϣ�ͳ�Ƹ�������������
		 * */	
		String  s = null;
		String[] str = null;
		Environment environment  =null;
		//countͳ��ʪ�ȣ��¶ȵ�����    str[3] ��16
		int count = 0;
		//count2ͳ�ƹ���ǿ�ȵ�����     str[3] ��256 
		int count2 = 0;
		//count3ͳ�ƶ�����̼������    str[3] ��1280
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
			
			if("16".equals(str[3])){        //�¶Ȼ���ʪ��
				float value = (float)((Integer.parseInt(str[6].substring(0, 4),16)*0.00268127)-46.85);
				//�����¶�
				environment.setName("�¶�");
				environment.setData(value);
				coll.add(environment);
				//����ʪ��     Ϊʪ�ȸ�ֵ
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
				environment.setName("ʪ��");
				coll.add(environment);				
				count++;		
			}else {
				float value = Integer.parseInt(str[6].substring(0, 4),16);
				if("256".equals(str[3])){
					environment.setName("����ǿ��");
					environment.setData(value);
					coll.add(environment);				
					count2++;
				}
				if("1280".equals(str[3])){
					environment.setName("������̼");
					environment.setData(value);
					coll.add(environment);			
					count3++;
				}
			}
		}			
		
//		System.out.println("�¶�/ʪ�ȣ�"+count+"����¼");
//		System.out.println("����ǿ�ȣ� "+count2+"����¼");
//		System.out.println("������̼:"+count3+"����¼");
		
		log.info("�¶�/ʪ�ȣ�"+count);
		log.info("����ǿ�ȣ� "+count2);
		log.info("������̼��"+count3);
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
















