package com.apache.thrift.struct.java.mydep.util.prop;
import com.apache.thrift.struct.java.mydep.util.eum.TProtocolEnum;
import com.apache.thrift.struct.java.mydep.util.eum.TransportEnum;
public class SysProp {
  // TFastFramed, TFramed, THttpClient, TSaslServer;
  public static TransportEnum trans = TransportEnum.TFramed;
  // TBinary, TCompact, TJSON, TSimpleJSON, TTuple
  public static TProtocolEnum tprotocol = TProtocolEnum.TJSON;
}
