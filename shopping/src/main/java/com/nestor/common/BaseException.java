package com.nestor.common;

import lombok.Getter;

/**
 * <p>基础异常</p>
 * 
 * @author Lenovo
 *
 */
public class BaseException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -3961087072958094598L;
    @Getter
    private int code;
    @Getter
    private String msg;

    public BaseException(int code, String msg) {
        super(String.format("异常信息: code:[%d], msg:[%s]", code, msg));
        this.msg = msg;
        this.code = code;
    }
}
