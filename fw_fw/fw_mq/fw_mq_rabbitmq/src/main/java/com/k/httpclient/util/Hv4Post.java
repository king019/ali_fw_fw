package com.k.httpclient.util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
public class Hv4Post {
  public static void httppost(String url, Map<String, String> map) {
    HttpPost post = null;
    CloseableHttpClient client = null;
    try {
      client = HttpClients.createDefault();
      post = new HttpPost(url);
      if (MapUtils.isNotEmpty(map)) {
        List<NameValuePair> parameters = new ArrayList<>();
        for (String key : map.keySet()) {
          parameters.add(new BasicNameValuePair(key, map.get(key)));
        }
        HttpEntity entity = new UrlEncodedFormEntity(parameters);
        post.setEntity(entity);
      }
      HttpResponse response = client.execute(post);
      // execute method and handle any error responses.
      HttpEntity rspEntity = response.getEntity();
      List<String> lines = IOUtils.readLines(rspEntity.getContent());
      // handle response.
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      post.releaseConnection();
      try {
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
