package com.apache.thrift.struct.java.mydep.util.method;
import com.apache.thrift.struct.java.mydep.util.eum.TProtocolEnum;
import com.apache.thrift.struct.java.mydep.util.eum.TransportEnum;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TSaslServerTransport;
import org.apache.thrift.transport.TTransport;
public class SysMethod {
  public static void serverTrans(AbstractServerArgs<?> tArgs, TransportEnum trans) {
    if (trans != null) {
      switch (trans) {
        case TFastFramed:
          tArgs.transportFactory(new TFastFramedTransport.Factory());
          break;
        case TFramed:
          tArgs.transportFactory(new TFramedTransport.Factory());
          break;
        case THttpClient:
          tArgs.transportFactory(new THttpClient.Factory(null));
          break;
        case TSaslServer:
          tArgs.transportFactory(new TSaslServerTransport.Factory());
          break;
        default:
          break;
      }
    }
  }
  public static void serverTprotocol(AbstractServerArgs<?> tArgs, TProtocolEnum tprotocol) {
    if (tprotocol != null) {
      switch (tprotocol) {
        case TBinary:
          tArgs.protocolFactory(new TBinaryProtocol.Factory());
          break;
        case TCompact:
          tArgs.protocolFactory(new TCompactProtocol.Factory());
          break;
        case TJSON:
          tArgs.protocolFactory(new TJSONProtocol.Factory());
          break;
        case TSimpleJSON:
          tArgs.protocolFactory(new TSimpleJSONProtocol.Factory());
          break;
        case TTuple:
          tArgs.protocolFactory(new TTupleProtocol.Factory());
          break;
        default:
          break;
      }
    }
  }
  public static TProtocol celentTprotocol(TProtocol protocol, TProtocolEnum tprotocol, TTransport transport) {
    if (tprotocol != null) {
      switch (tprotocol) {
        case TBinary:
          protocol = new TBinaryProtocol(transport);
          break;
        case TCompact:
          protocol = new TCompactProtocol(transport);
          break;
        case TJSON:
          protocol = new TJSONProtocol(transport);
          break;
        case TSimpleJSON:
          protocol = new TSimpleJSONProtocol(transport);
          break;
        case TTuple:
          protocol = new TTupleProtocol(transport);
          break;
        default:
          break;
      }
    }
    return protocol;
  }
}
