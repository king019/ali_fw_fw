package com.apache.thrift.struct.java.mydep.demo.TThreadPoolServer.server;

import com.apache.thrift.struct.java.dep.fw.StudentFWService;
import com.apache.thrift.struct.java.dep.fw.impl.StudentFWServiceImpl;
import com.apache.thrift.struct.java.mydep.util.eum.TProtocolEnum;
import com.apache.thrift.struct.java.mydep.util.eum.TransportEnum;
import com.apache.thrift.struct.java.mydep.util.method.SysMethod;
import com.apache.thrift.struct.java.mydep.util.prop.SysProp;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

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
    TransportEnum trans = null;
    TProtocolEnum tprotocol = SysProp.tprotocol;
    try {
      log.info("HelloWorld TThreadPoolServer start ....");
      TProcessor tprocessor = new StudentFWService.Processor<StudentFWService.Iface>(new StudentFWServiceImpl());
      TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
      TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);
      {
        tArgs.processor(tprocessor);
        // 必须是默认的trasn否则报错
        SysMethod.serverTrans(tArgs, trans);
        SysMethod.serverTprotocol(tArgs, tprotocol);
      }
      // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
      TServer server = new TThreadPoolServer(tArgs);
      server.serve();
    } catch (Exception e) {
      log.info("Server start error!!!");
      e.printStackTrace();
    }
  }
}
