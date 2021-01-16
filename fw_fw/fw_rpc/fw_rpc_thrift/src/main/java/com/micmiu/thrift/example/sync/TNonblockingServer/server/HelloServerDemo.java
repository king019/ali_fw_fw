package com.micmiu.thrift.example.sync.TNonblockingServer.server;

import com.micmiu.thrift.demo.HelloWorldService;
import com.micmiu.thrift.demo.impl.HelloWorldImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
@Slf4j
public class HelloServerDemo {
  public static final int SERVER_PORT = 8090;
  /**
   * @param args
   */
  public static void main(String[] args) {
    HelloServerDemo server = new HelloServerDemo();
    server.startServer();
  }
  public void startServer() {
    try {
      log.info("HelloWorld TNonblockingServer start ....");
      TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());
      TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
      TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
      tnbArgs.processor(tprocessor);
      tnbArgs.transportFactory(new TFramedTransport.Factory());
      tnbArgs.protocolFactory(new TCompactProtocol.Factory());
      // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
      TServer server = new TNonblockingServer(tnbArgs);
      server.serve();
    } catch (Exception e) {
      log.info("Server start error!!!");
      e.printStackTrace();
    }
  }
}
