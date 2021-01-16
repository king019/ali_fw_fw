package com.k.rbmq.api.multi.exe.rec;

import com.k.httpclient.util.H4PostPool;
import com.k.rbmq.api.multi.exe.base.MultiBase;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import rabbitmq.util.ConsumerProps;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
@Slf4j
public class ReceiveMultiBlock {

  public void test() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    {
      MultiBase.registeFactory(factory);
    }
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();
    channel.basicQos(10000);
    String queueName = MultiBase.queueName;
    {
      MultiBase.registerSpecifyRMq(channel);
    }
    Semaphore semaphore = new Semaphore(300);
    {
      boolean autoAck = false;
      ExecutorService pool = Executors.newFixedThreadPool(100);
      Consumer consumer = new DefaultConsumer(channel);
      channel.basicConsume(queueName, autoAck, consumer);
      for (int i = 0; i < 30; i++) {
        Thread th1 = new Thread(new MyThread(semaphore, consumer, channel, pool));
        th1.start();
      }
    }
    {
      Thread th1 = new Thread(new MyCount(semaphore));
      th1.start();
    }
  }

  class MyThread implements Runnable {

    private Semaphore semaphore;
    private Consumer consumer;
    private Channel channel;
    private ExecutorService pool;

    public MyThread(Semaphore semaphore, Consumer consumer, Channel channel, ExecutorService pool) {
      super();
      this.semaphore = semaphore;
      this.consumer = consumer;
      this.channel = channel;
      this.pool = pool;
    }

    @Override
    public void run() {
      while (true) {
        try {
          Delivery delivery = null;
          semaphore.acquire();
          //delivery = consumer.nextDelivery();
          MyCustomer customer = new MyCustomer(delivery, channel, semaphore);
          pool.execute(customer);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  class MyCustomer implements Runnable {

    private Delivery delivery;
    private Channel channel;
    private Semaphore semaphore;

    public MyCustomer(Delivery delivery, Channel channel, Semaphore semaphore) {
      this.delivery = delivery;
      this.channel = channel;
      this.semaphore = semaphore;
    }

    @Override
    public void run() {
      Envelope envelope = delivery.getEnvelope();
      byte[] body = delivery.getBody();
      String url = new String(body);
      url = ConsumerProps.hostring + url;
      try {
        H4PostPool.exec(url, semaphore);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        // semaphore.release();
      }
      log.info(url);
      long deliveryTag = envelope.getDeliveryTag();
      try {
        channel.basicAck(deliveryTag, false);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  class MyCount implements Runnable {

    private Semaphore semaphore;

    public MyCount(Semaphore semaphore) {
      this.semaphore = semaphore;
    }

    @Override
    public void run() {
      while (true) {
        log.info("",semaphore.availablePermits());
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
