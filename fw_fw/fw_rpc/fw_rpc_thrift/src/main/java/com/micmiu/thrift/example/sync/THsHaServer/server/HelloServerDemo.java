package com.micmiu.thrift.example.sync.THsHaServer.server;

import com.micmiu.thrift.demo.HelloWorldService;
import com.micmiu.thrift.demo.impl.HelloWorldImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
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
      log.info("HelloWorld THsHaServer start ....");
      TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());
      TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
      THsHaServer.Args thhsArgs = new THsHaServer.Args(tnbSocketTransport);
      thhsArgs.processor(tprocessor);
      thhsArgs.transportFactory(new TFramedTransport.Factory());
      thhsArgs.protocolFactory(new TBinaryProtocol.Factory());
      // 半同步半异步的服务模型
      TServer server = new THsHaServer(thhsArgs);
      server.serve();
    } catch (Exception e) {
      log.info("Server start error!!!");
      e.printStackTrace();
    }
  }
}
