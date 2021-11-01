package top.easyblog.titan.util;

import java.util.UUID;

/**
 * @author: frank.huang
 * @date: 2021-11-01 19:12
 */
public class IdGenerator {

    public static String getRequestId(){
        return "easyblog-cli-"+getUUID();
    }


    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }


}
