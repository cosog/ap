package com.gao.task;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.gao.model.drive.KafkaConfig;
import com.gao.utils.Config;
import com.gao.utils.EquipmentDriveMap;
import com.gao.utils.StringManagerUtils;
import com.gao.websocket.config.WebSocketByJavax;
import com.gao.websocket.handler.SpringWebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("KafkaServerTast")  
public class KafkaServerTask {
	public static  String HOST ="";
    public static  String[] TOPIC = {"Up-NormData","Up-RawData","Up-Config","Up-Model","Up-Freq","Up-RTC","Up-Online","Up-RunStatus"};
    private static  String clientid = "apKafkaClient"+new Date().getTime();
    @SuppressWarnings("unused")
	private ScheduledExecutorService scheduler;
	
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	@SuppressWarnings("deprecation")
	public void runKafkaServer() {
		clientid = "apKafkaClient"+new Date().getTime();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.initDriverConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		KafkaConfig driveConfig=(KafkaConfig)equipmentDriveMap.get("KafkaDrive");
		if(driveConfig==null){
			Gson gson = new Gson();
			StringManagerUtils stringManagerUtils=new StringManagerUtils();
			String path=stringManagerUtils.getFilePath("KafkaDriverConfig.json","protocolConfig/");
			String driverConfigData=stringManagerUtils.readFile(path,"utf-8");
			java.lang.reflect.Type type = new TypeToken<KafkaConfig>() {}.getType();
			driveConfig=gson.fromJson(driverConfigData, type);
		}
		if(driveConfig!=null){
			HOST=driveConfig.getServer().getIP()+":"+driveConfig.getServer().getPort();
			TOPIC=new String[8];
			TOPIC[0]=driveConfig.getTopic().getUp().getNormData();
			TOPIC[1]=driveConfig.getTopic().getUp().getRawData();
			TOPIC[2]=driveConfig.getTopic().getUp().getConfig();
			TOPIC[3]=driveConfig.getTopic().getUp().getModel();
			TOPIC[4]=driveConfig.getTopic().getUp().getFreq();
			TOPIC[5]=driveConfig.getTopic().getUp().getRTC();
			TOPIC[6]=driveConfig.getTopic().getUp().getOnline();
			TOPIC[7]=driveConfig.getTopic().getUp().getRunStatus();
		}
		
		Properties props = new Properties();
	    props.put("bootstrap.servers", HOST);
	    props.put("group.id", clientid);
	    props.put("enable.auto.commit", "true");
	    props.put("auto.commit.interval.ms", "1000");
	    props.put("auto.offset.reset", "latest");//latest,earliest,none
	    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    final KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);
	    consumer.subscribe(Arrays.asList(TOPIC),new ConsumerRebalanceListener() {
	        public void onPartitionsRevoked(Collection<TopicPartition> collection) {
	        }
	        public void onPartitionsAssigned(Collection<TopicPartition> collection) {
	            //将偏移设置到最开始
//	            consumer.seekToBeginning(collection);
	        	//将偏移设置到最后
	        	consumer.seekToEnd(collection);
	        }
	    });
	    while (true) {
	        ConsumerRecords<String, String> records = consumer.poll(100);
	        for (ConsumerRecord<String, String> record : records){
	        	KafkaDataAnalysisThread kafkaDataAnalysisThread=new KafkaDataAnalysisThread(record);
	        	kafkaDataAnalysisThread.start();
	        }
	            
	    }
	}
	
	public static void producerMsg(String topic,String title,String value){
		if(StringManagerUtils.isNotNull(HOST)){
			System.out.println("Kafka下行，topic："+topic+",title:"+title+",value:"+value);
			Properties props = new Properties();
		    props.put("bootstrap.servers", HOST);
		    //The "all" setting we have specified will result in blocking on the full commit of the record, the slowest but most durable setting.
	        //“所有”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。
		    props.put("acks", "all");
		    //如果请求失败，生产者也会自动重试，即使设置成0
		    props.put("retries", 0);
		    props.put("batch.size", 16384);
		    //默认立即发送，这里这是延时毫秒数
		    props.put("linger.ms", 1);
		    //生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
		    props.put("buffer.memory", 33554432);
		    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		    Producer<String, String> producer = new KafkaProducer<String, String>(props);
		    producer.send( new ProducerRecord<String, String>(topic, title, value) );
		    producer.close();
		}
	}
	
	@Bean
    public static SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }
	@Bean
    public static WebSocketByJavax infoHandler2() {
        return new WebSocketByJavax();
    }
	
	public static class KafkaDataAnalysisThread extends Thread{
		private ConsumerRecord<String, String> record;
		public KafkaDataAnalysisThread(ConsumerRecord<String, String> record) {
			super();
			this.record = record;
		}
		public void run(){
			System.out.println("topic:"+record.topic()+",offset = "+record.offset()+", key = "+record.key()+", value = "+record.value());
			Gson gson = new Gson();
			String saveDataUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/graphicalUploadController/saveKafkaUpData";
			String saveRawDataUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/graphicalUploadController/saveKafkaUpRawData";
			String saveAggrOnlineDataUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/graphicalUploadController/saveKafkaUpAggrOnlineData";
			String saveAggrRunStatusDataUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/graphicalUploadController/saveKafkaUpAggrRunStatusData";
			String UpNormDataTopic="Up-NormData";
			String UpRawDataTopic="Up-RawData";
			String UpConfigTopic="Up-Config";
			String UpModelTopic="Up-Model";
			String UpFreqTopic="Up-Freq";
			String UpRTCTopic="Up-RTC";
			String UpOnlineTopic="Up-Online";
			String UpRunStatusTopic="Up-RunStatus";
			Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
			if(equipmentDriveMap.size()==0){
				EquipmentDriverServerTask.initDriverConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			KafkaConfig driveConfig=(KafkaConfig)equipmentDriveMap.get("KafkaDrive");
			if(driveConfig==null){
				StringManagerUtils stringManagerUtils=new StringManagerUtils();
				String path=stringManagerUtils.getFilePath("KafkaDriverConfig.json","protocolConfig/");
				String driverConfigData=stringManagerUtils.readFile(path,"utf-8");
				java.lang.reflect.Type type = new TypeToken<KafkaConfig>() {}.getType();
				driveConfig=gson.fromJson(driverConfigData, type);
			}
			if(driveConfig!=null){
				UpNormDataTopic=driveConfig.getTopic().getUp().getNormData();
				UpRawDataTopic=driveConfig.getTopic().getUp().getRawData();
				UpConfigTopic=driveConfig.getTopic().getUp().getConfig();
				UpModelTopic=driveConfig.getTopic().getUp().getModel();
				UpFreqTopic=driveConfig.getTopic().getUp().getFreq();
				UpRTCTopic=driveConfig.getTopic().getUp().getRTC();
				UpOnlineTopic=driveConfig.getTopic().getUp().getOnline();
				UpRunStatusTopic=driveConfig.getTopic().getUp().getRunStatus();
			}
			
			if(UpNormDataTopic.equalsIgnoreCase(record.topic())){
        		java.lang.reflect.Type type = new TypeToken<KafkaUpData>() {}.getType();
        		KafkaUpData kafkaUpData=gson.fromJson(record.value(), type);
        		if(kafkaUpData!=null){
        			try {
        				String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            			//如果时间差达到半小时，校正时间
            			long diffTime=Math.abs(format.parse(currentTime).getTime()/1000-format.parse(kafkaUpData.getSysTime()).getTime()/1000);
						if(diffTime>60*30){
							System.out.println("设备ID:"+record.key()+",系统时间差距大于半小时，校正时间。currentTime:"+currentTime+",deviceSysTime:"+kafkaUpData.getSysTime()+",时间差:"+diffTime+"秒");
							kafkaUpData.setAcqTime(currentTime);
							//下行时间
							String topic="Down-"+record.key()+"-RTC";
							KafkaServerTask.producerMsg(topic, "下行时钟-"+record.key(), currentTime);
						}
						
						if(!StringManagerUtils.isNotNull(kafkaUpData.getAcqTime())){
							kafkaUpData.setAcqTime(currentTime);
						}
						
						long devAcqAndSysDiffTime=Math.abs(format.parse(kafkaUpData.getAcqTime()).getTime()/1000-format.parse(kafkaUpData.getSysTime()).getTime()/1000);
						kafkaUpData.setKey(record.key());
	        			StringManagerUtils.sendPostMethod(saveDataUrl, gson.toJson(kafkaUpData),"utf-8");	
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(record.value());
					}
        		}else{
        			System.out.println("接收到"+record.key()+"设备无效上传数据:"+record.value());
        		}
        	}else if(UpRawDataTopic.equalsIgnoreCase(record.topic())){//原始数据
        		java.lang.reflect.Type type = new TypeToken<KafkaUpRawData>() {}.getType();
        		KafkaUpRawData kafkaUpRawData=gson.fromJson(record.value(), type);
        		if(kafkaUpRawData!=null){
        			try {
        				String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            			//如果时间差达到半小时，校正时间
            			long diffTime=Math.abs(format.parse(currentTime).getTime()/1000-format.parse(kafkaUpRawData.getSysTime()).getTime()/1000);
						if(diffTime>60*30){
							System.out.println("设备ID:"+record.key()+",系统时间差距大于半小时，校正时间。currentTime:"+currentTime+",deviceSysTime:"+kafkaUpRawData.getSysTime()+",时间差:"+diffTime+"秒");
							kafkaUpRawData.setAcqTime(currentTime);
							String topic="Down-"+record.key()+"-RTC";
							KafkaServerTask.producerMsg(topic, "下行时钟-"+record.key(), currentTime);
						}
						if(StringManagerUtils.isNotNull(kafkaUpRawData.getAcqTime())){
							long devAcqAndSysDiffTime=Math.abs(format.parse(kafkaUpRawData.getAcqTime()).getTime()/1000-format.parse(kafkaUpRawData.getSysTime()).getTime()/1000);
							kafkaUpRawData.setKey(record.key());
		        			StringManagerUtils.sendPostMethod(saveRawDataUrl, gson.toJson(kafkaUpRawData),"utf-8");
						}else{
							System.out.println("接收到"+record.key()+"设备无效上传数据:"+record.value());
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(record.value());
					}
        		}
        	}else if(UpConfigTopic.equalsIgnoreCase(record.topic())){
        		String sendData="1##"+record.key()+"##"+StringManagerUtils.jsonStringFormat(record.value());
        		try {
        			infoHandler().sendMessageToUserByModule("kafkaConfig_kafkaConfigGridPanel", new TextMessage(sendData));
					infoHandler2().sendMessageToBy("kafkaConfig_kafkaConfigGridPanel", sendData);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}else if(UpModelTopic.equalsIgnoreCase(record.topic())){
        		String sendData="7##"+record.key()+"##"+StringManagerUtils.jsonStringFormat(record.value());
        		try {
        			infoHandler().sendMessageToUserByModule("kafkaConfig_kafkaConfigGridPanel", new TextMessage(sendData));
					infoHandler2().sendMessageToBy("kafkaConfig_kafkaConfigGridPanel", sendData);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}else if(UpFreqTopic.equalsIgnoreCase(record.topic())){
        		String sendData="5##"+record.key()+"##"+record.value();
        		try {
        			infoHandler().sendMessageToUserByModule("kafkaConfig_kafkaConfigGridPanel", new TextMessage(sendData));
					infoHandler2().sendMessageToBy("kafkaConfig_kafkaConfigGridPanel", sendData);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}else if(UpRTCTopic.equalsIgnoreCase(record.topic())){
        		String sendData="6##"+record.key()+"##"+record.value();
        		try {
        			infoHandler().sendMessageToUserByModule("kafkaConfig_kafkaConfigGridPanel", new TextMessage(sendData));
					infoHandler2().sendMessageToBy("kafkaConfig_kafkaConfigGridPanel", sendData);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}else if(UpOnlineTopic.equalsIgnoreCase(record.topic())){//通信状态  设备启动后上传一次
        		//上线数据
        		java.lang.reflect.Type type = new TypeToken<AggrOnline2Kafka>() {}.getType();
        		AggrOnline2Kafka aggrOnline2Kafka=gson.fromJson(record.value(), type);
        		if(aggrOnline2Kafka!=null){
        			try {
        				String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            			//如果时间差达到半小时，校正时间
            			long diffTime=Math.abs(format.parse(currentTime).getTime()/1000-format.parse(aggrOnline2Kafka.getSysTime()).getTime()/1000);
						if(diffTime>60*30){
							System.out.println("设备ID:"+record.key()+",系统时间差距大于半小时，校正时间。currentTime:"+currentTime+",deviceSysTime:"+aggrOnline2Kafka.getSysTime()+",时间差:"+diffTime+"秒");
							String topic="Down-"+record.key()+"-RTC";
							KafkaServerTask.producerMsg(topic, "下行时钟-"+record.key(), currentTime);
						}
						aggrOnline2Kafka.setKey(record.key());
	        			StringManagerUtils.sendPostMethod(saveAggrOnlineDataUrl, gson.toJson(aggrOnline2Kafka),"utf-8");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(record.value());
					}
        		}
        	}else if(UpRunStatusTopic.equalsIgnoreCase(record.topic())){//运行状态发生改变，设备上报
        		//运行状态数据
        		java.lang.reflect.Type type = new TypeToken<AggrRunStatus2Kafka>() {}.getType();
        		AggrRunStatus2Kafka aggrRunStatus2Kafka=gson.fromJson(record.value(), type);
        		if(aggrRunStatus2Kafka!=null){
        			try {
        				String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            			//如果时间差达到半小时，校正时间
            			long diffTime=Math.abs(format.parse(currentTime).getTime()/1000-format.parse(aggrRunStatus2Kafka.getSysTime()).getTime()/1000);
						if(diffTime>60*30){
							System.out.println("设备ID:"+record.key()+",系统时间差距大于半小时，校正时间。currentTime:"+currentTime+",deviceSysTime:"+aggrRunStatus2Kafka.getSysTime()+",时间差:"+diffTime+"秒");
							//下行时间
							String topic="Down-"+record.key()+"-RTC";
							KafkaServerTask.producerMsg(topic, "下行时钟-"+record.key(), currentTime);
						}
						aggrRunStatus2Kafka.setKey(record.key());
	        			StringManagerUtils.sendPostMethod(saveAggrRunStatusDataUrl, gson.toJson(aggrRunStatus2Kafka),"utf-8");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(record.value());
					}
        		}
        	}
		}

		public ConsumerRecord<String, String> getRecord() {
			return record;
		}

		public void setRecord(ConsumerRecord<String, String> record) {
			this.record = record;
		}
	}

	public static class KafkaUpData
	{
		private String Ver;
		
		private String SysTime;
		
		private String AcqTime;
		
		private Integer Signal;
	    
	    private String Key;
	    
	    private int TransferIntervel;
	    
	    private Integer wellId;
	    
	    private String wellName;
	    
	    private Integer ProdDataId;

	    private float Stroke;

	    private float SPM;

	    private List<Float> S;

	    private List<Float> F;

	    private List<Float> Watt;

	    private List<Float> I;

	    private float WaterCut;

	    private List<Float> WaterCutDiagram;

	    private float TubingPressure;

	    private float CasingPressure;

	    private float WellHeadFluidTemperature;

	    private float ProducingfluidLevel;

	    private List<Float> OriginalLevelDiagram;

	    private List<Float> FilteringLevelDiagram;

	    private float Freq;

	    private int ResultCode;

	    private TotalEnergy TotalEnergy;

	    private TodayEnergy TodayEnergy;

	    private boolean RunStatus;

	    private RunEfficiency RunEfficiency;

	    private boolean CommStatus;

	    private CommEfficiency CommEfficiency;
	    
	    private float IA;//A相电流					安培
	    private float IB;//B相电流					安培
	    private float IC;//C相电流					安培
	    private float VA;//A相电压					伏
	    private float VB;//B相电压					伏
	    private float VC;//C相电压					伏
	    private float Watt3;//有功功率					kW
	    private float Var3;//无功功率					kVar
	    private float VA3;//视在功率					kVA
	    private float PF3;//功率因数					小数

	    public void setAcqTime(String AcqTime){
	        this.AcqTime = AcqTime;
	    }
	    public String getAcqTime(){
	        return this.AcqTime;
	    }
	    public void setStroke(float Stroke){
	        this.Stroke = Stroke;
	    }
	    public float getStroke(){
	        return this.Stroke;
	    }
	    public void setSPM(float SPM){
	        this.SPM = SPM;
	    }
	    public float getSPM(){
	        return this.SPM;
	    }
	    public void setS(List<Float> S){
	        this.S = S;
	    }
	    public List<Float> getS(){
	        return this.S;
	    }
	    public void setF(List<Float> F){
	        this.F = F;
	    }
	    public List<Float> getF(){
	        return this.F;
	    }
	    public void setWatt(List<Float> Watt){
	        this.Watt = Watt;
	    }
	    public List<Float> getWatt(){
	        return this.Watt;
	    }
	    public void setI(List<Float> I){
	        this.I = I;
	    }
	    public List<Float> getI(){
	        return this.I;
	    }
	    public void setWaterCut(float WaterCut){
	        this.WaterCut = WaterCut;
	    }
	    public float getWaterCut(){
	        return this.WaterCut;
	    }
	    public void setWaterCutDiagram(List<Float> WaterCutDiagram){
	        this.WaterCutDiagram = WaterCutDiagram;
	    }
	    public List<Float> getWaterCutDiagram(){
	        return this.WaterCutDiagram;
	    }
	    public void setTubingPressure(float TubingPressure){
	        this.TubingPressure = TubingPressure;
	    }
	    public float getTubingPressure(){
	        return this.TubingPressure;
	    }
	    public void setCasingPressure(float CasingPressure){
	        this.CasingPressure = CasingPressure;
	    }
	    public float getCasingPressure(){
	        return this.CasingPressure;
	    }
	    public void setWellHeadFluidTemperature(float WellHeadFluidTemperature){
	        this.WellHeadFluidTemperature = WellHeadFluidTemperature;
	    }
	    public float getWellHeadFluidTemperature(){
	        return this.WellHeadFluidTemperature;
	    }
	    public void setProducingfluidLevel(float ProducingfluidLevel){
	        this.ProducingfluidLevel = ProducingfluidLevel;
	    }
	    public float getProducingfluidLevel(){
	        return this.ProducingfluidLevel;
	    }
	    public void setOriginalLevelDiagram(List<Float> OriginalLevelDiagram){
	        this.OriginalLevelDiagram = OriginalLevelDiagram;
	    }
	    public List<Float> getOriginalLevelDiagram(){
	        return this.OriginalLevelDiagram;
	    }
	    public void setFilteringLevelDiagram(List<Float> FilteringLevelDiagram){
	        this.FilteringLevelDiagram = FilteringLevelDiagram;
	    }
	    public List<Float> getFilteringLevelDiagram(){
	        return this.FilteringLevelDiagram;
	    }
	    public void setFreq(float Freq){
	        this.Freq = Freq;
	    }
	    public float getFreq(){
	        return this.Freq;
	    }
	    public void setResultCode(int ResultCode){
	        this.ResultCode = ResultCode;
	    }
	    public int getResultCode(){
	        return this.ResultCode;
	    }
	    public void setTotalEnergy(TotalEnergy TotalEnergy){
	        this.TotalEnergy = TotalEnergy;
	    }
	    public TotalEnergy getTotalEnergy(){
	        return this.TotalEnergy;
	    }
	    public void setTodayEnergy(TodayEnergy TodayEnergy){
	        this.TodayEnergy = TodayEnergy;
	    }
	    public TodayEnergy getTodayEnergy(){
	        return this.TodayEnergy;
	    }
	    public void setRunStatus(boolean RunStatus){
	        this.RunStatus = RunStatus;
	    }
	    public boolean getRunStatus(){
	        return this.RunStatus;
	    }
	    public void setRunEfficiency(RunEfficiency RunEfficiency){
	        this.RunEfficiency = RunEfficiency;
	    }
	    public RunEfficiency getRunEfficiency(){
	        return this.RunEfficiency;
	    }
	    public void setCommStatus(boolean CommStatus){
	        this.CommStatus = CommStatus;
	    }
	    public boolean getCommStatus(){
	        return this.CommStatus;
	    }
	    public void setCommEfficiency(CommEfficiency CommEfficiency){
	        this.CommEfficiency = CommEfficiency;
	    }
	    public CommEfficiency getCommEfficiency(){
	        return this.CommEfficiency;
	    }
		public String getKey() {
			return Key;
		}
		public void setKey(String key) {
			Key = key;
		}
		public String getWellName() {
			return wellName;
		}
		public void setWellName(String wellName) {
			this.wellName = wellName;
		}
		public Integer getProdDataId() {
			return ProdDataId;
		}
		public void setProdDataId(Integer prodDataId) {
			ProdDataId = prodDataId;
		}
	    public static class TotalEnergy
		{
		    private float KWattH;

		    private float PKWattH;

		    private float NKWattH;

		    private float KVarH;

		    private float PKVarH;

		    private float NKVarH;

		    private float KVAH;

		    public void setKWattH(float KWattH){
		        this.KWattH = KWattH;
		    }
		    public float getKWattH(){
		        return this.KWattH;
		    }
		    public void setPKWattH(float PKWattH){
		        this.PKWattH = PKWattH;
		    }
		    public float getPKWattH(){
		        return this.PKWattH;
		    }
		    public void setNKWattH(float NKWattH){
		        this.NKWattH = NKWattH;
		    }
		    public float getNKWattH(){
		        return this.NKWattH;
		    }
		    public void setKVarH(float KVarH){
		        this.KVarH = KVarH;
		    }
		    public float getKVarH(){
		        return this.KVarH;
		    }
		    public void setPKVarH(float PKVarH){
		        this.PKVarH = PKVarH;
		    }
		    public float getPKVarH(){
		        return this.PKVarH;
		    }
		    public void setNKVarH(float NKVarH){
		        this.NKVarH = NKVarH;
		    }
		    public float getNKVarH(){
		        return this.NKVarH;
		    }
		    public void setKVAH(float KVAH){
		        this.KVAH = KVAH;
		    }
		    public float getKVAH(){
		        return this.KVAH;
		    }
		}
		
		public static class TodayEnergy
		{
		    private float KWattH;

		    private float PKWattH;

		    private float NKWattH;

		    private float KVarH;

		    private float PKVarH;

		    private float NKVarH;

		    private float KVAH;

		    public void setKWattH(float KWattH){
		        this.KWattH = KWattH;
		    }
		    public float getKWattH(){
		        return this.KWattH;
		    }
		    public void setPKWattH(float PKWattH){
		        this.PKWattH = PKWattH;
		    }
		    public float getPKWattH(){
		        return this.PKWattH;
		    }
		    public void setNKWattH(float NKWattH){
		        this.NKWattH = NKWattH;
		    }
		    public float getNKWattH(){
		        return this.NKWattH;
		    }
		    public void setKVarH(float KVarH){
		        this.KVarH = KVarH;
		    }
		    public float getKVarH(){
		        return this.KVarH;
		    }
		    public void setPKVarH(float PKVarH){
		        this.PKVarH = PKVarH;
		    }
		    public float getPKVarH(){
		        return this.PKVarH;
		    }
		    public void setNKVarH(float NKVarH){
		        this.NKVarH = NKVarH;
		    }
		    public float getNKVarH(){
		        return this.NKVarH;
		    }
		    public void setKVAH(float KVAH){
		        this.KVAH = KVAH;
		    }
		    public float getKVAH(){
		        return this.KVAH;
		    }
		}
		public static class RunEfficiency
		{
		    private float Time;

		    private float Efficiency;

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
		    public void setRangeString(String RangeString){
		        this.RangeString = RangeString;
		    }
		    public String getRangeString(){
		        return this.RangeString;
		    }
		}
		
		public static class CommEfficiency
		{
		    private int Time;

		    private int Efficiency;

		    private String RangeString;

		    public void setTime(int Time){
		        this.Time = Time;
		    }
		    public int getTime(){
		        return this.Time;
		    }
		    public void setEfficiency(int Efficiency){
		        this.Efficiency = Efficiency;
		    }
		    public int getEfficiency(){
		        return this.Efficiency;
		    }
		    public void setRangeString(String RangeString){
		        this.RangeString = RangeString;
		    }
		    public String getRangeString(){
		        return this.RangeString;
		    }
		}

		public Integer getWellId() {
			return wellId;
		}
		public void setWellId(Integer wellId) {
			this.wellId = wellId;
		}
		public String getVer() {
			return Ver;
		}
		public void setVer(String ver) {
			Ver = ver;
		}
		public String getSysTime() {
			return SysTime;
		}
		public void setSysTime(String sysTime) {
			SysTime = sysTime;
		}
		public Integer getSignal() {
			return Signal;
		}
		public void setSignal(Integer signal) {
			Signal = signal;
		}
		public int getTransferIntervel() {
			return TransferIntervel;
		}
		public void setTransferIntervel(int transferIntervel) {
			TransferIntervel = transferIntervel;
		}
		public float getIA() {
			return IA;
		}
		public void setIA(float iA) {
			IA = iA;
		}
		public float getIB() {
			return IB;
		}
		public void setIB(float iB) {
			IB = iB;
		}
		public float getIC() {
			return IC;
		}
		public void setIC(float iC) {
			IC = iC;
		}
		public float getVA() {
			return VA;
		}
		public void setVA(float vA) {
			VA = vA;
		}
		public float getVB() {
			return VB;
		}
		public void setVB(float vB) {
			VB = vB;
		}
		public float getVC() {
			return VC;
		}
		public void setVC(float vC) {
			VC = vC;
		}
		public float getWatt3() {
			return Watt3;
		}
		public void setWatt3(float watt3) {
			Watt3 = watt3;
		}
		public float getVar3() {
			return Var3;
		}
		public void setVar3(float var3) {
			Var3 = var3;
		}
		public float getVA3() {
			return VA3;
		}
		public void setVA3(float vA3) {
			VA3 = vA3;
		}
		public float getPF3() {
			return PF3;
		}
		public void setPF3(float pF3) {
			PF3 = pF3;
		}
	}
	public static class KafkaUpRawData
	{
		private String Ver;
		
		private String SysTime;
		
		private String AcqTime;
		
		private Integer Signal;
	    
	    private String Key;
	    
	    private int TransferIntervel;
	    
	    private List<Float> Interval;
	    
	    private List<Float> A;

	    private List<Float> F;

	    private List<Float> Watt;

	    private List<Float> I;

		public String getVer() {
			return Ver;
		}

		public void setVer(String ver) {
			Ver = ver;
		}

		public String getSysTime() {
			return SysTime;
		}

		public void setSysTime(String sysTime) {
			SysTime = sysTime;
		}

		public String getAcqTime() {
			return AcqTime;
		}

		public void setAcqTime(String acqTime) {
			AcqTime = acqTime;
		}

		public Integer getSignal() {
			return Signal;
		}

		public void setSignal(Integer signal) {
			Signal = signal;
		}

		public String getKey() {
			return Key;
		}

		public void setKey(String key) {
			Key = key;
		}

		public List<Float> getInterval() {
			return Interval;
		}

		public void setInterval(List<Float> interval) {
			Interval = interval;
		}

		public List<Float> getA() {
			return A;
		}

		public void setA(List<Float> a) {
			A = a;
		}

		public List<Float> getF() {
			return F;
		}

		public void setF(List<Float> f) {
			F = f;
		}

		public List<Float> getWatt() {
			return Watt;
		}

		public void setWatt(List<Float> watt) {
			Watt = watt;
		}

		public List<Float> getI() {
			return I;
		}

		public void setI(List<Float> i) {
			I = i;
		}

		public int getTransferIntervel() {
			return TransferIntervel;
		}

		public void setTransferIntervel(int transferIntervel) {
			TransferIntervel = transferIntervel;
		}
	}
	
	public static class AggrOnline2Kafka
	{
	    private String Ver;

	    private String SysTime;
	    
	    private String Key;
	    
	    private String WellName;

	    private int Signal;

	    private int TransferIntervel;

	    private boolean CommStatus;

	    public void setVer(String Ver){
	        this.Ver = Ver;
	    }
	    public String getVer(){
	        return this.Ver;
	    }
	    public void setSysTime(String SysTime){
	        this.SysTime = SysTime;
	    }
	    public String getSysTime(){
	        return this.SysTime;
	    }
	    public void setSignal(int Signal){
	        this.Signal = Signal;
	    }
	    public int getSignal(){
	        return this.Signal;
	    }
	    public void setTransferIntervel(int TransferIntervel){
	        this.TransferIntervel = TransferIntervel;
	    }
	    public int getTransferIntervel(){
	        return this.TransferIntervel;
	    }
	    public void setCommStatus(boolean CommStatus){
	        this.CommStatus = CommStatus;
	    }
	    public boolean getCommStatus(){
	        return this.CommStatus;
	    }
		public String getKey() {
			return Key;
		}
		public void setKey(String key) {
			Key = key;
		}
		public String getWellName() {
			return WellName;
		}
		public void setWellName(String wellName) {
			WellName = wellName;
		}
	}
	
	public static class AggrRunStatus2Kafka
	{
	    private String Ver;

	    private String SysTime;
	    
	    private String Key;
	    
	    private String WellName;

	    private int Signal;

	    private int TransferIntervel;

	    private boolean RunStatus;

	    public void setVer(String Ver){
	        this.Ver = Ver;
	    }
	    public String getVer(){
	        return this.Ver;
	    }
	    public void setSysTime(String SysTime){
	        this.SysTime = SysTime;
	    }
	    public String getSysTime(){
	        return this.SysTime;
	    }
	    public void setSignal(int Signal){
	        this.Signal = Signal;
	    }
	    public int getSignal(){
	        return this.Signal;
	    }
	    public void setTransferIntervel(int TransferIntervel){
	        this.TransferIntervel = TransferIntervel;
	    }
	    public int getTransferIntervel(){
	        return this.TransferIntervel;
	    }
	    public void setRunStatus(boolean RunStatus){
	        this.RunStatus = RunStatus;
	    }
	    public boolean getRunStatus(){
	        return this.RunStatus;
	    }
		public String getKey() {
			return Key;
		}
		public void setKey(String key) {
			Key = key;
		}
		public String getWellName() {
			return WellName;
		}
		public void setWellName(String wellName) {
			WellName = wellName;
		}
	}
}
