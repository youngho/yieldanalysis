package com.tptech;

import java.util.Date;
import java.util.TimerTask;import org.apache.log4j.Logger;

import com.tptech.log.WriteLogger;import com.tptech.util.Utils;

public class TimerRun extends TimerTask{	private static Logger logger = WriteLogger.getInstance("TimerRun.java");	
	public void run(){		logger.info("killProcess Start");		logger.info("killProcess END  ==>  "+new Date());
		//Utils.makeLog("killProcess Start", Hist_Variable.sData_Format + "_Yield_Hist");		//Utils.makeLog("killProcess END  ==>  "+new Date(), Hist_Variable.sData_Format + "_Yield_Hist");
		System.exit(0);	}}
