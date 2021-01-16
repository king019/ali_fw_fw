package com.micmiu.thrift.example.sync.TThreadPoolServer.server;

import com.micmiu.thrift.demo.HelloWorldService;
import com.micmiu.thrift.demo.impl.HelloWorldImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

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
      log.info("HelloWorld TThreadPoolServer start ....");
      TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());
      TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
      TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(serverTransport);
      ttpsArgs.processor(tprocessor);
      ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
      // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
      TServer server = new TThreadPoolServer(ttpsArgs);
      server.serve();
    } catch (Exception e) {
      log.info("Server start error!!!");
      e.printStackTrace();
    }
  }
}
