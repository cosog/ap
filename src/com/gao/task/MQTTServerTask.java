package com.gao.task;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ScheduledExecutorService;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.task.MQTTServerTask.TransferDiscrete;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.JDBCUtil;
import com.gao.utils.MQTTRecvDataMap;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("static-access")
@Component("MQTTServerTast")  
public class MQTTServerTask {
	private String clientid = "mqttServerClient"+new Date().getTime();
    private static MqttClient client=null;
    private MqttConnectOptions options=null;  
    
    @SuppressWarnings("unused")
	private ScheduledExecutorService scheduler;
	
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void runMQTTServer() throws InstantiationException, IllegalAccessException, SQLException{
		//将以前接收到的数据清空
		Map<String, Object> map=MQTTRecvDataMap.getMapObject();
		if(!map.isEmpty()){
			map.clear();
		}
		String HOST =Config.getInstance().configFile.getMqtt().getServer();// "tcp://47.93.196.203:1883";
	    String[] TOPIC = {"Discrete/#","Diagram/#","Daily/#"};
	    int[] Qos  = {1,1,1};
	    
		String userName = Config.getInstance().configFile.getMqtt().getUserName();//"hinnotekClient1";   //非必须
	    String passWord = Config.getInstance().configFile.getMqtt().getPassWord();//"ZJ6m*#D4pd%b";  //非必须
		boolean isConnected=false;
		while(!isConnected){
			try {
	            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存  
	            client = new MqttClient(HOST, clientid, new MemoryPersistence());
	            // MQTT的连接设置
	            options = new MqttConnectOptions();
	            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
	            options.setCleanSession(true);
	            // 设置连接的用户名
	            options.setUserName(userName);
	            // 设置连接的密码
	            options.setPassword(passWord.toCharArray());
	            // 设置超时时间 单位为秒
	            options.setConnectionTimeout(10);
	            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制 
	            options.setKeepAliveInterval(20);
	            // 设置回调
	            client.setCallback(new MQTTPushCallback());
	            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
	            //遗嘱        options.setWill(topic, "close".getBytes(), 2, true);
	            client.connect(options);
	            //订阅消息
	            client.subscribe(TOPIC, Qos);
	            isConnected=true;
	        } catch (Exception e) {
	            e.printStackTrace();
	            isConnected=false;
	            try {
	            	if(client!=null&&client.isConnected()){
		        		client.disconnect();
		        	}
	            	client=null;
					Thread.sleep(10*1000);
				} catch (InterruptedException | MqttException e1) {
					e1.printStackTrace();
				}
	        }
		}
	}
	
