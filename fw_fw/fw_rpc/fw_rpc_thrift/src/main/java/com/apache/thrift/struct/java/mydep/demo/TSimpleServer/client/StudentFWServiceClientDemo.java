package com.apache.thrift.struct.java.mydep.demo.TSimpleServer.client;

import com.apache.thrift.struct.java.dep.fw.StudentFWService;
import com.apache.thrift.struct.java.dep.vo.Student;
import com.apache.thrift.struct.java.mydep.util.eum.TProtocolEnum;
import com.apache.thrift.struct.java.mydep.util.method.SysMethod;
import com.apache.thrift.struct.java.mydep.util.prop.SysProp;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

@Slf4j
public class StudentFWServiceClientDemo {
  public static final String SERVER_IP = "127.0.0.1";
  public static final int SERVER_PORT = 8090;
  public static final int TIMEOUT = 30000;
  /**
   * @param args
   */
  public static void main(String[] args) {
    StudentFWServiceClientDemo client = new StudentFWServiceClientDemo();
    client.startClient("Michael");
  }
  /**
   * @param userName
   */
  public void startClient(String userName) {
    TProtocolEnum tprotocol = SysProp.tprotocol;
    TTransport transport = null;
    try {
      transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
      // 协议要和服务端一致
      TProtocol protocol = null;
      {
        protocol = SysMethod.celentTprotocol(protocol, tprotocol, transport);
      }
      StudentFWService.Client client = new StudentFWService.Client(protocol);
      transport.open();
      Student stu = new Student();
      Student result = client.structck(stu);
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
