package com.apache.thrift.struct.java.mydep.demo.THsHaServer.server;

import com.apache.thrift.struct.java.dep.fw.StudentFWService;
import com.apache.thrift.struct.java.dep.fw.impl.StudentFWServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

@Slf4j
public class StudentFWServiceServerDemo {
  public static final int SERVER_PORT = 8090;
  /**
   * @param args
   */
  public static void main(String[] args) {
    StudentFWServiceServerDemo server = new StudentFWServiceServerDemo();
    server.startServer();
  }
  public void startServer() {
    try {
      log.info("HelloWorld THsHaServer start ....");
      TProcessor tprocessor = new StudentFWService.Processor<StudentFWService.Iface>(new StudentFWServiceImpl());
      TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
      THsHaServer.Args thhsArgs = new THsHaServer.Args(tnbSocketTransport);
      {
        thhsArgs.processor(tprocessor);
        thhsArgs.transportFactory(new TFramedTransport.Factory());
        thhsArgs.protocolFactory(new TBinaryProtocol.Factory());
      }
      // 半同步半异步的服务模型
      TServer server = new THsHaServer(thhsArgs);
      server.serve();
    } catch (Exception e) {
      log.info("Server start error!!!");
      e.printStackTrace();
    }
  }
}
