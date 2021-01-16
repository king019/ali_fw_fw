package com.k.book.rbmq.incaction.p2.p2.recive;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import rabbitmq.util.RabbitProps;

import java.io.IOException;

@Slf4j
public class Reciver {
  public static void main(String[] args) throws Exception {
    // Connecting to a broker
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(RabbitProps.usr);
    factory.setPassword(RabbitProps.pwd);
    factory.setVirtualHost("/");
    factory.setHost(RabbitProps.host);
    // factory.setPort(5672);
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();
    createExchange(channel);
    consumer(channel);
    // }
    // channel.close();
    // conn.close();
  }
  private static void createExchange(Channel channel) throws Exception {
    String exchangeName = "ec";
    String routingKey = "rk";
    String queueName = "qn";
    {
      channel.exchangeDeclare(exchangeName, "direct", true);
      DeclareOk dok = channel.queueDeclare(queueName, true, false, false, null);
      log.info("",dok.getMessageCount());
      channel.queueDeclare(routingKey, true, false, false, null);
      channel.queueBind(queueName, exchangeName, routingKey);
    }
  }
  private static void consumer(Channel channel) throws Exception {
    String queueName = "qn";
    {
      boolean autoAck = false;
      // while (true && !autoAck) {
      channel.basicConsume(queueName, autoAck, new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
          super.handleDelivery(consumerTag, envelope, properties, body);
          log.info(consumerTag);
          envelope.getRoutingKey();
          properties.getContentType();
          long deliveryTag = envelope.getDeliveryTag();
          channel.basicAck(deliveryTag, false);
          channel.basicCancel(consumerTag);
          // (process the message components here ...)
          // channel.basicPublish("", properties.getReplyTo(), properties, body);
          // channel.basicAck(deliveryTag, false);
          // channel.basicReject(deliveryTag, false);
          // throw new IOException();
        }
        @Override
        public void handleCancel(String consumerTag) throws IOException {
          super.handleCancel(consumerTag);
        }
        @Override
        public void handleCancelOk(String consumerTag) {
          super.handleCancelOk(consumerTag);
        }
        @Override
        public void handleConsumeOk(String consumerTag) {
          super.handleConsumeOk(consumerTag);
        }
        @Override
        public void handleRecoverOk(String consumerTag) {
          super.handleRecoverOk(consumerTag);
        }
        @Override
        public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
          super.handleShutdownSignal(consumerTag, sig);
        }
      });
    }
  }
}
