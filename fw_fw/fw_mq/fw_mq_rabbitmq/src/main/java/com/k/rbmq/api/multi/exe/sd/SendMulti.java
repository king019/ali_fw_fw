package com.k.rbmq.api.multi.exe.sd;

import com.k.rbmq.api.multi.exe.base.MultiBase;
import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import lombok.extern.slf4j.Slf4j;
import rabbitmq.util.ConsumerProps;

import java.io.IOException;

@Slf4j
public class SendMulti {
  public void test() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    {
      MultiBase.registeFactory(factory);
    }
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();
    channel.basicQos(1);
    String exchangeName = MultiBase.exchangeName;
    String routingKey = MultiBase.routingKey;
    {
      MultiBase.registerSpecifyRMq(channel);
    }
    channel.confirmSelect();
    channel.addConfirmListener(new ConfirmListener() {
      @Override
      public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        log.info("handleNack___" + deliveryTag);
      }
      @Override
      public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        log.info("handleAck____" + deliveryTag);
      }
    });
    {
      channel.addReturnListener(new ReturnListener() {
        @Override
        public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
          log.info("handleReturn___" + "addReturnListener");
        }
      });
    }
    {
      conn.addShutdownListener(new ShutdownListener() {
        @Override
        public void shutdownCompleted(ShutdownSignalException cause) {
          log.info("shutdownCompleted___" + "addShutdownListener");
        }
      });
    }
    {
      channel.addReturnListener(new ReturnListener() {
        @Override
        public void handleReturn(int arg0, String arg1, String arg2, String arg3, BasicProperties arg4, byte[] arg5) throws IOException {
          log.info("handleReturn___" + "addReturnListener");
        }
      });
    }
    {
      String url = ConsumerProps.prefix;
      byte[] messageBodyBytes = url.getBytes();
      BasicProperties properties = new BasicProperties();
      properties = properties.builder().deliveryMode(2).build();
      int flag = 1;
      while (flag == 1) {
        // Thread.sleep(1000);
        try {
          boolean mandatory = true;
          boolean immediate = false;
          channel.basicPublish(exchangeName, routingKey, mandatory, immediate, properties, messageBodyBytes);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    channel.close();
    conn.close();
  }
}