package com.gao.tast;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gao.thread.calculate.ProtocolRDSSThread;
import com.gao.utils.SerialPortUtils;
import com.gao.utils.StringManagerUtils;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

@Component("RSDDServerTast")  
public class RSDDServerTast {
	public static final String readNum="$CCICA,0,00*7B\r\n";//读取北斗卡号指令  本卡号 0395725
	public static final String readRSSI="$CCRMO,BSI,2,0*26\r\n";//读取北斗信号强度指令
	public static final String readLocation="$CCDWA,0000000,V,1,L,,0,,,0*65\r\n";//读取北斗定位指令
	public static StringBuffer recDataBuff=new StringBuffer();
	
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void runRSDDServer(){
		try{
			//获得系统端口列表
			SerialPortUtils.getSystemPort();
			//开启端口COM5，波特率115200，数据位 8，停止位1，校验位无，超时时间2000ms
			final SerialPort serialPort = SerialPortUtils.openSerialPort("COM5",115200,SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE,2000);
			
			//设置串口的listener
			SerialPortUtils.setListenerToSerialPort(serialPort, new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent arg0) {
					if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {//数据通知
						byte[] bytes = SerialPortUtils.readData(serialPort);
						System.out.println("北斗--收到的数据长度："+bytes.length);
						System.out.println("北斗--收到的数据："+new String(bytes));
						
						
						String recvData=new String(bytes).replaceAll(" ", "");
						if(recvData.startsWith("$")){
							recDataBuff=new StringBuffer();
						}
						recDataBuff.append(recvData);
						if(recDataBuff.toString().endsWith("\r\n")||recDataBuff.toString().endsWith("\\r\\n")){
							System.out.println("北斗--收到的完整数据："+recDataBuff.toString());
							ProtocolRDSSThread protocolRDSSThread=new ProtocolRDSSThread(serialPort,recDataBuff.toString());
							protocolRDSSThread.start();
							recDataBuff=new StringBuffer();
						}
					}
				}
			});
			
			
//			SerialPortUtils.sendData(serialPort, readNum.getBytes());//读取卡号
//			SerialPortUtils.sendData(serialPort, readRSSI.getBytes());//信号强度
//			SerialPortUtils.sendData(serialPort, readLocation.getBytes());//北斗定位
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static class AcquisitionData{
		public String terminalNo="";//终端卡号
		public String AcqTime="";
		public byte[] runStatus=new byte[2];//运行状态
		public byte[] prodData=new byte[16];//油压、套压。回应、井口油温
		public byte[] elecData=new byte[48];//电参数据
		public byte[] freqData=new byte[14];//变频数据
		public byte[] screwData=new byte[8];//螺杆泵转速、扭矩
		public byte[] gtInfo=new byte[22];//功图点数、采集时间、冲次冲程
		public int dataLength=0;//每包数据长度 单位：字节， 不包括A4+采集地址+总片段+当前片段+传输完成标志
		public byte[] gtSData=new byte[500];//功图位移数据
		public byte[] gtFData=new byte[500];//功图载荷数据
		public byte[] gtAData=new byte[500];//功图电流数据
		public byte[] gtPData=new byte[500];//功图功率数据
		
		
		public void init(){//数据清零
			this.AcqTime="";
			this.runStatus=new byte[2];
			this.prodData=new byte[16];
			this.elecData=new byte[48];
			this.freqData=new byte[14];
			this.screwData=new byte[8];
			this.gtInfo=new byte[20];
			this.dataLength=0;
			this.gtSData=new byte[500];
			this.gtFData=new byte[500];
			this.gtAData=new byte[500];
			this.gtPData=new byte[500];
		}
		
		public String getTerminalNo() {
			return terminalNo;
		}
		public void setTerminalNo(String terminalNo) {
			this.terminalNo = terminalNo;
		}
		public byte[] getRunStatus() {
			return runStatus;
		}
		public void setRunStatus(byte[] runStatus) {
			this.runStatus = runStatus;
		}
		public byte[] getProdData() {
			return prodData;
		}
		public void setProdData(byte[] prodData) {
			this.prodData = prodData;
		}
		public byte[] getElecData() {
			return elecData;
		}
		public void setElecData(byte[] elecData) {
			this.elecData = elecData;
		}
		public byte[] getScrewData() {
			return screwData;
		}
		public void setScrewData(byte[] screwData) {
			this.screwData = screwData;
		}
		public byte[] getGtInfo() {
			return gtInfo;
		}
		public void setGtInfo(byte[] gtInfo) {
			this.gtInfo = gtInfo;
		}
		public byte[] getGtSData() {
			return gtSData;
		}
		public void setGtSData(byte[] gtSData) {
			this.gtSData = gtSData;
		}
		public byte[] getGtFData() {
			return gtFData;
		}
		public void setGtFData(byte[] gtFData) {
			this.gtFData = gtFData;
		}
		public byte[] getGtAData() {
			return gtAData;
		}
		public void setGtAData(byte[] gtAData) {
			this.gtAData = gtAData;
		}
		public byte[] getGtPData() {
			return gtPData;
		}
		public void setGtPData(byte[] gtPData) {
			this.gtPData = gtPData;
		}

		public int getDataLength() {
			return dataLength;
		}

		public void setDataLength(int dataLength) {
			this.dataLength = dataLength;
		}

		public byte[] getFreqData() {
			return freqData;
		}

		public void setFreqData(byte[] freqData) {
			this.freqData = freqData;
		}

		public String getAcqTime() {
			return AcqTime;
		}

		public void setAcqTime(String acqTime) {
			AcqTime = acqTime;
		}
		
	}
}
