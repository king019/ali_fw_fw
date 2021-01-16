package com.k.httpclient.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class H4PostPool {
  static AtomicInteger all = new AtomicInteger(0);
  static AtomicInteger failed = new AtomicInteger(0);
  static AtomicInteger completed = new AtomicInteger(0);
  static AtomicInteger cancelled = new AtomicInteger(0);
  static PoolingNHttpClientConnectionManager connManager;
  private static CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setConnectionManager(connManager).build();
  static {
    ConnectingIOReactor ioReactor = null;
    try {
      ioReactor = new DefaultConnectingIOReactor();
    } catch (IOReactorException e) {
      e.printStackTrace();
    }
    connManager = new PoolingNHttpClientConnectionManager(ioReactor);
    connManager.setMaxTotal(1);
  }
  static {
    httpclient.start();
  }
  // private static final int MAX_AVAILABLE = 100;
  // private final static Semaphore available = new Semaphore(MAX_AVAILABLE);
  public static Future<HttpResponse> exec(String url, Semaphore semaphore) {
    HttpUriRequest request = new HttpPatch(url);
    Future<HttpResponse> resp = null;
    try {
      // log.info(semaphore.availablePermits());
      resp = httpclient.execute(request, new FutureCallback<HttpResponse>() {
        @Override
        public void failed(Exception ex) {
          failed.incrementAndGet();
          semaphore.release();
          // available.release();
        }
        @Override
        public void completed(HttpResponse result) {
          completed.incrementAndGet();
          semaphore.release();
          // available.release();
          try {
            // Thread.sleep(1000);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        @Override
        public void cancelled() {
          cancelled.incrementAndGet();
          semaphore.release();
          log.info("cancelled");
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return resp;
  }
}
