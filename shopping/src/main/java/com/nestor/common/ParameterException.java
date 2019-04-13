package com.nestor.common;

/**
 * <p>检验参数异常</p>
 * 
 * @author Lenovo
 *
 */
public class ParameterException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 4246607176455982870L;
    private static final int PARAMETER_EXCEPTION = 46;// 参数异常code

    public ParameterException(String msg) {
        super(PARAMETER_EXCEPTION, msg);
    }

}
