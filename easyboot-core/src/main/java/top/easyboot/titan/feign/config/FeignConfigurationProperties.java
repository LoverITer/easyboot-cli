package top.easyboot.titan.feign.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: frank.huang
 * @date: 2021-12-04 10:58
 */
@Data
@ConfigurationProperties(prefix = "feign", ignoreInvalidFields = true)
public class FeignConfigurationProperties {

    private int readTimeout = 6000;

    private int connectTimeout = 3000;

    private int period=100;

    private int retryMaxPeriod = 1000;

    private int retryMaxAttempts = 3;

}
