package com.k.rbmq.api.multi.exe.rec;

import com.k.httpclient.util.Hv4Post;
import com.k.rbmq.api.multi.exe.base.MultiBase;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import rabbitmq.util.ConsumerProps;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Slf4j
public class ReceiveMulti {

  public void test() throws Exception {
    // Connecting to a broker
    ConnectionFactory factory = new ConnectionFactory();
    {
      MultiBase.registeFactory(factory);
    }
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();
    channel.basicQos(100);
    String queueName = MultiBase.queueName;
    {
      MultiBase.registerSpecifyRMq(channel);
    }
    {
      boolean autoAck = false;
      ExecutorService pool = Executors.newCachedThreadPool();
      pool.submit(customer(channel, queueName, autoAck));
      pool.submit(customer(channel, queueName, autoAck));
      pool.submit(customer(channel, queueName, autoAck));
      pool.submit(customer(channel, queueName, autoAck));
    }
    {
      Consumer consumer = new DefaultConsumer(channel);
      channel.basicConsume(queueName, false, consumer);
//      QueueingConsumer.Delivery delivery = consumer.nextDelivery(1000);
//      delivery.getProperties();
    }
    log.info("",channel.getChannelNumber());
    log.info("recclose");
  }

  public Runnable customer(Channel channel, String queueName, boolean autoAck) {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        try {
          channel.basicConsume(queueName, autoAck, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
              String url = new String(body);
              url = ConsumerProps.hostring + url;
              try {
                Hv4Post.httppost(url, null);
              } catch (Exception e) {
                e.printStackTrace();
              }
              log.info(url);
              log.info("handleDelivery____" + consumerTag);
              String routingKey = envelope.getRoutingKey();
              String contentType = properties.getContentType();
              long deliveryTag = envelope.getDeliveryTag();
              // (process the message components here ...)
              channel.basicAck(deliveryTag, false);
              log.info(routingKey + "___" + contentType + "___" + deliveryTag);
              // channel.basicReject(deliveryTag, false);
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    return runnable;
  }
}
