package top.easyblog.titan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.easyblog.titan.annotation.ResponseWrapper;

/**
 * @author: frank.huang
 * @date: 2021-10-31 23:08
 */
@RestController
@RequestMapping("/v1/in/demo")
public class DemoController {

    @ResponseWrapper
    @GetMapping("/hello")
    public Integer hello(){
       return 1;
    }

}
