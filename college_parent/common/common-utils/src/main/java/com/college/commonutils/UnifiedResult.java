package com.college.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class UnifiedResult {

    private boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    private UnifiedResult() {}

    public static UnifiedResult ok() {
        UnifiedResult r = new UnifiedResult();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage("成功");
        return r;
    }

    public static UnifiedResult error() {
        UnifiedResult r = new UnifiedResult();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR.getCode());
        r.setMessage("失败");
        return r;
    }

    public UnifiedResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public UnifiedResult message(String message){
        this.setMessage(message);
        return this;
    }

    public UnifiedResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public UnifiedResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public UnifiedResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
