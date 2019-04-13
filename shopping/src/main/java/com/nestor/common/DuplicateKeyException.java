package com.nestor.common;

/**
 * <p>冲突键异常</p>
 * 
 * @author Lenovo
 *
 */
public class DuplicateKeyException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 4662672868078778868L;
    private static final int DUPLICATE_KEY_EXCEPTION = 45;

    public DuplicateKeyException(String msg) {
        super(DUPLICATE_KEY_EXCEPTION, msg);
    }

}
