package com.k.rbmq.api.dirct.sd;

import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import lombok.extern.slf4j.Slf4j;
import rabbitmq.util.RabbitProps;

import java.io.IOException;

@Slf4j
public class Send {
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
    channel.basicQos(1);
    String exchangeName = "ec";
    String routingKey = "rk";
    String queueName = "qn";
    addListener(channel, conn);
    {
      // channel.exchangeDeclare(exchangeName, "direct", true);
      // queueName = channel.queueDeclare().getQueue();
      // channel.queueBind(queueName, exchangeName, routingKey);
    }
    {
      channel.exchangeDeclare(exchangeName, "direct", true);
      channel.queueDeclare(queueName, true, false, false, null);
      channel.queueBind(queueName, exchangeName, routingKey);
    }
    {
      byte[] messageBodyBytes = "Hello, world!".getBytes();
      BasicProperties properties = new BasicProperties();
      // BasicProperties.Builder builder=new Builder();
      properties = properties.builder().deliveryMode(2).build();
      int index = 1;
      int num = 10000;
      while (index < num) {
        Thread.sleep(1000);
        try {
          boolean mandatory = true;
          boolean immediate = true;
          channel.basicPublish(exchangeName, routingKey, mandatory, immediate, properties, messageBodyBytes);
        } catch (Exception e) {
          e.printStackTrace();
        }
        index++;
      }
    }
    channel.close();
    conn.close();
  }
  private static void addListener(Channel channel, Connection conn) throws Exception {
    {
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
    {
      channel.addReturnListener(new ReturnListener() {
        @Override
        public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
          log.info("addReturnListener");
        }
      });
    }
    {
      conn.addShutdownListener(new ShutdownListener() {
        @Override
        public void shutdownCompleted(ShutdownSignalException cause) {
          log.info("addShutdownListener");
        }
      });
    }
    {
      channel.addReturnListener(new ReturnListener() {
        @Override
        public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
          log.info("addReturnListener");
        }
      });
    }
  }
}
