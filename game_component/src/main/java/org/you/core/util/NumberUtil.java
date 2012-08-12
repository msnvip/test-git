package org.you.core.util;

public class NumberUtil {

	public static int getInt(String i){
		try {
			return Integer.parseInt(i);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static long getLong(String i){
		try {
			return Long.parseLong(i);
		} catch (Exception e) {
			return 0L;
		}
	}
	
}
