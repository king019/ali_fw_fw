package com.k.rbmq.api.dirct.rc;

import com.rabbitmq.client.AMQP.BasicProperties;
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
    final Channel channel = conn.createChannel();
    String exchangeName = "ec";
    String routingKey = "rk";
    String queueName = "qn";
    {
      channel.exchangeDeclare(exchangeName, "direct", true);
      channel.queueDeclare(queueName, true, false, false, null);
      channel.queueBind(queueName, exchangeName, routingKey);
    }
    {
      boolean autoAck = false;
      // while (true && !autoAck) {
      channel.basicConsume(queueName, autoAck, new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
          super.handleDelivery(consumerTag, envelope, properties, body);
          log.info(consumerTag);
          String routingKey = envelope.getRoutingKey();
          String contentType = properties.getContentType();
          long deliveryTag = envelope.getDeliveryTag();
          // (process the message components here ...)
          channel.basicAck(deliveryTag, false);
          // channel.basicReject(deliveryTag, false);
        }
      });
      // channel.basicConsume(queueName, autoAck, "myConsumerTag",
      // new DefaultConsumer(channel) {
      // public void handleDelivery(null,
      // null,
      // null,
      // null)
      // throws IOException
      // {
      // String routingKey = envelope.getRoutingKey();
      // String contentType = properties.contentType;
      // long deliveryTag = envelope.getDeliveryTag();
      // // (process the message components here ...)
      // channel.basicAck(deliveryTag, false);
      // }
      // });
    }
    // }
    //channel.close();
    //conn.close();
  }
}
