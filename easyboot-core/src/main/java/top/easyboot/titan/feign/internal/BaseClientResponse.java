package top.easyboot.titan.feign.internal;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import top.easyboot.titan.response.BaseResponse;
import top.easyboot.titan.response.ResultCode;

/**
 * @author: frank.huang
 * @date: 2021-11-14 20:32
 */
@Data
public class BaseClientResponse<T> implements Response<T>{

    private T data;
    private String message;
    private String path;
    private String resultCode;
    private String serverTime;
    private String txId;

    @Override
    public boolean isSuccess() {
        return StringUtils.equalsIgnoreCase(resultCode, ResultCode.SUCCESS.getCode());
    }

    @Override
    public T data() {
        return this.data;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public String displayMessage() {
        return this.displayMessage();
    }

    @Override
    public String resultCode() {
        return this.resultCode;
    }
}