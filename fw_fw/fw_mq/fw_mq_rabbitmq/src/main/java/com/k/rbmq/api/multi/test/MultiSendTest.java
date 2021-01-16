package com.k.rbmq.api.multi.test;

import com.k.rbmq.api.multi.exe.sd.SendMulti;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
@Slf4j
public class MultiSendTest {

  @Test
  @Ignore
  public void test() {
    Thread thsend = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          log.info("send.test();");
          SendMulti send = new SendMulti();
          send.test();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    thsend.start();
    try {
      Thread.sleep(100000000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
