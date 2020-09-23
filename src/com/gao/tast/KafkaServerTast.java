package com.gao.tast;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gao.utils.Config;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Component("KafkaServerTast")  
public class KafkaServerTast {
	public static final String HOST =Config.getInstance().configFile.getKafka().getServer();//"39.98.64.56:9092";
    public static final String[] TOPIC = {"Up-Data","Up-Config","Up-Model"};
    private static final String clientid = "apKafkaClient"+new Date().getTime();
    @SuppressWarnings("unused")
	private ScheduledExecutorService scheduler;
	
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void runKafkaServer() {
		Properties props = new Properties();
	    props.put("bootstrap.servers", HOST);
	    props.put("group.id", clientid);
	    props.put("enable.auto.commit", "true");
	    props.put("auto.commit.interval.ms", "1000");
	    
	    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    final KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);
	    consumer.subscribe(Arrays.asList(TOPIC),new ConsumerRebalanceListener() {
	        public void onPartitionsRevoked(Collection<TopicPartition> collection) {
	        }
	        public void onPartitionsAssigned(Collection<TopicPartition> collection) {
	            //将偏移设置到最开始
	            consumer.seekToBeginning(collection);
	        }
	    });
	    while (true) {
	        ConsumerRecords<String, String> records = consumer.poll(100);
	        for (ConsumerRecord<String, String> record : records){
//	        	System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
//	        	System.out.println("topic:"+record.topic()+",offset = "+record.offset()+", key = "+record.key()+", value = "+record.value());
//	        	if("Up-Data".equalsIgnoreCase(record.topic())){
//	        		
//	        	}else if("Up-Config".equalsIgnoreCase(record.topic())){
//	        		
//	        	}else if("Up-Model".equalsIgnoreCase(record.topic())){
//	        		
//	        	}
	        	KafkaDataAnalysisThread kafkaDataAnalysisThread=new KafkaDataAnalysisThread(record);
	        	kafkaDataAnalysisThread.start();
	        }
	            
	    }
	}
	
	public static void producerMsg(String topic,String title,String value){
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
			
        	if("Up-Data".equalsIgnoreCase(record.topic())){
        		java.lang.reflect.Type type = new TypeToken<KafkaUpData>() {}.getType();
        		KafkaUpData kafkaUpData=gson.fromJson(record.value(), type);
        		if(kafkaUpData!=null){
        			try {
        				String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            			//如果时间差达到半小时，校正时间
						if(Math.abs(format.parse(currentTime).getTime()/1000-format.parse(kafkaUpData.getAcqTime()).getTime()/1000)>60*30){
							kafkaUpData.setAcqTime(currentTime);
							//下行时间
							String topic="Down-"+record.key()+"-RTC";
							KafkaServerTast.producerMsg(topic, "下行时钟-"+record.key(), currentTime);
						}
						kafkaUpData.setKey(record.key());
	        			StringManagerUtils.sendPostMethod(saveDataUrl, gson.toJson(kafkaUpData),"utf-8");
					} catch (ParseException e) {
						e.printStackTrace();
					}
        			
        			
        			
        			
        			
        		}
        	}else if("Up-Config".equalsIgnoreCase(record.topic())){
        		
        	}else if("Up-Model".equalsIgnoreCase(record.topic())){
        		
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
	    private String AcqTime;
	    
	    private String Key;
	    
	    private Integer wellId;
	    
	    private String wellName;
	    
	    private Integer ProdDataId;

	    private float Stroke;

	    private float SPM;

	    private List<Float> S;

	    private List<Float> F;

	    private List<Float> KWatt;

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
	    public void setKWatt(List<Float> KWatt){
	        this.KWatt = KWatt;
	    }
	    public List<Float> getKWatt(){
	        return this.KWatt;
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
	}
}
