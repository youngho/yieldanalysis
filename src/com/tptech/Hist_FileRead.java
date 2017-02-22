package com.tptech;
import java.io.BufferedReader;import java.io.File;import java.io.FileReader;import java.util.ArrayList;import java.util.Vector;import org.apache.log4j.Logger;
import com.tptech.log.WriteLogger;
public class Hist_FileRead {	private static Logger logger = WriteLogger.getInstance("Hist_FileRead.java");
	private Vector<String> list;
	/* 파일을 읽어서 Vector에 넣어준다. */	public void readTexts(String fname) {		FileReader fr = null;		BufferedReader br = null;		list = new Vector<String>();		Hist_Variable.aFileLine.clear();		File ff = null;		try {			ff = new File(fname);			fr = new FileReader(ff);			br = new BufferedReader(fr);			String str = null;
			do {				str = br.readLine();				if (str != null && !str.equals("")) {					list.add(str);					Hist_Variable.aFileLine.add(str);					if(str.contains("OP_INPUT")){						Hist_Variable.hs.put("OP_CNT", str);					}else if(str.contains("HD_INPUT")){						Hist_Variable.hs.put("HANDLER_CNT", str);					}else if(str.contains("EQP_INPUT")){						Hist_Variable.hs.put("EQP_CNT", str);					}				}			} while (!(str == null));		} catch (Exception ee) {			logger.error(ee.toString());		} finally {			try {				if (br != null) {					br.close();				}				if (fr != null) {					fr.close();				}			} catch (Exception eee) {				logger.error(eee.toString());			}		}	}
	public Vector<String> getAllLines() {		return list;	}}
