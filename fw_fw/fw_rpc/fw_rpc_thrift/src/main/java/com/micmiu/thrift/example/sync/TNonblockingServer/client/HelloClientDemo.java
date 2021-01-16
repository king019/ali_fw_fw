package com.micmiu.thrift.example.sync.TNonblockingServer.client;

import com.micmiu.thrift.demo.HelloWorldService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

@Slf4j
public class HelloClientDemo {
  public static final String SERVER_IP = "127.0.0.1";
  public static final int SERVER_PORT = 8090;
  public static final int TIMEOUT = 30000;
  /**
   * @param args
   */
  public static void main(String[] args) {
    HelloClientDemo client = new HelloClientDemo();
    client.startClient("Michael");
  }
  /**
   * @param userName
   */
  public void startClient(String userName) {
    TTransport transport = null;
    try {
      transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
      // 协议要和服务端一致
      TProtocol protocol = new TCompactProtocol(transport);
      HelloWorldService.Client client = new HelloWorldService.Client(protocol);
      transport.open();
      String result = client.sayHello(userName);
      log.info("Thrify client result =: " + result);
    } catch (TTransportException e) {
      e.printStackTrace();
    } catch (TException e) {
      e.printStackTrace();
    } finally {
      if (null != transport) {
        transport.close();
      }
    }
  }
}
