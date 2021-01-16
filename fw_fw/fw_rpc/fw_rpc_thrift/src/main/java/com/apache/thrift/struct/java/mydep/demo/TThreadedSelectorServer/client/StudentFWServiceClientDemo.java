package com.apache.thrift.struct.java.mydep.demo.TThreadedSelectorServer.client;

import com.apache.thrift.struct.java.dep.fw.StudentFWService;
import com.apache.thrift.struct.java.dep.vo.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class StudentFWServiceClientDemo {
  public static final String SERVER_IP = "127.0.0.1";
  public static final int SERVER_PORT = 8090;
  public static final int TIMEOUT = 30000;
  /**
   * @param args
   */
  public static void main(String[] args) {
    StudentFWServiceClientDemo client = new StudentFWServiceClientDemo();
    client.startClient("Michael");
  }
  /**
   * @param userName
   */
  public void startClient(String userName) {
    try {
      TNonblockingTransport transport = null;
      StudentFWService.AsyncClient asyncClient;
      try {
        {
          TAsyncClientManager clientManager = new TAsyncClientManager();
          transport = new TNonblockingSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
          TProtocolFactory tprotocol = new TBinaryProtocol.Factory();
          asyncClient = new StudentFWService.AsyncClient(tprotocol, clientManager, transport);
        }
        CountDownLatch latch = new CountDownLatch(1);
        Student stu = new Student();
        AsyncMethodCallback back = new AsyncMethodCallback() {
          @Override
          public void onComplete(Object response) {
            log.info("onComplete");
          }
          @Override
          public void onError(Exception exception) {
            exception.printStackTrace();
            log.info("onError");
          }
        };
        asyncClient.structck(stu, back);
        boolean wait = latch.await(30, TimeUnit.SECONDS);
        log.info("Thrify client result =: ");
      } catch (TTransportException e) {
        e.printStackTrace();
      } catch (TException e) {
        e.printStackTrace();
      } finally {
        if (null != transport) {
          transport.close();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
