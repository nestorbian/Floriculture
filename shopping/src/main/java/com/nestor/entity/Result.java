package com.nestor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API统一返回结果的model
 * @author bianzeyang
 *
 * @param <T>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
	public static final int SUCCESS = 0; // 成功code
//	public static final int UNKNOWN_EXCEPTION = 49; // 未知异常code
	
	private int status = SUCCESS;
	private String message;
	private T data;
	
//	public Result(Throwable e) {
//		this.status = UNKNOWN_EXCEPTION;
//		this.message = e.toString();
//	}
	
	public Result(int status, String msg) {
		this.status = status;
		this.message = msg;
	}
	
	public Result(T data) {
		this.data = data;
	}
}
