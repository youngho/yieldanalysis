package com.tptech.isi;

import org.apache.log4j.Logger;

import com.tptech.Hist_Variable;
import com.tptech.log.WriteLogger;


public class SmtIsi_JudgeNot {
	
	private static Logger logger = WriteLogger.getInstance("SmtSbl_JudgeExcept.java");
	
	public void run(SmtIsi_Parameter clsParam, SmtIsi_Stdinfo clsStd){
		//SBL_Parameter clsParam = new SBL_Parameter();
		//SBL_Stdinfo clsStd = new SBL_Stdinfo();

		clsParam.clearStepValue();
		clsParam.logPass = "Y";
		clsParam.logJudge1 = "NOT";		
		
		if(!clsParam.flag_Stop){
			tend100( clsParam,  clsStd);
			clsParam.ctrlFlag(false);
		}
	}
	
	
	private void tend100(SmtIsi_Parameter pParam, SmtIsi_Stdinfo pStd){
		try{
			pParam.logJudge1 = "NOT";
			pParam.logReason = " OP_Cat9=|" + String.valueOf(pParam.aOcnt[8])+ "|" ;
			pParam.logReason += " HANDLER=|" + Hist_Variable.hs.get("System")+ "|" ;
			pParam.logReason += " PRODUCT=|" +Hist_Variable.hs.get("ProductId")+ "|" ;
			
			if(pParam.aOcnt[8] > 100 
				&& Hist_Variable.hs.get("System").length()>=6 
				&& Hist_Variable.hs.get("System").substring(0,6).equals("M6541A")
				&& !Hist_Variable.hs.get("ProductId").substring(0,2).equals("K8"))
			{
				if(!Hist_Variable.hs.get("LotId").substring(0,3).equals("ENG") 
					||!(Hist_Variable.hs.get("ProcessId").length()>6 && Hist_Variable.hs.get("ProcessId").substring(4,7).equals("LQA")))
				{
					pParam.logReason ="[OP_Cat9>100] =>" + pParam.logReason;
					pParam.logPass = "N";
					pParam.logSpec = String.valueOf("100");
					pParam.logValue = String.valueOf(pParam.aOcnt[8]);
					
					pParam.flag_Stop = true;
					pParam.rtSblAllCode.add("");
					pParam.rtYield = pStd.LIMIT_YIELD;
					pParam.rtSBL1 = "LOT";
					pParam.rtSBL2 = "";
					pParam.rtSblCode = "NOT";
					pParam.rtSblComment = "";
					pParam.rtReasonCode.add("");
					
					pParam.makeLogResult();
					
				}
			}
		}catch(Exception e)
		{
			pParam.logPass = "ERROR";
			pParam.logReason = e.toString();
			logger.error(e.toString());
			//Sbl_Utils.sblmakeLog(pParam.MODEL, pParam.LOTID+" [[ERROR]] [JudgeNot] tend100) error >>>  "+e.toString() );
		}
	}
}
