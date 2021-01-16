package com.k.fw.mq.kafka.config;
import java.util.Properties;
public class KafkaConfig {
  //private static final String kafkaHost = "kaka1:9092,kaka2:9092,kaka3:9092";
  private static final String kafkaHost = "kafka:9092";
  //http://blog.csdn.net/deng624796905/article/details/77456559
  public static Properties queryTopicConfig() {
    final Properties props = new Properties();
    props.put("bootstrap.servers", kafkaHost);
    //	props.put("bootstrap.servers", "kaka1:9092,kaka2:9092,kaka3:9092");
    props.put("group.id", "test");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    return props;
  }
  public static Properties queryProducerConfig() {
    final Properties props = new Properties();
    props.put("bootstrap.servers", kafkaHost);
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    return props;
  }
  public static Properties queryConsumerConfig() {
    final Properties props = new Properties();
    props.put("bootstrap.servers", kafkaHost);
    props.put("group.id", "test");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    return props;
  }
}
