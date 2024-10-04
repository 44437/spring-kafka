package com.u44437.spring_kafka.client;

import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ArbitraryClient {
  private final String CLIENT_BASE_URL = "http://localhost:8020";
  private final MediaType JSON = MediaType.get("application/json; charset=utf-8");

  private final OkHttpClient client;

  public ArbitraryClient(OkHttpClient client) {
    this.client = client;
  }

  public HttpStatus sendRequest(String path, String message)  {
    RequestBody body = RequestBody.create(message, JSON);
    Request request = new Request.Builder()
            .url(CLIENT_BASE_URL + path)
            .post(body)
            .build();
    try (Response response = client.newCall(request).execute()) {
      return HttpStatus.valueOf(response.code());
    }catch (Exception e){
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }
}
