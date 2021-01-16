package com.apache.thrift.struct.java.mydep.demo.TThreadedSelectorServer.server;

import com.apache.thrift.struct.java.dep.fw.StudentFWService;
import com.apache.thrift.struct.java.dep.fw.impl.StudentFWServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
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
      log.info("HelloWorld TThreadPoolServer start ....");
      TProcessor tprocessor = new StudentFWService.Processor<StudentFWService.Iface>(new StudentFWServiceImpl());
      TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(SERVER_PORT);
      TThreadedSelectorServer.Args ttpsArgs = new TThreadedSelectorServer.Args(serverTransport);
      ttpsArgs.processor(tprocessor);
      ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
      // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
      TServer server = new TThreadedSelectorServer(ttpsArgs);
      server.serve();
    } catch (Exception e) {
      log.info("Server start error!!!");
      e.printStackTrace();
    }
  }
}
