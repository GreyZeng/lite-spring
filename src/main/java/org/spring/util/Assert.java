package org.spring.util;
/**
 * @author zenghui
 * 2020/8/2
 */
public abstract class Assert {
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}
}
