package com.k.rbmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import rabbitmq.util.RabbitProps;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Send {
  private final static String QUEUE_NAME = "hello";
  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(RabbitProps.usr);
    factory.setPassword(RabbitProps.pwd);
    factory.setVirtualHost("/");
    factory.setHost(RabbitProps.host);
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    Map<String, Object> map = new HashMap<>();
    map.put("111", "22");
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = "Hello World!";
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    log.info(" [x] Sent '" + message + "'");
    channel.close();
    connection.close();
  }
}