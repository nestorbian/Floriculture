package com.nestor.util;

/**
 * <p>ThreadLocal key:当前线程，value:Object类型</p>
 * @author Lenovo
 *
 */
public class ThreadLocalUtil {
	
	private static ThreadLocal<Object> threadLocal = new ThreadLocal<>();
	
	public static Object getValue() {
		return threadLocal.get();
	}
	
	public static void setValue(Object value) {
		threadLocal.set(value);
	}
}
