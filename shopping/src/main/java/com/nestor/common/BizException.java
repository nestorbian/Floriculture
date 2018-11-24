package com.nestor.common;

/**
 * 业务异常类
 * @author bianzeyang
 *
 */
public class BizException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -240448397871159762L;
	private int code;
	
	public BizException(String msg) {
		super(msg);
	}
	
	public BizException(int code, String msg) {
		super(msg);
		this.code = code;
	}

}
