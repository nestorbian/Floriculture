package com.nestor.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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
	private final int uploadSizeExceededExceptionCode = 44;
	private final String uploadSizeExceededExceptionMsg = "上传文件过大";
	
	/**
	 * <p>处理自定义异常</p>
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = {BaseException.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Result<?> handleBizExcption(BaseException e) {
		log.error("运行时异常： {}", e.toString());
		return new Result<>(e.getCode(), e.getMessage());
	}
	
	/**
	 * <p>处理上传文件异常</p>
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = {MaxUploadSizeExceededException.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Result<?> handleBizExcption(MaxUploadSizeExceededException e) {
		log.error("上传文件异常： {}", e.toString());
		return new Result<>(uploadSizeExceededExceptionCode, uploadSizeExceededExceptionMsg);
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
		StringBuilder stackInfo = new StringBuilder(e.getCause().getMessage());
        for (StackTraceElement traceElement : e.getCause().getStackTrace()) {
        	stackInfo.append("\n\tat ".concat(traceElement.toString()));
        }
		log.error("exception stack: {}", stackInfo.toString());
		return new Result<>(e);
	}
}
