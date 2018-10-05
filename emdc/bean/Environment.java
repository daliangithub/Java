package com.briup.emdc.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Environment implements Serializable{	
	/*
	 * �����洢ʵ���࣬�����������ࣨ�¶ȣ��ʶȣ�������̼��������ȣ������Ͷ�id����ݮ��id,
	 * ʵ����id,��������ַ������������ ��ָ���ţ�״̬������ֵ���ɼ�ʱ��
	 * 
	*/
	//100|101|2|16|1|3|5d606f7802|1|1516323596029
	//������������
	private String name;
	//���Ͷ�id
	private String srcId;
	//��ݮ��ϵͳid
	private String desId;
	//ʵ��������ģ��id��1-8��
	private String devId;
	//ģ���ϴ���������ַ
	private String sersorAddress;
	//����������
	private int count;
	//����ָ���� 3��ʾ�������ݣ�16��ʾ��������
	private String cmd;
	//״̬ Ĭ��1��ʾ�ɹ�
	private int status;
	//����ֵ
	private float data;
	//�ɼ�ʱ��
	private Timestamp gather_date;
	
	//constructors
	public Environment(){}
	public Environment(String name, String srcId, String desId, String devId, String sersorAddress, int count,
			String cmd, int status, float data, Timestamp gather_date) {
		super();
		this.name = name;
		this.srcId = srcId;
		this.desId = desId;
		this.devId = devId;
		this.sersorAddress = sersorAddress;
		this.count = count;
		this.cmd = cmd;
		this.status = status;
		this.data = data;
		this.gather_date = gather_date;
	}

	//getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getDesId() {
		return desId;
	}

	public void setDesId(String desId) {
		this.desId = desId;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getSersorAddress() {
		return sersorAddress;
	}

	public void setSersorAddress(String sersorAddress) {
		this.sersorAddress = sersorAddress;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getData() {
		return data;
	}

	public void setData(float data) {
		this.data = data;
	}

	public Timestamp getGather_date() {
		return gather_date;
	}

	public void setGather_date(Timestamp gather_date) {
		this.gather_date = gather_date;
	}

	@Override
	public String toString() {
		return "Environment [name=" + name + ", srcId=" + srcId + ", desId=" + desId + ", devId=" + devId
				+ ", sersorAddress=" + sersorAddress + ", count=" + count + ", cmd=" + cmd + ", status=" + status
				+ ", data=" + data + ", gather_date=" + gather_date + "]";
	}

	
}

































