package com.k.rbmq.api.multi.test;

import com.k.rbmq.api.multi.exe.rec.ReceiveMulti;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
@Slf4j
public class MultiReceiveTest {

  @Test
  @Ignore
  public void test() {
    Thread threceive = new Thread(new Runnable() {
      @Override
      public void run() {
        ReceiveMulti receive = new ReceiveMulti();
        try {
          log.info("receive.test();");
          receive.test();
          receive.test();
          receive.test();
          receive.test();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    threceive.start();
    try {
      Thread.sleep(100000000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
