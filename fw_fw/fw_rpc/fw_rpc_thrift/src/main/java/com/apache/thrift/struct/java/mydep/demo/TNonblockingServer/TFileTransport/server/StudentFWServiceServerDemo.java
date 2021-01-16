package com.apache.thrift.struct.java.mydep.demo.TNonblockingServer.TFileTransport.server;

import com.apache.thrift.struct.java.dep.fw.StudentFWService;
import com.apache.thrift.struct.java.dep.fw.impl.StudentFWServiceImpl;
import com.apache.thrift.struct.java.mydep.util.eum.TProtocolEnum;
import com.apache.thrift.struct.java.mydep.util.eum.TransportEnum;
import com.apache.thrift.struct.java.mydep.util.method.SysMethod;
import com.apache.thrift.struct.java.mydep.util.prop.SysProp;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
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
    TransportEnum trans = SysProp.trans;
    TProtocolEnum tprotocol = SysProp.tprotocol;
    try {
      log.info("HelloWorld TNonblockingServer start ....");
      TProcessor tprocessor = new StudentFWService.Processor<StudentFWService.Iface>(new StudentFWServiceImpl());
      TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
      TNonblockingServer.Args tArgs = new TNonblockingServer.Args(tnbSocketTransport);
      {
        tArgs.processor(tprocessor);
        SysMethod.serverTrans(tArgs, trans);
        SysMethod.serverTprotocol(tArgs, tprotocol);
      }
      // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
      TServer server = new TNonblockingServer(tArgs);
      server.serve();
    } catch (Exception e) {
      log.info("Server start error!!!");
      e.printStackTrace();
    }
  }
}
