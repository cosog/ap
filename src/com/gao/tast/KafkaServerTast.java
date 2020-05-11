package com.gao.tast;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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


@Component("KafkaServerTast")  
public class KafkaServerTast {
	public static final String HOST =Config.getInstance().configFile.getMqtt().getServer();// "tcp://47.93.196.203:1883";
    public static final String[] TOPIC = {"Discrete/#","Diagram/#","Daily/#"};
    
    @SuppressWarnings("unused")
	private ScheduledExecutorService scheduler;
	
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void runKafkaServer() {
//		Properties props = new Properties();
//	    props.put("bootstrap.servers", "localhost:9092");
//	    props.put("group.id", "test");
//	    props.put("enable.auto.commit", "true");
//	    props.put("auto.commit.interval.ms", "1000");
//	    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//	    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//	    final KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);
//	    consumer.subscribe(Arrays.asList("test"),new ConsumerRebalanceListener() {
//	        public void onPartitionsRevoked(Collection<TopicPartition> collection) {
//	        }
//	        public void onPartitionsAssigned(Collection<TopicPartition> collection) {
//	            //将偏移设置到最开始
//	            consumer.seekToBeginning(collection);
//	        }
//	    });
//	    while (true) {
//	        ConsumerRecords<String, String> records = consumer.poll(100);
//	        for (ConsumerRecord<String, String> record : records)
//	            System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
//	    }
		
		Properties props = new Properties();
	    props.put("bootstrap.servers", "127.0.0.1:9092");
	    props.put("acks", "all");
	    props.put("retries", 0);
	    props.put("batch.size", 16384);
	    props.put("linger.ms", 1);
	    props.put("buffer.memory", 33554432);
	    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

	    Producer<String, String> producer = new KafkaProducer<String, String>(props);
	    for (int i = 0; i < 100; i++)
	        producer.send( new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)) );

	    producer.close();
	}
}
