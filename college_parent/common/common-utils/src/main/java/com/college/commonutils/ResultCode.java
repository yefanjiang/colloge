package com.college.commonutils;

/**
 * @author winte
 *
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(20000),

    /**
     * 失败
     */
    ERROR(20001);

    private Integer code;

    ResultCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
