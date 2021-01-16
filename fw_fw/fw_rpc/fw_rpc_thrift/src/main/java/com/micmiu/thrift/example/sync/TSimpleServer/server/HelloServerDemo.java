package com.micmiu.thrift.example.sync.TSimpleServer.server;

import com.micmiu.thrift.demo.HelloWorldService;
import com.micmiu.thrift.demo.impl.HelloWorldImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
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
      log.info("HelloWorld TSimpleServer start ....");
      TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());
      // HelloWorldService.Processor&lt;HelloWorldService.Iface&gt; tprocessor =
      // new HelloWorldService.Processor&lt;HelloWorldService.Iface&gt;(
      // new HelloWorldImpl());
      // 简单的单线程服务模型，一般用于测试
      TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
      TServer.Args tArgs = new TServer.Args(serverTransport);
      tArgs.processor(tprocessor);
      tArgs.protocolFactory(new TBinaryProtocol.Factory());
      // tArgs.protocolFactory(new TCompactProtocol.Factory());
      // tArgs.protocolFactory(new TJSONProtocol.Factory());
      TServer server = new TSimpleServer(tArgs);
      server.serve();
    } catch (Exception e) {
      log.info("Server start error!!!");
      e.printStackTrace();
    }
  }
}
