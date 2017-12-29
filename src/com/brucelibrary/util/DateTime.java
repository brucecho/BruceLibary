/*
 * 這個套件負責處理有關時間的運算
 */
package com.brucelibrary.util;

/**
 *
 * @author bruce
 */
public class DateTime {

    /**
     * 計算兩個時間的相差秒數
     * 輸入值：
     * 開始時間(hh:mm:ss)
     * 結束時間(hh:mm:ss)
     * 回傳值：
     * 時間長度：hh:mm:ss:ms
     *
     * @param strStartTime
     * @param strEndTime
     * @return
     * @throws Exception
     */
    public static String getDuration(String strStartTime, String strEndTime) throws Exception {
	if (strStartTime == null || strStartTime.length() < 8 || strEndTime == null || strEndTime.length() < 8) {
	    return "";
	}
	try {
	    int intStartTime = timeToMSecond(strStartTime);
	    int intEndTime = timeToMSecond(strEndTime);
	    int intDuration = intEndTime - intStartTime;
	    return msecondFormat(intDuration);
	} catch (Exception error) {
	    throw new Exception(error.getMessage());
	}
    }

    /**
     * 將傳入的時間轉換成毫秒
     * 傳入值：(HH:mm:ss:SSS)或(HH:mm:ss)
     * 回傳值：毫秒
     *
     * @param strtime
     * @return
     */
    public static int timeToMSecond(String strtime) {
	String strArray[] = strtime.split(":");
	if (strtime == null || strtime.length() < 8) {
	    return 0;
	} else if (strtime.length() == 8) {
	    strtime = strtime + ":000";
	}
	int limit = 3600;
	int msecond = 0;
	for (int i = 0; i < strArray.length; i++) {
	    msecond += Integer.parseInt(strArray[i]) * limit;
	    limit /= 60;
	}
	msecond = msecond * 1000 + Integer.parseInt(strArray[strArray.length - 1]);
	return msecond;
    }

    /**
     * 將傳入的毫秒轉換成時間格式
     * 傳入值：毫秒
     * 回傳值：(HH:mm:ss:SSS)
     *
     * @param second
     * @return
     */
    public static String msecondFormat(int second) {
	String ms = Integer.toString(second % 1000);
	int sec = second / 1000;
	int limit = 3600;
	int intT[] = new int[3];
	String strT[] = new String[3];
	for (int i = 0; i < 3; i++) {
	    intT[i] = sec / limit;
	    strT[i] = Integer.toString(intT[i]).length() < 2 ? "0" + Integer.toString(intT[i]) : Integer.toString(intT[i]);
	    sec -= (intT[i] * limit);
	    limit /= 60;
	}
	if (ms.length() < 3) {
	    for (int i = 0; i <= (3 - ms.length()); i++) {
		ms = "0" + ms;
	    }
	}
	return strT[0] + ":" + strT[1] + ":" + strT[2] + ":" + ms;
    }
}
