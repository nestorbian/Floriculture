package com.nestor.common;

/**
 * <p>登录失效异常</p>
 * 
 * @author Lenovo
 *
 */
public class LoginExpiredException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = -3119825697629808031L;
    private static final int LOGIN_EXPIRED_EXCEPTION = 47; // 登录过期code

    public LoginExpiredException(String msg) {
        super(LOGIN_EXPIRED_EXCEPTION, msg);
    }
}
