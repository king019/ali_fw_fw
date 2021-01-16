package com.micmiu.thrift.demo.impl;
import com.micmiu.thrift.demo.HelloWorldService;
import org.apache.thrift.TException;
/**
 * blog http://www.micmiu.com/soa/rpc/thrift-sample/
 *
 * @author Michael
 */
public class HelloWorldImpl implements HelloWorldService.Iface {
  public HelloWorldImpl() {
  }
  @Override
  public String sayHello(String username) throws TException {
    return "Hi," + username + " welcome to my blog www.micmiu.com";
  }
}