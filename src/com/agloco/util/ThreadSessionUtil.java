package com.agloco.util;

public class ThreadSessionUtil {

	private static final ThreadLocal session = new ThreadLocal();

	private ThreadSessionUtil() {
		
	}
	
	public static Object get() {
		return session.get();
	}

	public static void set(Object obj) {
		session.set(obj);
	}

	public static void close() {
		session.set(null);
	}
}
