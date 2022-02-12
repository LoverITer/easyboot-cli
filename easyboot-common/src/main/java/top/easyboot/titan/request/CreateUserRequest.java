package top.easyboot.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Example of request parameter bean to create an object
 *
 * @author: frank.huang
 * @date: 2021-11-01 20:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank
    private String address;

    @Min(value = 0, message = "年龄不能小于0")
    @Max(value = 200, message = "年龄最大200")
    private Integer age;

}
