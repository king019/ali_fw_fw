package com.k.book.rbmq.incaction.p2.p2.send;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import rabbitmq.util.RabbitProps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SendListern {
  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(RabbitProps.usr);
    factory.setPassword(RabbitProps.pwd);
    factory.setVirtualHost("/");
    factory.setHost(RabbitProps.host);
    init(factory);
  }
  private static void init(ConnectionFactory factory) {
    try {
      String exchangeName = "ec";
      String routingKey = "rk";
      String queueName = "qn";
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();
      addListen(connection);
      addListen(channel);
      // BuiltinExchangeType.DIRECT;
      channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
      Map<String, Object> map = new HashMap<>();
      // map.put("ha-policy", "all");
      // map.put("x-ha-policy", "all");
      // map.put("x-ha-policy", ImmutableMap.of("exactly", "2"));
      // map.put("x-ha-policy", ImmutableMap.of("nodes", "rbcluster4"));
      // map.put("ha-mode", "all");
      //
      map.put("message-ttl", "1000");
      map.put("expires", "2000");
      map.put("max-length", "2000");
      map.put("max-length-bytes", "5000");
      map.put("max-priority", "1");
      channel.queueDeclare(queueName, true, false, false, map);
      String message = "Hello World!";
      BasicProperties props = new BasicProperties();
      props = props.builder().deliveryMode(2).replyTo(routingKey).build();
      for (int i = 0; i < 10; i++) {
        channel.basicPublish("", queueName, props, message.getBytes());
      }
      log.info(" [x] Sent '" + message + "'");
      Thread.sleep(10000);
      channel.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  private static void addListen(Channel channel) throws Exception {
    channel.confirmSelect();
    channel.addConfirmListener(new ConfirmListener() {
      @Override
      public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        log.info("",deliveryTag);
      }
      @Override
      public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        log.info("",deliveryTag);
      }
    });
  }
  private static void addListen(Connection connection) throws Exception {
    connection.addBlockedListener(new BlockedListener() {
      @Override
      public void handleUnblocked() throws IOException {
      }
      @Override
      public void handleBlocked(String reason) throws IOException {
      }
    });
  }
}