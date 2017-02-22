package com.tptech.isi;

import org.apache.log4j.Logger;

import com.tptech.Hist_Variable;
import com.tptech.log.WriteLogger;


public class SmtIsi_JudgeMix {

	private static Logger logger = WriteLogger.getInstance("SmtSbl_JudgeMix.java");
	/**************************************************************************
	 * Mix 판정을 한다.
	 * ------------------------------------------------------------------------
	 * @return - PASS, FAIL, ERROR
	 */
	public String run(SmtIsi_Parameter clsParam, SmtIsi_Stdinfo clsStd){

		clsParam.clearStepValue();
		clsParam.logPass = "Y";
		clsParam.logJudge1 = "MIX";		
		
		if(!clsParam.flag_Stop){
			judgeMix(clsParam, clsStd);
			clsParam.ctrlFlag(false);
		}
		
		return "";
	}
	
	
	
	/**************************************************************************
	 * Mix 판정을 한다.
	 * ------------------------------------------------------------------------
	 * @param pParam - 인자값 클래스
	 */
	public void judgeMix(SmtIsi_Parameter pParam, SmtIsi_Stdinfo pStd)
	{
		// BIN 1~4번까지 nOpCat > nEqpCat 경우가 생길 때
		try{
			
			// TEND 모드이거나  Ando 설비일 때
			pParam.logReason = " END_MODE=|" + Hist_Variable.hs.get("Tmode") + "|";
			pParam.logReason += " EQP_MODEL=|" + Hist_Variable.hs.get("System") + "|";
			
			if(Hist_Variable.hs.get("Tmode").equals("TEND")||Hist_Variable.hs.get("System").substring(0,3).equals("A60"))
			{
				pParam.logJudge2 = "TEND";
				for(int i=0; i<4; i++) // BIN 1~4번까지 nOpCat > nEqpCat 경우가 생길 때
				{
					pParam.logReason += " (OP_Cat"+String.valueOf(i+1)+"<HP_Cat" +String.valueOf(i+1)+ ")="; 
					pParam.logReason += "(" + String.valueOf(pParam.aOcnt[i]) + "<" +  String.valueOf(pParam.aHcnt[i]) + ")";
					if(pParam.aOcnt[i]>pParam.aHcnt[i])
					{
						pParam.logReason ="[LOOP BIN1~4, if(OP_Cat < HP_Cat)] =>" + pParam.logReason; 
						pParam.logPass = "N";
						pParam.logSpec = String.valueOf(pParam.aHcnt[i]);
						pParam.logValue = String.valueOf(pParam.aOcnt[i]);
						
						pParam.rtSblAllCode.add("HOLD");
						pParam.rtYield = pStd.LIMIT_YIELD;
						pParam.rtSBL1 = "LOT";
						pParam.rtSBL2 = "YLD";
						pParam.rtSblCode = "MIX";
						pParam.rtSblComment = "";
						pParam.rtReasonCode.add("");
						
						pParam.makeLogResult();
						
					}
				}
			}else{
				pParam.logJudge2 = "NOT_TEND";
				for(int i=0; i<4; i++) // BIN 1~4번까지 nHdCat > nEqpCat 경우가 생길 때
				{
					pParam.logReason += " (HD_Cat"+String.valueOf(i+1)+">OP_Cat" +String.valueOf(i+1)+ ")="; 
					pParam.logReason += "(" + String.valueOf(pParam.aHcnt[i]) + ">" +  String.valueOf(pParam.aOcnt[i]) + ")";
					if(pParam.aHcnt[i]>pParam.aOcnt[i])
					{
						pParam.logReason ="[LOOP BIN1~4, if(HD_Cat > OP_Cat)] =>" + pParam.logReason;
						pParam.logPass = "N";
						pParam.logSpec = String.valueOf(pParam.aOcnt[i]);
						pParam.logValue = String.valueOf(pParam.aHcnt[i]);
						
						pParam.rtSblAllCode.add("HOLD");
						pParam.rtYield = pStd.LIMIT_YIELD;
						pParam.rtSBL1 = "LOT";
						pParam.rtSBL2 = "YLD";
						pParam.rtSblCode = "MIX";
						pParam.rtSblComment = "";
						pParam.rtReasonCode.add("");
						
						pParam.makeLogResult();
					}
				}
			}			
		}catch(Exception e)
		{
			logger.error(e.toString());
/*			pParam.logPass = "ERROR";
			pParam.logReason = e.toString();*/
		}
	
	}
	
	
}
