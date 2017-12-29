/**
 * This class response for action of files
 */
package com.brucelibrary.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FileAccess {

//    private static String strLog = "C:\\LOG\\BruceLibERROR";
    /**
     * 給前端應用程式寫紀錄
     *
     * @param strFilePath
     * @param strMessage
     */
//    public static void writeLog(String strFilePath, String strMessage) throws Throwable {
//	//先行定義時間格式
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//	//取得現在時間
//	Date dt = new Date();
//	//透過SimpleDateFormat的format方法將Date轉為字串
//	String dts = sdf.format(dt);
//	strMessage = dts + " " + strMessage;
//
//	String strfilename = "";
//	Calendar calendar = Calendar.getInstance();
//	String stryear = Integer.toString(calendar.get(Calendar.YEAR));
//	String strmonth = Integer.toString(calendar.get(Calendar.MONTH) + 1);
//	String strday = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
//	if (strFilePath == null || strFilePath.length() == 0) {
//	    strfilename = "C:\\LOG\\ERROR_" + stryear + "_" + strmonth + "_" + strday + ".txt";
//	} else {
//	    strfilename = strFilePath + "_" + stryear + "_" + strmonth + "_" + strday + ".txt";
//	}
//	if (exists(strfilename)) {
//	    writeToTXT(strMessage, strfilename, "utf8", true);
//	} else {
//	    createNewFile(strfilename);
//	    writeToTXT(strMessage, strfilename, "utf8", false);
//	}
//    }
    /**
     * 將字串資料寫入指定的檔案中，資料附加在檔案的最後面，編碼用UTF-8碼
     *
     * @param strFilePath 檔案名稱
     * @param strMessage 要加入的字串
     */
    public static void writeFile(String strFilePath, String strMessage) throws Throwable {
	if (exists(strFilePath)) {
	    writeFile(strMessage, strFilePath, "utf8", true);
	} else {
	    createNewFile(strFilePath);
	    writeFile(strMessage, strFilePath, "utf8", false);
	}
    }

    /**
     * 將字串資料寫入指定的檔案中，可選擇資料附加方式，編碼用UTF-8碼
     *
     * @param strFilePath 檔案名稱
     * @param strMessage 要加入的字串
     * @param blAppend 是否附加在檔案後面
     * @throws Exception
     */
    public static void writeFile(String strFilePath, String strMessage, Boolean blAppend) throws Exception {
	if (exists(strFilePath)) {
	    writeFile(strMessage, strFilePath, "utf8", blAppend);
	} else {
	    createNewFile(strFilePath);
	    writeFile(strMessage, strFilePath, "utf8", blAppend);
	}
    }

    /**
     * 檢查檔案是否存在
     *
     * @param filename 檔案名稱
     * @return true 檔案已存在 | false 檔案不存在
     */
    public static boolean exists(String strFileName) {
	return new File(strFileName).exists();
    }

    /**
     * 檢查檔案是否存在
     *
     * @param fileCheckFile 檔案物件
     * @return
     */
    public static boolean exists(File fileCheckFile) {
	return fileCheckFile.exists();
    }

    /**
     * 建立新檔
     *
     * @param strFileName 檔案名稱
     * @throws Exception
     */
    private static void createNewFile(String strFileName) throws Exception {
	try {
	    File tempFile = new File(strFileName);
	    String strPathName = tempFile.getParent();
	    File tempDirectonary = new File(strPathName);
	    if (!tempDirectonary.exists()) {
		tempDirectonary.mkdirs();
	    }
	    tempFile.createNewFile();
	} catch (Exception e) {
	    throw new Exception("com.brucelibrary.util.FileAccess.createNewFile" + e.getMessage());
	}
    }

    /**
     * 刪除檔案
     *
     * @param path
     * @throws Exception
     */
    private static void deleteFile(String path) throws Exception {

	File file = new File(path);
	file.delete();

    }

    /**
     * 將字串資料寫入指定的檔案中，可指定編碼格式及資料附加方式
     *
     * @param strtext 要加入的字串
     * @param strfilename 檔案名稱
     * @param strformat 編碼方式
     * @param blappend 是否附加在檔案後面
     * @throws Exception
     */
    private static void writeFile(String strtext, String strfilename, String strformat, boolean blappend) throws Exception {
	if (strtext.equals("")) {
	    return;
	}
	File file = new File(strfilename);//建立檔案，準備寫檔
	try {
	    BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, blappend), strformat));
	    bufWriter.write(strtext);
	    bufWriter.newLine();
	    bufWriter.close();
	} catch (Exception error) {
	    throw new Exception("com.brucelibrary.util.FileAccess.writeFile" + error.getMessage());
	} finally {
	}
    }

    /**
     * 依傳入的檔案路徑，讀取檔案資料後傳回String一維陣列
     *
     * @param strFilePath
     * @return
     * @throws Throwable
     */
    public static String[] readTxt(String strFilePath) throws Exception {
	String[] rtnString = null;
	ArrayList<String> alData = new ArrayList<String>();
	try {
	    if (!exists(strFilePath)) {
		return rtnString;
	    }
//            FileReader fr = new FileReader(strFilePath);
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(strFilePath), "UTF-8"));
	    while (br.ready()) {
		alData.add(br.readLine());
	    }
	    br.close();
