package com.ktiteng.util;

import java.util.UUID;

public class Utils {
	private Utils() {
	}

	public static String getId() {
		return UUID.randomUUID().toString();
	}
}
