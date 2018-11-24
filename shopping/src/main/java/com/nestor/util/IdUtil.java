package com.nestor.util;

import java.util.UUID;

public class IdUtil {
	public static String generateId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
}
