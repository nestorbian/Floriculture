package com.nestor.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nestor.entity.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器
 * @author bianzeyang
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	/**
	 * 处理业务异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = BizException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Result<?> handleBizExcption(BizException e) {
		log.error("业务异常： {}", e.toString());
		return new Result<>(e.getMessage());
	}
	
	/**
	 * 处理未知异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Result<?> handleRuntimeException(RuntimeException e) {
		log.error("未知异常： {}", e.toString());
		return new Result<>(e);
	}
}
