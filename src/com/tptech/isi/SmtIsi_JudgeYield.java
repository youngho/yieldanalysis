package com.tptech.isi;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.tptech.log.WriteLogger;

public class SmtIsi_JudgeYield {

	private static Logger logger = WriteLogger.getInstance("SmtSbl_JudgeYield.java");
	
	public void run(SmtIsi_Parameter clsParam, SmtIsi_Stdinfo clsStd){
		//SBL_Parameter clsParam = new SBL_Parameter();
		//SBL_Stdinfo clsStd = new SBL_Stdinfo();

		clsParam.clearStepValue();
		clsParam.logPass = "Y";
		clsParam.logJudge1 = "YIELD";		
		

		if(!clsParam.flag_Stop)
		{
			yield(clsParam, clsStd);
			clsParam.ctrlFlag(false);
		}
		
		if(!clsParam.flag_Stop)
		{
			failsum(clsParam, clsStd);
			clsParam.ctrlFlag(false);
		}
	}
	
	
	private void yield(SmtIsi_Parameter pParam, SmtIsi_Stdinfo pStd){
		pParam.logJudge2 = "YIELD";	
		try{
			if(pParam.OP_TOTAL <= 0){
				pParam.logPass = "ERROR";
				pParam.logReason ="[OP_TOTAL<=0] => ";
				pParam.logReason += "OP_TOTAL=|" + String.valueOf(pParam.OP_TOTAL) + "|";
				return ;
			}
			
			Double dYield = Double.valueOf(pParam.OP_PASS) / Double.valueOf(pParam.OP_TOTAL) * 100.0;
			Double dSpec = pStd.dLIMIT_YIELD;
			
			pParam.logReason = " OP_PASS=|" + String.valueOf(pParam.OP_PASS)+ "|" ;
			pParam.logReason += " OP_TOTAL=|" + String.valueOf(pParam.OP_TOTAL)+ "|" ;
			pParam.logReason += " dYield=|" + String.valueOf(dYield)+ "|" ;
			pParam.logReason += " dSpec=|" + String.valueOf(dSpec)+ "|" ;	
			
			//설비 총 수량이 기준정보의 특정수량보다 작을 때
			if(dYield<dSpec){
				pParam.logReason ="[dYield<dSpec] =>" + pParam.logReason;
				pParam.logPass = "N";
				pParam.logSpec = String.valueOf(dSpec);
				pParam.logValue = String.valueOf(dYield);
				
				ArrayList<String> alTemp = new ArrayList<String>();
				
				splitArrayList(alTemp, pStd.YIELD_SBL, ":");
				for(int i=0; i<alTemp.size(); i++){
					if(i==0){
						pParam.rtSblAllCode.add(alTemp.get(i));
					}else if(i==1){
						pParam.rtSblComment = alTemp.get(i);
					}
				}
				pParam.rtYield = pStd.LIMIT_YIELD;
				pParam.rtSBL1 = "LOT";
				pParam.rtSBL2 = "YLD";
				pParam.rtSblCode = "101";
				pParam.rtReasonCode.add("");
				
				pParam.makeLogResult();
				//System.out.println("yiled 걸렸다");
			}
		}catch(Exception e){
			pParam.logPass = "ERROR";
			pParam.logReason = e.toString();
			logger.error(e.toString());
		}
	}
	
	private void failsum(SmtIsi_Parameter pParam, SmtIsi_Stdinfo pStd){
		pParam.logJudge2 = "FAIL_SUM";
		
		try{
			if(pParam.OP_TOTAL <= 0){
				pParam.logPass = "ERROR";
				pParam.logReason ="[OP_TOTAL<=0] => ";
				pParam.logReason += "OP_TOTAL=|" + String.valueOf(pParam.OP_TOTAL) + "|";
				return ;
			}
			Double dfailsum = Double.valueOf(pParam.aOcnt[4]+pParam.aOcnt[5]+pParam.aOcnt[6]+pParam.aOcnt[7]);
			Double dfailsumYield = dfailsum/Double.valueOf(pParam.OP_TOTAL)*100.0;
			Double dSpec = pStd.dFAIL_SUM;
			
			pParam.logReason = " OP_PASS=|" + String.valueOf(pParam.OP_PASS)+ "|" ;
			pParam.logReason += " OP_TOTAL=|" + String.valueOf(pParam.OP_TOTAL)+ "|" ;
			pParam.logReason += " dfailsum=|" + String.valueOf(dfailsum)+ "|" ;
			pParam.logReason += " dfailsumYield=|" + String.valueOf(dfailsumYield)+ "|" ;
			pParam.logReason += " dSpec=|" + String.valueOf(dSpec)+ "|" ;
			
			if(dfailsumYield > dSpec)
			{
				pParam.logReason ="[dfailsumYield > dSpec] =>";
				pParam.logPass = "N";
				pParam.logSpec = String.valueOf(dSpec);
				pParam.logValue = String.valueOf(dfailsumYield);

				ArrayList<String> alTemp = new ArrayList<String>();
				splitArrayList(alTemp, pStd.YIELD_SBL, ":");
				for(int i=0; i<alTemp.size(); i++)
				{
					if(i==0){
						pParam.rtSblAllCode.add(alTemp.get(i));
					}else if(i==1){
						pParam.rtSblComment = alTemp.get(i);
					}
				}
				pParam.flag_Stop = true;
				pParam.rtYield = pStd.FAIL_SUM;
				pParam.rtSBL1 = "LOT";
				pParam.rtSBL2 = "F_SUM";
				pParam.rtSblCode = "101";
				pParam.rtReasonCode.add("");

				pParam.makeLogResult();
			}
		}catch(Exception e)
		{
			pParam.logPass = "ERROR";
			pParam.logReason = e.toString();
			logger.error(e.toString());
		}
	}
	
	
	private boolean splitArrayList(ArrayList<String> pTemp, String pData, String pD){
		
		boolean PassFlag = true;
		try{
			if(pTemp == null || pData == null || pD == null ){
				PassFlag = false;
			}else{
				StringTokenizer stData = new StringTokenizer(pData, pD);
				
				while(stData.hasMoreTokens()){
					pTemp.add(stData.nextToken());
				}
			}
		}catch (Exception e){
			PassFlag = false;
		}
		
		return PassFlag;
	}
}
