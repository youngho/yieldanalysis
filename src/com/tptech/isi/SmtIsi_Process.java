package com.tptech.isi;

import org.apache.log4j.Logger;

import com.tptech.Hist_Variable;
import com.tptech.log.WriteLogger;
import com.tptech.smtoscar_hist.SmtOscar_Hist_Dele;
import com.tptech.util.Utils;

public class SmtIsi_Process {

	private static Logger logger = WriteLogger.getInstance("SmtSbl_Process.java");
	
	public void Sbl_Start(){
		SmtIsi_Parameter parameter  = new SmtIsi_Parameter();
		SmtIsi_Stdinfo stdinfo  = new SmtIsi_Stdinfo();
		
		if(!parameter.set()){
			parameter.ctrlFlag(false);
			logger.error(Hist_Variable.hs.get("LotId") + " Parameter"); 
		}
		
		boolean sStdinfoFalg = true;
		// ���������� setting �Ѵ�.
		// ���������� ���� ��� RESULT=FAIL , RESULT_CMD=NOT Register 
		if(!stdinfo.setValue(parameter)){
			sStdinfoFalg = false;
			parameter.ctrlFlag(false);
			logger.error(parameter.toString());	
			//return ;
		}		
		
		SmtIsi_JudgeExcept clsJudgeExcept = new SmtIsi_JudgeExcept();
		SmtIsi_JudgeMix clsJudgeMix = new SmtIsi_JudgeMix();
		SmtIsi_JudgeSubbin clsJudgeSubbin = new SmtIsi_JudgeSubbin();
		SmtIsi_JudgeYield clsJudgeYield = new SmtIsi_JudgeYield();
		SmtIsi_JudgeNot clsJudgeNot = new SmtIsi_JudgeNot();
		SmtIsi_JudgeLos clsJudgeLos = new SmtIsi_JudgeLos();
		SmtOscar_Hist_Dele dele = new SmtOscar_Hist_Dele();
		
		// ���� ����1 
		if(!parameter.flag_Stop){
			logger.info("���� ����1");
			clsJudgeExcept.runBefore(parameter, stdinfo);
		}
		
		// MIX ����
		if(!parameter.flag_Stop){
			logger.info("MIX ����");
			clsJudgeMix.run(parameter, stdinfo);
		}
		
		// ���� ����2 
		if(!parameter.flag_Stop){
			logger.info("���� ����2");
			clsJudgeExcept.runAfter(parameter, stdinfo);
		}
		
		// SubBin ����
		if(!parameter.flag_Stop){
			logger.info("SubBin ����");
			clsJudgeSubbin.run(parameter, stdinfo);
		}	
		
		// Yield ����
		if(!parameter.flag_Stop)
		{
			logger.info("Yield ����");
			clsJudgeYield.run(parameter, stdinfo);
		}		
		
		// NOT ����
		if(!parameter.flag_Stop)
		{
			logger.info("NOT ����");
			clsJudgeNot.run(parameter, stdinfo);
		}	
		
		// LOS ����
		if(!parameter.flag_Stop)
		{
			logger.info("LOS ����");
			clsJudgeLos.run(parameter, stdinfo);
		}
		
		if(!parameter.flag_Stop){
			// LOS ���� �ɸ����
			if(parameter.flag_RealMode && parameter.flag_ResultWrite){
				//dele.insertResult();
			}else if(parameter.flag_RealMode && !parameter.flag_ResultWrite){ // ����������  PASS�� �� ���.
				
				String[] sResultCode = new String[8];
				for(int i=0; i< 8; i++) sResultCode[i] = "";
				sResultCode[1] = stdinfo.LIMIT_YIELD;
				sResultCode[2] = "LOT";
				sResultCode[4] = "333";
				
				parameter.rtJudge1 = "-";
				parameter.rtJudge2 = "-";
				parameter.rtPass = "Y";
				parameter.rtSpec = "-";
				parameter.rtValue = "-";
				parameter.rtReason = "-";
				parameter.rtResult.clear();
				//parameter.rtResult.add(":" + stdinfo.LIMIT_YIELD + ":LOT:::::");
				parameter.rtResult.add(sResultCode);
				parameter.rtResultDb.add(":" + stdinfo.LIMIT_YIELD + ":LOT::333:::");
				
			}
		}
		dele.insertResult(parameter, stdinfo);

/*		String sResult="";
		for(int i=0; i<parameter.rtResult.size(); i++)
		{
			sResult = parameter.rtResult.get(i);
		}*/

		//for(int i=0; i<parameter.rtResult.size(); i++){
			String[] sResult = parameter.rtResult.get(0);
			Hist_Variable.hs.put("SBL_CODE", sResult[4]);
			Hist_Variable.hs.put("SBL_JUDGE", sResult[4]);
			Hist_Variable.hs.put("SBL_CMD", sResult[1]);
			Hist_Variable.hs.put("RUN_SBL_CODE", sResult[3]);
			Hist_Variable.hs.put("RUN_SBL_CMD", sResult[6]);
			
		//}
		

		
		Utils util = new Utils();
		String sSbl = "SBL_CODE="+Hist_Variable.hs.get("SBL_CODE") + " " 
						+ "SBL_JUDGE="+Hist_Variable.hs.get("SBL_JUDGE")+ " " 
						+ "SBL_CMD="+Hist_Variable.hs.get("SBL_CMD") + " "
						+ "RUN_SBL_CODE="+Hist_Variable.hs.get("RUN_SBL_CODE") + " " 
						+ "RUN_SBL_CMD="+Hist_Variable.hs.get("RUN_SBL_CMD");
		util.SmtOscarFileCreate(Hist_Variable.aFileLine, Hist_Variable.sFileName, sSbl);
	}
}
