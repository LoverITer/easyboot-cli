package top.easyboot.titan.feign.internal;

import okhttp3.OkHttpClient;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: frank.huang
 * @date: 2021-11-14 20:49
 */
public final class OkHttpClientFactory {

    private static final AtomicReference<OkHttpClient> OK_HTTP_CLIENT=new AtomicReference<>();

    public static OkHttpClient getInstance(){
      for(;;){
          OkHttpClient client = OK_HTTP_CLIENT.get();
          if(Objects.nonNull(client)){
              return client;
          }
          client=newOkHttpClient();
          if(OK_HTTP_CLIENT.compareAndSet(null,client)){
              return client;
          }
      }
    }

    private static OkHttpClient newOkHttpClient(){
        return new OkHttpClient().newBuilder()
                .connectTimeout(500L, TimeUnit.MILLISECONDS)
                .writeTimeout(6000,TimeUnit.MILLISECONDS)
                .writeTimeout(10000,TimeUnit.MILLISECONDS)
                .build();
    }

}
