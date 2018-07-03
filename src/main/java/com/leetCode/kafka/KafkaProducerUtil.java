package com.leetCode.kafka;


import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

/**
 * Created by Hachel on 2018/7/3
 */
public class KafkaProducerUtil {
    private static volatile Producer<String, String> producer = null;

    private static Producer<String, String> getInstance() {

        if (producer == null) {
            synchronized (KafkaProducerUtil.class) {
                if (producer == null) {
                    init();
                }
            }
        }
        return producer;
    }

    /**
     * @param topic 消息主题
     * @param key   消息键
     * @param value 消息值
     */
    public static void send(String topic, String key, String value) {
        getInstance().send(new ProducerRecord<String, String>(topic, key, value));
    }

    /**
     * @param topic    消息主题
     * @param key      消息键
     * @param value    消息值
     * @param callback 回调
     */
    public static void send(String topic, String key, String value, Callback callback) {
        getInstance().send(new ProducerRecord<String, String>(topic, key, value), callback);
    }

    /**
     * @param topic     消息主题
     * @param partition 分区
     * @param key       消息键
     * @param value     消息值
     */
    public static void send(String topic, int partition, String key, String value) {
        getInstance().send(new ProducerRecord<String, String>(topic, partition, key, value));
    }

    /**
     * @param topic     消息主题
     * @param partition 分区
     * @param key       消息键
     * @param value     消息值
     * @param callback  回调
     */
    public static void send(String topic, int partition, String key, String value, Callback callback) {
        getInstance().send(new ProducerRecord<String, String>(topic, partition, key, value), callback);
    }

    /**
     * 关闭
     */
    public void close() {
        if (producer != null) {
            producer.close();
        }
    }

    private static void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "bootstrap.servers");
        props.put("acks", "acks");
        props.put("retries", "retries");
        props.put("batch.size", "batch.size");
        props.put("linger.ms", "linger.ms");
        props.put("buffer.memory", "buffer.memory");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

}