//            fr.close();
	    rtnString = new String[alData.size()];
	    for (int i = 0; i < alData.size(); i++) {
		rtnString[i] = alData.get(i);
	    }
	    return rtnString;
	} catch (Exception error) {
	    throw new Exception("com.brucelibrary.util.FileAccess.readTxt" + error.getMessage());
	}
    }

    /**
     * 將來源檔案搬移成為目的檔案
     *
     * @param 來源檔案
     * @param 目的檔案
     *
     * @return
     * @throws Throwable
     */
    public static String MoveFile(String strFromFile, String strToFile) throws Exception {
	try {
	    File fileFromFile = new File(strFromFile);
	    if (!fileFromFile.exists() || fileFromFile.isDirectory()) {
		return "N";
	    }
	    File fileToFile = new File(strToFile);
	    File fileToFilePath = new File(fileToFile.getParent());
	    if (!fileToFilePath.exists()) {
		fileToFilePath.mkdirs();
	    }
	    fileFromFile.renameTo(fileToFile);
	} catch (Exception error) {
	    throw new Exception("com.brucelibrary.util.FileAccess.MoveFile" + error.getMessage());
	}
	return "Y";
    }

    /**
     * 將來源檔案複製成目的端檔案
     *
     * @param source
     * @param dest
     * @throws Exception
     */
    public static boolean CopyFileUsingFileChannels(File source, File dest) throws Exception {
	FileChannel inputChannel = null;
	FileChannel outputChannel = null;
	boolean result = false;
	try {
	    inputChannel = new FileInputStream(source).getChannel();
	    outputChannel = new FileOutputStream(dest).getChannel();
	    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	    result = true;
	} catch (Exception error) {
	    result = false;
	    throw new Exception("com.brucelibrary.util.FileAccess.CopyFileUsingFileChannels" + error.getMessage());
	} finally {
	    inputChannel.close();
	    outputChannel.close();
	    return result;
	}
    }

    public static boolean CopyFileUsingFileChannels(String source, String dest) throws Exception {
	FileChannel inputChannel = null;
	FileChannel outputChannel = null;
	boolean result = false;
	try {
	    inputChannel = new FileInputStream(new File(source)).getChannel();
	    outputChannel = new FileOutputStream(new File(dest)).getChannel();
	    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	    result = true;
	} catch (Exception error) {
	    result = false;
	    throw new Exception("com.brucelibrary.util.FileAccess.CopyFileUsingFileChannels" + error.getMessage());
	} finally {
	    inputChannel.close();
	    outputChannel.close();
	    return result;
	}
    }
}
