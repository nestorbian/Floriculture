package com.nestor.common;

/**
 * 业务异常类
 * @author bianzeyang
 *
 */
public class BizException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4648866296534704666L;
	private static final int BIZException_CODE = 48; // 业务异常code
	
	public BizException(String msg) {
		super(BIZException_CODE, msg);
	}

}
