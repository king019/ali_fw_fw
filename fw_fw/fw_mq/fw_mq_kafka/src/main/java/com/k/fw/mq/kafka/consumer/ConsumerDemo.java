package com.k.fw.mq.kafka.consumer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.k.fw.mq.kafka.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
@Slf4j
public class ConsumerDemo {
  public static void main(final String[] args) {
    final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaConfig.queryConsumerConfig());
    consumer.subscribe(Lists.newArrayList( "topic","test"));
    Map<String, List<PartitionInfo>> map = consumer.listTopics();
    final ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
    for (final ConsumerRecord<String, String> record : records) {
      Map<TopicPartition, OffsetAndMetadata> offsets = Maps.newHashMap();
      TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
      OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(record.offset());
      offsets.put(topicPartition, offsetAndMetadata);
      consumer.commitSync(offsets);
      consumer.commitAsync();
      log.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
    }
    consumer.close();
  }
}