/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brucelibrary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.microsoft.sqlserver.jdbc.*;

/**
 *
 * @author bruce
 */
public class AccessMSsqlDB {

    private String strConnString = "";
    private String strIP = "";
    private String strDBName = "";
    private String struser = "";
    private String strpassword = "";
    private Connection conn;
    private Statement stmt = null;
    private ResultSet rs = null;

    public void setStrIP(String strIP) {
	this.strIP = strIP;
    }

    public void setStrDBName(String strDBName) {
	this.strDBName = strDBName;
    }

    public void setStruser(String struser) {
	this.struser = struser;
    }

    public void setStrpassword(String strpassword) {
	this.strpassword = strpassword;
    }

    public String getStrIP() {
	return strIP;
    }

    public String getStrDBName() {
	return strDBName;
    }

    public String getStruser() {
	return struser;
    }

    public String getStrpassword() {
	return strpassword;
    }

    public AccessMSsqlDB() {

    }

    private void getConnection() throws Exception {
	try {
	    if (strIP.isEmpty() || strDBName.isEmpty() || struser.isEmpty() || strpassword.isEmpty()) {
		conn = null;
		throw new Exception("com.brucelibrary.db.AccessMSsqlDB.getConnection error！資料庫連線參數不足！");
	    }

	    strConnString = "jdbc:sqlserver://" + strIP + ";databaseName=" + strDBName + ";user=" + struser + ";password=" + strpassword + ";";
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    conn = DriverManager.getConnection(strConnString);
	} catch (Exception error) {
	    StackTraceElement[] errorTrace = error.getStackTrace();
	    StringBuilder strbError = new StringBuilder();
	    for (int i = 0; i < errorTrace.length; i++) {
		strbError.append(errorTrace[i].toString() + "\n");
	    }
	    throw new Exception("com.brucelibrary.db.AccessMSsqlDB.getConnection error！發生意外錯誤！\n" + error.getMessage() + "\n" + strbError.toString());
	}
    }

    public String[][] executeDBWithReturnB(String strSQL) throws Exception {
	ArrayList<String[]> list = new ArrayList<String[]>();
	String[][] res = null;
	int columns = 0;
	try {
	    getConnection();
	    StringBuilder strbSQL = new StringBuilder();
	    strbSQL.append(" begin try ");
	    strbSQL.append(" begin transaction ");
	    strbSQL.append(strSQL);
	    strbSQL.append("    commit transaction; ");
	    strbSQL.append(" end try ");
	    strbSQL.append(" begin catch");
	    strbSQL.append("    rollback;");
	    strbSQL.append("    select 'N' as Result,ERROR_MESSAGE() as Reason;");
	    strbSQL.append(" end catch; ");
	    stmt = conn.createStatement();
	    boolean results = stmt.execute(strbSQL.toString());
	    int count = 0;
	    do {
		if (results) {
		    ResultSet rs = stmt.getResultSet();
		    ResultSetMetaData metaData = rs.getMetaData();
		    columns = metaData.getColumnCount();
		    while (rs.next()) {
			String[] record = new String[columns];
			for (int i = 1; i <= columns; i++) {
			    record[i - 1] = rs.getString(i);
			}
			list.add(record);
		    }

		    rs.close();
		} else {
		    count = stmt.getUpdateCount();
		    if (count >= 0) {
			System.out.println("DDL or update data displayed here." + count);
		    } else {
			System.out.println("No more results to process.");
		    }
		}
		results = stmt.getMoreResults();
	    } while (results || count != -1);
//            stmt.close();
	    res = new String[list.size()][columns];
	    for (int i = 0; i < list.size(); i++) {
		for (int j = 0; j < columns; j++) {
		    System.out.println(list.get(i)[j]);
		    res[i][j] = list.get(i)[j];
		}
	    }
	    return res;
	} catch (Exception error) {
	    StackTraceElement[] errorTrace = error.getStackTrace();
	    StringBuilder strbError = new StringBuilder();
	    for (int i = 0; i < errorTrace.length; i++) {
		strbError.append(errorTrace[i].toString() + "\n");
	    }
	    throw new Exception("com.brucelibrary.db.AccessMSsqlDB.executeDBWithReturnB error！\n" + error.getMessage() + "\n" + strbError.toString());
	} finally {
	    if (rs != null) {
		try {
		    rs.close();
		} catch (SQLException error) {
		    StackTraceElement[] errorTrace = error.getStackTrace();
		    StringBuilder strbError = new StringBuilder();
		    for (int i = 0; i < errorTrace.length; i++) {
			strbError.append(errorTrace[i].toString() + "\n");
		    }
		    throw new Exception("com.brucelibrary.db.AccessMSsqlDB.executeDBWithReturnB_1 error\n" + error.getMessage() + "\n" + strbError.toString());
		}
	    }
	    if (stmt != null) {
		try {
		    stmt.close();
		} catch (SQLException error) {
		    StackTraceElement[] errorTrace = error.getStackTrace();
		    StringBuilder strbError = new StringBuilder();
		    for (int i = 0; i < errorTrace.length; i++) {
			strbError.append(errorTrace[i].toString() + "\n");
		    }
		    throw new Exception("com.brucelibrary.db.AccessMSsqlDB.executeDBWithReturnB_2 error\n" + error.getMessage() + "\n" + strbError.toString());
		}
	    }
	    if (conn != null) {
		try {
		    conn.close();
		} catch (SQLException error) {
		    StackTraceElement[] errorTrace = error.getStackTrace();
		    StringBuilder strbError = new StringBuilder();
		    for (int i = 0; i < errorTrace.length; i++) {
			strbError.append(errorTrace[i].toString() + "\n");
		    }
		    throw new Exception("com.brucelibrary.db.AccessMSsqlDB.executeDBWithReturnB_3 error\n" + error.getMessage() + "\n" + strbError.toString());
		}
	    }
	}
    }

