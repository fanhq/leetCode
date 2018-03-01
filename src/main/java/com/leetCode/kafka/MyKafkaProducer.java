package com.leetCode.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


/**
 * Created by Hachel on 2018/3/1
 */
public class MyKafkaProducer {
    public final static String TOPIC = "hachel";
   /* private final org.apache.kafka.clients.producer.KafkaProducer<String, String> producer;

    private MyKafkaProducer(){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(props);
    }

    void produce() {
        int messageNo = 1;
        final int COUNT = 2;

        while (messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = "hello kafka message " + key;
            boolean sync = false;   //是否同步

            if (sync) {
                try {
                    producer.send(new ProducerRecord<String, String>(TOPIC, data)).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                producer.send(new ProducerRecord<String, String>(TOPIC, data));
            }

            //必须写下面这句,相当于发送
            producer.flush();

            messageNo ++;
        }
    }*/

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord(TOPIC, Integer.toString(i), Integer.toString(i)));
        }
        producer.close();
    }

}
