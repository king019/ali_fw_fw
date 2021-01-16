package com.micmiu.thrift.example.sync.async.client;

import com.micmiu.thrift.demo.HelloWorldService;
import com.micmiu.thrift.demo.HelloWorldService.AsyncClient.sayHello_call;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HelloAsynClientDemo {
  public static final String SERVER_IP = "127.0.0.1";
  public static final int SERVER_PORT = 8090;
  public static final int TIMEOUT = 30000;
  /**
   * @param args
   */
  public static void main(String[] args) {
    HelloAsynClientDemo client = new HelloAsynClientDemo();
    client.startClient("Michael");
  }
  /**
   * @param userName
   */
  public void startClient(String userName) {
    try {
      TAsyncClientManager clientManager = new TAsyncClientManager();
      TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
      TProtocolFactory tprotocol = new TCompactProtocol.Factory();
      HelloWorldService.AsyncClient asyncClient = new HelloWorldService.AsyncClient(tprotocol, clientManager, transport);
      log.info("Client start .....");
      CountDownLatch latch = new CountDownLatch(1);
      AsynCallback callBack = new AsynCallback(latch);
      log.info("call method sayHello start ...");
      asyncClient.sayHello(userName, callBack);
      log.info("call method sayHello .... end");
      boolean wait = latch.await(30, TimeUnit.SECONDS);
      log.info("latch.await =:" + wait);
    } catch (Exception e) {
      e.printStackTrace();
    }
    log.info("startClient end.");
  }
  public class AsynCallback implements AsyncMethodCallback<sayHello_call> {
    private CountDownLatch latch;
    public AsynCallback(CountDownLatch latch) {
      this.latch = latch;
    }
    @Override
    public void onComplete(sayHello_call response) {
      log.info("onComplete");
      try {
        // Thread.sleep(1000L * 1);
        log.info("AsynCall result =:" + response.getResult().toString());
      } catch (TException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        latch.countDown();
      }
    }
    @Override
    public void onError(Exception exception) {
      log.info("onError :" + exception.getMessage());
      latch.countDown();
    }
  }
}
