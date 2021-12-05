package top.easyboot.titan;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableFeignClients
@EnableTransactionManagement
@MapperScans({
        @MapperScan("top.easyboot.titan.dao"),
        @MapperScan("top.easyboot.dao.auto")
})
@EnableConfigurationProperties({FeignConfigurationProperties.class})
@SpringBootApplication(scanBasePackages = {"top.easyboot.titan"})
public class Application {

    public static void main(String[] args) {
       try{
           SpringApplication.run(Application.class,args);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

}
