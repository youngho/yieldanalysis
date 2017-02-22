package com.tptech.isi;

import com.tptech.Hist_Variable;


public class SmtIsi_JudgeLos {

	
	public void run(SmtIsi_Parameter clsParam, SmtIsi_Stdinfo clsStd){
		//SBL_Parameter clsParam = new SBL_Parameter();
		//SBL_Stdinfo clsStd = new SBL_Stdinfo();

		clsParam.clearStepValue();
		clsParam.logPass = "Y";
		clsParam.logJudge1 = "LOS";		
		
		if(!clsParam.flag_Stop){
			losCase(clsParam, clsStd);
			clsParam.ctrlFlag(true);
		}
	}
	
	
	private void losCase(SmtIsi_Parameter pParam, SmtIsi_Stdinfo pStd){
		String sMode = Hist_Variable.hs.get("Tmode");
		String sModel = Hist_Variable.hs.get("Tester").toUpperCase();
		
		pParam.logReason = " END_MODE=|" + Hist_Variable.hs.get("Tmode") + "|" ;
		pParam.logReason += " EQP_MODEL=|" + Hist_Variable.hs.get("Tester")+ "|" ;
		if(sMode.substring(0,4).equals("TEND")){
			pParam.logJudge2 = "TEND";
			for(int i=0; i<4; i++){
				pParam.logReason += " (HP_Cat"+String.valueOf(i+1)+"<OP_Cat" +String.valueOf(i+1)+ ")="; 
				pParam.logReason += "(" + String.valueOf(pParam.aHcnt[i]) + "<" +  String.valueOf(pParam.aOcnt[i]) + ")";
				
				if(pParam.aHcnt[i] < pParam.aOcnt[i]){
					pParam.logReason ="[LOOP BIN1~4, if(HP_Cat < OP_Cat)] =>" + pParam.logReason; 
					pParam.logPass = "N";
					pParam.logSpec = String.valueOf(pParam.aOcnt[i]);
					pParam.logValue = String.valueOf(pParam.aHcnt[i]);
					
					pParam.rtSblAllCode.add("");
					pParam.rtYield = pStd.LIMIT_YIELD;
					pParam.rtSBL1 = "LOT";
					pParam.rtSBL2 = "";
					pParam.rtSblCode = "LOS";
					pParam.rtSblComment = "";
					pParam.rtReasonCode.add("");
					
					pParam.makeLogResult();
				}
			}
		}else{
			pParam.logJudge2 = "NOT_TEND";
			if(sModel.substring(0,3).equals("A60")){
				pParam.logReason += "[EQP_MODEL like ''A60%''] ";
				pParam.logPass = "N";
				pParam.logSpec = String.valueOf("A60%");
				pParam.logValue = String.valueOf(sModel);


				pParam.rtSblAllCode.add("");
				pParam.rtYield = pStd.LIMIT_YIELD;
				pParam.rtSBL1 = "LOT";
				pParam.rtSBL2 = "";
				pParam.rtSblCode = "LOS";
				pParam.rtSblComment = "";
				pParam.rtReasonCode.add("");
				
				pParam.makeLogResult();
			}else{
				for(int i=0; i<4; i++){
					pParam.logReason += " (HD_Cat"+String.valueOf(i+1)+"<OP_Cat" +String.valueOf(i+1)+ ")="; 
					pParam.logReason += " (" + String.valueOf(pParam.aHcnt[i]) + "<" + String.valueOf(pParam.aOcnt[i]) + ")";
					if(pParam.aHcnt[i] < pParam.aOcnt[i]){
						pParam.logReason ="[LOOP BIN1~4, if(HD_Cat < OP_Cat)] =>" + pParam.logReason; 
						pParam.logPass = "N";
						pParam.logSpec = String.valueOf(pParam.aOcnt[i]);
						pParam.logValue = String.valueOf(pParam.aHcnt[i]);

						pParam.rtSblAllCode.add("");
						pParam.rtYield = pStd.LIMIT_YIELD;
						pParam.rtSBL1 = "LOT";
						pParam.rtSBL2 = "";
						pParam.rtSblCode = "LOS";
						pParam.rtSblComment = "";
						pParam.rtReasonCode.add("");
						
						pParam.makeLogResult();
					}
				}
			}
		}
	}
}
