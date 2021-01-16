package com.apache.thrift.struct.java.mydep.demo.TSimpleServer.server;

import com.apache.thrift.struct.java.dep.fw.StudentFWService;
import com.apache.thrift.struct.java.dep.fw.impl.StudentFWServiceImpl;
import com.apache.thrift.struct.java.mydep.util.eum.TProtocolEnum;
import com.apache.thrift.struct.java.mydep.util.eum.TransportEnum;
import com.apache.thrift.struct.java.mydep.util.method.SysMethod;
import com.apache.thrift.struct.java.mydep.util.prop.SysProp;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

@Slf4j
public class StudentFWServiceServerDemo {
  public static final int SERVER_PORT = 8090;

  public static void main(String[] args) {
    StudentFWServiceServerDemo server = new StudentFWServiceServerDemo();
    server.startServer();
  }
  public void startServer() {
    TransportEnum trans = null;
    TProtocolEnum tprotocol = SysProp.tprotocol;
    try {
      log.info("HelloWorld TSimpleServer start ....");
      TProcessor tprocessor = new StudentFWService.Processor<StudentFWService.Iface>(new StudentFWServiceImpl());
      // 简单的单线程服务模型，一般用于测试
      TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
      TSimpleServer.Args tArgs = new TSimpleServer.Args(serverTransport);
      {
        tArgs.processor(tprocessor);
        // 必须是默认的trasn否则报错
        SysMethod.serverTrans(tArgs, trans);
        SysMethod.serverTprotocol(tArgs, tprotocol);
      }
      TServer server = new TSimpleServer(tArgs);
      server.serve();
    } catch (Exception e) {
      log.info("Server start error!!!");
      e.printStackTrace();
    }
  }
}
