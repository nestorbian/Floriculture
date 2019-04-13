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

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
