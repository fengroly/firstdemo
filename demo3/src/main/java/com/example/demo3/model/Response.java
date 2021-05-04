package com.example.demo3.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 全局响应类
 *
 * @param <T>
 * @author yanglei
 */
@ApiModel(value = "Response", description = "返回对象")
public class Response<T> {

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "状态编码")
    private String code;

    @ApiModelProperty(value = "处理结果描述")
    private String msg;

    @ApiModelProperty(value = "返回的对象信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)//该注解是不参与序列化，为null的时候不传递给前台
    private T data;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String isCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> Response<T> success() {
        Response<T> response = new Response<T>();
        response.setSuccess(true);
        response.setMsg("success");
        response.setData(null);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> Response<T> success(String msg) {
        Response<T> response = new Response<T>();
        response.setSuccess(true);
        response.setMsg(msg);
        response.setData(null);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> Response<T> success(T obj) {
        Response<T> response = new Response<T>();
        response.setSuccess(true);
        response.setMsg("success");
        response.setData(obj);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> Response<T> success(T obj, String msg) {
        Response<T> response = new Response<T>();
        response.setSuccess(true);
        response.setMsg(msg);
        response.setData(obj);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> Response<T> error(T obj, String msg) {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setMsg(msg);
        response.setData(obj);
        return response;
    }

    /**
     * 处理异常返回
     *
     * @param msg
     * @return
     */
    public static <T> Response<T> error(String msg) {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setMsg(msg);
        response.setData(null);
        return response;
    }

    /**
     * 处理警告返回
     *
     * @param msg
     * @return
     */
    public static <T> Response<T> warn(String msg) {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setCode("warn");
        response.setMsg(msg);
        response.setData(null);
        return response;
    }
}