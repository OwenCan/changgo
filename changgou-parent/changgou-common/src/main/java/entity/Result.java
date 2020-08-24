package entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 描述：springboot遵循restfull，用json封装
 *
 * @author 三国的包子
 * @version 1.0
 * @package entity *
 * @since 1.0
 */
@ApiModel(description = "Result",value = "Result")
public class Result<T> implements Serializable {
    @ApiModelProperty(value = "执行是否成功", required = true)
    private boolean flag;//是否成功
    @ApiModelProperty(value = "返回状态码，20000成功、200001失败、20002用户名密码错误、20004权限不足、20005重复操作", required = true)
    private Integer code;//返回码
    @ApiModelProperty(value = "提示信息", required = true)
    private String message;//返回消息
    @ApiModelProperty(value = "逻辑数据", required = true)
    private T data;//返回数据

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = (T) data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = "操作成功!";
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