    public ArrayList<String[]> queryFromPoolB(String strSQL) throws Exception {
	ArrayList<String[]> list = new ArrayList<String[]>();

	try {
	    getConnection();
	    if (conn == null) {
		return null;
	    }
	    stmt = conn.createStatement();
	    rs = stmt.executeQuery(strSQL);

	    ResultSetMetaData metaData = rs.getMetaData();
	    int columns = metaData.getColumnCount();
	    while (rs.next()) {
		String[] record = new String[columns];

		for (int i = 1; i <= columns; i++) {
		    record[i - 1] = rs.getString(i);
		}
		list.add(record);
	    }
	} catch (Exception error) {
	    StackTraceElement[] errorTrace = error.getStackTrace();
	    StringBuilder strbError = new StringBuilder();
	    for (int i = 0; i < errorTrace.length; i++) {
		strbError.append(errorTrace[i].toString() + "\n");
	    }
	    throw new Exception("com.brucelibrary.db.AccessMSsqlDB.queryFromPoolB error\n" + error.getMessage() + "\n" + strbError.toString());
	} finally {
	    if (rs != null) {
		try {
		    rs.close();
		} catch (SQLException error) {
		    StackTraceElement[] errorTrace = error.getStackTrace();
		    StringBuilder strbError = new StringBuilder();
		    for (int i = 0; i < errorTrace.length; i++) {
			strbError.append(errorTrace[i].toString() + "\n");
		    }
		    throw new Exception("com.brucelibrary.db.AccessMSsqlDB.queryFromPoolB_1 error\n" + error.getMessage() + "\n" + strbError.toString());
		}
	    }
	    if (stmt != null) {
		try {
		    stmt.close();
		} catch (SQLException error) {
		    StackTraceElement[] errorTrace = error.getStackTrace();
		    StringBuilder strbError = new StringBuilder();
		    for (int i = 0; i < errorTrace.length; i++) {
			strbError.append(errorTrace[i].toString() + "\n");
		    }
		    throw new Exception("com.brucelibrary.db.AccessMSsqlDB.queryFromPoolB_2 error\n" + error.getMessage() + "\n" + strbError.toString());
		}
	    }
	    if (conn != null) {
		try {
		    conn.close();
		} catch (SQLException error) {
		    StackTraceElement[] errorTrace = error.getStackTrace();
		    StringBuilder strbError = new StringBuilder();
		    for (int i = 0; i < errorTrace.length; i++) {
			strbError.append(errorTrace[i].toString() + "\n");
		    }
		    throw new Exception("com.brucelibrary.db.AccessMSsqlDB.queryFromPoolB_3 error\n" + error.getMessage() + "\n" + strbError.toString());
		}
	    }
	}
	return list;
    }

}
