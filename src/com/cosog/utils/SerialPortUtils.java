package com.cosog.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;


public class SerialPortUtils {
	/**
	 * 获得系统可用的端口名称列表
	 * @return 可用端口名称列表
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getSystemPort(){
		List<String> systemPorts = new ArrayList<>();
		CommPortIdentifier cpid;
		//获得系统可用的端口
		Enumeration<CommPortIdentifier> en = CommPortIdentifier.getPortIdentifiers();
		while(en.hasMoreElements()) {
			cpid=en.nextElement();
			if(cpid.getPortType()==CommPortIdentifier.PORT_SERIAL){
				String portName = cpid.getName();//获得端口的名字
				systemPorts.add(portName);
			}
		}
		System.out.println("系统可用端口列表："+systemPorts);
		return systemPorts;
	}
	
	/**
	 * 开启串口
	 * @param serialPortName 串口名称
	 * @param baudRate 波特率
	 * @param dataBit 数据位
	 * @param stopBit 停止位
	 * @param parityBit 校验位
	 * @param timeout 打开串口超时时间
	 * @return 串口对象
	 */
	public static SerialPort openSerialPort(String serialPortName,int baudRate,int dataBit,int stopBit,int parityBit,int timeout) {
		try {
			//通过端口名称得到端口
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(serialPortName);
			//打开端口，（自定义名字，打开超时时间）
			CommPort commPort = portIdentifier.open(serialPortName, timeout);
			//判断是不是串口
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                //设置串口参数（波特率，数据位8，停止位1，校验位无）
                serialPort.setSerialPortParams(baudRate, dataBit, stopBit, parityBit);                              
                System.out.println("开启串口成功，串口名称："+serialPortName);
                return serialPort;
            }        
            else {
                //是其他类型的端口
                throw new NoSuchPortException();
            }
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 关闭串口
	 * @param serialPort 要关闭的串口对象
	 */
	public static void closeSerialPort(SerialPort serialPort) {
		if(serialPort != null) {
			serialPort.close();
			System.out.println("关闭了串口："+serialPort.getName());
			serialPort = null;
		}
	}
	
	/**
	 * 获取校验位
	 * @param data 发送的数据，$到*(不包括$和*)之间的字符串
	 */
	public static String  getParityBit(String  data) {
		byte[] bytes=data.getBytes();
		byte result = 0;
		if(bytes.length>1){
			for(int i=0;i<bytes.length;i++){
				if(i==0){
					result=bytes[0];
				}else{
					result^=bytes[i];
				}
				
			}
		}
        String hv = Integer.toHexString(result & 0xFF);
		return hv;
	}
	
	/**
	 * 获取发送指令
	 * @param data 发送的数据，$到*(不包括$和*)之间的字符串
	 */
	public static String  getSendData(String  data) {
        String parity = getParityBit(data);//计算校验位
        String result="$"+data+"*"+parity+"\r\n";
		return result;
	}
 
	/**
	 * 向串口发送数据
	 * @param serialPort 串口对象 
	 * @param data 发送的数据
	 */
	public static void sendData(SerialPort serialPort, byte[] data) {
		OutputStream os = null;
        try {
        	os = serialPort.getOutputStream();//获得串口的输出流
        	os.write(data);
        	os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                	os.close();
                	os = null;
                }                
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
	}
	
	/**
	 * 从串口读取数据
	 * @param serialPort 要读取的串口
	 * @return 读取的数据
	 */
	public static byte[] readData(SerialPort serialPort) {
		InputStream is = null;
        byte[] bytes = null;
        try {
        	is = serialPort.getInputStream();//获得串口的输入流
            int bufflenth = is.available();//获得数据长度
            while (bufflenth != 0) {                             
                bytes = new byte[bufflenth];//初始化byte数组
                is.read(bytes);
                bufflenth = is.available();
            } 
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                	is.close();
                	is = null;
                }
            } catch(IOException e) {
            	e.printStackTrace();
            }
        }
        return bytes;
	}
	
	/**
	 * 给串口设置监听
	 * @param serialPort
	 * @param listener
	 */
	public static void setListenerToSerialPort(SerialPort serialPort, SerialPortEventListener listener) {
		try {
			//给串口添加事件监听
			serialPort.addEventListener(listener);
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		serialPort.notifyOnDataAvailable(true);//串口有数据监听
		serialPort.notifyOnBreakInterrupt(true);//中断事件监听
	}
}
