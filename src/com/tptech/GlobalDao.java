package com.tptech;
import java.sql.*;import java.util.*; import org.apache.log4j.Logger;
import com.tptech.conf.PropertiesReader;import com.tptech.log.WriteLogger;import com.tptech.util.*;
public class GlobalDao {
	private static Logger logger = WriteLogger.getInstance("GlobalDao.java");
	public static Vector<String[]> selectQuery(String sSql) throws SQLException {		Connection conn = null;		Statement st = null;     		ResultSet rs = null;		Vector<String[]> vReturn = null;
		// 인자로 넘어온 것들을 확인		if (sSql == null || sSql.equals("")) {			String m_sError = null;			if (sSql == null)				m_sError = "sSql에 null이 넘어왔습니다.";			else if (sSql.equals(""))				m_sError = "sSql 문에 아무것도 명령어가 없습니다.";			throw new SQLException(m_sError);		}
		try {			DbUtils db = new DbUtils();			PropertiesReader prop  = new PropertiesReader();
			conn = db.getConnection(prop.getProperties("DB_DRIVER"), prop.getProperties("DB_URL"), prop.getProperties("DB_USER"), prop.getProperties("DB_PASS"));			st = conn.createStatement();			rs = st.executeQuery(sSql);
			int nColumnCount = rs.getMetaData().getColumnCount();			if (nColumnCount != 0) {				vReturn = new Vector<String[]>();				while (rs.next()) {					String[] arrRow = new String[nColumnCount];					for (int nIndex = 0; nIndex < nColumnCount; nIndex++) {						arrRow[nIndex] = rs.getString(nIndex + 1);					}					vReturn.add(arrRow);				}			}		} catch (SQLException ex) {			logger.error(ex.toString());			//throw ex;		} finally {			try {				rs.close();				st.close();				conn.close();			} catch (Exception ex) {				logger.error(ex.toString());			}		}		return vReturn;	}

	public static String selectQuerys(String sSql)throws SQLException {
		Connection conn = null;		Statement st = null;     		ResultSet rs = null;		String sReturn = null;
		// 인자로 넘어온 것들을 확인		if (sSql == null || sSql.equals("")) {			String m_sError = null;			if (sSql == null)				m_sError = "sSql에 null이 넘어왔습니다.";			else if (sSql.equals(""))				m_sError = "sSql 문에 아무것도 명령어가 없습니다.";			throw new SQLException(m_sError);		}
		try {			DbUtils db = new DbUtils();			PropertiesReader prop  = new PropertiesReader();			conn = db.getConnection(prop.getProperties("DB_DRIVER"), prop.getProperties("DB_URL"), prop.getProperties("DB_USER"), prop.getProperties("DB_PASS"));						st = conn.createStatement();			rs = st.executeQuery(sSql);
			int nColumnCount = rs.getMetaData().getColumnCount();			if (nColumnCount != 0) {				while (rs.next()) {					sReturn= rs.getString(1);				}			}		} catch (SQLException ex) {			logger.error(ex.toString());			//throw ex;		} finally {			try {				rs.close();				st.close();				conn.close();			} catch (Exception ex) {				logger.error(ex.toString());			}		}		return sReturn;	}		public static int selectQueryInt(String sSql)throws SQLException {		Connection conn = null;		Statement st = null;     		ResultSet rs = null;		int iReturn = 0;		// 인자로 넘어온 것들을 확인		if (sSql == null || sSql.equals("")) {			String m_sError = null;			if (sSql == null)				m_sError = "sSql에 null이 넘어왔습니다.";			else if (sSql.equals(""))				m_sError = "sSql 문에 아무것도 명령어가 없습니다.";			throw new SQLException(m_sError);		}		try {			DbUtils db = new DbUtils();			PropertiesReader prop  = new PropertiesReader();			conn = db.getConnection(prop.getProperties("DB_DRIVER"), prop.getProperties("DB_URL"), prop.getProperties("DB_USER"), prop.getProperties("DB_PASS"));						st = conn.createStatement();			rs = st.executeQuery(sSql);			int nColumnCount = rs.getMetaData().getColumnCount();			if (nColumnCount != 0) {				while (rs.next()) {					iReturn= rs.getInt(1);				}			}		} catch (SQLException ex) {			logger.error(ex.toString());			//throw ex;		} finally {			try {				rs.close();				st.close();				conn.close();			} catch (Exception ex) {				logger.error(ex.toString());			}		}		return iReturn;	}				
	/**	 * NICE시스템에서 DB INSERT, DETETE, UPDATE	 * @param dbName db.properties에 선언된 DB Name	 * @param sSql SQL 문	 * @return 쿼리 실행한 row수, 0 = 실패	 * @throws SQLException	 */
	public static int executeUpdate(String sSql) throws SQLException {
		Connection conn = null;		Statement st = null;		int nReturn = 0;
		try {			DbUtils db = new DbUtils();			PropertiesReader prop  = new PropertiesReader();						conn = db.getConnection(prop.getProperties("DB_DRIVER"), prop.getProperties("DB_URL"), prop.getProperties("DB_USER"), prop.getProperties("DB_PASS"));						st = conn.createStatement();			nReturn = st.executeUpdate(sSql);			conn.commit();		} catch (SQLException ex) {		    System.out.println("============> " + ex.toString());			logger.error(ex.toString());		} finally {			try {				st.close();				conn.close();			} catch (Exception ex) {			    System.out.println("============> " + ex.toString());				logger.error(ex.toString());			}		}		return nReturn;	}
	public static int executeUpdate(String[] sSql) throws SQLException {		Connection conn = null;		Statement st = null;		int nReturn = 0;
		DbUtils db = new DbUtils();		PropertiesReader prop  = new PropertiesReader();		conn = db.getConnection(prop.getProperties("DB_DRIVER"), prop.getProperties("DB_URL"), prop.getProperties("DB_USER"), prop.getProperties("DB_PASS"));
		try {			for (int i=0; i<sSql.length ; i++) {			st = conn.createStatement();			nReturn = st.executeUpdate(sSql[i]);
			}		} catch (SQLException ex) {			logger.error(ex.toString());			//throw ex;		} finally {			try {				st.close();				conn.close();			} catch (Exception ex) {				logger.error(ex.toString());			}		}		return nReturn;	}			/**     * NICE시스템에서 DB INSERT, DETETE, UPDATE     * @param dbName db.properties에 선언된 DB Name     * @param sSql SQL 문     * @return 쿼리 실행한 row수, 0 = 실패     * @throws SQLException     */    public static int executePlsql(String sSql) throws SQLException {                System.out.println("sSql ==> " + sSql);                Connection conn = null;        CallableStatement cstmt = null;        int nReturn = 0;        try {            DbUtils db = new DbUtils();            PropertiesReader prop = new PropertiesReader();            conn = db.getConnection(prop.getProperties("DB_DRIVER"), prop.getProperties("DB_URL"), prop.getProperties("DB_USER"), prop.getProperties("DB_PASS"));            cstmt = conn.prepareCall("{call P_DUT_BIN(?)}");            cstmt.setString(1, sSql);            nReturn = cstmt.executeUpdate();                        System.out.println("nReturn =========> " + nReturn);                        conn.commit();        } catch (SQLException ex) {            System.out.println("==> " + ex);            logger.error(ex.toString());        } finally {            try {                if (cstmt != null)                    cstmt.close();                if (conn != null)                    conn.close();            } catch (Exception ex) {                logger.error(ex.toString());            }        }        return nReturn;    }}
