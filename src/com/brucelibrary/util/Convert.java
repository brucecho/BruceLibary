/*
 * 這個套件負責一些資料轉換的工作
 */
package com.brucelibrary.util;

/**
 *
 * @author bruce
 */
import java.util.Vector;

// Referenced classes of package jcx.util:
//            check, datetime, operation
public class Convert {

    public static String add0(String paramString1, String paramString2) {
	String str1 = paramString2;
	String str2 = "-1";
	if (!Check.isNum(str1)) {
	    return str2;
	}
	int i = Integer.parseInt(str1);
	int j = i - paramString1.length();
	for (int k = 0; k < j; k++) {
	    paramString1 = "0" + paramString1;
	}
	return paramString1;
    }

    public static String ToNumeric(String paramString1, String paramString2) {
	if (paramString1.length() == 0) {
	    return paramString1;
	}
	if (paramString2.indexOf(".") == -1) {
	    return paramString1;
	}
	String[] arrayOfString = separStr(paramString2, ".");
	int i;
	try {
	    i = paramString1.length() - Integer.parseInt(arrayOfString[1]);
	} catch (Exception localException) {
	    return paramString1;
	}
	String str = paramString1.substring(0, i) + "." + paramString1.substring(i);
	if (str.indexOf(".") == 0) {
	    str = "0" + str;
	} else if ((str.indexOf("+") == 0) && (str.indexOf(".") == 1)) {
	    str = "+0." + str.substring(2);
	} else if ((str.indexOf("-") == 0) && (str.indexOf(".") == 1)) {
	    str = "-0." + str.substring(2);
	}
	return str;
    }

    public static String[] separStr(String s, String s1) {
	if (s1.length() != 0) {
	    Vector vector = new Vector();
	    int i = 0;
	    for (int j = 0; (j = s.indexOf(s1, i)) != -1;) {
		vector.addElement(s.substring(i, j));
		i = j + s1.length();
	    }

	    vector.addElement(s.substring(i));
	    String as2[] = new String[vector.size()];
	    for (int i1 = 0; i1 < as2.length; i1++) {
		as2[i1] = (String) vector.elementAt(i1);
	    }

	    return as2;
	}
	String as[] = {
	    "!-1!"
	};
	String as1[] = new String[0];
	if (s.trim().length() == 0) {
	    return as1;
	}
	int k = s1.length();
	if (s.indexOf(s1) == -1) {
	    String as3[] = {
		s
	    };
	    return as3;
	}
	try {
	    String s2 = s.substring(s.length() - k);
	    if (s2.equals(s1)) {
		s = s.substring(0, s.length() - k);
	    }
	    String s3 = s.substring(0, k);
	    if (s3.equals(s1)) {
		s = s.substring(k);
	    }
	    if (s.indexOf(s1) == -1) {
		String as5[] = {
		    s
		};
		return as5;
	    }
	} catch (IndexOutOfBoundsException indexoutofboundsexception) {
	    return as;
	}
	int l = 0;
	for (int j1 = 0; j1 < s.length() && j1 + k <= s.length(); j1++) {
	    if (s.substring(j1, j1 + k).equals(s1)) {
		l++;
	    }
	}

	String as4[] = new String[l + 1];
	int k1 = 0;
	try {
	    for (int l1 = 0; l1 < l; l1++) {
		int j2 = k1;
		do {
		    if (j2 >= s.length() || j2 + k > s.length()) {
			break;
		    }
		    if (s.substring(j2, j2 + k).equals(s1)) {
			as4[l1] = s.substring(k1, j2);
			k1 = j2 + k;
			break;
		    }
		    j2++;
		} while (true);
	    }

	} catch (IndexOutOfBoundsException indexoutofboundsexception1) {
	    return as;
	}
	as4[l] = s.substring(k1);
	int i2 = 0;
	for (int k2 = 0; k2 < as4.length; k2++) {
	    if (!as4[k2].equals("")) {
		i2++;
	    }
	}

	if (i2 == as4.length) {
	    return as4;
	}
	String as6[] = new String[i2];
	int l2 = 0;
	for (int i3 = 0; i3 < as4.length; i3++) {
	    if (!as4[i3].equals("")) {
		as6[l2++] = as4[i3];
	    }
	}

	return as6;
    }

}
