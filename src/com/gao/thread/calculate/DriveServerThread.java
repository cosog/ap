package com.gao.thread.calculate;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.gao.model.drive.RTUDriveConfig;
import com.gao.task.EquipmentDriverServerTask;


public class DriveServerThread extends Thread{
	private ServerSocket serverSocket;
	private RTUDriveConfig driveConfig;
	private ExecutorService pool = Executors.newCachedThreadPool();
	public DriveServerThread(ServerSocket serverSocket,RTUDriveConfig driveConfig) {
		super();
		this.serverSocket = serverSocket;
		this.driveConfig = driveConfig;
	}
	
	public void run(){
		int serverSocketPort=driveConfig.getPort();
		synchronized(this){
			while(true){
				for(int i=0;i<EquipmentDriverServerTask.clientUnitList.size();i++){
					if(EquipmentDriverServerTask.clientUnitList.get(i).socket==null){
						System.out.println(driveConfig.getDriverCode()+"等待客户端连接...");
						try {
							if(serverSocket==null){
								serverSocket = new ServerSocket(serverSocketPort);
							}
							Socket socket=serverSocket.accept();
							EquipmentDriverServerTask.clientUnitList.get(i).socket=new Socket();
							EquipmentDriverServerTask.clientUnitList.get(i).socket=socket;
							
							if(EquipmentDriverServerTask.clientUnitList.size()>0&&EquipmentDriverServerTask.clientUnitList.get(i).socket!=null){
								System.out.println(driveConfig.getDriverCode()+"服务端接收到客户端连接,thread:"+i+",IP:"+EquipmentDriverServerTask.clientUnitList.get(i).socket.getInetAddress()+",端口:"+EquipmentDriverServerTask.clientUnitList.get(i).socket.getPort());
//								if(driveConfig.getProtocol()==1){//通讯协议为Modbus-TCP
//									EquipmentDriverServerTast.clientUnitList.get(i).thread=new ProtocolModbusTCPThread(i,EquipmentDriverServerTast.clientUnitList.get(i),driveConfig);
//								}
								if(driveConfig.getDriverCode().equals("SunMoonStandardDrive")){//蚌埠日月
									EquipmentDriverServerTask.clientUnitList.get(i).thread=new IntelligentPumpingUnitThread(i,EquipmentDriverServerTask.clientUnitList.get(i),driveConfig);
								}else{
									EquipmentDriverServerTask.clientUnitList.get(i).thread=new ProtocolModbusThread(i,EquipmentDriverServerTask.clientUnitList.get(i),driveConfig);
								}
								if(EquipmentDriverServerTask.clientUnitList.get(i).thread!=null){
//									EquipmentDriverServerTast.clientUnitList.get(i).thread.start();
									pool.submit(EquipmentDriverServerTask.clientUnitList.get(i).thread);
									System.out.println(driveConfig.getDriverCode()+"线程池中当前线程数："+((ThreadPoolExecutor)pool).getPoolSize());
									System.out.println(driveConfig.getDriverCode()+"线程池中当前活跃线程数："+((ThreadPoolExecutor)pool).getActiveCount());
									break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}


	public RTUDriveConfig getDriveConfig() {
		return driveConfig;
	}


	public void setDriveConfig(RTUDriveConfig driveConfig) {
		this.driveConfig = driveConfig;
	}


	public ServerSocket getServerSocket() {
		return serverSocket;
	}


	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
}
