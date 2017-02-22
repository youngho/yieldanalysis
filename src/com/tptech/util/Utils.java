package com.tptech.util;import com.tptech.log.WriteLogger;import org.apache.log4j.Logger;import java.io.*;import java.net.InetAddress;import java.net.UnknownHostException;import java.text.ParsePosition;import java.text.SimpleDateFormat;import java.util.ArrayList;import java.util.Calendar;import java.util.Date;import java.util.GregorianCalendar;import java.util.zip.GZIPOutputStream;public class Utils {	private static Logger logger = WriteLogger.getInstance("Utils.java");	public static int NOTIME = 0;	public static int TIMEONLY = 1;	public static int DATEONLY = 2;	public static int DATETIME = 3;	public static int NORMAL = 4;		//날짜형태가 Millis로 나타낸걸 pattern 형식으로 변경	public static String getDateMillis(String pattern, long longMillis) {		Calendar cal = new GregorianCalendar();		SimpleDateFormat sdf = new SimpleDateFormat(pattern);		cal.setTimeInMillis(longMillis);		String strRet = sdf.format(cal.getTime());		return strRet;	}		public static String getFormatDate(String date, String orignalformat, String waitformat){		Date d= null;		SimpleDateFormat dd = new SimpleDateFormat(orignalformat);		ParsePosition parse = new ParsePosition(0);		d = dd.parse(date, parse);		Calendar cal = Calendar.getInstance();		cal.setTime(d);		SimpleDateFormat sdf = new SimpleDateFormat(waitformat);		String day = sdf.format(cal.getTime());		return day;	}	/**	 * 	 * @param offset	 * @return	 */	public static String getDate(String pattern, int offset) {		SimpleDateFormat sdf = new SimpleDateFormat(pattern);		Calendar cal = Calendar.getInstance();		cal.add(Calendar.DATE, offset);		String strRet = sdf.format(cal.getTime());		return strRet;	}	/**	 * 	 * @param date	 * @param pattern	 * @param offset	 * @return	 */	public static String getDate(String date, String pattern, int offset) {		int iYear;		int iMonth;		int iDate;		String strRet;		iYear = Integer.parseInt(date.substring(0, 4));		iMonth = Integer.parseInt(date.substring(4, 6)) - 1;		iDate = Integer.parseInt(date.substring(6, 8));		SimpleDateFormat sdf = new SimpleDateFormat(pattern);		Calendar cal = Calendar.getInstance();		cal.set(iYear, iMonth, iDate);		cal.add(Calendar.DATE, offset);		strRet = sdf.format(cal.getTime());		return strRet;	}	/**	 * @param astrDate	 * @return	 */	public static int getDayOfWeek(String astrDate) {		int iYear;		int iMonth;		int iDate;		iYear = Integer.parseInt(astrDate.substring(0, 4));		iMonth = Integer.parseInt(astrDate.substring(4, 6)) - 1;		iDate = Integer.parseInt(astrDate.substring(6, 8));		Calendar cal = Calendar.getInstance();		cal.set(iYear, iMonth, iDate);		return cal.get(Calendar.DAY_OF_WEEK);	}	/**	 * IP, 호스트 네임이 있는지 확인 후 Return	 * @return	 */	public static String getMachineName() {		String strHost;		try {			strHost = InetAddress.getLocalHost().getHostName();		} catch (UnknownHostException ex) {			logger.error(ex.toString());			strHost = "unknown";		}		return strHost;	}	public static String getCurrentTime(String TimeMode) {		Calendar today = Calendar.getInstance();		SimpleDateFormat dateformat = new SimpleDateFormat(TimeMode);		String day = dateformat.format(today.getTime());		return day;	}	/* 4000 Byte 미만씩 잘라준다 */	public static String strLength(String arg1) {		String ngsub = null;		String ngsub2 = null;		if (arg1.getBytes().length >= 4000) {			int src = arg1.substring(0, 4000).lastIndexOf(",");			ngsub = arg1.substring(0, src + 1);			ngsub2 = arg1.substring(src + 1, arg1.getBytes().length);		}		return ngsub + "-" + ngsub2;	}		 //파일을 gz으로 압축하고 원본 파일은 삭제한다.  public static void zipping(File fromhuge, String tozip) throws IOException {           FileInputStream in=new FileInputStream(fromhuge);           GZIPOutputStream out =new GZIPOutputStream(new FileOutputStream(tozip));           byte[] buffer =new byte[4096];           int bytes_read;           while ((bytes_read=in.read(buffer)) !=-1)                   out.write(buffer,0,bytes_read);           in.close();           out.close();           fromhuge.delete();  }    //file delete  public static void fileDelete(File delFile){	  try{		  delFile.delete();	  }catch (Exception ex){		  logger.error(ex.toString());	  }  }      public void SmtOscarFileCreate(ArrayList<String> aFileLine, File FileName, String sSbl){	  boolean abCR = true;	  BufferedWriter bw;	  	  fileDelete(FileName);	  		try {				bw = new BufferedWriter(new FileWriter(FileMoveUtil.getDirpath("conFile") + "/" + FileName.getName(), true));						for(int i=0; i<aFileLine.size(); i++){				if(!aFileLine.get(i).contains("SBL_CODE")){					bw.write(aFileLine.get(i));					if(aFileLine.get(i).contains("PGMVER_START")){						bw.newLine();						bw.write(sSbl);					}					if( abCR == true)						bw.newLine();								}			}			bw.close();		} catch (IOException ex) {			logger.error(ex.toString());		}  }}