package com.fanhq.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Created by Hachel on 2018/3/1
 */
public class MyKafkaProducer {

    public static void main(String[] args) throws Exception{
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        int count = 0;
        while (true){
            count ++;
            System.out.println(count);
            producer.send(new ProducerRecord("test003", UUID.randomUUID().toString(), Integer.toString(count)));
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
