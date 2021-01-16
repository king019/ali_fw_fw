package com.k.fw.mq.kafka.producer;

import com.google.common.base.Stopwatch;
import com.k.fw.mq.kafka.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.concurrent.Future;

@Slf4j
public class ProducerDemo {
    public static void main(final String[] args) throws Exception {
        final Producer<String, String> producer = new KafkaProducer<>(KafkaConfig.queryProducerConfig());
        try {
            List<PartitionInfo> partions = producer.partitionsFor("topic");
            for (int i = 0; i < 2000000; i++) {
                for (PartitionInfo partion : partions) {
                    producer.send(new ProducerRecord<>("topic", partion.partition(), "key", "val"));
                }
                Future<RecordMetadata> send = producer.send(new ProducerRecord<>("test", "kev", "val__" + i + "___" + Stopwatch.createStarted().toString()));
                //, (metadata, exception) -> log.info(String.valueOf(metadata), exception)
                send.get();
                log.info("send.get:{}", send.get());
                log.info("send.isDone:{}", send.isDone());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}