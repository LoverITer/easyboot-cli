package top.easyboot.titan.feign.config;

import feign.Client;
import feign.Logger;
import feign.Request;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.easyboot.titan.feign.internal.OkHttpClientFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author: frank.huang
 * @date: 2021-11-14 20:31
 */
@Configuration
public class CommonFeignConfiguration {

    @Value("${ribbon.read-timeout:6000}")
    private int readTimeout;

    @Value("${ribbon.connect-timeout:3000}")
    private int connectTimeout;

    @Bean
    public Client client(){
        return new OkHttpClient(OkHttpClientFactory.getInstance());
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeout, TimeUnit.MILLISECONDS, readTimeout,TimeUnit.MILLISECONDS,true);
    }

    @Bean
    public Logger.Level logger(){
        return Logger.Level.FULL;
    }

}
