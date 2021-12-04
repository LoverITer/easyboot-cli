package top.easyboot.titan.feign.config;

import com.google.common.collect.Lists;
import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import top.easyboot.titan.constant.Constants;
import top.easyboot.titan.exception.BusinessException;
import top.easyboot.titan.feign.internal.FeignLogger;
import top.easyboot.titan.feign.internal.OkHttpClientFactory;
import top.easyboot.titan.response.ResultCode;

import java.util.concurrent.TimeUnit;

/**
 * @author: frank.huang
 * @date: 2021-11-14 20:31
 */
public abstract class FeignConfiguration {

    protected GsonHttpMessageConverter gsonHttpMessageConverter;

    {
        gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, new MediaType("application", "*+json")));
    }

    @Autowired
    private FeignConfigurationProperties properties;


    @Bean
    public Client client() {
        return new OkHttpClient(OkHttpClientFactory.getInstance(properties));
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(properties.getConnectTimeout(), TimeUnit.MILLISECONDS,
                properties.getReadTimeout(), TimeUnit.MILLISECONDS, true);
    }

    @Bean
    public RequestInterceptor requestHeader() {
        return requestTemplate -> requestTemplate.header(Constants.HEADER_Request_ID, MDC.get(Constants.REQUEST_ID));
    }


    @Bean
    public Retryer retryer() {
        return new Retryer.Default(properties.getPeriod(), properties.getRetryMaxPeriod(), properties.getRetryMaxAttempts());
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return (method, response) -> {
            throw new BusinessException(ResultCode.REMOTE_INVOKE_FAIL,response.reason());
        };
    }

    @Bean
    public Decoder decoder() {
        return new ResponseEntityDecoder(new SpringDecoder(() -> new HttpMessageConverters(false,Lists.newArrayList(gsonHttpMessageConverter))));
    }

    @Bean
    public Encoder encoder() {
        return new SpringEncoder(()->new HttpMessageConverters(false,Lists.newArrayList(gsonHttpMessageConverter)));
    }

    @Bean
    public Logger.Level logger() {
        return Logger.Level.FULL;
    }

    @Bean
    public FeignLoggerFactory feignLoggerFactory(@Value("spring.profiles.active:dev")String env){
        return type -> new FeignLogger(type,env);
    }

}
