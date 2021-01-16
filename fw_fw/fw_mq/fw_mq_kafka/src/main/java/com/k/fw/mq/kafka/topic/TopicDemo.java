package com.k.fw.mq.kafka.topic;
import com.google.common.collect.Lists;
import com.k.fw.mq.kafka.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
@Slf4j
public class TopicDemo {
  public static void main(final String[] args) throws Exception {
    AdminClient client = null;
    try {
      client = AdminClient.create(KafkaConfig.queryTopicConfig());
      final NewTopic topic = new NewTopic("test", 1, (short) 1);
      client.createTopics(Lists.newArrayList(topic));
      ListTopicsResult topics = client.listTopics();
      topics.names().get().forEach(topic1 -> log.info(topic1));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      client.close();
    }
  }
}