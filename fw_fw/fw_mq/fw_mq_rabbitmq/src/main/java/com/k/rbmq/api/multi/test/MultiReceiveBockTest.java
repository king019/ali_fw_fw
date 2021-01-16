package com.k.rbmq.api.multi.test;

import com.k.rbmq.api.multi.exe.rec.ReceiveMultiBlock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
@Slf4j
public class MultiReceiveBockTest {

  @Test
  @Ignore
  public void test() {
    Thread threceive = new Thread(new Runnable() {
      @Override
      public void run() {
        ReceiveMultiBlock receive = new ReceiveMultiBlock();
        try {
          log.info("receive.test();");
          receive.test();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    threceive.start();
    try {
      log.info("123456");
      Thread.sleep(100000000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
