package com.k.dubbo.client.service.notify.impl;


import com.k.dubbo.client.model.Person;
import com.k.dubbo.client.service.notify.Nofify;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
@Slf4j
public class NofifyImpl implements Nofify {
  public Map<String, Person> ret = new HashMap();
  public Map<String, Throwable> errors = new HashMap();
  @Override
  public void onreturn(Person msg, String id) {
    log.info("onreturn:" + msg);
    ret.put(id, msg);
  }
  @Override
  public void onthrow(Throwable ex, String id) {
    errors.put(id, ex);
  }
}
