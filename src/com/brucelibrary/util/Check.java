/*
 * 這個套件負責一些檢覈的工作
 */
package com.brucelibrary.util;

/**
 *
 * @author bruce
 */
public class Check {
/*
    
    */
    public static boolean isNum(String paramString) {
	if (paramString.length() == 0) {
	    return false;
	}
	if (paramString.startsWith("-")) {
	    paramString = paramString.substring(1);
	}
	for (int i = 0; i < paramString.length(); i++) {
	    int j = paramString.charAt(i);
	    if ((j < 48) || (j > 57)) {
		return false;
	    }
	}
	return true;
    }
}
