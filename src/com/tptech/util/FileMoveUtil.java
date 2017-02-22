package com.tptech.util;
import java.io.*;import java.util.Vector;import org.apache.log4j.Logger;
import com.tptech.Hist_Variable;import com.tptech.conf.PropertiesReader;import com.tptech.log.WriteLogger;
public class FileMoveUtil {
	private static Logger logger = WriteLogger.getInstance("FileMoveUtil.java");
	//파일복사	public static void copyFile(File in, File out) throws Exception {
	    String sPath = out.getParent();		File file = new File(sPath);		if (!file.exists()) {			file.mkdir();		}				FileInputStream fis  = new FileInputStream(in);	    FileOutputStream fos = new FileOutputStream(out);
	    byte[] buf = new byte[1024];	    int i = 0;	    while((i=fis.read(buf))!=-1) {	      fos.write(buf, 0, i);	      }	    fis.close();	    fos.close();	}		
	/* 파일을 이동 복사 시키는 부분 */	public static Vector<String> executeRuntime(String command) {		Vector<String> v = null;		try {			Process process = Runtime.getRuntime().exec(command);			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while (true) {				String line = in.readLine();				if (line == null)					break;				if (v == null)					v = new Vector<String>();
				v.addElement(line);			}
			BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while (true) {				String line = err.readLine();				if (line == null)					break;			}
			process.waitFor();		} catch (Exception e) {			logger.error(e.toString());
		}		return v;	}
	/* path에 따라 디렉토리 설정 */	public static String getDirpath(String path) {
		PropertiesReader prop  = new PropertiesReader();	

		String Directory = "";		if (path.equals("conFile")) {			Directory = prop.getProperties("CON_FILE_DIR");		} else if (path.equals("conFormat")) {			Directory = prop.getProperties("CON_FILE_ERR");		} else if (path.equals("conInsert")) {			Directory = prop.getProperties("CON_INSERT_ERR");		} else if (path.equals("conSuccess")) {			Directory = prop.getProperties("CON_FILE_BACK");		} else if (path.equals("rowFile")) {			Directory = prop.getProperties("ROW_FILE_DIR");		} else if (path.equals("rowSuccess")) {			Directory = prop.getProperties("ROW_FILE_BACK");				} else if (path.equals("rowFormat")) {			Directory = prop.getProperties("ROW_FILE_ERR");							}

		return Directory;
	}

	public static boolean moveFile(File f, String strNewFile) {
		boolean result = false;		File DelFile = new File(strNewFile + f.getName());		DelFile.delete();		String strName = strNewFile.substring(strNewFile.lastIndexOf("/"), strNewFile.length());
		try {			if (!strName.contains(".")) {// Dir -> Dir + existing file name				if (!strNewFile.endsWith("/")) {					File nDir = new File(strNewFile);					if (!nDir.exists()) {						nDir.mkdir();					}					strNewFile = strNewFile + "/";				}				strNewFile = strNewFile + f.getName();			} else {				String strPath = strNewFile.substring(0, strNewFile.lastIndexOf("/"));
				File file = new File(strPath);				if (!file.exists()) {					file.mkdir();				}			}
			File f1 = new File(strNewFile);			result = f.renameTo(f1);
			// └> remove dir/file name
		} catch (Exception ex) {			logger.error(ex.toString());
		}		return result; // succes -> return:true else -> return:false	}
	/* 데이터 파일을 정상적으로 insert 시키고 폴더로 이동시 폴더가 없을때 생성해준다. */	public static void makeDirectory(String directory) {
		//File dir = new File(FileMoveUtil.getDirpath("Success") + "/" + Hist_Variable.sData_Format + "/" + directory);		File dir = new File(directory);
		if (!dir.exists()) {			dir.mkdirs();			logger.info("[mkdir] " +  directory);		}	}

/*	public static void makeDirectory2(String directory) {
		File dir = new File(FileMoveUtil.getDirpath("Format") + "/" + directory);
		if (!dir.exists()) {			dir.mkdirs();			logger.info("[mkdir] " + FileMoveUtil.getDirpath("Format") + "/" + directory);		}	}
	public static void makeDirectory3(String directory) {
		File dir = new File(FileMoveUtil.getDirpath("Insert") + "/" + directory);
		if (!dir.exists()) {			dir.mkdirs();			logger.info("[mkdir] " + FileMoveUtil.getDirpath("Insert") + "/" + directory);		}	}*/
}