	public static void sendMessage(String topicStr,String messageStr,boolean retain){
		try {
			MqttMessage message = new MqttMessage();  
	        message.setQos(1);  //保证消息能到达一次
	        message.setRetained(retain);  //是否为保留信息
	        message.setPayload(messageStr.getBytes());
	        MqttTopic topic = client.getTopic(topicStr);
	        MqttDeliveryToken token;
			token = topic.publish(message);
			token.waitForCompletion();  
	        System.out.println("message is published completely! "+ token.isComplete()); 
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean initWellConfig() throws InstantiationException, IllegalAccessException, SQLException{
		String sql="select wellname,driveraddr,"
				+ "acqcycle_diagram,acqCycleSetStatus_diagram,"
				+ "acqcycle_discrete,acqCycleSetStatus_discrete  from tbl_wellinformation t ";
		List<WellConfigInfo> list=JDBCUtil.getListCommBean(sql, WellConfigInfo.class, null);
		Map<String, Object> map=MQTTRecvDataMap.getMapObject();
		map.put("wellConfigData", list);
		return true;
	}
	
	public static WellConfigInfo getWellConfigData(String driverAddr){
		String sql="select wellname,driveraddr,"
				+ " acqcycle_diagram,acqCycleSetStatus_diagram,"
				+ " acqcycle_discrete,acqCycleSetStatus_discrete  "
				+ " from tbl_wellinformation t "
				+ " where t.driveraddr='"+driverAddr+"'";
		WellConfigInfo wellConfigInfo=null;
		try {
			wellConfigInfo = (WellConfigInfo)JDBCUtil.getResultBean(sql, WellConfigInfo.class, null);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException
				| IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wellConfigInfo;
	}
	
	/** 
	 * 发布消息的回调类 
	 * 
	 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。 
	 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。 
	 * 在回调中，将它用来标识已经启动了该回调的哪个实例。 
	 * 必须在回调类中实现三个方法： 
	 * 
	 *  public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。 
	 * 
	 *  public void connectionLost(Throwable cause)在断开连接时调用。 
	 * 
	 *  public void deliveryComplete(MqttDeliveryToken token)) 
	 *  接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。 
	 *  由 MqttClient.connect 激活此回调。 
	 * 
	 */  

	public class MQTTPushCallback implements MqttCallback{
		public void connectionLost(Throwable cause) {  
	        // 连接丢失后，一般在这里面进行重连  
	        System.out.println("MQTT连接断开，进行重连...");  
	        try {
	        	if(client!=null&&client.isConnected()){
	        		client.disconnect();
	        	}
	        	client=null;
				runMQTTServer();
				System.out.println("MQTT重连成功。");
			} catch (MqttException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }  

	    public void deliveryComplete(IMqttDeliveryToken token) {  
	        System.out.println("deliveryComplete---------" + token.isComplete());  
	    }

	    public void messageArrived(String topic, MqttMessage message) throws Exception {
	        MQTTDataAnalysisThread mQTTDataAnalysisThread=new MQTTDataAnalysisThread(topic,message);
	        mQTTDataAnalysisThread.start();
	    }
	}
	
	public static class MQTTDataAnalysisThread extends Thread{
		private String topic;
		private MqttMessage message;
		public MQTTDataAnalysisThread(String topic, MqttMessage message) {
			super();
			this.topic = topic;
			this.message = message;
		}
		
		@SuppressWarnings({"unchecked", "unused" })
		public void run(){
			synchronized(this){
				StringBuffer recJsonBuff=new StringBuffer();
				byte recvData[]=message.getPayload();
				Gson gson = new Gson();
				String discreteUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/PSToFSController/saveMQTTTransferElecDiscreteData";
				String diagramUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/PSToFSController/saveMQTTTransferElecDiagramData";
				String dailyUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/PSToFSController/saveMQTTTransferElecDailyData";

				int Qos=message.getQos();
				if(
						("Discrete".equals(topic)||"Diagram".equals(topic)||"Daily".equals(topic)
						||topic.indexOf("Discrete/")==0||topic.indexOf("Diagram/")==0||topic.indexOf("Daily/")==0)){
					try{
						short Code;
						String ID;
						int Timestamp;
						int Number;
						int CNT;
						int Size;
						int Header;
						ID=topic.split("/")[1];//设备ID
						Timestamp=(int)StringManagerUtils.getLong(recvData, 0);//采集时间戳
						Number=(int)StringManagerUtils.getInt(recvData, 8);//当前编号 从1开始
						CNT=(int)StringManagerUtils.getInt(recvData, 12);//总编号
						Header=(int)StringManagerUtils.getInt(recvData, 16);//头大小
						String jsonData=new String(StringManagerUtils.subBytes(recvData, Header, recvData.length-Header),"utf-8");//数据
						String key=ID+"_"+topic+"_"+Timestamp;
						Map<String, Object> map=MQTTRecvDataMap.getMapObject();
						//将该包数据添加到内存中
						Map<Integer,String> valueMap=(Map<Integer, String>) map.get(key);
						if(valueMap==null){
							valueMap=new TreeMap<Integer,String>();//使用TreeMap 方便排序
						}
						valueMap.put(Number, jsonData);
						map.put(key, valueMap);
						valueMap=(Map<Integer, String>) map.get(key);
						if(valueMap.size()==CNT){//判断数据数据是否接收完成
							Iterator<Map.Entry<Integer, String>> entries = valueMap.entrySet().iterator();
							while (entries.hasNext()) {
								Map.Entry<Integer, String> entry = entries.next();
								recJsonBuff.append(entry.getValue());
							}
							//将数据取出后，从内存中删掉
							map.remove(key);
							String pubTimerCorrectionTopic="TimerCorrection/"+ID;//时间校正主题
							String pubTransferDiscreteIntervalTopic="TransferDiscreteInterval/"+ID;//离散传输周期设置主题
							String pubTransferDiagramIntervalTopic="TransferDiagramInterval/"+ID;//曲线传输周期设置主题
							if("Discrete".equals(topic)||topic.indexOf("Discrete/")==0){//离散数据
								java.lang.reflect.Type type = new TypeToken<TransferDiscrete>() {}.getType();
								TransferDiscrete transferDiscrete=gson.fromJson(recJsonBuff.toString(), type);
								if(transferDiscrete!=null){
									transferDiscrete.setID(ID);
									if(transferDiscrete.getAcquisitionTime().contains("T")){
										transferDiscrete.setAcquisitionTime(transferDiscrete.getAcquisitionTime().replace("T", " ").split("\\.")[0]);
									}
									StringManagerUtils.sendPostMethod(discreteUrl, gson.toJson(transferDiscrete),"utf-8");
									//时间校准
									DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									String timerCorrectionStart=StringManagerUtils.getCurrentTime()+" "+Config.getInstance().configFile.getAgileCalculate().getESDiagram().getInversion().getTimerCorrectionStart();
									String timerCorrectionEnd=StringManagerUtils.getCurrentTime()+" "+Config.getInstance().configFile.getAgileCalculate().getESDiagram().getInversion().getImerCorrectionEnd();
									String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
									if(format.parse(currentTime).getTime()>format.parse(timerCorrectionStart).getTime()
											&&format.parse(currentTime).getTime()<format.parse(timerCorrectionEnd).getTime()
											&&Math.abs(format.parse(currentTime).getTime()/1000-format.parse(transferDiscrete.getAcquisitionTime()).getTime()/1000)>Config.getInstance().configFile.getAgileCalculate().getESDiagram().getInversion().getTimerCorrectionLimit()){
										//设置该设备时间校正标准，接收完当天日报后，进行校正
										map.put(pubTimerCorrectionTopic, true);
									}
									WellConfigInfo wellConfigInfo= getWellConfigData(ID);
									if(wellConfigInfo!=null&&wellConfigInfo.getAcqCycle_Discrete()!=0&&wellConfigInfo.getAcqCycle_Discrete()!=transferDiscrete.getInterval2()){//如果采集间隔不一致
										map.put(pubTransferDiscreteIntervalTopic, wellConfigInfo.getAcqCycle_Discrete());
									}else if(wellConfigInfo!=null&&wellConfigInfo.getAcqCycle_Discrete()!=0&&wellConfigInfo.getAcqCycle_Discrete()==transferDiscrete.getInterval2()&&wellConfigInfo.getAcqCycleSetStatus_discrete()!=2){//如果一致，更新状态
										String updateSql="update tbl_wellinformation set acqCycleSetStatus_discrete=2 where driveraddr='"+ID+"'";
										JDBCUtil.updateRecord(updateSql, null);
									}
								}
					        }else if("Diagram".equals(topic)||topic.indexOf("Diagram/")==0){//曲线数据
					        	java.lang.reflect.Type type = new TypeToken<TransferDiagram>() {}.getType();
					        	TransferDiagram transferDiagram=gson.fromJson(recJsonBuff.toString().replaceAll("false", "0").replaceAll("true", "1"), type);
					        	if(transferDiagram!=null){
					        		transferDiagram.setID(ID);
					        		if(transferDiagram.getAcquisitionTime().contains("T")){
					        			transferDiagram.setAcquisitionTime(transferDiagram.getAcquisitionTime().replace("T", " ").split("\\.")[0]);
									}
									if(transferDiagram.getWatt()!=null&&transferDiagram.getWatt().size()>0){
										StringManagerUtils.sendPostMethod(diagramUrl, gson.toJson(transferDiagram),"utf-8");
									}else{
										System.out.println("无效的曲线数据");
									}
									WellConfigInfo wellConfigInfo= getWellConfigData(ID);
									if(wellConfigInfo!=null&&wellConfigInfo.getAcqCycle_Diagram()!=0&&wellConfigInfo.getAcqCycle_Diagram()!=transferDiagram.getInterval2()&&wellConfigInfo.getAcqCycleSetStatus_diagram()!=1){//如果采集间隔不一致
										sendMessage(pubTransferDiagramIntervalTopic,wellConfigInfo.getAcqCycle_Diagram()+"",false);
										String updateSql="update tbl_wellinformation set acqCycleSetStatus_diagram=1 where driveraddr='"+ID+"'";
										JDBCUtil.updateRecord(updateSql, null);
									}else if(wellConfigInfo!=null&&wellConfigInfo.getAcqCycle_Diagram()!=0&&wellConfigInfo.getAcqCycle_Diagram()==transferDiagram.getInterval2()&&wellConfigInfo.getAcqCycleSetStatus_diagram()!=2){//如果一致，更新状态
										String updateSql="update tbl_wellinformation set acqCycleSetStatus_diagram=2 where driveraddr='"+ID+"'";
										JDBCUtil.updateRecord(updateSql, null);
									}
					        	}
					        }else if("Daily".equals(topic)||topic.indexOf("Daily/")==0){//汇总数据
					        	java.lang.reflect.Type type = new TypeToken<TransferDaily>() {}.getType();
					        	TransferDaily transferDaily=gson.fromJson(recJsonBuff.toString(), type);
					        	if(transferDaily!=null){
					        		transferDaily.setID(ID);
					        		StringManagerUtils.sendPostMethod(dailyUrl, gson.toJson(transferDaily),"utf-8");
					        		//判断是否进行时间校正
					        		Object TimerCorrectionSign=map.get(pubTimerCorrectionTopic);
				        			if(TimerCorrectionSign!=null&&(boolean)TimerCorrectionSign){
				        				String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
				        				sendMessage(pubTimerCorrectionTopic,currentTime,false);
				        				map.remove(pubTimerCorrectionTopic);
				        			}
				        			int TransferDiscreteIntervalSetSign=(int)map.get(pubTransferDiscreteIntervalTopic);
				        			if(TransferDiscreteIntervalSetSign>0){
				        				sendMessage(pubTransferDiscreteIntervalTopic,TransferDiscreteIntervalSetSign+"",false);
				        				map.remove(pubTransferDiscreteIntervalTopic);
				        				//更新状态 已下发
				        				String updateSql="update tbl_wellinformation set acqCycleSetStatus_discrete=1 where driveraddr='"+ID+"'";
										JDBCUtil.updateRecord(updateSql, null);
				        			}
					        	}
					        }
						}
					}catch(Exception e){
//						e.printStackTrace();
					}
				}
			}
		}
		
		public String getTopic() {
			return topic;
		}
		public void setTopic(String topic) {
			this.topic = topic;
		}
		public MqttMessage getMessage() {
			return message;
		}
		public void setMessage(MqttMessage message) {
			this.message = message;
		}
	}
	
	public static class TransferDiscrete{
		private String WellName;

	    private String ID;
	    
	    private String Ver="";

	    private String AcquisitionTime;

	    private int ResultStatus;
	    
	    private float Signal;
	    
	    private int Interval;
	    
	    private int Interval2=30;
	    
	    private ABCAve V;
	    
	    private ABCAve V2;
	    
	    private ABCAve I;
	    
	    private ABCSum Watt;
	    
	    private ABCSum Var;
	    
	    private ABCSum VA;
	    
	    private ABCSum PF;
	    
	    private float FREQ;
	    
	    private float RPM=0;
	    
	    private Energy TotalEnergy;
	    
	    private Energy TodayEnergy;
	    
	    private boolean RunStatus;
	    
	    private TimeEfficiency RunEfficiency;
	    
	    private boolean CommStatus;
	    
	    private TimeEfficiency CommEfficiency;

	    private int ResultCode;
	    
	    private int SingleCode;
	    
	    private String ResultStr="";

	    private AlarmItems AlarmItems;

	    private ElectricLimit ElectricLimit;


		public static class ABCAve{
			private float A;

		    private float B;

		    private float C;

		    private float Avg;

		    public void setA(float A){
		        this.A = A;
		    }
		    public float getA(){
		        return this.A;
		    }
		    public void setB(float B){
		        this.B = B;
		    }
		    public float getB(){
		        return this.B;
		    }
		    public void setC(float C){
		        this.C = C;
		    }
		    public float getC(){
		        return this.C;
		    }
		    public void setAvg(float Avg){
		        this.Avg = Avg;
		    }
		    public float getAvg(){
		        return this.Avg;
		    }
		}
		
		public static class ABCSum{
			private float A;

		    private float B;

		    private float C;

		    private float Sum;

		    public void setA(float A){
		        this.A = A;
		    }
		    public float getA(){
		        return this.A;
		    }
		    public void setB(float B){
		        this.B = B;
		    }
		    public float getB(){
		        return this.B;
		    }
		    public void setC(float C){
		        this.C = C;
		    }
		    public float getC(){
		        return this.C;
		    }
		    public void setSum(float Sum){
		        this.Sum = Sum;
		    }
		    public float getSum(){
		        return this.Sum;
		    }
		}
		
		public static class AlarmStatus{
			private boolean MaxValueStatus;

		    private boolean MinValueStatus;

		    private boolean ZeroLevelStatus;

		    private boolean BalacneStatus;

			public boolean isMaxValueStatus() {
				return MaxValueStatus;
			}

			public void setMaxValueStatus(boolean maxValueStatus) {
				MaxValueStatus = maxValueStatus;
			}

			public boolean isMinValueStatus() {
				return MinValueStatus;
			}

			public void setMinValueStatus(boolean minValueStatus) {
				MinValueStatus = minValueStatus;
			}

			public boolean isZeroLevelStatus() {
				return ZeroLevelStatus;
			}

			public void setZeroLevelStatus(boolean zeroLevelStatus) {
				ZeroLevelStatus = zeroLevelStatus;
			}

			public boolean isBalacneStatus() {
				return BalacneStatus;
			}

			public void setBalacneStatus(boolean balacneStatus) {
				BalacneStatus = balacneStatus;
			}

		    
		}
		
		public static class ABCAlarmStatus{
			AlarmStatus A;
			
			AlarmStatus B;
			
			AlarmStatus C;
			
			private boolean BalacneStatus;

			public AlarmStatus getA() {
				return A;
			}

			public void setA(AlarmStatus a) {
				A = a;
			}

			public AlarmStatus getB() {
				return B;
			}

			public void setB(AlarmStatus b) {
				B = b;
			}

			public AlarmStatus getC() {
				return C;
			}

			public void setC(AlarmStatus c) {
				C = c;
			}

			public boolean isBalacneStatus() {
				return BalacneStatus;
			}

			public void setBalacneStatus(boolean balacneStatus) {
				BalacneStatus = balacneStatus;
			}
		}
		
		public static class  AlarmItems{
			ABCAlarmStatus I;
			ABCAlarmStatus V;
			public ABCAlarmStatus getI() {
				return I;
			}
			public void setI(ABCAlarmStatus i) {
				I = i;
			}
			public ABCAlarmStatus getV() {
				return V;
			}
			public void setV(ABCAlarmStatus v) {
				V = v;
			}
		}
		
		public static class  Limit{
			private float Max;

		    private float Min;

		    private float Zero;

		    private float MaxPercent;

		    private float MinPercent;

		    public void setMax(float Max){
		        this.Max = Max;
		    }
		    public float getMax(){
		        return this.Max;
		    }
		    public void setMin(float Min){
		        this.Min = Min;
		    }
		    public float getMin(){
		        return this.Min;
		    }
		    public void setZero(float Zero){
		        this.Zero = Zero;
		    }
		    public float getZero(){
		        return this.Zero;
		    }
		    public void setMaxPercent(float MaxPercent){
		        this.MaxPercent = MaxPercent;
		    }
		    public float getMaxPercent(){
		        return this.MaxPercent;
		    }
		    public void setMinPercent(float MinPercent){
		        this.MinPercent = MinPercent;
		    }
		    public float getMinPercent(){
		        return this.MinPercent;
		    }
		}
		
		public static class  ABCLimit{
			Limit A;
			Limit B;
			Limit C;
			public Limit getA() {
				return A;
			}
			public void setA(Limit a) {
				A = a;
			}
			public Limit getB() {
				return B;
			}
			public void setB(Limit b) {
				B = b;
			}
			public Limit getC() {
				return C;
			}
			public void setC(Limit c) {
				C = c;
			}
		}
		
		public static class ElectricLimit{
			ABCLimit I;
			ABCLimit V;
			public ABCLimit getI() {
				return I;
			}
			public void setI(ABCLimit i) {
				I = i;
			}
			public ABCLimit getV() {
				return V;
			}
			public void setV(ABCLimit v) {
				V = v;
			}
		}
		
		public static class Range{
			private String StartTime;

		    private String EndTime;

		    public void setStartTime(String StartTime){
		        this.StartTime = StartTime;
		    }
		    public String getStartTime(){
		        return this.StartTime;
		    }
		    public void setEndTime(String EndTime){
		        this.EndTime = EndTime;
		    }
		    public String getEndTime(){
		        return this.EndTime;
		    }
		}

		public static class Energy{
			private float Watt;
			
			private float PWatt;
			
			private float NWatt;
			
			private float Var;
			
			private float PVar;
			
			private float NVar;
			
			private float VA;

			public float getWatt() {
				return Watt;
			}

			public void setWatt(float watt) {
				Watt = watt;
			}

			public float getPWatt() {
				return PWatt;
			}

			public void setPWatt(float pWatt) {
				PWatt = pWatt;
			}

			public float getNWatt() {
				return NWatt;
			}

			public void setNWatt(float nWatt) {
				NWatt = nWatt;
			}

			public float getVar() {
				return Var;
			}

			public void setVar(float var) {
				Var = var;
			}

			public float getPVar() {
				return PVar;
			}

			public void setPVar(float pVar) {
				PVar = pVar;
			}

			public float getNVar() {
				return NVar;
			}

			public void setNVar(float nVar) {
				NVar = nVar;
			}

			public float getVA() {
				return VA;
			}

			public void setVA(float vA) {
				VA = vA;
			}
		}
		
		public static class TimeEfficiency{
			private List<Range> Range;
			private float Time;
			private float Efficiency;
			private String RangeString;
			public List<Range> getRange() {
				return Range;
			}
			public void setRange(List<Range> range) {
				Range = range;
			}
			public float getTime() {
				return Time;
			}
			public void setTime(float time) {
				Time = time;
			}
			public float getEfficiency() {
				return Efficiency;
			}
			public void setEfficiency(float efficiency) {
				Efficiency = efficiency;
			}
			public String getRangeString() {
				return RangeString;
			}
			public void setRangeString(String rangeString) {
				RangeString = rangeString;
			}
		}
		
		public String getWellName() {
			return WellName;
		}

		public void setWellName(String wellName) {
			WellName = wellName;
		}

		public String getID() {
			return ID;
		}

		public void setID(String iD) {
			ID = iD;
		}

		public String getAcquisitionTime() {
			return AcquisitionTime;
		}

		public void setAcquisitionTime(String acquisitionTime) {
			AcquisitionTime = acquisitionTime;
		}

		public int getResultStatus() {
			return ResultStatus;
		}

		public void setResultStatus(int resultStatus) {
			ResultStatus = resultStatus;
		}

		public ABCAve getV() {
			return V;
		}

		public void setV(ABCAve v) {
			V = v;
		}

		public ABCAve getV2() {
			return V2;
		}

		public void setV2(ABCAve v2) {
			V2 = v2;
		}

		public ABCAve getI() {
			return I;
		}

		public void setI(ABCAve i) {
			I = i;
		}

		public ABCSum getWatt() {
			return Watt;
		}

		public void setWatt(ABCSum watt) {
			Watt = watt;
		}

		public ABCSum getVar() {
			return Var;
		}

		public void setVar(ABCSum var) {
			Var = var;
		}

		public ABCSum getVA() {
			return VA;
		}

		public void setVA(ABCSum vA) {
			VA = vA;
		}

		public ABCSum getPF() {
			return PF;
		}

		public void setPF(ABCSum pF) {
			PF = pF;
		}

		
		public float getFREQ() {
			return FREQ;
		}

		public void setFREQ(float fREQ) {
			FREQ = fREQ;
		}

		public boolean getRunStatus() {
			return RunStatus;
		}

		public void setRunStatus(boolean runStatus) {
			RunStatus = runStatus;
		}

		public int getResultCode() {
			return ResultCode;
		}

		public void setResultCode(int resultCode) {
			ResultCode = resultCode;
		}

		public AlarmItems getAlarmItems() {
			return AlarmItems;
		}

		public void setAlarmItems(AlarmItems alarmItems) {
			AlarmItems = alarmItems;
		}

		public ElectricLimit getElectricLimit() {
			return ElectricLimit;
		}

		public void setElectricLimit(ElectricLimit electricLimit) {
			ElectricLimit = electricLimit;
		}

		public String getVer() {
			return Ver;
		}

		public void setVer(String ver) {
			Ver = ver;
		}

		public float getRPM() {
			return RPM;
		}

		public void setRPM(float rPM) {
			RPM = rPM;
		}

		public Energy getTotalEnergy() {
			return TotalEnergy;
		}

		public void setTotalEnergy(Energy totalEnergy) {
			TotalEnergy = totalEnergy;
		}

		public Energy getTodayEnergy() {
			return TodayEnergy;
		}

		public void setTodayEnergy(Energy todayEnergy) {
			TodayEnergy = todayEnergy;
		}

		public TimeEfficiency getRunEfficiency() {
			return RunEfficiency;
		}

		public void setRunEfficiency(TimeEfficiency runEfficiency) {
			RunEfficiency = runEfficiency;
		}

		public boolean isCommStatus() {
			return CommStatus;
		}

		public void setCommStatus(boolean commStatus) {
			CommStatus = commStatus;
		}

		public TimeEfficiency getCommEfficiency() {
			return CommEfficiency;
		}

		public void setCommEfficiency(TimeEfficiency commEfficiency) {
			CommEfficiency = commEfficiency;
		}

		public float getSignal() {
			return Signal;
		}

		public void setSignal(float signal) {
			Signal = signal;
		}

		public int getInterval() {
			return Interval;
		}

		public void setInterval(int interval) {
			Interval = interval;
		}

		public int getInterval2() {
			return Interval2;
		}

		public void setInterval2(int interval2) {
			Interval2 = interval2;
		}

		public int getSingleCode() {
			return SingleCode;
		}

		public void setSingleCode(int singleCode) {
			SingleCode = singleCode;
		}

		public String getResultStr() {
			return ResultStr;
		}

		public void setResultStr(String resultStr) {
			ResultStr = resultStr;
		}
	}
	
	public static class TransferDiagram{
		private String WellName;

	    private String ID;
	    
	    private String Ver;

	    private String AcquisitionTime;
	    
	    private int ResultStatus=1;
	    
	    private float Signal;
	    
	    private int Interval2=60;
	    
	    private int CNT;

	    private float Stroke;

	    private float SPM;

	    private float MaxF;
	    
	    private float MinF;
	    
	    private float UpstrokeIMax;
	    
	    private float DownstrokeIMax;
	    
	    private float UpstrokeWattMax;
	    
	    private float DownstrokeWattMax;
	    
	    private float IDegreeBalance;
	    
	    private float WattDegreeBalance;
	    
	    private float UpstrokeIMax_Filter;
	    
	    private float DownstrokeIMax_Filter;
	    
	    private float UpstrokeWattMax_Filter;
	    
	    private float DownstrokeWattMax_Filter;
	    
	    private float IDegreeBalance_Filter;
	    
	    private float WattDegreeBalance_Filter;
	    
	    private float MotorInputAvgWatt;

	    private List<Float> I;

	    private List<Float> Watt;
	    
	    private List<Float> Interval;
	    
	    private List<Float> RPM;
	    
	    private List<Float> I_Filter;

	    private List<Float> Watt_Filter;
	    
	    private List<Float> RPM_Filter;

	    private List<Float> F;

	    private List<Float> S;
	    
	    private List<Float> F360;
	    
	    private List<Float> S360;
	    
	    private List<Float> A360;

		public String getWellName() {
			return WellName;
		}

		public void setWellName(String wellName) {
			WellName = wellName;
		}

		public String getID() {
			return ID;
		}

		public void setID(String iD) {
			ID = iD;
		}

		public String getAcquisitionTime() {
			return AcquisitionTime;
		}

		public void setAcquisitionTime(String acquisitionTime) {
			AcquisitionTime = acquisitionTime;
		}

		public float getStroke() {
			return Stroke;
		}

		public void setStroke(float stroke) {
			Stroke = stroke;
		}

		public float getSPM() {
			return SPM;
		}

		public void setSPM(float sPM) {
			SPM = sPM;
		}

		public int getCNT() {
			return CNT;
		}

		public void setCNT(int cNT) {
			CNT = cNT;
		}

		public List<Float> getI() {
			return I;
		}

		public void setI(List<Float> i) {
			I = i;
		}

		public List<Float> getWatt() {
			return Watt;
		}

		public void setWatt(List<Float> watt) {
			Watt = watt;
		}

		public List<Float> getF() {
			return F;
		}

		public void setF(List<Float> f) {
			F = f;
		}

		public List<Float> getS() {
			return S;
		}

		public void setS(List<Float> s) {
			S = s;
		}

		public String getVer() {
			return Ver;
		}

		public void setVer(String ver) {
			Ver = ver;
		}

		public float getUpstrokeIMax() {
			return UpstrokeIMax;
		}

		public void setUpstrokeIMax(float upstrokeIMax) {
			UpstrokeIMax = upstrokeIMax;
		}

		public float getDownstrokeIMax() {
			return DownstrokeIMax;
		}

		public void setDownstrokeIMax(float downstrokeIMax) {
			DownstrokeIMax = downstrokeIMax;
		}

		public float getUpstrokeWattMax() {
			return UpstrokeWattMax;
		}

		public void setUpstrokeWattMax(float upstrokeWattMax) {
			UpstrokeWattMax = upstrokeWattMax;
		}

		public float getDownstrokeWattMax() {
			return DownstrokeWattMax;
		}

		public void setDownstrokeWattMax(float downstrokeWattMax) {
			DownstrokeWattMax = downstrokeWattMax;
		}

		public float getIDegreeBalance() {
			return IDegreeBalance;
		}

		public void setIDegreeBalance(float iDegreeBalance) {
			IDegreeBalance = iDegreeBalance;
		}

		public float getWattDegreeBalance() {
			return WattDegreeBalance;
		}

		public void setWattDegreeBalance(float wattDegreeBalance) {
			WattDegreeBalance = wattDegreeBalance;
		}

		public List<Float> getInterval() {
			return Interval;
		}

		public void setInterval(List<Float> interval) {
			Interval = interval;
		}

		public List<Float> getRPM() {
			return RPM;
		}

		public void setRPM(List<Float> rPM) {
			RPM = rPM;
		}

		public List<Float> getI_Filter() {
			return I_Filter;
		}

		public void setI_Filter(List<Float> i_Filter) {
			I_Filter = i_Filter;
		}

		public List<Float> getWatt_Filter() {
			return Watt_Filter;
		}

		public void setWatt_Filter(List<Float> watt_Filter) {
			Watt_Filter = watt_Filter;
		}

		public List<Float> getRPM_Filter() {
			return RPM_Filter;
		}

		public void setRPM_Filter(List<Float> rPM_Filter) {
			RPM_Filter = rPM_Filter;
		}

		public float getUpstrokeIMax_Filter() {
			return UpstrokeIMax_Filter;
		}

		public void setUpstrokeIMax_Filter(float upstrokeIMax_Filter) {
			UpstrokeIMax_Filter = upstrokeIMax_Filter;
		}

		public float getDownstrokeIMax_Filter() {
			return DownstrokeIMax_Filter;
		}

		public void setDownstrokeIMax_Filter(float downstrokeIMax_Filter) {
			DownstrokeIMax_Filter = downstrokeIMax_Filter;
		}

		public float getUpstrokeWattMax_Filter() {
			return UpstrokeWattMax_Filter;
		}

		public void setUpstrokeWattMax_Filter(float upstrokeWattMax_Filter) {
			UpstrokeWattMax_Filter = upstrokeWattMax_Filter;
		}

		public float getDownstrokeWattMax_Filter() {
			return DownstrokeWattMax_Filter;
		}

		public void setDownstrokeWattMax_Filter(float downstrokeWattMax_Filter) {
			DownstrokeWattMax_Filter = downstrokeWattMax_Filter;
		}

		public float getIDegreeBalance_Filter() {
			return IDegreeBalance_Filter;
		}

		public void setIDegreeBalance_Filter(float iDegreeBalance_Filter) {
			IDegreeBalance_Filter = iDegreeBalance_Filter;
		}

		public float getWattDegreeBalance_Filter() {
			return WattDegreeBalance_Filter;
		}

		public void setWattDegreeBalance_Filter(float wattDegreeBalance_Filter) {
			WattDegreeBalance_Filter = wattDegreeBalance_Filter;
		}

		public int getResultStatus() {
			return ResultStatus;
		}

		public void setResultStatus(int resultStatus) {
			ResultStatus = resultStatus;
		}

		public float getMotorInputAvgWatt() {
			return MotorInputAvgWatt;
		}

		public void setMotorInputAvgWatt(float motorInputAvgWatt) {
			MotorInputAvgWatt = motorInputAvgWatt;
		}

		public float getMaxF() {
			return MaxF;
		}

		public void setMaxF(float maxF) {
			MaxF = maxF;
		}

		public float getMinF() {
			return MinF;
		}

		public void setMinF(float minF) {
			MinF = minF;
		}

		public List<Float> getF360() {
			return F360;
		}

		public void setF360(List<Float> f360) {
			F360 = f360;
		}

		public List<Float> getS360() {
			return S360;
		}

		public void setS360(List<Float> s360) {
			S360 = s360;
		}

		public List<Float> getA360() {
			return A360;
		}

		public void setA360(List<Float> a360) {
			A360 = a360;
		}

		public float getSignal() {
			return Signal;
		}

		public void setSignal(float signal) {
			Signal = signal;
		}

		public int getInterval2() {
			return Interval2;
		}

		public void setInterval2(int interval2) {
			Interval2 = interval2;
		}

	}
	
	public static class TransferDaily{
		
		private String WellName;
		
		private String ID;
		
		private String Date;
		
		private int ResultCode;
		
		private List<Integer> ResultCodes;
		
		private String ResultStr="";

	    private boolean RunStatus;

	    private boolean CommStatus;

	    private TimeEfficiency RunEfficiency;

	    private TimeEfficiency CommEfficiency;

	    private MaxMinAvg Signal;

	    private Energy Energy;

	    private ABCMaxMinAvg V;

	    private ABCMaxMinAvg I;
	    
	    private MaxMinAvg SPM;
	    
	    private MaxMinAvg F;
	    
	    private MaxMinAvg F360;
	    
	    private MaxMinAvg IDegreeBalance;
	    
	    private MaxMinAvg WattDegreeBalance;
	    
		public static class Range
		{
		    private String StartTime;

		    private String EndTime;

		    public void setStartTime(String StartTime){
		        this.StartTime = StartTime;
		    }
		    public String getStartTime(){
		        return this.StartTime;
		    }
		    public void setEndTime(String EndTime){
		        this.EndTime = EndTime;
		    }
		    public String getEndTime(){
		        return this.EndTime;
		    }
		}
		
		public static class TimeEfficiency
		{
		    private float Time;

		    private float Efficiency;

		    private List<Range> Range;

		    private String RangeString;

		    public void setTime(float Time){
		        this.Time = Time;
		    }
		    public float getTime(){
		        return this.Time;
		    }
		    public void setEfficiency(float Efficiency){
		        this.Efficiency = Efficiency;
		    }
		    public float getEfficiency(){
		        return this.Efficiency;
		    }
		    public void setRange(List<Range> Range){
		        this.Range = Range;
		    }
		    public List<Range> getRange(){
		        return this.Range;
		    }
		    public void setRangeString(String RangeString){
		        this.RangeString = RangeString;
		    }
		    public String getRangeString(){
		        return this.RangeString;
		    }
		}
		
		public static class MaxMinAvg
		{
		    private float Max;

		    private float Min;

		    private float Avg;

		    public void setMax(float Max){
		        this.Max = Max;
		    }
		    public float getMax(){
		        return this.Max;
		    }
		    public void setMin(float Min){
		        this.Min = Min;
		    }
		    public float getMin(){
		        return this.Min;
		    }
		    public void setAvg(float Avg){
		        this.Avg = Avg;
		    }
		    public float getAvg(){
		        return this.Avg;
		    }
		}
		
		public static class Energy
		{
		    private float Watt;

		    private float PWatt;

		    private float NWatt;

		    private float Var;

		    private float PVar;

		    private float NVar;

		    private float VA;

		    public void setWatt(float Watt){
		        this.Watt = Watt;
		    }
		    public float getWatt(){
		        return this.Watt;
		    }
		    public void setPWatt(float PWatt){
		        this.PWatt = PWatt;
		    }
		    public float getPWatt(){
		        return this.PWatt;
		    }
		    public void setNWatt(float NWatt){
		        this.NWatt = NWatt;
		    }
		    public float getNWatt(){
		        return this.NWatt;
		    }
		    public void setVar(float Var){
		        this.Var = Var;
		    }
		    public float getVar(){
		        return this.Var;
		    }
		    public void setPVar(float PVar){
		        this.PVar = PVar;
		    }
		    public float getPVar(){
		        return this.PVar;
		    }
		    public void setNVar(float NVar){
		        this.NVar = NVar;
		    }
		    public float getNVar(){
		        return this.NVar;
		    }
		    public void setVA(float VA){
		        this.VA = VA;
		    }
		    public float getVA(){
		        return this.VA;
		    }
		}
		
		public static class ABCMaxMinAvg
		{
		    private MaxMinAvg A;

		    private MaxMinAvg B;

		    private MaxMinAvg C;

			public MaxMinAvg getA() {
				return A;
			}

			public void setA(MaxMinAvg a) {
				A = a;
			}

			public MaxMinAvg getB() {
				return B;
			}

			public void setB(MaxMinAvg b) {
				B = b;
			}

			public MaxMinAvg getC() {
				return C;
			}

			public void setC(MaxMinAvg c) {
				C = c;
			}
		}

		
		public String getDate() {
			return Date;
		}

		public void setDate(String date) {
			Date = date;
		}

		public boolean getRunStatus() {
			return RunStatus;
		}

		public void setRunStatus(boolean runStatus) {
			RunStatus = runStatus;
		}

		public boolean getCommStatus() {
			return CommStatus;
		}

		public void setCommStatus(boolean commStatus) {
			CommStatus = commStatus;
		}

		public TimeEfficiency getRunEfficiency() {
			return RunEfficiency;
		}

		public void setRunEfficiency(TimeEfficiency runEfficiency) {
			RunEfficiency = runEfficiency;
		}

		public TimeEfficiency getCommEfficiency() {
			return CommEfficiency;
		}

		public void setCommEfficiency(TimeEfficiency commEfficiency) {
			CommEfficiency = commEfficiency;
		}

		public MaxMinAvg getSignal() {
			return Signal;
		}

		public void setSignal(MaxMinAvg signal) {
			Signal = signal;
		}

		public Energy getEnergy() {
			return Energy;
		}

		public void setEnergy(Energy energy) {
			Energy = energy;
		}

		public ABCMaxMinAvg getV() {
			return V;
		}

		public void setV(ABCMaxMinAvg v) {
			V = v;
		}

		public ABCMaxMinAvg getI() {
			return I;
		}

		public void setI(ABCMaxMinAvg i) {
			I = i;
		}

		public String getID() {
			return ID;
		}

		public void setID(String iD) {
			ID = iD;
		}

		public String getWellName() {
			return WellName;
		}

		public void setWellName(String wellName) {
			WellName = wellName;
		}

		public int getResultCode() {
			return ResultCode;
		}

		public void setResultCode(int resultCode) {
			ResultCode = resultCode;
		}

		public List<Integer> getResultCodes() {
			return ResultCodes;
		}

		public void setResultCodes(List<Integer> resultCodes) {
			ResultCodes = resultCodes;
		}

		public MaxMinAvg getSPM() {
			return SPM;
		}

		public void setSPM(MaxMinAvg sPM) {
			SPM = sPM;
		}

		public MaxMinAvg getF() {
			return F;
		}

		public void setF(MaxMinAvg f) {
			F = f;
		}

		public MaxMinAvg getF360() {
			return F360;
		}

		public void setF360(MaxMinAvg f360) {
			F360 = f360;
		}

		public MaxMinAvg getIDegreeBalance() {
			return IDegreeBalance;
		}

		public void setIDegreeBalance(MaxMinAvg iDegreeBalance) {
			IDegreeBalance = iDegreeBalance;
		}

		public MaxMinAvg getWattDegreeBalance() {
			return WattDegreeBalance;
		}

		public void setWattDegreeBalance(MaxMinAvg wattDegreeBalance) {
			WattDegreeBalance = wattDegreeBalance;
		}

		public String getResultStr() {
			return ResultStr;
		}

		public void setResultStr(String resultStr) {
			ResultStr = resultStr;
		}

	}
	
	public static class WellConfigInfo{
		private String wellName;
		private String driverAddr;
		private int acqCycle_Diagram;
		private int acqCycleSetStatus_diagram;
		private int acqCycle_Discrete;
		private int acqCycleSetStatus_discrete;
		
		public String getWellName() {
			return wellName;
		}
		public void setWellName(String wellName) {
			this.wellName = wellName;
		}
		public String getDriverAddr() {
			return driverAddr;
		}
		public void setDriverAddr(String driverAddr) {
			this.driverAddr = driverAddr;
		}
		public int getAcqCycle_Diagram() {
			return acqCycle_Diagram;
		}
		public void setAcqCycle_Diagram(int acqCycle_Diagram) {
			this.acqCycle_Diagram = acqCycle_Diagram;
		}
		public int getAcqCycle_Discrete() {
			return acqCycle_Discrete;
		}
		public void setAcqCycle_Discrete(int acqCycle_Discrete) {
			this.acqCycle_Discrete = acqCycle_Discrete;
		}
		public int getAcqCycleSetStatus_diagram() {
			return acqCycleSetStatus_diagram;
		}
		public void setAcqCycleSetStatus_diagram(int acqCycleSetStatus_diagram) {
			this.acqCycleSetStatus_diagram = acqCycleSetStatus_diagram;
		}
		public int getAcqCycleSetStatus_discrete() {
			return acqCycleSetStatus_discrete;
		}
		public void setAcqCycleSetStatus_discrete(int acqCycleSetStatus_discrete) {
			this.acqCycleSetStatus_discrete = acqCycleSetStatus_discrete;
		}
		
	}
}
