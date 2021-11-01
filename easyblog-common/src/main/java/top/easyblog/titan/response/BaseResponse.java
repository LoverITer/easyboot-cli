package top.easyblog.titan.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import top.easyblog.titan.constant.Constants;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: frank.huang
 * @date: 2021-11-01 08:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse implements Serializable {

    private Object data;
    private String requestId = MDC.get(Constants.REQUEST_ID);
    private String message;
    private String code;
    private Boolean success;
    private Long timestamp;
    private String path;

    public BaseResponse(Object data,String message,String code){
        this.data=data;
        this.message=message;
        this.code=code;
        this.success=isSuccess();
        this.requestId=MDC.get(Constants.REQUEST_ID);
        this.timestamp=System.currentTimeMillis();
    }

    public BaseResponse(Object data,String path,String message,String code){
        this.data=data;
        this.path=path;
        this.message=message;
        this.code=code;
        this.success=isSuccess();
        this.requestId=MDC.get(Constants.REQUEST_ID);
        this.timestamp=System.currentTimeMillis();
    }


    public boolean isSuccess(){
        return ResultCode.SUCCESS.getCode().equalsIgnoreCase(this.code);
    }

    public  static BaseResponse ok(Object data){
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(ResultCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }

    public static  BaseResponse failed(){
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setCode(ResultCode.INTERNAL_ERROR.getCode());
        response.setMessage(ResultCode.INTERNAL_ERROR.getMessage());
        return response;
    }

}
