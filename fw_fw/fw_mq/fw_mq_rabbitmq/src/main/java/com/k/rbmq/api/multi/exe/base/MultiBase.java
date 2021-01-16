package com.k.rbmq.api.multi.exe.base;

import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.AMQP.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import lombok.extern.slf4j.Slf4j;
import rabbitmq.util.RabbitProps;

import java.io.IOException;

@Slf4j
public class MultiBase {
  public static String exchangeName = "multiex";
  public static String routingKey = "multirk";
  public static String queueName = "multiqueue";
  public static void registeFactory(ConnectionFactory factory) throws IOException {
    {
      factory.setUsername(RabbitProps.usr);
      factory.setPassword(RabbitProps.pwd);
      factory.setVirtualHost("/");
      factory.setHost(RabbitProps.host);
      // factory.setPort(5672);
    }
  }
  public static void registerSpecifyRMq(Channel channel) throws IOException {
    {
      Exchange.DeclareOk ed = channel.exchangeDeclare(exchangeName, "direct", true);
      Queue.DeclareOk qd = channel.queueDeclare(queueName, true, false, false, null);
      Queue.BindOk qb = channel.queueBind(queueName, exchangeName, routingKey);
      Consumer consumer = channel.getDefaultConsumer();
    }
  }
  public static void registerDefaultRMq(Channel channel) throws IOException {
    {
      channel.exchangeDeclare(exchangeName, "direct", true);
      queueName = channel.queueDeclare().getQueue();
      channel.queueBind(queueName, exchangeName, routingKey);
    }
  }
}
