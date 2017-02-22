package com.tptech.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;import org.apache.log4j.Logger;

import com.tptech.Hist_Variable;import com.tptech.conf.PropertiesReader;import com.tptech.log.WriteLogger;
public class DbUtils {		private static Logger logger = WriteLogger.getInstance("DbUtils.java");	public Connection conn;
	public DbUtils() {		conn = null;	}
	public Connection getConnection(String astrDriver, String astrUrl, String astrUser, String astrPass) {
		try {			Class.forName(astrDriver);			DriverManager.setLoginTimeout(60000);			conn = DriverManager.getConnection(astrUrl, astrUser, astrPass);
			if (conn == null) {				logger.error("DB Connection Erro");				return null;			} else {				return conn;			}		} catch (ClassNotFoundException ex) {			logger.error(ex.toString());			return null;		} catch (SQLException ex) {			logger.error(ex.toString());			return null;		}	}	
	public void closeConnection() {		try {			if (conn != null)				conn.close();		} catch (SQLException ex) {			logger.error(ex.toString());			//Utils.makeLog("DbUtils  closeConnection ERROR : " + ex, Hist_Variable.sData_Format + "_Yield_Hist");		}	}			}
